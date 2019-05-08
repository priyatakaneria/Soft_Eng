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
public class ClueCard extends Card {
    private final ClueType cardType;

    public ClueCard(ClueType card) {
        super(CardType.Clue);
        this.cardType = card;
    }

    public ClueType getClueType() {
        return this.cardType;
    }
}
