/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 *
 * @author matth
 */
public class AccuSuggest 
{
    private cluedo.gameLogic.gameBoard.Room room;
    private Weapon weapon;
    private Character character;
    private cluedo.gameLogic.player.Player asker;

    public AccuSuggest(Room room, Weapon weapon, Character character, Player asker) {
        this.room = room;
        this.weapon = weapon;
        this.character = character;
        this.asker = asker;
    }

    public Room getRoom() {
        return room;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Character getCharacter() {
        return character;
    }

    public Player getAsker() {
        return asker;
    }
}
