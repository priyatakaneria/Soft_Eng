package cluedo.userInterface;

import cluedo.gameLogic.ClueCard;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;

/**
 * This displays the clue card in the player's detective notes.
 *
 * @author Jamie Thelin
 */
public class CardPane extends BorderPane
{

    private ClueCard clue;

    /**
     * creates the ClueCardPane and gives it a string identifying it.
     * 
     * @param cc the logical cluecard this represents
     * @param size which size enum this card should be
     */
    public CardPane(ClueCard cc, Size size)
    {
        clue = cc;

        Label leftText = new Label(clue.getClueType().toString());
        leftText.setTextAlignment(TextAlignment.LEFT);
        leftText.getTransforms().add(new Rotate(-90));
        Label rightText = new Label(clue.getClueType().toString());
        rightText.setTextAlignment(TextAlignment.RIGHT);
        leftText.getTransforms().add(new Rotate(90));

        setLeft(leftText);
        //setRight(rightText);
        setCenter(null);

        setWidth(size.getWidth());
        setHeight(size.getHeight());

        setBackground(Game.createSolidBackground(Color.CORNSILK));
        setBorder(Game.SOLID_BLACK_BORDER);
    }

    /**
     * This specifies two sizes that the card can be displayed as, one for the
     * standard detective notes, and one for selecting the possible clues.
     */
    public enum Size
    {
        large(150, 225), small(100, 150);

        private int width;
        private int height;

        private Size(int width, int height)
        {
            this.width = width;
            this.height = height;
        }

        public int getWidth()
        {
            return width;
        }

        public int getHeight()
        {
            return height;
        }
    }

}
