package com.mycompany.rpg.engine;

import com.mycompany.rpg.dao.ScoreDAO;
import com.mycompany.rpg.model.*;
import com.mycompany.rpg.model.effects.*;
import com.mycompany.rpg.ui.*;
import java.util.List;

/**
 * Drives the core game loop.
 *
 * The loop was previously embedded in {@code Game.main()}. Extracting it into a
 * reusable engine lets any front-end (console or GUI) start a game by simply
 * constructing a GameEngine and calling {@link #run()}; the engine never
 * touches System.out / Scanner directly, it speaks to the active presentation
 * through {@link GameIO}.
 *
 * High-score persistence is delegated to a {@link ScoreDAO} (data-access
 * layer), so the engine has no knowledge of how or where scores are stored.
 *
 * @author balla
 */
public class GameEngine {

    private static final int LEADERBOARD_SIZE = 5;

    private final Player player;
    private final ScoreDAO scoreDao;
    private final EventHandler eventHandler = new EventHandler();
    private int score = 0;

    public GameEngine(Player player, ScoreDAO scoreDao) {
        this.player = player;
        this.scoreDao = scoreDao;
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

        int savedHighScore = scoreDao.getHighScore();
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
        } else {
            GameIO.show("High Score remains: " + savedHighScore);
        }

        recordScore();
        showLeaderboard();
    }

    public int getScore() {
        return score;
    }

    /** Ask the player's name and persist this run's score via the DAO. */
    private void recordScore() {
        String name = GameIO.askText("Enter your name for the leaderboard:");
        if (name == null || name.isBlank()) {
            name = "Anonymous";
        }
        scoreDao.addScore(new ScoreEntry(name, score));
    }

    /** Display the top scores read back from the database. */
    private void showLeaderboard() {
        GameIO.show("");
        GameIO.show("===== LEADERBOARD (Top " + LEADERBOARD_SIZE + ") =====");
        List<ScoreEntry> top = scoreDao.getTopScores(LEADERBOARD_SIZE);
        if (top.isEmpty()) {
            GameIO.show("No scores yet.");
            return;
        }
        int rank = 1;
        for (ScoreEntry entry : top) {
            GameIO.show(rank + ". " + entry.getPlayerName() + " - " + entry.getScore());
            rank++;
        }
    }
}
