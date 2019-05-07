package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 * Represents a suggestion made by a player. This is their intermediate guess at
 * each turn which is put to the other players.
 *
 * @author Jamie
 */
public class Suggestion extends AccuSuggest
{
    /**
     * Creates a suggestion object with the arguments provided. Simply calls
     * the constructor for AccuSuggestion
     *
     * @param character the murderer
     * @param room the room in which the murder happened
     * @param weapon the weapon which was used
     * @param asker the player who proposed this suggestion
     */
    public Suggestion(Character character, Room room, Weapon weapon, Player asker)
    {
        super(character, room, weapon, asker);
    }

}
