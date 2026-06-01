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
import java.util.Iterator;

public class CleanseEvent extends Event {

    @Override
    public String getImageName() {
        return "cleanse";
    }

    @Override
    public void displayText(Player plr) {

        GameIO.show("You stumble upon a tranquil, shimmering lake.");
        GameIO.show("Its waters radiate a soothing, purifying aura.");
        GameIO.show("Before making your choice, you inspect yourself...");

        // Print inventory
        GameIO.show("\n--- Current Inventory ---");
        if (plr.itemManager.inventory.isEmpty()) {
            GameIO.show("Inventory is empty.");
        } else {
            for (Item item : plr.itemManager.inventory) {
                GameIO.show("- " + item.name + " (" + item.type + ")");
            }
        }

        // Print effects
        GameIO.show("\n--- Current Effects ---");
        plr.printEffects();

        // Print stats
        GameIO.show("\n--- Player Stats ---");
        plr.displayStats();

        GameIO.show("\nDo you dare cleanse yourself in the lake?");
    }

    @Override
    protected String getEventText() {
        return ""; // displayText handles printing
    }

    @Override
    protected void onYes(Player plr) {
        GameIO.show("You step into the lake... A cleansing force washes over you.");

        Iterator<Item> it = plr.itemManager.inventory.iterator();
        int removedCount = 0;

        while (it.hasNext()) {
            Item item = it.next();
            if (item.type == ItemType.CURSE || item.type == ItemType.BUFF) {
                item.remove(plr);
                it.remove();
                removedCount++;
            }
        }

        if (removedCount == 0) {
            GameIO.show("But nothing happens... You had no curses or blessings.");
            return;
        }

        int healAmount = removedCount * 5;
        plr.Heal(healAmount);

        GameIO.show("The lake restores your vitality.");
        GameIO.show("You were cleansed of " + removedCount + " item(s).");
        GameIO.show("You healed " + healAmount + " HP.");
    }

    @Override
    protected void onNo(Player plr) {
        GameIO.show("You cautiously dip your hand into the water...");

        for (Item item : plr.itemManager.inventory) {
            if (item.type == ItemType.CURSE) {
                GameIO.show("A curse has been lifted: " + item.name);
                item.remove(plr);
                plr.itemManager.inventory.remove(item);
                return;
            }
        }

        GameIO.show("But you had no curses to cleanse.");
    }
}
