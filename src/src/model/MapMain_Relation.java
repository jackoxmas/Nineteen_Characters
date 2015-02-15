/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.Main;
import src.SaveData;
import src.controller.Avatar;
import src.controller.Entity;
import src.controller.Item;
import src.controller.Terrain;
import src.view.MapView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Allows for the initialization of the map.
 *
 * @author JohnMichaelReed (includes inner functions)
 */
public class MapMain_Relation implements SaveData {

    //private final Map map_reference_ = Map.getMyReferanceToTheMap(this);
    private Map current_map_reference_;

    private MapMain_Relation(Map map) {
        current_map_reference_ = map;
    }

    /**
     * Creates a new map and associates this maprelation with that map: 
     * This is the first function that a MapMain_Relation must call.
     * @author John-Michael Reed
     * @param x - width of the map
     * @param y - height of the map
     */
    public void bindToNewMapOfSize(int x, int y) {
        current_map_reference_ = new Map(x, y);
    }

    public MapMain_Relation() {
        current_map_reference_ = null;
    }

    public Map getMyMap() {
        return current_map_reference_;
    }

    /**
     * Adds an avatar to the map
     *
     * @param a
     * @param x
     * @param y
     * @return -1 on fail, 0 on success
     */
    public int addAvatar(Avatar a, int x, int y) {
        int error_code = current_map_reference_.addAvatar(a, x, y);
        return error_code;
    }

    public int addEntity(Entity e, int x, int y) {
        int error_code = current_map_reference_.addEntity(e, x, y);
        return error_code;
    }

    public int removeAvatar(Avatar a) {
        a.getMapRelation().associateWithMap(null);
        return current_map_reference_.removeAvatar(a);
    }

    public int removeEntity(Entity e) {
        e.getMapRelation().associateWithMap(null);
        return current_map_reference_.removeEntity(e);
    }

    public int addItem(Item i, int x, int y) {
        int error_code = current_map_reference_.addItem(i, x, y);
        return error_code;
    }

    public Item removeTopItem(Item i, int x, int y) {
        i.getMapRelation().associateWithMap(null);
        return current_map_reference_.removeTopItem(x, y);
    }

    public MapTile getTile(int x, int y) {
        return current_map_reference_.getTile(x, y);
    }
    
    public void addViewToMap(MapView view) {
    	if(current_map_reference_ == null){System.out.println("A");}
    		if(view == null){System.out.println("B");}
    		if(view.getMapRelation() == null){System.out.println("C");}
    	view.getMapRelation().associateWithMap(current_map_reference_);
    }

    /**
     * Once a tile has terrain, that terrain is constant.
     *
     * @param t
     * @param x
     * @param y
     * @return error code
     */
    public int addTerrain(Terrain t, int x, int y) {
        return current_map_reference_.initializeTerrain(t, x, y);
    }


    @Override
    public String getSerTag() {
        return "RELMAPMAIN";
    }

    @Override
    public Object deserialize(ObjectInputStream ois, LinkedList<Integer> out_refHashes) throws ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public void relink(Object[] refs) {
        if (refs.length > 0 && refs[0] instanceof Map)
            this.current_map_reference_ = (Map)refs[0];
        else
            Main.errOut("Invalid Map reference");
    }

    @Override
    public void serialize(ObjectOutputStream oos, HashMap<SaveData, Boolean> refMap) throws IOException {

    }
    // </editor-fold>
}
