package edu.bsu.cs222;

import org.w3c.dom.Document;
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
    public Document buildDocumentStream(String filepath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        return builder.parse(new File(filepath));
    }

    private Transformer buildTransformer() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        return transformerFactory.newTransformer();
    }

    public void write(Document document, String filepath) throws TransformerException {
        Transformer transformer = buildTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(document);
        StreamResult result = new StreamResult(new File(filepath));
        transformer.transform(source, result);
    }

    public Document buildNewDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        return builder.newDocument();
    }

    public int[] parseStats(String stats) {
        if (stats.equals("")) {
            return new int[]{0, 0, 0, 0, 0, 0};
        } else {
            int[] statList = new int[6];
            String[] statText = stats.split(",");
            for (int i = 0; i < 6; i++) {
                statList[i] = Integer.parseInt(statText[i]);
            }
            return statList;
        }
    }

    public String writeStats(int[] stats) {
        StringBuilder statString = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            statString.append(stats[i]).append(",");
        }
        statString.append(stats[5]);
        return statString.toString();
    }
}