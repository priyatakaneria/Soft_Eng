/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

/**
 *
 * @author Jamie Thelin
 */
public class BoardSquare extends BoardSpace
{

    private boolean isIntrigue;

    /**
     * Creates a new BoardSqaure with occupant limit as 1
     *
     * @param isIntrigue Defines whether this square is an 'intrigue space'
     * resulting in a card pickup.
     */
    public BoardSquare(boolean isIntrigue)
    {
        super(1);
        this.isIntrigue = isIntrigue;
    }

    /**
     * @return Whether or not this square is an intrigue space
     */
    public boolean isIntrigue()
    {
        return isIntrigue;
    }

    /**
     * Sets isIntrigue to a new value.
     * @param isIntrigue The new value to set isIntrigue to
     */
    public void setIntrigue(boolean isIntrigue)
    {
        this.isIntrigue = isIntrigue;
    }

}
