/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-13
 */
package src;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.model.MapMain_Relation;

/**
 * Initializes, opens the program.
 * @author JohnReedLOL, Alex Stewart
 */
public class Main
{
    
    private static ProgramOpts pOpts_ = null;
    private static SavedGame saveGame_;

    private static MapMain_Relation mmr_;


    public static void main(String[] args) {
        //parseArgs(args); // Parse command line arguments
        //initialize(); // Initialize any data we need to before loading
        //handleArgs(args);

        // testing
        //saveGameToDisk();

        //exitGame();
        //initializeEverything();
    }


    // <editor-fold desc="GAME METHODS" defaultstate="collapsed">
    private static void exitGame() {

    }

    private static void initialize() {
        saveGame_ = null;
        mmr_ = new MapMain_Relation(); // Initialize the Map Object
        MapMain_Relation newmmr_ = new MapMain_Relation();
        newmmr_.bindToNewMapOfSize(5, 5); // Each MapMain Relation creates a map and binds itself to that map.
        newmmr_.addEntity(new src.controller.Avatar("test", 'x', 0, 0), 0, 0);
    }

    private static void saveGameToDisk() {
        if (saveGame_ == null) {
            saveGame_ = SavedGame.newSavedGame();
        }
        Exception e = null;
        saveGame_.saveFile(mmr_, e);
        if (e != null)
            errOut(e);
    }

    // TODO: complete
    private static int startNewGame() {
        return 0;
    }
    // </editor-fold>

    // <editor-fold desc="UTILITIES" defaultstate="collapsed">
    // Error date format for the errOut(Exception) write
    private static SimpleDateFormat errDateFormat_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    /**
     * This class holds information about optional program utilities which may
     * be triggered via command line arguments. Reference {@link #parseArgs}
     * for parsing implementation.
     */
    private static class ProgramOpts {
        // Debug Mode
        String[] dbg_match = {"-d", "--debug"};
        boolean dbg_flag = false;

        // Load Saved Game
        String[] lsg_match = {"-l", "--load"}; // option flag match string
        boolean lsg_flag = false; // whether or not to load the game
        int lsg_path = -1; // the index in args to get the path from

        // Redirect STDERR
        String[] err_match = {"-e", "-err-out"};
        boolean err_flag = false;
        int err_path = -1;
    }

    /**
     * Writes the provided String to the errOut stream with the prefix: (DEBUG).
     * @param s The String to write.
     */
    public static void dbgOut(String s) {
        if (s == null) s = "NULL";
        if (pOpts_.dbg_flag)
            errOut("(DEBUG) " + s);
    }

    /**
     * Writes the provided Exception to the errOut stream with the prefix: "ERROR:" and WITHOUT a stack trace called.
     * If you wish to print the stack tace, call {@link #errOut(Exception, boolean)} with printTrace set to TRUE.
     * @param e The Exception to write
     */
    public static void errOut(Exception e) {
        errOut(e, false);
    }

    /**
     * Writes the provided Exception to the errOut stream with the prefix: "ERROR:"
     * @param e The Exception object to write
     * @param printTrace whether or not to print the Exception's stack trace below the error output
     */
    public static void errOut(Exception e, boolean printTrace) {
        if (e == null) {
            errOut("errOut called with null Exception");
        }
        errOut("ERROR: " + e.getMessage());
        if (!printTrace)
            return;
        for (StackTraceElement elem : e.getStackTrace()) {
            errOut("TRACE: " + elem.toString());
        }
    }

    /**
     * Writes the provided String to the errOut stream.
     * @param s The message to write out.
     */
    public static void errOut(String s) {
        if (s == null) s = "NULL";
        System.err.println("[" + errDateFormat_.format(new Date()) + "] " + s);
    }

    private static void handleArgs(String[] args) {
        if (pOpts_.err_flag) {
            try {
                System.setErr(new PrintStream(args[pOpts_.err_path]));
            } catch (FileNotFoundException e) {
                errOut(e);
            }
        }
        if (pOpts_.lsg_flag) {
            saveGame_ = new SavedGame(args[pOpts_.lsg_path]);
            Exception e = null;
            int s = saveGame_.loadFile(mmr_, e);
            if (s == 0) { // the saved game load has failed
                errOut(e);  // print out error
                if (startNewGame() == 0) {
                    errOut(e);
                    exitGame();
                }
            }
        }
    }

    private static void parseArgs(String[] args) {
        pOpts_ = new ProgramOpts();

        for (int a = 0; a < args.length; a++) {
            // DEBUG
            for (String m : pOpts_.dbg_match) {
                if (m.equals(args[a])) {
                    pOpts_.dbg_flag = true;
                    break;
                }
            }

            // LOAD SAVED GAME
            for (String m : pOpts_.lsg_match) {
                if (m.equals(args[a]) && (args.length > a + 1)) {
                    pOpts_.lsg_path = a + 1;
                    pOpts_.lsg_flag = true;
                    break;
                }
            }
            // REDIRECT STDERR
            for (String m : pOpts_.err_match) {
                if (m.equals(args[a]) && (args.length > a + 1)) {
                    pOpts_.err_path = a + 1;
                    pOpts_.err_flag = true;
                    break;
                }
            }
        }
    }
    // </editor-fold>
} // end of Main
