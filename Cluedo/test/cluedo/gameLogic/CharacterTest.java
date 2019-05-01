/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.Character;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tymek
 */
public class CharacterTest
{

    public CharacterTest()
    {
    }

    private Character c1, c2, c3;
    // private Character character1, character2, character3;

    @Before
    public void setUp()
    {
        c1 = Character.ColMustard;
        // character1 = new Character(c1);

        c2 = Character.MissScarlett;
        // character2 = new Character(c2);

        c3 = Character.MrsPeacock;
        // character3 = new Character(c3);
    }

    @Test
    public void testGetCharacterStringName()
    {
        String s1 = "Col Mustard";
        String s2 = "Miss Scarlett";
        String s3 = "Mrs Peacock";

        assertEquals(s1, c1.getCharacterName());
        assertEquals(s2, c2.getCharacterName());
        assertEquals(s3, c3.getCharacterName());
    }
}
