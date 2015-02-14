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
 * @author JohnMichaelReed (includes inner functions)
 */
public class MapMain_Relation {

    //private final Map map_reference_ = Map.getMyReferanceToTheMap(this);
    private Map current_map_reference_;

    /**
     * Creates a new map and associates this maprelation with that map. This is
     * the first function that a new MapMain_Relation must call.
     *
     * @author John-Michael Reed
     * @param x - width of the map
     * @param y - height of the map
     */
    public void createNewMap(int x, int y) {
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
        return current_map_reference_.removeTopItem(i, x, y);
    }

    public MapTile getTile(int x, int y) {
        return current_map_reference_.getTile(x, y);
    }

    /**
     * Once a tile has terrain, that terrain is constant.
     *
     * @param t
     * @param x
     * @param y
     * @return error code
     */
    public int initializeTerrain(Terrain t, int x, int y) {
        return current_map_reference_.initializeTerrain(t, x, y);
    }
}
