/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.Weapon.WeaponType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tymek
 */
public class WeaponTest
{

    public WeaponTest()
    {
    }

    private WeaponType w1, w2, w3;
    private Weapon weapon1, weapon2, weapon3;

    @Before
    public void setUp()
    {
        w1 = WeaponType.candlestick;
        weapon1 = new Weapon(w1);

        w2 = WeaponType.dagger;
        weapon2 = new Weapon(w2);

        w3 = WeaponType.leadPiping;
        weapon3 = new Weapon(w3);
    }

    @Test
    public void testGetWeaponStringName()
    {
        String s1 = "candlestick";
        String s2 = "dagger";
        String s3 = "lead piping";

        assertEquals(s1, w1.getWeaponStringName());
        assertEquals(s2, w2.getWeaponStringName());
        assertEquals(s3, w3.getWeaponStringName());
    }

    @Test
    public void testGetWeaponName()
    {
        WeaponType e1 = WeaponType.candlestick;
        WeaponType e2 = WeaponType.dagger;
        WeaponType e3 = WeaponType.leadPiping;

        assertEquals(e1, weapon1.getWeaponName());
        assertEquals(e2, weapon2.getWeaponName());
        assertEquals(e3, weapon3.getWeaponName());

    }

}
