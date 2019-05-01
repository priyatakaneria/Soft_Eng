

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.BoardSpace;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author Jamie
 */
public class Player
{
    private ArrayList<ClueCard> clueHand;
    private Character character;
    private DetectiveNotes detNotes;
    private GameBoard gb;
    private String playerName;
     
    //Add Gameboard as Parameter
   
    public Player(Character character, String playerName, GameBoard gb)
    {
        clueHand = new ArrayList<>();
        this.character = character;
        detNotes = new DetectiveNotes();
        this.gb = gb;
        
    }
    
    public DetectiveNotes getDetNotes() {
        return detNotes;
    }

    public ArrayList<ClueCard> getClueHand() {
        return clueHand;
    }

    public void setClueHand(ClueCard card) {
        clueHand.add(card);
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public void setDetNotes(DetectiveNotes detNotes) {
        this.detNotes = detNotes;
    }
    
    
    
    public boolean Move(BoardSpace space)
    {
        //Main Method needs to instantiate BoardConstructor. Once this is done, the GameBoard's hashmap can be referenced from non-static context. Or BoardConstructor needs to be static. 
        //This is so player is removed from previous space. 
        Iterator i = gb.getGrid().entrySet().iterator();
        while (i.hasNext())
        {
            Map.Entry innerHm = (Map.Entry)i.next();
            Iterator j = ((HashMap)innerHm.getValue()).entrySet().iterator();
            while (j.hasNext())
            {
                Map.Entry boardspace = (Map.Entry)j.next();
                if (((BoardSpace)boardspace.getValue()).getOccupants().contains(this))
                {
                    ((BoardSpace)boardspace.getValue()).removeOccupant(this);
                }
            }
        }
        return space.addOccupant(this);
        
        //If this doesn't work, change the grid field in BoardSpace to public, and reference directly (not through getGrid method). 
    }
    
    public void makeSuggestion(Character character, Room room, Weapon weapon)
    {
        
    }
    
       
    public ClueCard respondToSuggestion(Suggestion suggestion)
    {
        String name;
        for (ClueCard clue : clueHand)
        {
            name = clue.getClueType().getClass().getSimpleName().toString();
            if (name.equals("Weapon"))
            {
                if (suggestion.getWeapon().equals(clue.getClueType()))
                {
                    return clue;
                }
            }
            else if (name.equals("Room"))
            {
                if (suggestion.getRoom().equals(clue.getClueType()))
                {
                    return clue;
                }
            }
            else if (name.equals("Character"))
            {
                if (suggestion.getCharacter().equals(clue.getClueType()))
                {
                    return clue;
                }
            }
        }
        return null;
    }
    
    public boolean makeAccusation(Character character, Room room, Weapon weapon, cluedo.gameLogic.Envelope envelope)
    {
        if (character.equals(envelope.getCharacter()) && room.equals(envelope.getRoom()) && weapon.equals(envelope.getWeapon()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void markDetectiveTable(ClueType cluetype, Player clueGiver)
    {
        
    }
    
    public ClueCard enquirePlayer(Suggestion suggestion, Player player)
    {
         return player.respondToSuggestion(suggestion);
    }
    
    
}
