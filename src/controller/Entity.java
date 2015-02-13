/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

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
    }

    private Occupation occupation_ = null;

    Item inventory_[];

    // Only 1 equipped item in iteration 1
    Item equipped_item_;

    //private final int max_level_;

    private StatsPack my_stats_after_powerups_;

    private void recalculateStats() {
        //my_stats_after_powerups_.equals(my_stats_after_powerups_.add(equipped_item_.get_stats_pack_()));
    }

    public void levelUp() {

    }
    
    public void setOccupation(Occupation occupation) {
        occupation_ = occupation;
    }
    
    public Occupation getOccupation(){
        return occupation_;
    }
    
    public void addToInventory(Item item){
        
    }
}
