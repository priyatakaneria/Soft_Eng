

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;
import java.util.ArrayList;
/**
 *
 * @author Jamie
 */
public class Player
{
    private ArrayList<ClueCard> card;
    private Character character;
    //private DetectiveTable detectiveTable;  // To be implemented later

    public Player(Character character)
    {
        card = new ArrayList<>();
        this.character = character;
    }
    
    public void Move()
    {
        
    }
    
    public void makeSuggestion(Character character, Room room, Weapon weapon)
    {
        
    }
    
    public boolean makeAccusation(Character character, Room room, Weapon weapon)
    {
        return false;
    }
    
    public ClueCard respondToSuggestion(Suggestion suggestion)
    {
        return null;
    }
    
    public void markDetectiveTable(ClueType cluetype, Player clueGiver)
    {
        
    }
    
    
}
