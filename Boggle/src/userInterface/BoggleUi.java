/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import core.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.EtchedBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 *
 * @author jojot
 */
public class BoggleUi {
    //Member variables
    private static JFrame frame;
    private static JMenuBar menuBar;
    private static JMenu menu;
    private static JMenuItem menuItemNewGame;
    private static JMenuItem menuItemExit;
    private static JPanel panelSouth;
    private static JButton[][] buttonDice;
    private static JPanel panelWest;
    private static JScrollPane scroll;
    private static JTextPane textPane;
    private static JLabel labelCurrentWord;
    private static JButton buttonSubmit;
    private static JPanel panelEast;
    private static JLabel labelScore;
    private static JButton buttonShake;
    private static JLabel labelTime;
    private static Board board;
    private static Timer timer;
    private static int minutes;
    private static int seconds;
    private static resetGameListener reset;
    private static int playerScore;
    
    
    //Custom Constructor
    public BoggleUi(Board board) {
        //Shadow assign the member reference of Board
        this.board = board;
        
        //Instantiate reset action listener
        reset = new resetGameListener();
        minutes = 3;
        seconds = 0;
        
        //Call method initComponents method
        initComponents();
    }
    
    //Initialize all the components for the UI
    private void initComponents() {
    //Instantiate all the components
    frame = new JFrame("Boggle");
    menuBar = new JMenuBar();
    menu = new JMenu("Boggle");
    menuItemNewGame = new JMenuItem("New Game");
    menuItemExit = new JMenuItem("Exit");
    panelSouth = new JPanel();
    buttonDice = new JButton[Board.GRID][Board.GRID];
    panelWest = new JPanel();
    textPane = new JTextPane();
    scroll = new JScrollPane(textPane);
    labelCurrentWord = new JLabel();
    buttonSubmit = new JButton("Submit Word");
    panelEast = new JPanel();
    labelScore = new JLabel();
    buttonShake = new JButton("Shake Dice");
    labelTime = new JLabel("3:00", SwingConstants.CENTER);
    
    
    /*
        Frame
    */
    //Set the default size of the JFrame
    frame.setMinimumSize(new Dimension(820, 620));
    
    //Set the default close operation of the JFrame
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    //Use default layout manager BorderLayout for the frame
    frame.setLayout(new BorderLayout());
    
    
    /*
        Menu Bar
    */
    //Add Boggle menu to the menu bar
    menuBar.add(menu);
    
    //Register the reset game action listener to the new game menu item
    menuItemNewGame.addActionListener(reset);
    menuItemExit.addActionListener(new exitGameListener());
            
    //Add menu items to the menu
    menu.add(menuItemNewGame);
    menu.add(menuItemExit);
    
    
    /*
        SOUTH Panel
    */
    //Use default layout manager FlowLayout for the panel to hold the user's word
    panelSouth.setLayout(new FlowLayout());
    panelSouth.setPreferredSize(new Dimension(820, 100));
    panelSouth.setBorder(BorderFactory.createTitledBorder("Current Word"));
    
    //Add a label to the panel for the current word being created to the South panel
    labelCurrentWord.setPreferredSize(new Dimension(380, 60));
    labelCurrentWord.setBorder(BorderFactory.createTitledBorder("Current Word"));
    panelSouth.add(labelCurrentWord);
    
    //Add a button to submit the current word being created to the South panel
    buttonSubmit.setPreferredSize(new Dimension(250, 60));
    buttonSubmit.addActionListener(new submitWordListener());
    panelSouth.add(buttonSubmit);
    
    //Add a label for the player's score to the South panel
    labelScore.setPreferredSize(new Dimension(130, 60));
    labelScore.setBorder(BorderFactory.createTitledBorder("Score"));
    panelSouth.add(labelScore);
    
    
    /*
        WEST Panel
    */
    //Use default layout manager GridLayout for the West panel
    panelWest.setLayout(new GridLayout(Board.GRID, Board.GRID));
    panelWest.setPreferredSize(new Dimension(500, 440));
    panelWest.setBorder(BorderFactory.createTitledBorder("Boggle Board"));
    
    //Loop through all the buttons of the 2-dimensional array
    int count = 0;
    for (int row = 0; row < Board.GRID; row++) {
        for (int col = 0; col < Board.GRID; col++) {
            //Instantiate the current button
            buttonDice[row][col] = new JButton();
            
            //Add letter to the current button
            buttonDice[row][col].setText(board.getGameDatum().get(count));
            count++;
            
            buttonDice[row][col].addActionListener(new dieListener());
            
            //Add the grid of dice buttons to the West panel
            panelWest.add(buttonDice[row][col]);
        }
    }
    
    
    /*
        EAST Panel
    */    
    //Use default layout manager BoxLayout for the East panel
    panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
    panelEast.setPreferredSize(new Dimension(305, 1));
    panelEast.setBorder(BorderFactory.createTitledBorder("Enter Words Found"));
    
    //Edit the text area
    textPane.setEditable(false);
    //textPane.setLineWrap(true);
    
    //Set the scroll bar policies and add it to the East panel
    scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scroll.setMaximumSize(new Dimension(300, 180));
    scroll.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
    scroll.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
    panelEast.add(scroll);
    
    //Instantiate the timer member variable
    timer = new Timer(1000, new timerListener());
    timer.start();
    
    //Add the time leaft label to the East panel
    labelTime.setMaximumSize(new Dimension(280, 130));
    labelTime.setFont(new Font("Serif", Font.PLAIN, 50));
    labelTime.setBorder(BorderFactory.createTitledBorder("Time Left"));
    panelEast.add(labelTime);
    
    //Add the button that shakes the dice
    buttonShake.setMaximumSize(new Dimension(280, 130));
    buttonShake.addActionListener(reset);
    panelEast.add(buttonShake);
    
    
    
    //Add all the components to the frame and set visible
    frame.setJMenuBar(menuBar);
    frame.add(panelSouth, BorderLayout.SOUTH);
    frame.add(panelEast, BorderLayout.EAST);
    frame.add(panelWest, BorderLayout.WEST);
    frame.setVisible(true);
    }
    
    
    //Inner class to exit the game
    private static class exitGameListener
    implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Ask the user if they want to exit the game
            int result = JOptionPane.showConfirmDialog(null, 
                    "Are you sure you want to exit?", 
                    "Boggle", JOptionPane.YES_NO_OPTION);
            
            //Check for the user's answer
            if (result == JOptionPane.YES_OPTION) {
                //Close the application
                frame.dispose();
            }
        }
    }
    
    
    //Inner class to start a new game
    private static class resetGameListener
    implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Shake the dice
            board.shakeDice();
            
            //Populate the dice with the new letters
            int count = 0;
            for (int row = 0; row < Board.GRID; row++) {
                for (int col = 0; col < Board.GRID; col++) {
                    //Add new letter to the current button
                    buttonDice[row][col].setText(board.getGameDatum().get(count));
                    buttonDice[row][col].setEnabled(true);
                    count++;
                }
            }
            
            //Clear the text in the JTextPane
            textPane.setText("");
            
            //Clear the text in score JLabel
            labelScore.setText("");
            
            //Clear the text in the CurrentWord label
            labelCurrentWord.setText("");
            
            //Reset the time label to 3:00
            labelTime.setText("3:00");
            
            //Revalidate and repaint the frame
            frame.revalidate();
            frame.repaint();
            
            //Reset the timer
            timer.stop();
            minutes = 3;
            seconds = 0;
            timer.start();
            
            //Reset the player's score
            playerScore = 0;
        }
    }
    
    
    //Inner class to count the timer down and perform end-of-game tasks
    private static class timerListener
    implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Check if the minutes and seconds are equal to zero
            if (minutes + seconds == 0) {
                //Stop the timer
                timer.stop();
                
                //Message that the game has finished
                JOptionPane.showMessageDialog(frame,
                    "The computer is comparing words.", "GAME OVER",
                    JOptionPane.INFORMATION_MESSAGE);
                
                //Make an arraylist of all the words in the text area
                String s[] = textPane.getText().split("\\r?\\n");
                ArrayList<String>submittedWords = new ArrayList<>(Arrays.asList(s));
                
                //Find out how many words the computer found that matches the player's words
                int computerWords = (int)(Math.random()*submittedWords.size()) + 1;
                
                //Check if th eplayer won
                if (playerScore - computerWords > 0){
                    //Message declaring how many words the computer found
                    JOptionPane.showMessageDialog(frame,
                        "\nThe computer found " + computerWords + " of the Player's " + playerScore + " words.",
                        "You won!", JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    //Message declaring how many words the computer found
                    JOptionPane.showMessageDialog(frame,
                        "\nThe computer found " + computerWords + " of the Player's " + playerScore + " words.",
                        "You tied", JOptionPane.INFORMATION_MESSAGE);
                }
                
                //Array of indices representing each of the submitted words
                int[] wordIndices = new int[playerScore];
                
                //Create an array of all the indices for the words
                for(int count = 0; count < playerScore; count++) {
                    wordIndices[count] = count;
                }
                
                //Randomly shuffle the array of word indices
                Random rnd = ThreadLocalRandom.current();
                for (int i = playerScore - 1; i > 0; i--) {
                    //Select a random index
                    int index = rnd.nextInt(i + 1);

                    //Swap the random index with the current one
                    int temp = wordIndices[index];
                    wordIndices[index] = wordIndices[i];
                    wordIndices[i] = temp;
                }
                
                //Array list of all the words found by the comupter that must by strikethru'd
                ArrayList<String> strikeWords = new ArrayList<>();
                
                //Find which words were randomly found by the computer
                for (int j = 0; j < computerWords; j++) {
                    //TO DO: Figure out how to strike through words
                    strikeWords.add(submittedWords.get(wordIndices[j]));
                }
                
                //Boolean to know if a word was found in the strike words array list
                boolean found = false;
                
                //Reset the text in the text pane
                textPane.setText("");
                
                //Set up the default document style
                DefaultStyledDocument doc = new DefaultStyledDocument();
                StyleContext sc = new StyleContext();
                
                //Set up the strikethru style
                Style Style = sc.addStyle("strikethru", null);
                StyleConstants.setStrikeThrough(Style, true);
                Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
                
                //Loop through all the lines of the submitted words
                for (int row = 0; row < playerScore; row++) {
                    //Loop through all the strike words
                    for (String word : strikeWords) {
                        //Check if the current submitted word matches the current strike word
                        if (word.equals(submittedWords.get(row))) {
                            //Indicate that the word was found in the strike words
                            found = true;
                        }
                    }
                    
                    try {
                        //Check if the current submitted word was found in the strike words
                        if (found) {
                            //Add the submitted word back into the text pane with strikethru style
                            doc.insertString(doc.getLength(), submittedWords.get(row) + "\n", Style);
                        }
                        else {
                            //Add the submitted word back into the text pane with default style
                            doc.insertString(doc.getLength(), submittedWords.get(row) + "\n", defaultStyle);
                        }
                    }
                    catch (BadLocationException ex) {
                        System.out.println("error");
                    }
                    
                    //Reset the found word boolean to false
                    found = false;
                }
                
                //Add the document to the text pane to show the player which words were cancelled
                textPane.setDocument(doc);
                
                //Update the player's score
                playerScore -= computerWords;
                labelScore.setText("" + playerScore);
            }
            //Check if the seconds are equal to zero
            else if (seconds == 0) {
                //Go to the next minute
                minutes--;
                seconds = 60;
            }
            
            //Check if the timer is still running
            if(timer.isRunning()){
                //Decrement the second by one
                seconds--;
            }
            
            //Make sure that the seconds always show two numbers
            DecimalFormat myFormatter = new DecimalFormat("00");
            
            //Update the JLabel showing the remaining time
            labelTime.setText(minutes + ":" + myFormatter.format((double)(seconds)));
        }
    }
    
    //Inner class to submit a player's word
    private static class submitWordListener
    implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Loop through all the dice to enable them
            for (int row = 0; row < Board.GRID; row++) {
                for (int col = 0; col < Board.GRID; col++) {
                    //Enable the current die
                    buttonDice[row][col].setEnabled(true);
                }
            }
            
            //Save the score before submitting the word
            int oldScore = playerScore;
            
            //Check if the word is long enough for Boggle standards (3 letters)
            if (labelCurrentWord.getText().length() >= 3) {
                //Loop through the dictionary
                for (String word : board.getDictionaryDatum()) {
                    //Check if the submitted word is in the dictionary
                    if (labelCurrentWord.getText().equalsIgnoreCase(word)) {
                        //Add the new word to the list of submitted words
                        textPane.setText(textPane.getText() + labelCurrentWord.getText() + "\n");
                        
                        //Incrememnt the player's score
                        playerScore++;
                        
                        //Stop looking through the dictionary since a match was already found
                        break;
                    }
                }
            }
            
            //Check if a word was found in the dictionary
            if (oldScore == playerScore) {
                //Warn the player that the word they submitted was invalid
                JOptionPane.showMessageDialog(frame,
                    labelCurrentWord.getText() + " is not a word", "INVALID WORD",
                    JOptionPane.WARNING_MESSAGE);
                labelCurrentWord.setText("INVALID WORD");
            }
            else {
                //Clear the current word JLabel
                labelCurrentWord.setText("");
            }
        }
    }
    
    //Inner class to submit a player's word
    private static class dieListener
    implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //Check for warning message from remainging submission
            if (labelCurrentWord.getText().equals("INVALID WORD")) {
                labelCurrentWord.setText("");
            }
            
            //Update the JLabel with the current word to include the new letter
            labelCurrentWord.setText(labelCurrentWord.getText() + ((JButton)ae.getSource()).getText());
            
            //Loop through all of the dice to find the selected die
            int r = 0, c = 0;
            search:
            for (int row = 0; row < Board.GRID; row++) {
                for (int col = 0; col < Board.GRID; col++) {
                    //Check if the button is the one that was selected
                    if(buttonDice[row][col] == (JButton)ae.getSource()){
                        //Save the coordinates of the selceted button
                        r = row;
                        c = col;
                        
                        //Stop looking for the selected die's coordinates
                        break search;
                    }
                }
            }
            
            //Loop through all of the dice again to activate appropriate dice
            for (int row = 0; row < Board.GRID; row++) {
                //System.out.println("\nrow = " + row);
                for (int col = 0; col < Board.GRID; col++) {
                    //Check ifthe button is next to the selected button
                    if(row == r && col == c) {
                        //Disable the selected die
                        buttonDice[row][col].setEnabled(false);
                    }
                    else if (row >= r+2 || row <= r-2 || col >= c+2 || col <= c-2) {
                        //Enable the dice surrounding the selected die
                        buttonDice[row][col].setEnabled(false);
                    }
                    else {
                        //Disable all other dice
                        buttonDice[row][col].setEnabled(true);
                    }
                }
            }
        }
    }
}
