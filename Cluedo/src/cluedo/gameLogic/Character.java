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
public class Character implements ClueType
{

    public enum CharacterType
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
    }

}
