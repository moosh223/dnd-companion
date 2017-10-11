package edu.bsu.cs222;

import java.util.*;
import java.lang.Math;

public class Functions {

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
