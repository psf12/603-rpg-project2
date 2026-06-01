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
import java.util.Random;

public class BattleEvent extends Event {

    private static final Random random = new Random();
    private int difficulty;     // 0 = easy, 1 = medium, 2 = hard
    private String diffName;    // Easy / Medium / Hard

    @Override
    public String getImageName() {
        return "battle";
    }

    @Override
    protected String getEventText() {

        // Choose difficulty BEFORE player chooses yes/no
        difficulty = random.nextInt(3);

        diffName = switch (difficulty) {
            case 0 -> "Easy";
            case 1 -> "Medium";
            case 2 -> "Hard";
            default -> "Unknown";
        };

        return  "A hostile presence lurks nearby...\n"
              + "You sense danger in the air.\n"
              + "Difficulty: " + diffName + "\n"
              + "Will you confront the threat?";
    }

    @Override
    protected void onYes(Player plr) {

        NPC enemy = NPCHandler.getRandomNPC(difficulty);

        GameIO.show("You prepare to battle " + enemy.name + "!");
        BattleHandler.fight(plr, enemy);
    }

    @Override
    protected void onNo(Player plr) {

        // Escape chance between 50% and 100%
        int escapeChance = 50 + random.nextInt(51);

        GameIO.show("Escape chance: " + escapeChance + "%");
        GameIO.show("Attempting to escape...");

        int roll = random.nextInt(100) + 1;

        if (roll <= escapeChance) {
            GameIO.show("You successfully escaped!");
            return;
        }

        // Escape failed → forced battle
        GameIO.show("Escape failed! You are forced into battle!");

        NPC enemy = NPCHandler.getRandomNPC(difficulty);

        GameIO.show("You must fight " + enemy.name + "!");
        BattleHandler.fight(plr, enemy);
    }
}

