package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import cluedo.userInterface.boardTiles.BoardSpacePane;

/**
 * An abstract implementation of an arbitrary space that players can enter on
 * the Cluedo board.
 *
 * Each instance has an adjacency HashMap which maps integer 0 through 3 (for
 * North, East, South and West) to an ArrayList of BoardSpaces, describing the
 * spaces which a player can move into from this space.
 *
 * @author Jamie Thelin
 */
public abstract class BoardSpace
{

    private ArrayList<Player> occupants;
    private HashMap<Integer, ArrayList<BoardSpace>> adjacency;
    private int occupantLimit;
    private BoardSpacePane guiPane;

    /**
     * Creates a new board space and sets the max number of occupants
     *
     * @param occupantLimit The maximum number of players allowed in this
     * instance of a board space (1 for a regular square, 6 for a room)
     */
    public BoardSpace(int occupantLimit)
    {
        this.occupants = new ArrayList<>();
        this.occupantLimit = occupantLimit;
        this.adjacency = new HashMap<>();
        for (int i = 0; i < 4; i++)
        {
            this.adjacency.put(i, new ArrayList<>());
        }
    }

    /**
     * @return true if the space is at capacity, false otherwise.
     */
    public boolean isFull()
    {
        return occupantLimit == occupants.size();
    }

    /**
     * Adds a player to a space, if it is not at capacity.
     *
     * @param p The player object to add to the space.
     * @return true if the player was added successfully, false if the space is
     * full.
     */
    public boolean addOccupant(Player p)
    {
        if (!isFull())
        {
            occupants.add(p);
            return true;
        }
        return false;
    }

    /**
     * Removes a player from a space, providing it is currently there.
     *
     * @param p The player object to remove from the space.
     * @return true if the player was removed successfully, false if the space
     * does not contain the player.
     */
    public boolean removeOccupant(Player p)
    {
        if (occupants.contains(p))
        {
            occupants.remove(p);
            return true;
        }
        return false;
    }

    /**
     * @return The list of Players currently in the space.
     */
    public ArrayList<Player> getOccupants()
    {
        return occupants;
    }

    /**
     * @return The array describing any adjacent spaces.
     */
    public HashMap<Integer, ArrayList<BoardSpace>> getAdjacency()
    {
        return adjacency;
    }

    /**
     * Sets the corresponding position in the adjacency array to a new
     * BoardSpace, and performs a recursive call on the provided space to make
     * sure both BoardSpaces are kept up to date.
     *
     * @param index The position in the adjacency array to set (0=North, 1=East,
     * 2=South, 3=West).
     * @param space The BoardSpace to store in the adjacency.
     */
    public void setAdjacent(int index, BoardSpace space)
    {
        adjacency.get(index).add(space);
        if (!space.getAdjacency().get((index + 2) % 4).contains(this))
        {
            space.setAdjacent((index + 2) % 4, this);
        }
    }

    /**
     * @return The maximum number of allowed occupants.
     */
    public int getOccupantLimit()
    {
        return occupantLimit;
    }

    /**
     * Sets the occupant limit for this space, probably unused...
     *
     * @param occupantLimit The new value for the number of allowed occupants
     */
    public void setOccupantLimit(int occupantLimit)
    {
        this.occupantLimit = occupantLimit;
    }

    /**
     * Sets the GUI object representing this logical BoardSpace.
     *
     * @param bsp the BoardSquarePane which to store
     */
    public void setGuiPane(BoardSpacePane bsp)
    {
        guiPane = bsp;
    }

    /**
     * @return the GUI object stored by this BoardSpace
     */
    public BoardSpacePane getGuiPane()
    {
        return guiPane;
    }
}
