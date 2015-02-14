/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.AreaFunctor;
import src.controller.DrawableThing;
import src.controller.Entity;
import src.controller.StatsPack;

import java.io.Serializable;

/**
 *
 * @author JohnMichaelReed
 */
public class MapDrawableThing_Relation implements Serializable {
    private static final long serialVersionUID = Long.parseLong("RELATIONMD", 35);

    protected Map current_map_reference_ = null;
    private MapTile my_tile_ = null;
    private final DrawableThing drawable_thing_;

    public MapDrawableThing_Relation(DrawableThing drawable_thing) {
        drawable_thing_ = drawable_thing;
    }

    public int getMyXCordinate() {
        return my_tile_.x_;
    }

    /**
     * This function must be called to associate a map_relation with a map.
     */
    public void associateWithMap(Map m) {
        current_map_reference_ = m;
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
     *
     * @param entity The entity to be moved
     * @param x - distance to push in the x direction
     * @param y - distance to push in the y direction
     * @return error codes: -1 if tile is taken, -2 if entity is null, -3 if
     * entity cannot be found, -4 if tile is off the map
     * @author John-Michael Reed
     */
    public int pushEntityInDirection(Entity e, int delta_x, int delta_y) {
        if (e == null) {
            return -2;
        }
        int old_x = e.getMapRelation().getMyXCordinate();
        int old_y = e.getMapRelation().getMyYCordinate();
        Entity toMove = current_map_reference_.getTile(old_x, old_y).getEntity();
        if (toMove == e) {
            current_map_reference_.getTile(old_x, old_y).removeEntity();
            MapTile move_tile = current_map_reference_.getTile(old_x + delta_x, old_y + delta_y);
            if (move_tile == null) { // put the entity back in its place
                current_map_reference_.getTile(old_x, old_y).addEntity(e);
                return -4;
            } else { // move the entity
                return move_tile.addEntity(e);
            }
        } else {
            return -3;
        }
    }
    private class areaDamager extends AreaFunctor {

        @Override
        public void repeat(int x_pos, int y_pos) {
            MapTile infliction = current_map_reference_.getTile(x_pos, y_pos);
            if (infliction != null) {
                Entity to_hurt = infliction.getEntity();
                if (to_hurt != null) {
                    //StatsPack s = to_hurt.get_stats_pack_();
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    };
    
    //area effects
    public void hurtWithinRadius(int damage, int radius) {
        areaDamager a = new areaDamager();
        
}

public void healWithinRadius(int heal_quantity, int radius) {

    }

    public void killWithinRadius(boolean will_kill_players, boolean will_kill_npcs, int radius) {

    }

    public void levelUpWithinRadius(boolean will_level_up_players, boolean will_level_up_npcs, int radius) {

    }
}
