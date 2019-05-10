/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import org.junit.Test;
import static org.junit.Assert.*;
import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.player.HumanPlayer;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;
import java.io.FileNotFoundException;
import java.util.LinkedList;

/**
 *
 * @author jt401
 */
public class SuggestionTest
{
    @Test
    public void suggestionTest() throws InvalidSetupFileException, FileNotFoundException
    {
        BoardConstructor bc = new BoardConstructor();
        GameBoard gb = bc.createBoard();
        
        Player p = new HumanPlayer(Character.MissScarlett, "player", gb, gb.getStartingSquares().get(Character.MissScarlett), new LinkedList<Player>());
        
        p.Move(gb.getRooms().get(1));
        Suggestion s = p.makeSuggestion(Character.ColMustard, (Room) p.getCurrentPosition(), Weapon.candlestick);
        assertEquals(true, s instanceof Suggestion);
    }
    
}
