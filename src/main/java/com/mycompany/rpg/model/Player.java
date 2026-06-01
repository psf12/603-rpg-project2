/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rpg.model;
import com.mycompany.rpg.model.effects.*;
import com.mycompany.rpg.ui.*;

/**
 *
 * @author balla
 */
import java.util.HashMap;

public class Player {
    public int hp;
    public int dmg;

    public ItemManager itemManager;

    public Player(int hp, int dmg) {
        this.hp = hp;
        this.dmg = dmg;

        // Player now owns an ItemManager
        this.itemManager = new ItemManager(this);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDMG(int dmg){
        this.hp -= dmg;
        GameIO.show(dmg + " DMG taken!");
    }
    
    public void Heal(int amount){
        this.hp += amount;
        GameIO.show(amount + " Healed!");
    }

    public void displayStats() {
        GameIO.show("HP: " + hp + " | DMG: " + dmg);
    }
    public void printEffects() {
        HashMap<String, Integer> effectCounts = new HashMap<>();

        for (Item item : itemManager.inventory) {
            for (Effects e : item.getEffects()) {
                String effectName = e.getClass().getSimpleName();
                effectCounts.put(effectName, effectCounts.getOrDefault(effectName, 0) + 1);
            }
        }

        if (effectCounts.isEmpty()) {
            GameIO.show("No active effects.");
            return;
        }

        GameIO.show("Active Effects:");
        for (var entry : effectCounts.entrySet()) {
            GameIO.show(" - " + entry.getKey() + " x " + entry.getValue());
        }
    }

}
