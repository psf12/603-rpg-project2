package com.mycompany.rpg;

import com.mycompany.rpg.engine.GameEngine;
import com.mycompany.rpg.model.Player;
import com.mycompany.rpg.ui.ConsoleView;
import com.mycompany.rpg.ui.GameIO;

/**
 * Application entry point.
 *
 * Wires up the presentation strategy and starts the game engine. Currently uses
 * the console front-end ({@link ConsoleView}); Project 2 Step 4 adds a Swing
 * front-end that is selected here instead, with no change to the game logic.
 *
 * @author balla
 */
public class App {

    public static void main(String[] args) {
        // Select the console presentation (original Project 1 CUI behaviour).
        GameIO.setView(new ConsoleView());

        Player player = new Player(100, 10); // starting stats
        new GameEngine(player).run();
    }
}
