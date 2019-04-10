/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

/**
 *
 * @author Tymek
 */
public class Card {
    
    public enum CardType
    {

        Clue("Clue"), Intrigue("Intrigue");

        private final String nameString;

        CardType(String name)
        {
            this.nameString = name;
        }

        public String getCardStringName()
        {
            return this.nameString;
        }

    };

    private final CardType cardName;

    public Card(CardType cardName)
    {
        this.cardName = cardName;
    }

    public CardType getCardName()
    {
        return this.cardName;
    }

}
