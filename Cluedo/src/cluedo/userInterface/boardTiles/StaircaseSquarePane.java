/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.StaircaseSquare;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author sb816
 */
public class StaircaseSquarePane extends BoardSpacePane
{
    public StaircaseSquarePane(StaircaseSquare ss)
    {
        super(ss);
        Rectangle tileShape = new Rectangle(25, 25);
        setStdFill(Color.DARKGREY);
        setStdStroke(Color.DARKGRAY);
        setNormalColours();
        getChildren().add(getTileShape());
    }
}
