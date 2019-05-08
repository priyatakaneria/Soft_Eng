package cluedo.gameLogic;

/**
 * Represents an instance of a card that the player can hold. Is extended by
 * ClueCard and IntrigueCard.
 *
 * @author Tymek
 */
public class Card
{

    public enum CardType
    {

        Clue("Clue"), Intrigue("Intrigue");

        private final String cardTypeString;

        /**
         * stores the cardTypeName provided.
         *
         * @param cardTypeName
         */
        CardType(String cardTypeName)
        {
            this.cardTypeString = cardTypeName;
        }

        /**
         * @return the textual representation of the card type (either Clue or
         * Intrigue)
         */
        public String getCardStringName()
        {
            return this.cardTypeString;
        }

    };

    private final CardType cardType;

    /**
     * @param cardType stores the type of card this represents
     */
    public Card(CardType cardType)
    {
        this.cardType = cardType;
    }

    /**
     * @return returns the type of card this object represents
     */
    public CardType getCardType()
    {
        return this.cardType;
    }

}
