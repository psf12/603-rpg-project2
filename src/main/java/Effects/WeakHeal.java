/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Effects;

import Game.*;

/**
 *
 * @author balla
 */
public class WeakHeal implements Effects{
    @Override
    public void OnEventStart(Player plr){
        plr.Heal(5);
    }
    
}
