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
public class RoomSquarePane extends StackPane{

    private RoomSquare logicalRoomSquare;
    protected Rectangle tileShape;

    public RoomSquarePane(RoomSquare logicalRoomSquare) {
        this.logicalRoomSquare = logicalRoomSquare;
        tileShape = new Rectangle(25, 25);
        colorRoom();
        
    }

    public void colorRoom() {
        int roomNum = logicalRoomSquare.getRoomNo();
        switch (roomNum) {
            case (1):
                tileShape.setFill(Color.GREEN);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (2):
                tileShape.setFill(Color.BEIGE);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (3):
                tileShape.setFill(Color.AQUAMARINE);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (4):
                tileShape.setFill(Color.HOTPINK);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (5):
                tileShape.setFill(Color.LIMEGREEN);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (6):
                tileShape.setFill(Color.HONEYDEW);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (7):
                tileShape.setFill(Color.INDIANRED);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (8):
                tileShape.setFill(Color.MEDIUMBLUE);
                tileShape.setStroke(Color.TRANSPARENT);
                break;
            case (9):
                tileShape.setFill(Color.BURLYWOOD);
                tileShape.setStroke(Color.TRANSPARENT);
                break;

        }
        
    }

}
