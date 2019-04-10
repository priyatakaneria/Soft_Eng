/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.Card.CardType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tymek
 */
public class CardTest
{

    public CardTest()
    {
    }

    private CardType c1, c2;
    private Card card1, card2;

    @Before
    public void setUp()
    {
        c1 = CardType.Clue;
        card1 = new Card(c1);

        c2 = CardType.Intrigue;
        card2 = new Card(c2);

    }

    @Test
    public void testGetCardStringName()
    {
        String s1 = "Clue";
        String s2 = "Intrigue";

        assertEquals(s1, c1.getCardStringName());
        assertEquals(s2, c2.getCardStringName());
    }

    @Test
    public void testGetCharacterName()
    {
        CardType e1 = CardType.Clue;
        CardType e2 = CardType.Intrigue;

        assertEquals(e1, card1.getCardName());
        assertEquals(e2, card2.getCardName());

    }

}
