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
    private HashMap<Integer, Room> rooms;
    private int width;
    // private ArrayList<Room> rooms;

    public GameBoard()
    {
        dice = new Dice(2);
        grid = emptyGrid(24);
        startingSquares = new HashMap<Character.CharacterType, BoardSquare>();
        rooms = new HashMap<>();
        width = 24;
        /*insertBoardSpace(0, 0, new BoardSquare(false));
        insertBoardSpace(0, 1, new BoardSquare(false));
        insertBoardSpace(0, 2, new BoardSquare(false));
        insertBoardSpace(0, 3, new BoardSquare(false));*/
    }

    public GameBoard(int width)
    {
        dice = new Dice(2);
        this.width = width;
        grid = emptyGrid(width);
        startingSquares = new HashMap<Character.CharacterType, BoardSquare>();
        rooms = new HashMap<>();
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
        for (int i = 1; i <= width; i++)
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
        if (x < 1 || x > width || y < 1)
        {
            return new EmptySquare();
        }
        else
        {
            return grid.get(x).get(y);
        }
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
        for (int i = 1; i <= grid.size() && !found; i++)
        {
            HashMap<Integer, BoardSpace> row = grid.get(i);
            for (int j = 1; j <= row.size() && !found; j++)
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
     * Gets & returns the game board's grid (ie. all of its board spaces, 
     * respective board space positions and board space properties. 
     * 
     * @return The Hashmap representing the board's grid. 
     */
    public HashMap<Integer, HashMap<Integer, BoardSpace>> getGrid() 
    {
        return grid;
    }

    /**
     * scans each space in the gameboard and assigns its adjacencies
     * appropriatly for the rules of the board.
     */
    public void setAdjacencies() throws InvalidSetupFileException
    {
        for (int y = 1; y < grid.get(1).size(); y++)
        {
            for (int x = 1; x < grid.size(); x++)
            {
                BoardSpace currSquare = getBoardSpace(x, y);
                if (currSquare instanceof BoardSquareDoor)
                {
                    BoardSpace north = getBoardSpace(x, y - 1);
                    BoardSpace east = getBoardSpace(x + 1, y);
                    BoardSpace south = getBoardSpace(x, y + 1);
                    BoardSpace west = getBoardSpace(x - 1, y);

                    
                    // System.out.println("[" + x + "," + y +"]");
                    if (north instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) north);
                        currSquare.setAdjacent(0, rooms.get(((RoomSquareDoor) north).getRoomNo()));
                    }
                    else if (east instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) east);
                        currSquare.setAdjacent(1, rooms.get(((RoomSquareDoor) east).getRoomNo()));
                    }
                    else if (south instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) south);
                        currSquare.setAdjacent(2, rooms.get(((RoomSquareDoor) south).getRoomNo()));
                    }
                    else if (west instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) west);
                        currSquare.setAdjacent(3, rooms.get(((RoomSquareDoor) west).getRoomNo()));
                    }
                    else
                    {
                        throw new InvalidSetupFileException();
                    }
                } else if (currSquare instanceof BoardSquare)
                {
                    BoardSpace south = getBoardSpace(x, y + 1);
                    if (south != null)
                    {
                        if (south instanceof BoardSquare)
                        {
                            currSquare.setAdjacent(2, south);
                        }
                    }
                    BoardSpace east = getBoardSpace(x + 1, y);
                    if (east != null)
                    {
                        if (south instanceof BoardSquare)
                        {
                            currSquare.setAdjacent(1, east);
                        }
                    }
                }
                else if (currSquare instanceof SecretPassage)
                {
                    ((SecretPassage) currSquare).setRoomA(rooms.get(((SecretPassage) currSquare).getRoomNoA()));
                    
                    BoardSpace north = getBoardSpace(x, y - 1);
                    BoardSpace east = getBoardSpace(x + 1, y);
                    BoardSpace south = getBoardSpace(x, y + 1);
                    BoardSpace west = getBoardSpace(x - 1, y);
                    
                    if (north instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) north).getRoomNo()));
                    }
                    else if (east instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) east).getRoomNo()));
                    }
                    else if (south instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) south).getRoomNo()));
                    }
                    else if (west instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) west).getRoomNo()));
                    }
                    
                    currSquare.setAdjacent(0, ((SecretPassage) currSquare).getRoomB());
                    try
                    {
                        currSquare.setAdjacent(2, ((SecretPassage) currSquare).getRoomA());
                    }
                    catch(NullPointerException npe)
                    {
                        throw new InvalidSetupFileException();
                    }
                }
            }
        }
    }

    private void setDoorRoom(RoomSquareDoor current) throws InvalidSetupFileException
    {
        int[] coords = getSpaceCoords(current);
        
        BoardSpace north = getBoardSpace(coords[0], coords[1] - 1);
        BoardSpace east = getBoardSpace(coords[0] + 1, coords[1]);
        BoardSpace south = getBoardSpace(coords[0], coords[1] + 1);
        BoardSpace west = getBoardSpace(coords[0] - 1, coords[1]);
        
        if (north instanceof RoomSquare && !(north instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) north).getRoomNo());
        }
        else if (east instanceof RoomSquare && !(east instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) east).getRoomNo());
        }
        else if (south instanceof RoomSquare && !(south instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) south).getRoomNo());
        }
        else if (west instanceof RoomSquare && !(west instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) west).getRoomNo());
        }
        else
        {
            throw new InvalidSetupFileException();
        }
    }
    
    public void createRooms()
    {
        for (int y = 1; y < grid.get(1).size(); y++)
        {
            for (int x = 1; x < grid.size(); x++)
            {
                BoardSpace nextBoardSpace = getBoardSpace(x, y);
                if (nextBoardSpace instanceof RoomSquare)
                {
                    RoomSquare nextRoomSquare = (RoomSquare) nextBoardSpace;
                    int nextRoomNo = nextRoomSquare.getRoomNo();
                    if (!rooms.containsKey(nextRoomNo))
                    {
                        Room newRoom = null;
                        if (nextRoomNo == 1)
                        {
                            newRoom = new Room(Room.RoomType.kitchen);
                        } else if (nextRoomNo == 2)
                        {
                            newRoom = new Room(Room.RoomType.ballRoom);
                        } else if (nextRoomNo == 3)
                        {
                            newRoom = new Room(Room.RoomType.conservatory);
                        } else if (nextRoomNo == 4)
                        {
                            newRoom = new Room(Room.RoomType.billiardRoom);
                        } else if (nextRoomNo == 5)
                        {
                            newRoom = new Room(Room.RoomType.library);
                        } else if (nextRoomNo == 6)
                        {
                            newRoom = new Room(Room.RoomType.study);
                        } else if (nextRoomNo == 7)
                        {
                            newRoom = new Room(Room.RoomType.hall);
                        } else if (nextRoomNo == 8)
                        {
                            newRoom = new Room(Room.RoomType.lounge);
                        } else if (nextRoomNo == 9)
                        {
                            newRoom = new Room(Room.RoomType.diningRoom);
                        }
                        rooms.put(nextRoomSquare.getRoomNo(), newRoom);
                    }
                    nextRoomSquare.setBelongsTo(rooms.get(nextRoomNo));

                }
            }
        }
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
        Stack<BoardSpace> toDo = new Stack<>();
        HashMap<BoardSpace, Integer> distanceMappings = new HashMap<>();
        
        distanceMappings.put(start, 0);
        
        toDo.push(start);
        if (start instanceof Room)
        {
            for (int i = 0; i < start.getAdjacency().size(); i++)
            {
                for (int j = 0; j < start.getAdjacency().get(i).size(); j++)
                {
                    BoardSpace nextAdj = start.getAdjacency().get(i).get(j);
                    distanceMappings.put(nextAdj, 1);
                    toDo.add(nextAdj);
                }
            }
        }
        
        while (!toDo.empty())
        {
            BoardSpace next = toDo.pop();
            if (distanceMappings.get(next) == diceRoll)
            {
                possibleMoves.add(next);
            }
            else if (distanceMappings.get(next) < diceRoll)
            {
                seen.add(next);
                if (!(next instanceof Room))
                {
                    for (int i = 0; i < 4; i++)
                    {
                        try
                        {
                            for (int j = 0; j < next.getAdjacency().get(i).size(); j++)
                            {
                                BoardSpace nextChild = next.getAdjacency().get(i).get(j);
                                if (!toDo.contains(nextChild) && !seen.contains(nextChild))
                                {
                                    distanceMappings.put(nextChild, distanceMappings.get(next) + 1);
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
                        // ToDo: sort out boardSquareDoors not working!!
                    }
                }
                else
                {
                    possibleMoves.add(next);
                }
            }
        }
        /*
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
                            for (int j = 0; j < next.getBoardSpace().getAdjacency().get(i).size(); j++)
                            {
                                Pair nextChild = new Pair(next.getDistance() + 1, next.getBoardSpace().getAdjacency().get(i).get(j));
                                if (!toDo.contains(nextChild) && !seen.contains(nextChild.getBoardSpace()))
                                {
                                    toDo.add(nextChild);
                                }
                            }

                        } catch (NullPointerException npe)
                        {
                            /*
                             * Adjacency array was null in this location
                             * i.e. there is no board space in that direction
                             *
                        }
                    }
                } else
                {
                    possibleMoves.add(next.getBoardSpace());
                }
            }
        }*/
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

    public HashMap<Integer, Room> getRooms()
    {
        return rooms;
    }

    /**
     * @return The board in it's current state in a textual format.
     */
    @Override
    public String toString()
    {
        String str = "";

        for (int y = 1; y <= grid.get(1).size(); y++)
        {
            for (int x = 1; x <= grid.size(); x++)
            {
                str += grid.get(x).get(y).toString();
            }
            str += "\n";
        }

        return str;
    }

}
