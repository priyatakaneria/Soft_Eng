/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.detectiveNotes;

import cluedo.gameLogic.ClueType;
import cluedo.gameLogic.player.DetNoteType;
import cluedo.gameLogic.player.DetectiveNotes;
import cluedo.gameLogic.player.Player;
import userInterface.Game;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Jamie Thelin
 */
public class DetTypeImg extends Pane
{
    private ClueType clue;
    private Player player;
    private DetectiveNotes notesTable;
    private DetNoteType noteType;
    private ImageView iv;

    public DetTypeImg(ClueType clue, Player player, DetectiveNotes notesTable)
    {
        this.clue = clue;
        this.player = player;
        this.notesTable = notesTable;
        this.noteType = DetNoteType.noKnowledge;
        
        iv = new ImageView();
        iv.setImage(new Image("/src/userInterface/detectiveNotes/noKnowledge.png"));
        iv.setFitWidth(32);
        iv.setPreserveRatio(true);
        iv.setSmooth(true);
        iv.setCache(true);

        getChildren().add(iv);
        
        
        setOnMouseClicked(click -> toggle());
    }

    public void toggle()
    {
        if (noteType == DetNoteType.noKnowledge)
        {
            notesTable.markTable(player, clue, DetNoteType.mightHaveClue);
            iv.setImage(new Image("/src/userInterface/detectiveNotes/mightHaveClue.png"));
        } //
        else if (noteType == DetNoteType.mightHaveClue)
        {
            notesTable.markTable(player, clue, DetNoteType.hasClue);
            iv.setImage(new Image("/src/userInterface/detectiveNotes/hasClue.png"));
        } //
        else if (noteType == DetNoteType.hasClue)
        {
            notesTable.markTable(player, clue, DetNoteType.doesntHaveClue);
            iv.setImage(new Image("/src/userInterface/detectiveNotes/doesn'tHaveClue.png"));
        } //
        else if (noteType == DetNoteType.doesntHaveClue)
        {
            notesTable.markTable(player, clue, DetNoteType.noKnowledge);
            iv.setImage(new Image("/src/userInterface/detectiveNotes/noKnowledge.png"));
        } //
        noteType = notesTable.checkClue(player, clue);
    }
}
