/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.Character;

/**
 *
 * @author Jamie
 */
public class HumanPlayer extends Player
{

    public HumanPlayer(Character character, cluedo.gameLogic.gameBoard.GameBoard gb) {
        super(character, gb);
    }
    
    public void writeNotes(String notes)
    {
        //
    }
    
}
