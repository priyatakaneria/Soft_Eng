/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.Weapon;
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

    private Weapon w1, w2, w3;

    @Before
    public void setUp()
    {
        w1 = Weapon.candlestick;

        w2 = Weapon.dagger;

        w3 = Weapon.leadPiping;
    }

    @Test
    public void testGetWeaponStringName()
    {
        String s1 = "candlestick";
        String s2 = "dagger";
        String s3 = "lead piping";

        assertEquals(s1, w1.getWeaponName());
        assertEquals(s2, w2.getWeaponName());
        assertEquals(s3, w3.getWeaponName());
    }
}
