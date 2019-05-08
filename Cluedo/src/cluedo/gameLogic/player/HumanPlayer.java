/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.GameBoard;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Jamie
 */
public class HumanPlayer extends Player
{

    public HumanPlayer(Character character, String playerName, GameBoard gb, BoardSpace start, Collection<Player> otherPlayers) {
        super(character, playerName, gb, start, otherPlayers);
    }
    
    public void writeNotes(String notes)
    {
        getDetNotes().writeNotes(notes);
    }
    
}
