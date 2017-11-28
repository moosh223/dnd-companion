package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

public class CharacterTest{
    private CharacterParser char1 = new CharacterParser("src/test/resources/example.xml");

    @Test
    public void testCharHP() {
        char1.writeTag("maxhp","25");
        int Health = Integer.parseInt(char1.readTag("maxhp"));
        int base_HP = 20;
        Assert.assertNotEquals(Health, base_HP);
        Assert.assertEquals(Health, Integer.parseInt(char1.readTag("maxhp")));
    }
    @Test
    public void testCharSTR() {
        char1.setStat(0, 12);
        int Strength = Integer.parseInt(char1.readTag("stats").split(",")[0]);
        int base_STR = 23;
        assert(Strength != base_STR);
        assert(Strength == Integer.parseInt(char1.readTag("stats").split(",")[0]));
    }
    @Test
    public void testCharDEX() {
        char1.setStat(1, 14);
        int Dexterity = Integer.parseInt(char1.readTag("stats").split(",")[1]);
        int base_DEX = 15;
        assert(Dexterity != base_DEX);
        assert(Dexterity == Integer.parseInt(char1.readTag("stats").split(",")[1]));
    }
    @Test
    public void testCharCON() {
        char1.setStat(2, 14);
        int Constitution = Integer.parseInt(char1.readTag("stats").split(",")[2]);
        int base_CON = 14;
        assert(Constitution == Integer.parseInt(char1.readTag("stats").split(",")[2]));
    }
    @Test
    public void testCharINT() {
        char1.setStat(3, 13);
        int Intelligence = Integer.parseInt(char1.readTag("stats").split(",")[3]);
        int base_INT = 14;
        assert(Intelligence != base_INT);
        assert(Intelligence == Integer.parseInt(char1.readTag("stats").split(",")[3]));
    }
    @Test
    public void testCharWIS() {
        char1.setStat(4, 15);
        int Wisdom = Integer.parseInt(char1.readTag("stats").split(",")[4]);
        int base_WIS = 10;
        assert(Wisdom != base_WIS);
        assert(Wisdom == Integer.parseInt(char1.readTag("stats").split(",")[4]));
    }
    @Test
    public void testCharCHA() {
        char1.setStat(5, 3);
        int Charisma = Integer.parseInt(char1.readTag("stats").split(",")[5]);
        int base_CHA = 12;
        assert(Charisma != base_CHA);
        assert(Charisma == Integer.parseInt(char1.readTag("stats").split(",")[5]));
    }

    @Test
    public void testRetrieveName(){
        char1.writeTag("name","Character Name");
        Assert.assertEquals(char1.readTag("name"),"Character Name");
    }
}
