/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.AIPlayer;
import cluedo.gameLogic.player.HumanPlayer;
import cluedo.gameLogic.player.Player;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import javafx.application.Application;
import userInterface.Game;

/**
 *
 * @author Jamie
 */
public class TurnManager
{

    private GameBoard gameBoard;
    private Queue<Player> turnQueue;
    private Queue<Player> realPlayers;
    private ArrayList<Player> allPlayers;
    
    private Player currPlayer;
    private Game GUI;

    /**
     * Sets up the turn manager with a list of players and their relative chosen
     * characters,along with the filename of the custom board. If no custom
     * board is required, use the constructor without the filename parameter to
     * use the default file.
     *
     * The filename parameter is likely to change depending on how the file
     * selection is implemented from the GUI side of things
     *
     * @param characterPlayerMap The mappings from the Character chosen to the
     * player's name (as a String)
     * @param noAiPlayers The number of players to be controlled by the AI.
     * @param customBoardFilename The filename to load as a custom board file
     */
    public TurnManager(HashMap<Character, String> characterPlayerMap, int noAiPlayers, String customBoardFilename, Game GUI) throws InvalidSetupFileException
    {
        this.GUI = GUI;
        // create BoardConstructor and subsequently GameBoard
        BoardConstructor bc;
        try
        {
            bc = new BoardConstructor(customBoardFilename);
            gameBoard = bc.createBoard();
        } //
        catch (FileNotFoundException e)
        {
            // display some gui error?
        }

        init(characterPlayerMap, noAiPlayers);
    }

    /**
     * returns the gameBoard assigned to this TurnManager.
     *
     * @return a GameBoard
     */
    public GameBoard getGameBoard()
    {
        return gameBoard;
    }

    public Player getCurrPlayer()
    {
        return currPlayer;
    }
    
    /**
     * @return the Queue of players in the game, AI and Human 
     */
    public Queue<Player> getRealPlayers()
    {
        return realPlayers;
    }

    /**
     * creates a new turn manager with the default GameBoard setup file
     * (../customisation/board layout/default.txt), called if default board
     * button pressed in the GUI
     *
     * @param characterPlayerMap The mappings from the player's name (as a
     * String) to their respective chosen Character.
     */
    public TurnManager(HashMap<Character, String> characterPlayerMap, int noAiPlayers, Game GUI) throws InvalidSetupFileException
    {
        this.GUI = GUI;
        // create BoardConstructor and subsequently GameBoard
        BoardConstructor bc;
        try
        {
            bc = new BoardConstructor("../Cluedo/customisation/board layout/default.txt");
            gameBoard = bc.createBoard();
        } //
        catch (FileNotFoundException e)
        {
            // display some gui error?
        }

        init(characterPlayerMap, noAiPlayers);
    }

    /**
     * performs most of the initialisation besides the board construction
     */
    private void init(HashMap<Character, String> characterPlayerMap, int noAiPlayers)
    {
        // Create human and AI players and add them to the player queue
        turnQueue = new LinkedList<>();
        realPlayers = new LinkedList<>();
        allPlayers = new ArrayList<>();

        int currAiPlayers = 0;
        Player newPlayer;
        System.out.println("Starting Squares:");
        System.out.println(gameBoard.getStartingSquares());
        for (Character c : Character.values())
        {
            if (characterPlayerMap.keySet().contains(c))
            {
                int[] startCoords = gameBoard.getSpaceCoords(gameBoard.getStartingSquares().get(c));
                System.out.println(c.getCharacterName() + " starting at " + startCoords[0]+","+startCoords[1]);
                newPlayer = new HumanPlayer(c, characterPlayerMap.get(c), gameBoard, gameBoard.getStartingSquares().get(c), realPlayers);
               
                //newPlayer.Move(gameBoard.getStartingSquares().get(newPlayer.getCharacter()));
                turnQueue.add(newPlayer);
                realPlayers.add(newPlayer);
            } //
            else if (currAiPlayers < noAiPlayers)
            {
                newPlayer = new AIPlayer(0.8, c, c.getCharacterName(), gameBoard, gameBoard.getStartingSquares().get(c), realPlayers);
                //newPlayer.Move(gameBoard.getStartingSquares().get(newPlayer.getCharacter()));
                turnQueue.add(newPlayer);
                realPlayers.add(newPlayer);
                currAiPlayers++;
            } //
            else
            {
                newPlayer = new AIPlayer(0, c, c.getCharacterName(), gameBoard, gameBoard.getStartingSquares().get(c), realPlayers);
                // newPlayer.Move());
            }
            allPlayers.add(newPlayer);
        }
        
        for (Player p : allPlayers)
        {
            if (p instanceof HumanPlayer)
            {
                p.getDetNotes().initTable();
            }
        }

        // deal cards
        LinkedList<Card> clueDeck = gameBoard.getClueDeck();
        while (clueDeck.size() > 1)
        {
            Player beingDealt = turnQueue.poll();
            beingDealt.addClueCard((ClueCard) clueDeck.poll());
            turnQueue.add(beingDealt);
            realPlayers.add(realPlayers.poll());
        }
    }

    public void gameLoop()
    {
        System.out.println("entering turnmanager gameLoop()");
        //new Thread(new TurnManagerThread(gameBoard, turnQueue, realPlayers, currPlayer, GUI)).start();
        TurnManagerThread<Integer> task = new TurnManagerThread<Integer>(gameBoard, turnQueue, realPlayers, currPlayer, GUI);
        System.out.println("starting thread");
        Thread th = new Thread(task);
        th.start();
        System.out.println("thread started");
    }

    public Iterable<Player> getAllPlayers()
    {
        return allPlayers;
    }
}
