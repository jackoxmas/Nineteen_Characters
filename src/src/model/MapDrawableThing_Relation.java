/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.DrawableThing;
import src.controller.Entity;

/**
 *
 * @author JohnMichaelReed
 */
public class MapDrawableThing_Relation {

    protected final Map map_reference_ = Map.getMyReferanceToTheMap(this);
    private MapTile my_tile_ = null;
    private final DrawableThing drawable_thing_;

    public MapDrawableThing_Relation(DrawableThing drawable_thing) {
        drawable_thing_ = drawable_thing;
    }

    public int getMyXCordinate() {
        return my_tile_.x_;
    }

    public int getMyYCordinate() {
        return my_tile_.y_;
    }

    public MapTile getMapTile() {
        return my_tile_;
    }

    public void setMapTile(MapTile new_tile) {
        my_tile_ = new_tile;
    }
    
    /**
     * Moves an entity without removing it from the list of entities
     * @param entity The entity to be moved
     * @param x - distance to push in the x direction
     * @param y  - distance to push in the y direction
     * @return error codes: -1 if tile is taken, -2 if entity is null, -3 if entity cannot be found
     * @author John-Michael Reed
     */
    public int pushEntityInDirection(Entity e, int delta_x, int delta_y) {
        if(e == null) {
            return -2;
        }
        int old_x = e.getMapRelation().getMyXCordinate();
        int old_y = e.getMapRelation().getMyYCordinate();
        Entity toMove = map_reference_.getTile(old_x, old_y).getEntity();
        if (toMove == e) {
            map_reference_.getTile(old_x, old_y).removeEntity();
            return map_reference_.getTile(old_x + delta_x, old_y + delta_y).addEntity(e);
        }
        return -3;
    }
    
    //area effects
    public void hurtWithinRadius(int damage, int radius) {

    }

    public void healWithinRadius(int heal_quantity, int radius) {

    }

    public void killWithinRadius(boolean will_kill_players, boolean will_kill_npcs, int radius) {

    }

    public void levelUpWithinRadius(boolean will_level_up_players, boolean will_level_up_npcs, int radius) {

    }
}
