/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.rpg.model.effects;
import com.mycompany.rpg.model.*;


/**
 *
 * @author balla
 */
public class AzureFire extends Fire implements Effects{
    @Override
    public void OnEventStart(Player plr){
        plr.takeDMG(15);
    }
    
}
