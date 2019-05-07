/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.gameBoard.Room;
import cluedo.gameLogic.player.Player;

/**
 * A utility superclass extends by accusation and suggestion as these both
 * essentially store a room, character, weapon and player.
 *
 * @author matth
 */
public abstract class AccuSuggest
{
    private cluedo.gameLogic.gameBoard.Room room;
    private Weapon weapon;
    private Character character;
    private cluedo.gameLogic.player.Player asker;

    /**
     * Stores the supplied arguments.
     * 
     * @param character the murderer
     * @param room the room in which the murder happened
     * @param weapon the weapon which was used
     * @param asker the player who proposed this accusation
     */
    public AccuSuggest(Character character, Room room, Weapon weapon, Player asker)
    {
        this.room = room;
        this.weapon = weapon;
        this.character = character;
        this.asker = asker;
    }

    public Room getRoom()
    {
        return room;
    }

    public Weapon getWeapon()
    {
        return weapon;
    }

    public Character getCharacter()
    {
        return character;
    }

    public Player getAsker()
    {
        return asker;
    }
}
