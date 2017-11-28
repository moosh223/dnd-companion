package edu.bsu.cs222.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class XMLParser {
    private Document buildDocumentStream(String filepath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        System.err.println("Completed doc: "+builder.parse(new File(filepath)));
        return builder.parse(new File(filepath));
    }

    private Transformer buildTransformer() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        return transformerFactory.newTransformer();
    }

    protected void write(Document document, String filepath) throws TransformerException{
        Transformer transformer = buildTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }

    private Document buildNewDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        return builder.newDocument();
    }

    public Document linkToDocument(String filepath, String rootName) throws IOException, SAXException, ParserConfigurationException{
        Document xmlDoc;
        try {
            System.out.println("Build Path: "+filepath);
            xmlDoc = buildDocumentStream(filepath);
        }catch(IOException e){
            System.err.println("building new");
            xmlDoc = buildNewDocument();
            createRootElement(xmlDoc, rootName);
        }
        return xmlDoc;
    }


    private void createRootElement(Document document, String rootName) {
        document.appendChild(document.createElement(rootName));
    }


    protected Element getRoot(Document document) {
        return document.getDocumentElement();
    }
}