package edu.bsu.cs222;

import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

    @Test
    public void testDiceRoller(){
       Functions roller = new Functions();
        int roll = roller.rollDice(20);
        Assert.assertTrue(20 >= roll && roll >= 1 );
    }

    @Test
    public void testCheckWindowSwap(){
        AnchorPane pane1 = new AnchorPane();
        AnchorPane pane2 = new AnchorPane();
        AnchorPane visiblePane;
        pane1.setVisible(true);
        pane2.setVisible(false);
        Assert.assertTrue(pane1.isVisible());
        visiblePane = pane1;
        visiblePane.setVisible(false);
        Assert.assertFalse(pane1.isVisible());
    }
}
