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
import java.util.HashMap;
import java.util.ArrayList;
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
     * 
     * No longer passes since BoardConstructor ensures that a board has at least
     * one room, but it used to work I promise.
     */
    @Test
    public void availableMovesEmptyGridTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesEmptyGridTest:");
        BoardConstructor bc = new BoardConstructor("customisation/board layout/hallways.txt");
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
        
        predicted.add(gb.getBoardSpace(4, 3));
        predicted.add(gb.getBoardSpace(3, 4));
        predicted.add(gb.getBoardSpace(4, 5));
        predicted.add(gb.getBoardSpace(5, 4));
        
        assertEquals(predicted, avail);
    }

    /**
     * Test for whether the availableMoves method works for moving into a room.
     */
    @Test
    public void availableMovesIntoRoomTest() throws FileNotFoundException, InvalidSetupFileException
    {
        System.out.println("\navailableMovesIntoRoomTest:");
        BoardConstructor bc = new BoardConstructor("customisation/board layout/one room.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getBoardSpace(6, 7);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(7, 6));
        predicted.add(gb.getBoardSpace(8, 7));
        predicted.add(gb.getBoardSpace(7, 8));
        predicted.add(gb.getBoardSpace(5, 8));
        predicted.add(gb.getBoardSpace(4, 7));
        
        predicted.add(gb.getBoardSpace(6, 8));
        predicted.add(gb.getBoardSpace(5, 7));
        predicted.add(gb.getBoardSpace(7, 7));
        
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
        BoardConstructor bc = new BoardConstructor("customisation/board layout/one room.txt");
        gb = bc.createBoard();

        BoardSpace start = gb.getRoom(1);
        HashSet<BoardSpace> avail = gb.availableMoves(start, 2);

        HashSet<BoardSpace> predicted = new HashSet<>();
        predicted.add(gb.getBoardSpace(5, 8));
        predicted.add(gb.getBoardSpace(4, 7));
        predicted.add(gb.getBoardSpace(6, 7));
        predicted.add(gb.getRoom(1));
        
        predicted.add(gb.getBoardSpace(5, 7));
        

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
        BoardConstructor bc = new BoardConstructor("customisation/board layout/one room two doors.txt");
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
        
        predicted.add(gb.getBoardSpace(7, 3));
        predicted.add(gb.getBoardSpace(5, 7));

        for (BoardSpace bs : avail)
        {
            int[] coords = gb.getSpaceCoords(bs);
            System.out.println(coords[0] + "\t" + coords[1]);
        }
        System.out.println(avail);

        assertEquals(predicted, avail);
    }

    /**
     * ensures the adjacency of all the starting squares are set appropriately
     *
     * @throws FileNotFoundException if the setupFile cannot be found
     * @throws InvalidSetupFileException if the
     */
    @Test
    public void variousAdjacencyTest() throws FileNotFoundException, InvalidSetupFileException
    {
        BoardConstructor bc = new BoardConstructor("customisation/board layout/one room.txt");
        gb = bc.createBoard();

        System.out.println("kitchen adjacency:");
        System.out.println(gb.getRoom(1).getAdjacency());
        System.out.println("kitchen available moves (2):");
        System.out.println(gb.availableMoves(gb.getRoom(1), 2));

        bc = new BoardConstructor("customisation/board layout/default.txt");
        gb = bc.createBoard();
        for (Character c : gb.getStartingSquares().keySet())
        {
            System.out.println("Character: " + c + "\tStart Adjacency: " + gb.getStartingSquares().get(c).getAdjacency());
        }

        HashMap<Integer, ArrayList<BoardSpace>> adjacency = gb.getStartingSquares().get(Character.ColMustard).getAdjacency();
        assertEquals(0, (adjacency).get(0).size());
        assertEquals(1, (adjacency).get(1).size());
        assertEquals(0, (adjacency).get(2).size());
        assertEquals(0, (adjacency).get(3).size());
        assertEquals(true, (adjacency).get(1).get(0) instanceof BoardSquare);

        adjacency = gb.getStartingSquares().get(Character.MissScarlett).getAdjacency();
        assertEquals(1, (adjacency).get(0).size());
        assertEquals(0, (adjacency).get(1).size());
        assertEquals(0, (adjacency).get(2).size());
        assertEquals(0, (adjacency).get(3).size());
        assertEquals(true, (adjacency).get(0).get(0) instanceof BoardSquare);

        adjacency = gb.getStartingSquares().get(Character.MrsPeacock).getAdjacency();
        assertEquals(0, (adjacency).get(0).size());
        assertEquals(0, (adjacency).get(1).size());
        assertEquals(0, (adjacency).get(2).size());
        assertEquals(1, (adjacency).get(3).size());
        assertEquals(true, (adjacency).get(3).get(0) instanceof BoardSquare);

        adjacency = gb.getStartingSquares().get(Character.MrsWhite).getAdjacency();
        assertEquals(0, (adjacency).get(0).size());
        assertEquals(0, (adjacency).get(1).size());
        assertEquals(1, (adjacency).get(2).size());
        assertEquals(0, (adjacency).get(3).size());
        assertEquals(true, (adjacency).get(2).get(0) instanceof BoardSquare);

        adjacency = gb.getStartingSquares().get(Character.ProfPlum).getAdjacency();
        assertEquals(0, (adjacency).get(0).size());
        assertEquals(0, (adjacency).get(1).size());
        assertEquals(0, (adjacency).get(2).size());
        assertEquals(1, (adjacency).get(3).size());
        assertEquals(true, (adjacency).get(3).get(0) instanceof BoardSquare);
    }
    
    
    @Test
    public void secretPassageTest() throws FileNotFoundException, InvalidSetupFileException
    {
        BoardConstructor bc = new BoardConstructor("customisation/board layout/default.txt");
        gb = bc.createBoard();
        
        HashMap<Integer, ArrayList<BoardSpace>> adjacency;
        adjacency = gb.getRooms().get(1).getAdjacency();
        HashSet<BoardSpace> avail = gb.availableMoves(gb.getRooms().get(1), 2);
        assertEquals(true, avail.contains(gb.getRooms().get(8)));
    }
}
