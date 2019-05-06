/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.player.Player;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import userInterface.detectiveNotes.DetectiveNotesPane;

/**
 *
 * @author Jamie Thelin
 */
public class PlayerWindow extends Stage
{
    private Player player;
    private Scene mainScene;
    private Scene responseScene;
    private Pane root;
    private FlowPane cardHand;
    
    public PlayerWindow(Player p)
    {
        super();
        player = p;
        int width = 500;
        int height = 750;
        setResizable(false);
        setWidth(width);
        //setHeight(height);
        
        //Main Scene
        root = new VBox();
        root.setMaxWidth(width);
        //root.setMaxHeight(height);
        
        setTitle(player.getPlayerName()+"'s Detective Notes");
        Label title = new Label(player.getCharacter().getCharacterName());
        title.setTextAlignment(TextAlignment.LEFT);
        
        cardHand = new FlowPane();
        for (ClueCard cc : player.getClueHand())
        {
            cardHand.getChildren().add(new CardPane(cc, CardPane.Size.small));
        }
        cardHand.setHgap(10);
        cardHand.setVgap(10);
        
        root.getChildren().addAll(cardHand, new DetectiveNotesPane(player));
        
        mainScene = new Scene(root);
        setScene(mainScene);
        // Response Scene
        HBox responseRoot = new HBox();
        responseRoot.setAlignment(Pos.CENTER);
    }
}