/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author balla
 */
import Effects.*;
import java.io.*;

public class Game {

    private int score = 0;
    private static final String HIGHSCORE_FILE = "highscore.txt";

    public static void main(String[] args) {

        Game game = new Game();

        Player plr = new Player(100, 10);   // Example starting stats
        EventHandler eventHandler = new EventHandler();
        
        System.out.println("They said grandmother lives in the forest.");
        System.out.println("You tread forth, repeatedly checking your back; the light of the entrance slowly shrinking");
        System.out.println("'Could they have been lying?', you question. But its too late.");
        System.out.println("Your cloth futile against the cold that traces your skin.");
        System.out.println("No knowledge of forward or back, you give up. You step into the tempered mist");
        System.out.println("Will you make it back, or will you find your grandmother? Each step a ticking hand,");
        System.out.println("You question: 'Can I make it back alive?'");
        new InputHandler().waitForEnter();
        
        int savedHighScore = game.loadHighScore();
        System.out.println("Current High Score: " + savedHighScore);

        while (plr.isAlive()) {
            for (Item item : plr.itemManager.inventory) {
                for (Effects e : item.getEffects()) {
                    e.OnEventStart(plr);
                }
            }
            Event event = eventHandler.getRandomEvent();

            event.run(plr);
            
            game.score++;
            
            for (Item item : plr.itemManager.inventory) {
                for (Effects e : item.getEffects()) {
                    e.OnEventEnd(plr);
                }
            }
            
            new InputHandler().waitForEnter();
        }

        System.out.println("GAME OVER!");
        System.out.println("Final Score: " + game.score);

        // Check for new high score
        if (game.score > savedHighScore) {
            System.out.println("New High Score!");
            game.saveHighScore(game.score);
        } else {
            System.out.println("High Score remains: " + savedHighScore);
        }
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
            System.out.println("Error saving high score.");
        }
    }
}
