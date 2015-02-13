/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.util.LinkedList;
import java.io.Serializable;
import java.util.ListIterator;
import src.controller.Entity;
import src.controller.Item;
import src.controller.Terrain;

/**
 *
 * @author JohnReedLOL
 */
final class MapTile implements Serializable {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("MapTile", 35);

    public final int x_;
    public final int y_;

    MapTile(int x, int y) {
        x_ = x;
        y_ = y;
        terrain_ = null;
        entity_ = null;
        items_ = new LinkedList<Item>();
    }

    private Terrain terrain_;
    private Entity entity_;
    private LinkedList<Item> items_;

    /**
     * Returns 0 on success, returns -1 if terrain is already set.
     *
     * @param terrain - terrain to be added to the tile
     */
    public int initializeTerrain(Terrain terrain) {
        if (this.terrain_ == null && terrain != null) {
            terrain.getMapRelation().setMapTile(this);
            this.terrain_ = terrain;
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Only works if there in no entity there already.
     *
     * @param entity - entity to be added to the tile
     * @return error codes: -1 if an entity is already there.
     */
    public int addEntity(Entity entity) {
        if (this.entity_ == null && entity != null) {
            entity.getMapRelation().setMapTile(this);
            this.entity_ = entity;
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Will return -1 if entity already equals null
     *
     * @return 0 on success, non-zero on error
     */
    public int removeEntity() {
        if (this.entity_ == null) {
            return -1;
        } else {
            this.entity_.getMapRelation().setMapTile(null);
            this.entity_ = null;
            return 0;
        }
    }

    /**
     * Returns 0 on success, -1 when blocking item is already there, -2 when
     * item is null
     *
     * @param item - item to be added to the tile
     * @return -
     */
    public int addItem(Item item) {
        if (item == null) {
            return -2;
        }
        // Make sure there are no impassible items on this tile
        ListIterator<Item> listIterator = items_.listIterator();
        while (listIterator.hasNext()) {
            if (!listIterator.next().isPassable()) {
                return -1;
            }
        }
        // Add the item.
        item.getMapRelation().setMapTile(this);
        this.items_.add(item);
        return 0;
    }

    public Terrain getTerrain() {
        return this.terrain_;
    }

    public Entity getEntity() {
        return this.entity_;
    }

    public Item viewTopItem() {
        return this.items_.peekLast();
    }

    public Item removeTopItem() {
        return this.items_.removeLast();
    }

    /**
     * Checks the tile to gets its character representation
     * Returns empty space when tile is empty 
     * @return the character that will represent this tile on the map
     * @author Reed, John
     */
    public char getTopCharacter() {
        if (!items_.isEmpty()) {
            return items_.peekLast().getRepresentation();
        }
        else if (entity_ != null) {
            return entity_.getRepresentation();
        }
        else if (terrain_ != null) {
            return terrain_.getRepresentation();
        } else {
            return ' ';
        }
    }
}
