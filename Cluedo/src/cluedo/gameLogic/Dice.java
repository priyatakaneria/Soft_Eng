/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import java.util.Random;

/**
 *
 * @author jamie
 */
public class Dice
{

    private int numDice;
    private Random rand;

    /**
     * creates a new instance of Dice simulating multiple dice rolls as defined
     * by numDice
     *
     * @param numDice the number of six sided dice to simulate with this
     * instance of the class
     */
    public Dice(int numDice)
    {
        this.numDice = numDice;
        this.rand = new Random();
    }

    public int getNumDice()
    {
        return numDice;
    }

    public void setNumDice(int numDice)
    {
        this.numDice = numDice;
    }

    /**
     * generates the dice with a random seed, used for debugging and testing
     *
     * @param numDice the number of six sided dice to simulate with this
     * instance of the class
     * @param seed
     */
    public Dice(int numDice, long seed)
    {
        this.numDice = numDice;
        this.rand = new Random(seed);
    }

    /**
     * @return the sum of a series of simulated dice rolls, one for each die as
     * defined by numDice
     */
    public int roll()
    {
        int sum = 0;
        for (int i = 0; i < numDice; i++)
        {
            sum += rand.nextInt(6) + 1;
        }
        return sum;
    }
}
