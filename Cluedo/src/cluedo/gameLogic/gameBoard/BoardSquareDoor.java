/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import userInterface.boardTiles.BoardSquarePane;

/**
 *
 * @author Jamie
 */
public class BoardSquareDoor extends BoardSquare
{

    public BoardSquareDoor(boolean isIntrigue)
    {
        super(isIntrigue);
        setGuiPane(new BoardSquarePane(this));
    }
}
