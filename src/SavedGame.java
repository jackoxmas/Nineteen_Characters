/*
 * Implementor: Alex Stewart
 * Last Access: 15-02-13
 */
package src;
import java.io.*;
import src.model.*;

/**
 * This class manages a saved game object. A saved game has a file path and 
 * methods to interact with that file (loading and saving data).
 * 
 * @author Alex Stewart
 */
public class SavedGame {
    private String filePath_ = null;
    
    public SavedGame(String filePath) {
        
    }
    
    public int loadFile(MapMain_Relation mapRel, Exception exep) {
        // Error checking
        if (filePath_ == null) {
            exep = new Exception("Provided filepath is NULL");
            return 0;
        }
        if (mapRel == null) {
            exep = new Exception("Invalid map relationship provided");
            return 0;
        }
        
        // Attempt to open the file and read the map object
        try
        {
            FileInputStream fis = new FileInputStream(filePath_);
            ObjectInputStream ois = new ObjectInputStream(fis);
            
        }
        catch (Exception e)
        {
            exep = e;
            return 0;
        }
        
        return 1;
    }
    
    public int saveFile() {
        return 0;
    }
}
