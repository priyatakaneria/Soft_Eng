package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.StaircaseSquarePane;

/**
 * Represents the places on the board taken up by the staircase image on the
 * original board.
 *
 * @author Jamie
 */
public class StaircaseSquare extends EmptySquare
{

    public StaircaseSquare()
    {
        super();
        setGuiPane(new StaircaseSquarePane(this));
    }

    /**
     * @return An '/' character to represent a square in the staircase space.
     */
    @Override
    public String toString()
    {
        return "/ ";
    }
}
