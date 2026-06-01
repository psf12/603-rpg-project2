/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Game;

/**
 *
 * @author balla
 */
import java.util.Random;

public class NPCHandler {

    // Static array storing items (currently empty)
    public static NPC[][] NPCs = new NPC[][]{
        { // easy
            new NPC("Slime", 25, 15),
            new NPC("Goblin", 15, 25)
        },
        { // medium
            new NPC("Cyclopse", 50, 20),
            new NPC("Ghoul", 25, 35)
        },
        { // hard
            new NPC("King Slime", 1000, 20),
            new NPC("Igris", 520, 150)
        }
    };

    private static final Random random = new Random();

    // Returns a random item from ITEMS
    public static NPC getRandomNPC(int difficulty) {
        int index = random.nextInt(NPCs[difficulty].length);
        return NPCs[difficulty][index];
    }
}