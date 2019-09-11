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
public interface IBoard {
    //Constant fields
    public static final int NUMBER_OF_DICE = 16;
    public static final int GRID = 4;
    
    //Method signatures
    void populateDice();
    ArrayList shakeDice();
    void displayGameData();
}
