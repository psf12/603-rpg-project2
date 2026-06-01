package com.mycompany.rpg.ui;

/**
 * Presentation abstraction for the game (Strategy pattern).
 *
 * The game logic talks to a GameView instead of directly to System.out / a
 * Scanner. This keeps the business logic independent of how the game is
 * presented, so the same engine can drive either a console front-end
 * ({@link ConsoleView}) or a Swing GUI front-end (added in Project 2 Step 4).
 *
 * @author balla
 */
public interface GameView {

    /** Display a line of text/output to the user. */
    void show(String message);

    /**
     * Ask the user a yes/no question and block until they answer.
     * The question text itself is shown via {@link #show(String)} beforehand.
     *
     * @return true for "yes", false for "no"
     */
    boolean askYesNo();

    /** Pause until the user acknowledges they are ready to continue. */
    void waitForContinue();

    /**
     * Prompt the user for a line of free text (e.g. their name) and return it.
     *
     * @param prompt the message shown before reading input
     * @return the text the user entered (trimmed)
     */
    String askText(String prompt);

    /**
     * Display the scene image associated with the given name. Graphical views
     * look for {@code images/<imageName>.png}; the console view ignores this.
     *
     * @param imageName base file-name (no extension) of the image to show
     */
    void showImage(String imageName);
}
