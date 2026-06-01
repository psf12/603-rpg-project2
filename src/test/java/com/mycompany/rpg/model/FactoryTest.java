package com.mycompany.rpg.model;

import com.mycompany.rpg.testutil.SilentView;
import com.mycompany.rpg.ui.GameIO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Before;
import org.junit.Test;

/**
 * Business-logic tests for the Factory pattern and item stacking
 * (Component 2.4.4).
 *
 * @author balla
 */
public class FactoryTest {

    @Before
    public void setUp() {
        GameIO.setView(new SilentView());
    }

    @Test
    public void npcFactoryProducesIndependentInstances() {
        NPC first = NPCFactory.createRandomNPC(0);
        NPC second = NPCFactory.createRandomNPC(0);

        // Each call must build a brand-new NPC (fixes the shared-instance bug
        // where a defeated enemy kept its lost HP into the next encounter).
        assertNotSame(first, second);

        int secondHpBefore = second.hp;
        first.hp -= 5; // damaging one enemy must not affect another
        assertEquals(secondHpBefore, second.hp);
    }

    @Test
    public void itemFactoryProducesIndependentInstances() {
        assertNotSame(ItemFactory.createRandom(), ItemFactory.createRandom());
    }

    @Test
    public void duplicateItemsStackTheirStatBonus() {
        Player player = new Player(100, 10);
        // Two copies of a +25 HP curse item (intended stacking behaviour).
        Item shardA = new Item("Test Shard", 25, 0).setType(ItemType.CURSE);
        Item shardB = new Item("Test Shard", 25, 0).setType(ItemType.CURSE);

        player.itemManager.addItem(shardA);
        player.itemManager.addItem(shardB);

        assertEquals(150, player.hp);                       // both bonuses applied
        assertEquals(2, player.itemManager.inventory.size()); // both copies kept
    }
}
