package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.AIPlayer;
import cluedo.gameLogic.player.HumanPlayer;
import cluedo.gameLogic.player.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.concurrent.Task;
import cluedo.userInterface.Game;

/**
 * This object does the actual management of who's turn it is and what point in
 * the turn process they are in.
 *
 * This is implemented rather poorly and is unfinished, it is very tightly bound
 * to the Game class in the user interface, which is bad practice.
 *
 * Uses some hacky methods which I'm not proud of to retrieve data from the GUI,
 * there is definitely a better way of doing this, but it's too close to the
 * deadline for me to refactor the whole thing.
 *
 * @author Jamie Thelin
 */
public class TurnManagerThread<T> extends Task
{
    private GameBoard gameBoard;
    private Queue<Player> turnQueue;
    private Queue<Player> realPlayers;
    private ArrayList<Player> allPlayers;

    private Player currPlayer;
    private final Game GUI;
    HashSet<BoardSpace> availableMoves;

    /**
     * Instantiates the Thread with all the variables defined in TurnManager
     *
     * @param gameBoard the GameBoard used in this instance of the game
     * @param turnQueue the queue of players
     * @param realPlayers the set of all players who take turns and respond to
     * suggestions
     * @param currPlayer the player who's turn it is currently
     * @param GUI the javafx application
     */
    public TurnManagerThread(GameBoard gameBoard, Queue<Player> turnQueue, Queue<Player> realPlayers, Player currPlayer, Game GUI)
    {
        this.gameBoard = gameBoard;
        this.turnQueue = turnQueue;
        this.realPlayers = realPlayers;
        this.currPlayer = currPlayer;
        this.GUI = GUI;
    }

    /**
     * The initial call to this object.
     *
     * @return 1 because the superclass Task requires a return value.
     */
    @Override
    public Integer call()
    {
        gameLoop();
        return 1;
    }

    /**
     * controls the player turns and performs the following actions in the 
     * following order:
     * 
     *  1. roll dice
     *  2. move
     * 3a. pickup intrigue card (if on intrigue space)
     * 3b. make suggestion (if in room)
     *  4. possibly make accusation
     *  5. end turn
     * 
     * The loop escapes once there is only one player left in the game or a
     * player makes a correct accusation.
     */
    public void gameLoop()
    {
        boolean gameOver = false;
        while (!gameOver)
        {
            currPlayer = turnQueue.poll();
            System.out.println(currPlayer.getCharacter().getCharacterName());
            if (turnQueue.size() == 0) // if there are no other player remaining in the game
            {
                gameOver = true;
            } //
            else
            {
                if (!currPlayer.hasPlayerLost())
                {
                    gameOver = takeTurn(currPlayer);
                    turnQueue.add(currPlayer);
                }
                realPlayers.add(realPlayers.poll());
            }
        }
        GUI.winningsPage(currPlayer);
    }

    /**
     * waits for the user to click on the dice
     */
    private class GetDiceRoll implements Runnable
    {
        private int rollValue;

        public GetDiceRoll()
        {

        }

        @Override
        public void run()
        {

            rollValue = GUI.rollDice();
        }

        public int getValue()
        {
            return rollValue;
        }
    }

    /**
     * determines the set of available moves
     */
    private class GetAvailableMoves implements Runnable
    {
        private HashSet<BoardSpace> avail;
        private Player p;
        private int rollValue;

        public GetAvailableMoves(Player p, int rollValue)
        {
            this.p = p;;
            this.rollValue = rollValue;
        }

        @Override
        public void run()
        {
            
            avail = gameBoard.availableMoves(p.getCurrentPosition(), rollValue);
            System.out.println(avail);
            if (p instanceof HumanPlayer)
            {
                GUI.setWaitingForMove(true);
                GUI.chooseSpace(avail);
            }
        }

        public HashSet<BoardSpace> getValue()
        {
            return avail;
        }
    }

    /**
     * prompts the user to select a new board space to move to 
     */
    private class GetChosenMove implements Runnable
    {
        private BoardSpace bs;

        public GetChosenMove()
        {
        }

        @Override
        public void run()
        {
            bs = GUI.getMovementChoice();
        }

        public BoardSpace getValue()
        {
            return bs;
        }
    }

    /**
     * moves the player to the newly selected boardSpace.
     */
    private class MovePlayer implements Runnable
    {
        private BoardSpace newSpace;
        private Player player;

        public MovePlayer(BoardSpace newSpace, Player player)
        {
            this.newSpace = newSpace;
            this.player = player;
        }

        @Override
        public void run()
        {
            player.Move(newSpace);

        }
    }

    /**
     * prompts the user to click on the end turn button.
     */
    private class EndTurn implements Runnable
    {
        private boolean endTurn = false;

        @Override
        public void run()
        {
            GUI.setWaitingForEndTurn(true);
            endTurn = GUI.getEndTurn();
        }

        public boolean getEndTurn()
        {
            return endTurn;
        }
    }

    /**
     * prompts the user to make a suggestion.
     */
    private class MakeSuggestion implements Runnable
    {
        private Suggestion suggestion = null;

        @Override
        public void run()
        {
            suggestion = GUI.makeSuggestion(currPlayer);
            GUI.setNewSuggestion(null);
        }

        public Suggestion getSuggestion()
        {
            return suggestion;
        }
    }

    /**
     * ensures the latest Platform.runLater() call is completed before 
     * continuing.
     */
    public static void waitForRunLater()
    {
        try
        {
            Semaphore semaphore = new Semaphore(0);
            Platform.runLater(() -> semaphore.release());
            semaphore.acquire();
        } //
        catch (InterruptedException ie)
        {
        }
    }

    /**
     * runs the whole process of rolling dice, moving players, making a
     * suggestion and an accusation, picking up intrigueCards, showing clues,
     * marking detective notes for a single player
     *
     * @param player the player whose turn it is
     * @return a boolean for whether the game has been one (i.e. a correct
     * accusation has been made)
     */
    public boolean takeTurn(Player player)
    {
        System.out.println("entering takeTurn()");
        boolean gameOver = false;

        boolean endTurn = false;
        boolean extraTurn = false;
        boolean pickedUpExtraTurn = false;
        boolean makeAccusation = false;
        IntrigueCard newIntrigueCard;

        if (!player.hasPlayerLost())
        {
            System.out.println("starting turn in turn manager thread");
            boolean ai = (player instanceof AIPlayer);
            AIPlayer aiPlayer = null;

            // any call to the UI thread must be encapsulated by this garbage:
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    GUI.displayTurn(player);
                }
            });
            waitForRunLater();

            // roll dice
            int rollValue = 0;
            if (ai)
            {
                aiPlayer = (AIPlayer) player;
                rollValue = gameBoard.rollDice();
            } //
            else
            {
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        GUI.setWaitingForDice(true);
                    }
                });
                waitForRunLater();
                GetDiceRoll rv = new GetDiceRoll();
                while (rollValue == 0)
                {
                    Platform.runLater(rv);
                    waitForRunLater();
                    rollValue = rv.getValue();
                }
                Platform.runLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        GUI.setRollValue(0);
                    }
                });
                waitForRunLater();

                System.out.println("received dice");

                System.out.println(rollValue);
            }

            // find available spaces and move to chosen one
            GetAvailableMoves gam = new GetAvailableMoves(player, rollValue);
            Platform.runLater(gam);
            waitForRunLater();
            availableMoves = gam.getValue();

            BoardSpace newSpace = null;
            if (ai)
            {
                newSpace = aiPlayer.chooseSpace(availableMoves);
            } //
            else
            {
                GetChosenMove cm = new GetChosenMove();

                while (newSpace == null)
                {
                    Platform.runLater(cm);
                    waitForRunLater();
                    newSpace = cm.getValue();
                }
            }
            Platform.runLater(new MovePlayer(newSpace, player));
            waitForRunLater();

            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
                    GUI.setMovementChoice(null);
                }
            });
            waitForRunLater();

            System.out.println("player moved");

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
                            rollValue = GUI.rollDice(); // needs implementing
                            availableMoves = gameBoard.availableMoves(player.getCurrentPosition(), rollValue);
                            //newSpace = GUI.chooseSpace(availableMoves); // needs implementing
                        }
                        player.Move(newSpace);
                    } //
                    else if (newIntrigueCard.getIntrigueType() == IntrigueType.teleport)
                    {
                        if (ai)
                        {
                            newSpace = aiPlayer.chooseTeleport();
                        } //
                        else
                        {
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
                } //
                else
                {
                    while (!endTurn)
                    {
                        EndTurn et = new EndTurn();
                        Platform.runLater(et);
                        waitForRunLater();
                        endTurn = et.getEndTurn();
                    }
                }
            } //
            else if (playerPosition instanceof Room)
            {
                Suggestion newSuggestion = null;
                if (ai)
                {
                    newSuggestion = aiPlayer.decideSuggestion();
                } //
                else
                {
                    Platform.runLater(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            GUI.setWaitingForSuggestion(true);
                        }
                    });
                    waitForRunLater();
                    MakeSuggestion ns = new MakeSuggestion();
                    while (newSuggestion == null)
                    {
                        Platform.runLater(ns);
                        waitForRunLater();
                        newSuggestion = ns.getSuggestion();
                    }
                }
//////////////////////////////////////////////////////////////////////////////////////
                /**
                 * move players into the room they are called into for a
                 * suggestion
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
                ArrayList<Player> playersWithoutClues = new ArrayList<>();
                while (turnQueue.peek() != player && possibleClues.isEmpty())
                {
                    nextEnquiry = turnQueue.poll();
                    possibleClues = nextEnquiry.respondToSuggestion(newSuggestion);
                    turnQueue.add(nextEnquiry);
                    if (possibleClues.isEmpty())
                    {
                        playersWithoutClues.add(nextEnquiry);
                    }
                }

                /**
                 * ensures the playerQueue is left in the same state that it
                 * started in i.e. the current player is last in the queue.
                 */
                while (realPlayers.peek() != player)
                {
                    realPlayers.add(realPlayers.poll());
                }
                realPlayers.add(realPlayers.poll());

                if (possibleClues.isEmpty())
                {
                    if (ai)
                    {
                        aiPlayer.noPlayerClues(nextEnquiry, playersWithoutClues);
                    } //
                    else
                    {
                        GUI.noPlayerClues();
                    }
                } //
                else
                {
                    ClueCard response;
                    if (nextEnquiry instanceof AIPlayer)
                    {
                        response = ((AIPlayer) nextEnquiry).chooseResponse(possibleClues);
                    } //
                    else
                    {
                        response = GUI.chooseResponse(nextEnquiry, possibleClues);
                    }

                    if (ai)
                    {
                        aiPlayer.receivedClue(response, nextEnquiry, playersWithoutClues);
                    } //
                    else
                    {
                        GUI.showClue(response, player, nextEnquiry);
                    }
                }

                if (ai)
                {
                    makeAccusation = aiPlayer.accusationQuery();
                } //
                else
                {
                    makeAccusation = GUI.accusationQuery();
                }

                if (makeAccusation)
                {
                    Accusation newAccusation;
                    if (ai)
                    {
                        newAccusation = aiPlayer.decideAccusation();
                    } else
                    {
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

                    GUI.displayExtraTurn(player);
                    takeTurn(player);
                }
            }
        }
        return gameOver;
    }
}
