/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package winningspage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 *
 * @author Kaarunya_Inparaj
 */
public class WinningsPage extends Application {
   String Winner;
   MediaPlayer player;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        Stage winningsWindow = primaryStage;
        // makes sure game cannot be continued as all user inputs outsie of window are ignored
//        winningsWindow.initModality(Modality.APPLICATION_MODAL); 
        winningsWindow.setTitle("Winner!!");
        
        Text t = new Text();
        t.setText("WINNER IS");
        t.setFont(Font.font(Font.getFamilies().get(1),FontWeight.EXTRA_BOLD, 100));
        t.setFill(Color.WHITE);
        t.setTextAlignment(TextAlignment.RIGHT);
        t.setX(350);
        t.setY(400);
       // t.setFont(100);
        Button close = new Button("Close");
        close.setOnAction(e -> winningsWindow.close());
          
        Media music = new Media ("CLUE.mp3");
        player = new MediaPlayer(music);
        player.setAutoPlay(true);
        
        BorderPane bp = new BorderPane();
        bp.setBottom(close);
        bp.getChildren().add(t);
        
       // setting background image 
       Image image = new Image("SQ_NSwitchDS_Cluedo.jpg");
       BackgroundImage backgroundImage = new BackgroundImage(image, 
               BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
               BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        bp.setBackground(new Background(backgroundImage));
             

        
        Scene scene = new Scene(bp, 1000, 1000);
        
        winningsWindow.setScene(scene);
        winningsWindow.show();
    }
    
//    public void display(String winner){
//        
//     
//        
//        
//        
//        
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
