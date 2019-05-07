package cluedo.gameLogic.player;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.GameBoard;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a human player
 *
 * @author Jamie
 */
public class HumanPlayer extends Player
{

    public HumanPlayer(Character character, String playerName, GameBoard gb, BoardSpace start, Collection<Player> otherPlayers)
    {
        super(character, playerName, gb, start, otherPlayers);
    }

    /**
     * takes a string and writes it to the detective notes
     *
     * @param notes note to take
     */
    public void writeNotes(String notes)
    {
        getDetNotes().writeNotes(notes);
    }

}
