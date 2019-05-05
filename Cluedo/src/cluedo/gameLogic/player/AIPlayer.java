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
import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.Room;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Jamie
 */
public class AIPlayer extends Player
{

    private double confidence;
    private Random randDecision;
    private HashMap<ClueType, Integer> clueFrequency;

    public AIPlayer(double confidence, Character character, String aiName, GameBoard gb, BoardSpace start)
    {
        super(character, aiName, gb, start);
        this.confidence = confidence;
        randDecision = new Random();
        clueFrequency = createClueFreqTable();

    }

    /**
     * creates a table for recording how often a clue has been given. used to
     * 'intelligently' decide which clue to show instead of making a completely
     * random choice.
     *
     * @return The completed empty table
     */
    private HashMap<ClueType, Integer> createClueFreqTable()
    {
        HashMap<ClueType, Integer> clueFreq = new HashMap<>();
        for (Character c : Character.values())
        {
            clueFreq.put(c, 0);
        }
        for (Weapon w : Weapon.values())
        {
            clueFreq.put(w, 0);
        }
        for (Room r : gb.getRooms().values())
        {
            clueFreq.put(r, 0);
        }
        return clueFreq;
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
        BoardSpace newSpace = null;
        HashSet<Room> availableRooms = new HashSet<>();
        for (BoardSpace bs : availableMoves)
        {
            if (bs instanceof Room)
            {
                availableRooms.add((Room) bs);
            }
        }

        if (availableRooms.size() > 0)
        {
            int randRoom = randDecision.nextInt(availableRooms.size());
            int i = 0;
            for (Room r : availableRooms)
            {
                if (i == randRoom)
                {
                    newSpace = r;
                }
                i++;
            }

        } //
        else
        {
            int randSpace = randDecision.nextInt(availableMoves.size());
            int i = 0;
            for (BoardSpace r : availableMoves)
            {
                if (i == randSpace)
                {
                    newSpace = r;
                }
                i++;
            }
        }

        return newSpace;
    }

    /**
     * Should pick a suitable BoardSpace from the GameBoard, It makes sense that
     * the AI would always enter a room.
     *
     * @return the chosen BoardSpace
     */
    public BoardSpace chooseTeleport()
    {
        HashMap<Integer, Room> availableMoves = super.gb.getRooms();
        int randRoom = randDecision.nextInt(availableMoves.size());
        return availableMoves.get(randRoom + 1);
    }

    

    /**
     * should make the appropriate detective notes for not receiving a new clue
     * after a suggestion according to the suggestion made.
     */
    public void noPlayerClues(Player enquired, Suggestion newSuggestion)
    {
        markDetectiveTable(enquired, lastSuggestion.getCharacter(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getRoom(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getWeapon(), DetNoteType.mightHaveClue);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * should make the relevant detective notes for having received the clue
     * 'response'.
     *
     * @param response the ClueCard being shown
     * @param enquired the player showing the clue
     * @param playerQueue the queue of players
     */
    public void receivedClue(ClueCard response, Player enquired)
    {
        markDetectiveTable(enquired, lastSuggestion.getCharacter(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getRoom(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getWeapon(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, response.getClueType(), DetNoteType.hasClue);
    }

    /**
     * should make a somewhat intelligent decision on which of the possible
     * clues to show, perhaps random if pushed for time.
     *
     * @return the ClueCard corresponding to the clue to show
     */
    public ClueCard chooseResponse(ArrayList<ClueCard> possibleClues)
    {
        int max = -1;
        ClueCard bestResponse = null;
        for (ClueCard cc : possibleClues)
        {
            int nextFreq = clueFrequency.get(cc.getClueType());
            if (nextFreq > max)
            {
                max = nextFreq;
                bestResponse = cc;
            }
        }
        return bestResponse;
    }

    /**
     * should make an appropriate guess at a suggestion and return it.
     *
     * @return a new Suggestion object
     */
    public Suggestion decideSuggestion()
    {
        //Select a random combination from clueTable. 
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
