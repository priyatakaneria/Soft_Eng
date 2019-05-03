/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.Accusation;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.gameBoard.BoardSpace;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Jamie
 */
public class AIPlayer extends Player
{

    private double confidence;

    public AIPlayer(double confidence, Character character, String aiName, GameBoard gb, BoardSpace start)
    {
        super(character, aiName, gb, start);
        this.confidence = confidence;
    }

    /**
     * AIPlayer.chooseSpace takes a set of possible spaces and returns one. May
     * as well be random, but prioritise Room instances.
     *
     * @param availableMoves the set of available moves
     * @return the chosen BoardSpace
     */
    public BoardSpace chooseSpace(HashSet<BoardSpace> availableMoves)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Should pick a suitable BoardSpace from the GameBoard, It makes sense that
     * the AI would always enter a room.
     *
     * @return the chosen BoardSpace
     */
    public BoardSpace chooseTeleport()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * should make an appropriate guess at a suggestion and return it.
     *
     * @return a new Suggestion object
     */
    public Suggestion decideSuggestion()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * should make the appropriate detective notes for not receiving a new clue
     * after a suggestion according to the suggestion made.
     */
    public void noPlayerClues(Suggestion newSuggestion)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * should make a somewhat intelligent decision on which of the possible
     * clues to show, perhaps random if pushed for time.
     *
     * @return the ClueCard corresponding to the clue given
     */
    public ClueCard chooseResponse(ArrayList<ClueCard> possibleClues)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * should make the relevant detective notes for having received the clue
     * 'response'.
     *
     * @param response the ClueCad being shown
     */
    public void receivedClue(ClueCard response, Player enquired)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * AIPlayer.accusationQuery should make a decision on whether to make a
     * final accusation or not.
     *
     * maybe use the confidence field as some sort of probability decision
     * boundary if we have time, otherwise just make some reasonable decisions?
     *
     * @return a boolean indicating their choice
     */
    public boolean accusationQuery()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * AIPlayer.decideAccusation should decide on, create and return a suitable
     * accusation.
     *
     * @return an Accusation reflecting the player's choice
     */
    public Accusation decideAccusation()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
