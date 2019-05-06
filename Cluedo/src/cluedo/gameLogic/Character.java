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
public enum Character implements ClueType
{

    MissScarlett("Miss Scarlett"), ColMustard("Col Mustard"), MrsWhite("Mrs White"),
    RevGreen("Rev Green"), ProfPlum("Prof Plum"), MrsPeacock("Mrs Peacock");
    
    private final String nameString;

    Character(String name)
    {
        this.nameString = name;
    }

    public String getCharacterName()
    {
        return this.nameString;
    }
    
    @Override
    public String toString()
    {
        return getCharacterName();
    }
}
