package cluedo.userInterface;

import cluedo.gameLogic.Weapon;
import cluedo.userInterface.boardTiles.BoardSpacePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Used to display the weapons being placed in the rooms due to a suggestion
 * 
 * @author Jamie Thelin
 */
public class WeaponPiece extends Pane
{
    private BoardSpacePane guiLocation;
    private Weapon weapon;
    private ImageView iv;
    
    private Image spanner;
    private Image dagger;
    private Image candlestick;
    private Image revolver;
    private Image leadPiping;
    private Image rope;
    
    
    
    public WeaponPiece(Weapon weapon)
    {
        
    }
}
