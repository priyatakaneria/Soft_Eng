package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;
import cluedo.gameLogic.Character;

/**
 * Stores the murder scenario for each game, this consists of a Character, Room
 * and Weapon.
 *
 * @author Jamie
 */
public class Envelope
{

    private Character character;
    private Room room;
    private Weapon weapon;

    /**
     * creates a new envelope with the provided arguments stored
     *
     * @param character the character card in the envelope
     * @param room the room card in the envelope
     * @param weapon the weapon card in the envelope
     */
    public Envelope(ClueCard character, ClueCard room, ClueCard weapon)
    {
        this.character = (Character) ((ClueCard) character).getClueType();
        this.room = (Room) ((ClueCard) room).getClueType();
        this.weapon = (Weapon) ((ClueCard) weapon).getClueType();
    }

    /**
     * Determines whether an accusation is correct.
     *
     * @param accusation the accusation to check
     * @return true if all elements of an accusation match that of the envelope,
     * otherwise false.
     */
    public boolean checkEnvelope(Accusation accusation)
    {
        return ((accusation.getCharacter() == character) && (accusation.getRoom() == room) && (accusation.getWeapon() == weapon));
    }

    /**
     * @return the character specified in this envelope
     */
    public Character getCharacter()
    {
        return character;
    }

    /**
     * @return the room specified in this envelope
     */
    public Room getRoom()
    {
        return room;
    }

    /**
     * @return the weapon specified in this envelope
     */
    public Weapon getWeapon()
    {
        return weapon;
    }

}
