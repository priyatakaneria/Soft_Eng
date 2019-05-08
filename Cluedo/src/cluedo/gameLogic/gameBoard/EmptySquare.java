package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.EmptySquarePane;

/**
 * Represents a non-walkable tile which are located at the edges of the board to
 * ensure that the GameBoard has the same width/height throughout.
 *
 * @author Jamie Thelin
 */
public class EmptySquare extends BoardSpace
{
    public EmptySquare()
    {
        super(0);
        setGuiPane(new EmptySquarePane(this));
    }

    /**
     * @return a '#' character to represent an empty space.
     */
    @Override
    public String toString()
    {
        return "# ";
    }
}
