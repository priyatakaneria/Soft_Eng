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
public class ClueCard {
    private final ClueType cardType;

    public ClueCard(ClueType card) {
        this.cardType = card;
    }

    public ClueType getCardType() {
        return this.cardType;
    }
}
