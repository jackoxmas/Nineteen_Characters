/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-11
 */
package src;

import src.controller.Avatar;
import src.controller.Item;
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
        mmr.bindToNewMapOfSize(19, 12);
        mmr.addItem(new Item("TEST1", 'T', false, true, true), 3, 4);
        mmr.addItem(new Item("TEST2", 'Y', true, true, true), 5, 5);
        mmr.addItem(new Item("TEST3", '*', false, false, false), 0, 0);
        sg.saveFile(mmr, new Exception());
        mmr = null;
        mmr = sg.loadFile();
        System.out.println("== FINISH LINE ==");
    }
}
