package com.mycompany.rpg.engine;

import com.mycompany.rpg.model.*;
import com.mycompany.rpg.model.effects.*;
import com.mycompany.rpg.ui.*;
import java.io.*;

/**
 * Drives the core game loop.
 *
 * The loop was previously embedded in {@code Game.main()}. Extracting it into a
 * reusable engine lets any front-end (console or GUI) start a game by simply
 * constructing a GameEngine and calling {@link #run()}; the engine never
 * touches System.out / Scanner directly, it speaks to the active presentation
 * through {@link GameIO}.
 *
 * High-score persistence is still file-based here; it is replaced by a Derby
 * data-access layer in Project 2 Step 3.
 *
 * @author balla
 */
public class GameEngine {

    private static final String HIGHSCORE_FILE = "highscore.txt";

    private final Player player;
    private final EventHandler eventHandler = new EventHandler();
    private int score = 0;

    public GameEngine(Player player) {
        this.player = player;
    }

    /** Run a full game from intro to game-over. */
    public void run() {
        GameIO.show("They said grandmother lives in the forest.");
        GameIO.show("You tread forth, repeatedly checking your back; the light of the entrance slowly shrinking");
        GameIO.show("'Could they have been lying?', you question. But its too late.");
        GameIO.show("Your cloth futile against the cold that traces your skin.");
        GameIO.show("No knowledge of forward or back, you give up. You step into the tempered mist");
        GameIO.show("Will you make it back, or will you find your grandmother? Each step a ticking hand,");
        GameIO.show("You question: 'Can I make it back alive?'");
        GameIO.waitForContinue();

        int savedHighScore = loadHighScore();
        GameIO.show("Current High Score: " + savedHighScore);

        while (player.isAlive()) {
            for (Item item : player.itemManager.inventory) {
                for (Effects e : item.getEffects()) {
                    e.OnEventStart(player);
                }
            }

            Event event = eventHandler.getRandomEvent();
            event.run(player);

            score++;

            for (Item item : player.itemManager.inventory) {
                for (Effects e : item.getEffects()) {
                    e.OnEventEnd(player);
                }
            }

            GameIO.waitForContinue();
        }

        GameIO.show("GAME OVER!");
        GameIO.show("Final Score: " + score);

        if (score > savedHighScore) {
            GameIO.show("New High Score!");
            saveHighScore(score);
        } else {
            GameIO.show("High Score remains: " + savedHighScore);
        }
    }

    public int getScore() {
        return score;
    }

    // -----------------------------
    // High Score File I/O
    // -----------------------------

    private int loadHighScore() {
        File file = new File(HIGHSCORE_FILE);
        if (!file.exists()) {
            return 0; // No high score yet
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            return Integer.parseInt(br.readLine());
        } catch (Exception e) {
            return 0; // If file corrupted, reset
        }
    }

    private void saveHighScore(int score) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(HIGHSCORE_FILE))) {
            bw.write(String.valueOf(score));
        } catch (IOException e) {
            GameIO.show("Error saving high score.");
        }
    }
}
