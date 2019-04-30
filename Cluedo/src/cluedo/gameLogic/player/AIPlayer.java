/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

/**
 *
 * @author Jamie
 */
public class AIPlayer extends Player
{

     
    private double confidence;

    public AIPlayer(double confidence, cluedo.gameLogic.Character character, cluedo.gameLogic.gameBoard.GameBoard gb) 
    {
        super(character, gb);
        this.confidence = confidence;
    }
    
    
    
}
