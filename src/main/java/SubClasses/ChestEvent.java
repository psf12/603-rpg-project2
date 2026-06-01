/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SubClasses;

import java.util.Random;
import Game.*;
/**
 *
 * @author balla
 */
public class ChestEvent extends Event{
    @Override
    protected String getEventText(){
        String text = "A small glitter dances in the corner of your vision\nUpon slowly treading towards it, you notice its silhoutte.\nits a chest! What secrets could it hold?\nBut randomly here in this lustrous forest...?\nWill you trust your adventurer gut?";
        return text;
    }
    protected void onYes(Player plr){
        Random random = new Random(); 
        int coin_flip = random.nextInt(3);
        if (coin_flip == 0){
          System.out.println("As slow as a sloth, as patient as a rock.\nYour eyes trace the tiny entrance of the tightly closed chest, the gleam glowing brighter.\nUntil you notice that no gold is to be found, but only jagged teeth.\nIt was a mimic...");
          plr.takeDMG(15);
        }else{
          Item item = plr.itemManager.getRandomItem();
          
          System.out.println("You noted that you should trust your gut more.\nYou found an item!");
          item.displayItem();
          boolean choice = InputHandler.getYesNoInput();
          if (choice){
              plr.itemManager.addItem(item);
            }
        }
    }
    protected void onNo(Player plr){
        System.out.println("Your boring decision damages whatever pride you have left...");
    }
}
