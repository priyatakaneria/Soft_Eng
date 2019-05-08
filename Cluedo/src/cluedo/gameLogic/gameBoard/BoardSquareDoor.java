package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.BoardSquarePane;

/**
 * an extension of BoardSquare which represents a square that is next to a
 * doorway, has the same appearance as the standard BoardSquare, but used by the
 * board constructor to set the appropriate adjacencies.
 *
 * @author Jamie
 */
public class BoardSquareDoor extends BoardSquare
{

    public BoardSquareDoor(boolean isIntrigue)
    {
        super(isIntrigue);
        setGuiPane(new BoardSquarePane(this));
    }
}
