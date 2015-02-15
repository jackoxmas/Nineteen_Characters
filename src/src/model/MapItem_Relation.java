/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Item;
import java.io.Serializable;

/**
 *
 * @author JohnReedLOL
 */
public class MapItem_Relation extends MapDrawableThing_Relation implements Serializable {

    private final Item item_;

    public MapItem_Relation(Item item,
            boolean goes_in_inventory, boolean is_one_shot) {
        item_ = item;
        goes_in_inventory_ = goes_in_inventory;
        is_one_shot_ = is_one_shot;
    }
    private final boolean goes_in_inventory_;
    private final boolean is_one_shot_;

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("RELATION_MI", 35);
    // </editor-fold>
}
