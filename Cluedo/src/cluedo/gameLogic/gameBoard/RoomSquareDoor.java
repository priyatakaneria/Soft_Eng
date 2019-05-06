/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import userInterface.boardTiles.RoomSquareDoorPane;

/**
 *
 * @author Jamie
 */
public class RoomSquareDoor extends RoomSquare
{

    public RoomSquareDoor()
    {
        super(-1);
        setGuiPane(new RoomSquareDoorPane(this));
    }

    @Override
    public String toString()
    {
        return "[]";
    }
}
