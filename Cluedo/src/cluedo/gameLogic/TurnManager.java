package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.player.AIPlayer;
import cluedo.gameLogic.player.HumanPlayer;
import cluedo.gameLogic.player.Player;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Queue;
import cluedo.userInterface.Game;

/**
 * Manages the turn process of the game, along with win conditions
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
     * @param GUI the instance of javaFXApplication that is the main GUi application window
     * @throws InvalidSetupFileException if the supplied setup file is not valid
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
            // this never occurs since the filname is provided by a file chooser
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

    /**
     * @return the player who's turn it is.
     */
    public Player getCurrPlayer()
    {
        return currPlayer;
    }

    /**
     * @return the Queue of players in the game, AI and Human, not including
     * 'empty' players who do not take turns or respond to suggestions.
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
     * @param characterPlayerMap The mappings from the Character chosen to the
     * player's name (as a String)
     * @param noAiPlayers The number of players to be controlled by the AI.
     * @param GUI the instance of the javafx application that called this constructor
     * @throws InvalidSetupFileException if the setup file used is invalid
     */
    public TurnManager(HashMap<Character, String> characterPlayerMap, int noAiPlayers, Game GUI) throws InvalidSetupFileException
    {
        this.GUI = GUI;
        // create BoardConstructor and subsequently GameBoard
        BoardConstructor bc;
        try
        {
            bc = new BoardConstructor();
            gameBoard = bc.createBoard();
        } //
        catch (FileNotFoundException e)
        {
            // display some gui error?
        }

        init(characterPlayerMap, noAiPlayers);
    }

    /**
     * performs most of the initialisation besides the board construction,
     * including creating the players and dealing cards.
     * 
     * @param characterPlayerMap The mappings from the Character chosen to the
     * player's name (as a String)
     * @param noAiPlayers The number of players to be controlled by the AI.
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
            System.out.println("Allocating character: " + c.getCharacterName());
            if (characterPlayerMap.keySet().contains(c))
            {
                int[] startCoords = gameBoard.getSpaceCoords(gameBoard.getStartingSquares().get(c));
                System.out.println(c.getCharacterName() + " starting at " + startCoords[0] + "," + startCoords[1]);
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

        Player firstPlayer = realPlayers.peek();
        
        System.out.println("player order");
        for (Player p : allPlayers)
        {
            System.out.println("\t" + p.getCharacter().getCharacterName());
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
        }
        
        while (turnQueue.peek() != firstPlayer)
        {
            turnQueue.add(turnQueue.poll());
        }
    }

    /**
     * Creates the separate thread and starts it
     */
    public void gameLoop()
    {
        TurnManagerThread<Integer> task = new TurnManagerThread<Integer>(gameBoard, turnQueue, realPlayers, currPlayer, GUI);
        Thread th = new Thread(task);
        th.start();
    }

    /**
     * @return the set of all players in the game
     */
    public Iterable<Player> getAllPlayers()
    {
        return allPlayers;
    }
}
