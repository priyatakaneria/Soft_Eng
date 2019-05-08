/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.player;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.IntrigueCard;
import cluedo.gameLogic.IntrigueType;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.BoardSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 *
 * @author Jamie
 */
public class Player
{

    private ArrayList<ClueCard> clueHand;
    private ArrayList<IntrigueCard> intrigueHand;
    private Character character;
    private DetectiveNotes detNotes;
    private GameBoard gb;
    private String playerName;
    private BoardSpace currentPosition;
    private boolean playerLost;
    private Suggestion lastSuggestion;
    private Collection<Player> otherPlayers;

    //Add Gameboard as Parameter
    public Player(Character character, String playerName, GameBoard gb, BoardSpace start, Collection<Player> otherPlayers)
    {
        clueHand = new ArrayList<>();
        intrigueHand = new ArrayList<>();
        this.character = character;
        detNotes = new DetectiveNotes(otherPlayers, gb.getRooms().values());
        this.gb = gb;
        currentPosition = start;
        playerLost = false;
        lastSuggestion = null;
        this.otherPlayers = otherPlayers;
    }

    public GameBoard getGameBoard()
    {
        return gb;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public Suggestion getLastSuggestion()
    {
        return lastSuggestion;
    }

    public Collection<Player> getOtherPlayers()
    {
        return otherPlayers;
    }
    
    public boolean hasPlayerLost()
    {
        return playerLost;
    }

    public void setPlayerLost(boolean playerLost)
    {
        this.playerLost = playerLost;
    }

    public DetectiveNotes getDetNotes()
    {
        return detNotes;
    }

    public ArrayList<ClueCard> getClueHand()
    {
        return clueHand;
    }

    public void addClueCard(ClueCard card)
    {
        clueHand.add(card);
    }

    public Character getCharacter()
    {
        return character;
    }

    public void setCharacter(Character character)
    {
        this.character = character;
    }

    public BoardSpace getCurrentPosition()
    {
        return currentPosition;
    }

    public boolean Move(BoardSpace space)
    {
        currentPosition.removeOccupant(this);
        currentPosition = space;
        return space.addOccupant(this);
    }

    public Suggestion makeSuggestion(Character character, Room room, Weapon weapon)
    {
        lastSuggestion = new Suggestion(room, weapon, character, this);
        return lastSuggestion;
    }

    /**
     * Accepts a suggestion and returns all the possible clues to give.
     *
     * @param suggestion the suggestion to check against
     * @return ArrayList of ClueCards corresponding to the possible clues to
     * give.
     */
    public ArrayList<ClueCard> respondToSuggestion(Suggestion suggestion)
    {
        ArrayList<ClueCard> possibleClues = new ArrayList<>();
        ClueType clueType;
        for (ClueCard clue : clueHand)
        {
            clueType = clue.getClueType();
            if (clueType instanceof Weapon)
            {
                if (suggestion.getWeapon() == clueType)
                {
                    possibleClues.add(clue);
                }
            } //
            else if (clueType instanceof Room)
            {
                if (suggestion.getRoom() == clueType)
                {
                    possibleClues.add(clue);
                }
            } //
            else if (clueType instanceof Character)
            {
                if (suggestion.getCharacter() == clueType)
                {
                    possibleClues.add(clue);
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
        } else
        {
            return false;
        }
    }

    public void markDetectiveTable(Player clueGiver, ClueType cluetype, DetNoteType dnt)
    {
        detNotes.markTable(clueGiver, cluetype, dnt);
    }

    /**
     * possible not needed?
     *
     * @param suggestion
     * @param player
     * @return
     */
    public ArrayList<ClueCard> enquirePlayer(Suggestion suggestion, Player player)
    {
        return player.respondToSuggestion(suggestion);
    }

    public void addIntrigueCard(IntrigueCard ic)
    {
        intrigueHand.add(ic);
    }

    /**
     * Searches through the intrigueHand until the corresponding intrigue type
     * is found, and removes it.
     *
     * @param it the IntrigueType to look for
     * @return the card removed from the hand
     */
    public IntrigueCard removeIntrigueCard(IntrigueType it)
    {
        IntrigueCard foundCard = null;
        for (IntrigueCard ic : intrigueHand)
        {
            if (ic.getIntrigueType() == it)
            {
                foundCard = ic;
            }
        }
        if (foundCard != null)
        {
            intrigueHand.remove(foundCard);
        }
        return foundCard;
    }

    public boolean hasIntrigueType(IntrigueType it)
    {
        IntrigueCard foundCard = null;
        for (IntrigueCard ic : intrigueHand)
        {
            if (ic.getIntrigueType() == it)
            {
                foundCard = ic;
            }
        }
        return foundCard != null;
    }

}
