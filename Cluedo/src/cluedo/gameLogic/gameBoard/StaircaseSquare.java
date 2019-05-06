/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import userInterface.boardTiles.StaircaseSquarePane;

/**
 *
 * @author Jamie
 */
public class StaircaseSquare extends EmptySquare
{

    public StaircaseSquare()
    {
        super();
        setGuiPane(new StaircaseSquarePane(this));
    }

    /**
     * @return An '/' character to represent a square in the staircase space.
     */
    @Override
    public String toString()
    {
        return "/ ";
    }
}
