/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import cluedo.gameLogic.Accusation;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.player.Player;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import javafx.geometry.Rectangle2D;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import userInterface.boardTiles.RoomPane;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.TurnManager;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.EmptySquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.RoomSquare;
import cluedo.gameLogic.gameBoard.RoomSquareDoor;
import cluedo.gameLogic.gameBoard.SecretPassage;
import cluedo.gameLogic.gameBoard.StaircaseSquare;
import java.util.HashMap;
import javafx.scene.control.Alert.AlertType;
import userInterface.boardTiles.BoardSquarePane;
import userInterface.boardTiles.EmptySquarePane;
import userInterface.boardTiles.RoomSquareDoorPane;
import userInterface.boardTiles.RoomSquarePane;
import userInterface.boardTiles.SecretPassagePane;
import userInterface.boardTiles.StaircaseSquarePane;

/**
 *
 * @author sb816
 */
public class Game extends Application
{

    private Pane root = new Pane();
    private Pane root2 = new Pane();
    private Pane root3 = new Pane();
    private Scene scene1, scene2, scene3, scene4;
    private String time, winner;

    private ArrayList<String> playerNames = new ArrayList<String>();
    // this need to be a map between instances of character and player names ~ Sriram and Jamie
    private HashMap<Character, String> characterPlayerMap;
    //this needs to be the number of ai players ~ Sriram and Jamie
    private int noAiPlayers;
    //This might not be a string but whichever way you are doing the file selection.
    private String customBoardFileName;
    private AnimationTimer timer;
    private Label lblTime = new Label("0 .s");
    private int seconds, multiplayer = 0;
    private MediaPlayer mediaPlayer;

    private ArrayList<ArrayList<StackPane>> gameBoardPanes;

    @Override
    public void start(Stage primaryStage)
    {
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
        final String[] players = new String[]
        {
            "1", "2", "3", "4", "5", "6"
        };
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
        button1.setOnAction(e -> setCharactersPage(window));
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

        cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
        {
            public void changed(ObservableValue ov, Number value, Number new_value)
            {
                multiplayer = Integer.parseInt(players[new_value.intValue()]);
                System.out.println(multiplayer);
            }
        });

        //layout1.setAlignment(button1, Pos.CENTER);
        //layout1.setAlignment(button4, Pos.BOTTOM_CENTER);
        layout1.getChildren().addAll(label1, cb, button1, button4);
        Image image = new Image(getClass().getResourceAsStream("Cluedo-HomepageTest.png"), 500, 650, false, true);
        BackgroundImage myBI = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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
    public static void main(String[] args)
    {
        launch(args);
    }

    public void displayTurn(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int rollDice()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BoardSpace chooseSpace(HashSet<BoardSpace> availableMoves)
    {
        // find all BoardSpace's corresponding GUI pane and 
//        for (BoardSpace bs : availableMoves)
//        {
//            bs.setOnMouseClicked(e ->
//            {
//                return bs;
//            });
//        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public BoardSpace teleport()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean endTurn()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Suggestion makeSuggestion(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void noPlayerClues()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ClueCard chooseResponse(Player nextEnquiry, ArrayList<ClueCard> possibleClues)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void showClue(ClueCard response, Player player, Player clueGiver)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean accusationQuery()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Accusation makeAccusation(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void displayExtraTurn(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void winningsPage(Player currPlayer)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Tile extends StackPane
    {

        private Text text = new Text();

        public Tile(String colour, int startOrDoor)
        {
            Rectangle border = new Rectangle(25, 25);
            border.setFill(null);
            border.setStroke(Color.BLACK);
            setAlignment(Pos.CENTER);
            getChildren().addAll(border);

            switch (colour)
            {
                case "yellow":
                    border.setFill(Color.YELLOW);
                    break;
                case "beige":
                    border.setFill(Color.BEIGE);
                    break;
            }

            if (startOrDoor == 1)
            {
                text.setText("START");
                text.setFont(Font.font("Calibri", 8));
            }
            if (startOrDoor == 2)
            {
                text.setText("DOOR");
                text.setFont(Font.font("Calibri", 8));
            }

            getChildren().add(text);

            setOnMouseClicked(event ->
            {

            });
        }
    }

    public void setGameboard(Stage window)
    {     
        /*
        TurnManager turnManager;
        try
        {
            turnManager = new TurnManager(characterPlayerMap, noAiPlayers, customBoardFileName, this);
            createGameboard(window, turnManager.getGameBoard());
            window.setTitle("Cluedo");
            window.setScene(scene2);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        } //
        catch (InvalidSetupFileException e)
        {
            //Alert(Alert.AlertType AlertType, String("Invalid Setup File!"));
            //jOptionPane.showMessageDialog(null, "Invalid Setup File!");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Invalid Setup File");
            alert.setTitle("Error");
            alert.showAndWait();

        }
        */
        
        createGameboard(window);
        window.setTitle("Cluedo");
        window.setScene(scene2);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    public void setWinningsPage(Stage window)
    {
        createWinningsPage(window);
        window.setTitle("Winner!!");
        window.setScene(scene3);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }
    
    public void setCharactersPage(Stage window){
        
        for (int x = 1; x <= multiplayer; x++)
        {
            setPlayerNames(x);
        }
        System.out.println(playerNames);
        
        if (multiplayer == 0)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("No players selected");
            alert.setContentText("You must select the number of players in this game.");

            alert.showAndWait();
        } 
        
        else
        { 
            createCharacterPage(window);
            window.setTitle("Please choose a character");
            window.setScene(scene4);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
        }
    }

    public void createCharacterPage(Stage primaryStage){
        
        Stage window = primaryStage;
        Button save = new Button("Save");
        save.setOnAction(e ->
        {
            setGameboard(primaryStage);  
        });
        
        Rectangle selectCharacter = new Rectangle(50, 50);
        selectCharacter.setFill(null);
        selectCharacter.setTranslateX(50);
        selectCharacter.setTranslateY(50);
        // t.setFont(100);

        // setting background image 
        Image image = new Image(getClass().getResourceAsStream("characters.jpg"), 600, 600, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root3.setBackground(new Background(backgroundImage));

        int screenSizeX = (600);
        int screenSizeY = (600);

        save.setLayoutX(500);
        save.setLayoutY(550);
        root3.getChildren().addAll(selectCharacter, save);

        scene4 = new Scene(root3, screenSizeX, screenSizeY);
        
    }
    
    public void createGameboard(Stage primaryStage)
    {
        Stage window = primaryStage;
        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e ->
        {
            mediaPlayer.pause();
            window.setScene(scene1);
            window.close();
            Platform.runLater(() -> new Game().start(new Stage()));
        });
        
        /* 
        gameBoardPanes = new ArrayList<>();

        //x coord
        for (int x = 1; x < gb.getWidth(); x++)
        {
            gameBoardPanes.add(new ArrayList<>());
            for (int y = 1; y < gb.getHeight(); y++)
            {
                //Make GUI Tiles in Here
                StackPane newPane = null;
                BoardSpace nextBoardSpace = gb.getBoardSpace(x, y);
                if (nextBoardSpace instanceof BoardSquare)
                {
                    newPane = new BoardSquarePane();
                } //
                else if (nextBoardSpace instanceof EmptySquare)
                {
                    newPane = new EmptySquarePane();
                } //
                else if (nextBoardSpace instanceof StaircaseSquare)
                {
                    newPane = new StaircaseSquarePane();
                } //
                else if (nextBoardSpace instanceof SecretPassage)
                {
                    newPane = new SecretPassagePane();
                } //
                else if (nextBoardSpace instanceof RoomSquare)
                {
                    newPane = new RoomSquarePane((RoomSquare) nextBoardSpace);
                } //
                else if (nextBoardSpace instanceof RoomSquareDoor)
                {
                    newPane = new RoomSquareDoorPane((RoomSquare) nextBoardSpace);
                }
                gameBoardPanes.get(x - 1).add(newPane);
            }
        }
        */
        
        Button winningsPage = new Button("Winnings Page");
        winningsPage.setOnAction(e -> setWinningsPage(primaryStage));
        winningsPage.setLayoutX(425);
        winningsPage.setLayoutY(600);
        
        exitButton.setLayoutX(525);
        exitButton.setLayoutY(600);
        root.getChildren().addAll(exitButton, winningsPage );

        /*   timer = new AnimationTimer() {
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
        button2.setOnAction(e -> {mediaPlayer.pause(); window.setScene(scene1); window.close(); Platform.runLater(() -> new Game().start(new Stage()));});
        Button button3 = new Button("Winnings Page");
        button3.setOnAction(e -> setWinningsPage(primaryStage));
        
        Room study = new Room(7,4,"STUDY","red","black",18);  
        study.setTranslateX(0);
        study.setTranslateY(0);
        
        Room library = new Room(7,5,"LIBRARY","crimson","black",18);
        library.setTranslateX(0);
        library.setTranslateY(150);
        
        Room billiard = new Room(6,5,"BILLIARD ROOM","blue","black",18);
        billiard.setTranslateX(0);
        billiard.setTranslateY(300);
        
        Room conservatory = new Room(6,5,"CONSERVATORY","green","black",18);
        conservatory.setTranslateX(0);
        conservatory.setTranslateY(475);
        
        Room hall = new Room(6,7,"HALL","orange","black",18);
        hall.setTranslateX(225);
        hall.setTranslateY(0);
                
        Room cluedo = new Room(5,4,"CLUEDO","black","white",30);
        cluedo.setTranslateX(225);
        cluedo.setTranslateY(200);
        
        Room game = new Room(5,3,"The Classic Detective Game","black","white",11);
        game.setTranslateX(225);
        game.setTranslateY(300);
        
        Room ball = new Room(8,6,"BALL ROOM","purple","black",18);
        ball.setTranslateX(200);
        ball.setTranslateY(425);
        
        Room lounge = new Room(7,5,"LOUNGE","pink","black",18);
        lounge.setTranslateX(425);
        lounge.setTranslateY(0);
        
        Room dining = new Room(8,7,"DINING ROOM","grey","black",18);
        dining.setTranslateX(400);
        dining.setTranslateY(200);
        
        Room kitchen = new Room(6,6,"KITCHEN","brown","black",18);
        kitchen.setTranslateX(450);
        kitchen.setTranslateY(450);
        
        root.getChildren().addAll(study,library,billiard,conservatory,hall,cluedo,game,ball,lounge,dining,kitchen);
       
        int count = 0;
        for (int i=1; i<25; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(175);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }

        count = 25;
        for (int i=1; i<17; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(200);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }
        
        count = 275;
        for (int i=1; i<13; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(150);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }
        
        count = 25;
        for (int i=1; i<8; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(400);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }

        count = 25;
        for (int i=1; i<17; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(375);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }
        
        count = 175;
        for (int i=1; i<11; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(350);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }             
        
        count = 375;
        for (int i=1; i<10; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(400);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }
        
        count = 375;
        for (int i=1; i<9; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(425);
            tile.setTranslateY(count);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 25;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(100);
            count = count+25;
            root.getChildren().add(tile);
        }  
        
        count = 25;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(125);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 25;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(275);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 25;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(425);
            count = count+25;
            root.getChildren().add(tile);
        }     
        
        count = 25;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(450);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 225;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(375);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 225;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(400);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 425;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(125);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 425;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(150);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 425;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(175);
            count = count+25;
            root.getChildren().add(tile);
        }    
        
        count = 225;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(175);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 450;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(375);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 450;
        for (int i=1; i<6; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(400);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        count = 450;
        for (int i=1; i<7; i++){
            Tile tile = new Tile("yellow", 0);     
            tile.setTranslateX(count);
            tile.setTranslateY(425);
            count = count+25;
            root.getChildren().add(tile);
        }        
        
        Tile tile = new Tile("yellow", 1);
        tile.setTranslateX(400);
        tile.setTranslateY(0);
        
        Tile tile1 = new Tile("beige", 2);
        tile1.setTranslateX(150);
        tile1.setTranslateY(200);      
        
        Tile tile2 = new Tile("yellow", 1);
        tile2.setTranslateX(0);
        tile2.setTranslateY(125);
        
        Tile tile3 = new Tile("yellow", 1);
        tile3.setTranslateX(0);
        tile3.setTranslateY(450);
        
        Tile tile4 = new Tile("yellow", 1);
        tile4.setTranslateX(575);
        tile4.setTranslateY(150);
        
        Tile tile5 = new Tile("yellow", 0);
        tile5.setTranslateX(200);
        tile5.setTranslateY(575);
        
        Tile tile6 = new Tile("yellow", 0);
        tile6.setTranslateX(225);
        tile6.setTranslateY(575);
        
        Tile tile7 = new Tile("yellow", 1);
        tile7.setTranslateX(250);
        tile7.setTranslateY(600);
        
        Tile tile8 = new Tile("yellow", 1);
        tile8.setTranslateX(325);
        tile8.setTranslateY(600);
        
        Tile tile9 = new Tile("yellow", 0);
        tile9.setTranslateX(350);
        tile9.setTranslateY(575);
        
        Tile tile10 = new Tile("yellow", 0);
        tile10.setTranslateX(375);
        tile10.setTranslateY(575);
                
        Tile tile11 = new Tile("beige", 2);
        tile11.setTranslateX(150);
        tile11.setTranslateY(75);
        
        Tile tile12 = new Tile("beige", 2);
        tile12.setTranslateX(75);
        tile12.setTranslateY(250);
        
        Tile tile13 = new Tile("beige", 2);
        tile13.setTranslateX(125);
        tile13.setTranslateY(375);

        Tile tile14 = new Tile("beige", 2);
        tile14.setTranslateX(25);
        tile14.setTranslateY(300);
        
        Tile tile15 = new Tile("beige", 2);
        tile15.setTranslateX(100);
        tile15.setTranslateY(475);
        
        Tile tile16 = new Tile("beige", 2);
        tile16.setTranslateX(225);
        tile16.setTranslateY(100);
        
        Tile tile17 = new Tile("beige", 2);
        tile17.setTranslateX(200);
        tile17.setTranslateY(475);
        
        Tile tile18 = new Tile("beige", 2);
        tile18.setTranslateX(225);
        tile18.setTranslateY(425);
        
        Tile tile19 = new Tile("beige", 2);
        tile19.setTranslateX(350);
        tile19.setTranslateY(425);
        
        Tile tile20 = new Tile("beige", 2);
        tile20.setTranslateX(375);
        tile20.setTranslateY(475);
        
        Tile tile21 = new Tile("beige", 2);
        tile21.setTranslateX(425);
        tile21.setTranslateY(100);
        
        Tile tile24 = new Tile("beige", 2);
        tile24.setTranslateX(425);
        tile24.setTranslateY(200);
        
        Tile tile22 = new Tile("beige", 2);
        tile22.setTranslateX(400);
        tile22.setTranslateY(300);
        
        Tile tile23 = new Tile("beige", 2);
        tile23.setTranslateX(475);
        tile23.setTranslateY(450);
        
        root.getChildren().addAll(tile, tile1, tile2, tile3, tile4, tile5, tile6, tile7, tile8, tile9, tile10, tile11, tile12, tile13, tile14, tile15, tile16, tile17, tile18, tile19, tile20, tile21, tile22, tile23, tile24); 
        */       
        
        int screenSizeX = (600);
        int screenSizeY = (625);        
        
        scene2 = new Scene(root, screenSizeX, screenSizeY);         
    }

    public void createWinningsPage(Stage primaryStage)
    {
        Stage window = primaryStage;
        Button button2 = new Button("Exit Game");
        button2.setOnAction(e ->
        {
            mediaPlayer.pause();
            window.setScene(scene1);
            window.close();
            Platform.runLater(() -> new Game().start(new Stage()));
        });

        Text t = new Text();
        t.setText("WINNER IS");
        t.setFont(Font.font(Font.getFamilies().get(1), FontWeight.EXTRA_BOLD, 90));
        t.setFill(Color.WHITE);
        t.setTextAlignment(TextAlignment.RIGHT);
        t.setX(250);
        t.setY(300);
        // t.setFont(100);

        // setting background image 
        Image image = new Image(getClass().getResourceAsStream("SQ_NSwitchDS_Cluedo.jpg"), 600, 600, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root2.setBackground(new Background(backgroundImage));

        int screenSizeX = (600);
        int screenSizeY = (600);

        button2.setLayoutX(500);
        button2.setLayoutY(550);
        root2.getChildren().addAll(t, button2);

        scene3 = new Scene(root2, screenSizeX, screenSizeY);
    }

    public void setPlayerNames(int playerNumber)
    {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Player " + playerNumber + " name");
        dialog.setHeaderText("Please enter the name of Player " + playerNumber);
        dialog.setContentText("Player " + playerNumber + ":");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            String playerName = result.get();
            playerNames.add(playerName);
        }        
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No name entered");
            alert.setContentText("You must enter a player name.");

            alert.showAndWait();
            setPlayerNames(playerNumber);
        }
            
    }
}
