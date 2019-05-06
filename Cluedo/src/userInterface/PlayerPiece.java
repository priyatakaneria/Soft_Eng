/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import cluedo.gameLogic.player.Player;
import cluedo.gameLogic.Character;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author Jamie Thelin
 */
public class PlayerPiece extends Circle
{
    private Player player;
    
    public PlayerPiece(Player player)
    {
        super(12.5);
        this.player = player;
        Character c = player.getCharacter();
        if (c == Character.ColMustard)
        {
            setFill(Color.GOLD);
        } //
        else if (c == Character.MissScarlett)
        {
            setFill(Color.CRIMSON);
        } //
        else if (c == Character.MrsPeacock)
        {
            setFill(Color.MEDIUMBLUE);
        } //
        else if (c == Character.MrsWhite)
        {
            setFill(Color.GHOSTWHITE);
        } //
        else if (c == Character.ProfPlum)
        {
            setFill(Color.DARKMAGENTA);
        } //
        else if (c == Character.RevGreen)
        {
            setFill(Color.GREEN);
        } //
        setStroke(Color.BLACK);
    }
    
    
}
