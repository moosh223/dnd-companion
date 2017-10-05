package edu.bsu.cs222;

import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

    @Test
    public void testDiceRoller(){
        DiceRoller roller = new DiceRoller();
        int roll = roller.rollDice(20);
        Assert.assertTrue(20 >= roll && roll >= 1 );
    }
}
