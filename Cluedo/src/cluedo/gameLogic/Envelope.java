package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 * Stores the murder scenario for each game. this consists of a Character, Room
 * and Weapon.
 *
 * @author Jamie
 */
public class Envelope
{

    private Card character;
    private Card room;
    private Card weapon;

    public Envelope(Card character, Card room, Card weapon)
    {
        this.character = character;
        this.room = room;
        this.weapon = weapon;
    }
    
    public boolean checkEnvelope(Accusation accusation)
    {
        return false;
        // waiting for accusation and player implementations...
        // return (accusation.getCharacter() == character && accusation.getRoom() == room && accusation.getWeapon() == weapon);
    }

    public Card getCharacter() {
        return character;
    }

    public Card getRoom() {
        return room;
    }

    public Card getWeapon() {
        return weapon;
    }
    
    
}
