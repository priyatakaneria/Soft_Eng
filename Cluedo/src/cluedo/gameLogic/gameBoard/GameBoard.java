package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.Envelope;
import cluedo.gameLogic.Card;
import cluedo.gameLogic.Character;
import cluedo.gameLogic.ClueCard;
import cluedo.gameLogic.Dice;
import cluedo.gameLogic.IntrigueCard;
import cluedo.gameLogic.IntrigueType;
import cluedo.gameLogic.Weapon;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * Represents the logical gameboard
 * 
 * @author Jamie Thelin
 */
public class GameBoard
{

    private Dice dice;
    private HashMap<Integer, HashMap<Integer, BoardSpace>> grid;
    private HashMap<Character, BoardSquare> startingSquares;
    private HashMap<Integer, Room> rooms;
    private int width;
    private int height;

    private LinkedList<Card> intrigueDeck;
    private LinkedList<Card> clueDeck;

    private Envelope envelope;
    // private ArrayList<Room> rooms;

    /**
     * Constructs the board with the standard 24x25 setup.
     */
    public GameBoard() throws InvalidSetupFileException
    {
        dice = new Dice(2);
        grid = emptyGrid(24);
        startingSquares = new HashMap<>();
        rooms = new HashMap<>();
        width = 24;
        height = 25;
        intrigueDeck = createIntrigueDeck();
    }

    /**
     * @return the width of the board
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * @return the height of the board
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Constructs a board of a given width.
     *
     * @param width
     */
    public GameBoard(int width, int height) throws InvalidSetupFileException
    {
        dice = new Dice(2);
        this.width = width;
        this.height = height;
        grid = emptyGrid(width);
        startingSquares = new HashMap<>();
        rooms = new HashMap<>();
        intrigueDeck = createIntrigueDeck();
    }

    /**
     * rolls a pair of dice and returns the value
     *
     * @return int between 2 and 12 representing a dice roll
     */
    public int rollDice()
    {
        return dice.roll();
    }

    /**
     * @return the int[] containing the previous rolls of the dice
     */
    public int[] getLastRolls()
    {
        return dice.getLastRolls();
    }

    /**
     * @return the starting squares of each character
     */
    public HashMap<Character, BoardSquare> getStartingSquares()
    {
        return startingSquares;
    }

    /**
     * @return the rooms with their mappings from roomNumbers
     */
    public HashMap<Integer, Room> getRooms()
    {
        return rooms;
    }

    /**
     * @return List of intrigue cards
     */
    public LinkedList<Card> getIntrigueDeck()
    {
        return intrigueDeck;
    }

    /**
     * @return the envelope
     */
    public Envelope getEnvelope()
    {
        return envelope;
    }

    /**
     * creates and shuffles the pack of intrigue cards.
     *
     * @return a shuffled deck of intrigue cards
     */
    public LinkedList<Card> createIntrigueDeck()
    {
        LinkedList<Card> intrigueDeck = new LinkedList<>();
        intrigueDeck.add(new IntrigueCard(IntrigueType.avoidSuggestion));
        intrigueDeck.add(new IntrigueCard(IntrigueType.avoidSuggestion));
        intrigueDeck.add(new IntrigueCard(IntrigueType.avoidSuggestion));
        intrigueDeck.add(new IntrigueCard(IntrigueType.extraTurn));
        intrigueDeck.add(new IntrigueCard(IntrigueType.extraTurn));
        intrigueDeck.add(new IntrigueCard(IntrigueType.extraTurn));
        intrigueDeck.add(new IntrigueCard(IntrigueType.teleport));
        intrigueDeck.add(new IntrigueCard(IntrigueType.teleport));
        intrigueDeck.add(new IntrigueCard(IntrigueType.teleport));
        intrigueDeck.add(new IntrigueCard(IntrigueType.throwAgain));
        intrigueDeck.add(new IntrigueCard(IntrigueType.throwAgain));
        intrigueDeck.add(new IntrigueCard(IntrigueType.throwAgain));

        shuffleDeck(intrigueDeck);

        return intrigueDeck;
    }

    /**
     * Creates and shuffles the pack of clue cards, as well as placing one each
     * of weapon, room and character in the envelope
     *
     * @return the list of shuffled clue cards
     * @throws InvalidSetupFileException if the board file does not have any
     * rooms
     */
    public LinkedList<Card> createClueDeck() throws InvalidSetupFileException
    {
        LinkedList<Card> roomDeck = new LinkedList<>();
        LinkedList<Card> weaponDeck = new LinkedList<>();
        LinkedList<Card> characterDeck = new LinkedList<>();

        characterDeck.add(new ClueCard(Character.ColMustard));
        characterDeck.add(new ClueCard(Character.MissScarlett));
        characterDeck.add(new ClueCard(Character.MrsPeacock));
        characterDeck.add(new ClueCard(Character.MrsWhite));
        characterDeck.add(new ClueCard(Character.ProfPlum));
        characterDeck.add(new ClueCard(Character.RevGreen));

        try
        {

            for (Room r : rooms.values())
            {
                roomDeck.add(new ClueCard(r));
            }
        } catch (NullPointerException npe)
        {
            throw new InvalidSetupFileException();
        }

        weaponDeck.add(new ClueCard(Weapon.candlestick));
        weaponDeck.add(new ClueCard(Weapon.dagger));
        weaponDeck.add(new ClueCard(Weapon.leadPiping));
        weaponDeck.add(new ClueCard(Weapon.revolver));
        weaponDeck.add(new ClueCard(Weapon.rope));
        weaponDeck.add(new ClueCard(Weapon.spanner));

        shuffleDeck(characterDeck);
        shuffleDeck(roomDeck);
        shuffleDeck(weaponDeck);

        try
        {
            envelope = new Envelope((ClueCard) characterDeck.pop(), (ClueCard) roomDeck.pop(), (ClueCard) weaponDeck.pop());
        } catch (NoSuchElementException e)
        {
            throw new InvalidSetupFileException();
        }

        LinkedList<Card> masterDeck = new LinkedList<>();
        masterDeck.addAll(characterDeck);
        masterDeck.addAll(roomDeck);
        masterDeck.addAll(weaponDeck);

        shuffleDeck(masterDeck);

        return masterDeck;
    }

    /**
     * @return the deck of clue cards
     */
    public LinkedList<Card> getClueDeck()
    {
        return clueDeck;
    }

    /**
     * draws an intrigue card from the top of the deck
     *
     * @return the top intrigue card from the deck
     */
    public IntrigueCard drawIntrigue()
    {
        return (IntrigueCard) intrigueDeck.pop();
    }

    /**
     * returns an intrigue card to the bottom of the deck
     *
     * @param ic the card t return
     */
    public void returnIntrigue(IntrigueCard ic)
    {
        //intrigueDeck;
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
            return null;
        } else
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
                    } else if (east instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) east);
                        currSquare.setAdjacent(1, rooms.get(((RoomSquareDoor) east).getRoomNo()));
                    } else if (south instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) south);
                        currSquare.setAdjacent(2, rooms.get(((RoomSquareDoor) south).getRoomNo()));
                    } else if (west instanceof RoomSquareDoor)
                    {
                        setDoorRoom((RoomSquareDoor) west);
                        currSquare.setAdjacent(3, rooms.get(((RoomSquareDoor) west).getRoomNo()));
                    } else
                    {
                        throw new InvalidSetupFileException();
                    }
                }
                if (currSquare instanceof BoardSquare)
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
                        if (east instanceof BoardSquare)
                        {
                            currSquare.setAdjacent(1, east);
                        }
                    }
                } else if (currSquare instanceof SecretPassage)
                {
                    ((SecretPassage) currSquare).setRoomA(rooms.get(((SecretPassage) currSquare).getRoomNoA()));

                    BoardSpace north = getBoardSpace(x, y - 1);
                    BoardSpace east = getBoardSpace(x + 1, y);
                    BoardSpace south = getBoardSpace(x, y + 1);
                    BoardSpace west = getBoardSpace(x - 1, y);

                    if (north instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) north).getRoomNo()));
                    } //
                    else if (east instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) east).getRoomNo()));
                    } //
                    else if (south instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) south).getRoomNo()));
                    } //
                    else if (west instanceof RoomSquare)
                    {
                        ((SecretPassage) currSquare).setRoomB(rooms.get(((RoomSquare) west).getRoomNo()));
                    }

                    currSquare.setAdjacent(0, ((SecretPassage) currSquare).getRoomB());
                    try
                    {
                        currSquare.setAdjacent(2, ((SecretPassage) currSquare).getRoomA());
                    } //
                    catch (NullPointerException npe)
                    {
                        throw new InvalidSetupFileException();
                    }
                }
            }
        }
    }

    /**
     * links a room with it's doors
     *
     * @param current the BoardSpace currently being processed
     * @throws InvalidSetupFileException if there is no RoomSquare adjacent to
     * the RoomSquareDoor
     */
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
        } else if (east instanceof RoomSquare && !(east instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) east).getRoomNo());
        } else if (south instanceof RoomSquare && !(south instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) south).getRoomNo());
        } else if (west instanceof RoomSquare && !(west instanceof RoomSquareDoor))
        {
            current.setRoomNo(((RoomSquare) west).getRoomNo());
        } else
        {
            throw new InvalidSetupFileException();
        }
    }

    /**
     * creates the room Objects from the unconnected grid.
     */
    public void createRooms() throws InvalidSetupFileException
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
                        } //
                        else if (nextRoomNo == 2)
                        {
                            newRoom = new Room(Room.RoomType.ballRoom);
                        } //
                        else if (nextRoomNo == 3)
                        {
                            newRoom = new Room(Room.RoomType.conservatory);
                        } //
                        else if (nextRoomNo == 4)
                        {
                            newRoom = new Room(Room.RoomType.billiardRoom);
                        } //
                        else if (nextRoomNo == 5)
                        {
                            newRoom = new Room(Room.RoomType.library);
                        } //
                        else if (nextRoomNo == 6)
                        {
                            newRoom = new Room(Room.RoomType.study);
                        } //
                        else if (nextRoomNo == 7)
                        {
                            newRoom = new Room(Room.RoomType.hall);
                        } //
                        else if (nextRoomNo == 8)
                        {
                            newRoom = new Room(Room.RoomType.lounge);
                        } //
                        else if (nextRoomNo == 9)
                        {
                            newRoom = new Room(Room.RoomType.diningRoom);
                        }
                        rooms.put(nextRoomSquare.getRoomNo(), newRoom);
                    }
                    nextRoomSquare.setBelongsTo(rooms.get(nextRoomNo));

                }
            }
        }
        rooms.remove(-1);
        clueDeck = createClueDeck();
    }

    /**
     * Returns a set of all the available spaces that are accessible from a
     * particular BoardSpace given a particular dice roll.
     *
     * uses a breadth first search.
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
            } //
            else if (distanceMappings.get(next) < diceRoll)
            {
                //seen.add(next);
                if (!(next instanceof Room))
                {
                    if (next instanceof SecretPassage)
                    {
                        System.out.println("Secret passage to room " + ((SecretPassage) next).getRoomA());
                        distanceMappings.put(((SecretPassage) next).getRoomA(), 2);
                        toDo.add(((SecretPassage) next).getRoomA());
                    } //
                    else
                    {
                        for (int i = 0; i < 4; i++)
                        {
                            try
                            {
                                for (int j = 0; j < next.getAdjacency().get(i).size(); j++)
                                {
                                    BoardSpace nextChild = next.getAdjacency().get(i).get(j);
                                    if (distanceMappings.keySet().contains(nextChild))
                                    {
                                        if (distanceMappings.get(nextChild) > distanceMappings.get(next) + 1)
                                        {
                                            distanceMappings.put(nextChild, distanceMappings.get(next) + 1);
                                            toDo.add(nextChild);
                                        }
                                    }
                                    if (!toDo.contains(nextChild) && !nextChild.isFull())
                                    {
                                        distanceMappings.put(nextChild, distanceMappings.get(next) + 1);
                                        toDo.add(nextChild);
                                    }
                                }
                            } //
                            catch (NullPointerException npe)
                            {
                                /**
                                 * Adjacency array was null in this location i.e.
                                 * there is no board space in that direction
                                 */
                            }
                        }
                    }
                } //
                else
                {
                    possibleMoves.add(next);
                }
            }
        }
        for (BoardSpace bs : distanceMappings.keySet())
        {
            if (distanceMappings.get(bs) <= diceRoll && !(bs instanceof SecretPassage))
            {
                possibleMoves.add(bs);
            }
        }
        if (!(start instanceof Room))
        {
            possibleMoves.remove(start);
        }
        //System.out.println(possibleMoves);
        return possibleMoves;
    }

    /**
     * Maintains the BoardSquares that each player should start at
     *
     * @param character The character who starts st the specified BoardSquare
     * @param bs The BoardSquare at which the specified character starts at.
     */
    public void setStartSquare(Character character, BoardSquare bs)
    {
        startingSquares.put(character, bs);
    }

    /**
     * Returns the corresponding room to a supplied roomNo
     *
     * @param roomNo The room number to lookup
     * @return Room object represented by roomNo
     */
    public Room getRoom(int roomNo)
    {
        return rooms.get(roomNo);
    }

    /**
     * Shuffles a given deck of cards. Actually just a wrapper for
     * Collections.shuffle()
     *
     * @param deck The deck of cards to shuffle.
     */
    public void shuffleDeck(LinkedList<Card> deck)
    {
        Collections.shuffle(deck);
    }

    public HashSet<RoomSquare> getAllFromRoom(Room room)
    {
        HashSet<RoomSquare> allFromRoom = new HashSet<>();
        for (int x = 1; x <= width; x++)
        {
            for (int y = 1; y <= width; y++)
            {
                BoardSpace nextSpace = getBoardSpace(x, y);
                if (nextSpace instanceof RoomSquare)
                {
                    if (((RoomSquare) nextSpace).belongsTo() == room)
                    {
                        allFromRoom.add((RoomSquare) nextSpace);
                    }
                }
            }
        }
        return allFromRoom;
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
