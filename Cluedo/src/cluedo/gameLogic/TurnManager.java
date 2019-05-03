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
    private Queue<Player> playerQueue;
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
            bc = new BoardConstructor("default.txt");
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
        playerQueue = new LinkedList();
        allPlayers = new ArrayList<>();

        int currAiPlayers = 0;
        Player newPlayer;
        for (Character c : Character.values())
        {

            if (characterPlayerMap.keySet().contains(c))
            {
                newPlayer = new HumanPlayer(c, characterPlayerMap.get(c), gameBoard, gameBoard.getStartingSquares().get(c));
                //newPlayer.Move(gameBoard.getStartingSquares().get(newPlayer.getCharacter()));
                playerQueue.add(newPlayer);
            } //
            else if (currAiPlayers < noAiPlayers)
            {
                newPlayer = new AIPlayer(0.8, c, c.getCharacterName(), gameBoard, gameBoard.getStartingSquares().get(c));
                //newPlayer.Move(gameBoard.getStartingSquares().get(newPlayer.getCharacter()));
                playerQueue.add(newPlayer);
                currAiPlayers++;
            } else
            {
                newPlayer = new AIPlayer(0, c, c.getCharacterName(), gameBoard, gameBoard.getStartingSquares().get(c));
                // newPlayer.Move());
            }
            allPlayers.add(newPlayer);
        }

        // deal cards
        LinkedList<Card> clueDeck = gameBoard.getClueDeck();
        while (clueDeck.size() > 1)
        {
            Player beingDealt = playerQueue.remove();
            beingDealt.addClueCard((ClueCard) clueDeck.remove());
            playerQueue.add(beingDealt);
        }
    }

    /**
     * runs the whole process of rolling dice, moving players, making a
     * suggestion and an accusation, picking up intrigueCards, showing clues,
     * marking detective notes etc.
     *
     * @param player the player whose turn it is
     * @return a boolean for whether the game has been one (i.e. a correct
     * accusation has been made)
     */
    public boolean takeTurn(Player player)
    {
        boolean gameOver = false;

        boolean endTurn = false;
        boolean extraTurn = false;
        boolean pickedUpExtraTurn = false;
        boolean makeAccusation = false;
        IntrigueCard newIntrigueCard;

        if (player.hasPlayerLost())
        {
            boolean ai = player instanceof AIPlayer;
            AIPlayer aiPlayer = null;
            /**
             * DisplayTurn displays a message of which player's turn it is. It
             * takes the current player as an argument. Waits a predetermined
             * number of seconds before finishing.
             */
            GUI.displayTurn(player);

            // roll dice
            int rollValue;
            if (ai)
            {
                aiPlayer = (AIPlayer) player;
                rollValue = gameBoard.rollDice();
            } //
            else
            {
                /**
                 * GUI.rollDice() should wait for the user to click on the dice,
                 * or some 'roll dice' button before returning the rolled value.
                 */
                rollValue = GUI.rollDice(); // needs implementing
            }

            // find available spaces and move to chosen one
            HashSet<BoardSpace> availableMoves = gameBoard.availableMoves(player.getCurrentPosition(), rollValue);

            BoardSpace newSpace;
            if (ai)
            {
                /**
                 * AIPlayer.chooseSpace takes a set of possible spaces and
                 * returns one. May as well be random, but prioritise Room
                 * instances.
                 */
                newSpace = aiPlayer.chooseSpace(availableMoves);
            } //
            else
            {
                /**
                 * GUI.chooseSpace should take a hashSet of boardSpaces and
                 * visible show them as available on the board (including
                 * rooms). The method then should wait for the user to select
                 * one of the lit up spaces and return the chosen space.
                 */
                newSpace = GUI.chooseSpace(availableMoves); // needs implementing
            }
            player.Move(newSpace);

            // intrigue cards
            BoardSpace playerPosition = player.getCurrentPosition();
            if (playerPosition instanceof BoardSquare)
            {
                if (((BoardSquare) playerPosition).isIntrigue())
                {
                    newIntrigueCard = gameBoard.drawIntrigue();
                    player.addIntrigueCard(newIntrigueCard);
                    if (newIntrigueCard.getIntrigueType() == IntrigueType.throwAgain)
                    {
                        if (ai)
                        {
                            rollValue = gameBoard.rollDice();
                            availableMoves = gameBoard.availableMoves(player.getCurrentPosition(), rollValue);
                            newSpace = aiPlayer.chooseSpace(availableMoves);
                        } //
                        else
                        {
                            /**
                             * GUI.rollDice() should wait for the user to click
                             * on the dice, or some 'roll dice' button before
                             * returning the rolled value.
                             */
                            rollValue = GUI.rollDice(); // needs implementing
                            availableMoves = gameBoard.availableMoves(player.getCurrentPosition(), rollValue);
                            newSpace = GUI.chooseSpace(availableMoves); // needs implementing
                        }
                        player.Move(newSpace);
                    } //
                    else if (newIntrigueCard.getIntrigueType() == IntrigueType.teleport)
                    {
                        if (ai)
                        {
                            /**
                             * AIPlayer.chooseTeleport should pick a suitable
                             * BoardSpace from the GameBoard, I don't see any
                             * reason for it to not be a Room?
                             *
                             * returns the chosen BoardSpace.
                             */
                            aiPlayer.chooseTeleport();
                        } //
                        else
                        {
                            /**
                             * GUI.teleport should do the same as choose space,
                             * but highlight every possible location
                             */
                            newSpace = GUI.teleport(); // needs implementing
                        }
                        player.Move(newSpace);
                    }//
                    else if (newIntrigueCard.getIntrigueType() == IntrigueType.extraTurn)
                    {
                        pickedUpExtraTurn = true;
                    }
                }

                if (ai)
                {
                    endTurn = true;
                } else
                {
                    /**
                     * GUI.endTurn should prompt the user to end their turn and
                     * wait for them to press the end-turn button.
                     *
                     * returns true.
                     */
                    endTurn = GUI.endTurn();
                }
            } //
            else if (playerPosition instanceof Room)
            {
                Suggestion newSuggestion = null;
                if (ai)
                {
                    /**
                     * AIPlayer.decideSuggestion should make an appropriate
                     * guess at a suggestion and return it.
                     */
                    newSuggestion = aiPlayer.decideSuggestion();
                } //
                else
                {
                    /**
                     * GUI.makeSuggestion should take as input the player who is
                     * making the suggestion i.e. the asker. The method should
                     * query the user and wait until they enter their suggestion
                     * details. The return value should be a new suggestion
                     * object representing the user's choice.
                     */
                    newSuggestion = GUI.makeSuggestion(player);
                }
                /**
                 * move players into the room they are called into for a suggestion
                 */
                for (Player p : allPlayers)
                {
                    if (p.getCharacter() == newSuggestion.getCharacter())
                    {
                        p.Move(newSuggestion.getRoom());
                    }
                }
                /**
                 * find the set of possible clues from a player's hand
                 */
                ArrayList<ClueCard> possibleClues = new ArrayList<>();
                Player nextEnquiry = null;
                while (playerQueue.peek() != player && possibleClues.size() == 0)
                {
                    nextEnquiry = playerQueue.poll();
                    possibleClues = nextEnquiry.respondToSuggestion(newSuggestion);
                    playerQueue.add(nextEnquiry);
                }

                /**
                 * ensures the playerQueue is left in the same state that it
                 * started in i.e. the current player is last in the queue.
                 */
                while (playerQueue.peek() != player)
                {
                    playerQueue.add(playerQueue.poll());
                }
                playerQueue.add(playerQueue.poll());

                if (possibleClues.size() == 0)
                {
                    if (ai)
                    {
                        /**
                         * AIPlayer.noPlayerClues should make the appropriate
                         * detective notes according to the suggestion made
                         */
                        aiPlayer.noPlayerClues(newSuggestion);
                    } //
                    else
                    {
                        /**
                         * GUI.noPlayerClues should imform the user that their
                         * suggestion has provided no clues, and wait for them
                         * to maybe fill in some detective notes and click an
                         * accept button or something.
                         *
                         * Doesn't need to return anything.
                         */
                        GUI.noPlayerClues();
                    }
                } //
                else
                {
                    ClueCard response;
                    if (nextEnquiry instanceof AIPlayer)
                    {
                        /**
                         * AIPlayer.chooseResponse should make a somewhat
                         * intelligent decision on which of the possible clues
                         * to show.
                         *
                         * returns a ClueCard
                         */
                        response = ((AIPlayer) nextEnquiry).chooseResponse(possibleClues);
                    } //
                    else
                    {
                        /**
                         * GUI.ChooseResponse should take a player (nextEnquiry)
                         * and prompt that player to select from one of the
                         * possible cards to show to the asker.
                         */
                        response = GUI.chooseResponse(nextEnquiry, possibleClues);
                    }

                    if (ai)
                    {
                        /**
                         * AIPlayer.receivedClue should make the relevant
                         * detective notes for having received the clue
                         * 'response'.
                         *
                         * returns void.
                         */
                        aiPlayer.receivedClue(response);
                    } //
                    else
                    {
                        /**
                         * GUI.showClue takes a ClueCard and displays it to the
                         * player who made the suggestion, then waits for the
                         * user to make some notes and press a continue button
                         */
                        GUI.showClue(response, player);
                    }
                }

                if (ai)
                {
                    /**
                     * AIPlayer.accusationQuery should make a decision on
                     * whether to make a final accusation or not.
                     *
                     * maybe use the confidence field as some sort of
                     * probability decision boundary if we have time, otherwise
                     * just make some reasonable decisions?
                     *
                     * returns a boolean indicating their choice
                     */
                    makeAccusation = aiPlayer.accusationQuery();
                } //
                else
                {
                    /**
                     * GUI.accusationQuery should display a message asking if
                     * the user wants to make an accusation and waits for their
                     * input to click a 'make accusation' button or an 'end
                     * turn' button.
                     *
                     * Returns true if they press make accusation, or false if
                     * the press end turn.
                     */
                    makeAccusation = GUI.accusationQuery();
                }

                if (makeAccusation)
                {
                    Accusation newAccusation;
                    if (ai)
                    {
                        /**
                         * AIPlayer.decideAccusation should decide on, create
                         * and return a suitable accusation.
                         */
                        newAccusation = aiPlayer.decideAccusation();
                    } else
                    {
                        /**
                         * GUI.MakeAccusation should take a parameter of the
                         * player making the accusation and prompt them to
                         * select the clues they think are the final solution.
                         * Waits until they press some 'submit' button then
                         * returns an Accusation object.
                         */
                        newAccusation = GUI.makeAccusation(player);
                    }
                    boolean accusationResult = gameBoard.getEnvelope().checkEnvelope(newAccusation);
                    if (accusationResult)
                    {
                        gameOver = true;
                    } //
                    else
                    {
                        player.setPlayerLost(true);
                    }
                }
            }

            if (!gameOver)
            {

                if (player.hasIntrigueType(IntrigueType.extraTurn) && !pickedUpExtraTurn)
                {
                    gameBoard.returnIntrigue(player.removeIntrigueCard(IntrigueType.extraTurn));
                    /**
                     * GUI.displayExtraTurn should display the fact the a player
                     * specified in the parameters is taking an extra turn
                     * because of their intrigue card from last turn.
                     */
                    GUI.displayExtraTurn(player);
                    takeTurn(player);
                }
            }
        }
        return gameOver;
    }

    public void makeSuggestion(Player currPlayer)
    {

    }

    public void gameLoop()
    {
        boolean gameOver = false;
        while (!gameOver)
        {
            currPlayer = playerQueue.remove();
            if (playerQueue.size() == 0) // if there are no other player remaining in the game
            {
                gameOver = true;
            } //
            else
            {
                playerQueue.add(currPlayer);
                gameOver = takeTurn(currPlayer);
                if (!currPlayer.hasPlayerLost())
                {
                    playerQueue.add(currPlayer);
                }
            }
        }
        /**
         * GUI.winningsPage is the end screen, takes the current (winning)
         * player as an argument.
         */
        GUI.winningsPage(currPlayer);
    }
}
