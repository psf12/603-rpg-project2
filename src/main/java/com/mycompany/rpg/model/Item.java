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

public class Item {
    public String name;
    public int hp;
    public int dmg;

    public ItemType type = ItemType.NONE;

    private ArrayList<Effects> effects = new ArrayList<>();

    public Item(String name, int hp, int dmg) {
        this.name = name;
        this.hp = hp;
        this.dmg = dmg;
    }

    public Item setType(ItemType type) {
        this.type = type;
        return this;
    }

    public Item addEffect(Effects effect) {
        effects.add(effect);
        return this;
    }

    public ArrayList<Effects> getEffects() {
        return effects;
    }

    public void displayItem() {
        System.out.println("Item: " + name);
        System.out.println("Type: " + type);
        System.out.println("HP Bonus: " + hp);
        System.out.println("DMG Bonus: " + dmg);
    }

    public void remove(Player p) {
        p.hp -= this.hp;
        p.dmg -= this.dmg;
        System.out.println("Removed: " + name);
    }
}


