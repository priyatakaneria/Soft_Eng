/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.Character;

/**
 *
 * @author Jamie
 */
public class AIPlayer extends Player
{

     
    private double confidence;

    public AIPlayer(double confidence, Character character, String aiName, GameBoard gb) 
    {
        super(character, aiName, gb);
        this.confidence = confidence;
    }
    
    
    
}
