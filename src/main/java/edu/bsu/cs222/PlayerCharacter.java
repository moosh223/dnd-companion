package edu.bsu.cs222;

public class PlayerCharacter {
    int HP = 20;
    int STR, DEX, INT, CON, CHA, WIS = 10;

    public PlayerCharacter(){
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getSTR() {
        return STR;
    }

    public void setSTR(int STR) {
        this.STR = STR;
    }

    public int getDEX() {
        return DEX;
    }

    public void setDEX(int DEX) {
        this.DEX = DEX;
    }

    public int getINT() {
        return INT;
    }

    public void setINT(int INT) {
        this.INT = INT;
    }

    public int getCON() { return CON; }

    public void setCONST(int CON) {
        this.CON = CON;
    }

    public int getCHA() {
        return CHA;
    }

    public void setCHA(int CHA) {
        this.CHA = CHA;
    }

    public int getWIS() {
        return WIS;
    }

    public void setWIS(int WIS) {
        this.WIS = WIS;
    }
}
