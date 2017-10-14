package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class PlayerCharacter {
    private int[] stats = new int[6];
    private String playerName;
    private String filepath="src/main/resources/characters/";
    private XMLParser parser = new XMLParser();
    private Document xmlDoc;
    private Element root;


    public PlayerCharacter(String playerName, String characterName) {
        this.playerName = playerName;
        this.filepath += playerName.toLowerCase().replace(' ', '_') + "-"
                + characterName.toLowerCase().replace(' ','_')+".xml";
        try{
            linkToDocument();
            setCharacterName(characterName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }



    private void linkToDocument() throws IOException, SAXException, ParserConfigurationException{
        try {
            xmlDoc = parser.buildDocumentStream(filepath);
            root = xmlDoc.getDocumentElement();
        }catch(IOException e){
            System.err.println("File doesn't Exist! Creating...");
            xmlDoc = parser.buildNewDocument();
            createRootElement();
            buildXML();
        }
    }
    public void setNumStats(int[] stats){this.stats = stats;}
    public void setStat(int statPos, int statValue){stats[statPos] = statValue;}

    public int[] getNumStats(){return stats;}

    private void buildXML() {
        root.appendChild(xmlDoc.createElement("name"));
        root.appendChild(xmlDoc.createElement("race"));
        root.appendChild(xmlDoc.createElement("class"));
        root.appendChild(xmlDoc.createElement("stats"));
        root.appendChild(xmlDoc.createElement("hp"));
        root.appendChild(xmlDoc.createElement("exp"));
        root.appendChild(xmlDoc.createElement("age"));
        root.appendChild(xmlDoc.createElement("alignment"));
        root.appendChild(xmlDoc.createElement("size"));
        root.appendChild(xmlDoc.createElement("height"));
        root.appendChild(xmlDoc.createElement("speed"));
        root.appendChild(xmlDoc.createElement("languages"));
    }
    private void setCharacterName(String name){
        root.getElementsByTagName("name").item(0).setTextContent(name);
        updateDoc();
    }
    public void setRace(String race){
        root.getElementsByTagName("race").item(0).setTextContent(race);
        updateDoc();
    }
    public void setClassName(String className){
        root.getElementsByTagName("class").item(0).setTextContent(className);
        updateDoc();
    }
    public void setStats(String stats){
        root.getElementsByTagName("stats").item(0).setTextContent(stats);
        updateDoc();
    }
    public void setHP(String hp) {
        root.getElementsByTagName("hp").item(0).setTextContent(hp);
        updateDoc();
    }
    public void setAge(String age){
        root.getElementsByTagName("age").item(0).setTextContent(age);
        updateDoc();
    }
    public void setAlignment(String alignment){
        root.getElementsByTagName("alignment").item(0).setTextContent(alignment);
        updateDoc();
    }
    public void setSize(String size){
        root.getElementsByTagName("size").item(0).setTextContent(size);
        updateDoc();
    }
    public void setHeight(String height){
        root.getElementsByTagName("height").item(0).setTextContent(height);
        updateDoc();
    }
    public void setSpeed(String speed){
        root.getElementsByTagName("speed").item(0).setTextContent(speed);
        updateDoc();
    }
    public void setLanguages(String languages){
        root.getElementsByTagName("languages").item(0).setTextContent(languages);
        updateDoc();
    }

    public String getPlayerName() {
        return root.getAttribute("owner");
    }
    public String getCharacterName(){
        return root.getElementsByTagName("name").item(0).getTextContent();
    }
    public String getRace(){
        return root.getElementsByTagName("race").item(0).getTextContent();
    }
    public String getClassName(){
        return root.getElementsByTagName("class").item(0).getTextContent();
    }
    public String getStats(){
        return root.getElementsByTagName("stats").item(0).getTextContent();
    }
    public String getHP() {
        return root.getElementsByTagName("hp").item(0).getTextContent();
    }
    public String getAge(){
        return root.getElementsByTagName("age").item(0).getTextContent();
    }
    public String getAlignment(){
        return root.getElementsByTagName("alignment").item(0).getTextContent();
    }
    public String getSize(){
        return root.getElementsByTagName("size").item(0).getTextContent();
    }
    public String getHeight(){
       return root.getElementsByTagName("height").item(0).getTextContent();
    }
    public String getSpeed(){
        return root.getElementsByTagName("speed").item(0).getTextContent();
    }
    public String getLanguages(){
        return root.getElementsByTagName("languages").item(0).getTextContent();
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

    public Element getRoot() {
        return root;
    }
}
