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
public class CursedStatueEvent extends Event {

    @Override
    public void displayText(Player plr) {

        GameIO.show("A towering statue carved from obsidian looms before you.");
        GameIO.show("Its eyes glow faintly, whispering promises of power...");

        // Show effects BEFORE choice
        GameIO.show("\n--- Current Effects ---");
        plr.printEffects();

        // Show stats
        GameIO.show("\n--- Player Stats ---");
        plr.displayStats();

        GameIO.show("\nWill you offer a sacrifice?");
    }

    @Override
    protected String getEventText() {
        return ""; // displayText handles printing
    }

    @Override
    protected void onYes(Player plr) {
        GameIO.show("You place your hand upon the statue... It drains your life!");

        plr.takeDMG(20);

        Item buff = plr.itemManager.getItemOfType(ItemType.BUFF);

        if (buff == null) {
            GameIO.show("But no blessings respond to your sacrifice...");
            return;
        }

        GameIO.show("A blessing manifests before you!");
        buff.displayItem();
        plr.itemManager.addItem(buff);
    }

    @Override
    protected void onNo(Player plr) {
        GameIO.show("You refuse the statue's offer... It curses you!");

        Item curse = plr.itemManager.getItemOfType(ItemType.CURSE);

        if (curse == null) {
            GameIO.show("But no curses answer the call...");
            return;
        }

        curse.displayItem();
        plr.itemManager.addItem(curse);
    }
}
