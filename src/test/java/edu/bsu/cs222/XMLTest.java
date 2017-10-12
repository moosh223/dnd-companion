package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLTest {
    String path = "src/test/resources/example.xml";

    @Test
    public void testXMLRetrieval() throws IOException, SAXException, ParserConfigurationException {
        String doctype = null;
        XMLParser parser = new XMLParser();
        Document document = parser.buildDocumentStream(path);
        doctype = document.getDocumentElement().getNodeName();
        Assert.assertNotNull(doctype);
    }

    @Test
    public void testGetNameFromXML() throws IOException, SAXException, ParserConfigurationException {
        String name = null;
        XMLParser parser = new XMLParser();
        Document document = parser.buildDocumentStream(path);
        Element root = document.getDocumentElement();
        name = root.getElementsByTagName("name").item(0).getTextContent();
        Assert.assertEquals(name, "Character Name");
    }

    @Test
    public void testGetStatsFromXML() throws IOException, SAXException, ParserConfigurationException {
        int[] stats = new int[6];
        XMLParser parser = new XMLParser();
        Document document = parser.buildDocumentStream(path);
        Element root = document.getDocumentElement();
        String[] statText = root.getElementsByTagName("stats").item(0).getTextContent().split(",");
        for(int i = 0; i < 6; i++){
            stats[i] = Integer.parseInt(statText[i]);
        }

        Assert.assertEquals(stats[2],14);
    }

    @Test
    public void testXMLNameChange() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        String name = null;
        XMLParser parser = new XMLParser();
        Document document = parser.buildDocumentStream(path);
        Element root = document.getDocumentElement();
        String originalName = root.getElementsByTagName("name").item(0).getTextContent();
        root.getElementsByTagName("name").item(0).setTextContent("New Character Name");
        //parser.overwrite(document, path);
        root = document.getDocumentElement();
        name = root.getElementsByTagName("name").item(0).getTextContent();

        Assert.assertNotEquals(name, originalName);
    }
}
