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
public class IntrigueCard extends Card {
    
    private final IntrigueType intrigueName;

    public IntrigueCard(IntrigueType cardName)
    {
        super(CardType.Intrigue);
        this.intrigueName = cardName;
    }

    public IntrigueType getIntrigueType()
    {
        return this.intrigueName;
    }
    
}
