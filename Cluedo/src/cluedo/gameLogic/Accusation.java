package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 * Represents an accusation made by a player. This is their final guess and is
 * checked against the envelope to see if they have won the game.
 *
 * @author Jamie
 */
public class Accusation extends AccuSuggest
{
    /**
     * Creates an accusation object with the arguments provided. Simply calls
     * the constructor for AccuSuggestion
     *
     * @param character the murderer
     * @param room the room in which the murder happened
     * @param weapon the weapon which was used
     * @param asker the player who proposed this accusation
     */
    public Accusation(Character character, Room room, Weapon weapon, Player asker)
    {
        super(character, room, weapon, asker);
    }
}
