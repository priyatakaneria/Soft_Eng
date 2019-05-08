/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.RoomSquare;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import cluedo.userInterface.PlayerPiece;

/**
 *
 * @author sb816
 */
public class RoomSquarePane extends BoardSpacePane
{
    
    

    public RoomSquarePane(RoomSquare logicalRoomSquare)
    {
        super(logicalRoomSquare);
        colorRoom();
        getChildren().add(getTileShape());
    }

    public void colorRoom()
    {
        int roomNum = ((RoomSquare) getLogicalBoardSpace()).getRoomNo();
        switch (roomNum)
        {
            case (1):
                setStdFill(Color.GREEN);
                setStdStroke(Color.GREEN);
                break;
            case (2):
                setStdFill(Color.BEIGE);
                setStdStroke(Color.BEIGE);
                break;
            case (3):
                setStdFill(Color.AQUAMARINE);
                setStdStroke(Color.AQUAMARINE);
                break;
            case (4):
                setStdFill(Color.HOTPINK);
                setStdStroke(Color.HOTPINK);
                break;
            case (5):
                setStdFill(Color.LIMEGREEN);
                setStdStroke(Color.LIMEGREEN);
                break;
            case (6):
                setStdFill(Color.CRIMSON);
                setStdStroke(Color.CRIMSON);
                break;
            case (7):
                setStdFill(Color.INDIANRED);
                setStdStroke(Color.INDIANRED);
                break;
            case (8):
                setStdFill(Color.MEDIUMBLUE);
                setStdStroke(Color.MEDIUMBLUE);
                break;
            case (9):
                setStdFill(Color.BURLYWOOD);
                setStdStroke(Color.BURLYWOOD);
                break;
        }
        setNormalColours();
    }
}
