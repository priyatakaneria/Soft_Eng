/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.RoomSquare;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author sb816
 */
public class RoomSquareDoorPane extends RoomSquarePane{
    public RoomSquareDoorPane(RoomSquare rs)
    {
        super(rs);
        tileShape.setFill(Color.WHITE);
        tileShape.setStroke(Color.BLACK);
        
    }
    
}
