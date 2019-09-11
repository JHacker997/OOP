/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jojot
 */
public class ReadDataFile 
implements IReadDataFile {
    
    //Member variables
    private static Scanner inputFile = null;
    private final String fileName;
    private final ArrayList<String> data;
    
    //Custom constructor
    public ReadDataFile(String nameOfFile) {
        fileName = nameOfFile;
        data = new ArrayList<>();
    }
    
    //Getter for fileDatum
    public ArrayList<String> getData() {
        return data;
    }

    //Store all the information from the dictionary file
    @Override
    public void populateData() {
        try {
            //Initialize an instance of URL and File
            URL url = getClass().getResource(fileName);
            File file = new File(url.toURI());
            
            //Initialize the instance of Scanner
            inputFile = new Scanner(file);
            
            while (inputFile.hasNext()) {
                data.add(inputFile.next());
            }
                
        } 
        catch (IOException | URISyntaxException ex) {
            Logger.getLogger(ReadDataFile.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {
            if (inputFile != null) {
                inputFile.close();
            }
        }
            
        
    }
}
