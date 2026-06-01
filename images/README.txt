==============================================================================
 GAME IMAGES FOLDER
==============================================================================
 Drop PNG files in THIS folder to give the game scene artwork. The GUI fills
 roughly the top ~70% of the window with the image for the current scene.

 HOW IT WORKS
 - Each scene/event has a fixed image name. The game looks for:
       images/<name>.png
 - If the matching PNG is missing, the game still runs and simply shows a
   labelled grey placeholder (e.g. "[ chest ]") where the image would be.
 - So adding art = just save a correctly-named .png in this folder. No code
   changes, no rebuild of the image list.

 EXPECTED FILE NAMES (save as <name>.png)
 -----------------------------------------------------------------------------
   intro.png          Shown at the start (the forest intro).
   chest.png          ChestEvent.
   battle.png         BattleEvent.
   cleanse.png        CleanseEvent (the cleansing lake).
   cursed_statue.png  CursedStatueEvent.
   gameover.png       Shown on the Game Over screen.
   menu.png           (optional) Background for the main menu.

 NOTES
 - Format: PNG (.png). File names are lower-case and must match exactly.
 - The image is scaled to COVER the image area (fills it, keeping aspect
   ratio, cropping any overflow), so any reasonably large image works well.
   Landscape images around 1280x720 or larger look best.
 - To add a brand-new event later: have its Event subclass return a new name
   from getImageName(), then drop a PNG with that name here.
==============================================================================
