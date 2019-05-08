/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

/**
 *
 * @author Jamie Thelin
 */
public class SecretPassage extends BoardSpace
{
    private Room roomA;
    private Room roomB;
    private int roomNoA;

    public SecretPassage(Room roomA, Room roomB)
    {
        super(0);
        this.roomA = roomA;
        this.roomB = roomB;
        super.setAdjacent(2, roomA);
        super.setAdjacent(0, roomB);
    }
    
    public SecretPassage(Room roomA)
    {
        super(0);
        this.roomA = roomA;
        super.setAdjacent(2, roomA);
    }
    
    public SecretPassage(int roomNoA)
    {
        super(0);
        this.roomNoA = roomNoA;
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
