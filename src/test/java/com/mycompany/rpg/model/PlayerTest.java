package com.mycompany.rpg.model;

import com.mycompany.rpg.testutil.SilentView;
import com.mycompany.rpg.ui.GameIO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Business-logic tests for {@link Player} (Component 2.4.4): damage, healing,
 * the alive/dead condition, and the Observer notification.
 *
 * @author balla
 */
public class PlayerTest {

    @Before
    public void setUp() {
        GameIO.setView(new SilentView()); // no console I/O during tests
    }

    @Test
    public void takeDamageReducesHp() {
        Player player = new Player(100, 10);
        player.takeDMG(30);
        assertEquals(70, player.hp);
    }

    @Test
    public void healIncreasesHp() {
        Player player = new Player(50, 10);
        player.Heal(25);
        assertEquals(75, player.hp);
    }

    @Test
    public void isAliveWhenHpIsPositive() {
        assertTrue(new Player(1, 0).isAlive());
    }

    @Test
    public void isNotAliveWhenHpReachesZero() {
        Player player = new Player(10, 0);
        player.takeDMG(10);
        assertFalse(player.isAlive());
    }

    @Test
    public void observerIsNotifiedWhenStatsChange() {
        Player player = new Player(100, 10);
        int[] notifications = {0};
        int[] observedHp = {-1};

        player.addListener(p -> {
            notifications[0]++;
            observedHp[0] = p.hp;
        });

        player.takeDMG(40);

        assertEquals(1, notifications[0]);
        assertEquals(60, observedHp[0]);
    }
}
