package com.mycompany.rpg;

import com.mycompany.rpg.dao.Database;
import com.mycompany.rpg.dao.DerbyScoreDAO;
import com.mycompany.rpg.dao.ScoreDAO;
import com.mycompany.rpg.engine.GameEngine;
import com.mycompany.rpg.model.Player;
import com.mycompany.rpg.ui.ConsoleView;
import com.mycompany.rpg.ui.GameIO;
import com.mycompany.rpg.ui.MainFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Application entry point.
 *
 * Wires up the data-access layer (embedded Derby + DAO) then launches the
 * graphical front-end by default. Passing {@code --console} runs the original
 * text-based (CUI) front-end instead; both share the same game engine and
 * data-access layer unchanged, thanks to the {@code GameView} abstraction.
 *
 * @author balla
 */
public class App {

    public static void main(String[] args) {
        boolean console = args.length > 0 && "--console".equalsIgnoreCase(args[0]);

        // Data-access layer: embedded Derby database + DAO (auto-created).
        Database database;
        ScoreDAO scoreDao;
        try {
            database = Database.getInstance();
            scoreDao = new DerbyScoreDAO(database.getConnection());
        } catch (RuntimeException e) {
            String message = "The game database could not be started:\n" + e.getMessage();
            if (console) {
                System.err.println(message);
            } else {
                JOptionPane.showMessageDialog(null, message, "Database Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        // Ensure Derby is shut down cleanly however the program exits.
        Runtime.getRuntime().addShutdownHook(new Thread(database::shutdown));

        if (console) {
            GameIO.setView(new ConsoleView());
            new GameEngine(new Player(100, 10), scoreDao).run();
        } else {
            SwingUtilities.invokeLater(() -> {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception ignored) {
                    // fall back to the default look and feel
                }
                new MainFrame(scoreDao).setVisible(true);
            });
        }
    }
}
