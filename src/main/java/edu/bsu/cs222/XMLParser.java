package edu.bsu.cs222;

import org.w3c.dom.Document;
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

public class XMLParser {
    public Document buildDocumentStream(String filepath) throws ParserConfigurationException,SAXException,IOException{
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(new File(filepath));
            return document;
    }

    public Transformer buildTransformer() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        return transformer;
    }

    public void overwrite(Document document, String filepath) throws TransformerException {
        Transformer transformer = buildTransformer();
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }
}
