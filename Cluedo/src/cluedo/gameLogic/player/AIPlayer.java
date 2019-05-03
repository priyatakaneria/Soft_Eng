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

    public BoardSpace chooseSpace(HashSet<BoardSpace> availableMoves)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void chooseTeleport()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Suggestion decideSuggestion()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void noPlayerClues(Suggestion newSuggestion)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ClueCard chooseResponse(ArrayList<ClueCard> possibleClues)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void receivedClue(ClueCard response)
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
