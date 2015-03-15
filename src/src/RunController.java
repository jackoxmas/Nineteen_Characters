package src;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import src.io.controller.Controller;
import src.io.controller.GameController;
import src.io.controller.MapEditorController;
import src.model.map.Map;
import src.model.map.constructs.Avatar;
import src.model.map.constructs.Item;
import src.model.map.constructs.Merchant;
import src.model.map.constructs.Monster;
import src.model.map.constructs.ObstacleRemovingItem;
import src.model.map.constructs.OneHandedSword;
import src.model.map.constructs.OneShotAreaEffectItem;
import src.model.map.constructs.OneWayTeleportItem;
import src.model.map.constructs.PermanentObstacleItem;
import src.model.map.constructs.Sheild;
import src.model.map.constructs.TemporaryObstacleItem;
import src.model.map.constructs.Terrain;
import src.model.map.constructs.Trap;
import src.model.map.constructs.TwoHandedSword;
import src.model.map.constructs.Villager;

/**
 * Initializes, opens the program.
 *
 * @author JohnReedLOL, Alex Stewart
 */
public class RunController {

    private static ProgramOpts pOpts_ = null;
    private static SavedGame saveGame_;
    private static Controller uc_;
    private static boolean map_editor_mode_ = false;
    

    public static final String hostName = "localhost";
    public static final int portNumber = Map.TCP_PORT_NUMBER;
    public static Socket tcp_socket = new Socket();
    public static final Random generator = new Random();
    public static final String unique_id = Integer.toString(generator.nextInt(), 10);
    public static ObjectInputStream object_input_stream = null;
    public static ObjectOutputStream object_output_stream = null;
    
    public static Initiate i = null;
    public static void main(String[] args) {
        System.out.println("1");
        try {
            tcp_socket.setTcpNoDelay(true);
            tcp_socket.connect(new InetSocketAddress(hostName, portNumber));
            System.out.println("TCP socket connected.");
            object_output_stream = new ObjectOutputStream(tcp_socket.getOutputStream());
            object_output_stream.flush();
            System.out.println("2 [flushed output stream]");
            object_input_stream = new ObjectInputStream(tcp_socket.getInputStream());
            System.out.println("3");
            tcp_socket.setTcpNoDelay(true);
            if(tcp_socket.isConnected() == true ) {
                System.out.println("4");
                i = new src.Initiate(tcp_socket, object_output_stream);
                object_output_stream = null;
                System.out.println("5");
                i.start();
                System.out.println("Initiator is started.");
            } else {
                System.out.println("Fail in main.");
                System.exit(-2);
            }
                
            //= new RunController.Initiate(singleton.tcp_socket);
            //RunController.Initiate i = (new RunController.Initiate(null)); //.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception in RunController.main");
            return;
        }
        parseArgs(args); // Parse command line arguments
        System.out.println("parsed args");
        handleArgs(args);
        System.out.println("handled args");
        startGame();
        System.out.println("started game");
    }

    private static void startGame() {
        String avatar_dot_name = "avatar";
        uc_ = new GameController(avatar_dot_name);
    }

    private static void loadGame(String file_path) {

    }

    // <editor-fold desc="GAME METHODS" defaultstate="collapsed">
    private static void exitGame() {

    }

    // </editor-fold>
    // <editor-fold desc="UTILITIES" defaultstate="collapsed">
    // Error date format for the errOut(Exception) write
    private static SimpleDateFormat errDateFormat_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    /**
     * This class holds information about optional program utilities which may
     * be triggered via command line arguments. Reference {@link #parseArgs} for
     * parsing implementation.
     */
    private static class ProgramOpts {

        // Debug Mode
        String[] dbg_match = {"-d", "--debug"};
        String[] editor_match = {"-e", "--editor"};
        boolean dbg_flag = false;
        boolean editor_flag = false;
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
     * Writes the provided String to the errOut stream with the prefix:
     * (DEBUG|0).
     *
     * @param s The String to write.
     */
    public static void dbgOut(String s) {
        if (s == null) {
            s = "NULL";
        }
        if (pOpts_.dbg_flag) {
            errOut("(DEBUG|0) " + s);
        }
    }

    /**
     * Writes the provided String to the errOut stream with the prefix:
     * (DEBUG|X) where X is the debug level of the output. If the provided debug
     * level is greater (larger number) than the debug level option set in
     * {@link ProgramOpts}, the debug string will not be output.
     *
     * @param s The debug string to write
     * @param dLevel The debug level of the output
     */
    public static void dbgOut(String s, int dLevel) {
        if (dLevel > pOpts_.dbg_level) {
            return;
        }
        if (s == null) {
            s = "NULL";
        }
        if (pOpts_.dbg_flag) {
            errOut("(DEBUG|" + dLevel + ") " + s);
        }
    }

    /**
     * Writes the provided Exception to the errOut stream with the prefix:
     * "ERROR:" and WITHOUT a stack trace called. If you wish to print the stack
     * tace, call {@link #errOut(Exception, boolean)} with printTrace set to
     * TRUE.
     *
     * @param e The Exception to write
     */
    public static void errOut(Exception e) {
        errOut(e, false);
    }

    /**
     * Writes the provided Exception to the errOut stream with the prefix:
     * "ERROR:"
     *
     * @param e The Exception object to write
     * @param printTrace whether or not to print the Exception's stack trace
     * below the error output
     */
    public static void errOut(Exception e, boolean printTrace) {
        if (e == null) {
            errOut("errOut called with null Exception");
        }
        errOut("ERROR: " + e.toString());
        if (!printTrace) {
            return;
        }
        for (StackTraceElement elem : e.getStackTrace()) {
            errOut("TRACE: " + elem.toString());
        }
    }

    /**
     * Writes the provided String to the errOut stream.
     *
     * @param s The message to write out.
     */
    public static void errOut(String s) {
        if (s == null) {
            s = "NULL";
        }
        System.err.println("[" + errDateFormat_.format(new Date()) + "] " + s);
    }

    /**
     * Commits the changes specified by the current {@link ProgramOpts}.
     *
     * @param args The command line arguments that were given to this program
     * (not used in this method)
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
        if (pOpts_.editor_flag) {
            map_editor_mode_ = true;
        }
    }

    /**
     * Parses an array of String objects for program options and sets their
     * appropriate values in {@link ProgramOpts}.
     *
     * @param args The command line arguments that were given to this program
     */
    protected static void parseArgs(String[] args) {
        pOpts_ = new ProgramOpts();

        for (int a = 0; a < args.length; a++) {
            // DEBUG
            for (String m : pOpts_.dbg_match) {
                if (m.equals(args[a])) {
                    if (args.length > a + 1) {
                        int temp = Integer.parseInt(args[a + 1]);
                        if (temp > 0) {
                            pOpts_.dbg_level = temp;
                        }
                    }
                    pOpts_.dbg_flag = true;
                    break;
                }
            }

            // LOAD SAVED GAME
            for (String m : pOpts_.lsg_match) {
                if (m.equals(args[a]) && (args.length > a + 1)) {
                    pOpts_.lsg_path = a + 1;
                    // TODO: add line to load saveGame_
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
            // Map Editing
            for (String m : pOpts_.editor_match) {
                if (m.equals(args[a])) {
                    pOpts_.editor_flag = true;
                    break;
                }
            }
        }
    }
    // </editor-fold>
} // end of Main
