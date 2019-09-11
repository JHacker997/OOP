/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import static core.IBoard.*;
import static core.IDie.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author jojot
 */
public class Board 
implements IBoard {
    //Member variables
    private ArrayList<String> diceDatum;
    private ArrayList<String> dictionaryDatum;
    private ArrayList<Die> gameDice;
    private ArrayList<String> gameDatum;
    
    //Returns array list gameDatum
    public ArrayList<String> getGameDatum() {
        return gameDatum;
    }
    
    //Returns array list dictionaryDatum
    public ArrayList<String> getDictionaryDatum() {
        return dictionaryDatum;
    }
    
    //Custom constructor
    public Board(ArrayList<String> dice, ArrayList<String> dictionary) {
        //Set values of member variables
        diceDatum = dice;
        dictionaryDatum = dictionary;
        gameDice = new ArrayList<>();
        gameDatum = new ArrayList<>();
    }

    //Fills the gameDice array list and print the values of each die
    @Override
    public void populateDice() {
        //Declare variables
        Die die;
        int counter = 0;
        
        //Loop through dice
        for (int dieNumber = 0; dieNumber < NUMBER_OF_DICE; dieNumber++) {
            //Instantiate the instance of class Die
            die = new Die();
            
            //Print the current die's number
            System.out.print("Die " + dieNumber + ":");
            
            //Loop through the six sides of the die
            for (int sideNumber = 0; sideNumber < NUMBER_OF_SIDES; sideNumber++, counter++) {
                //Add each letter to the Die ArrayList
                die.addLetter(diceDatum.get(counter));
            }
            
            //Display all the letters on the die
            die.displayLetters();
            
            //Add the current die to the array list of dice
            gameDice.add(die);
        }
        
    }

    //Shakes all the dice and stores the revealed values
    @Override
    public ArrayList shakeDice() {
        //Clear the game datum of of any content for if calling after the firts time
        gameDatum.clear();
        
        //Create an array of all the indices for the dice
        int[] diceIndices = {0, 1, 2, 3, 4, 5, 6, 7, 15, 14, 13, 12, 11, 10, 9, 8};
        
        //Randomly shuffle the array of dice indices
        Random rnd = ThreadLocalRandom.current();
        for (int i = NUMBER_OF_DICE - 1; i > 0; i--) {
            //Select a random index
            int index = rnd.nextInt(i + 1);
            
            //Swap the random index with the current one
            int temp = diceIndices[index];
            diceIndices[index] = diceIndices[i];
            diceIndices[i] = temp;
        }
        
        //Loop through all 16 dice
        for (int count = 0; count < NUMBER_OF_DICE; count++) {
            //Select the dice at a random index
            Die current = gameDice.get(diceIndices[count]);
            
            //Roll the current die and stores the returned value into the gameDatum array list
            gameDatum.add(current.rollDie());
        }
        
        //Call diplayGameDatamethod to print out the boggle board
        this.displayGameData();
        
        //Return array list of revealed values
        return gameDatum;
    }

    //Prints the game board to the output window
    @Override
    public void displayGameData() {
        //Print board title
        System.out.print("\nBoggle board");
        
        //Loop through all of the diceon the board
        for (int j = 0; j < NUMBER_OF_DICE; j++)
        {
            //Check if four dice have been printed since the last new line
            if (j % 4 == 0) {
                //Print a new line
                System.out.println();
            }
            
            //Print the value showing on each die
            System.out.print(gameDatum.get(j) + " ");
        }
        //Move cursor to new line afterall of the board is printed
        System.out.println();
    }
    
}
