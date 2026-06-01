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
public class CursedStatueEvent extends Event {

    @Override
    public void displayText(Player plr) {

        System.out.println("A towering statue carved from obsidian looms before you.");
        System.out.println("Its eyes glow faintly, whispering promises of power...");

        // Show effects BEFORE choice
        System.out.println("\n--- Current Effects ---");
        plr.printEffects();

        // Show stats
        System.out.println("\n--- Player Stats ---");
        plr.displayStats();

        System.out.println("\nWill you offer a sacrifice?");
    }

    @Override
    protected String getEventText() {
        return ""; // displayText handles printing
    }

    @Override
    protected void onYes(Player plr) {
        System.out.println("You place your hand upon the statue... It drains your life!");

        plr.takeDMG(20);

        Item buff = plr.itemManager.getItemOfType(ItemType.BUFF);

        if (buff == null) {
            System.out.println("But no blessings respond to your sacrifice...");
            return;
        }

        System.out.println("A blessing manifests before you!");
        buff.displayItem();
        plr.itemManager.addItem(buff);
    }

    @Override
    protected void onNo(Player plr) {
        System.out.println("You refuse the statue's offer... It curses you!");

        Item curse = plr.itemManager.getItemOfType(ItemType.CURSE);

        if (curse == null) {
            System.out.println("But no curses answer the call...");
            return;
        }

        curse.displayItem();
        plr.itemManager.addItem(curse);
    }
}
