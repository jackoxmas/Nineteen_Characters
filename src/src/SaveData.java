/*
 * Implementor: Alex Stewart
 */
package src;

import src.model.MapDrawableThing_Relation;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 41d3n on 2015-02-14.
 */
public interface SaveData {
    public static String CUSTOMLINK = "linkOther";
    public static String CUSTOMREAD = "readOther";
    public static String CUSTOMWRITE = "writeOther";
    public String getSerTag();
    //public void setDTRelation(MapDrawableThing_Relation dtr) throws ClassNotFoundException;
    //public Class<? extends SaveData> deserialize(ObjectInputStream ois, LinkedList<Integer> out_refHashes) throws ClassNotFoundException, IOException;
    //public void relink(Object[] refs);
    //public void serialize(ObjectOutputStream oos, HashMap<SaveData, Boolean> savMap) throws IOException;

    /*
    private void linkOther (ArrayDeque<SaveData> refs) {

    }

    private void readOther (ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {

    }

    private void writeOther (ObjectOutputStream oos, HashMap<SaveData, Boolean> saveMap) throws IOException {

    }
     */
}
