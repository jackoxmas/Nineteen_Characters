/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.io.Serializable;
import src.controller.Entity;
import src.controller.Item;
import src.controller.Avatar;
import src.controller.StatsPack;
import src.controller.Terrain;

/**
 * The map contains the map.
 *
 * @author John-Michael Reed
 */
final class Map implements Serializable {

    // Set this to false if not debugging.
    public static boolean NDEBUG_ = true;

    // MAP MUST BE SQUARE
    public static final int debug_map_height_ = 3;
    public static final int debug_map_width_ = 3;

    public static final int map_height_ = 10;
    public static final int map_width_ = 20;

    private Map() {
        if (NDEBUG_) {
            map_grid_ = new MapTile[debug_map_height_][debug_map_width_];
            for (int i = 0; i < debug_map_height_; ++i) {
                for (int j = 0; j < debug_map_width_; ++j) {
                    map_grid_[i][j] = new MapTile(j, i); //switch rows and columns
                }
            }
        } else {
            map_grid_ = new MapTile[map_height_][map_width_];
            for (int i = 0; i < map_height_; ++i) {
                for (int j = 0; j < map_width_; ++j) {
                    map_grid_[i][j] = new MapTile(j, i); //switch rows and columns
                }
            }
        }
        avatar_list_ = new LinkedHashMap();
        entity_list_ = new LinkedHashMap();
        time_measured_in_turns = 0;
    }

    // MapModel.map_model_ is static because there is only one map_model_  
    private static final Map the_map_ = new Map();

    public static MapTile[][] getMyReferanceToTheMapGrid(MapDisplay_Association m) {
        return Map.the_map_.map_grid_;
    }

    public static Map getMyReferanceToTheMap(MapDrawableThing_Association d) {
        return Map.the_map_;
    }

    public static Map getMyReferanceToTheMap(MapMain_Association m) {
        return Map.the_map_;
    }

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("MapModel", 35);

    // 2d array of tiles.
    private MapTile map_grid_[][];

    // String is the avatar's name. The avatar name must be unqiue or else bugs will occur.
    private LinkedHashMap<String, Avatar> avatar_list_;

    /**
     * Adds an avatar to the map
     *
     * @param a
     * @param x
     * @param y
     * @return -1 on fail, 0 on success
     */
    public int addAvatar(Avatar a, int x, int y) {
        int error_code = this.map_grid_[y][x].addEntity(a);
        if (error_code == 0) {
            this.avatar_list_.put(a.name_, a);
            return 0;
        } else {
            return error_code;
        }
    }

    public int addEntity(Entity e, int x, int y) {
        int error_code = this.map_grid_[y][x].addEntity(e);
        if (error_code == 0) {
            this.entity_list_.put(e.name_, e);
            return 0;
        } else {
            return error_code;
        }
    }

    public int addItem(Item i, int x, int y) {
        int error_code = this.map_grid_[y][x].addItem(i);
        return error_code;
    }
    public Item removeTopItem(Item i, int x, int y) {
        return this.map_grid_[y][x].removeTopItem();
    }

    /**
     * Once a tile has terrain, that terrain is constant.
     * @param t
     * @param x
     * @param y
     * @return error code 
     */
    public int initializeTerrain(Terrain t, int x, int y) {
        int error_code = this.map_grid_[y][x].initializeTerrain(t);
        return error_code;
    }

    /**
     * Returns -1 if the entity to be removed does not exist.
     *
     * @param a
     * @return
     */
    public int removeAvatar(Avatar a) {
        this.avatar_list_.remove(a.name_);
        if (this.map_grid_[a.getMyXCordinate()][a.getMyYCordinate()].getEntity() == a) {
            this.map_grid_[a.getMyXCordinate()][a.getMyYCordinate()].removeEntity();
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * Returns -1 if the entity to be removed does not exist.
     *
     * @param e
     * @return
     */
    public int removeEntity(Entity e) {
        this.avatar_list_.remove(e.name_);
        if (this.map_grid_[e.getMyXCordinate()][e.getMyYCordinate()].getEntity() == e) {
            this.map_grid_[e.getMyXCordinate()][e.getMyYCordinate()].removeEntity();
            return 0;
        }
        return -1;
    }

    /**
     *
     * @param item - item to be added
     * @param x - x position of item
     * @param y - y position of item
     * @return 0 if success, -1 if fail
     */
    public int addItemAtPosition(Item item, int x, int y) {
        int error_code = this.map_grid_[y][x].addItem(item);
        return error_code;
    }

    public Avatar getAvatarByName(String name) {
        return this.avatar_list_.get(name);
    }

    public Entity getEntityByName(String name) {
        return this.entity_list_.get(name);
    }

    public MapTile getTile(int x_pos, int y_pos) {
        return map_grid_[x_pos][y_pos];
    }
    // String is the entity's name. The entity name must be unqiue or else bugs will occur.
    private LinkedHashMap<String, Entity> entity_list_;

    // Item is the address of an item in memory. Location is its xy coordinates on the grid.
    private LinkedList<Item> items_list_;

    // The map has a clock
    private int time_measured_in_turns;

// Entity Area Effects
    /**
     * Used to modify current health, mana, defense within radius of an entity
     *
     * @param entity
     * @param can_effect_players
     * @param can_effect_npcs
     * @param modifiable_stats_pack
     * @param radius
     * @author John-Michael Reed
     */
    public void entityAreaModifyStatsPacks(boolean can_effect_players, boolean can_effect_npcs, StatsPack stats_pack, int radius) {

    }

    /**
     * Levels up npc's/players within radius of entity
     *
     * @param entity
     * @param will_level_up_players
     * @param will_level_up_npcs
     * @param radius
     * @author John-Michael Reed
     */
    public void entityAreaLevelUp(Entity entity, boolean will_level_up_players, boolean will_level_up_npcs, int radius) {

    }

    // Item Area Effects
    /**
     * Used to modify current health, mana, defense within radius of an item
     *
     * @param item
     * @param can_effect_players
     * @param can_effect_npcs
     * @param modifiable_stats_pack
     * @param radius
     * @author John-Michael Reed
     */
    public void itemAreaModifyStatsPacks(Item item, boolean can_effect_players, boolean can_effect_npcs, StatsPack stats_pack, int radius) {

    }

    /**
     * Levels up npc's/players within radius of an item
     *
     * @param item
     * @param will_level_up_players
     * @param will_level_up_npcs
     * @param radius
     * @author John-Michael Reed
     */
    public void entityAreaLevelUp(Item item, boolean will_level_up_players, boolean will_level_up_npcs, int radius) {

    }

    // Terrain Area Effects
    /**
     * Used to modify current health, mana, defense within radius of a terrain
     * tile.
     *
     * @param terrain
     * @param can_effect_players
     * @param can_effect_npcs
     * @param modifiable_stats_pack
     * @param radius
     * @author John-Michael Reed
     */
    public void terrainAreaModifyStatsPacks(Terrain terrain, boolean can_effect_players, boolean can_effect_npcs, StatsPack stats_pack, int radius) {

    }

    /**
     * Levels up npc's/players within radius of a terrain tile
     *
     * @param terrain
     * @param will_level_up_players
     * @param will_level_up_npcs
     * @param radius
     * @author John-Michael Reed
     */
    public void terrainAreaLevelUp(Terrain terrain, boolean will_level_up_players, boolean will_level_up_npcs, int radius) {

    }
}
