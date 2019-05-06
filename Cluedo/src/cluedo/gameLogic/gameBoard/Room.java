/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.ClueType;

/**
 *
 * @author Tymek
 */
public class Room extends BoardSpace implements ClueType
{

    public enum RoomType
    {

        kitchen("kitchen", 1), ballRoom("ball room", 2), conservatory("conservatory", 3), 
        billiardRoom("billiard room", 4), library("library", 5), study("study", 6), 
        hall("hall", 7), lounge("lounge", 8), diningRoom("dining room", 9);

        private final String nameString;
        private final int roomNo;
        
        RoomType(String name, int roomNo)
        {
            this.nameString = name;
            this.roomNo = roomNo;
        }

        public String getRoomStringName()
        {
            return this.nameString;
        }
        
        public int getRoomNo()
        {
            return roomNo;
        }
    };

    private SecretPassage secretPassage;
    private final RoomType roomName;

    public Room(RoomType roomName)
    {
        super(6);
        this.roomName = roomName;
    }

    public Room(RoomType roomName, SecretPassage secretPassage)
    {
        super(6);
        this.roomName = roomName;
    }
    
    public RoomType getRoomName()
    {
        return this.roomName;
    }
    
    public int getRoomNo()
    {
        return roomName.getRoomNo();
    }

    @Override
    public String toString()
    {
        return roomName.getRoomStringName();
    }
}
