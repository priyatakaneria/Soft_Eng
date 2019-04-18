/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

/**
 * a class used to denote the physical spaces on the board grid which belong to certain rooms
 * @author Jamie
 */
public class RoomSquare extends BoardSpace
{
    private Room belongsTo;
    private int roomNo;
    
    public RoomSquare()
    {
        super(0);
    }

    public Room getBelongsTo()
    {
        return belongsTo;
    }

    public void setBelongsTo(Room belongsTo)
    {
        this.belongsTo = belongsTo;
    }

    public int getRoomNo()
    {
        return roomNo;
    }

    public void setRoomNo(int roomNo)
    {
        this.roomNo = roomNo;
    }
    
    
}
