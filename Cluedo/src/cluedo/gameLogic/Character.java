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
    
    /*public enum CharacterType
    {

        ColMustard("Col Mustard"), ProfPlum("Prof Plum"), RevGreen("Rev Green"),
        MrsPeacock("Mrs Peacock"), MissScarlett("Miss Scarlett"), MrsWhite("Mrs White");

        private final String nameString;

        CharacterType(String name)
        {
            this.nameString = name;
        }

        public String getCharacterStringName()
        {
            return this.nameString;
        }

    };

    private final CharacterType characterName;

    public Character(CharacterType characterName)
    {
        this.characterName = characterName;
    }

    public CharacterType getCharacterName()
    {
        return this.characterName;
    }*/

}
