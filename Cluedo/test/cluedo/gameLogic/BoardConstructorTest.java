/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.BoardConstructor;
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

    @Test
    public void fileReadTest()
    {
        try
        {
            BoardConstructor bc = new BoardConstructor();
            bc.fileInput.readLine();
            System.out.println(bc.fileInput.readLine());
        }
        catch(Exception e)
        {
            System.out.println("error: "+e);
        }
    }
}
