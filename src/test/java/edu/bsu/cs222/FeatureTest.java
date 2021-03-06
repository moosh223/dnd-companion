package edu.bsu.cs222;

import edu.bsu.cs222.control.DiceRollerController;
import javafx.scene.layout.AnchorPane;
import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

    @Test
    public void testDiceRoller(){
       DiceRollerController roller = new DiceRollerController();
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
