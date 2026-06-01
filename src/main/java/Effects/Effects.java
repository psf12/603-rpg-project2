/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Effects;

/**
 *
 * @author balla
 */
import Game.*;
public interface Effects {
    default void OnBattleEnd(Player plr){}
    default void OnEventEnd(Player plr){}
    default void OnBattleStart(Player plr){}
    default void OnEventStart(Player plr){}
}
