/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map;

import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;

/**
 *
 * @author JohnReedLOL
 */
public class MapItem_Relation extends MapDrawableThing_Relation {

    private final boolean is_one_shot_;

    public boolean isOneShot() {
        return is_one_shot_;
    }

    private final boolean is_passable_;

    public boolean isPassable() {
        return is_passable_;
    }

    private final Item item_;

    public MapItem_Relation(Map m, Item item,
            boolean is_passable, boolean is_one_shot) {
        super(m);
        item_ = item;
        is_passable_ = is_passable;
        is_one_shot_ = is_one_shot;
    }

    public Entity getTheEntityOnTopOfMe() {
        return this.getMapTile().getEntity();
    }

    /**
     * @author John-Michael Reed
     * @param x - x coordinate of tele-port
     * @param y - y coordinate of tele-port
     * @return -1 if an entity is already there, -2 if tele-port location is
     * invalid, -4 if destination is impassable
     */
    public int teleportTo(int new_x, int new_y) {
        MapTile destination = current_map_reference_.getTile(new_x, new_y);
        if (destination == null) {
            return -2;
        } else {
            int old_x = this.getMyXCoordinate();
            int old_y = this.getMyYCoordinate();
            current_map_reference_.getTile(old_x, old_y).removeSpecificItem(item_);
            if (destination.isPassable() == false) { // put the entity back in its place
                current_map_reference_.getTile(old_x, old_y).addItem(item_);
                return -4;
            } else { // move the entity
                int error_code = destination.addItem(item_);
                Item landed_on_item = destination.viewTopItem();
                if (landed_on_item != null) { // make the item walked on do stuff
                    landed_on_item.onWalkOver();
                }
                return error_code;
            }
        }
    }
}
