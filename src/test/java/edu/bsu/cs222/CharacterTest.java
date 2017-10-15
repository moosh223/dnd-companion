package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

public class CharacterTest{
    private int base_HP = 20;
    private int base_STR, base_DEX, base_INT, base_CON, base_CHA, base_WIS = 10;
    private PlayerCharacter char1 = new PlayerCharacter("Example","Character Name");

    @Test
    public void testCharHP() {
        char1.setHP("25");
        int Health = Integer.parseInt(char1.getHP());
        Assert.assertNotEquals(Health, base_HP);
        Assert.assertEquals(Health, Integer.parseInt(char1.getHP()));
    }
    @Test
    public void testCharSTR() {
        char1.setStat(0, 12);
        int Strength = char1.getStats()[0];
        assert(Strength != base_STR);
        assert(Strength == char1.getStats()[0]);
    }
    @Test
    public void testCharDEX() {
        char1.setStat(1, 14);
        int Dexterity = char1.getStats()[1];
        assert(Dexterity != base_DEX);
        assert(Dexterity == char1.getStats()[1]);
    }
    @Test
    public void testCharCON() {
        char1.setStat(2, 8);
        int Constitution = char1.getStats()[2];
        assert(Constitution != base_CON);
        assert(Constitution == char1.getStats()[2]);
    }
    @Test
    public void testCharINT() {
        char1.setStat(3, 13);
        int Intelligence = char1.getStats()[3];
        assert(Intelligence != base_INT);
        assert(Intelligence == char1.getStats()[3]);
    }
    @Test
    public void testCharWIS() {
        char1.setStat(4, 15);
        int Wisdom = char1.getStats()[4];
        assert(Wisdom != base_WIS);
        assert(Wisdom == char1.getStats()[4]);
    }
    @Test
    public void testCharCHA() {
        char1.setStat(5, 3);
        int Charisma = char1.getStats()[5];
        assert(Charisma != base_CHA);
        assert(Charisma == char1.getStats()[5]);
    }

    @Test
    public void testRetrieveName(){
        Assert.assertEquals(char1.getCharacterName(),"Character Name");
    }
}
