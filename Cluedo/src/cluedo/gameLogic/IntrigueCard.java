package cluedo.gameLogic;

/**
 * Represents an intrigue card
 * 
 * @author Tymek
 */
public class IntrigueCard extends Card {
    
    private final IntrigueType intrigueType;

    /**
     * Creates a new instance and stores the supplied IntrigueType.
     * 
     * @param intrigueType the IntrigueType represented by this IntrigueCard
     */
    public IntrigueCard(IntrigueType intrigueType)
    {
        super(CardType.Intrigue);
        this.intrigueType = intrigueType;
    }

    /**
     * @return the IntrigueType of this card
     */
    public IntrigueType getIntrigueType()
    {
        return this.intrigueType;
    }
    
}
