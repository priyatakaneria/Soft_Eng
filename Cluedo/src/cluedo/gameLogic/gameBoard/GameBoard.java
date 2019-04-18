/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.Character;
import cluedo.gameLogic.Dice;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * @author Jamie Thelin
 */
public class GameBoard
{

    private Dice dice;
    private HashMap<Integer, HashMap<Integer, BoardSpace>> grid;
    private HashMap<Character.CharacterType, BoardSquare> startingSquares;
    // private ArrayList<Room> rooms;

    public GameBoard()
    {
        dice = new Dice(2);
        grid = emptyGrid(24);
        startingSquares = new HashMap<Character.CharacterType, BoardSquare>();
        /*insertBoardSpace(0, 0, new BoardSquare(false));
        insertBoardSpace(0, 1, new BoardSquare(false));
        insertBoardSpace(0, 2, new BoardSquare(false));
        insertBoardSpace(0, 3, new BoardSquare(false));*/
    }

    public GameBoard(int width)
    {
        dice = new Dice(2);
        grid = emptyGrid(width);
    }

    /**
     * Creates a new HashMap with multiple sub-HashMaps representing the rows
     * and columns of a game board. Used to initialise different board
     * configurations.
     *
     * @param width The required width of the board
     * @return
     */
    private HashMap<Integer, HashMap<Integer, BoardSpace>> emptyGrid(int width)
    {
        HashMap<Integer, HashMap<Integer, BoardSpace>> eGrid = new HashMap<>();
        for (int i = 0; i < width; i++)
        {
            eGrid.put(i, new HashMap<>());
        }
        return eGrid;
    }

    /**
     * Inserts a BoardSpace into the grid, bypassing the ugly .get().put()
     * syntax.
     *
     * @param x The x coord to insert at
     * @param y The y coord to insert at
     * @param bs The BoardSpace to insert
     * @param north The space to the north
     * @param east The space to the east
     * @param south The space to the south
     * @param west The space to the west
     */
    public void insertBoardSpace(int x, int y, BoardSpace bs, BoardSpace north, BoardSpace east, BoardSpace south, BoardSpace west)
    {
        grid.get(x).put(y, bs);
        bs.setAdjacent(0, north);
        bs.setAdjacent(1, east);
        bs.setAdjacent(2, south);
        bs.setAdjacent(3, west);
    }

    /**
     * Inserts a BoardSpace into the grid, bypassing the ugly .get().put()
     * syntax.
     *
     * @param x The x coord to insert at
     * @param y The y coord to insert at
     * @param bs The BoardSpace to insert
     */
    public void insertBoardSpace(int x, int y, BoardSpace bs)
    {
        grid.get(x).put(y, bs);
    }

    /**
     * gets a board space specified by its x and y coords, (1, 1) being the top
     * left of the grid.
     *
     * @param x x coordinate
     * @param y y coordinate
     * @return the BoardSapce at (x,y) in the grid
     */
    public BoardSpace getBoardSpace(int x, int y)
    {
        return grid.get(x).get(y);
    }

    /**
     * Searches the grid for the given BoardSpace and returns it's coordinates,
     * or -1, -1 if it cannot be found in the grid.
     *
     * @param bs the BoardSpace to search for
     * @return the x and y coords of bs in the grid, or [-1,-1] if not found
     */
    public int[] getSpaceCoords(BoardSpace bs)
    {
        int[] coords = new int[2];
        coords[0] = -1;
        coords[1] = -1;
        boolean found = false;
        for (int i = 0; i < grid.size() && !found; i++)
        {
            HashMap<Integer, BoardSpace> row = grid.get(i);
            for (int j = 0; j < row.size() && !found; j++)
            {
                if (bs == row.get(j))
                {
                    found = true;
                    coords[0] = i;
                    coords[1] = j;
                }
            }
        }
        return coords;
    }

    /**
     * A utility class that stores an integer for the number of moves to a space
     * and the BoardSpace in question.
     */
    private class Pair
    {

        private int distance;
        private BoardSpace boardSpace;

        public Pair(int distance, BoardSpace boardSpace)
        {
            this.distance = distance;
            this.boardSpace = boardSpace;
        }

        public int getDistance()
        {
            return distance;
        }

        public BoardSpace getBoardSpace()
        {
            return boardSpace;
        }

    }

    /**
     * Returns a set of all the available spaces that are accessible from a
     * particular BoardSpace given a particular dice roll.
     *
     * @param start The BoardSpace to start the search at
     * @param diceRoll The number of spaces available to move according to a
     * roll of the dice.
     * @return a HashSet of all the available spaces to move to.
     */
    public HashSet<BoardSpace> availableMoves(BoardSpace start, int diceRoll)
    {
        HashSet<BoardSpace> seen = new HashSet<>();
        HashSet<BoardSpace> possibleMoves = new HashSet<>();
        Stack<Pair> toDo = new Stack<>();
        toDo.push(new Pair(0, start));
        if (start instanceof Room)
        {
            for (int i = 0; i < start.getAdjacency().size(); i++)
            {
                for (int j = 0; j < start.getAdjacency().get(i).size(); j++)
                {
                    toDo.add(new Pair(1, start.getAdjacency().get(i).get(j)));
                }
            }
        }
        while (!toDo.empty())
        {
            Pair next = toDo.pop();
            if (next.getDistance() == diceRoll)
            {
                possibleMoves.add(start);
            } else if (next.getDistance() < diceRoll)
            {
                seen.add(next.getBoardSpace());
                if (!(next.getBoardSpace() instanceof Room))
                {
                    for (int i = 0; i < 4; i++)
                    {
                        try
                        {
                            for (int j = 0; j < next.getBoardSpace().getAdjacency().size(); j++)
                            {
                                Pair nextChild = new Pair(next.getDistance() + 1, next.getBoardSpace().getAdjacency().get(i).get(j));
                                if (!toDo.contains(nextChild.getBoardSpace()) && !seen.contains(nextChild.getBoardSpace()))
                                {
                                    toDo.add(nextChild);
                                }
                            }

                        } catch (NullPointerException npe)
                        {
                            /*
                             * Adjacency array was null in this location
                             * i.e. there is no board space in that direction
                             */
                        }
                    }
                } else
                {
                    possibleMoves.add(next.getBoardSpace());
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Maintains the BoardSquares that each player should start at
     *
     * @param character The character who starts st the specified BoardSquare
     * @param bs The BoardSquare at which the specified character starts at.
     */
    public void setStartSquare(Character.CharacterType character, BoardSquare bs)
    {
        startingSquares.put(character, bs);
    }

    /**
     * @return The board in it's current state in a textual format.
     */
    @Override
    public String toString()
    {
        String str = "";

        for (Integer xCoord : grid.keySet())
        {
            for (Integer yCoord : grid.get(xCoord).keySet())
            {
                str += grid.get(xCoord).get(yCoord).toString();
            }
            str += "\n";
        }

        return str;
    }

}
