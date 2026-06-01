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

        boolean choice = InputHandler.getYesNoInput();

        if (choice) {
            onYes(plr);
        } else {
            onNo(plr);
        }
    }

    // Concrete method (shared behavior)
    public void displayText(Player plr) {
        System.out.println(getEventText());
        plr.displayStats();
    }

    // Each event provides its own text
    protected abstract String getEventText();

    // Event-specific outcomes
    protected abstract void onYes(Player plr);
    protected abstract void onNo(Player plr);
}
