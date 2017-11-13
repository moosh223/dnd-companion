package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class CampaignCreation {
    private String filepath;
    private XMLParser parser = new XMLParser();
    private Document xmlDoc;

    public Document getXML() {
        return xmlDoc;
    }

    private enum TagType{
        title,
        description,
    }

    public CampaignCreation(String filepath) {
        this.filepath = filepath;
        if(!filepath.contains(".Xml")){
            this.filepath += ".Xml";
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
    private Element getRoot() {
        return xmlDoc.getDocumentElement();
    }

    private void createRootElement() {
        xmlDoc.appendChild(xmlDoc.createElement("campaign"));
    }

    private void updateDoc(){
        try{
            parser.write(xmlDoc,filepath);
        }catch(TransformerException te){
            te.printStackTrace();
        }
    }

    public void setCampaignTitle(String title){
        getRoot().getElementsByTagName("title").item(0).setTextContent(title);
        updateDoc();
    }

    public void setCampaignDescription(String description){
        getRoot().getElementsByTagName("description").item(0).setTextContent(description);
        updateDoc();
    }
}
