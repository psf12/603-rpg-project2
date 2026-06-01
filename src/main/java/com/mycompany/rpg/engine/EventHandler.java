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