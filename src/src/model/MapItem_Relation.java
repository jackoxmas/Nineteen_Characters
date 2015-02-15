/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.SaveData;
import src.controller.Item;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author JohnReedLOL
 */
public class MapItem_Relation extends MapDrawableThing_Relation implements SaveData {

    private final Item item_;

    public MapItem_Relation(Item item,
            boolean goes_in_inventory, boolean is_one_shot) {
        item_ = item;
        goes_in_inventory_ = goes_in_inventory;
        is_one_shot_ = is_one_shot;
    }
    private final boolean goes_in_inventory_;
    private final boolean is_one_shot_;
    public boolean goesInInventory() {
        return goes_in_inventory_;
    }
    public boolean isOneShot() {
        return is_one_shot_;
    }
    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    @Override
    public String getSerTag() {
        return "RELATION_MAP_ITEM";
    }
    // </editor-fold>
}
