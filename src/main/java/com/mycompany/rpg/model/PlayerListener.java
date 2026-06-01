package com.mycompany.rpg.model;

/**
 * Observer of {@link Player} state changes (Observer pattern).
 *
 * The GUI registers as a listener so it can refresh its live HP/DMG display the
 * moment the player's stats change, without the model knowing anything about the
 * UI.
 *
 * @author balla
 */
public interface PlayerListener {

    /** Called whenever the player's stats change. */
    void onPlayerChanged(Player player);
}
