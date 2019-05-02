/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.gameBoard.Room;
import java.io.FileNotFoundException;
import java.util.HashSet;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jamie Thelin
 */
public class GameBoardTest
{

    GameBoard gb;

    public GameBoardTest()
    {
    }

    @Before
    public void setup() throws InvalidSetupFileException
    {
        gb = new GameBoard();
    }

    /**
     * Test for whether the availableMoves method works correctly for traversal
     * of the standard hallway squares.
     */
    @Test
    public void availableMovesEmptyGridTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesEmptyGridTest:");
        BoardConstructor bc = new BoardConstructor("hallways.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getBoardSpace(4, 4);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(4, 2));
        predicted.add(gb.getBoardSpace(5, 3));
        predicted.add(gb.getBoardSpace(6, 4));
        predicted.add(gb.getBoardSpace(5, 5));
        predicted.add(gb.getBoardSpace(4, 6));
        predicted.add(gb.getBoardSpace(3, 5));
        predicted.add(gb.getBoardSpace(2, 4));
        predicted.add(gb.getBoardSpace(3, 3));

        assertEquals(predicted, avail);
    }

    /**
     * Test for whether the availableMoves method works for moving into a room.
     */
    @Test
    public void availableMovesIntoRoomTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesIntoRoomTest:");
        BoardConstructor bc = new BoardConstructor("one room.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getBoardSpace(6, 7);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(7, 6));
        predicted.add(gb.getBoardSpace(8, 7));
        predicted.add(gb.getBoardSpace(7, 8));
        predicted.add(gb.getBoardSpace(5, 8));
        predicted.add(gb.getBoardSpace(4, 7));
        predicted.add(gb.getRoom(1));

        for (BoardSpace bs : avail)
        {
            int[] coords = gb.getSpaceCoords(bs);
            System.out.println(coords[0] + "\t" + coords[1]);
        }

        assertEquals(predicted, avail);
    }

    /**
     * Test for whether the availableMoves method works correctly for when a
     * player starts in a room with no secret passage and one door.
     */
    @Test
    public void availableMovesFromRoomTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesFromRoomTest:");
        BoardConstructor bc = new BoardConstructor("one room.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getRoom(1);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(5, 8));
        predicted.add(gb.getBoardSpace(4, 7));
        predicted.add(gb.getBoardSpace(6, 7));
        predicted.add(gb.getRoom(1));

        for (BoardSpace bs : avail)
        {
            int[] coords = gb.getSpaceCoords(bs);
            System.out.println(coords[0] + "\t" + coords[1]);
        }

        assertEquals(predicted, avail);
    }
    
    /**
     * Test for whether the availableMoves method works correctly for when a
     * player starts in a room with no secret passage but two door.
     */
    @Test
    public void availableMovesFromRoomTwoDoorTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesFromRoomTest:");
        BoardConstructor bc = new BoardConstructor("one room two doors.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getRoom(1);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(5, 8));
        predicted.add(gb.getBoardSpace(4, 7));
        predicted.add(gb.getBoardSpace(6, 7));
        
        predicted.add(gb.getBoardSpace(7, 2));
        predicted.add(gb.getBoardSpace(8, 3));
        predicted.add(gb.getBoardSpace(7, 4));
        
        predicted.add(gb.getRoom(1));

        for (BoardSpace bs : avail)
        {
            int[] coords = gb.getSpaceCoords(bs);
            System.out.println(coords[0] + "\t" + coords[1]);
        }
        System.out.println(avail);

        assertEquals(predicted, avail);
    }

    @Test
    public void variousAdjacencyTest() throws FileNotFoundException, InvalidSetupFileException
    {
        BoardConstructor bc = new BoardConstructor("one room.txt");
        gb = bc.createBoard();

        System.out.println("kitchen adjacency:");
        System.out.println(gb.getRoom(1).getAdjacency());
        System.out.println("kitchen available moves (2):");
        System.out.println(gb.availableMoves(gb.getRoom(1), 2));
    }
}
