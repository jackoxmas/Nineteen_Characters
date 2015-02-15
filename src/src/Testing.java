/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-11
 */
package src;

import src.controller.Avatar;
import src.model.*;
import java.lang.reflect.Field;

/**
 * This class holds static methods for testing individual elements of the 
 * project.
 * 
 * @author Alex Stewart
 */
public class Testing extends Main{

    public static void main (String[] args) {
        Main.parseArgs(args);
        Main.handleArgs(args);

        SavedGame sg = SavedGame.newSavedGame();
        MapMain_Relation mmr = new MapMain_Relation();
        mmr.bindToNewMapOfSize(10, 10);
        sg.saveFile(mmr, new Exception());
        sg.loadFile(mmr, new Exception());

        System.out.println("== FINISH LINE ==");
    }
}
