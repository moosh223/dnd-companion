package edu.bsu.cs222;

import org.junit.Test;

public class CharacterTest{
    int base_HP = 20;
    int base_STR, base_DEX, base_INT, base_CON, base_CHA, base_WIS = 10;
    PlayerCharacter char1 = new PlayerCharacter();

    @Test
    public void testCharHP() {
        int Health = char1.getHP();
        assert(Health == base_HP);

        char1.setHP(30);
        Health = char1.getHP();
        assert(Health != base_HP);
        assert(Health == char1.getHP());
    }
    @Test
    public void testCharSTR() {
        int Strength = char1.getSTR();
        assert(Strength == base_STR);

        char1.setSTR(12);
        Strength = char1.getSTR();
        assert(Strength != base_STR);
        assert(Strength == char1.getSTR());
    }
    @Test
    public void testCharDEX() {
        int Dexterity = char1.getDEX();
        assert(Dexterity == base_DEX);

        char1.setDEX(14);
        Dexterity = char1.getDEX();
        assert(Dexterity != base_DEX);
        assert(Dexterity == char1.getDEX());
    }
    @Test
    public void testCharINT() {
        int Intelligence = char1.getINT();
        assert(Intelligence == base_INT);

        char1.setINT(13);
        Intelligence = char1.getINT();
        assert(Intelligence != base_INT);
        assert(Intelligence == char1.getINT());
    }
    @Test
    public void testCharCONST() {
        int Constitution = char1.getCON();
        assert(Constitution == base_CON);

        char1.setCONST(8);
        Constitution = char1.getCON();
        assert(Constitution != base_CON);
        assert(Constitution == char1.getCON());
    }
    @Test
    public void testCharCHA() {
        int Charisma = char1.getCHA();
        assert(Charisma == base_CHA);

        char1.setCHA(3);
        Charisma = char1.getCHA();
        assert(Charisma != base_CHA);
        assert(Charisma == char1.getCHA());
    }
    @Test
    public void testCharWIS() {
        int Wisdom = char1.getWIS();
        assert(Wisdom == base_WIS);

        char1.setWIS(15);
        Wisdom = char1.getWIS();
        assert(Wisdom != base_WIS);
        assert(Wisdom == char1.getWIS());
    }
}
