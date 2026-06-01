package com.mycompany.rpg.ui;

import com.mycompany.rpg.dao.ScoreDAO;
import com.mycompany.rpg.engine.GameEngine;
import com.mycompany.rpg.model.Player;
import com.mycompany.rpg.model.ScoreEntry;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Main application window (the View/Controller shell of the GUI).
 *
 * Holds three cards in a {@link java.awt.CardLayout}: the main menu, the
 * in-game screen ({@link SwingView}), and the leaderboard. "New Game" runs a
 * {@link GameEngine} on a background thread so the GUI stays responsive while
 * the game logic blocks waiting for the player's button clicks.
 *
 * @author balla
 */
public class MainFrame extends JFrame {

    private static final String MENU = "MENU";
    private static final String GAME = "GAME";
    private static final String BOARD = "BOARD";

    private final java.awt.CardLayout cards = new java.awt.CardLayout();
    private final JPanel root = new JPanel(cards);
    private final ScoreDAO scoreDao;
    private final SwingView gameView = new SwingView();
    private DefaultTableModel boardModel;

    public MainFrame(ScoreDAO scoreDao) {
        this.scoreDao = scoreDao;
        setTitle("Into the Forest — RPG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 760);
        setMinimumSize(new Dimension(720, 560));
        setLocationRelativeTo(null);

        root.add(buildMenu(), MENU);
        root.add(gameView.getPanel(), GAME);
        root.add(buildBoard(), BOARD);
        setContentPane(root);
        cards.show(root, MENU);
    }

    // ----------------------------------------------------------------
    // Menu
    // ----------------------------------------------------------------

    private JComponent buildMenu() {
        ImagePanel background = new ImagePanel();
        BufferedImage menuImg = ImageLoader.load("menu");
        if (menuImg != null) {
            background.setImage(menuImg);
        } else {
            background.setPlaceholder("menu");
        }
        background.setLayout(new GridBagLayout());

        JPanel box = new JPanel();
        box.setOpaque(false);
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Into the Forest");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Serif", Font.BOLD, 48));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton newGame = menuButton("New Game");
        JButton leaderboard = menuButton("Leaderboard");
        JButton quit = menuButton("Quit");

        newGame.addActionListener(e -> startGame());
        leaderboard.addActionListener(e -> {
            refreshBoard();
            cards.show(root, BOARD);
        });
        quit.addActionListener(e -> dispose());

        box.add(title);
        box.add(Box.createVerticalStrut(30));
        box.add(newGame);
        box.add(Box.createVerticalStrut(12));
        box.add(leaderboard);
        box.add(Box.createVerticalStrut(12));
        box.add(quit);

        background.add(box, new GridBagConstraints());
        return background;
    }

    private JButton menuButton(String text) {
        JButton b = new JButton(text);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(240, 44));
        b.setMaximumSize(new Dimension(240, 44));
        b.setFont(new Font("SansSerif", Font.BOLD, 18));
        return b;
    }

    // ----------------------------------------------------------------
    // Leaderboard
    // ----------------------------------------------------------------

    private JComponent buildBoard() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JLabel heading = new JLabel("Leaderboard", SwingConstants.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 28));
        p.add(heading, BorderLayout.NORTH);

        boardModel = new DefaultTableModel(new Object[]{"Rank", "Name", "Score", "Date"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(boardModel);
        table.setRowHeight(26);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        p.add(new JScrollPane(table), BorderLayout.CENTER);

        JButton back = new JButton("Back to Menu");
        back.addActionListener(e -> cards.show(root, MENU));
        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));
        south.add(back);
        p.add(south, BorderLayout.SOUTH);
        return p;
    }

    private void refreshBoard() {
        boardModel.setRowCount(0);
        List<ScoreEntry> top = scoreDao.getTopScores(10);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int rank = 1;
        for (ScoreEntry entry : top) {
            String date = entry.getAchievedAt() != null ? entry.getAchievedAt().format(fmt) : "";
            boardModel.addRow(new Object[]{rank++, entry.getPlayerName(), entry.getScore(), date});
        }
    }

    // ----------------------------------------------------------------
    // Game
    // ----------------------------------------------------------------

    private void startGame() {
        gameView.reset();
        GameIO.setView(gameView);
        cards.show(root, GAME);

        Thread gameThread = new Thread(() -> {
            try {
                new GameEngine(new Player(100, 10), scoreDao).run();
                gameView.show("");
                gameView.show("Press Continue to return to the menu.");
                gameView.waitForContinue();
            } finally {
                SwingUtilities.invokeLater(() -> cards.show(root, MENU));
            }
        }, "game-thread");
        gameThread.setDaemon(true);
        gameThread.start();
    }
}
