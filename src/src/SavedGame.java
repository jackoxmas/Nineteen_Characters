/*
 * Implementor: Alex Stewart
 * Last Update: 15-02-13
 */
package src;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import src.model.*;
import src.model.constructs.*;

import org.w3c.dom.*;

/**
 * This class manages a saved game object. A saved game has a file path and 
 * methods to interact with that file (loading and saving data).
 * 
 * @author Alex Stewart
 */
public class SavedGame {

    public static final SimpleDateFormat SAVE_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
    /**
     * SAVE_DATA_VERSION is the data format version number. It should be 
     * incremented any time that the serialization or deserialization sequence 
     * is modified. The version number 0 is reserved. This value has no 
     * relation to the Java native Serialization object ID.
     */
    public static final long SAVE_DATA_VERSION = 3;
    public static final String SAVE_EXT = ".xml";
    public static final String KEY_EXT = ".key";
    public static final char SAVE_ITERATOR_FLAG = '_';
    // SAVE FILE FORMAT: yyMMdd_<number>.sav

    // XML node names
    private static final String XML_ROOT = "save_game";
    private static final String XML_SAVEVERSION = "version";
    private static final String XML_MAP = "map";
    private static final String XML_USERNAME = "username";
    private static final String XML_KEYMAP = "keymap";
    private static final String XML_KEY = "key";
    private static final String XML_REMAP = "remap";
    public static final String XML_MAP_MAPGRID = "map_grid";
    public static final String XML_MAP_MAPGRID_WIDTH = "width";
    public static final String XML_MAP_MAPGRID_HEIGHT = "height";
    public static final String XML_MAP_TIME = "time";

    /**
     * Searches the given directory (or current dir, if the param is null) for the current files and returns a string
     * that will be unique to the files in that directory.
     * @param directory The directory to search for files in or NULL - to search the current directory
     * @param extension The file extension to filter for
     * @return A unique file name for the given directory or NULL, if an invalid directory was given
     */
    private static String autoGen (String directory, String extension) {
        if (directory == null || directory.length() == 0)
            directory = System.getProperty("user.dir"); // ask the JVM for the current dir

        RunGame.dbgOut("New file requested for dir: " + directory, 3);
        File dir = new File(directory);
        if (!dir.isDirectory() || !dir.exists()) {
            RunGame.errOut("Requested directory path is invalid or does not exist");
            return null;
        }

        String date = SAVE_DATE_FORMAT.format(new Date()); // get the current date string

        // Search the current directory for existing files and keep an iterator to append the file name with a unique
        // ID for this day.
        int iterator = 1;
        try {
            File[] files = dir.listFiles();
            String buf_string;

            int buf_iter;
            for (File f : files) { // Search files in directory
                if (f.getName().endsWith(extension)) {
                    buf_string = f.getName(); // temporarily store the filename
                    if(!buf_string.startsWith(date))
                        continue; // if the file isn't from this date, ignore it
                    buf_string = buf_string.substring(buf_string.lastIndexOf('_') + 1, buf_string.lastIndexOf(".")); // otherwise, get the ID
                    buf_iter = Integer.parseInt(buf_string);
                    if (buf_iter >= iterator) iterator = buf_iter + 1; // ensure that the iterator is always ahead by 1
                }
            }
        } catch (Exception e) {
            RunGame.errOut(e, true);
        }
        // iterator is now the correct unique ID
        // ready to construct path
        String ff = date + SAVE_ITERATOR_FLAG + iterator + extension;
        RunGame.dbgOut("Returning autogen: " + ff, 4);
        return ff;
    }

    public static src.model.Map loadGame(String filepath) {//UserController controller) {
        try {
            File saveFile = null; // TODO: VALIDATE THIS

            // Initialize DOM document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document load = docBuilder.parse(saveFile);
            RunGame.dbgOut("XML file loaded from: " + saveFile.getPath(), 3);

            Element root = load.getDocumentElement();
            root.normalize();
            RunGame.dbgOut("XML Captured document root as: " + root.getNodeName(), 4);

            // Validate save data version
            NodeList ns_result = root.getElementsByTagName("version"); // ns_result used for node search results
            if (ns_result.getLength() == 0) {
                RunGame.errOut("XML Error: no save version found - cannot load");
                return null;
            }
            else if (ns_result.getLength() > 1) {
                RunGame.errOut("XML WARN: save file has [" + ns_result.getLength() + "] versions. Ignoring all but first.");
            }
            Integer v = Integer.parseInt(ns_result.item(0).getTextContent());
            RunGame.dbgOut("XML DBG: Checking save versions. Expecting [" + SAVE_DATA_VERSION + "], got: [" + v + "].", 3);
            if (SAVE_DATA_VERSION != v) {
                RunGame.errOut("XML ERR: save data version mismatch. Save game cannot be loaded.");
                // TODO: uncomment line below
                //return null;
            }

            // TODO: load user controller
            ns_result = root.getElementsByTagName(XML_KEYMAP);

            // Load map
            ns_result = root.getElementsByTagName(XML_MAP);
            if (ns_result.getLength() == 0) {
                RunGame.errOut("XML ERR: no map found. Cannot load save file.");
                return null;
            } else if (ns_result.getLength() > 1) {
                RunGame.errOut("XML WARN: save file contains more than [" + ns_result.getLength() + "] map nodes. Loading the first one.");
            }

            src.model.Map mm = src.model.Map.xml_readMap(load, (Element)ns_result.item(0));

            if (mm == null) {
                RunGame.errOut("XML ERR: map load has failed.");
            } else {
                RunGame.dbgOut("XML INFO: load has completed successfully.");
            }
            return mm; // return with map (may be null, if map load failed)

        } catch (Exception e) {
            RunGame.errOut(e, true);
            return null;
        }
    }

    public static int saveGame(String filepath, src.model.Map map) {
        try {
            File saveFile = validateFile(filepath, SAVE_EXT);
            if (saveFile == null)
                throw new Exception("Could not save file");

            saveFile.createNewFile(); // create the save file if it doesn't already exist

            // open or create the save file
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            Document save = docBuilder.newDocument();
            Element root = save.createElement(XML_ROOT);
            save.appendChild(root);

            Element e_version = save.createElement("version");
            e_version.appendChild(save.createTextNode(Long.toString(SAVE_DATA_VERSION)));
            root.appendChild(e_version);

            /*
            if (controller != null) {
                // CONTROLLER KEYMAP
                Element e_keymap = save.createElement(XML_KEYMAP);
                e_keymap.setAttribute(XML_USERNAME, controller.getUserName());

                Element e_key;
                for (Map.Entry<Character, Character> e : controller.getRemap().entrySet()) {
                    e_key = save.createElement(XML_REMAP);
                    e_key.setAttribute(XML_KEY, e.getKey().toString());
                    e_key.appendChild(save.createTextNode(e.getValue().toString()));
                    e_keymap.appendChild(e_key);
                }
                root.appendChild(e_keymap);
            } else {
                RunGame.dbgOut("XML INFO: no user controller provided for saving.", 3);
            */

            // MAP
            Element e_map = save.createElement(XML_MAP);

            map.xml_writeMap(save, e_map);

            root.appendChild(e_map);

            // write the content into xml file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(save);
            StreamResult result = new StreamResult(saveFile);
            transformer.transform(source, result); // actually write the XML to the file

        } catch (Exception e) {
            RunGame.errOut(e, true);
        }

        return 1;
    }

    /**
     * Saves the given key remapping to the provided file (or a new autogenerated file)
     * @param filepath The file to save to, the directory to save to, or null to autogenerate
     * @param remap The key remapping to save
     * @return 0 if the keymap was not saved, 1 otherwise
     */
    public static int saveKeymap(String filepath, HashMap<Character, Key_Commands> remap) {
        RunGame.dbgOut("FUNC: SavedGame.saveKeymap()", 2);
        try {
            if (remap == null) {
                throw new Exception("Invalid remapping");
            }
            if (remap.size() == 0) {
                RunGame.dbgOut("Keymap Save: remap size is 0", 3);
                return 0;
            }

            File saveFile = validateFile(filepath, KEY_EXT);
            if (saveFile == null)
                throw new Exception("Invalid keymap save filepath");

            saveFile.createNewFile(); // create the save file if it doesn't already exist

            // open or create the save file
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            Document save = docBuilder.newDocument();
            Element root = save.createElement("remapping");
            save.appendChild(root);

            Element e_version = save.createElement("version");
            e_version.appendChild(save.createTextNode(Long.toString(SAVE_DATA_VERSION)));
            root.appendChild(e_version);

            Element eKeymap = save.createElement("keymap");
            Element tmp_eKey;
            for (Map.Entry<Character, Key_Commands> e : remap.entrySet()) {
                tmp_eKey = save.createElement("remap");
                tmp_eKey.setAttribute("key", e.getKey().toString());
                tmp_eKey.appendChild(save.createTextNode(e.getValue().name()));
                eKeymap.appendChild(tmp_eKey);
                RunGame.dbgOut("Keymap adding: <" + e.getKey().toString() + " -> " + e.getValue().name() + ">", 5);
            }
            root.appendChild(eKeymap);
            RunGame.dbgOut("Keymap object added to DOM", 3);

            // write the content into xml file
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(save);
            StreamResult result = new StreamResult(saveFile);
            transformer.transform(source, result); // actually write the XML to the file

            RunGame.dbgOut("Keymap successfully saved.", 2);
            return 1; // Return SUCCESS
        } catch (Exception e) {
            RunGame.errOut(e, true);
        }
        return 0; // Return FAILURE
    }

    /**
     * Validates a given filepath and returns a File object representing a valid file
     * <p>NOTE: This function DOES NOT create a the file it validates. It does verify that the file that it returns
     * can be read-from and written-to.</p>
     * @param filepath Either a file path, a directory path for an autogenerate file, or null to auto-generate in the
     *                 current directory.
     * @param fileExt The file extension to look for or create with
     * @return Either a valid File object or null, if there was a problem validating the file
     */
    private static File validateFile(String filepath, String fileExt) {
        RunGame.dbgOut("FUNC: validateFile()", 3);
        File ret_file;
        // if the filepath is null or 0-length, autogenerate the file in the current dir
        if (filepath == null || filepath.length() == 0) {
            ret_file = new File(autoGen(null, fileExt));

            RunGame.dbgOut("Validate File: returning autogen file: " + ret_file.getPath());
            return ret_file; // return the file
        }

        // otherwise, create a new file object
        ret_file = new File(filepath);

        // if the filepath is a directory, autogenerate a new file in that directory
        if (ret_file.isDirectory()) {
            ret_file = new File(autoGen(ret_file.getPath(), fileExt));
        }
        // otherwise, the file is a local file. Append with the appropriate extension
        else if (!ret_file.getAbsolutePath().endsWith(fileExt))
            ret_file = new File(ret_file.getAbsolutePath() + fileExt);

        // Check that the file can be read and written to
        if (!ret_file.canRead() || !ret_file.canWrite())
            return null;

        RunGame.dbgOut("Validate File: returning found file: " + ret_file.getPath(), 4);
        return ret_file;
    }
}
