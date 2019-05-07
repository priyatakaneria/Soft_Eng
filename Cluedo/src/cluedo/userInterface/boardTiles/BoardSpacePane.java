/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface.boardTiles;

import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.player.Player;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import cluedo.userInterface.PlayerPiece;

/**
 *
 * @author Jamie Thelin
 */
public abstract class BoardSpacePane extends StackPane
{
    private BoardSpace logicalBoardSpace;
    private Color stdFill;
    private Color stdStroke;
    private Rectangle tileShape;
    
    private PlayerPiece player;
    
    
    private Player occupant;
    
    
    public BoardSpacePane(BoardSpace bs)
    {
        logicalBoardSpace = bs;
        tileShape = new Rectangle(25, 25);
        logicalBoardSpace.setGuiPane(this);
    }

    public Color getStdFill()
    {
        return stdFill;
    }

    public void setStdFill(Color stdFill)
    {
        this.stdFill = stdFill;
    }

    public Color getStdStroke()
    {
        return stdStroke;
    }

    public void setStdStroke(Color stdStroke)
    {
        this.stdStroke = stdStroke;
    }
    
    public void setTmpColours(Color fill, Color stroke)
    {
        tileShape.setFill(fill);
        tileShape.setStroke(stroke);
    }
    
    public void setNormalColours()
    {
        tileShape.setFill(getStdFill());
        tileShape.setStroke(getStdStroke());
    }

    public BoardSpace getLogicalBoardSpace()
    {
        return logicalBoardSpace;
    }
    
    public Rectangle getTileShape()
    {
        return tileShape;
    }
    
    public void addPlayer(PlayerPiece pp)
    {
        getChildren().add(pp);
        pp.setVisible(true);
        pp.toFront();
    }

    public boolean removePlayer(PlayerPiece pp)
    {
        return getChildren().remove(pp);
    }
}
