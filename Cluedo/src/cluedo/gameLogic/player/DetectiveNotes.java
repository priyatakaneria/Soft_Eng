package cluedo.gameLogic.player;

import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.gameBoard.Room;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Represents the detective notes page, including the clue table and a blank
 * notes box.
 *
 * @author tymoteuszilczyszyn
 */
public class DetectiveNotes
{

    private HashMap<Player, HashMap<ClueType, DetNoteType>> detectiveTable;
    private String detectiveNotes;
    private Collection<Player> players;
    private Collection<Room> rooms;

    /**
     * constructs a new instance with the appropriate player and an empty
     * HashMap
     *
     * @param players the collection of all players
     * @param rooms the collection of rooms for the current game
     */
    public DetectiveNotes(Collection<Player> players, Collection<Room> rooms)
    {
        this.players = players;
        detectiveNotes = "";
        detectiveTable = new HashMap<>();
        this.rooms = rooms;

    }

    /**
     * fill detectiveTable with all the players and clueTypes
     */
    public void initTable()
    {
        for (Player p : players)
        {
            detectiveTable.put(p, new HashMap<ClueType, DetNoteType>());
            for (Weapon w : Weapon.values())
            {
                detectiveTable.get(p).put(w, DetNoteType.noKnowledge);
            }
            for (Room r : rooms)
            {
                detectiveTable.get(p).put(r, DetNoteType.noKnowledge);
            }
            for (Character c : Character.values())
            {
                detectiveTable.get(p).put(c, DetNoteType.noKnowledge);
            }
        }
    }

    /**
     * @return the written notes.
     */
    public String getNotes()
    {
        return detectiveNotes;
    }

    /**
     * Make a note on the detective table.
     *
     * @param p1 the player who gave this clue
     * @param ct1 the clue they gave
     * @param dnt1 the type of detective note to make
     */
    public void markTable(Player p1, ClueType ct1, DetNoteType dnt1)
    {
        detectiveTable.get(p1).put(ct1, dnt1);
    }

    /**
     * write new notes to the 'notes' section
     *
     * @param n1 String to write
     */
    public void writeNotes(String n1)
    {
        detectiveNotes = detectiveNotes + "\n" + n1;
    }

    /**
     * returns the DetNoteType of a particular clue / player combination.
     *
     * @param p the Player
     * @param ct the ClueType
     * @return the DetNoteType assigned
     */
    public DetNoteType checkClue(Player p, ClueType ct)
    {
        return detectiveTable.get(p).get(ct);
    }

    /**
     * @return the Collection of players
     */
    public Collection<Player> getPlayers()
    {
        return players;
    }
}
