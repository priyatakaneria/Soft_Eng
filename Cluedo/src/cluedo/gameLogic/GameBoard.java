/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

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

    public GameBoard()
    {
        dice = new Dice(2);
        grid = emptyGrid(24);
        insertBoardSpace(0, 0, new BoardSquare(false));
        insertBoardSpace(0, 1, new BoardSquare(false));
        insertBoardSpace(0, 2, new BoardSquare(false));
        insertBoardSpace(0, 3, new BoardSquare(false));
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
     */
    public void insertBoardSpace(int x, int y, BoardSpace bs)
    {
        grid.get(x).put(y, bs);
    }
    
    public BoardSpace getBoardSpace(int x, int y)
    {
        return grid.get(x).get(y);
    }
    
    public int[] getSpaceCoords(BoardSpace bs)
    {
        int[] coords = new int[2];
        found = false;
        for (HashMap<Integer, HashMap<Intger, Board row : grid)
        {
            
        }
        coords[0] = grid.
    }

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

    public HashSet<BoardSpace> availableMoves(BoardSpace start, int diceRoll)
    {
        HashSet<BoardSpace> seen = new HashSet<>();
        HashSet<BoardSpace> possibleMoves = new HashSet<>();
        Stack<Pair> toDo = new Stack<>();
        toDo.push(new Pair(0, start));
        if (start instanceof Room)
        {
            
        } else
        {
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
                                Pair nextChild = new Pair(next.getDistance() + 1, next.getBoardSpace().getAdjacency()[i]);
                                if (!toDo.contains(nextChild.getBoardSpace()) && !seen.contains(nextChild.getBoardSpace()))
                                {
                                    toDo.add(nextChild);
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
                         
                    }
                }
            }
        }
        return possibleMoves;
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
