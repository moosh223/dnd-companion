package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

public class CharacterTest{
    private PlayerCharacter char1 = new PlayerCharacter("Example","Character Name");

    @Test
    public void testCharHP() {
        char1.setMaxHp("25");
        int Health = Integer.parseInt(char1.getHP());
        int base_HP = 20;
        Assert.assertNotEquals(Health, base_HP);
        Assert.assertEquals(Health, Integer.parseInt(char1.getHP()));
    }
    @Test
    public void testCharSTR() {
        char1.setStat(0, 12);
        int Strength = char1.getStats()[0];
        int base_STR = 23;
        assert(Strength != base_STR);
        assert(Strength == char1.getStats()[0]);
    }
    @Test
    public void testCharDEX() {
        char1.setStat(1, 14);
        int Dexterity = char1.getStats()[1];
        int base_DEX = 15;
        assert(Dexterity != base_DEX);
        assert(Dexterity == char1.getStats()[1]);
    }
    @Test
    public void testCharCON() {
        char1.setStat(2, 8);
        int Constitution = char1.getStats()[2];
        int base_CON = 14;
        assert(Constitution != base_CON);
        assert(Constitution == char1.getStats()[2]);
    }
    @Test
    public void testCharINT() {
        char1.setStat(3, 13);
        int Intelligence = char1.getStats()[3];
        int base_INT = 14;
        assert(Intelligence != base_INT);
        assert(Intelligence == char1.getStats()[3]);
    }
    @Test
    public void testCharWIS() {
        char1.setStat(4, 15);
        int Wisdom = char1.getStats()[4];
        int base_WIS = 10;
        assert(Wisdom != base_WIS);
        assert(Wisdom == char1.getStats()[4]);
    }
    @Test
    public void testCharCHA() {
        char1.setStat(5, 3);
        int Charisma = char1.getStats()[5];
        int base_CHA = 12;
        assert(Charisma != base_CHA);
        assert(Charisma == char1.getStats()[5]);
    }

    @Test
    public void testRetrieveName(){
        char1.setCharacterName("Character Name");
        Assert.assertEquals(char1.getCharacterName(),"Character Name");
    }
}
