package com.mycompany.rpg.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * Panel that displays the current scene image, scaled to COVER the panel
 * (fills the whole area, preserving aspect ratio and cropping any overflow).
 *
 * When no image is set it paints a labelled placeholder so the game is still
 * fully playable before any artwork has been added to the {@code images/}
 * folder. The panel can hold child components (used by the menu to overlay
 * buttons on top of its background image).
 *
 * @author balla
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private String placeholder;

    public ImagePanel() {
        setBackground(new Color(20, 20, 24));
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        this.placeholder = null;
        repaint();
    }

    public void setPlaceholder(String text) {
        this.image = null;
        this.placeholder = text;
        repaint();
    }

    public void clear() {
        this.image = null;
        this.placeholder = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int pw = getWidth();
        int ph = getHeight();

        if (image != null) {
            int iw = image.getWidth();
            int ih = image.getHeight();
            if (iw > 0 && ih > 0) {
                double scale = Math.max((double) pw / iw, (double) ph / ih); // cover
                int dw = (int) Math.round(iw * scale);
                int dh = (int) Math.round(ih * scale);
                int x = (pw - dw) / 2;
                int y = (ph - dh) / 2;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(image, x, y, dw, dh, null);
                g2.dispose();
            }
        } else if (placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(90, 90, 100));
            g2.setFont(getFont().deriveFont(Font.BOLD, 28f));
            String text = "[ " + placeholder + " ]";
            FontMetrics fm = g2.getFontMetrics();
            int tw = fm.stringWidth(text);
            g2.drawString(text, (pw - tw) / 2, ph / 2);
            g2.dispose();
        }
    }
}
