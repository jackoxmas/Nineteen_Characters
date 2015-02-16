/*
 * Implementor: Alex Stewart
 * Last Update: 15-02-13
 */
package src;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

import src.model.Map;
import src.model.MapTile;
import src.controller.AreaEffectItem;
import src.controller.Avatar;
import src.controller.Item;
import src.model.MapMain_Relation;

/**
 * This class manages a saved game object. A saved game has a file path and 
 * methods to interact with that file (loading and saving data).
 * 
 * @author Alex Stewart
 */
public class SavedGame {
    private String file_path_ = null;
    private ArrayList<String> array_list;

    public static final SimpleDateFormat SAVE_DATE_FORMAT = new SimpleDateFormat("yyMMdd");
    /**
     * SAVE_DATA_VERSION is the data format version number. It should be 
     * incremented any time that the serialization or deserialization sequence 
     * is modified. The version number 0 is reserved. This value has no 
     * relation to the Java native Serialization object ID.
     */
    public static final long SAVE_DATA_VERSION = 1;
    public static final String SAVE_EXT = ".sav";
    public static final char SAVE_ITERATOR_FLAG = '_';
    private static final String SAVE_EOF_STRING = "///END OF FILE///";
    // SAVE FILE FORMAT: yyMMdd_<number>.sav
    
    public SavedGame(String filePath) {
        file_path_ = filePath;
    }

    public int saveGame(Avatar my_avatar) {
    	BufferedWriter bw = null;
    	
    	try {
    		bw = new BufferedWriter( new FileWriter( file_path_ ) );
    		StringBuilder sb = new StringBuilder();
    		
    		sb.append(my_avatar.name_ + "\n");
    		sb.append(my_avatar.getMapRelation().getMyXCoordinate() + "\n");
    		sb.append(my_avatar.getMapRelation().getMyYCoordinate() + "\n");
    		sb.append(my_avatar.getOccupation().toString() + "\n");
    		sb.append(my_avatar.getRepresentation() + "\n");
    		sb.append(my_avatar.getStatsPack().life_ + "\n");
    		sb.append(my_avatar.getStatsPack().mana_ + "\n");
    		sb.append(my_avatar.getStatsPack().offensive_rating_ + "\n");
    		sb.append(my_avatar.getStatsPack().defensive_rating_ + "\n");
    		sb.append(my_avatar.getStatsPack().armor_rating_ + "\n");
    		sb.append(my_avatar.getStatsPack().lives_left_ + "\n");
    		sb.append(my_avatar.getStatsPack().strength_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().agility_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().intellect_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().hardiness_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().quantity_of_experience_ + "\n");
    		sb.append(my_avatar.getStatsPack().movement_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().moves_left_in_turn_ + "\n");
    		sb.append(my_avatar.getStatsPack().cached_current_level_ + "\n");
    		sb.append(my_avatar.getStatsPack().current_life_ + "\n");
    		sb.append(my_avatar.getStatsPack().current_mana_ + "\n");
    		sb.append(my_avatar.getStatsPack().current_offensive_rating_ + "\n");
    		sb.append(my_avatar.getStatsPack().current_defensive_rating_ + "\n");
    		sb.append(my_avatar.getStatsPack().current_armor_rating_ + "\n");
    		Item equipped = my_avatar.getEquipped();
    		if (equipped != null) {
    			sb.append("true\n");
    			sb.append(equipped.name_ + "\n");
    			sb.append(equipped.getRepresentation() + "\n");
    			sb.append(equipped.isPassable() ? "true\n" : "false\n");
    			sb.append(equipped.getStatsPack().life_ + "\n");
    			sb.append(equipped.getStatsPack().mana_  + "\n");
    			sb.append(equipped.getStatsPack().offensive_rating_ + "\n");
    			sb.append(equipped.getStatsPack().defensive_rating_ + "\n");
    			sb.append(equipped.getStatsPack().armor_rating_ + "\n");
    		} else
    			sb.append("false\nnull\nnull\nnull\nnull\nnull\nnull\nnull\nnull\n");
    		ArrayList<Item> inventory = my_avatar.getInventory();
    		for (Item item : inventory) {
    			sb.append(item.name_ + "\n");
    			sb.append(item.getRepresentation() + "\n");
    			sb.append(item.isPassable() ? "true\n" : "false\n");
    			sb.append(item.getStatsPack().life_ + "\n");
    			sb.append(item.getStatsPack().mana_  + "\n");
    			sb.append(item.getStatsPack().offensive_rating_ + "\n");
    			sb.append(item.getStatsPack().defensive_rating_ + "\n");
    			sb.append(item.getStatsPack().armor_rating_ + "\n");
    		}
    		sb.append("map\n");
    		Map map_reference = my_avatar.getMapRelation().getMap();
    		MapTile[][] map_grid = map_reference.getMapGrid();
    		for (int i = 0; i < map_grid.length; i++) {
    			for (int j = 0; j < map_grid[i].length; j++) {
    				MapTile tile = map_grid[i][j];
    				sb.append(tile.x_ + "\n");
    				sb.append(tile.y_ + "\n");
    				sb.append(tile.getTerrain().name_ + "\n");
    				sb.append(tile.getTerrain().getRepresentation() + "\n");
    				sb.append(tile.getTerrain().hasDecal() ? tile.getTerrain().getDecal()+"\n" : "null\n");
    				sb.append(tile.getTerrain().hasWater() + "\n");
    				sb.append(tile.getTerrain().hasMountain() + "\n");
    			}
    		}
    		LinkedList<Item> items_list = map_reference.getItemsList();
    		for (Item item : items_list) {
    			sb.append(item.getMapRelation().getMyXCoordinate() + "\n");
    			sb.append(item.getMapRelation().getMyYCoordinate() + "\n");
    			sb.append(item.isOneShot() ? "true\n" : "false\n");
    			if (item instanceof AreaEffectItem) {
    				sb.append("true\n");
    				sb.append(((AreaEffectItem)item).getEffect() + "\n");
    				sb.append(((AreaEffectItem)item).getPower() + "\n");
    				sb.append(((AreaEffectItem)item).hasBeenActivated() ? "true\n" : "false\n");
    			} else
    				sb.append("false\n0\n0\n0\n");
    			sb.append(item.name_ + "\n");
    			sb.append(item.getRepresentation() + "\n");
				sb.append(item.getViewable() ? "true\n" : "false\n");
				sb.append(item.isPassable() ? "true\n" : "false\n");
    			sb.append(item.goesInInventory() ? "true\n" : "false\n");
    			sb.append(item.getStatsPack().life_ + "\n");
    			sb.append(item.getStatsPack().mana_  + "\n");
    			sb.append(item.getStatsPack().offensive_rating_ + "\n");
    			sb.append(item.getStatsPack().defensive_rating_ + "\n");
    			sb.append(item.getStatsPack().armor_rating_ + "\n");
    		}
    		bw.write(sb.toString());
    		bw.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	} finally {
    		try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
        return 0;
    }

    public MapMain_Relation loadGame() {
        return null;
    }

    /**
     * Generates a new SavedGame object with the file path set to the next available save game file path in the current
     * working directory.
     * @return The new SavedGame object
     */
    public static SavedGame newSavedGame() {
        String pwd = System.getProperty("user.dir"); // get the current working directory
        return newSavedGame(pwd);
    }

    public static SavedGame newSavedGame(String directory) {
        RunGame.dbgOut("New save game requested for dir: " + directory);
        String date = SAVE_DATE_FORMAT.format(new Date()); // get the current date string

        // Search the current directory for existing saves and keep an iterator to append the save name with a unique
        // ID for this day.
        int iterator = 1;
        try {
            File dir = new File(System.getProperty("user.dir"));
            File[] files = dir.listFiles();
            String s_buff;

            int i_buff;
            for (File f : files) { // Search files in directory
                if (f.getName().endsWith(SavedGame.SAVE_EXT)) { // for save files...
                    s_buff = f.getName(); // temprorarily store the filename
                    if(!s_buff.startsWith(date))
                        continue; // if the save isn't from this date, ignore it
                    s_buff = s_buff.substring(s_buff.lastIndexOf('_') + 1, s_buff.lastIndexOf(".")); // otherwise, get the ID
                    i_buff = Integer.parseInt(s_buff);
                    if (i_buff >= iterator) iterator = i_buff + 1; // ensure that the iterator is always ahead by 1
                }
            }
        } catch (Exception e) {
            RunGame.errOut(e);
        }
        // iterator is now the correct unique ID
        // ready to construct path
        String path = date + SAVE_ITERATOR_FLAG + iterator + SAVE_EXT;
        return new SavedGame(path);
    }

    // <editor-fold desc="SAVEGAME SERIALIZATION CORPSE" defaultstate="collapsed">
    /*
    public int loadFile(MapMain_Relation out_mapRel, Exception exep) {
        Main.dbgOut("Save game load requested from: " + filePath_, 2);
        exep = null; // First, set the out Exception variable to null
        // Error checking
        if (filePath_ == null) {
            exep = new Exception("Provided filepath is NULL");
            return 0;
        }

        // Attempt to open the file and read the map object
        try {
            FileInputStream fis = new FileInputStream(filePath_);
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Check the version number
            if (ois.readLong() != SAVE_DATA_VERSION)
                throw new IOException("Invalid save file version number");

            // Deserialize map starting with Map
            HashMap<SaveData, ArrayDeque<Integer>> sdRelations = new HashMap<SaveData, ArrayDeque<Integer>>();
            HashMap<Integer, SaveData> hashes = new HashMap<Integer, SaveData>();


            Class temp;
            Class<? extends SaveData> sd_type;
            SaveData sObject;
            // TIME TO CHEESE IT!
            String cl_string = (String)ois.readUTF();
            while (cl_string.compareTo(SAVE_EOF_STRING) != 0) {
                //sd_type = Class.forName(cl_string).asSubclass(Class<? extends SaveData>);
                sd_type = Class.forName(cl_string).asSubclass(SaveData.class);    // read the first object's class

                ArrayDeque<Integer> relHashes = new ArrayDeque<Integer>(); // object's relationship hashes
                sObject = sd_type.cast(defaultDataRead(sd_type, ois, relHashes));
                Integer objHash = relHashes.pop();
                if (objHash == 0)
                    throw new Exception("Object not deserialized correctly.");

                sdRelations.putIfAbsent(sObject, relHashes);    // add the rel hashes to the rel-hash-map
                hashes.putIfAbsent(objHash, sObject);           // add this object to the hash map
                cl_string = ois.readUTF();  // read the next class name
            };

            // Relink everything
            ArrayDeque<SaveData> refs;
            // Iterate through the relationship map and crosscheck using hash map
            for (Map.Entry<SaveData, ArrayDeque<Integer>> e : sdRelations.entrySet()) {
                refs = new ArrayDeque<SaveData>();
                while (e.getValue().size() > 0) {
                    refs.addLast(hashes.get(e.getValue().pop()));
                }
                defaultDataRelink(e.getKey(), refs); // relink keys to their references
            }

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            Main.errOut(ioe, true);
            return 0;
        } catch (Exception e) {
            exep = e;
            return 0;
        }

        return 1;
    }

    public int saveFile(MapMain_Relation mapRel, Exception out_exep) {
        out_exep = null; // first, set the out Exception variable to null
        Main.dbgOut("Saving Game File to: " + filePath_, 1);
        try {
            FileOutputStream fos = new FileOutputStream(filePath_, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            Main.dbgOut("Obj. Output Stream Open", 3);

            oos.writeLong(SAVE_DATA_VERSION); // write the save version
            Main.dbgOut("Writing SAVE VERSION: " + SAVE_DATA_VERSION, 2);

            //HashMap<SaveData, ArrayDeque<Integer>> sdRelations = new HashMap<SaveData, ArrayDeque<Integer>>();
            HashMap<SaveData, Boolean> saveMap = new HashMap<SaveData, Boolean>();

            SaveData sObject = mapRel;
            saveMap.put(sObject, false);
            do {
                //oos.writeChars(sObject.getClass().getName());
                oos.writeUTF(sObject.getClass().getName());
                Main.dbgOut("Wrote Class Name: " + sObject.getClass().getName(), 4);
                defaultDataWrite(sObject, oos, saveMap);
                //sObject.serialize(oos, saveMap);
                saveMap.replace(sObject, true);
                sObject = null;
                for (HashMap.Entry<SaveData, Boolean> e : saveMap.entrySet()) {
                    if (!e.getValue().booleanValue()) {
                        sObject = e.getKey();
                        break;
                    }
                }
            } while (sObject != null);
            oos.writeUTF(SAVE_EOF_STRING);  // write the EOF flag

            oos.close();
            fos.close();
            Main.dbgOut("Obj. Output Stream & FILE closed.", 3);
            Main.dbgOut("Saving completed successfully.", 1);
            return 1;
        } catch (Exception e) {
            Main.errOut(e, true);
            Main.dbgOut("Saving failed.", 1);
            return 0;
        }
    }

    public static Object defaultDataRead(Class<? extends SaveData> caller_t, ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {
        // read the object's save version and compare to input stream
        Object caller = null;
        try { // Call the default constructor
            for (Constructor<?> m : caller_t.getDeclaredConstructors()) {
                if (m.getParameterCount() == 0) {
                    m.setAccessible(true);
                    caller = m.newInstance();
                    break;
                }
            }
            if (caller == null)
                throw new Exception("NO default constructor in " + caller_t.getName());
        } catch (Exception noCon) {
            Main.errOut(noCon, true);
            Main.errOut(">> Check that " + caller_t.getName() + " has a valid default constructor.");
            return null;
        }
        try {
            final long sdv = genSDVersion(caller_t.cast(caller).getSerTag());
            if (Long.compare(sdv, ois.readLong()) != 0)
                throw new ClassNotFoundException();
            if (out_rels == null) out_rels = new ArrayDeque<Integer>();
            out_rels.add(ois.readInt()); // add caller's hash to refHashes

            // if superclass is SD also, read its fields
            for (Class<?> i : caller.getClass().getSuperclass().getInterfaces()) {
                if (i == SaveData.class) {
                    fieldDataRead(SaveData.class.cast(caller), ois, out_rels);
                    break;
                }
            }
            // now read this class' fields
            fieldDataRead(caller_t.cast(caller), ois, out_rels);
            Method other = getCustomRead(caller_t.cast(caller));

            if (other != null)
                other.invoke(caller, ois, out_rels);

            return caller;
        } catch (IllegalAccessException iae) {
            Main.errOut(iae, true);
        } catch (InvocationTargetException ivte) {
            Main.errOut(ivte, true);
        }
        return null;
    }

    public static void defaultDataRelink(SaveData caller, ArrayDeque<SaveData> refs) {
        // jump to superclass needs first
        for (Class<?> i : caller.getClass().getSuperclass().getInterfaces()) {
            if (i == SaveData.class) {
                fieldDataLink(SaveData.class.cast(caller), refs);
                break;
            }
        }

        // now link caller references
        fieldDataLink(caller, refs);
        Method other = getCustomLink(caller);
        try {
            if (other != null)
                other.invoke(caller, refs);
        } catch (IllegalAccessException iae) {
            Main.errOut(iae, true);
        } catch (InvocationTargetException ivte) {
            Main.errOut(ivte, true);
        }
    }

    public static void defaultDataWrite(SaveData caller, ObjectOutputStream oos, HashMap<SaveData, Boolean> savMap) throws IOException{
        // write the object's save version
        oos.writeLong(genSDVersion(caller.getSerTag()));
        oos.writeInt(getHash(caller)); // write caller's hash

        // if superclass is SD also, write its fields
        for (Class<?> i : caller.getClass().getSuperclass().getInterfaces()) {
            if (i == SaveData.class) {
                fieldDataWrite(SaveData.class.cast(caller), oos, savMap);
                break;
            }
        }

        // now write this class' fields
        fieldDataWrite(caller, oos, savMap);
        Method other = getCustomWrite(caller);
        try {
            if (other != null)
                other.invoke(caller, oos, savMap);
        } catch (IllegalAccessException iae) {
            Main.errOut(iae, true);
        } catch (InvocationTargetException ivte) {
            Main.errOut(ivte, true);
        }
    }

    private static <T extends SaveData> void fieldDataRead(T caller, ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {
        try {
            ArrayDeque<Field> fields = new ArrayDeque<Field>();
            ArrayDeque<Field> SDs = new ArrayDeque<Field>();
            ripFields(caller, fields, SDs);

            for (Field f : fields) {
                f.set(caller, ois.readObject());
            }
            for (Field f : SDs) {
                out_rels.add(ois.readInt());
            }
        } catch (IllegalAccessException e) {
            Main.errOut(e, true);
        }
    }

    private static <T extends SaveData> void fieldDataLink(T caller, ArrayDeque<SaveData> refs) {
        try {
            ArrayDeque<Field> SDs = new ArrayDeque<Field>();
            ripFields(caller, new ArrayDeque<Field>(), SDs);   // We don't care about the fields, in this case

            for (Field f : SDs) {
                f.set(caller, f.getType().cast(refs.pop()));
            }
        } catch (IllegalAccessException e) {
            Main.errOut(e, true);
        }
    }

    private static <T extends SaveData> void fieldDataWrite(T caller, ObjectOutputStream oos, HashMap<SaveData, Boolean> savMap) throws IOException {
        try {
            ArrayDeque<Field> fields = new ArrayDeque<Field>();
            ArrayDeque<Field> SDs = new ArrayDeque<Field>();
            ripFields(caller, fields, SDs);

            for (Field f : fields) {
                oos.writeObject(f.get(caller));
            }
            for (Field f : SDs) {
                oos.writeInt(((SaveData) (f.get(caller))).hashCode());
                savMap.putIfAbsent(SaveData.class.cast(f.get(caller)), false);
            }
        } catch (IllegalAccessException e) {
            Main.errOut(e, true);
        }
    }

    public static final long genSDVersion(String name) {
        if (name == null || name.length() == 0) {
            Main.errOut("SDVersion requested for NULL or empty string");
            return 0;
        }

        long sdv = SAVE_DATA_VERSION;
        for (char c : name.toCharArray()) {
            sdv *= c - 1;
        }
        return sdv;
    }

    private static Method getCustomLink(SaveData caller) {
        for (Method m : caller.getClass().getDeclaredMethods()) {
            m.setAccessible(true);
            if(!m.getName().equals(SaveData.CUSTOMLINK))
                continue;
            if(m.getReturnType().equals(Void.class))
                continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length != 2)
                continue;
            if (!params[0].equals(ArrayDeque.class))
                continue;
            return m;
        }
        return null;
    }

    public static Method getCustomRead(SaveData caller) {
        for (Method m : caller.getClass().getDeclaredMethods()) {
            m.setAccessible(true);
            if(!m.getName().equals(SaveData.CUSTOMREAD))
                continue;
            if(m.getReturnType().equals(Void.class))
                continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length != 2)
                continue;
            if (!params[0].equals(ObjectOutputStream.class) || !params[1].equals(HashMap.class))
                continue;
            return m;
        }
        return null;
    }

    public static Method getCustomWrite(SaveData caller) {
        for (Method m : caller.getClass().getDeclaredMethods()) {
            m.setAccessible(true);
            if(!m.getName().equals(SaveData.CUSTOMWRITE))
                continue;
            if(m.getReturnType().equals(Void.class))
                continue;
            Class<?>[] params = m.getParameterTypes();
            if (params.length != 2)
                continue;
            if (!params[0].equals(ObjectOutputStream.class) || !params[1].equals(HashMap.class))
                continue;
            return m;
        }
        return null;
    }

    public static final int getHash (Object o) {
        return System.identityHashCode(o);
    }*/
    // </editor-fold>
}
