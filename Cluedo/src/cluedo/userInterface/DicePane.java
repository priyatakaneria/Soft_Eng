/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Jamie Thelin
 */
public class DicePane extends Pane
{
    private int lastRoll;
    private ImageView diceImg;
    private Image one;
    private Image two;
    private Image three;
    private Image four;
    private Image five;
    private Image six;
    
    
    public DicePane()
    {
        lastRoll = 1;
        
        one = new Image(getClass().getResourceAsStream("die1.png"));
        two = new Image(getClass().getResourceAsStream("die2.png"));
        three = new Image(getClass().getResourceAsStream("die3.png"));
        four = new Image(getClass().getResourceAsStream("die4.png"));
        five = new Image(getClass().getResourceAsStream("die5.png"));
        six = new Image(getClass().getResourceAsStream("die6.png"));
        diceImg = new ImageView(one);
        getChildren().add(diceImg);
        diceImg.setFitWidth(64);
        diceImg.setPreserveRatio(true);
        diceImg.setSmooth(true);
        diceImg.setCache(true);
    }

    public void setLastRoll(int lastRoll)
    {
        this.lastRoll = lastRoll;
        display();
    }
    
    public void display()
    {
        if (lastRoll == 1)
        {
            diceImg.setImage(one);
        } //
        else if (lastRoll == 2)
        {
            diceImg.setImage(two);
        } //
        else if (lastRoll == 3)
        {
            diceImg.setImage(three);
        } //
        else if (lastRoll == 4)
        {
            diceImg.setImage(four);
        } //
        else if (lastRoll == 5)
        {
            diceImg.setImage(five);
        } //
        else if (lastRoll == 6)
        {
            diceImg.setImage(six);
        }
    }
}
