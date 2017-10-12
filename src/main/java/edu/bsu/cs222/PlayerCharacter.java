package edu.bsu.cs222;

public class PlayerCharacter {
    int Health = 20;
    int Strength, Dexterity, Intellegince, Constitution, Charisma, Wisdom = 10;

    public int getHP() {
        return Health;
    }

    public void setHP(int HP) {
        this.Health = HP;
    }

    public int getSTR() {
        return Strength;
    }

    public void setSTR(int STR) {
        this.Strength = STR;
    }

    public int getDEX() {
        return Dexterity;
    }

    public void setDEX(int DEX) {
        this.Dexterity = DEX;
    }

    public int getINT() {
        return Intellegince;
    }

    public void setINT(int INT) {
        this.Intellegince = INT;
    }

    public int getCONST() {
        return Constitution;
    }

    public void setCONST(int CONST) {
        this.Constitution = CONST;
    }

    public int getCHA() {
        return Charisma;
    }

    public void setCHA(int CHA) {
        this.Charisma = CHA;
    }

    public int getWIS() {
        return Wisdom;
    }

    public void setWIS(int WIS) {
        this.Wisdom = WIS;
    }
}
