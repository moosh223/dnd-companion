package edu.bsu.cs222;

public class PlayerCharacter {
    private int HP, STR, DEX, INT, CON, CHA, WIS;
    private String playerName, characterName, race;
    private String filepath="characters/";


    public PlayerCharacter(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        this.filepath += playerName.toLowerCase().replace(' ', '_') + "-"
                + characterName.toLowerCase().replace(' ','_');
        System.out.println(filepath);
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

    public void setRace(String race) {
        this.race = race;
    }
}
