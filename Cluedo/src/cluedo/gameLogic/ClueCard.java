package cluedo.gameLogic;

/**
 * Represents an instance of a clue card, stores a clue (this is an instance of
 * ClueType, which can be a room, character or weapon.
 *
 * @author Tymek
 */
public class ClueCard extends Card
{
    private final ClueType cardType;

    /**
     * Stores the clue specified in the arguments.
     *
     * @param clue the clue this object should represent
     */
    public ClueCard(ClueType clue)
    {
        super(CardType.Clue);
        this.cardType = clue;
    }

    /**
     * @return returns the clue this card represents
     */
    public ClueType getClueType()
    {
        return this.cardType;
    }
}
