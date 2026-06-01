/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rpg.model;
import com.mycompany.rpg.ui.*;

/**
 *
 * @author balla
 */
import java.util.ArrayList;

public class ItemManager {

    // Player inventory
    public ArrayList<Item> inventory = new ArrayList<>();

    private Player player;

    public ItemManager(Player player) {
        this.player = player;
    }

    // Get a fresh random item (built by the factory, never a shared instance)
    public static Item getRandomItem() {
        return ItemFactory.createRandom();
    }

    // Get a fresh random item of the given type
    public Item getItemOfType(ItemType type) {
        return ItemFactory.createOfType(type);
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
            player.fireStatsChanged();

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

