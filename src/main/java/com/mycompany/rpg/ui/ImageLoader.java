package com.mycompany.rpg.ui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Loads scene images from the {@code images/} folder in the project directory.
 *
 * Look-ups are by base name: {@code load("chest")} reads {@code images/chest.png}.
 * Successfully loaded images are cached. A missing file returns {@code null}
 * (the caller shows a placeholder), and is not cached so the artwork can simply
 * be dropped in later without restarting.
 *
 * @author balla
 */
public final class ImageLoader {

    private static final File IMAGE_DIR = new File("images");
    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private ImageLoader() {
    }

    /**
     * @param name base file name without extension
     * @return the loaded image, or {@code null} if no matching PNG exists
     */
    public static BufferedImage load(String name) {
        if (name == null) {
            return null;
        }
        BufferedImage cached = CACHE.get(name);
        if (cached != null) {
            return cached;
        }
        File file = new File(IMAGE_DIR, name + ".png");
        if (file.isFile()) {
            try {
                BufferedImage img = ImageIO.read(file);
                if (img != null) {
                    CACHE.put(name, img); // cache hits only
                }
                return img;
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }
}
