package edu.bsu.cs222;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CampaignParser extends XMLParser{
    private String filepath;
    private Document xmlDoc;

    public Document getXML() {
        return xmlDoc;
    }

    public enum TagType{
        name(""),
        description("");

        private String value;
        TagType(String value){
            this.value = value;
        }
    }

    public CampaignParser(String filepath) {
        this.filepath = filepath;
        if(!filepath.contains(".xml")){
            this.filepath += ".xml";
        }
        try{
            xmlDoc = linkToDocument(this.filepath,"campaign");
            readXML();
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe){
            buildXML();
        }
        updateDoc();
    }

    private void buildXML() {
        for(TagType tag: TagType.values()) {
            getRoot(xmlDoc).appendChild(xmlDoc.createElement(tag.toString()));
        }
    }
    public void readXML() {
        for(TagType tag: TagType.values()) {
            tag.value = getRoot(xmlDoc).getElementsByTagName(tag.toString()).item(0).getTextContent();
        }
    }
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(TagType tag: TagType.values()) {
            out.append(tag.value+",");
        }
        return out.toString();
    }


    private void updateDoc(){
        try{
            write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
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
    }
}