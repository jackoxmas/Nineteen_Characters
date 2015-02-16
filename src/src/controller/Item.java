/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.SaveData;
import src.model.MapItem_Relation;
import src.view.Display;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Class item represents a stackable entity that
 * cannot move itself.
 *
 * @author JohnReedLOL
 */
public class Item extends DrawableThing {

    // map_relationship_ is used in place of a map_referance_
    private MapItem_Relation map_relationship_;

    private boolean hasBeenActivated;
    /**
     * Use this to call functions contained within the MapItem relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    public MapItem_Relation getMapRelation() {
        return map_relationship_;
    }

    public void setMapRelation(MapItem_Relation i) {
        map_relationship_ = i;
    }

    private boolean is_passable_;

    public Item(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot) {
        super(name, representation);
        is_passable_ = is_passable;
        map_relationship_ = new MapItem_Relation(
                this, goes_in_inventory, is_one_shot);
        hasBeenActivated = false;
    }

    /**
     * Returns true if passable, false if not.
     */
    @Override
    public boolean isPassable() {
        return this.is_passable_;
    }

    /**
     * Checks if item can go in Inventory.
     * @return true if item can be put into inventory, false if not.
     */
    public boolean goesInInventory() {
        return this.getMapRelation().goesInInventory();
    }

    /**
     * Checks if item is one shot.
     * @return true if item is one shot, false if not.
     */
    public boolean isOneShot() {
        return this.getMapRelation().isOneShot();
    }

    /**
     * P
     */
    public void onWalkOver() {
        //System.out.println("Item: " + this.toString() + " is being walked on.");
        if (this.isOneShot() && !this.goesInInventory()) {
            this.getMapRelation().getMapTile().removeTopItem();
        }
        // Display.setMessage("Walked on Item: " + this.toString(), 3);
        // this.getMapRelation().hurtWithinRadius(10, 2);
    }

    /**
     * The use function allows an item to exert its effect on an entity.
     *
     * @param target - The entity that the item will be used on.
     */
    public void use(Entity target) {
        //System.out.println("Item: " + this.toString() + " is being used by entity + " + target.toString());
    	hasBeenActivated = true;
        Display.setMessage("Used Item: " + this.toString() + " Health: " + target.getStatsPack().current_life_
                + "Level: " + target.getStatsPack().cached_current_level_, 3);
    }

    /**
     * The use function also allows an item to exert an effect on another item.
     *
     * @param target - The item that this item will be used upon.
     */
    public void use(Item target) {

    }

    public boolean determineIfCanPass(Entity entity) {
        if (this.is_passable_) {
            return false;
        } else {
            return true;
        }
    }

    public String toString() {
        String s = "Item name: " + name_;
        s += "\n is_passable_: " + is_passable_;

        s += "\n map_relationship_: ";
        if (map_relationship_ == null) {
            s += "null";
        } else {
            s += "Not null";
        }

        s += "\n associated with map: " + map_relationship_.isAssociatedWithMap();

        return s;
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    // </editor-fold>
}
