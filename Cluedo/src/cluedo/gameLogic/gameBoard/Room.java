package cluedo.gameLogic.gameBoard;

import cluedo.gameLogic.ClueType;

/**
 * Represents the rooms on the board. Unlike other subclasses of BoardSpace Room
 * does not correspond to a visible square, instead a series of RoomSquares
 * belong to this object.
 *
 * @author Tymek
 */
public class Room extends BoardSpace implements ClueType
{

    /**
     * The options available for possible room.
     */
    public enum RoomType
    {

        kitchen("kitchen", 1), ballRoom("ball room", 2), conservatory("conservatory", 3),
        billiardRoom("billiard room", 4), library("library", 5), study("study", 6),
        hall("hall", 7), lounge("lounge", 8), diningRoom("dining room", 9);

        private final String nameString;
        private final int roomNo;

        RoomType(String name, int roomNo)
        {
            this.nameString = name;
            this.roomNo = roomNo;
        }

        /**
         * @return a string representing the name of the room
         */
        public String getRoomStringName()
        {
            return this.nameString;
        }

        /**
         * @return The room number corresponding to the specific room.
         */
        public int getRoomNo()
        {
            return roomNo;
        }
    };

    private SecretPassage secretPassage;
    private final RoomType roomName;

    /**
     * Creates a Room
     *
     * @param roomName the RoomType this Room corresponds to
     */
    public Room(RoomType roomName)
    {
        super(6);
        this.roomName = roomName;
    }

    /**
     * @return the RoomType of this room.
     */
    public RoomType getRoomName()
    {
        return this.roomName;
    }

    /**
     * @return the room number of this Room
     */
    public int getRoomNo()
    {
        return roomName.getRoomNo();
    }

    @Override
    public String toString()
    {
        return roomName.getRoomStringName();
    }
}
