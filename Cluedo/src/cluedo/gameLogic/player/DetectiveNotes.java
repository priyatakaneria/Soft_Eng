/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.gameBoard.Room;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author tymoteuszilczyszyn
 */
public class DetectiveNotes
{

    private HashMap<Player, HashMap<ClueType, DetNoteType>> detectiveTable;
    private String detectiveNotes;
    private Collection<Player> players;

    public DetectiveNotes(Collection<Player> players, Collection<Room> rooms)
    {
        this.players = players;
        detectiveNotes = "";
        detectiveTable = new HashMap<>();
        // fill detectiveTable with all the players and clueTypes
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
    
    public String getNotes()
    {
        return detectiveNotes;
    }

    public void markTable(Player p1, ClueType ct1, DetNoteType dnt1)
    {
        detectiveTable.get(p1).put(ct1, dnt1);
    }

    public void writeNotes(String n1)
    {
        detectiveNotes = detectiveNotes + "\n" + n1;
    }
    
    public DetNoteType checkClue(Player p, ClueType ct)
    {
        return detectiveTable.get(p).get(ct);
    }
    
    public Collection<Player> getPlayers()
    {
        return players;
    }
}
