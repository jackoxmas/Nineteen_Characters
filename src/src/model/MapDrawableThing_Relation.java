/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.AreaFunctor;
import src.entityThings.Entity;
import src.entityThings.EntityStatsPack;
import src.entityThings.Item;

/**
 * This should be abstract because you can't make drawable things
 * @author JohnMichaelReed
 */
public class MapDrawableThing_Relation {

    protected final Map current_map_reference_;
    public MapDrawableThing_Relation(Map m) {
        current_map_reference_ = m;
    }
    private MapTile my_tile_ = null;

    private void initguardMap() {
        if (current_map_reference_ == null) {
            System.err.println("Empty map reference, "
                    + "and attempted to access map. Perhaps avatar was never passed a map, or mapview was never passed a map");
        }
    }

    private void initguardTile() {
        if (current_map_reference_ == null) {
            System.err.println("Empty tile reference, "
                    + "and attempted to access map. Perhaps avatar was never passed a map, or mapview was never passed a map");
        }
    }

    /**
     * 
     * @return x coordinate of tile drawable thing (avatar/entity/item/etc.) is on.
     */
    public int getMyXCoordinate() {
        initguardTile();
        return my_tile_.x_;
    }

    /**
     * 
     * @return y coordinate of tile drawable thing (avatar/entity/item/etc.) is on.
     */
    public int getMyYCoordinate() {
        initguardTile();
        return my_tile_.y_;
    }

    /**
     * 
     * @return MapTile associated with drawable thing (avatar/entity/item/etc.).
     */
    public MapTile getMapTile() {
        return my_tile_;
    }

    /**
     * Set MapTile that drawable thing (avatar/entity/item/etc.) is on.
     * @param new_tile 
     */
    public void setMapTile(MapTile new_tile) {
        my_tile_ = new_tile;
    }
    
    /**
     * Get map reference
     */
    public Map getMap() {
    	return current_map_reference_;
    }

    /**
     * Moves an entity without removing it from the list of entities
     *
     * @param: entity The entity to be moved
     * @param: x - distance to push in the x direction
     * @param: y - distance to push in the y direction
     * @return error codes: -1 if tile is taken, -2 if entity is null, -3 if
     * entity cannot be found, -4 if tile is off the map
     * @author John-Michael Reed
     */
    public int pushEntityInDirection(Entity e, int delta_x, int delta_y) {
        if (e == null) {
            return -2;
        }
        int old_x = e.getMapRelation().getMyXCoordinate();
        int old_y = e.getMapRelation().getMyYCoordinate();
        Entity toMove = current_map_reference_.getTile(old_x, old_y).getEntity();
        if (toMove == e) {
            current_map_reference_.getTile(old_x, old_y).removeEntity();
            MapTile move_tile = current_map_reference_.getTile(old_x + delta_x, old_y + delta_y);
            if (move_tile == null || move_tile.isPassable() == false) { // put the entity back in its place
                current_map_reference_.getTile(old_x, old_y).addEntity(e);
                return -4;
            } else { // move the entity
                int error_code = move_tile.addEntity(e);
                Item walked_on_item = move_tile.viewTopItem();
                if (walked_on_item != null) { // make the item walked on do stuff
                    walked_on_item.onWalkOver();
                }
                return error_code;
            }
        } else {
            return -3;
        }
    }

    public final class AreaDamager extends AreaFunctor {

        @Override
        public void repeat(int x_pos, int y_pos, int strength) {
            MapTile infliction = current_map_reference_.getTile(x_pos, y_pos);
            if (infliction != null) {
                // If there is no decal, fuck shit up
                if (infliction.getTerrain() != null && !infliction.getTerrain().hasDecal()) {
                    infliction.getTerrain().addDecal('♨');
                }
                Entity to_hurt = infliction.getEntity();
                if (to_hurt != null) {
                    EntityStatsPack s = to_hurt.getStatsPack();
                    // s.current_life_ -= strength;
                    to_hurt.receiveAttack(strength, null); // kills avatar if health is negative
                    src.userIO.Display.setMessage("Current Life after: " + s.current_life_, 3);
                }
            }
        }
    };

    public final class AreaHealer extends AreaFunctor {

        @Override
        public void repeat(int x_pos, int y_pos, int strength) {
            MapTile infliction = current_map_reference_.getTile(x_pos, y_pos);
            if (infliction != null) {
                // If there is no decal, fuck shit up
                if (infliction.getTerrain() != null && !infliction.getTerrain().hasDecal()) {
                    infliction.getTerrain().addDecal('♥');
                }
                Entity to_heal = infliction.getEntity();
                if (to_heal != null) {
                    EntityStatsPack s = to_heal.getStatsPack();
                    s.current_life_ += strength;
                    src.userIO.Display.setMessage("Current Life after: " + s.current_life_, 3);
                }
            }
        }
    };

    public final class AreaKiller extends AreaFunctor {

        /**
         * Used to repeatedly apply exorbitant damage on a tile
         * @param x_pos
         * @param y_pos
         * @param num_kills This parameter is not currently used
         */
        @Override
        public void repeat(int x_pos, int y_pos, int num_kills) {
            MapTile infliction = current_map_reference_.getTile(x_pos, y_pos);
            if (infliction != null) {
                // If there is no decal, fuck shit up
                if (infliction.getTerrain() != null && !infliction.getTerrain().hasDecal()) {
                    infliction.getTerrain().addDecal('☣');
                }
                Entity to_kill = infliction.getEntity();
                if (to_kill != null) {
                    EntityStatsPack s = to_kill.getStatsPack();
                    to_kill.commitSuicide();
                    src.userIO.Display.setMessage("Lives left after: " + s.lives_left_, 3);
                }
            }
        }
    }

    public final class AreaLeveler extends AreaFunctor {

        /**
         * Used to repeatedly apply level up on a tile
         *
         * @param x_pos x position to effect
         * @param y_pos y position to effect
         * @param num_level_ups - number of levels to up
         */
        @Override
        public void repeat(int x_pos, int y_pos, int num_level_ups) {
            MapTile infliction = current_map_reference_.getTile(x_pos, y_pos);
            if (infliction != null) {
                // If there is no decal, fuck shit up
                if (infliction.getTerrain() != null && !infliction.getTerrain().hasDecal()) {
                    infliction.getTerrain().addDecal('↑');
                }
                Entity to_level = infliction.getEntity();
                if (to_level != null) {
                    EntityStatsPack s = to_level.getStatsPack();
                    for (int i = 0; i < num_level_ups; ++i) {
                        to_level.gainEnoughExperienceTolevelUp();
                    }
                    src.userIO.Display.setMessage("Current Level after: " + s.cached_current_level_, 3);
                }
            }
        }
    }

    private final AreaDamager areaHurtFunctor = new AreaDamager();
    private final AreaHealer areaHealFunctor = new AreaHealer();
    private final AreaKiller areaKillFunctor = new AreaKiller();
    private final AreaLeveler areaLevelFunctor = new AreaLeveler();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone(); //To change body of generated methods, choose Tools | Templates.
    }

    //area effects
    public void hurtWithinRadius(int damage, int radius) {
        AreaDamager a = new AreaDamager();
        a.effectArea(this.getMyXCoordinate(), this.getMyYCoordinate(), radius, damage);
    }

    public void healWithinRadius(int heal_quantity, int radius) {
        AreaHealer a = new AreaHealer();
        a.effectArea(this.getMyXCoordinate(), this.getMyYCoordinate(), radius, heal_quantity);
    }

    public void killWithinRadius(/*boolean will_kill_players, boolean will_kill_npcs, */int radius) {
        AreaKiller a = new AreaKiller();
        a.effectArea(this.getMyXCoordinate(), this.getMyYCoordinate(), radius, 1);
    }

    public void levelUpWithinRadius(/*boolean will_level_up_players, boolean will_level_up_npcs, */int radius) {
        AreaLeveler a = new AreaLeveler();
        a.effectArea(this.getMyXCoordinate(), this.getMyYCoordinate(), radius, 1);
    }

    public boolean isAssociatedWithMap() {
        if (current_map_reference_ == null) {
            return false;
        } else {
            return true;
        }
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    // </editor-fold>
}
