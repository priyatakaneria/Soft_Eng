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
    
    private final Image spanner = new Image(getClass().getResourceAsStream("Spanner.png"));
    private final Image dagger = new Image(getClass().getResourceAsStream("Dagger.png"));;
    private final Image candlestick = new Image(getClass().getResourceAsStream("Candlestick.png"));;
    private final Image revolver = new Image(getClass().getResourceAsStream("Revolver.png"));;
    private final Image leadPiping = new Image(getClass().getResourceAsStream("Lead Piping.png"));;
    private final Image rope = new Image(getClass().getResourceAsStream("Rope.png"));;
    
    
    
    public WeaponPiece(Weapon weapon)
    {
        
    }
}
