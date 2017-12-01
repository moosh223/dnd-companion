package edu.bsu.cs222.util;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class CampaignParser extends XMLParser{
    private String filepath;
    private Document xmlDoc;
    private Map<String, String> tags = new TreeMap<>();


    public CampaignParser(String filepath) {
        initMap();
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

    private void initMap() {
        tags.put("name","");
        tags.put("description","");
    }

    private void buildXML() {
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

    public String getTagString() {
        StringBuilder out = new StringBuilder();
        ArrayList<String> tagList = new ArrayList<>(tags.values());
        for(int i=0;i<tagList.size() - 1;i++){
            out.append(tagList.get(i)).append(",");
        }
        out.append(tagList.get(tagList.size()-1));
        return out.toString();
    }

    public String toString() {
        return tags.get("name");
    }

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

    public String getCampaignDirectory() {
        return new File(filepath).getParent();
    }

    private void updateDoc(){
        try{
            saveXML();
            write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
    }
}