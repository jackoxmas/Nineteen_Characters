package src.model.map;

import java.util.LinkedHashMap;
import java.util.LinkedList;

import src.model.map.constructs.Avatar;
import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;
import src.model.map.constructs.Terrain;
import src.model.*;
import src.model.map.constructs.MapViewable;

/**
 *
 * @author John-Michael Reed
 */
public class Map implements MapViewable {

    public static final int MAX_NUMBER_OF_WORLDS = 1;
    private static int number_of_worlds_generated_ = 0;

    /**
     * Once a tile has terrain, that terrain is constant.
     *
     * @param t - Terrain
     * @param x - x position for tile
     * @param y - y position for tile
     * @return error code
     */
    public int addTerrain(Terrain t, int x, int y) {
        t.setMapRelation(new MapTerrain_Relation(this, t));
        int error_code = this.map_grid_[y][x].addTerrain(t);
        if (error_code == 0) {
            t.getMapRelation().setMapTile(this.map_grid_[y][x]);
        } else {
            t.setMapRelation(null);
        }
        return error_code;
    }

    // String is the avatar's name. The avatar name must be unqiue or else bugs will occur.
    private transient LinkedHashMap<String, Avatar> avatar_list_;
    /**
     * Adds an avatar to the map.
     *
     * @param a - Avatar to be added
     * @param x - x position of where you want to add Avatar
     * @param y - y posiition of where you want to add Avatar
     * @return -1 on fail, 0 on success
     */
    public int addAvatar(Avatar a, int x, int y) {
        a.setMapRelation(new MapAvatar_Relation(this, a, x, y));
        int error_code = this.map_grid_[y][x].addEntity(a);
        if (error_code == 0) {
            this.avatar_list_.put(a.name_, a);
        } else {
            a.setMapRelation(null);
        }
        return error_code;
    }

    /**
     *
     * @param name - name of Avatar
     * @return Avatar with the name of of input.
     */
    public Avatar getAvatarByName(String name) {
        return this.avatar_list_.get(name);
    }
    
    /**
     * Removes and Avatar from map.
     *
     * @param a - Avatar to be removed.
     * @return -1 if the entity to be removed does not exist.
     */
    public int removeAvatar(Avatar a) {
        this.avatar_list_.remove(a.name_);
        if (this.map_grid_[a.getMapRelation().getMyXCoordinate()][a.getMapRelation().getMyYCoordinate()].getEntity() == a) {
            this.map_grid_[a.getMapRelation().getMyXCoordinate()][a.getMapRelation().getMyYCoordinate()].removeEntity();
            a.setMapRelation(null);
            return 0;
        } else {
            return -1;
        }
    }

    // String is the entity's name. The entity name must be unqiue or else bugs will occur.
    private transient LinkedHashMap<String, Entity> entity_list_;
    /**
     * Adds an entity to the map.
     *
     * @param e - Entity to be added
     * @param x - x position of where you want to add entity
     * @param y - y posiition of where you want to add entity
     * @return -1 on fail, 0 on success
     */
    public int addEntity(Entity e, int x, int y) {
        e.setMapRelation(new MapEntity_Relation(this, e, x, y));
        int error_code = this.map_grid_[y][x].addEntity(e);
        if (error_code == 0) {
            this.entity_list_.put(e.name_, e);
        } else {
            e.setMapRelation(null);
        }
        return error_code;
    }

    /**
     *
     * @param name - name of Entity
     * @return Entity with the name of of input.
     */
    public Entity getEntityByName(String name) {
        return this.entity_list_.get(name);
    }

    /**
     * Removes entity from map.
     *
     * @param e - entity to be removed
     * @return -1 if the entity to be removed does not exist.
     */
    public int removeEntity(Entity e) {
        this.avatar_list_.remove(e.name_);
        if (this.map_grid_[e.getMapRelation().getMyXCoordinate()][e.getMapRelation().getMyYCoordinate()].getEntity() == e) {
            this.map_grid_[e.getMapRelation().getMyXCoordinate()][e.getMapRelation().getMyYCoordinate()].removeEntity();
            e.setMapRelation(null);
            return 0;
        }
        return -1;
    }

    /**
     * Gets mapTile at (x,y).
     *
     * @param x_pos - x position of tile
     * @param y_pos - y position of tile
     * @return MapTile at (x,y), null if tile is outside the map.
     */
    public MapTile getTile(int x_pos, int y_pos) {
        if (x_pos < 0 || y_pos < 0 || x_pos >= map_grid_[0].length || y_pos >= map_grid_.length) {
            return null;
        }
        return map_grid_[y_pos][x_pos];
    }
    
   /**
     * Gets the character representation of a tile
     *
     * @author John-Michael Reed
     * @param x
     * @param y
     * @return error code: returns empty space if the tile is off the map
     */
    public char getTileRepresentation(int x, int y) {
        MapTile tile_at_x_y = this.getTile(x, y);
        if (tile_at_x_y == null) {
            return ' ';
        } else {
            return tile_at_x_y.getTopCharacter();
        }
    }

    // Item is the address of an item in memory. Location is its xy coordinates on the grid.
    private transient LinkedList<Item> items_list_;
    /**
     * Adds an item to the map.
     *
     * @param i - Item to be added
     * @param x - x position of where you want to add item
     * @param y - y posiition of where you want to add item
     * @return -1 on fail, 0 on success
     */
    public int addItem(Item i, int x, int y, boolean goes_in_inventory, boolean is_one_shot) {
        i.setMapRelation(new MapItem_Relation(this, i, goes_in_inventory, is_one_shot));
        int error_code = this.map_grid_[y][x].addItem(i);
        if (error_code == 0) {
            items_list_.add(i);
        } else {
            i.setMapRelation(null);
        }
        return error_code;
    }

    public LinkedList<Item> getItemsList() {
        return items_list_;
    }

    /**
     * Removes top item from tile in position (x,y).
     *
     * @param x - x position of tile
     * @param y - y position of tile
     * @return Top item from tile (x,y)
     */
    public Item removeTopItem(int x, int y) {
        Item item = this.map_grid_[y][x].removeTopItem();
        items_list_.remove(item);
        return item;
    }
    
  //public static boolean NDEBUG_ = true;
    // MAP MUST BE SQUARE
    //TODO:if Map has to be square, why have two different variables that will always be equivalent?
    public int height_;
    public int width_;

    // This should never get called
    private Map() {//throws Exception {
        height_ = 0;
        width_ = 0;
        System.exit(-1);
        /*
         Exception e = new Exception("Do not use this constructor");
         throw e;*/

    }

    /**
     * Map Constructor, creates new x by y Map.
     *
     * @param x - Length of Map
     * @param y - Height of Map
     */
    public Map(int x, int y) {
        if (number_of_worlds_generated_ >= MAX_NUMBER_OF_WORLDS) {
            System.err.println("Number of world allowed: "
                    + MAX_NUMBER_OF_WORLDS);
            System.err.println("Number of worlds already in existence: "
                    + number_of_worlds_generated_);
            System.err.println("Please don't make more than "
                    + MAX_NUMBER_OF_WORLDS + " worlds.");
            System.exit(-1);

        } else {
            ++number_of_worlds_generated_;

            height_ = y;
            width_ = x;

            map_grid_ = new MapTile[height_][width_];
            for (int i = 0; i < height_; ++i) {
                for (int j = 0; j < width_; ++j) {
                    map_grid_[i][j] = new MapTile(j, i); //switch rows and columns
                }
            }
            avatar_list_ = new LinkedHashMap();
            entity_list_ = new LinkedHashMap();
            items_list_ = new LinkedList<Item>();
            time_measured_in_turns = 0;
        }
    }

    // 2d array of tiles.
    private transient MapTile map_grid_[][];

    public MapTile[][] getMapGrid() {
        return map_grid_;
    }

    /**
     * @author John-Michael Reed Sends a key press from a keyboard to an avatar
     * whose name is name. THIS FUNCTION SHOULD ONLY BE ACCESSIBLE VIA A
     * MAP_KEYBOARD_RELATION
     * @param name - Name of avatar to command
     * @param command - signal to send to avatar
     * @return IO_Package of stuff that can be displayed.
     */
    public IO_Package sendCommandToAvatarByName(String name, char command) {
        Avatar to_recieve_command = this.getAvatarByName(name);
        int error_code = to_recieve_command.acceptKeyCommand(command);
        IO_Package return_package = null;
        return return_package;
    }
    
    // The map has a clock
    private int time_measured_in_turns;
}
