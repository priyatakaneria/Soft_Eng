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
public enum IntrigueType {

    extraTurn("extra turn"), throwAgain("throw again"), teleport("teleport"),
    avoidSuggestion("avoid suggestion");

    private final String nameString;

    IntrigueType(String name) {
        this.nameString = name;
    }

    public String getCharacterStringName() {
        return this.nameString;
    }

};
