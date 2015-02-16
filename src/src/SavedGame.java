/*
 * Implementor: Alex Stewart
 * Last Update: 15-02-13
 */
package src;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

import src.model.MapMain_Relation;

/**
 * This class manages a saved game object. A saved game has a file path and 
 * methods to interact with that file (loading and saving data).
 * 
 * @author Alex Stewart
 */
public class SavedGame {
    private String filePath_ = null;

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
        filePath_ = filePath;
    }
    
    public MapMain_Relation loadFile() {
        Main.dbgOut("Save game load requested from: " + filePath_, 2);

        // Error checking
        if (filePath_ == null) {
            Main.errOut("Provided filepath is NULL");
            return null;
        }

        MapMain_Relation new_mmr = null;
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

                // Pull the first serialized object out. It is the map-main relationship
                if (new_mmr == null) new_mmr = MapMain_Relation.class.cast(sObject);

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

            // Close streams
            ois.close();
            fis.close();

        } catch (IOException ioe) {
            Main.errOut(ioe, true);
            return null;
        } catch (Exception e) {
            Main.errOut(e, true);
            return null;
        }
        
        return new_mmr;
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

                oos.writeLong(genSDVersion(sObject.getSerTag()));
                oos.writeInt(getHash(sObject)); // write caller's hash
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
            if (!params[0].equals(ObjectInputStream.class) || !params[1].equals(ArrayDeque.class))
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
        Main.dbgOut("New save game requested for dir: " + directory);
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
            Main.errOut(e);
        }
        // iterator is now the correct unique ID
        // ready to construct path
        String path = date + SAVE_ITERATOR_FLAG + iterator + SAVE_EXT;
        return new SavedGame(path);
    }

    private static void ripFields (Object caller, ArrayDeque<Field> out_fields, ArrayDeque<Field> out_SDs) throws IllegalAccessException {
        Field[] fs = caller.getClass().getDeclaredFields();
        int fMod;
        for (Field f : fs) {    // for each field in the class
            f.setAccessible(true);  // ignore stupid concepts like (private or final)
            fMod = f.getModifiers();    // get any other modifiers on the field
            if (Modifier.isTransient(fMod)) continue;   // skip this field if it is "transient"
            if (Modifier.isStatic(fMod)) continue;      // skip if "static"

            Class<?>[] interfaces = f.getType().getInterfaces();    // get the field's type's interfaces
            boolean isSD = false;   // check to see if the field is a SaveData object reference
            for (Class<?> i : interfaces)
                if (i == SaveData.class) isSD = true;

            if (isSD) {
                out_SDs.push(f);
            } else {
                out_fields.push(f);
            }
        }
    }


}
