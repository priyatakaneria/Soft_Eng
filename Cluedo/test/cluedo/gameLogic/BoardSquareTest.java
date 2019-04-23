/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.BoardSpace;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Jamie Thelin
 */
public class BoardSquareTest
{
    private BoardSpace bSq1;
    private BoardSpace bSq2;
    
    public BoardSquareTest(){}
    
    @Before
    public void setUp()
    {
        bSq1 = new BoardSquare(false);
        bSq2 = new BoardSquare(false);
    }
    /*
    @Test
    public void adjacencySetTest()
    {
        
        
        assertArrayEquals(new BoardSpace[4], bSq1.getAdjacency());
        assertArrayEquals(new BoardSpace[4], bSq2.getAdjacency());
        
        bSq1.setAdjacent(0, bSq2);
        
        assertEquals(bSq2, bSq1.getAdjacency()[0]);
        assertEquals(bSq1, bSq2.getAdjacency()[2]);
    }
    */
    @Test
    public void intrigueSetTest()
    {
        assertEquals(false, ((BoardSquare) (bSq1)).isIntrigue());
        
        ((BoardSquare) (bSq1)).setIntrigue(true);
        
        assertEquals(true, ((BoardSquare) (bSq1)).isIntrigue());
    }
}
