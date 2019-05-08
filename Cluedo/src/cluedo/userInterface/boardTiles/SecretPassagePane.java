/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.SecretPassage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author sb816
 */
public class SecretPassagePane extends BoardSpacePane
{
    public SecretPassagePane(SecretPassage sp)
    {   
        super(sp);
        setStdFill(Color.GREY);
        setStdStroke(Color.BLACK);
        setNormalColours();
        getChildren().add(getTileShape());
    }

}
