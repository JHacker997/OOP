/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boggle;

import core.*;
import userInterface.*;
import inputOutput.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author jojot
 */
public class Boggle {
    
    //Create member variables
    private static ArrayList<String> boggleData;
    private static String boggleFileName = "../data/BoggleData.txt";
    public static ArrayList<String> dictionaryData;
    private static String dictionaryFileName = "../data/Dictionary.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Print introductory message
        System.out.println("Welcome to Boggle!");
        
        //Generate a pane to tell the user they will be playing the game
        JOptionPane.showMessageDialog(null, "Let's Play Boggle!");
        
        //Instantiate an instance of class ReadDataFile for boggle data
        ReadDataFile diceFile;
        diceFile = new ReadDataFile(boggleFileName);
        
        //Call method populateData on boggle data object
        diceFile.populateData();
        
        //Instantiate an instance of class ReadDataFile for dictionary
        ReadDataFile dictionaryFile;
        dictionaryFile = new ReadDataFile(dictionaryFileName);
        
        //Call method populateData on dictionary object
        dictionaryFile.populateData();
        
        //Instantiate an instance of class Board
        Board board;
        board = new Board(diceFile.getData(), dictionaryFile.getData());
        
        //Call method populateDice on board object reference 
        board.populateDice();
        
        //Print number of objects in dictionaryDatum
        System.out.println("There are " + dictionaryFile.getData().size() + " entries in the dictionary");
        
        //Set boggle data array list equal to shakeDice method
        boggleData = board.shakeDice();
        
        //Instantiate BoggleUi
        BoggleUi boggleUi;
        boggleUi = new BoggleUi(board);
    }
    
}
