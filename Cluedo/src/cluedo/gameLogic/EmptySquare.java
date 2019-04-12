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
public class EmptySquare extends BoardSpace
{
    public EmptySquare()
    {
        super(0);
    }
    
    /**
     * @return An '_' character to represent an empty space.
     */
    @Override
    public String toString()
    {
        return "_ ";
    }
}
