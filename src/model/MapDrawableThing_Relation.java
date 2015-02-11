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
    
    public void pushEntityInDirection(Entity entity, int x, int y) {

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
