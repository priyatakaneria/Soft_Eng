/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.BoardSpace;
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
    public void availableMovesTest()
    {
        gb = new GameBoard(4);
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                gb.insertBoardSpace(i, j, new BoardSquare(false));
            }
        }
        System.out.println(gb);
        HashSet<BoardSpace> avail = gb.availableMoves(gb.getBoardSpace(1, 1), 2);
        
        for (BoardSpace bs : avail)
        {
            System.out.println(bs);
        }
    }
}
