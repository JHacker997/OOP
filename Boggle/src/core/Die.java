/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.*;

/**
 *
 * @author jojot
 */
public class Die 
implements IDie {
    //Member vairable
    private final ArrayList<String> diceLetters = new ArrayList<>();

    //Radnomly selects and returns a random letter from the die
    @Override
    public String rollDie() {
        //Initialize an instance of Random
        Random rand = new Random();
        
        //Generate a random number from 0 to 5
        int n = rand.nextInt(NUMBER_OF_SIDES);
        
        //Return a letter
        return diceLetters.get(n);
    }

    //Add the parameter to the 
    @Override
    public void addLetter(String letter) {
        diceLetters.add(letter);
    }

    //Print the letter on each side of a die
    @Override
    public void displayLetters() {
        for (String current : diceLetters) {
            System.out.print(" " + current);
        }
        System.out.println();
    }
    
    
}
