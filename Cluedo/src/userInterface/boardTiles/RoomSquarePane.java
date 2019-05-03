/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.RoomSquare;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author sb816
 */
public class RoomSquarePane {
    private RoomSquare logicalRoomSquare; 
    public RoomSquarePane(RoomSquare logicalRoomSquare)
    {
        Rectangle tileShape = new Rectangle(25, 25);
        tileShape.setFill(Color.GREEN);
        tileShape.setStroke(Color.BLACK);
        
    }
}
