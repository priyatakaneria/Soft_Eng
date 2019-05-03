/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.boardTiles;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author sb816
 */
public class RoomPane extends StackPane {

    private Text text = new Text();

    public RoomPane(int x, int y, String roomName, String colour, String textColour, int fontSize) {
        int width = (x * 25);
        int height = (y * 25);
        Rectangle border = new Rectangle(width, height);
        border.setFill(null);
        border.setStroke(Color.BLACK);

        text.setFont(Font.font("Jokerman", fontSize));

        switch (textColour) {
            case "black":
                text.setFill(Color.BLACK);
                break;
            case "white":
                text.setFill(Color.WHITE);
                break;
        }

        setAlignment(Pos.CENTER);
        getChildren().addAll(border, text);

        switch (colour) {
            case "red":
                border.setFill(Color.RED);
                break;
            case "crimson":
                border.setFill(Color.CRIMSON);
                break;
            case "blue":
                border.setFill(Color.CADETBLUE);
                break;
            case "green":
                border.setFill(Color.YELLOWGREEN);
                break;
            case "orange":
                border.setFill(Color.ORANGE);
                break;
            case "purple":
                border.setFill(Color.MAGENTA);
                break;
            case "pink":
                border.setFill(Color.PINK);
                break;
            case "grey":
                border.setFill(Color.GREY);
                break;
            case "brown":
                border.setFill(Color.BROWN);
                break;
            case "black":
                border.setFill(Color.BLACK);
                break;
        }
        //border.setFill(Color.BLACK);
        text.setText(roomName);

        setOnMouseClicked(event -> {});
    }
    
}
