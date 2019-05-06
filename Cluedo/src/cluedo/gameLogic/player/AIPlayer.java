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
import java.util.Collection;
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

    public AIPlayer(double confidence, Character character, String aiName, GameBoard gb, BoardSpace start, Collection<Player> otherPlayers)
    {
        super(character, aiName, gb, start, otherPlayers);
        this.confidence = confidence;
        randDecision = new Random();
        clueFrequency = createClueFreqTable();
        for (ClueCard cc : getClueHand())
        {
            markDetectiveTable(this, cc.getClueType(), DetNoteType.hasClue);
        }
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
        for (Room r : getGameBoard().getRooms().values())
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
            newSpace = randChoice(availableMoves);
//            int randSpace = randDecision.nextInt(availableMoves.size());
//            int i = 0;
//            for (BoardSpace r : availableMoves)
//            {
//                if (i == randSpace)
//                {
//                    newSpace = r;
//                }
//                i++;
//            }
        }

        return newSpace;
    }

    public <T> T randChoice(Collection<T> c)
    {
        T randChoice = null;
        int choiceNum = randDecision.nextInt(c.size());
        int i = 0;
        for (T t : c)
        {
            if (i == choiceNum)
            {
                randChoice = t;
            }
            i++;
        }
        return randChoice;
    }

    /**
     * Should pick a suitable BoardSpace from the GameBoard, It makes sense that
     * the AI would always enter a room.
     *
     * @return the chosen BoardSpace
     */
    public BoardSpace chooseTeleport()
    {
        HashMap<Integer, Room> availableMoves = getGameBoard().getRooms();
        int randRoom = randDecision.nextInt(availableMoves.size());
        return availableMoves.get(randRoom + 1);
    }

    /**
     * should make the appropriate detective notes for not receiving a new clue
     * after a suggestion according to the suggestion made.
     */
    public void noPlayerClues(Player enquired, ArrayList<Player> withoutClues)
    {
        Suggestion lastSuggestion = getLastSuggestion();
        for (Player p : withoutClues)
        {
            markDetectiveTable(enquired, lastSuggestion.getCharacter(), DetNoteType.doesntHaveClue);
            markDetectiveTable(enquired, lastSuggestion.getRoom(), DetNoteType.doesntHaveClue);
            markDetectiveTable(enquired, lastSuggestion.getWeapon(), DetNoteType.doesntHaveClue);
        }
    }

    /**
     * should make the relevant detective notes for having received the clue
     * 'response'.
     *
     * @param response the ClueCard being shown
     * @param enquired the player showing the clue
     * @param playerQueue the queue of players
     */
    public void receivedClue(ClueCard response, Player enquired, ArrayList<Player> withoutClues)
    {
        Suggestion lastSuggestion = getLastSuggestion();
        markDetectiveTable(enquired, lastSuggestion.getCharacter(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getRoom(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, lastSuggestion.getWeapon(), DetNoteType.mightHaveClue);
        markDetectiveTable(enquired, response.getClueType(), DetNoteType.hasClue);

        for (Player p : withoutClues)
        {
            markDetectiveTable(enquired, lastSuggestion.getCharacter(), DetNoteType.doesntHaveClue);
            markDetectiveTable(enquired, lastSuggestion.getRoom(), DetNoteType.doesntHaveClue);
            markDetectiveTable(enquired, lastSuggestion.getWeapon(), DetNoteType.doesntHaveClue);
        }
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
        Weapon bestWeapon = (Weapon) randChoice(mostLikely(Weapon.values()));
        Character bestCharacter = (Character) randChoice(mostLikely(Character.values()));
        return new Suggestion(bestCharacter, (Room) getCurrentPosition(), bestWeapon, this);
    }

    private HashSet<ClueType> mostLikely(ClueType[] allClues)
    {
        DetectiveNotes detNotes = getDetNotes();

        ClueType bestChoice = null;
        
        HashSet<ClueType> possible = possibleClues(allClues);

        HashMap<ClueType, Integer> likely = new HashMap<ClueType, Integer>();
        //find clue that is most likely for plaers to not have
        for (ClueType ct : possible)
        {
            for (Player p : getOtherPlayers())
            {
                if (p != this && getDetNotes().checkClue(p, ct) == DetNoteType.mightHaveClue)
                {
                    likely.put(ct, likely.getOrDefault(ct, 0) + 1);
                }
            }
        }

        HashSet<ClueType> bestChoices = new HashSet<>();
        int min = Integer.MAX_VALUE;
        for (ClueType ct : likely.keySet())
        {
            int nextFreq = likely.get(ct);
            if (nextFreq < min)
            {
                min = nextFreq;
                bestChoices.add(ct);
            }
        }
        return bestChoices;
    }
    
    private HashSet<ClueType> possibleClues(ClueType[] allClues)
    {
        // determine possible clues which we don't know a player has / doesn't have
        HashSet<ClueType> possible = new HashSet<>();
        for (ClueType ct : allClues)
        {
            for (Player p : getOtherPlayers())
            {
                if (getDetNotes().checkClue(p, ct) != DetNoteType.hasClue)
                {
                    possible.add(ct);
                }
            }
        }
        return possible;
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
        HashSet<ClueType> possibleWeapons = possibleClues(Weapon.values());
        HashSet<ClueType> possibleCharacters = possibleClues(Character.values());
        Collection<Room> roomCollection = getGameBoard().getRooms().values();
        ClueType[] roomArray = new Room[roomCollection.size()];
        int i = 0;
        for (Room r : roomCollection)
        {
            roomArray[i] = r;
            i++;
        }
        HashSet<ClueType> possibleRooms = possibleClues(roomArray);
        
        boolean accusationQuery = false;
        if (possibleWeapons.size() + possibleRooms.size() + possibleCharacters.size() <= 6)
        {
            accusationQuery = true;
        }
        return accusationQuery;
    }

    /**
     * AIPlayer.decideAccusation should decide on, create and return a suitable
     * accusation.
     *
     * @return an Accusation reflecting the player's choice
     */
    public Accusation decideAccusation()
    {
        Weapon bestWeapon = (Weapon) randChoice(mostLikely(Weapon.values()));
        Character bestCharacter = (Character) randChoice(mostLikely(Character.values()));
        Collection<Room> roomCollection = getGameBoard().getRooms().values();
        ClueType[] roomArray = new Room[roomCollection.size()];
        int i = 0;
        for (Room r : roomCollection)
        {
            roomArray[i] = r;
            i++;
        }
        Room bestRoom = (Room) randChoice(mostLikely(roomArray));
        return new Accusation(bestCharacter, bestRoom, bestWeapon, this);
    }
}
