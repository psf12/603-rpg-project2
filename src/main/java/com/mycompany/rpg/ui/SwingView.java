package com.mycompany.rpg.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Swing implementation of {@link GameView}: the in-game screen.
 *
 * Layout: a large {@link ImagePanel} fills the centre (~70% of the window) and
 * a control strip sits along the bottom with the narrative text log and the
 * input widgets (Yes/No, Continue, and a text field for the name prompt).
 *
 * Threading: the {@link com.mycompany.rpg.engine.GameEngine} runs on a
 * background thread and calls these methods from there. Output is marshalled to
 * the Swing event thread via {@code invokeLater}; the blocking {@code ask*} /
 * {@code waitForContinue} methods park the game thread on a queue until a button
 * handler (on the event thread) supplies a value. This lets the synchronous
 * game logic stay unchanged while being driven by an event-based GUI.
 *
 * @author balla
 */
public class SwingView implements GameView {

    private static final int MODE_NONE = 0;
    private static final int MODE_YESNO = 1;
    private static final int MODE_CONTINUE = 2;
    private static final int MODE_TEXT = 3;

    private final JPanel panel = new JPanel(new BorderLayout());
    private final ImagePanel imagePanel = new ImagePanel();
    private final JTextArea log = new JTextArea();
    private final JLabel promptLabel = new JLabel(" ");
    private final JTextField textField = new JTextField(16);
    private final JButton yesButton = new JButton("Yes");
    private final JButton noButton = new JButton("No");
    private final JButton continueButton = new JButton("Continue");
    private final JButton submitButton = new JButton("Submit");

    /** Hand-off between the EDT (button clicks) and the game thread. */
    private final BlockingQueue<String> input = new LinkedBlockingQueue<>();

    public SwingView() {
        buildUI();
    }

    /** @return the screen panel, to be placed in the main window. */
    public JPanel getPanel() {
        return panel;
    }

    private void buildUI() {
        log.setEditable(false);
        log.setLineWrap(true);
        log.setWrapStyleWord(true);
        log.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane logScroll = new JScrollPane(log);
        logScroll.setPreferredSize(new Dimension(100, 160));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 6));
        controls.add(promptLabel);
        controls.add(textField);
        controls.add(submitButton);
        controls.add(yesButton);
        controls.add(noButton);
        controls.add(continueButton);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
        bottom.add(logScroll, BorderLayout.CENTER);
        bottom.add(controls, BorderLayout.SOUTH);
        bottom.setPreferredSize(new Dimension(100, 240)); // ~30% of the window

        panel.add(imagePanel, BorderLayout.CENTER);       // ~70% of the window
        panel.add(bottom, BorderLayout.SOUTH);

        yesButton.addActionListener(e -> input.add("Y"));
        noButton.addActionListener(e -> input.add("N"));
        continueButton.addActionListener(e -> input.add(""));
        ActionListener submit = e -> input.add(textField.getText().trim());
        submitButton.addActionListener(submit);
        textField.addActionListener(submit); // Enter key submits

        setMode(MODE_NONE);
    }

    /** Enable only the widgets relevant to the current prompt (EDT only). */
    private void setMode(int mode) {
        yesButton.setEnabled(mode == MODE_YESNO);
        noButton.setEnabled(mode == MODE_YESNO);
        continueButton.setEnabled(mode == MODE_CONTINUE);
        boolean text = (mode == MODE_TEXT);
        textField.setEnabled(text);
        submitButton.setEnabled(text);
        if (!text) {
            promptLabel.setText(" ");
        } else {
            textField.requestFocusInWindow();
        }
    }

    /** Clear the screen for a fresh game. */
    public void reset() {
        input.clear();
        SwingUtilities.invokeLater(() -> {
            log.setText("");
            imagePanel.clear();
            setMode(MODE_NONE);
        });
    }

    // ----------------------------------------------------------------
    // GameView
    // ----------------------------------------------------------------

    @Override
    public void show(String message) {
        SwingUtilities.invokeLater(() -> {
            log.append(message + "\n");
            log.setCaretPosition(log.getDocument().getLength());
        });
    }

    @Override
    public void showImage(String imageName) {
        BufferedImage img = ImageLoader.load(imageName);
        SwingUtilities.invokeLater(() -> {
            if (img != null) {
                imagePanel.setImage(img);
            } else {
                imagePanel.setPlaceholder(imageName);
            }
        });
    }

    @Override
    public boolean askYesNo() {
        input.clear();
        SwingUtilities.invokeLater(() -> setMode(MODE_YESNO));
        return "Y".equals(take());
    }

    @Override
    public String askText(String prompt) {
        input.clear();
        SwingUtilities.invokeLater(() -> {
            promptLabel.setText(prompt);
            textField.setText("");
            setMode(MODE_TEXT);
        });
        return take();
    }

    @Override
    public void waitForContinue() {
        input.clear();
        SwingUtilities.invokeLater(() -> setMode(MODE_CONTINUE));
        take();
    }

    /** Block the game thread until a button supplies input, then disable widgets. */
    private String take() {
        try {
            String value = input.take();
            SwingUtilities.invokeLater(() -> setMode(MODE_NONE));
            return value;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "";
        }
    }
}
