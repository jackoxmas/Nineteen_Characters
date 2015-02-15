/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.Main;
import src.SaveData;
import src.SavedGame;
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
public class MapMain_Relation implements  SaveData {

    //private final Map map_reference_ = Map.getMyReferanceToTheMap(this);
    private Map current_map_reference_;

    public static final int MAX_NUMBER_OF_WORLDS = 1;
    private static int number_of_worlds_generated_ = 0;

// private MapMain_Relation(Map map) {
    // current_map_reference_ = map;
    // }

    /**
     * Creates a new map and binds this mmr to that map
     *
     * @param x - width of newly creared map
     * @param y - height of newly created map
     */
    public MapMain_Relation(int x, int y) {
        bindToNewMapOfSize(x, y);
    }

    /**
     * Creates a new map and associates this maprelation with that map: This is
     * the first function that a MapMain_Relation must call.
     *
     * @param x - width of the map
     * @param y - height of the map
     * @author John-Michael Reed
     */
    public void bindToNewMapOfSize(int x, int y) {
        if (number_of_worlds_generated_ >= MAX_NUMBER_OF_WORLDS) {
            System.err.println("Number of world allowed: "
                    + MAX_NUMBER_OF_WORLDS);
            System.err.println("Number of worlds already in existence: "
                    + number_of_worlds_generated_);
            System.err.println("Please don't make more than "
                    + MAX_NUMBER_OF_WORLDS + " worlds.");
            System.exit(-1);

        } else {
            current_map_reference_ = new Map(x, y);
            ++number_of_worlds_generated_;
        }
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

    /**
     * Adds an entity to the map.
     *
     * @param e - entity to be added.
     * @param x - x position
     * @param y - y position
     * @return -1 on fail, 0 on success
     */
    public int addEntity(Entity e, int x, int y) {
        int error_code = current_map_reference_.addEntity(e, x, y);
        return error_code;
    }

    /**
     * Removes an Avatar from the map.
     *
     * @param a
     * @return -1 on fail, 0 on success
     */
    public int removeAvatar(Avatar a) {
        a.getMapRelation().associateWithMap(null);
        return current_map_reference_.removeAvatar(a);
    }

    /**
     * Removes an Entity from the map.
     *
     * @param e - entity to be removed
     * @return -1 on fail, 0 on success
     */
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
        if (current_map_reference_ == null) {
            System.out.println("A");
        }
        if (view == null) {
            System.out.println("B");
        }
        if (view.getMapRelation() == null) {
            System.out.println("C");
        }
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


    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    @Override
    public String getSerTag() {
        return "RELATION_MAP_MAIN";
    }
    // </editor-fold>
}