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
import SubClasses.*;
        
public class EventHandler {

    // Static array storing events (currently empty)
    public static Event[] EVENTS = new Event[]{
        new ChestEvent(),
        new BattleEvent(),
        new CleanseEvent(),
        new CursedStatueEvent()
    };

    private static final Random random = new Random();

    // Returns a random event from EVENTS
    public Event getRandomEvent() {
        int index = random.nextInt(EVENTS.length);
        return EVENTS[index];
    }
}