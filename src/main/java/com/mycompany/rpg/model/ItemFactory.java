package com.mycompany.rpg.model;

import com.mycompany.rpg.model.effects.AzureFire;
import com.mycompany.rpg.model.effects.Bleed;
import com.mycompany.rpg.model.effects.Fire;
import com.mycompany.rpg.model.effects.WeakHeal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory that builds fresh {@link Item} instances from the item catalogue
 * (Factory pattern).
 *
 * Each call constructs brand-new Item (and effect) objects, fixing the original
 * shared-instance bug where the same Item object from a static array could be
 * added to the inventory more than once and have its stat bonuses applied or
 * removed inconsistently.
 *
 * @author balla
 */
public final class ItemFactory {

    private static final Random RANDOM = new Random();

    private ItemFactory() {
    }

    /** @return a fresh copy of every item in the catalogue. */
    private static List<Item> catalogue() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("Vampire Shard", 25, 0).setType(ItemType.CURSE).addEffect(new Bleed()));
        items.add(new Item("Blazing Soul", 5, 5).setType(ItemType.CURSE).addEffect(new Fire()));
        items.add(new Item("Blue Ash", 0, 15).setType(ItemType.CURSE).addEffect(new AzureFire()));
        items.add(new Item("Goldilocks Flower", 0, -1).setType(ItemType.BUFF).addEffect(new WeakHeal()));
        items.add(new Item("Journeyman Dagger", 0, 15).setType(ItemType.WEAPON));
        items.add(new Item("Hero Sword", 0, 25).setType(ItemType.WEAPON));
        return items;
    }

    /** @return a new random item from the catalogue. */
    public static Item createRandom() {
        List<Item> items = catalogue();
        return items.get(RANDOM.nextInt(items.size()));
    }

    /**
     * @param type the item category to pick from
     * @return a new random item of that type, or {@code null} if none exist
     */
    public static Item createOfType(ItemType type) {
        List<Item> matches = new ArrayList<>();
        for (Item item : catalogue()) {
            if (item.type == type) {
                matches.add(item);
            }
        }
        if (matches.isEmpty()) {
            return null;
        }
        return matches.get(RANDOM.nextInt(matches.size()));
    }
}
