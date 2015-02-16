/**
 * Implementor: Alex Stewart
 * Last Update: 15-02-13
 */
package src;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import src.controller.AreaEffectItem;
import src.controller.Avatar;
import src.controller.AvatarController;
import src.controller.Item;
import src.controller.Terrain;
import src.model.MapMain_Relation;
import src.view.Display;
import src.view.Viewport;

/**
 * Initializes, opens the program.
 * @author JohnReedLOL, Alex Stewart
 */
public class Main
{
    
    private static ProgramOpts pOpts_ = null;
    private static SavedGame saveGame_;
    private static Avatar avatar_;

    private static MapMain_Relation mmr_;


    public static void main(String[] args) {
        //parseArgs(args); // Parse command line arguments
        initialize(); // Initialize any data we need to before loading
        populateMap();//Add stuff into the map
        startGame(); // Begin the avatarcontroller loop
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
        mmr_ = new MapMain_Relation();
        mmr_.bindToNewMapOfSize(Viewport.width_ / 2, Viewport.height_); //Can change these later if we so desire. 
        Avatar avatar = new Avatar("avatar", '☃', 0, 0);
        avatar.setMap(mmr_);
        avatar_ = avatar;
        Display _display = new Display(avatar.getMyView());
        _display.printView();


    }
    private static void populateMap(){
        Item equipable = new Item("☂", '☂', true, true, false);
        equipable.getStatsPack().offensive_rating_ += 17;
        
        mmr_.addItem(equipable, 5, 5); // ▨
        for (int y = 0; y < Viewport.height_; ++y) {
            for (int x = 0; x < Viewport.width_ / 2; ++x) {
                Terrain obstacle = new Terrain("land", '▨', false, false);
                if (y == 4) {
                    if (x == 2) {
                        obstacle.addDecal('☠');
                    } else if (x == 6) {
                        obstacle.addDecal('★');
                    } else if (x == 9) {
                        obstacle.addDecal('✚');
                    }
                }
                mmr_.addTerrain(obstacle, x, y);
            }
        }
            // String name, char representation, boolean is_passable,
        // boolean goes_in_inventory, boolean is_one_shot, Effect effect, int power
        AreaEffectItem inflict_pain = new AreaEffectItem("inflict_pain", '♨', true, false,
                true, AreaEffectItem.Effect.HURT, 10);
        mmr_.addItem(inflict_pain, 16, 7);
        
        AreaEffectItem area_heal = new AreaEffectItem("area_heal", '♥', true, false,
                false, AreaEffectItem.Effect.HEAL, 10);
        mmr_.addItem(area_heal, 12, 12);
        
        AreaEffectItem area_kill = new AreaEffectItem("area_kill", '☣', true, false,
                true, AreaEffectItem.Effect.KILL, 10);
        mmr_.addItem(area_kill, 3, 11);
        
        AreaEffectItem area_level = new AreaEffectItem("area_level", '↑', true, false,
                true, AreaEffectItem.Effect.LEVEL, 10);
        mmr_.addItem(area_level, 11, 5);
        

        Terrain obstacle = new Terrain("boulder", '■', true, false);
        mmr_.addTerrain(obstacle, 2, 2);
    }
    private static void startGame(){
    	AvatarController AC = new AvatarController(avatar_);
    	AC.runTheGame();
    }
    private static void saveGameToDisk() {
        if (saveGame_ == null) {
            saveGame_ = SavedGame.newSavedGame();
        }
        Exception e = null;

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
        int dbg_level = 1;

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
     * Writes the provided String to the errOut stream with the prefix: (DEBUG|0).
     * @param s The String to write.
     */
    public static void dbgOut(String s) {
        if (s == null) s = "NULL";
        if (pOpts_.dbg_flag)
            errOut("(DEBUG|0) " + s);
    }

    /**
     * Writes the provided String to the errOut stream with the prefix: (DEBUG|X) where X is the debug level of the
     * output. If the provided debug level is greater (larger number) than the debug level option set in
     * {@link ProgramOpts}, the debug string will not be output.
     * @param s The debug string to write
     * @param dLevel The debug level of the output
     */
    public static void dbgOut(String s, int dLevel) {
        if (dLevel > pOpts_.dbg_level)
            return;
        if (s == null) s = "NULL";
        if (pOpts_.dbg_flag)
            errOut("(DEBUG|" + dLevel + ") " + s);
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
        errOut("ERROR: " + e.toString());
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

    /**
     * Commits the changes specified by the current {@link ProgramOpts}.
     * @param args The command line arguments that were given to this program (not used in this method)
     */
    protected static void handleArgs(String[] args) {
        if (pOpts_.err_flag) {
            try {
                System.setErr(new PrintStream(args[pOpts_.err_path]));
                dbgOut("ARGS: error out set piped to: " + pOpts_.err_path, 2);
            } catch (FileNotFoundException e) {
                errOut(e);
            }
        }
        if (pOpts_.dbg_flag) {
            dbgOut("ARGS: debug mode enabled at level: " + pOpts_.dbg_level, 2);
        }
        if (pOpts_.lsg_flag) {
            saveGame_ = new SavedGame(args[pOpts_.lsg_path]);
            Exception e = null;
            //int s = saveGame_.loadFile(mmr_, e);
            /*
            if (s == 0) { // the saved game load has failed
                errOut(e);  // print out error
                if (startNewGame() == 0) {
                    errOut(e);
                    exitGame();
                }
            }*/
        }
    }

    /**
     * Parses an array of String objects for program options and sets their appropriate values in {@link ProgramOpts}.
     * @param args The command line arguments that were given to this program
     */
    protected static void parseArgs(String[] args) {
        pOpts_ = new ProgramOpts();

        for (int a = 0; a < args.length; a++) {
            // DEBUG
            for (String m : pOpts_.dbg_match) {
                if (m.equals(args[a])) {
                    if (args.length > a + 1) {
                        int temp = Integer.parseInt(args[a+1]);
                        if (temp > 0)
                            pOpts_.dbg_level = temp;
                    }
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
