/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Avatar;
import src.controller.Entity;
import src.controller.Item;
import src.controller.Terrain;

/**
 * Allows for the initialization of the map.
 *
 * @author JohnMichaelReed
 */
public class MapMain_Relation {

    private Map map_reference_;

    public MapMain_Relation(){
        map_reference_ = Map.getMyReferanceToTheMap(this);
    }

    private MapMain_Relation(Map map) {
        map_reference_ = map;
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
        return map_reference_.addAvatar(a, x, y);
    }

    public int addEntity(Entity e, int x, int y) {
        return map_reference_.addEntity(e, x, y);
    }

    public int removeAvatar(Avatar a) {
        return map_reference_.removeAvatar(a);
    }

    public int removeEntity(Entity e) {
        return map_reference_.removeEntity(e);
    }

    public int addItem(Item i, int x, int y) {
        return map_reference_.addItem(i, x, y);
    }

    public Item removeTopItem(Item i, int x, int y) {
        return map_reference_.removeTopItem(i, x, y);
    }

    /**
     * Once a tile has terrain, that terrain is constant.
     * @param t
     * @param x
     * @param y
     * @return error code
     */
    public int initializeTerrain(Terrain t, int x, int y) {
        return map_reference_.initializeTerrain(t, x, y);
    }
    
    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    /**
     * Build a Map and MapMain_Relation from the serialization stream.
     * @param inStream The java.io.ObjectInputStream to pull data from
     * @throws Exception 
     */
    public static MapMain_Relation deserializeMap(java.io.ObjectInputStream inStream) throws Exception {
        try {
            MapMain_Relation mmr = new MapMain_Relation((Map)inStream.readObject());
            return mmr;
        } catch (Exception e) {
            throw e;
        }
    }
    
    /**
     * Write the Map to the serialization stream
     * @param outStream The java.io.ObjectOutputStream to push data to
     * @throws Exception 
     */
    public void serializeMap(java.io.ObjectOutputStream outStream) throws Exception {
        try {
            outStream.writeObject(map_reference_);
        } catch (Exception e) {
            throw e;
        }
    }
    // </editor-fold>
}
