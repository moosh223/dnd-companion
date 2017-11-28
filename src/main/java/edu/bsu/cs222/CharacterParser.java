package edu.bsu.cs222;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.Arrays;

public class CharacterParser extends XMLParser {

    private String filepath;
    private Document xmlDoc;

    private enum TagType {
        name(""),
        race(""),
        classname(""),
        stats(""),
        maxhp(""),
        currenthp(""),
        exp(""),
        age(""),
        alignment(""),
        size(""),
        height(""),
        speed(""),
        languages("");

        private String value;

        TagType(String value) {
            this.value = value;
        }
    }

    public CharacterParser(String filepath) {
        this.filepath = filepath;
        if (!filepath.contains(".xml")) {
            this.filepath += ".xml";
        }
        try {
            xmlDoc = linkToDocument(this.filepath, "character");
            readXML();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            buildXML();
        }
        updateDoc();
    }

    private void buildXML() {
        for (TagType tag : TagType.values()) {
            getRoot(xmlDoc).appendChild(xmlDoc.createElement(tag.toString()));
        }
    }

    private void readXML() {
        for (TagType tag : TagType.values()) {
            tag.value = getRoot(xmlDoc).getElementsByTagName(tag.toString()).item(0).getTextContent();
        }
    }

    private void saveXML(){
        for(TagType tag : TagType.values()){
            getRoot(xmlDoc).getElementsByTagName(tag.toString()).item(0).setTextContent(tag.value);
        }
    }

    public String toString() {
        StringBuilder out = new StringBuilder();
        for (TagType tag : TagType.values()) {
            out.append(tag.value).append(",");
        }
        return out.toString();
    }

    public String readTag(String tagName){
        for(TagType tag : TagType.values()){
            if(tag.toString().equals(tagName)){
                return tag.value;
            }
        }
        return null;
    }

    public void writeTag(String tagName, String value) {
        for(TagType tag : TagType.values()){
            if(tag.toString().equals(tagName)){
                tag.value = value;
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
        String[] stats = TagType.stats.value.split(",");
        stats[stat] = String.valueOf(value);
        for (int i = 0; i < stats.length - 1; i++) {
            result.append(stats[i]).append(",");
        }
        result.append(stats[stats.length - 1]);
        TagType.stats.value = result.toString();
        updateDoc();
    }
}
