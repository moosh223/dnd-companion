package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class PlayerCharacter {
    private int HP;
    private int[] stats = new int[6];
    private String playerName, characterName, race, className;
    private String filepath="src/main/resources/characters/";
    XMLParser parser = new XMLParser();
    Document xmlDoc;


    public PlayerCharacter(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        this.filepath += playerName.toLowerCase().replace(' ', '_') + "-"
                + characterName.toLowerCase().replace(' ','_')+".xml";
        try{linkToDocument();}catch(Exception e){e.printStackTrace();}
    }

    private void linkToDocument() throws IOException, SAXException, ParserConfigurationException, TransformerException{
        try {
            xmlDoc = parser.buildDocumentStream(filepath);
        }catch(IOException e){
            System.err.println("File doesn't Exist! Creating...");
            xmlDoc = parser.buildNewDocument();
        }
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setStats(int[] stats){this.stats = stats;}
    public void setStat(int statPos, int statValue){stats[statPos] = statValue;}

    public int[] getStats(){return stats;}

    public void setRace(String race) {
        this.race = race;
    }

    public void setClass(String className){this.className = className;}
}
