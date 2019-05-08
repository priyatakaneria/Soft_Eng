/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import java.io.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jamie
 */
public class BoardConstructorTest
{

    public BoardConstructorTest()
    {
    }

    /**
     * Ensures that the default file is read correctly and returns an accurate
     * string representing the various BoardSpaces.
     * 
     * 
     *
     * @throws FileNotFoundException if the default file is somehow missing from
     * the project folder
     * @throws InvalidSetupFileException if the default file is somehow
     * corrupted and
     */
    @Test
    public void defaultFileReadTest() throws FileNotFoundException, InvalidSetupFileException
    {
        BoardConstructor bc = new BoardConstructor();
        String stringBoard = bc.createBoard().toString();
        assertEquals(
                "# # # # # # # # # _ 2 2 2 2 _ # # # # # # # # # \n" +
                "1 1 1 1 1 6 # _ _ _ 2 2 2 2 _ _ _ # 3 3 3 3 3 3 \n" +
                "1 1 1 1 1 1 _ _ 2 2 2 2 2 2 2 2 _ _ 3 3 3 3 3 3 \n" +
                "1 1 1 1 1 1 _ _ 2 2 2 2 2 2 2 2 _ _ 3 3 3 3 3 3 \n" +
                "1 1 1 1 1 1 _ _ 2 2 2 2 2 2 2 2 _ _ []3 3 3 3 3 \n" +
                "1 1 1 1 1 1 _ _ []2 2 2 2 2 2 []_ _ _ 3 3 3 8 # \n" +
                "# 1 1 1 []1 _ _ 2 2 2 2 2 2 2 2 _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ 2 []2 2 2 2 []2 _ _ _ _ _ _ _ # \n" +
                "# _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 4 4 4 4 4 4 \n" +
                "9 9 9 9 9 _ _ _ _ _ _ _ _ _ _ _ _ _ []4 4 4 4 4 \n" +
                "9 9 9 9 9 9 9 9 _ _ / / / / / _ _ _ 4 4 4 4 4 4 \n" +
                "9 9 9 9 9 9 9 9 _ _ / / / / / _ _ _ 4 4 4 4 4 4 \n" +
                "9 9 9 9 9 9 9 []_ _ / / / / / _ _ _ 4 4 4 4 []4 \n" +
                "9 9 9 9 9 9 9 9 _ _ / / / / / _ _ _ _ _ _ _ _ # \n" +
                "9 9 9 9 9 9 9 9 _ _ / / / / / _ _ _ 5 5 []5 5 # \n" +
                "9 9 9 9 9 9 []9 _ _ / / / / / _ _ 5 5 5 5 5 5 5 \n" +
                "# _ _ _ _ _ _ _ _ _ / / / / / _ _ []5 5 5 5 5 5 \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 5 5 5 5 5 5 5 \n" +
                "# _ _ _ _ _ _ _ _ 7 7 [][]7 7 _ _ _ 5 5 5 5 5 # \n" +
                "3 8 8 8 8 8 []_ _ 7 7 7 7 7 7 _ _ _ _ _ _ _ _ _ \n" +
                "8 8 8 8 8 8 8 _ _ 7 7 7 7 7 []_ _ _ _ _ _ _ _ # \n" +
                "8 8 8 8 8 8 8 _ _ 7 7 7 7 7 7 _ _ []6 6 6 6 6 1 \n" +
                "8 8 8 8 8 8 8 _ _ 7 7 7 7 7 7 _ _ 6 6 6 6 6 6 6 \n" +
                "8 8 8 8 8 8 8 _ _ 7 7 7 7 7 7 _ _ 6 6 6 6 6 6 6 \n" +
                "8 8 8 8 8 8 # _ # 7 7 7 7 7 7 # _ 6 6 6 6 6 6 6 \n",
                stringBoard
        );

    }
    
    @Test
    public void customFileReadTest() throws FileNotFoundException, InvalidSetupFileException
    {
        
        BoardConstructor bc = new BoardConstructor("customisation/board layout/one room.txt");
        String stringBoard = bc.createBoard().toString();
        assertEquals(
                "1 1 1 1 1 1 # _ \n" +
                "1 1 1 1 1 1 _ _ \n" +
                "1 1 1 1 1 1 _ _ \n" +
                "1 1 1 1 1 1 _ _ \n" +
                "1 1 1 1 1 1 _ _ \n" +
                "# 1 1 1 []1 _ _ \n" +
                "_ _ _ _ _ _ _ _ \n" +
                "# _ _ _ _ _ _ _ \n",
                stringBoard
        );
        
        bc = new BoardConstructor("customisation/board layout/example.txt");
        stringBoard = bc.createBoard().toString();
        assertEquals(
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ 2 2 2 2 2 2 2 _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ []2 []_ _ _ 2 _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ 2 2 2 _ _ _ 2 _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ 2 2 2 2 []2 _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ 2 2 []2 2 _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ 2 2 _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ 2 2 _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ 2 2 _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ # _ _ _ _ 2 2 _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ 1 1 []1 _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ 1 1 1 1 _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ 1 1 1 1 _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ 1 1 1 1 _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n" +
                "_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ \n",
                stringBoard
        );
    }
}
