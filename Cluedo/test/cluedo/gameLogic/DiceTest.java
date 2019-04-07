package cluedo.gameLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jamie
 */
public class DiceTest
{

    private Dice d1;

    public DiceTest()
    {
    }

    @Before
    public void setUp()
    {
        d1 = new Dice(2, 1000);
    }

    /**
     * asserts that the random rolls are generated within the limits of 2 and 12
     * (for a dice instance with numDice set to 2).
     */
    @Test
    public void diceTest()
    {
        HashMap<Integer, Integer> rolls = new HashMap<>();
        for (int i = 0; i < 100000; i++)
        {
            int newRoll = d1.roll();
            rolls.put(newRoll, rolls.getOrDefault(newRoll, 0) + 1);
            assertTrue(newRoll <= 12);
            assertTrue(newRoll >= 2);
        }
        for (Integer i : rolls.keySet())
        {
            System.out.println(i + ": " + rolls.get(i));
        }
    }
}
