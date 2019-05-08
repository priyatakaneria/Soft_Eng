package cluedo.gameLogic;

/**
 * Represents the four types of Intrigue Card.
 * 
 * @author Tymek
 */
public enum IntrigueType {

    extraTurn("extra turn"), throwAgain("throw again"), teleport("teleport"),
    avoidSuggestion("avoid suggestion");

    private final String intrigueString;

    /**
     * @param intrigueString the string to setup the IntrigueType with.
     */
    IntrigueType(String intrigueString) {
        this.intrigueString = intrigueString;
    }

    /**
     * @return returns the string associated with the IntrigueType
     */
    public String getIntrigueString() {
        return this.intrigueString;
    }

};
