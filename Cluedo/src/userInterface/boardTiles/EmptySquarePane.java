/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.boardTiles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author sb816
 */
public class EmptySquarePane {
    public EmptySquarePane()
    {
        //May Produce an error on Null
        Rectangle tileShape = new Rectangle(25, 25);
        tileShape.setFill(null);
        tileShape.setStroke(null);
        
    }
}
