package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.Character;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Creates a new GameBoad from a given text file
 *
 * @author Jamie
 */
public class BoardConstructor
{

    public BufferedReader fileInput;
    private GameBoard gb;

    /**
     * Creates a BufferedReader pointing to the default board file
     *
     * @throws FileNotFoundException if the specified file cannot be found, but
     * this should never happen as this file is a predetermined one.
     */
    public BoardConstructor() throws FileNotFoundException
    {
        try
        {
            InputStream is = getClass().getResourceAsStream("customisation/board layout/default.txt");
            //fileInput = new BufferedReader(new FileReader("cluedo/gameLogic/gameBoard/customisation/board layout/default.txt"));
            fileInput = new BufferedReader(new InputStreamReader(is));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            fileInput = new BufferedReader(new FileReader("src/cluedo/gameLogic/gameBoard/customisation/board layout/default.txt"));
        }
        
    }

    /**
     * Creates a BufferedReader from the provided filename
     *
     * @param fileName the file to use for the gameboard
     * @throws FileNotFoundException if the supplied file cannot be found,
     * should never happen because the filename is defined by a GUI based file
     * chooser.
     */
    public BoardConstructor(String fileName) throws FileNotFoundException
    {
        fileInput = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Creates a new GameBoard from the fileInput defined in the constructor.
     *
     * @return a new GameBoard
     * @throws InvalidSetupFileException if some placement of the
     */
    public GameBoard createBoard() throws InvalidSetupFileException
    {
        try
        {
            int width = Integer.parseInt(fileInput.readLine().substring(6));
            int height = Integer.parseInt(fileInput.readLine().substring(7));
            gb = new GameBoard(width, height);
            // ignores the x coord line in the setupfile
            fileInput.readLine();

            for (int y = 1; y <= height; y++)
            {
                // discards the initial two characters in each line of the setup file so that only the squares' data are read.
                fileInput.read();
                fileInput.read();
                for (int x = 1; x <= width; x++)
                {
                    gb.insertBoardSpace(x, y, readBoardSquare());
                }
                fileInput.readLine();
            }
            fileInput.close();

            gb.createRooms();
            gb.createClueDeck();
            gb.setAdjacencies();
            return gb;
        } //
        catch (IOException e)
        {
            throw new InvalidSetupFileException();
        }
    }

    /**
     * Reads a single instance of a boardSquare from the file (represented by a
     * symbol surrounded in square brackets (or parenthesise for a secret
     * passage)
     *
     * @return a valid BoardSpace object
     * @throws InvalidSetupFileException if the file is formatted incorrectly
     */
    private BoardSpace readBoardSquare() throws InvalidSetupFileException
    {
        BoardSpace newSquare = null;

        char[] input = new char[3];
        try
        {
            fileInput.read(input, 0, 3);
        } //
        catch (IOException ioe)
        {
            throw new InvalidSetupFileException();
        }

        if (input[0] == '[' && input[2] == ']')
        {
            char symbol = input[1];
            // Blank, unwalkable space
            if (symbol == '#')
            {
                newSquare = new EmptySquare();
            } // Standard Hallway
            else if (symbol == '_')
            {
                newSquare = new BoardSquare(false);
            } // Staircase Square
            else if (symbol == '/')
            {
                newSquare = new StaircaseSquare();
            } // Door Square
            else if (symbol == 'd')
            {
                newSquare = new BoardSquareDoor(false);
            } // Room Door Square
            else if (symbol == 'e')
            {
                newSquare = new RoomSquareDoor();
            }// Character Start Squares:
            else if (symbol == 'w')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.MrsWhite, (BoardSquare) newSquare);
            } //
            else if (symbol == 'g')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.RevGreen, (BoardSquare) newSquare);
            } //
            else if (symbol == 'p')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.MrsPeacock, (BoardSquare) newSquare);
            } //
            else if (symbol == 'l')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.ProfPlum, (BoardSquare) newSquare);
            } //
            else if (symbol == 's')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.MissScarlett, (BoardSquare) newSquare);
            } //
            else if (symbol == 'm')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.ColMustard, (BoardSquare) newSquare);
            } // Room numbers:
            else
            {
                try
                {
                    int roomNo = Integer.parseInt("" + input[1]);
                    if (roomNo > 0)
                    {
                        newSquare = new RoomSquare(roomNo);
                    }
                } //
                catch (NumberFormatException nfe)
                {
                    throw new InvalidSetupFileException();
                }
            }
        } //
        else if (input[0] == '(' && input[2] == ')')
        {
            try
            {
                int roomNo = Integer.parseInt("" + input[1]);
                if (roomNo > 0)
                {
                    newSquare = new SecretPassage(roomNo);
                }
            } //
            catch (NumberFormatException nfe)
            {
                throw new InvalidSetupFileException();
            }

        } //
        else
        {
            throw new InvalidSetupFileException();
        }
        return newSquare;
    }
}
