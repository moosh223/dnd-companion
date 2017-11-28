package edu.bsu.cs222;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CharacterParser extends XMLParser {

    private String filepath;
    private Document xmlDoc;
    private Map<String, String> tags = new HashMap<>();

    public CharacterParser(String filepath) {
        initMap();
        this.filepath = filepath;
        if (!filepath.contains(".xml")) {
            this.filepath += ".xml";
        }
        try {
            xmlDoc = linkToDocument(this.filepath, "character");
            System.out.println(toString());
            System.out.println("DOC: "+xmlDoc.getDocumentURI());
            readXML();
            System.out.println(toString());
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            buildXML();
        }
        System.out.println(this.filepath);
        updateDoc();
    }

    private void initMap(){
        tags.put("name","");
        tags.put("race","");
        tags.put("classname","0");
        tags.put("stats","0,0,0,0,0,0");
        tags.put("maxhp","0");
        tags.put("currenthp","0");
        tags.put("exp","0");
        tags.put("age","0");
        tags.put("alignment","0");
        tags.put("size","0");
        tags.put("height","0");
        tags.put("speed","");
        tags.put("languages","");
    }

    private void buildXML() {
        System.err.println("NO CHARACTER DATA FOUND! MAKING NOW...");
        System.err.println(getRoot(xmlDoc));
        for (String tag : tags.keySet()) {
            getRoot(xmlDoc).appendChild(xmlDoc.createElement(tag));
        }
    }

    private void readXML() {
        for (String tag : tags.keySet()) {
            tags.put(tag,getRoot(xmlDoc).getElementsByTagName(tag).item(0).getTextContent());
        }
    }

    private void saveXML(){
        for(String tag : tags.keySet()){
            getRoot(xmlDoc).getElementsByTagName(tag).item(0).setTextContent(tags.get(tag));
        }
    }

    /*public String toString() {
        StringBuilder out = new StringBuilder();
        for(int i=0;i<tags.keySet().length - 1;i++){
            out.append(tags.keySet()[i].value).append(",");
        }
        out.append(TagType.values()[TagType.values().length-1].value);
        return out.toString();
    }*/

    public String readTag(String tagName){
        for(String tag : tags.keySet()){
            if(tag.equals(tagName)){
                return tags.get(tag);
            }
        }
        return null;
    }

    public void writeTag(String tagName, String value) {
        for(String tag : tags.keySet()){
            if(tag.equals(tagName)){
                tags.put(tag,value);
            }
        }
        updateDoc();
    }

    private void updateDoc() {
        try {
            saveXML();
            write(xmlDoc, filepath);
        } catch (TransformerException te) {
            te.printStackTrace();
        }
    }

    public void setStat(int stat, int value){
        StringBuilder result = new StringBuilder();
        String[] stats = tags.get("stats").split(",");
        stats[stat] = String.valueOf(value);
        for (int i = 0; i < stats.length - 1; i++) {
            result.append(stats[i]).append(",");
        }
        result.append(stats[stats.length - 1]);
        tags.put("stats",result.toString());
        updateDoc();
    }
}
