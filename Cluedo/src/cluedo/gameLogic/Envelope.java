package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;
import cluedo.gameLogic.Character;

/**
 * Stores the murder scenario for each game. this consists of a Character, Room
 * and Weapon.
 *
 * @author Jamie
 */
public class Envelope
{

    private Character character;
    private Room room;
    private Weapon weapon;

    public Envelope(ClueCard character, ClueCard room, ClueCard weapon)
    {
        this.character = (Character)((ClueCard) character).getClueType();
        this.room = (Room) ((ClueCard) room).getClueType();
        this.weapon = (Weapon) ((ClueCard) weapon).getClueType();
    }
    
    public boolean checkEnvelope(Accusation accusation)
    {
        return ((accusation.getCharacter() == character) && (accusation.getRoom() == room) && (accusation.getWeapon() == weapon));
    }

    public Character getCharacter() {
        return character;
    }

    public Room getRoom() {
        return room;
    }

    public Weapon getWeapon() {
        return weapon;
    }
    
    
}
