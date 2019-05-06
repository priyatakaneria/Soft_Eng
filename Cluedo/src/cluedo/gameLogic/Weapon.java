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
public enum Weapon implements ClueType
{

    dagger("dagger"), candlestick("candlestick"), revolver("revolver"),
    rope("rope"), leadPiping("lead piping"), spanner("spanner");

    private final String nameString;

    Weapon(String name)
    {
        this.nameString = name;
    }

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
