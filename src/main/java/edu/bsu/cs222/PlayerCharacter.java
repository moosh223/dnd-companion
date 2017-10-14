package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class PlayerCharacter {
    private String HP;
    private int[] stats = new int[6];
    private String playerName, characterName, race, className;
    private String filepath="src/main/resources/characters/";
    private XMLParser parser = new XMLParser();
    private Document xmlDoc;
    private Element root;


    public PlayerCharacter(String playerName, String characterName) {
        this.playerName = playerName;
        this.characterName = characterName;
        this.filepath += playerName.toLowerCase().replace(' ', '_') + "-"
                + characterName.toLowerCase().replace(' ','_')+".xml";
        try{linkToDocument();}catch(Exception e){e.printStackTrace();}
    }

    private void linkToDocument() throws IOException, SAXException, ParserConfigurationException{
        try {
            xmlDoc = parser.buildDocumentStream(filepath);
        }catch(IOException e){
            System.err.println("File doesn't Exist! Creating...");
            xmlDoc = parser.buildNewDocument();
            createRootElement();
            setCharacterName(characterName);
        }
    }

    public String getHP() {
        return HP;
    }

    public void setHP(String hp) {
        root.appendChild(xmlDoc.createElement("hp")).setTextContent(hp);
        updateDoc();
    }

    public void setNumStats(int[] stats){this.stats = stats;}
    public void setStat(int statPos, int statValue){stats[statPos] = statValue;}

    public int[] getStats(){return stats;}

    public void setRace(String race){
        root.appendChild(xmlDoc.createElement("race")).setTextContent(race);
        updateDoc();
    }

    public void setStats(String stats){
        root.appendChild(xmlDoc.createElement("stats")).setTextContent(stats);
        updateDoc();
    }
    public void setCharacterName(String name){
        root.appendChild(xmlDoc.createElement("name")).setTextContent(name);
        updateDoc();
    }
    public void setClass(String className){
        root.appendChild(xmlDoc.createElement("class")).setTextContent(className);
        updateDoc();
    }
    public void setAge(String age){
        root.appendChild(xmlDoc.createElement("age")).setTextContent(age);
        updateDoc();
    }
    public void setHeight(String height){
        root.appendChild(xmlDoc.createElement("height")).setTextContent(height);
        updateDoc();
    }
    public void setAlignment(String alignment){
        root.appendChild(xmlDoc.createElement("alignment")).setTextContent(alignment);
        updateDoc();
    }
    public void setSpeed(String speed){
        root.appendChild(xmlDoc.createElement("speed")).setTextContent(speed);
        updateDoc();
    }
    public void setSize(String size){
        root.appendChild(xmlDoc.createElement("size")).setTextContent(size);
        updateDoc();
    }
    public void setLanguages(String languages){
        root.appendChild(xmlDoc.createElement("languages")).setTextContent(languages);
        updateDoc();
    }

    public String getSize(){
        return xmlDoc.getDocumentElement().getElementsByTagName("size").item(0).getTextContent();
    }

    public String getPlayerName() {
        return playerName;
    }

    private void createRootElement() {
        root = xmlDoc.createElement("character");
        xmlDoc.appendChild(root);
        root.setAttribute("owner",playerName);
    }

    private void updateDoc(){
        try{
            parser.write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
    }
}
