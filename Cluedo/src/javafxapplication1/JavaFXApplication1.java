/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author sb816
 */
public class JavaFXApplication1 extends Application {
    
    private Pane root = new Pane();
    private Scene scene1, scene2;
    private String time;
    private ArrayList<String> playerNames = new ArrayList<String>();
    private AnimationTimer timer;
    private Label lblTime = new Label("0 .s");
    private int seconds, multiplayer;
    private MediaPlayer mediaPlayer;
    
    @Override
    public void start(Stage primaryStage){        
        //Media media = new Media("file://CLUE.mp3"); //replace /Movies/test.mp3 with your file
        //MediaPlayer player = new MediaPlayer(media); 
        //player.setAutoPlay(true);
        //player.play();
        
        //String path = Test.class.getResource("/Kalimba.mp3").toString();
        //Media media = new Media(pa);
        //MediaPlayer mp = new MediaPlayer(media);
        //mp.play();
        
        final URL resource = getClass().getResource("CLUE.mp3");
        final Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
                
        Button button1, button3, button4;
        Stage window = primaryStage;        
     
        Label label1 = new Label("Number of Players:");
        final String[] players = new String[]{"1", "2", "3", "4", "5", "6"};
        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6"));
        //TextField sizeInput = new TextField();
        //Label label2 = new Label("Difficulty:");
        //ToggleButton r = new RadioButton("Easy");
        //ToggleButton a = new RadioButton("Medium");
        //ToggleButton d = new RadioButton("Hard");
        //final ToggleGroup tg = new ToggleGroup();
        //r.setToggleGroup(tg);
        //r.setSelected(true);
        //a.setToggleGroup(tg);
        //d.setToggleGroup(tg);        
        
        //tg.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
        //@Override
        //    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
        //        RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
        //        difficulty = chk.getText();
        //    }
        //});
              
        button1 = new Button("Start game");
        button4 = new Button("Exit menu");
        button1.setOnAction(e -> setStage(window));
        button4.setOnAction(e -> window.close());
        
        //label1, sizeInput, label2, r, a, d, 
        
        Pane layout1 = new Pane();
        button1.setLayoutX(218);
        button1.setLayoutY(551);
        button4.setLayoutX(432);
        label1.setLayoutX(167);
        label1.setLayoutY(480);             
        label1.setTextFill(Color.web("FFFFFF"));
        label1.setFont(new Font("Calibri", 15));
        cb.setLayoutX(297);
        cb.setLayoutY(478);
        
        cb.getSelectionModel().selectedIndexProperty().addListener(new 
            ChangeListener<Number>(){
                public void changed(ObservableValue ov, Number value, Number new_value){
                    multiplayer = Integer.parseInt(players[new_value.intValue()]);
                    System.out.println(multiplayer);
                }
            });
        
        //layout1.setAlignment(button1, Pos.CENTER);
        //layout1.setAlignment(button4, Pos.BOTTOM_CENTER);
        layout1.getChildren().addAll(label1, cb, button1, button4);
        Image image = new Image(getClass().getResourceAsStream("Cluedo-HomepageTest.png"),500,650,false,true);
        BackgroundImage myBI= new BackgroundImage(image,BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
        BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        //then you set to your node
        layout1.setBackground(new Background(myBI));           
        scene1 = new Scene(layout1, 500, 650);       
        window.setScene(scene1);
        window.setTitle("Cluedo");        
        window.show();            
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void setStage(Stage window){
        createContent(window);
        window.setScene(scene2);  
        for(int x=1; x<=multiplayer; x++){
            setPlayerNames(x);
        }        
        System.out.println(playerNames);
    }
    
    public void createContent(Stage primaryStage){
      
        timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime != 0) {
                    if (now > lastTime + 1_000_000_000) {
                        seconds++;
                        time = (Integer.toString(seconds));
                        lastTime = now;
                    }
                } else {
                    lastTime = now;

                }
            }

            @Override
            public void stop() {
                super.stop();
                lastTime = 0;
                seconds = 0;
            }
        };
        
        timer.start();
       
        int numFill = 0;
        Stage window = primaryStage;        
        Button button2 = new Button("Exit Game");
        button2.setOnAction(e -> {mediaPlayer.pause(); window.setScene(scene1); window.close(); Platform.runLater(() -> new JavaFXApplication1().start(new Stage()));});
     
        int screenSizeX = (50);
        int screenSizeY = (50+35);
        
        button2.setLayoutX(20);
        button2.setLayoutY(screenSizeX+5);
        root.getChildren().addAll(button2);
        
        scene2 = new Scene(root, screenSizeX, screenSizeY);
    }
    
    public void setPlayerNames(int playerNumber){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Player " + playerNumber + " name");
        dialog.setHeaderText("Please enter the name of Player " + playerNumber);
        dialog.setContentText("Player " + playerNumber + ":");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            String playerName = result.get();
            playerNames.add(playerName);
        }
    }
}

