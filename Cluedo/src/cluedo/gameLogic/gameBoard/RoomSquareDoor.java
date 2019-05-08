package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.RoomSquareDoorPane;

/**
 * Represents the doorway of a room
 * 
 * @author Jamie
 */
public class RoomSquareDoor extends RoomSquare
{

    public RoomSquareDoor()
    {
        super(-1);
        setGuiPane(new RoomSquareDoorPane(this));
    }

    @Override
    public String toString()
    {
        return "[]";
    }
}
