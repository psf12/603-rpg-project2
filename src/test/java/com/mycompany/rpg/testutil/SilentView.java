package com.mycompany.rpg.testutil;

import com.mycompany.rpg.ui.GameView;

/**
 * A no-op {@link GameView} for tests: swallows all output and never blocks on
 * input. Installing it via {@code GameIO.setView(new SilentView())} keeps unit
 * tests free of console I/O so they run deterministically and without hanging.
 *
 * @author balla
 */
public class SilentView implements GameView {

    @Override
    public void show(String message) {
    }

    @Override
    public boolean askYesNo() {
        return false;
    }

    @Override
    public void waitForContinue() {
    }

    @Override
    public String askText(String prompt) {
        return "";
    }

    @Override
    public void showImage(String imageName) {
    }
}
