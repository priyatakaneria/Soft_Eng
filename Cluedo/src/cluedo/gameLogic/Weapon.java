/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

/**
 * Represents a weapon clue
 * 
 * @author Tymek
 */
public enum Weapon implements ClueType
{

    dagger("dagger"), candlestick("candlestick"), revolver("revolver"),
    rope("rope"), leadPiping("lead piping"), spanner("spanner");

    private final String nameString;

    /**
     * @param name the name of the weapon to create
     */
    Weapon(String name)
    {
        this.nameString = name;
    }

    /**
     * @return a textual representation of this weapon
     */
    public String getWeaponName()
    {
        return this.nameString;
    }
    
    @Override
    public String toString()
    {
        return getWeaponName();
    }
}
