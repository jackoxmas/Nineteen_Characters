/*
 * Implementor: Alex Stewart
 */
package src;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by 41d3n on 2015-02-14.
 */
public interface SaveData {
    public String getSerTag();
    public Object deserialize(ObjectInputStream ois, LinkedList<Integer> out_refHashes) throws ClassNotFoundException, IOException;
    public void relink(Object[] refs);
    public void serialize(ObjectOutputStream oos, HashMap<SaveData, Boolean> refMap) throws IOException;
}
