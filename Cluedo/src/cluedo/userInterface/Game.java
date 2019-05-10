/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.userInterface;

import cluedo.gameLogic.Accusation;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.Suggestion;
import cluedo.gameLogic.gameBoard.BoardSpace;
import cluedo.gameLogic.player.Player;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

import cluedo.gameLogic.Character;
import cluedo.gameLogic.TurnManager;
import cluedo.gameLogic.Weapon;
import cluedo.gameLogic.gameBoard.BoardSquare;
import cluedo.gameLogic.gameBoard.GameBoard;
import cluedo.gameLogic.gameBoard.InvalidSetupFileException;
import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.gameBoard.RoomSquare;
import cluedo.gameLogic.player.HumanPlayer;
import java.util.HashMap;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import cluedo.userInterface.boardTiles.BoardSpacePane;
import javafx.stage.FileChooser;
import cluedo.userInterface.boardTiles.RoomSquarePane;

/**
 * This is the main class which launches the GUI
 * 
 * @author sb816
 */
public class Game extends Application
{

    private Pane root = new Pane();
    private Pane root2 = new Pane();
    private Pane root3 = new Pane();
    private Pane root4 = new Pane();
    private Scene scene1, scene2, scene3, scene4, scene5;
    private String time, winner, player;

    private ArrayList<String> playerNames = new ArrayList<String>();
    // this need to be a map between instances of character and player names ~ Sriram and Jamie
    private HashMap<Character, String> characterPlayerMap = new HashMap<Character, String>();
    //this needs to be the number of ai players ~ Sriram and Jamie
    private int noAiPlayers;
    //This might not be a string but whichever way you are doing the file selection.
    private String customBoardFileName;
    private AnimationTimer timer;
    private Label lblTime = new Label("0 .s");
    private int seconds, multiplayer = 0, countPlayers = 1;
    private MediaPlayer mediaPlayer;
    private Stage window;

    // GameBoard stuff
    private GameBoard gb;
    private GridPane gameBoardGridPane;
    private HashMap<Player, PlayerWindow> playerWindows;

    // Side Panel
    private VBox sidePanel;
    private HBox dice;
    private DicePane dieOne;
    private DicePane dieTwo;

    private VBox suggestionBox;
    private Suggestion newSuggestion;

    private VBox accusationBox;
    private Accusation newAccusation;

    public static final Border SOLID_BLACK_BORDER = createSolidBorder(Color.BLACK);

    //turn manager misc.:
    private boolean waitingForDice;
    private int lastRoll;
    private boolean waitingForSuggestion;
    private boolean accusationEscape;

    private boolean waitingForMove;
    private HashSet<BoardSpace> availableMoves;
    private BoardSpace movementChoice;
    private boolean waitingForEndTurn;
    
    private Player suggestionAsker;

    @Override
    public void start(Stage primaryStage)
    {

        final URL resource = getClass().getResource("CLUE.mp3");
        final Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

        Button button1, button3, button4;
        window = primaryStage;

        Label label1 = new Label("Number of Players:");
        final String[] players = new String[]
        {
            "1", "2", "3", "4", "5", "6"
        };

        ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6"));

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
        scene1 = new Scene(layout1, 490, 630);
        window.setScene(scene1);
        window.setTitle("Cluedo");
        window.show();
        window.setResizable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

    /**
     * DisplayTurn displays a message of which player's turn it is. It takes the
     * current player as an argument. Waits a predetermined number of seconds
     * before finishing.
     * 
     * @param player the Player to show whose turn it is
     */
    public void displayTurn(Player player)
    {
//        System.out.println("entering displayTurn");
//        customAlert(player.getPlayerName() + "'s Turn");
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(player.getPlayerName() + "'s Turn");
        alert.setTitle("New Turn");
        alert.setWidth(150);
        alert.setHeight(100);
        alert.showAndWait();
    }

    /**
     * GUI.rollDice() should wait for the user to click on the dice, or some
     * 'roll dice' button before returning the rolled value.
     * 
     * @return the value of the last dice roll
     */
    public int rollDice()
    {
        return lastRoll;
    }

    /**
     * sets the value of waitingForDice to b
     * @param b the boolean to set
     */
    public void setWaitingForDice(boolean b)
    {
        waitingForDice = b;
    }
    
    /**
     * @return the value of WaitingForDice
     */
    public boolean getWaitingForDice()
    {
        return waitingForDice;
    }

    /**
     * GUI.chooseSpace should take a hashSet of boardSpaces and visible show
     * them as available on the board (including rooms). The method then should
     * wait for the user to select one of the lit up spaces and return the
     * chosen space.
     * 
     * @param availableMoves the set of available moves for the 
     */
    public void chooseSpace(HashSet<BoardSpace> availableMoves)
    {
        this.availableMoves = new HashSet<>();
        this.availableMoves.addAll(availableMoves);
        System.out.println("entering chooseSpace");
        BoardSpace choice = null;
        for (BoardSpace bs : availableMoves)
        {
            if (bs instanceof Room)
            {
                HashSet<RoomSquare> allSquares = gb.getAllFromRoom((Room) bs);
                this.availableMoves.addAll(allSquares);
                for (RoomSquare rs : allSquares)
                {
                    BoardSpacePane guiPane = rs.getGuiPane();
                    if (guiPane instanceof RoomSquarePane)
                    {
                        guiPane.setTmpColours(Color.ORANGE, guiPane.getStdStroke());
                    }
                    else
                    {
                        guiPane.setTmpColours(Color.ORANGE, Color.ORANGERED);
                    }
                }
            } //
            else
            {
                BoardSpacePane guiPane = bs.getGuiPane();
                guiPane.setTmpColours(Color.ORANGE, Color.ORANGERED);
            }
        }
    }

    /**
     * @return the last movement choice made by the player
     */
    public BoardSpace getMovementChoice()
    {
        return movementChoice;
    }
    
    /**
     * sets the movement choice to a new boardspace
     * @param bs the new position
     */
    public void setMovementChoice(BoardSpace bs)
    {
        movementChoice = bs;
    }

    /**
     * sets the value of WaitingForMove
     * 
     * @param b the value to set
     */
    public void setWaitingForMove(boolean b)
    {
        waitingForMove = true;
    }

    /**
     * GUI.teleport should do the same as choose space, but highlight every
     * possible location
     * 
     * @return the boardspace chosen by the player
     */
    public BoardSpace teleport()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.endTurn should prompt the user to end their turn and wait for them to
     * press the end-turn button.
     * 
     * @param b the new value to set waitingForTurn to 
     */
    public void setWaitingForEndTurn(boolean b)
    {
        waitingForEndTurn = b;
    }
    
    public boolean getEndTurn()
    {
        return waitingForEndTurn;
    }

    /**
     * GUI.makeSuggestion should take as input the player who is making the
     * suggestion i.e. the asker. The method should query the user and wait
     * until they enter their suggestion details. The return value should be a
     * new suggestion object representing the user's choice.
     * 
     * @param Player the player that made the suggestion
     * @return the new Suggestion made by the player
     */
    public Suggestion makeSuggestion(Player player)
    {
        suggestionAsker = player;
        return newSuggestion;
    }
    
    public void setWaitingForSuggestion(boolean b)
    {
        waitingForSuggestion = true;
    }
    
    public void setNewSuggestion(Suggestion s)
    {
        newSuggestion = s;
    }

    /**
     * GUI.noPlayerClues should inform the user that their suggestion has
     * provided no clues, and wait for them to maybe fill in some detective
     * notes and click an accept button or something.
     *
     * Doesn't need to return anything.
     */
    public void noPlayerClues()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.ChooseResponse should take a player (nextEnquiry) and prompt that
     * player to select from one of the possible cards to show to the asker.
     * 
     * @param nextEnquiry the next player to be asked about the suggestion
     * @param possibleClues the set of possible clues the player has to choose
     * @return the ClueCard chosen by the player
     */
    public ClueCard chooseResponse(Player nextEnquiry, ArrayList<ClueCard> possibleClues)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.showClue takes a ClueCard and the enquired player and displays it to
     * the player who made the suggestion, then waits for the user to make some
     * notes and press a continue button.
     * 
     * @param response the clue to show
     * @param player the player to shoe the clue to
     * @param clueGiver the player giving the clue
     */
    public void showClue(ClueCard response, Player player, Player clueGiver)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.accusationQuery should display a message asking if the user wants to
     * make an accusation and waits for their input to click a 'make accusation'
     * button or an 'end turn' button.
     *
     * @return true if they press make accusation, or false if the press end
     * turn.
     */
    public boolean accusationQuery()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.MakeAccusation should take a parameter of the player making the
     * accusation and prompt them to select the clues they think are the final
     * solution. Waits until they press some 'submit' button then returns an
     * Accusation object.
     * 
     * @param player the player making the accusation
     * @return the new accusation made
     */
    public Accusation makeAccusation(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * GUI.displayExtraTurn should display the fact the a player specified in
     * the parameters is taking an extra turn because of their intrigue card
     * from last turn.
     * 
     * @player the player who has an extra turn
     */
    public void displayExtraTurn(Player player)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * display a congratulations to a player who has made an accurate accusation
     * 
     * @param currPlayer the player who has won the game
     */
    public void winningsPage(Player currPlayer)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void configureFileChooser(
            final FileChooser fileChooser)
    {
        fileChooser.setTitle("View Files");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("txt", "*.txt"),
                new FileChooser.ExtensionFilter("board", "*.board")
        );
    }

    private void setFileChooser(Stage window)
    {

        final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Custom board");
        final Button defaultButton = new Button("Default board");

        openButton.setOnAction(
                new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(final ActionEvent e)
            {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(window);
                if (file != null)
                {
                    customBoardFileName = file.getPath();
                    setGameboard(window, false);
                }
            }
        });

        defaultButton.setOnAction(
                new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(final ActionEvent e)
            {
                setGameboard(window, true);
            }
        });

        openButton.setTranslateX(5);
        openButton.setTranslateY(5);
        defaultButton.setTranslateX(100);
        defaultButton.setTranslateY(5);
        root4.getChildren().addAll(openButton, defaultButton);

        scene5 = new Scene(root4, 300, 40);
        window.setTitle("Choose a board layout:");
        window.setScene(scene5);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
        window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
    }

    public void setGameboard(Stage window, Boolean defaultBoard)
    {

        TurnManager turnManager;

        try
        {
            if (defaultBoard)
            {
                turnManager = new TurnManager(characterPlayerMap, noAiPlayers, this);
                createGameboard(window, turnManager);
                window.setTitle("Cluedo");
                window.setScene(scene2);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
                window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
            } else
            {
                turnManager = new TurnManager(characterPlayerMap, noAiPlayers, customBoardFileName, this);
                createGameboard(window, turnManager);
                window.setTitle("Cluedo");
                window.setScene(scene2);
                Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
                window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);
            }

            playerWindows = new HashMap<>();
            for (Player p : turnManager.getRealPlayers())
            {
                if (p instanceof HumanPlayer)
                {
                    PlayerWindow nextPlayerWindow = new PlayerWindow(p);
                    playerWindows.put(p, nextPlayerWindow);
                    nextPlayerWindow.show();
                }
            }
            turnManager.gameLoop();
        } //
        catch (InvalidSetupFileException e)
        {
            //Alert(Alert.AlertType AlertType, String("Invalid Setup File!"));
            //jOptionPane.showMessageDialog(null, "Invalid Setup File!");
            Alert alert = new Alert(AlertType.WARNING);
            alert.setContentText("Invalid Setup File");
            alert.setTitle("Error");
            alert.showAndWait();
            window.setScene(scene1);
            e.printStackTrace();
        }
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

    public void setCharactersPage(Stage window)
    {

        ArrayList<String> AIchoices = new ArrayList<>();

        if (multiplayer < 6)
        {
            for (int i = 0; i < (6 - multiplayer + 1); i++)
            {
                String n = Integer.toString(i);
                AIchoices.add(n);
            }

            ChoiceDialog<String> dialog = new ChoiceDialog<>("0", AIchoices);
            dialog.setTitle("AI players");
            dialog.setHeaderText("Please select the number of AI players in the game.");
            dialog.setContentText("AI players:");
            dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
            dialog.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> event.consume());

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent())
            {
                int a = Integer.parseInt(result.get());
                noAiPlayers = a;
            }

            System.out.println(noAiPlayers);
        }

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
        } else
        {
            createCharacterPage(window);
            window.setTitle("Please choose a character:");
            window.setScene(scene4);
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            window.setX((primScreenBounds.getWidth() - window.getWidth()) / 2);
            window.setY((primScreenBounds.getHeight() - window.getHeight()) / 2);

            player = playerNames.get(0);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(playerNames.get(0) + "'s Character");
            alert.setHeaderText("Please choose a character for " + playerNames.get(0));
            alert.setContentText("Select the character by clicking the corresponding picture.");
            alert.showAndWait();

        }
    }

    public void createCharacterPage(Stage primaryStage)
    {

        Stage window = primaryStage;

        Rectangle mrsPeacock = new Rectangle(145, 215);
        mrsPeacock.setFill(Color.TRANSPARENT);
        mrsPeacock.setTranslateX(5);
        mrsPeacock.setTranslateY(5);
        mrsPeacock.setOnMouseClicked(e
                ->
        {
            Character mrsPeacockChar = Character.MrsPeacock;
            chooseCharacterButtonClicked(mrsPeacockChar, primaryStage);

        });

        Rectangle colonelMustard = new Rectangle(145, 215);
        colonelMustard.setFill(Color.TRANSPARENT);
        colonelMustard.setTranslateX(150);
        colonelMustard.setTranslateY(5);
        colonelMustard.setOnMouseClicked(e
                ->
        {
            Character colonelMustardChar = Character.ColMustard;
            chooseCharacterButtonClicked(colonelMustardChar, primaryStage);
        });

        Rectangle missScarlet = new Rectangle(145, 215);
        missScarlet.setFill(Color.TRANSPARENT);
        missScarlet.setTranslateX(300);
        missScarlet.setTranslateY(5);
        missScarlet.setOnMouseClicked(e
                ->
        {
            Character missScarletChar = Character.MissScarlett;
            chooseCharacterButtonClicked(missScarletChar, primaryStage);

        });

        Rectangle profPlum = new Rectangle(145, 215);
        profPlum.setFill(Color.TRANSPARENT);
        profPlum.setTranslateX(5);
        profPlum.setTranslateY(230);
        profPlum.setOnMouseClicked(e
                ->
        {
            Character professorPlumChar = Character.ProfPlum;
            chooseCharacterButtonClicked(professorPlumChar, primaryStage);
        });

        Rectangle mrsWhite = new Rectangle(145, 215);
        mrsWhite.setFill(Color.TRANSPARENT);
        mrsWhite.setTranslateX(150);
        mrsWhite.setTranslateY(230);
        mrsWhite.setOnMouseClicked(e
                ->
        {
            Character mrsWhiteChar = Character.MrsWhite;
            chooseCharacterButtonClicked(mrsWhiteChar, primaryStage);
        });

        Rectangle revGreen = new Rectangle(145, 215);
        revGreen.setFill(Color.TRANSPARENT);
        revGreen.setTranslateX(300);
        revGreen.setTranslateY(230);
        revGreen.setOnMouseClicked(e
                ->
        {
            Character revGreenChar = Character.RevGreen;
            chooseCharacterButtonClicked(revGreenChar, primaryStage);
        });

        // setting background image 
        Image image = new Image(getClass().getResourceAsStream("characters.jpg"), 450, 450, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root3.setBackground(new Background(backgroundImage));

        int screenSizeX = (450);
        int screenSizeY = (450);
        root3.getChildren().addAll(mrsPeacock, colonelMustard, missScarlet, profPlum, mrsWhite, revGreen);
        scene4 = new Scene(root3, screenSizeX, screenSizeY);
    }

    public void chooseCharacterButtonClicked(Character character, Stage primaryStage)
    {
        characterPlayerMap.put(character, player);
        System.out.println(characterPlayerMap);
        if (countPlayers < playerNames.size())
        {
            player = playerNames.get(countPlayers);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(playerNames.get(countPlayers) + "'s Character");
            alert.setHeaderText("Please choose a character for " + playerNames.get(countPlayers));
            alert.setContentText("Select the character by clicking the corresponding picture.");
            alert.showAndWait();
            countPlayers++;
        } else
        {
            setFileChooser(primaryStage);
        }
    }

    public void createGameboard(Stage primaryStage, TurnManager tm)
    {
        gb = tm.getGameBoard();
        Stage window = primaryStage;
        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(e
                ->
        {
            mediaPlayer.pause();
            window.setScene(scene1);
            window.close();
            Platform.runLater(() -> new Game().start(new Stage()));
        });

        exitButton.setAlignment(Pos.CENTER);

        gameBoardGridPane = new GridPane();

        //x coord
        for (int x = 1; x <= tm.getGameBoard().getWidth(); x++)
        {
            for (int y = 1; y <= tm.getGameBoard().getHeight(); y++)
            {
                //Make GUI Tiles in Here
                final BoardSpacePane newPane;
                BoardSpace nextBoardSpace = tm.getGameBoard().getBoardSpace(x, y);

                newPane = nextBoardSpace.getGuiPane();

                newPane.setOnMouseClicked(click ->
                {
                    if (waitingForMove)
                    {
                        if (availableMoves.contains(newPane.getLogicalBoardSpace()))
                        {
                            
                            System.out.println("Space clicked");
                            movementChoice = newPane.getLogicalBoardSpace();
                            if (newPane instanceof RoomSquarePane)
                            {
                                movementChoice = ((RoomSquare) movementChoice).belongsTo();
                            }
                            setNormalColours();
                            setWaitingForMove(false);
                        }
                    }
                });

                gameBoardGridPane.add(newPane, x - 1, y - 1);
            }
        }
        for (Player p : tm.getAllPlayers())
        {
            System.out.println(p.getCharacter());
            HashMap<Character, BoardSquare> starting = tm.getGameBoard().getStartingSquares();
            Character playerChar = p.getCharacter();
            BoardSquare charSquare = starting.get(playerChar);
            BoardSpacePane guiPane = charSquare.getGuiPane();
            PlayerPiece pp = p.getGuiPiece();
            guiPane.addPlayer(pp);
        }

        // create things to go in the sidepanel, like dice
        sidePanel = new VBox();
        sidePanel.setAlignment(Pos.TOP_CENTER);

        VBox.setMargin(exitButton, new Insets(10, 10, 10, 10));

        dice = new HBox();
        dice.setAlignment(Pos.CENTER);
        dieOne = new DicePane();
        HBox.setMargin(dieOne, new Insets(10, 10, 10, 10));
        dieTwo = new DicePane();
        HBox.setMargin(dieTwo, new Insets(10, 10, 10, 10));
        dice.getChildren().addAll(dieOne, dieTwo);

        dice.setOnMouseClicked(click ->
        {
            System.out.println("dice clicked");
            if (waitingForDice)
            {
                lastRoll = gb.rollDice();
                int[] lastRolls = gb.getLastRolls();
                dieOne.setLastRoll(lastRolls[0]);
                dieTwo.setLastRoll(lastRolls[1]);
                setWaitingForDice(false);
                System.out.println("dice escape");
            }
        });

        // Suggestion panel
        suggestionBox = new VBox();
        suggestionBox.setAlignment(Pos.CENTER);
        Text makeSuggestionText = new Text("Choose your selection below,\nthen click 'Make Suggestion'");
        Label chooseCharacterLabel = new Label("Character:");
        ChoiceBox characterChoice = new ChoiceBox();
        for (Character c : Character.values())
        {
            characterChoice.getItems().add(c.getCharacterName());
        }
        Label chooseRoomLabel = new Label("Room:");
        Label currentRoom = new Label("");
        Label chooseWeaponLabel = new Label("Weapon:");
        ChoiceBox weaponChoice = new ChoiceBox();
        for (Weapon w : Weapon.values())
        {
            weaponChoice.getItems().add(w.getWeaponName());
        }
        Button makeSuggestionBtn = new Button("Make Suggestion");
        makeSuggestionBtn.setOnAction(btnPress ->
        {
            newSuggestion = readSuggestion((String) characterChoice.getValue(), (String) weaponChoice.getValue(), suggestionAsker);
            if (newSuggestion == null)
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setContentText("You must select one Character, Room and Weapon");
                alert.setTitle("Invalid Suggestion");
                alert.showAndWait();
            } //
            else
            {
                waitingForSuggestion = true;
            }
        });

        
        suggestionBox.getChildren().addAll(makeSuggestionText, chooseCharacterLabel, characterChoice, chooseRoomLabel, currentRoom, chooseWeaponLabel, weaponChoice, makeSuggestionBtn);
        for (Node n : suggestionBox.getChildrenUnmodifiable())
        {
            VBox.setMargin(n, new Insets(10, 10, 10, 10));
        }
        
        // Accusation panel
        accusationBox = new VBox();
        accusationBox.setAlignment(Pos.CENTER);
        Text makeAccusationText = new Text("Decide on your final accusation below and click 'Make Accusation");

        Label chooseCharacterLabelAcc = new Label("Character:");
        ChoiceBox characterChoiceAcc = new ChoiceBox();
        for (Character c : Character.values())
        {
            characterChoiceAcc.getItems().add(c.getCharacterName());
        }
        Label chooseRoomLabelAcc = new Label("Room:");
        ChoiceBox roomChoiceAcc = new ChoiceBox();
        for (Room r : tm.getGameBoard().getRooms().values())
        {
            roomChoiceAcc.getItems().add(r.getRoomName().getRoomStringName());
        }
        Label chooseWeaponLabelAcc = new Label("Weapon:");
        ChoiceBox weaponChoiceAcc = new ChoiceBox();
        for (Weapon w : Weapon.values())
        {
            weaponChoiceAcc.getItems().add(w.getWeaponName());
        }
        
        
        
        Button makeAccusationBtn = new Button("Make Accusation");
        makeAccusationBtn.setOnAction(btnPress ->
        {
            newAccusation = readAccusation((String) characterChoiceAcc.getValue(), (String) roomChoiceAcc.getValue(), (String) weaponChoiceAcc.getValue(), tm.getCurrPlayer());
            if (newAccusation == null)
            {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setContentText("You must select one Character, Room and Weapon");
                alert.setTitle("Invalid Accusation");
                alert.showAndWait();
            } else
            {
                accusationEscape = true;
            }
        });

        accusationBox.getChildren().addAll(makeAccusationText, chooseCharacterLabelAcc, characterChoiceAcc, chooseRoomLabelAcc, roomChoiceAcc, chooseWeaponLabelAcc, weaponChoiceAcc, makeAccusationBtn);

        Button endTurnBtn = new Button("End Turn");
        endTurnBtn.setOnMouseClicked(click -> 
        {
            if (waitingForEndTurn)
            {
                System.out.println("end turn clicked");
                waitingForEndTurn = false;
            }
            
        });
        
        sidePanel.getChildren().addAll(exitButton, dice, suggestionBox, endTurnBtn);

        BorderPane centerSideContainer = new BorderPane();
        centerSideContainer.setCenter(gameBoardGridPane);
        centerSideContainer.setRight(sidePanel);

//        Button winningsPage = new Button("Winnings Page");
//        winningsPage.setOnAction(e -> setWinningsPage(primaryStage));
//        winningsPage.setLayoutX(425);
//        winningsPage.setLayoutY(600);
        root.getChildren().add(centerSideContainer);

//        int screenSizeX = (1280);
//        int screenSizeY = (720);
        scene2 = new Scene(root);
        window.setScene(scene2);
        window.show();
        System.out.println("GUI Thread: " + Thread.currentThread().getName());
    }

    public void createWinningsPage(Stage primaryStage)
    {
        Stage window = primaryStage;
        Button button2 = new Button("Exit Game");
        button2.setOnAction(e
                ->
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
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setDisable(true);
        dialog.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> event.consume());

        String playerName = null;

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent())
        {
            playerName = result.get();

            if (playerName.length() == 0)
            {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No name entered");
                alert.setContentText("You must enter a player name.");

                alert.showAndWait();
                setPlayerNames(playerNumber);
            } else
            {
                playerNames.add(playerName);
            }
        }
    }

    private Suggestion readSuggestion(String character, String weapon, Player currPlayer)
    {
        Character chosenCharacter = null;
        for (Character c : Character.values())
        {
            if (c.getCharacterName().equals(character))
            {
                chosenCharacter = c;
            }
        }
        Room chosenRoom = null;
        if (currPlayer != null)
        {
            chosenRoom = (Room) currPlayer.getCurrentPosition();
        }
        Weapon chosenWeapon = null;
        for (Weapon w : Weapon.values())
        {
            if (w.getWeaponName().equals(weapon))
            {
                chosenWeapon = w;
            }
        }
        return new Suggestion(chosenCharacter, chosenRoom, chosenWeapon, currPlayer);
    }

    private Accusation readAccusation(String character, String room, String weapon, Player currPlayer)
    {
        Character chosenCharacter = null;
        for (Character c : Character.values())
        {
            if (c.getCharacterName().equals(character))
            {
                chosenCharacter = c;
            }
        }
        Room chosenRoom = null;
        for (Room r : gb.getRooms().values())
        {
            if (r.getRoomName().getRoomStringName().equals(room))
            {
                chosenRoom = r;
            }
        }
        Weapon chosenWeapon = null;
        for (Weapon w : Weapon.values())
        {
            if (w.getWeaponName().equals(weapon))
            {
                chosenWeapon = w;
            }
        }
        return new Accusation(chosenCharacter, chosenRoom, chosenWeapon, currPlayer);
    }

    public void setNormalColours()
    {
        for (BoardSpace bs : availableMoves)
        {
            if (!(bs instanceof Room))
            {
                bs.getGuiPane().setNormalColours();
            }
        }
    }

    public static Background createSolidBackground(Color c)
    {
        return new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY));
    }

    public static Border createSolidBorder(Color c)
    {
        return new Border(new BorderStroke(c, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
    }

    public void setRollValue(int i)
    {
        lastRoll = i;
    }
}
