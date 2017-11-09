package edu.bsu.cs222;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.*;

public class DiceRollerController {
    @FXML ComboBox<String> diceType;
    @FXML Label rollResult;
    @FXML Label rollSum;
    @FXML TextField diceToRoll;

    @FXML
    public void rollDice(){
        int diceType = parseDiceType(this.diceType.getValue());
        int numberOfDice = Integer.parseInt(diceToRoll.getText());
        int[] diceResult = rollMultipleDice(numberOfDice,diceType);

        rollResult.setText("You rolled: "+Arrays.toString(diceResult));
        rollSum.setText("Sum of your dice rolls: "+String.valueOf(sumDiceRolls(diceResult)));
    }

    private int parseDiceType(String value) {
        return Integer.parseInt(value.replace("D",""));
    }

    public int rollDice(int i) {
        Random rand = new Random();
        return rand.nextInt(i)+1;
    }

    public int[] rollMultipleDice(int numberOfRolls, int numberOfSides){
        int[] results = new int[numberOfRolls];
        for(int i = 0; i <numberOfRolls;i++){
            results[i] = rollDice(numberOfSides);
        }
        return results;
    }

    public int sumDiceRolls(int[] diceRolls){
        int sum = 0;
        for(int roll: diceRolls){
            sum += roll;
        }
        return sum;
    }


}