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

    private final Map map_reference_ = Map.getMyReferanceToTheMap(this);

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
}
