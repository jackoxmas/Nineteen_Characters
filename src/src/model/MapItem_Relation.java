/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.EntityThing.Item;

import java.io.Serializable;

/**
 *
 * @author JohnReedLOL
 */
public class MapItem_Relation extends MapDrawableThing_Relation implements Serializable {

    private final Item item_;

    public MapItem_Relation(Map m, Item item,
            boolean is_passable, boolean is_one_shot) {
        super(m);
        item_ = item;
        is_passable_ = is_passable;
        is_one_shot_ = is_one_shot;
    }
    
    private final boolean is_passable_;
    private final boolean is_one_shot_;
    
    public boolean isPassable() {
        return is_one_shot_;
    }
    public boolean isOneShot() {
        return is_one_shot_;
    }
    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = 53142515L; // Long.parseLong("re_mi", 35);
    // </editor-fold>
}
