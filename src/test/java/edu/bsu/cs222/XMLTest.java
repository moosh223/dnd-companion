package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class XMLTest {
    private String path = "src/test/resources/example.xml";

    @Test
    public void testXMLRetrieval() throws IOException, SAXException, ParserConfigurationException {
        CharacterParser parser = new CharacterParser(path);
        Assert.assertNotNull(parser.toString());
    }

    @Test
    public void testGetNameFromXML() throws IOException, SAXException, ParserConfigurationException {
        String name;
        CharacterParser parser = new CharacterParser(path);
        name = parser.readTag("name");
        Assert.assertNotEquals(name, "Character Name");
    }

    @Test
    public void testGetStatsFromXML() throws IOException, SAXException, ParserConfigurationException {
        int[] stats = new int[6];
        CharacterParser parser = new CharacterParser(path);
        String[] statText = parser.readTag("stats").split(",");
        for(int i = 0; i < 6; i++){
            stats[i] = Integer.parseInt(statText[i]);
        }

        Assert.assertEquals(stats[2],14);
    }

    @Test
    public void testXMLNameChange() throws IOException, SAXException, ParserConfigurationException, TransformerException {
        String name;
        CharacterParser parser = new CharacterParser(path);
        String originalName = parser.readTag("name");
        parser.writeTag("name", "New Character Name");
        name = parser.readTag("name");

        Assert.assertNotEquals(name, originalName);
    }

    @Test
    public void testNewXML() throws IOException, ParserConfigurationException, TransformerException{
        CharacterParser parser = new CharacterParser(path);
        CharacterParser newParse = new CharacterParser("src/test/resources/newTest.xml");
        Assert.assertNotNull(newParse.readTag("name"));
    }

    @Test
    public void testCampaignParser() throws IOException, ParserConfigurationException, TransformerException{
        CampaignParser parser = new CampaignParser("src/test/resources/campaignTest");
        parser.readXML();
    }
}