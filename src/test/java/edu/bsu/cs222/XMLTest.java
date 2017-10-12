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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLTest {

    @Test
    public void testXMLRetrieval(){
        String doctype = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File("src/test/resources/example.xml"));
            doctype = document.getDocumentElement().getNodeName();
        }catch(ParserConfigurationException x){
            x.printStackTrace();
        }catch(SAXException x){
            x.printStackTrace();
        }catch(IOException x){
            x.printStackTrace();
        }

        Assert.assertNotNull(doctype);
    }

    @Test
    public void testGetNameFromXML(){
        String name = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File("src/test/resources/example.xml"));
            Element root = document.getDocumentElement();
            name = root.getElementsByTagName("name").item(0).getTextContent();
        }catch(ParserConfigurationException x){
            x.printStackTrace();
        }catch(SAXException x){
            x.printStackTrace();
        }catch(IOException x){
            x.printStackTrace();
        }

        Assert.assertEquals(name, "New Character Name");
    }

    @Test
    public void testGetStatsFromXML(){
        int[] stats = new int[6];
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File("src/test/resources/example.xml"));
            Element root = document.getDocumentElement();
            String[] statText = root.getElementsByTagName("stats").item(0).getTextContent().split(",");
            for(int i = 0; i < 6; i++){
                stats[i] = Integer.parseInt(statText[i]);
            }
        }catch(ParserConfigurationException x){
            x.printStackTrace();
        }catch(SAXException x){
            x.printStackTrace();
        }catch(IOException x){
            x.printStackTrace();
        }

        Assert.assertEquals(stats[2],14);
    }

    @Test
    public void testXMLNameChange(){
        String name = null;
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File("src/test/resources/example.xml"));
            Element root = document.getDocumentElement();
            root.getElementsByTagName("name").item(0).setTextContent("New Character Name");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("src/test/resources/example.xml"));
            transformer.transform(source, result);

            root = document.getDocumentElement();
            name = root.getElementsByTagName("name").item(0).getTextContent();
        }catch(ParserConfigurationException x){
            x.printStackTrace();
        }catch(SAXException x){
            x.printStackTrace();
        }catch(IOException x){
            x.printStackTrace();
        }catch(TransformerException x){
            x.printStackTrace();
        }

        Assert.assertEquals(name, "New Character Name");
    }
}
