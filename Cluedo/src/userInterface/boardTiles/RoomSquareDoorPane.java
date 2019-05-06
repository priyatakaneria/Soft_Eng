/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.RoomSquare;
import cluedo.gameLogic.gameBoard.RoomSquareDoor;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import userInterface.PlayerPiece;

/**
 *
 * @author sb816
 */
public class RoomSquareDoorPane extends RoomSquarePane{
    public RoomSquareDoorPane(RoomSquareDoor rsd)
    {
        super(rsd);
        setStdFill(Color.WHITE);
        setStdStroke(Color.BLACK);
        setNormalColours();
    }
    
    public void addPlayer(PlayerPiece pp)
    {
        getChildren().add(pp);
    }
    
    public boolean removePlayer(PlayerPiece pp)
    {
        return getChildren().remove(pp);
    }
    
}
