/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Entity;
import src.controller.Item;
import src.controller.Occupation;

import java.io.Serializable;

/**
 *
 * @author JohnReedLOL
 */
public class MapEntity_Relation extends MapDrawableThing_Relation {

    private final Entity entity_;

    public MapEntity_Relation(Entity entity,
            int x_respawn_point, int y_respawn_point) {
        entity_ = entity;
        x_respawn_point_ = x_respawn_point;
        y_respawn_point_ = y_respawn_point;
    }
    private final int x_respawn_point_;
    private final int y_respawn_point_;

    public void spawn(Entity toSpawn, int time_until_spawn) {

    }

    /**
     * Moves the entity that this relation refers to over x and up y
     *
     * @param x x displacement
     * @param y y displacement
     * @return error codes: see function pushEntityInDirection() in
     * MapDrawableThing_Relation
     * @author John-Michael Reed
     */
    public int moveInDirection(int x, int y) {
        return super.pushEntityInDirection(entity_, x, y);
    }

    public void sendAttack(int x, int y) {

    }

    public void recieveAttack(int damage) {

    }

    /**
     * An item underneath you can be picked up using the parameters 0,0. 0 if
     * item is picked up successfully, -1 if no item is on the specified tile.
     *
     * @param x
     * @param y
     * @return error_code
     */
    public int pickUpItemInDirection(int x, int y) {
        int error_code = -1;

        Item itemToBePickedUp = current_map_reference_.removeTopItem(x + getMyXCordinate(), y + getMyYCordinate());
        if (itemToBePickedUp != null) {
            entity_.addItemToInventory(itemToBePickedUp);
            error_code = 0;
        }

        return error_code;
    }
    /**
     * 
     * @return -1 if no item can be dropped (inventory empty)
     */
    public int dropItem() {
        Item itemToBeDropped = entity_.pullFirstItemOutOfInventory();
        if (itemToBeDropped != null) {
            current_map_reference_.addItem(itemToBeDropped, this.getMapTile().x_, this.getMapTile().y_);
            return 0;
        } else {
            return -1;
        }
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    // </editor-fold>
}
