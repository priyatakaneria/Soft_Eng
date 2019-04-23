/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.Character;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author Jamie
 */
public class BoardConstructor
{

    public BufferedReader fileInput;
    private GameBoard gb;

    public BoardConstructor() throws FileNotFoundException
    {
        try
        {
            fileInput = new BufferedReader(new FileReader("customisation/board layout/default.txt"));
        } catch (FileNotFoundException e)
        {
            throw e;
        }
    }

    public BoardConstructor(String fileName) throws FileNotFoundException
    {
        try
        {
            fileInput = new BufferedReader(new FileReader("customisation/board layout/" + fileName));
        } catch (FileNotFoundException e)
        {
            throw e;
        }
    }

    public GameBoard createBoard() throws InvalidSetupFileException
    {
        try
        {
            int width = Integer.parseInt(fileInput.readLine().substring(6));
            int height = Integer.parseInt(fileInput.readLine().substring(7));
            gb = new GameBoard(width);
            // ignores the x coord line in the setupfile
            fileInput.readLine();

            for (int y = 1; y <= height; y++)
            {
                // discards the initial two characters in each line of the setup file so that only the squares' data are read.
                fileInput.read();
                fileInput.read();
                for (int x = 1; x <= width; x++)
                {
                    // System.out.println("[" + x + "," + y +"]");
                    gb.insertBoardSpace(x, y, readBoardSquare());
                }
                fileInput.readLine();
            }
            fileInput.close();

            gb.createRooms();
            gb.setAdjacencies();
            return gb;
        } catch (IOException e)
        {
            throw new InvalidSetupFileException();
        }
    }

    private BoardSpace readBoardSquare() throws InvalidSetupFileException
    {
        BoardSpace newSquare = null;

        char[] input = new char[3];
        try
        {
            fileInput.read(input, 0, 3);
        } catch (IOException ioe)
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
                gb.setStartSquare(Character.CharacterType.MrsWhite, (BoardSquare) newSquare);
            } else if (symbol == 'g')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.CharacterType.RevGreen, (BoardSquare) newSquare);
            } else if (symbol == 'p')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.CharacterType.MrsPeacock, (BoardSquare) newSquare);
            } else if (symbol == 'l')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.CharacterType.ProfPlum, (BoardSquare) newSquare);
            } else if (symbol == 's')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.CharacterType.MissScarlett, (BoardSquare) newSquare);
            } else if (symbol == 'm')
            {
                newSquare = new BoardSquare(false);
                gb.setStartSquare(Character.CharacterType.ColMustard, (BoardSquare) newSquare);
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
                } catch (NumberFormatException nfe)
                {
                    throw new InvalidSetupFileException();
                }
            }
        } else if (input[0] == '(' && input[2] == ')')
        {
            try
            {
                int roomNo = Integer.parseInt("" + input[1]);
                if (roomNo > 0)
                {
                    newSquare = new SecretPassage(roomNo);
                }
            } catch (NumberFormatException nfe)
            {
                throw new InvalidSetupFileException();
            }

        } else
        {
            throw new InvalidSetupFileException();
        }
        return newSquare;
    }

}
