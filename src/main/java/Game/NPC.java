/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author balla
 */
public class NPC extends Player {
    public String name;

    // Constructor
    public NPC(String name, int hp, int dmg) {
        super(hp, dmg); // call Player constructor
        this.name = name;
    }

    // Optional: display NPC info
    public void displayInfo() {
        System.out.println("NPC: " + name);
        displayStats(); // inherited from Player
    }
}
