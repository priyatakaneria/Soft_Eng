/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.player.AIPlayer;
import cluedo.gameLogic.player.HumanPlayer;
import cluedo.gameLogic.player.Player;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Jamie
 */
public class TurnManager
{

    private GameBoard gameBoard;
    private Queue<Player> playerQueue;
    private Player currPlayer;
    
    /**
     * Sets up the turn manager with a list of players and their relative chosen
     * characters,along with the filename of the custom board. If no custom
     * board is required, use the constructor without the filename parameter to
     * use the default file.
     *
     * The filename parameter is likely to change depending on how the file
     * selection is implemented from the GUI side of things
     *
     * @param characterPlayerMap The mappings from the Character chosen to the player's name (as a
     * String)
     * @param noAiPlayers The number of players to be controlled by the AI.
     * @param customBoardFilename The filename to load as a custom board file
     */
    public TurnManager(HashMap<Character, String> characterPlayerMap, int noAiPlayers, String customBoardFilename)
    {
        // create BoardConstructor and subsequently GameBoard
        BoardConstructor bc;
        try
        {
            bc = new BoardConstructor(customBoardFilename);
            gameBoard = bc.createBoard();
        }
        catch(FileNotFoundException e)
        {
            // display some gui error?
        }
        catch(InvalidSetupFileException e)
        {
            // display some gui error?
        }
        
        // Create human and AI players and add them to the player queue
        playerQueue = new LinkedList();
        
        int currAiPlayers = 0;
        for (Character c : Character.values())
        {
            if (characterPlayerMap.keySet().contains(c))
            {
                playerQueue.add(new HumanPlayer(c, characterPlayerMap.get(c), gameBoard));
            }
            else if (currAiPlayers < noAiPlayers)
            {
                playerQueue.add(new AIPlayer(0.8, c, c.getCharacterName(), gameBoard));
                currAiPlayers++;
            }
        }
    }

    /**
     * creates a new turn manager with the default GameBoard setup file
     * (../customisation/board layout/default.txt), called if default board
     * button pressed in the GUI
     *
     * @param characterPlayerMap The mappings from the player's name (as a
     * String) to their respective chosen Character.
     */
    public TurnManager(HashMap<Character, String> characterPlayerMap, int noAiPlayers)
    {
        // create BoardConstructor and subsequently GameBoard
        BoardConstructor bc;
        try
        {
            bc = new BoardConstructor("default.txt");
            gameBoard = bc.createBoard();
        }
        catch(FileNotFoundException e)
        {
            // display some gui error?
        }
        catch(InvalidSetupFileException e)
        {
            // display some gui error?
        }
        
        // Create human and AI players and add them to the player queue
        playerQueue = new LinkedList();
        
        int currAiPlayers = 0;
        for (Character c : Character.values())
        {
            if (characterPlayerMap.keySet().contains(c))
            {
                playerQueue.add(new HumanPlayer(c, characterPlayerMap.get(c), gameBoard));
            }
            else if (currAiPlayers < noAiPlayers)
            {
                playerQueue.add(new AIPlayer(0.8, c, c.getCharacterName(), gameBoard));
                currAiPlayers++;
            }
        }
    }
    
    
}
