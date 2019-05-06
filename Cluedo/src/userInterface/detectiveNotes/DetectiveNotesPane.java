/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.detectiveNotes;

import cluedo.gameLogic.player.DetectiveNotes;
import cluedo.gameLogic.player.Player;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.DetNoteType;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

/**
 *
 * @author Jamie
 */
public class DetectiveNotesPane extends StackPane
{

    // logical
    private DetectiveNotes logicalDetectiveNotes;
    private Player player;
    // layout
    private VBox mainContainer;
    private Label title;
    private HBox horizontalDivider;
    // right hand side
    private VBox rightBox;
    private Text writtenNotes;
    private TextField newNotes;
    // left hand side
    private StackPane leftBox;
    private GridPane clueGrid;

    public DetectiveNotesPane(Player player)
    {
        this.logicalDetectiveNotes = player.getDetNotes();
        this.player = player;

        // main container
        mainContainer = new VBox();
        mainContainer.setMaxWidth(500);
        
        // title 
        title = new Label(player.getPlayerName() + "'s Detective Notes");
        title.setAlignment(Pos.CENTER);
        title.setTextAlignment(TextAlignment.CENTER);
        mainContainer.getChildren().add(title);
        // horizontal divider
        horizontalDivider = new HBox();
        mainContainer.getChildren().add(horizontalDivider);
        rightBox = new VBox();
        leftBox = new StackPane();
        horizontalDivider.getChildren().addAll(leftBox, rightBox);

        /**
         * Right side
         */
        // written notes
        writtenNotes = new Text("Notes:\n" + this.logicalDetectiveNotes.getNotes());
        writtenNotes.setTextAlignment(TextAlignment.LEFT);
        StackPane.setMargin(writtenNotes, new Insets(10, 10, 10, 10));
        // textField
        newNotes = new TextField("Write notes and press enter:");
        newNotes.setOnAction(enter ->
        {
            this.logicalDetectiveNotes.writeNotes(newNotes.getText());
            writtenNotes.setText("Notes:\n" + this.logicalDetectiveNotes.getNotes());
            newNotes.clear();
        });
        StackPane.setMargin(newNotes, new Insets(10, 10, 10, 10));
        rightBox.getChildren().addAll(writtenNotes, newNotes);

        /**
         * Left side
         */
        clueGrid = createClueTable();
        leftBox.getChildren().add(clueGrid);
        
        getChildren().add(mainContainer);
    }

    private GridPane createClueTable()
    {
        ArrayList<ClueType> clueTableClues = new ArrayList<>();
        GridPane clueGrid = new GridPane();
        int row = 1;
        int column = 0;
        clueTableClues.add(null);
        clueGrid.add(new Label("Characters:"), column, row);
        row++;
        for (Character c : Character.values())
        {
            clueTableClues.add(c);
            clueGrid.add(new Label("  " + c.getCharacterName()), column, row);
            row++;
        }
        clueTableClues.add(null);
        clueGrid.add(new Label("Rooms:"), column, row);
        row++;
        for (Room r : player.getGameBoard().getRooms().values())
        {
            clueTableClues.add(r);
            clueGrid.add(new Label("  " + r.getRoomName()), column, row);
            row++;
        }
        clueTableClues.add(null);
        clueGrid.add(new Label("Weapons:"), column, row);
        row++;
        for (Weapon w : Weapon.values())
        {
            clueTableClues.add(w);
            clueGrid.add(new Label("  " + w.getWeaponName()), column, row);
            row++;
        }

        column = 1;
        row = 1;

        Label playerLabel = new Label(player.getPlayerName());
        playerLabel.getTransforms().add(new Rotate(-90));
        clueGrid.add(playerLabel, column, row);
        for (ClueType ct : clueTableClues)
        {
            if (ct != null)
            {
                DetTypeImg detImage = new DetTypeImg(ct, player, logicalDetectiveNotes);
                clueGrid.add(detImage, column, row);
                for (ClueCard cc : player.getClueHand())
                {
                    if (cc.getClueType() == ct)
                    {
                        detImage.toggle();
                        detImage.toggle();
                    }
                }
            }
            row++;
        }
        column++;
        for (Player p : player.getOtherPlayers())
        {
            row = 0;
            if (p != player)
            {
                playerLabel = new Label(p.getPlayerName());
                playerLabel.getTransforms().add(new Rotate(-90));
                clueGrid.add(playerLabel, column, row);
                row++;
                for (ClueType ct : clueTableClues)
                {
                    if (ct != null)
                    {
                        DetTypeImg detImage = new DetTypeImg(ct, p, logicalDetectiveNotes);
                        clueGrid.add(detImage, column, row);
                    }
                    row++;
                }
            }
            column++;
        }

        return clueGrid;
    }
}
