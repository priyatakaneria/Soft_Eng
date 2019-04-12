/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

/**
 *
 * @author Tymek
 */
public class Room extends BoardSpace implements ClueType
{

    public enum RoomType
    {

        study("study"), hall("hall"), lounge("lounge"), diningRoom("dining room"),
        kitchen("kitchen"), ballRoom("ball room"), conservatory("conservatory"),
        billardRoom("billard room"), library("library");

        private final String nameString;

        RoomType(String name)
        {
            this.nameString = name;
        }

        public String getRoomStringName()
        {
            return this.nameString;
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

}
