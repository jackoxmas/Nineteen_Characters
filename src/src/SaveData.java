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

    /*
    protected void linkOther (ArrayDeque<SaveData> refs) {

    }

    protected void readOther (ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {

    }

    protected void writeOther (ObjectOutputStream oos, HashMap<SaveData, Boolean> saveMap) throws IOException {

    }
     */
}
