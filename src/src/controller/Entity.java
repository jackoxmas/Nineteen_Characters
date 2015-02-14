/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import java.util.ArrayList;
import src.model.MapEntity_Relation;

import java.io.Serializable;

/**
 *
 * @author JohnReedLOL
 */
abstract public class Entity extends DrawableThing implements Serializable {

    // Converts an entity's name [which must be unique] into a unique base 35 number
    private static final long serialVersionUID = Long.parseLong("ENTITY", 35);

    // map_relationship_ is used in place of a map_referance_
    private final MapEntity_Relation map_relationship_;
    

    /**
     * Use this to call functions contained within the MapEntity relationship
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapEntity_Relation getMapRelation() {
        return map_relationship_;
    }
    
    public Entity(String name, char representation, 
            int x_respawn_point, int y_respawn_point) {
        super(name, representation);
        map_relationship_ = new MapEntity_Relation( this, x_respawn_point, y_respawn_point );
        inventory_ = new ArrayList<Item>();
    }

    private Occupation occupation_ = null;

    ArrayList<Item> inventory_;

    // Only 1 equipped item in iteration 1
    Item equipped_item_;

    //private final int max_level_;

    private StatsPack my_stats_after_powerups_;

    /**
     * Adds default stats to item stats and updates my_stats_after_powerups
     * @author Jessan
     */
    private void recalculateStats() {
        //my_stats_after_powerups_.equals(my_stats_after_powerups_.add(equipped_item_.get_stats_pack_()));
        my_stats_after_powerups_ = get_default_stats_pack_().add(equipped_item_.get_default_stats_pack_());
    }

    /**
     * this function levels up an entity
     * @author Jessan
     */
    public void levelUp() {
        if(occupation_ == null){
           //levelup normally
            StatsPack new_stats = new StatsPack(0,1,1,1,1,1,1,1,1);
            set_default_stats_pack(get_default_stats_pack_().add(new_stats));
        }
        //if occupation is not null/have an occupation
        else {
            set_default_stats_pack(occupation_.change_stats(get_default_stats_pack_()));
        }

    }
    
    public void setOccupation(Occupation occupation) {
        occupation_ = occupation;
    }
    
    public Occupation getOccupation(){
        return occupation_;
    }
    
    public void addItemToInventory(Item item) {
        inventory_.add(item);
    }
}
