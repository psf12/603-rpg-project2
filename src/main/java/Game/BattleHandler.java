/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

import Effects.*;

/**
 *
 * @author balla
 */
public class BattleHandler {

    // Runs the fight and returns true if player wins
    public static void fight(Player plr, NPC npc) {

        // Trigger OnBattleStart effects
        for (Item item : plr.itemManager.inventory) {
            for (Effects e : item.getEffects()) {
                e.OnBattleStart(plr);
            }
        }

        // Fight loop
        while (plr.hp > 0 && npc.hp > 0) {
            plr.hp -= npc.dmg;
            npc.hp -= plr.dmg;
        }

        // Trigger OnBattleEnd effects
        for (Item item : plr.itemManager.inventory) {
            for (Effects e : item.getEffects()) {
                e.OnBattleEnd(plr);
            }
        }

        // NEW: Stat absorption on victory
        if (plr.isAlive()) {
            int hpGain = plr.hp / 2;
            int dmgGain = npc.dmg / 2;

            plr.Heal(hpGain); 
            plr.dmg += dmgGain;

            System.out.println("You absorbed power from " + npc.name + "!");
            System.out.println("Gained +" + hpGain + " HP and +" + dmgGain + " DMG");
        }

        displayBattleResult(plr.isAlive(), npc);
    }


    // Displays result of the fight
    public static void displayBattleResult(boolean result, NPC npc) {
        if (result) {
            System.out.println(npc.name + " Defeated!");
        } else {
            System.out.println("You were defeated by " + npc.name + "!");
        }
    }
}
