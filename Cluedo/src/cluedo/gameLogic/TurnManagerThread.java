/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.AIPlayer;
import cluedo.gameLogic.player.Player;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javafx.application.Platform;
import javafx.concurrent.Task;
import userInterface.Game;

/**
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
    private boolean waitingForDice;

    public TurnManagerThread(GameBoard gameBoard, Queue<Player> turnQueue, Queue<Player> realPlayers, Player currPlayer, Game GUI)
    {
        this.gameBoard = gameBoard;
        this.turnQueue = turnQueue;
        this.realPlayers = realPlayers;
        this.currPlayer = currPlayer;
        this.GUI = GUI;
    }

    @Override
    public Integer call()
    {
        gameLoop();
        return 1;
    }

    public void gameLoop()
    {
        boolean gameOver = false;
        while (!gameOver)
        {
            System.out.println("TurnManager Thread: " + Thread.currentThread().getName());
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
        /**
         * GUI.winningsPage is the end screen, takes the current (winning)
         * player as an argument.
         */
        //GUI.winningsPage(currPlayer);
    }

    private class GetDiceRoll implements Runnable
    {
        private int rollValue;

        public GetDiceRoll()
        {

        }

        @Override
        public void run()
        {
            GUI.setWaitingForDice(true);
            rollValue = GUI.rollDice();
        }

        public int getValue()
        {
            return rollValue;
        }
    }

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
            GUI.setWaitingForMove(true);
            GUI.chooseSpace(avail);
        }

        public HashSet<BoardSpace> getValue()
        {
            return avail;
        }
    }

    private class GetChosenMove implements Runnable
    {
        private BoardSpace bs;

        public GetChosenMove()
        {
        }

        @Override
        public void run()
        {
            bs = GUI.getChosenSpace();
        }

        public BoardSpace getValue()
        {
            return bs;
        }
    }

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
     * marking detective notes etc.
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

            // roll dice
            int rollValue = 0;
            if (ai)
            {
                aiPlayer = (AIPlayer) player;
                rollValue = gameBoard.rollDice();
            } //
            else
            {
                while (rollValue == 0)
                {
                    GetDiceRoll rv = new GetDiceRoll();
                    Platform.runLater(rv);
                    waitForRunLater();
                    rollValue = rv.getValue();
                }

                System.out.println("received dice");

                System.out.println(rollValue);
            }

            // find available spaces and move to chosen one
            GetAvailableMoves gam = new GetAvailableMoves(player, rollValue);
            Platform.runLater(gam);
            waitForRunLater();
            availableMoves = gam.getValue();
            System.out.println("available moves received");
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
            player.Move(newSpace);

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
                } else
                {
                    endTurn = GUI.endTurn();
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
                    newSuggestion = GUI.makeSuggestion(player);
                }
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
