package com.mycompany.rpg.ui;

/**
 * Static facade that routes all game input/output through the currently active
 * {@link GameView} (Strategy pattern context).
 *
 * Game logic calls {@code GameIO.show(...)}, {@code GameIO.askYesNo()} and
 * {@code GameIO.waitForContinue()} without knowing or caring whether the active
 * view is the console or the Swing GUI. The front-end selects the presentation
 * by calling {@link #setView(GameView)} once at start-up.
 *
 * @author balla
 */
public final class GameIO {

    /** Active presentation strategy. Defaults to the console view. */
    private static GameView view = new ConsoleView();

    private GameIO() {
        // static-only utility
    }

    /** Swap the active presentation (e.g. console -> Swing GUI). */
    public static void setView(GameView newView) {
        view = newView;
    }

    /** @return the active view. */
    public static GameView getView() {
        return view;
    }

    public static void show(String message) {
        view.show(message);
    }

    public static boolean askYesNo() {
        return view.askYesNo();
    }

    public static void waitForContinue() {
        view.waitForContinue();
    }

    public static String askText(String prompt) {
        return view.askText(prompt);
    }
}
