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
import src.model.constructs.Entity;
import src.model.constructs.items.Item;

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
    public static final long SAVE_DATA_VERSION = 4;
    public static final String SAVE_EXT = ".xml";
    public static final String KEY_EXT = ".key";
    public static final char SAVE_ITERATOR_FLAG = '_';
    // SAVE FILE FORMAT: yyMMdd_<number>.sav

    // XML node names
    private static final String XML_ROOT = "save_game";
    private static final String XML_SAVEVERSION = "version";
    private static final String XML_MAP = "map";
    private static final String XML_KEYMAP = "keymap";
    private static final String XML_KEY = "key";
    private static final String XML_ROOT_CONTROLLER = "remap";
    public static final String XML_MAP_MAPGRID = "map_grid";
    public static final String XML_MAP_MAPGRID_WIDTH = "width";
    public static final String XML_MAP_MAPGRID_HEIGHT = "height";
    public static final String XML_MAP_TIME = "time";

    //<editor-fold desc="PUBLIC METHODS" defaultstate="collapsed">

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

            src.model.Map mm = xml_readMap(load, (Element) ns_result.item(0));

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

    public static HashMap<Character, Key_Commands> loadKeymap(String filepath) {
        try {
            File loadFile = validateFile(filepath, KEY_EXT);
            if (loadFile == null)
                throw new Exception("Could not load file");

            if (!loadFile.exists()) throw new Exception("Load file does not exist");

            HashMap<Character, Key_Commands> ret_kMap = new HashMap<>();

            // Initialize DOM document
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(false);
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            Document load = docBuilder.parse(loadFile.getAbsolutePath());
            RunGame.dbgOut("XML Key file loaded from: " + loadFile.getPath(), 3);

            // Capture root element
            Element root = load.getDocumentElement();
            root.normalize();
            RunGame.dbgOut("XML Captured document root as: " + root.getNodeName(), 4);

            if (!validateDataVersion(root))
                throw new Exception("Invalid data version");

            // Load the user controller
            NodeList ns_result = root.getElementsByTagName(XML_KEYMAP);
            int numkeys = 0;
            Node tmp_nRemap, tmp_nKey;
            Character tmp_char;
            Key_Commands tmp_cmd;

            for (; numkeys < ns_result.getLength(); numkeys++) {
                tmp_nRemap = ns_result.item(numkeys);
                tmp_nKey = tmp_nRemap.getAttributes().getNamedItem(XML_KEY);
                if (tmp_nKey == null
                        || tmp_nKey.getNodeValue().length() != 1
                        || tmp_nKey.getTextContent() == null
                        || tmp_nKey.getTextContent().length() == 0) {
                    RunGame.errOut("Malformed key remapping @ index = " + numkeys);
                    continue;
                }

                tmp_char = tmp_nKey.getNodeValue().charAt(0);
                tmp_cmd = Key_Commands.valueOf(tmp_nKey.getTextContent());
                if (tmp_cmd == null) {
                    RunGame.errOut("Malformed key remapping, invalid cmd @ index = " + numkeys);
                    continue;
                }

                ret_kMap.put(tmp_char, tmp_cmd);
                RunGame.dbgOut("Loaded mapping: " + tmp_char + " -> " + tmp_cmd.name(), 5);
            }
            RunGame.dbgOut("Processed " + numkeys + " key remaps.", 3);

            return ret_kMap;
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

            // MAP
            Element e_map = save.createElement(XML_MAP);

            xml_writeMap(save, e_map, map);

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

            Element eKeymap = save.createElement(XML_KEYMAP);
            Element tmp_eKey;
            for (Map.Entry<Character, Key_Commands> e : remap.entrySet()) {
                tmp_eKey = save.createElement(XML_ROOT_CONTROLLER);
                tmp_eKey.setAttribute(XML_KEY, e.getKey().toString());
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

    //</editor-fold>

    //<editor-fold desc="PRIVATE METHODS" defaultstate="collapsed">

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

    private static boolean validateDataVersion(Element root) {
        // Validate save data version
        NodeList ns_result = root.getElementsByTagName("version"); // ns_result used for node search results

        // If there is no "version" tag, FAIL
        if (ns_result.getLength() == 0) {
            RunGame.errOut("XML Error: no save version found - cannot load");
            return false;
        }
        // If there are multiple "version" tags, notify
        else if (ns_result.getLength() > 1) {
            RunGame.errOut("XML WARN: save file has [" + ns_result.getLength() + "] versions. Ignoring all but first.");
        }

        // Parse version number from first version tag
        Integer v = Integer.parseInt(ns_result.item(0).getTextContent());
        RunGame.dbgOut("XML DBG: Checking save versions. Expecting [" + SAVE_DATA_VERSION + "], got: [" + v + "].", 3);
        // If the versions do not match, FAIL
        if (SAVE_DATA_VERSION != v) {
            RunGame.errOut("XML ERR: save data version mismatch. Save game cannot be loaded.");
            return false;
        }

        return true; // Otherwise, return SUCCESS
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

    //<editor-fold desc="XML WRITING" defaultstate="collapsed">

    private static src.model.Map xml_readMap(Document doc, Element e_map) {

        Element e_mapgrid = (Element) e_map.getElementsByTagName(SavedGame.XML_MAP_MAPGRID).item(0);
        Integer map_x = Integer.parseInt(e_mapgrid.getAttributes().getNamedItem(SavedGame.XML_MAP_MAPGRID_WIDTH).getNodeValue());
        Integer map_y = Integer.parseInt(e_mapgrid.getAttributes().getNamedItem(SavedGame.XML_MAP_MAPGRID_HEIGHT).getNodeValue());
        RunGame.dbgOut("XML Parsed: map grid x = " + map_x, 4);
        RunGame.dbgOut("XML Parsed: map grid y = " + map_y, 4);

        src.model.Map mm = new src.model.Map(map_x, map_y);
        return mm;
    }

    /**
     * Writes this map to the given XML Element in the given XML document
     *
     * @param doc The XML Document to write to
     * @param e_map The XML Element to write to
     * @return 0 = success
     * @author Alex Stewart
     */
    private static int xml_writeMap(Document doc, Element e_map, src.model.Map map) {
        // MAP::TIME)
        Element e_time = doc.createElement(SavedGame.XML_MAP_TIME);
        e_map.appendChild(doc.createTextNode(Integer.toString(map.getTime())));

        // MAP::MAP_GRID
        Element e_map_grid = doc.createElement(SavedGame.XML_MAP_MAPGRID);
        e_map_grid.setAttribute(SavedGame.XML_MAP_MAPGRID_WIDTH, Integer.toString(map.getWidth()));
        e_map_grid.setAttribute(SavedGame.XML_MAP_MAPGRID_HEIGHT, Integer.toString(map.getHeight()));
        RunGame.dbgOut("Writing map grid of size: " + map.getWidth() + "x" + map.getHeight(), 4);

        Element e_l;
        for (int j = 0; j < map.getHeight(); j++) {
            for (int i = 0; i < map.getWidth(); i++) {
                e_l = doc.createElement("map_tile");
                e_l.setAttribute("x", Integer.toString(i));
                e_l.setAttribute("y", Integer.toString(j));

                MapTile[][] grid = map.getMapGrid();

                // Terrain
                Terrain terr = grid[j][i].getTerrain();
                RunGame.dbgOut("Writing map tile [" + i + ", " + j + "]", 5);
                if (terr == null) {
                    RunGame.errOut("xml_writeMap: null terrain @ [" + i + ", " + j + "]");
                    return 1;
                }
                xml_writeTerrain(doc, e_l, terr);

                /*
                // Entity
                Entity ent = grid[i][j].getEntity();
                if (ent != null) {
                    xml_writeEntity(doc, e_l, ent);
                }

                // Item list
                if (map.getMapGrid()[i][j].getItemList().size() != 0) {
                    Element e_itemlist = doc.createElement("item_list");
                    for (Item item : grid[i][j].getItemList()) {
                        xml_writeItem(doc, e_itemlist, item);
                    }
                    e_l.appendChild(e_itemlist);
                }
                */

                e_map_grid.appendChild(e_l);
            }
        }

        // MAP - APPEND
        e_map.appendChild(e_time);
        e_map.appendChild(e_map_grid);

        return 0; // Return success
    }

    /**
     * Writes an entity to an XML element
     *
     * @param doc The DOM document to write to
     * @param parent The parent element to write this entity in
     * @param entity The Entity object to write
     * @return The entity's DOM Element, or null - on failure.
     */
    private static Element xml_writeEntity(Document doc, Element parent, Entity entity) {
        Element e_entity = doc.createElement("entity");

        // Name
        e_entity.setAttribute("name", entity.getName());

        /*
         if (this.avatar_list_.containsValue(entity)) {
         e_entity.appendChild(doc.createElement("b_avatar"));
         }*/
        // Direction
        Element e_dir = doc.createElement("direction");
        e_dir.appendChild(doc.createTextNode(entity.getFacingDirection().toString()));

        // Item List
        Element e_itemList = doc.createElement("item_list");
        // write inventory items to xml
        //Item equipped = entity.getEquipped(); //TODO FIX
        //ArrayList<Item> tmp_inv = entity.getInventory();
        Element tmp_eInvItem; // temp inventory item
        for (int i = 0; i < entity.getInventory().size(); i++) {
            //tmp_eInvItem = xml_writeItem(doc, e_itemList, tmp_inv.get(i));

            /*
             if (tmp_inv.get(i) == equipped)
             tmp_eInvItem.appendChild(doc.createElement("b_equipped")); */
            //e_itemList.appendChild(tmp_eInvItem);
        }
        e_entity.appendChild(e_itemList);

        xml_writeStatsDrawable(doc, e_entity, (DrawableThingStatsPack) entity.getStatsPack());
        xml_writeEntityStatsPack(doc, e_entity, entity.getStatsPack());

        parent.appendChild(e_entity);

        return e_entity;
    }

    // TODO REMOVE
    /**
    private static Element xml_writeItem(Document doc, Element parent, Item item) {
        Element e_item = doc.createElement("item");
        e_item.setAttribute("id", item.getID())

        // Name
        //e_item.setAttribute("name", item.getName());
        // Is One Shot
        if (item.isOneShot()) {
            e_item.appendChild(doc.createElement("b_one_shot"));
        }
        // Is Passable
        if (item.isPassable()) {
            e_item.appendChild(doc.createElement("b_passable"));
        }
        // Goes in Inventory
        if (item.goesInInventory()) {
            e_item.appendChild(doc.createElement("b_inventory-able"));
        }

        xml_writeStatsDrawable(doc, e_item, item.getStatsPack());

        parent.appendChild(e_item);
        return e_item;
    }*/

    private static void xml_writeStatsDrawable(Document doc, Element parent, DrawableThingStatsPack stats) {
        if (stats == null) {
            RunGame.errOut("xml_writeStatsDrawable: null statspack");
            return;
        }

        Element trans_eStat;

        if (stats.getArmor_rating_() != 0) {
            trans_eStat = doc.createElement("armor_rating");
            trans_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getArmor_rating_())));
            parent.appendChild(trans_eStat);
        }
        // TODO FIX:
        /*
         if (stats.getDefensive_rating_() != 0) {
         trans_eStat = doc.createElement("def_rating");
         trans_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getDefensive_rating_())));
         e_stats.appendChild(trans_eStat);
         }*/
        if (stats.getOffensive_rating_() != 0) {
            trans_eStat = doc.createElement("off_rating");
            trans_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getOffensive_rating_())));
            parent.appendChild(trans_eStat);
        }
    }

    private static void xml_writeEntityStatsPack(Document doc, Element parent, EntityStatsPack stats) {
        if (stats == null) {
            RunGame.errOut("xml_writeStatsEntity: null statspack");
            return;
        }

        Element tra_eStat;

        if (stats.getLives_left_() != 0) {
            tra_eStat = doc.createElement("lives");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getLives_left_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getStrength_level_() != 0) {
            tra_eStat = doc.createElement("strength");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getStrength_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getAgility_level_() != 0) {
            tra_eStat = doc.createElement("agility");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getAgility_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getIntellect_level_() != 0) {
            tra_eStat = doc.createElement("intellect");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getIntellect_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getHardiness_level_() != 0) {
            tra_eStat = doc.createElement("hardness");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getHardiness_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getQuantity_of_experience_() != 0) {
            tra_eStat = doc.createElement("XP");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getQuantity_of_experience_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getMovement_level_() != 0) {
            tra_eStat = doc.createElement("movement");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getMovement_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getMax_life_() != 0) {
            tra_eStat = doc.createElement("max_life");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getMax_life_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getMax_mana_() != 0) {
            tra_eStat = doc.createElement("max_mana");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getMax_mana_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getMoves_left_in_turn_() != 0) {
            tra_eStat = doc.createElement("moves_remaining");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getMoves_left_in_turn_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getCached_current_level_() != 0) {
            tra_eStat = doc.createElement("level");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getCached_current_level_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getCurrent_life_() != 0) {
            tra_eStat = doc.createElement("life");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getCurrent_life_())));
            parent.appendChild(tra_eStat);
        }
        if (stats.getCurrent_mana_() != 0) {
            tra_eStat = doc.createElement("mana");
            tra_eStat.appendChild(doc.createTextNode(Integer.toString(stats.getCurrent_mana_())));
            parent.appendChild(tra_eStat);
        }
    }

    private static void xml_writeTerrain(Document doc, Element parent, Terrain terr) {
        if (terr.getName() == null) {
            RunGame.errOut("xml_writeTerrain: null Terrain name");
            return;
        }
        parent.setAttribute("name", terr.getName());

        // BOOLEANS:
        if (terr.isMountain()) {
            parent.appendChild(doc.createElement("b_mountain"));
        }

        if (terr.isWater()) {
            parent.appendChild(doc.createElement("b_water"));
        }

        // Terrain::Decal - only write if non-null
        if (terr.getDecal() != '\u0000') {
            Element e_decal = doc.createElement("decal");
            e_decal.appendChild(doc.createTextNode(Character.toString(terr.getDecal())));
            parent.appendChild(e_decal);
        }

        // Terrain::Character
        Element e_dChar = doc.createElement("terr_char");
        e_dChar.appendChild(doc.createTextNode(Character.toString(terr.getRepresentation())));
        parent.appendChild(e_dChar);

        // TODO TERRAIN COLOR
        // Terrain::Color - only write if non-null
        /*
         if (terr.color_ != null) {
         Element e_color = doc.createElement("color");
         e_color.appendChild(doc.createTextNode(terr.color_.name()));
         e_Terrain.appendChild(e_color);
         }*/
    }

    private static Element xml_writeDrawable(Document doc, Element parent, DrawableThing dt) {
        if (dt == null) {
            RunGame.errOut("Invalid drawable thing reference");
            return null;
        }

        Element e_dt = doc.createElement("drawable_thing");

        e_dt.setAttribute("id", Integer.toString(dt.getID()));

        switch (dt.getID()) {
            case 0:
                RunGame.errOut("Attempted to write invalid DrawableThing ID (0)");
                break;
            case 1:
                xml_writeTerrain(doc, e_dt, (Terrain)dt);
                break;
            default:
                RunGame.errOut("Attempted to write invalid DrawableThing ID (default)");
        };

        parent.appendChild(e_dt);
        return e_dt;
    }
    /**
     * MAGIC NUMBERS:
     * 0    Reserved
     * 1    Terrain
     * 2    Bow
     * 3    Invisibility Serum
     * 4    Knights Serum
     * 5    Two Handed Sword
     * 6    Trap
     * 7    Staff
     * 8    Shield
     * 9    One Handed Sword
     * 10   One Way Teleport Item
     * 11   One Shot Area Effect Item
     * 12   Temporary Obstacle Item
     * 13   Obstacle Removing Item
     * 14   Permanent Obstacle Item
     * 15   Monster
     * 16   Avatar
     * 17   Villager
     * 18   Merchant
     */

    //</editor-fold>
    //</editor-fold>
}
