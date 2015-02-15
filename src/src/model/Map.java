package src.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.io.Serializable;

import src.SaveData;
import src.controller.Entity;
import src.controller.Item;
import src.controller.Avatar;
import src.controller.Terrain;

/**
 * The map contains the map.\ THIS CLASS SHOULD NOT BE PUBLIC JUST BECAUSE - IT IS FUCKING PACKAGE PRIVATE I MADE IT PUBLIC TO TEST SOMETHING.
 * SENDCOMMANDTOAVATAR IS STUPID - NO ITS NOT STUPID IT REPRESENTS A NETWORK CONNECTION PASSING COMMANDS FROM USER TO MAP
 * WHAT HAVE YOU DONE!!!!!! THAT MAP IS SUPPOSED TO BE A PACKAGE PRIVATE ENTITY ONLY ACCESSIBLE VIA RELATIONS
 * YOU ARE BREAKING ENCAPSULATION!!!!!!!!!!!!!!!
 * @author John-Michael Reed
 */
class Map {

    /**
     * @author John-Michael Reed Sends a key press from a keyboard to an avatar
     * whose name is name. THIS FUNCTION SHOULD ONLY BE ACCESSIBLE VIA A
     * MAP_KEYBOARD_RELATION
     * @param name - Name of avatar to command
     * @param command - signal to send to avatar
     * @return zero if avatar accepts the command, non-zero if they do not
     */
    public int sendCommandToAvatarByName(String name, char command) {
        Avatar to_recieve_command = this.getAvatarByName(name);
        int error_code = to_recieve_command.acceptKeyCommand(command);
        return error_code;
    }

    // The map has a clock
    private int time_measured_in_turns;

    /* MAP DATA OBJECTS */
    // 2d array of tiles.
    private MapTile map_grid_[][];
    // String is the avatar's name. The avatar name must be unqiue or else bugs will occur.
    private LinkedHashMap<String, Avatar> avatar_list_;
    // String is the entity's name. The entity name must be unqiue or else bugs will occur.
    private LinkedHashMap<String, Entity> entity_list_;
    // Item is the address of an item in memory. Location is its xy coordinates on the grid.
    private LinkedList<Item> items_list_;

    //public static boolean NDEBUG_ = true;
    // MAP MUST BE SQUARE
    public int height_;
    public int width_;

    // This should never get called
    private Map() {//throws Exception {
        height_ = 0;
        width_ = 0; /*
         Exception e = new Exception("Do not use this constructor");
         throw e;*/

    }

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
        items_list_ = new LinkedList<Item>();
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

    public int addEntity(Entity e, int x, int y) {
        int error_code = this.map_grid_[y][x].addEntity(e);
        if (error_code == 0) {
            this.entity_list_.put(e.name_, e);
            e.getMapRelation().associateWithMap(this);
        }
        return error_code;

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
        if (error_code == 0) {
            t.getMapRelation().associateWithMap(this);
            t.getMapRelation().setMapTile(this.map_grid_[y][x]);
        }
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
        if (this.map_grid_[a.getMapRelation().getMyXCoordinate()][a.getMapRelation().getMyYCoordinate()].getEntity() == a) {
            this.map_grid_[a.getMapRelation().getMyXCoordinate()][a.getMapRelation().getMyYCoordinate()].removeEntity();
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
        if (this.map_grid_[e.getMapRelation().getMyXCoordinate()][e.getMapRelation().getMyYCoordinate()].getEntity() == e) {
            this.map_grid_[e.getMapRelation().getMyXCoordinate()][e.getMapRelation().getMyYCoordinate()].removeEntity();
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

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
/*
    private void readObjectData(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        /*
         try {
         // Map Tile Grid
         int w = in.readInt();
         int h = in.readInt();
         map_grid_ = new MapTile[w][h];
         for (int j = 0; j < h; j++) {
         for (int i = 0; i < w; i++) {
         map_grid_[i][j] = (MapTile)in.readObject();
         }
         }
         // Update local map height and width
         map_width_ = w;
         map_height_ = h;
                
         // Avatar List
         avatar_list_ = (LinkedHashMap<String, Avatar>)in.readObject();
            
         // Entity List
         entity_list_ = (LinkedHashMap<String, Entity>)in.readObject();
            
         // Item List
         items_list_ = (LinkedList<Item>)in.readObject();
         }
         catch (IOException e) {
         throw e;
         }
    }

    public void loadFromStream(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        time_measured_in_turns = ois.readInt();
        width_ = ois.readInt();
        height_ = ois.readInt();

        int count = ois.readInt();
        Avatar a;
        for (int i = 0; i < count; i++) {
            a = Avatar.load(ois);
            addAvatar(a, ois.readInt(), ois.readInt());
        }

        count = readInt();
    }

    private void relate(SaveData actor, MapDrawableThing_Relation mdtr) throws ClassNotFoundException {
        if (actor instanceof Avatar) {
            ((Avatar) actor).setDTRelation(mdtr);
        }
    }

    public void saveToStream(ObjectOutputStream oos) throws IOException {
        oos.writeInt(time_measured_in_turns);
        oos.writeInt(width_);
        oos.writeInt(height_);

        // ENTITIES
        oos.writeInt(avatar_list_.size());
        for (java.util.Map.Entry<String, Avatar> e : avatar_list_.entrySet()) {
            Avatar.save(e.getValue(), oos);
            oos.writeInt(e.getValue().getMapRelation().getMyXCoordinate());
            oos.writeInt(e.getValue().getMapRelation().getMyYCoordinate());
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        /*
         if (NDEBUG_)
         throw new java.io.IOException("Will not save map with \"DEBUG_\" enabled");
         try {
         // MAP GRID
         if (map_grid_ == null) throw new java.io.IOException("Map grid not initialized");
         out.writeInt(map_width_);
         out.writeInt(map_height_);
         for (int j = 0; j < map_height_; j++) {
         for (int i = 0; i < map_width_; i++) {
         out.writeObject(map_grid_[i][j]);
         }
         }
         // AVATAR LIST
         if (avatar_list_ == null) throw new java.io.IOException("Avatar list not initialized");
         out.writeObject(avatar_list_);
         // ENTITY LIST
         if (entity_list_ == null) throw new java.io.IOException("Entity list not initialized");
         out.writeObject(entity_list_);
         // ITEMS LIST
         if (items_list_ == null) throw new java.io.IOException("Items list not initialized");
         out.writeObject(items_list_);
         }
         catch (IOException e) {
         throw e;
         }

    }*/
    // </editor-fold>
}
