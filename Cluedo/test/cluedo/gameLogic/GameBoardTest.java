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
    public void setup()
    {
        gb = new GameBoard();
    }

    /*
    @Test
    public void initTest()
    {

        System.out.println(gb);
    }
     */
    @Test
    public void availableMovesEmptyGridTest() throws FileNotFoundException, InvalidSetupFileException
    {
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

    @Test
    public void availableMovesIntoRoomTest() throws FileNotFoundException, InvalidSetupFileException
    {
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
        predicted.add(gb.getRooms().get(1));

        for (BoardSpace bs : avail)
        {
            int[] coords = gb.getSpaceCoords(bs);
            System.out.println(coords[0] + "\t" + coords[1]);
        }

        assertEquals(predicted, avail);
    }
    
    @Test
    public void variousAdjacencyTest() throws FileNotFoundException, InvalidSetupFileException
    {
        BoardConstructor bc = new BoardConstructor("one room.txt");
        gb = bc.createBoard();
        
        System.out.println("[6,7] adjacency:");
        System.out.println(gb.getBoardSpace(6,7).getAdjacency());
    }
}
