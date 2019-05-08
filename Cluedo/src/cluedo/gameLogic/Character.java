package cluedo.gameLogic;

/**
 * Represents a character in the game
 *
 * @author Tymek
 */
public enum Character implements ClueType
{

    MissScarlett("Miss Scarlett"), ColMustard("Col Mustard"), MrsWhite("Mrs White"),
    RevGreen("Rev Green"), ProfPlum("Prof Plum"), MrsPeacock("Mrs Peacock");

    private final String nameString;

    /**
     * creates a new Character with the given name
     *
     * @param name the name for the new character
     */
    private Character(String name)
    {
        this.nameString = name;
    }

    /**
     * @return the name of the character
     */
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
