/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import cluedo.gameLogic.ClueCard;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Jamie Thelin
 */
public class CardPane extends BorderPane
{
    private ClueCard clue;
    public CardPane(ClueCard cc, Size size)
    {
        clue = cc;
        
        Label leftText = new Label(clue.getClueType().toString());
        leftText.setTextAlignment(TextAlignment.LEFT);
        leftText.getTransforms().add(new Rotate(-90));
        Label rightText = new Label(clue.getClueType().toString());
        rightText.setTextAlignment(TextAlignment.RIGHT);
        leftText.getTransforms().add(new Rotate(90));
        
        setLeft(leftText);
        setRight(rightText);
        setCenter(null);
        
        setWidth(size.getWidth());
        setHeight(size.getHeight());

        setBackground(Game.createSolidBackground(Color.CORNSILK));
        setBorder(Game.SOLID_BLACK_BORDER);
    }
    
    public enum Size
    {
        large(150, 225), small(100, 150);
        
        private int width;
        private int height;
        
        private Size(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }           
    }
    
}
