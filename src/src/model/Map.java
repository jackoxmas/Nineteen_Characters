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
    //public static boolean NDEBUG_ = true;
    // MAP MUST BE SQUARE
    public final int height_;
    public final int width_;

    // This should never get called
    private Map() throws Exception {
        height_ = 0;
        width_ = 0;
        Exception e = new Exception("Do not use this constructor");
        throw e;
    }

    //public static final int map_height_ = 10;
    //public static final int map_width_ = 20;
    public Map(int x, int y) {
        //if (NDEBUG_) {
        height_ = y;
        width_ = x;

        map_grid_ = new MapTile[height_][width_];
        for (int i = 0; i < height_; ++i) {
            for (int j = 0; j < width_; ++j) {
                map_grid_[i][j] = new MapTile(j, i); //switch rows and columns
            }
        }
        /*} else {
         map_grid_ = new MapTile[map_height_][map_width_];
         for (int i = 0; i < map_height_; ++i) {
         for (int j = 0; j < map_width_; ++j) {
         map_grid_[i][j] = new MapTile(j, i); //switch rows and columns
         }
         }
         }
         */
        avatar_list_ = new LinkedHashMap();
        entity_list_ = new LinkedHashMap();
        time_measured_in_turns = 0;
    }
    
    private void initializeGrid(int x, int y) {
        
    }

    // MapModel.map_model_ is static because there is only one map_model_  
    //private static final Map the_map_ = new Map();

    /*
     public static Map getMyReferanceToTheMapGrid(MapDisplay_Relation m) {
     return Map.the_map_;
     }

     public static Map getMyReferanceToTheMap(MapDrawableThing_Relation d) {
     return Map.the_map_;
     }

     public static Map getMyReferanceToTheMap(MapMain_Relation m) {
     return Map.the_map_;
     }
     */
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
            a.getMapRelation().associateWithMap(this);
            return 0;
        } else {
            return error_code;
        }
    }

    /**
     * Adds an entity to the map at the defined (x,y) position
     * 
     * @param e
     * @param x
     * @param y
     * @return 0 on success, -1 on fail
     */
    public int addEntity(Entity e, int x, int y) {
        int error_code = this.map_grid_[y][x].addEntity(e);
        if (error_code == 0) {
            this.entity_list_.put(e.name_, e);
            e.getMapRelation().associateWithMap(this);
            return 0;
        } else {
            return error_code;
        }
    }

    public int addItem(Item i, int x, int y) {
        int error_code = this.map_grid_[y][x].addItem(i);
        if (error_code == 0) {
            i.getMapRelation().associateWithMap(this);
        }
        return error_code;
    }
    
    public Item removeTopItem(int x, int y) {
        return this.map_grid_[y][x].removeTopItem();
    }

    /**
     * Once a tile has terrain, that terrain is constant.
     *
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
        if (this.map_grid_[a.getMapRelation().getMyXCordinate()][a.getMapRelation().getMyYCordinate()].getEntity() == a) {
            this.map_grid_[a.getMapRelation().getMyXCordinate()][a.getMapRelation().getMyYCordinate()].removeEntity();
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
        if (this.map_grid_[e.getMapRelation().getMyXCordinate()][e.getMapRelation().getMyYCordinate()].getEntity() == e) {
            this.map_grid_[e.getMapRelation().getMyXCordinate()][e.getMapRelation().getMyYCordinate()].removeEntity();
            return 0;
        }
        return -1;
    }

    public Avatar getAvatarByName(String name) {
        return this.avatar_list_.get(name);
    }

    public Entity getEntityByName(String name) {
        return this.entity_list_.get(name);
    }

    /**
     * Returns null if tile is outside the map
     *
     * @param x_pos
     * @param y_pos
     * @return
     */
    public MapTile getTile(int x_pos, int y_pos) {
        if (x_pos < 0 || y_pos < 0 || x_pos >= map_grid_[0].length || y_pos >= map_grid_.length) {
            return null;
        }
        return map_grid_[y_pos][x_pos];
    }
    // String is the entity's name. The entity name must be unqiue or else bugs will occur.
    private LinkedHashMap<String, Entity> entity_list_;

    // Item is the address of an item in memory. Location is its xy coordinates on the grid.
    private LinkedList<Item> items_list_;

    // The map has a clock
    private int time_measured_in_turns;

}
