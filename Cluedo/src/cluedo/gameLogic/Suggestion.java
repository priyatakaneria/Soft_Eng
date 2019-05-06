/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 *
 * @author Jamie
 */
public class Suggestion extends AccuSuggest
{

    public Suggestion(Character character, Room room, Weapon weapon, Player asker) {
        super(character, room, weapon, asker);
    }

        
}
