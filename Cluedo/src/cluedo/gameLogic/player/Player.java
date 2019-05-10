package cluedo.gameLogic.player;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.IntrigueCard;
import cluedo.gameLogic.IntrigueType;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.RoomSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.BoardSpace;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import cluedo.userInterface.PlayerPiece;
import cluedo.userInterface.boardTiles.BoardSpacePane;

/**
 * Represents the player
 *
 * @author Jamie
 */
public abstract class Player
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

    private PlayerPiece playerPiece;

    private Random randRoomSquare;
    private BoardSpacePane guiRoomLocation;

    /**
     * initialises the player
     *
     * @param character the Character this player is playing
     * @param playerName the name of the player
     * @param gb the GameBoard
     * @param start the initial starting position for the player's chosen
     * character
     * @param otherPlayers the Collection of all other players in the game
     */
    public Player(Character character, String playerName, GameBoard gb, BoardSpace start, Collection<Player> otherPlayers)
    {
        clueHand = new ArrayList<>();
        intrigueHand = new ArrayList<>();
        this.character = character;
        detNotes = new DetectiveNotes(otherPlayers, gb.getRooms().values());
        this.gb = gb;
        currentPosition = start;
        start.addOccupant(this);
        playerLost = false;
        lastSuggestion = null;
        this.otherPlayers = otherPlayers;
        this.playerName = playerName;
        playerPiece = new PlayerPiece(this);
        randRoomSquare = new Random();
    }

    /**
     * @return the GameBoard
     */
    public GameBoard getGameBoard()
    {
        return gb;
    }

    /**
     * @return the player's name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @return the player's previous suggestion made
     */
    public Suggestion getLastSuggestion()
    {
        return lastSuggestion;
    }

    /**
     * @return the Collection of all other players
     */
    public Collection<Player> getOtherPlayers()
    {
        return otherPlayers;
    }

    /**
     * @return true if the player can no longer move / make suggestions /
     * accusations.
     */
    public boolean hasPlayerLost()
    {
        return playerLost;
    }

    /**
     * Sets whether the player has lost
     *
     * @param playerLost whether the player has lost or not
     */
    public void setPlayerLost(boolean playerLost)
    {
        this.playerLost = playerLost;
    }

    /**
     * @return DetectiveNotes belonging to this player
     */
    public DetectiveNotes getDetNotes()
    {
        return detNotes;
    }

    /**
     * @return the player's hand of clue cards
     */
    public ArrayList<ClueCard> getClueHand()
    {
        return clueHand;
    }

    /**
     * Adds a ClueCard to the player's hand.
     *
     * @param card the card to add
     */
    public void addClueCard(ClueCard card)
    {
        clueHand.add(card);
    }

    /**
     * @return the Character of this player.
     */
    public Character getCharacter()
    {
        return character;
    }

    /**
     * @return the player's current space on the board
     */
    public BoardSpace getCurrentPosition()
    {
        return currentPosition;
    }

    /**
     * moves the player to a new space.
     *
     * @param space the new space
     * @return true if moved successfully, otherwise false;
     */
    public boolean Move(BoardSpace space)
    {

        if (!(currentPosition instanceof Room))
        {
            currentPosition.getGuiPane().removePlayer(playerPiece);
        } //
        else
        {
            guiRoomLocation.removePlayer(getGuiPiece());
        }
        currentPosition.removeOccupant(this);
        currentPosition = space;
        if (currentPosition instanceof Room)
        {
            HashSet<RoomSquare> possibleTiles = gb.getAllFromRoom(((Room) currentPosition));
            int randChoice = randRoomSquare.nextInt(possibleTiles.size());
            guiRoomLocation = ((BoardSpace) possibleTiles.toArray()[randChoice]).getGuiPane();
            guiRoomLocation.addPlayer(playerPiece);
        } //
        else
        {
            currentPosition.getGuiPane().addPlayer(getGuiPiece());
        }

        return space.addOccupant(this);
    }

    /**
     * Generates a new Suggestion and returns it
     *
     * @param character the character to suggest
     * @param room the room to suggest
     * @param weapon the weapon to suggest
     * @return the new suggestion
     */
    public Suggestion makeSuggestion(Character character, Room room, Weapon weapon)
    {
        lastSuggestion = new Suggestion(character, room, weapon, this);
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

    /**
     * Generates a new Accusation and returns it
     *
     * @param character the character to suggest
     * @param room the room to suggest
     * @param weapon the weapon to suggest
     * @param envelope the murder envelope to check the accusation against
     * @return the new Accusation
     */
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

    /**
     * Make a note on the player's detective table.
     *
     * @param clueGiver the player who gave this clue
     * @param cluetype the clue they gave
     * @param dnt the type of detective note to make
     */
    public void markDetectiveTable(Player clueGiver, ClueType cluetype, DetNoteType dnt)
    {
        detNotes.markTable(clueGiver, cluetype, dnt);
    }

    /**
     * @deprecated Not used, enquiries are to be made from the TurnManager
     * instead
     * 
     * @param player the player to enquire
     * @param suggestion the suggestion made
     * @return the list of clues that the player responded with
     */
    public ArrayList<ClueCard> enquirePlayer(Suggestion suggestion, Player player)
    {
        return player.respondToSuggestion(suggestion);
    }

    /**
     * adds an intrigue card to the player's hand.
     *
     * @param ic the card to add
     */
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

    /**
     * sets the corresponding GUI object for this player.
     *
     * @param pp the visible playing piece connected to this player
     */
    public void setGuiPiece(PlayerPiece pp)
    {
        playerPiece = pp;
    }

    /**
     * @return the corresponding GUI object for this player
     */
    public PlayerPiece getGuiPiece()
    {
        return playerPiece;
    }
}
