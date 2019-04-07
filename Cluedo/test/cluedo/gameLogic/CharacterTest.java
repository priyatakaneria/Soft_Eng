/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cluedo.gameLogic;

import cluedo.gameLogic.Character.CharacterType;
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

    private CharacterType c1, c2, c3;
    private Character character1, character2, character3;

    @Before
    public void setUp()
    {
        c1 = CharacterType.ColMustard;
        character1 = new Character(c1);

        c2 = CharacterType.MissScarlett;
        character2 = new Character(c2);

        c3 = CharacterType.MrsPeacock;
        character3 = new Character(c3);
    }

    @Test
    public void testGetCharacterStringName()
    {
        String s1 = "Col Mustard";
        String s2 = "Miss Scarlett";
        String s3 = "Mrs Peacock";

        assertEquals(s1, c1.getCharacterStringName());
        assertEquals(s2, c2.getCharacterStringName());
        assertEquals(s3, c3.getCharacterStringName());
    }

    @Test
    public void testGetCharacterName()
    {
        CharacterType e1 = CharacterType.ColMustard;
        CharacterType e2 = CharacterType.MissScarlett;
        CharacterType e3 = CharacterType.MrsPeacock;

        assertEquals(e1, character1.getCharacterName());
        assertEquals(e2, character2.getCharacterName());
        assertEquals(e3, character3.getCharacterName());

    }

}
