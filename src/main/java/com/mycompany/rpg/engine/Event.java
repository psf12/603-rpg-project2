/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rpg.engine;
import com.mycompany.rpg.model.*;
import com.mycompany.rpg.model.effects.*;
import com.mycompany.rpg.ui.*;

/**
 *
 * @author balla
 */
public abstract class Event {

    // Runs the event (controls flow)
    public void run(Player plr) {
        displayText(plr);

        boolean choice = GameIO.askYesNo();

        if (choice) {
            onYes(plr);
        } else {
            onNo(plr);
        }
    }

    // Concrete method (shared behavior)
    public void displayText(Player plr) {
        GameIO.show(getEventText());
        plr.displayStats();
    }

    /**
     * Base file-name (without extension) of the scene image shown for this
     * event. A graphical front-end looks for {@code images/<name>.png}.
     * Subclasses override this to point at their own image.
     */
    public String getImageName() {
        return "event";
    }

    // Each event provides its own text
    protected abstract String getEventText();

    // Event-specific outcomes
    protected abstract void onYes(Player plr);
    protected abstract void onNo(Player plr);
}
