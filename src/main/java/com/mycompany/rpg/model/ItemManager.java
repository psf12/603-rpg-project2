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
import java.util.ArrayList;
import java.util.Random;

public class ItemManager {

    // Master list of all possible items
    public static Item[] ITEMS = new Item[]{
        new Item("Vampire Shard", 25, 0).setType(ItemType.CURSE).addEffect(new Bleed()),
        new Item("Blazing Soul", 5, 5).setType(ItemType.CURSE).addEffect(new Fire()),
        new Item("Blue Ash", 0, 15).setType(ItemType.CURSE).addEffect(new AzureFire()), 
        new Item("Goldilocks Flower", 0, -1).setType(ItemType.BUFF).addEffect(new WeakHeal()),
        new Item("Journeyman Dagger", 0, 15).setType(ItemType.WEAPON),
        new Item("Hero Sword", 0, 25).setType(ItemType.WEAPON)
    };

    private static final Random random = new Random();

    // Player inventory
    public ArrayList<Item> inventory = new ArrayList<>();

    private Player player;

    public ItemManager(Player player) {
        this.player = player;
    }

    // Get a random item from the master list
    public static Item getRandomItem() {
        int index = random.nextInt(ITEMS.length);
        return ITEMS[index];
    }
    
    // Get an item that is a type of that class
    public Item getItemOfType(ItemType type) {
        ArrayList<Item> matches = new ArrayList<>();

        for (Item i : ITEMS) {
            if (i.type == type) {
                matches.add(i);
            }
        }

        if (matches.isEmpty()) return null;

        return matches.get(random.nextInt(matches.size()));
    }
    
    private Item getCurrentWeapon() {
        for (Item i : inventory) {
            if (i.type == ItemType.WEAPON) {
                return i;
            }
        }
        return null;
    }



    // Add item to inventory and apply buffs
    public void addItem(Item item) {

        // If the new item is a weapon
        if (item.type == ItemType.WEAPON) {

                // Check if player already has a weapon
                Item currentWeapon = getCurrentWeapon();

                if (currentWeapon != null) {
                    GameIO.show("You already have a weapon: " + currentWeapon.name);
                    GameIO.show("Replace it with " + item.name + "?");

                    boolean choice = GameIO.askYesNo();

                    if (!choice) {
                        GameIO.show("You keep your current weapon.");
                        return;
                    }

                    // Remove old weapon
                    currentWeapon.remove(player);
                    inventory.remove(currentWeapon);
                }
            }

            // Add new item normally
            inventory.add(item);
            player.hp += item.hp;
            player.dmg += item.dmg;

            GameIO.show("You received: " + item.name);
    }


    // Remove last item and undo buffs
    public Item removeItem() {
        if (inventory.isEmpty()) {
            GameIO.show("Inventory is empty!");
            return null;
        }

        Item removed = inventory.remove(inventory.size() - 1);
        removed.remove(player); // undo buffs

        return removed;
    }

    public void printInventory() {
        GameIO.show("Inventory:");
        for (Item i : inventory) {
            GameIO.show("- " + i.name);
        }
    }
}

