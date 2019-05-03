/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.ClueType;
import java.util.HashMap;

/**
 *
 * @author tymoteuszilczyszyn
 */
public class DetectiveNotes
{

    private HashMap<Player, HashMap<ClueType, DetNoteType>> detectiveTable;
    private String detectiveNotes;

    public DetectiveNotes()
    {
        detectiveNotes = "";
        detectiveTable = new HashMap<>();
    }

    public void markTable(Player p1, ClueType ct1, DetNoteType dnt1)
    {
        detectiveTable.get(p1).put(ct1, dnt1);
    }

    public void writeNotes(String n1)
    {
        detectiveNotes = detectiveNotes + "\n" + n1;
    }

}
