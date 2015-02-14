/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.model.MapItem_Relation;

/**
 * Class item represents a stackable entity [Alex's definition of entity] that
 * cannot move itself.
 *
 * @author JohnReedLOL
 */
public class Item extends DrawableThing {

    // Converts a class name [which must be unique] into a unique base 35 number

    private static final long serialVersionUID = Long.parseLong("Item", 35);

    // map_relationship_ is used in place of a map_referance_
    private final MapItem_Relation map_relationship_;
    
    /**
     * Use this to call functions contained within the MapItem relationship
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapItem_Relation getMapRelation() {
        return map_relationship_;
    }

    private boolean is_passable_;

    public Item(String name, char representation, boolean is_passable,
            boolean goes_in_inventory, boolean is_one_shot) {
        super(name, representation);
        is_passable_ = is_passable;
        map_relationship_ = new MapItem_Relation(
                this, goes_in_inventory, is_one_shot);
    }
    
    public boolean isPassable() {
        return this.is_passable_;
    }

    public void onWalkOver() {
    	
    }

    /**
     * The use function allows an item to exert its effect on an entity.
     *
     * @param target - The entity that the item will be used on.
     */
    public void use(Entity target) {
    	
    }

    /**
     * The use function also allows an item to exert an effect on another item.
     *
     * @param target - The item that this item will be used upon.
     */
    public void use(Item target) {

    }

    boolean determineIfCanPass(Entity entity) {
        if (this.is_passable_) {
            return false;
        } else {
            return true;
        }
    }
    
    public String toString(){
    	String s = "Item name: " + name_;
    	s += "\n is_passable_: " + is_passable_;
    	
    	s+="\n map_relationship_: ";
    	if(map_relationship_ == null)
    		s += "null";
    	else 
    		s += "Not null" ;
    	
   		s += "\n associated with map: " + map_relationship_.isAssociatedWithMap();

    	return s;
    }
}
