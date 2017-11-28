package edu.bsu.cs222;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class PlayerCharacter {
    private String filepath;
    private XMLParser parser = new XMLParser();
    private Document xmlDoc;
    public boolean updated = false;

    public Document getXML() {
        return xmlDoc;
    }

    private enum TagType{
        name,
        race,
        classname,
        stats,
        maxhp,
        currenthp,
        exp,
        age,
        alignment,
        size,
        height,
        speed,
        languages,
    }

    public PlayerCharacter(String filepath) {
        this.filepath = filepath;
        if(!filepath.contains(".xml")){
            this.filepath += ".xml";
        }
        try{
            linkToDocument();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void linkToDocument() throws IOException, SAXException, ParserConfigurationException{
        try {
            xmlDoc = parser.buildDocumentStream(filepath);
        }catch(IOException e){
            xmlDoc = parser.buildNewDocument();
            createRootElement();
            buildXML();
        }
    }
    private void buildXML() {
        for(TagType tag: TagType.values()) {
            getRoot().appendChild(xmlDoc.createElement(tag.toString()));
        }
    }

    public void setPlayerName(String playerName){
        getRoot().setAttribute("owner",playerName);
        updateDoc();
    }
    public void setCharacterName(String name){
        getRoot().getElementsByTagName("name").item(0).setTextContent(name);
        updateDoc();
    }
    public void setRace(String race){
        getRoot().getElementsByTagName("race").item(0).setTextContent(race);
        updateDoc();
    }
    public void setClassName(String className){
        getRoot().getElementsByTagName("classname").item(0).setTextContent(className);
        updateDoc();
    }
    public void setStats(String stats){
        getRoot().getElementsByTagName("stats").item(0).setTextContent(stats);
        updateDoc();
    }
    public void setStat(int statPos, int statValue){
        int[] stats = getStats();
        stats[statPos] = statValue;
        setStats(parser.writeStats(stats));
    }
    public void setMaxHp(String maxHp) {
        getRoot().getElementsByTagName("maxhp").item(0).setTextContent(maxHp);
        updateDoc();
    }
    public void setCurrentHp(String currentHp) {
        getRoot().getElementsByTagName("currenthp").item(0).setTextContent(currentHp);
        updateDoc();
    }
    public void setEXP(String exp) {
        getRoot().getElementsByTagName("exp").item(0).setTextContent(exp);
        updateDoc();
    }
    public void setAge(String age){
        getRoot().getElementsByTagName("age").item(0).setTextContent(age);
        updateDoc();
    }
    public void setAlignment(String alignment){
        getRoot().getElementsByTagName("alignment").item(0).setTextContent(alignment);
        updateDoc();
    }
    public void setSize(String size){
        getRoot().getElementsByTagName("size").item(0).setTextContent(size);
        updateDoc();
    }
    public void setHeight(String height){
        getRoot().getElementsByTagName("height").item(0).setTextContent(height);
        updateDoc();
    }
    public void setSpeed(String speed){
        getRoot().getElementsByTagName("speed").item(0).setTextContent(speed);
        updateDoc();
    }
    public void setLanguages(String languages){
        getRoot().getElementsByTagName("languages").item(0).setTextContent(languages);
        updateDoc();
    }

    private Element getRoot() {
        return xmlDoc.getDocumentElement();
    }
    public String getPlayerName() {
        return getRoot().getAttribute("owner");
    }
    public String getCharacterName(){
        return getRoot().getElementsByTagName("name").item(0).getTextContent();
    }
    public String getRace(){
        return getRoot().getElementsByTagName("race").item(0).getTextContent();
    }
    public String getClassName(){
        return getRoot().getElementsByTagName("classname").item(0).getTextContent();
    }
    public int[] getStats(){
        String stats = getRoot().getElementsByTagName("stats").item(0).getTextContent();
        return parser.parseStats(stats);

    }
    public String getMaxHP() {
        return getRoot().getElementsByTagName("maxhp").item(0).getTextContent();
    }
    public String getAge(){
        return getRoot().getElementsByTagName("age").item(0).getTextContent();
    }
    public String getAlignment(){
        return getRoot().getElementsByTagName("alignment").item(0).getTextContent();
    }
    public String getSize(){
        return getRoot().getElementsByTagName("size").item(0).getTextContent();
    }
    public String getHeight(){
       return getRoot().getElementsByTagName("height").item(0).getTextContent();
    }
    public String getSpeed(){
        return getRoot().getElementsByTagName("speed").item(0).getTextContent();
    }
    public String getLanguages(){
        return getRoot().getElementsByTagName("languages").item(0).getTextContent();
    }
    public String getEXP(){
        return getRoot().getElementsByTagName("exp").item(0).getTextContent();
    }
    public String getCurrentHp(){
        return getRoot().getElementsByTagName("currenthp").item(0).getTextContent();
    }

    private void createRootElement() {
        xmlDoc.appendChild(xmlDoc.createElement("character"));
    }
    private void updateDoc(){
        try{
            parser.write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
    }

    public boolean isUpdated(){
        return updated;
    }
}