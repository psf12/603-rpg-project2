package com.mycompany.rpg.model;

import java.util.Random;

/**
 * Factory that produces fresh {@link NPC} instances by difficulty tier
 * (Factory pattern).
 *
 * Returning a brand-new NPC each call fixes the original shared-instance bug:
 * previously {@code getRandomNPC} handed back the same NPC object stored in a
 * static array, so the HP lost in one fight carried over to the next encounter
 * with that enemy. Each encounter now starts from the full template stats.
 *
 * @author balla
 */
public final class NPCFactory {

    /** Immutable stat template for an enemy type. */
    private record Template(String name, int hp, int dmg) {
    }

    // 0 = easy, 1 = medium, 2 = hard
    private static final Template[][] TIERS = {
        { new Template("Slime", 25, 15), new Template("Goblin", 15, 25) },
        { new Template("Cyclopse", 50, 20), new Template("Ghoul", 25, 35) },
        { new Template("King Slime", 1000, 20), new Template("Igris", 520, 150) }
    };

    private static final Random RANDOM = new Random();

    private NPCFactory() {
    }

    /**
     * @param difficulty tier index (0 easy, 1 medium, 2 hard)
     * @return a new NPC with full template stats
     */
    public static NPC createRandomNPC(int difficulty) {
        Template[] tier = TIERS[difficulty];
        Template template = tier[RANDOM.nextInt(tier.length)];
        return new NPC(template.name(), template.hp(), template.dmg());
    }
}
