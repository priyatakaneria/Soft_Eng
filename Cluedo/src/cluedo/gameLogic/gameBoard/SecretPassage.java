package cluedo.gameLogic.gameBoard;

import cluedo.userInterface.boardTiles.SecretPassagePane;

/**
 * Represents the secret passage squares on the board linking two rooms
 *
 * @author Jamie Thelin
 */
public class SecretPassage extends BoardSpace
{
    private Room roomA;
    private Room roomB;
    private int roomNoA;

    /**
     * Creates a new SecretPassage given the room number it links to
     *
     * @param roomNoA the room this space links to
     */
    public SecretPassage(int roomNoA)
    {
        super(0);
        this.roomNoA = roomNoA;
        setGuiPane(new SecretPassagePane(this));
    }

    /**
     * Determines the result destination of moving through the passage, or
     * returns null if the supplied origin room is not one of the two connected
     * rooms.
     *
     * @param originRoom The room the player is starting from.
     * @return The available room to travel to (or null if originRoom is
     * invalid).
     */
    public Room getDest(Room originRoom)
    {
        if (originRoom == roomA)
        {
            return roomB;
        } else if (originRoom == roomA)
        {
            return roomA;
        } else
        {
            return null;
        }
    }

    public Room getRoomA()
    {
        return roomA;
    }

    public void setRoomA(Room roomA)
    {
        this.roomA = roomA;
    }

    public Room getRoomB()
    {
        return roomB;
    }

    public void setRoomB(Room roomB)
    {
        this.roomB = roomB;
    }

    public int getRoomNoA()
    {
        return roomNoA;
    }

    @Override
    public String toString()
    {
        return "" + roomNoA + " ";
    }

}
