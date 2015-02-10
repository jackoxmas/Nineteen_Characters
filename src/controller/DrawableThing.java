package src.controller;

import java.io.Serializable;
import src.model.MapDrawableThing_Association;
import src.model.MapTile;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JohnReedLOL
 */
abstract public class DrawableThing implements Serializable {

    // Converts the class name into a base 35 number. "DrawableThing" too long
    private static final long serialVersionUID = Long.parseLong("Drawable", 35);

    // names of items and terrain should be non-unique.
    // names of entities should be unique to fit in a hashmap.
    public final String name_;

    // For things that take up only  1 tile or need to appear on a minimap
    private final char single_character_representation_;
    
    private final MapDrawableThing_Association map_relationship_;
    
    public void setMapTile(MapTile map_tile) {
        map_relationship_.setMapTile(map_tile);
    }
    
    public MapTile getMapTile() {
        map_relationship_.getMapTile();
    }
    
    protected DrawableThing(String name, char representation) {
        name_ = name;
        single_character_representation_ = representation;
        is_viewable_ = true;
        map_relationship_ = new MapDrawableThing_Association(this);
    }
    
    private boolean is_viewable_;
    
    private StatsPack stats_pack_ = new StatsPack();
    
    public StatsPack get_stats_pack_() {
        return this.stats_pack_;
    }
    
    public void onTurn() {
        
    }
    
    public int getMyXCordinate() {
        return map_relationship_.getMyXCordinate();
    }

    public int getMyYCordinate() {
        return map_relationship_.getMyYCordinate();
    }

    //representation changes for terrain with/without decal
    public char getRepresentation() {
        return this.single_character_representation_;
    }
    
    public void setViewable(boolean is_viewable) {
        is_viewable_ = is_viewable;
    }
    
    public boolean getViewable() {
        return this.is_viewable_;
    }

    public void pushEntityInDirection(Entity entity, int x, int y) {
        map_relationship_.pushEntityInDirection(entity, x, y);
    }

    //area effects
    public void hurtWithinRadius(int damage, int radius) {
        map_relationship_.hurtWithinRadius(damage, radius);
    }
    
    public void healWithinRadius(int heal_quantity, int radius) {
        map_relationship_.healWithinRadius(heal_quantity, radius);
    }
    
    public void killWithinRadius(boolean will_kill_players, boolean will_kill_npcs, int radius) {
        map_relationship_.killWithinRadius(will_kill_players, will_kill_npcs, radius);
    }
    
    public void levelUpWithinRadius(boolean will_level_up_players, boolean will_level_up_npcs, int radius) {
        map_relationship_.levelUpWithinRadius(will_level_up_players, will_level_up_npcs, radius);
    }
    
}
