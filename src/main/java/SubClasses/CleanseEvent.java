/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubClasses;


import Game.*;

/**
 *
 * @author balla
 */
import java.util.Iterator;

public class CleanseEvent extends Event {

    @Override
    public void displayText(Player plr) {

        System.out.println("You stumble upon a tranquil, shimmering lake.");
        System.out.println("Its waters radiate a soothing, purifying aura.");
        System.out.println("Before making your choice, you inspect yourself...");

        // Print inventory
        System.out.println("\n--- Current Inventory ---");
        if (plr.itemManager.inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
        } else {
            for (Item item : plr.itemManager.inventory) {
                System.out.println("- " + item.name + " (" + item.type + ")");
            }
        }

        // Print effects
        System.out.println("\n--- Current Effects ---");
        plr.printEffects();

        // Print stats
        System.out.println("\n--- Player Stats ---");
        plr.displayStats();

        System.out.println("\nDo you dare cleanse yourself in the lake?");
    }

    @Override
    protected String getEventText() {
        return ""; // displayText handles printing
    }

    @Override
    protected void onYes(Player plr) {
        System.out.println("You step into the lake... A cleansing force washes over you.");

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
            System.out.println("But nothing happens... You had no curses or blessings.");
            return;
        }

        int healAmount = removedCount * 5;
        plr.Heal(healAmount);

        System.out.println("The lake restores your vitality.");
        System.out.println("You were cleansed of " + removedCount + " item(s).");
        System.out.println("You healed " + healAmount + " HP.");
    }

    @Override
    protected void onNo(Player plr) {
        System.out.println("You cautiously dip your hand into the water...");

        for (Item item : plr.itemManager.inventory) {
            if (item.type == ItemType.CURSE) {
                System.out.println("A curse has been lifted: " + item.name);
                item.remove(plr);
                plr.itemManager.inventory.remove(item);
                return;
            }
        }

        System.out.println("But you had no curses to cleanse.");
    }
}
