package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.RoomSquarePane;

/**
 * a class used to denote the physical spaces on the board grid which belong to
 * certain rooms
 *
 * @author Jamie
 */
public class RoomSquare extends BoardSpace
{
    private Room belongsTo;
    private int roomNo;

    /**
     * creates a RoomSquare from the given room number
     * @param roomNo room number this belongs to 
     */
    public RoomSquare(int roomNo)
    {
        super(0);
        this.roomNo = roomNo;
        setGuiPane(new RoomSquarePane(this));
    }

    /**
     * @return the room that this space belongs to
     */
    public Room belongsTo()
    {
        return belongsTo;
    }

    /**
     * set the Room that this square belongs to
     * @param belongsTo the room that this room tile belongs to
     */
    public void setBelongsTo(Room belongsTo)
    {
        this.belongsTo = belongsTo;
    }

    /**
     * @return the room number this belongs to
     */
    public int getRoomNo()
    {
        return roomNo;
    }

    /**
     * sets the room number of this RoomSquare
     * @param roomNo the new room number
     */
    public void setRoomNo(int roomNo)
    {
        this.roomNo = roomNo;
    }

    @Override
    public String toString()
    {
        return "" + roomNo + " ";
    }

}
