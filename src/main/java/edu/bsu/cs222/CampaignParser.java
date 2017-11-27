package edu.bsu.cs222;

import edu.bsu.cs222.util.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class CampaignParser extends XMLParser{
    private String filepath;
    private Document xmlDoc;

    public Document getXML() {
        return xmlDoc;
    }

    private enum TagType{
        name,
        description,
    }

    public CampaignParser(String filepath) {
        this.filepath = filepath;
        if(!filepath.contains(".xml")){
            this.filepath += ".xml";
        }
        try{
            linkToDocument(filepath);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void buildXML() {
        for(TagType tag: TagType.values()) {
            getRoot().appendChild(xmlDoc.createElement(tag.toString()));
        }
    }
    private Element getRoot() {
        return xmlDoc.getDocumentElement();
    }

    private void createRootElement() {
        xmlDoc.appendChild(xmlDoc.createElement("campaign"));
    }

    private void updateDoc(){
        try{
            write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
    }

    public void setCampaignName(String name){
        getRoot().getElementsByTagName("name").item(0).setTextContent(name);
        updateDoc();
    }

    public void setCampaignDescription(String description){
        getRoot().getElementsByTagName("description").item(0).setTextContent(description);
        updateDoc();
    }
}