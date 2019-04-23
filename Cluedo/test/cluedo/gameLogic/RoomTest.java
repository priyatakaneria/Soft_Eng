/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.Room.RoomType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tymek
 */
public class RoomTest
{

    public RoomTest()
    {
    }

    private RoomType r1, r2, r3;
    private Room room1, room2, room3;

    @Before
    public void setUp()
    {
        r1 = RoomType.ballRoom;
        room1 = new Room(r1);

        r2 = RoomType.billiardRoom;
        room2 = new Room(r2);

        r3 = RoomType.conservatory;
        room3 = new Room(r3);
    }

    @Test
    public void testGetRoomStringName()
    {
        String s1 = "ball room";
        String s2 = "billard room";
        String s3 = "conservatory";

        assertEquals(s1, r1.getRoomStringName());
        assertEquals(s2, r2.getRoomStringName());
        assertEquals(s3, r3.getRoomStringName());
    }

    @Test
    public void testGetRoomName()
    {
        RoomType e1 = RoomType.ballRoom;
        RoomType e2 = RoomType.billiardRoom;
        RoomType e3 = RoomType.conservatory;

        assertEquals(e1, room1.getRoomName());
        assertEquals(e2, room2.getRoomName());
        assertEquals(e3, room3.getRoomName());

    }

}
