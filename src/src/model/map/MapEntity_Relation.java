/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map;

import src.Effect;
import src.FacingDirection;
import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;
import src.io.view.Display;

/**
 * One line description
 *
 * @author JohnReedLOL
 */
public class MapEntity_Relation extends MapDrawableThing_Relation {

    public class AreaEffect extends MapDrawableThing_Relation.AreaEffect {

        /**
         * Casts a 90 degree wide area effect
         *
         * @author Reed, John-Michael
         */
        public void effectAreaWithinArc(int length, int strength, Effect effect) {
            if(length < 0 || strength < 0) {
                System.exit(-1);
            }
            FacingDirection attack_direction = entity_.getFacingDirection();
            final int x_start = entity_.getMapRelation().getMyXCoordinate();
            final int y_start = entity_.getMapRelation().getMyYCoordinate();
            for (int i = 1; i <= length; ++i) {
                int reduction = 0;
                if (effect == Effect.HEAL || effect == Effect.HURT) {
                    reduction = i-1;
                }
                for (int width = -i + 1; width <= i - 1; ++width) {
                    switch (attack_direction) {
                        case UP:
                            repeat(x_start + width, y_start + i, strength - reduction, effect);
                            break;
                        case DOWN:
                            repeat(x_start + width, y_start - i, strength - reduction, effect);
                            break;
                        case RIGHT:
                            repeat(x_start + i, y_start + width, strength - reduction, effect);
                            break;
                        case LEFT:
                            repeat(x_start - i, y_start + width, strength - reduction, effect);
                            break;
                        case UP_RIGHT:
                            repeat(x_start + width + i, y_start - width + i, strength - reduction, effect);
                            break;
                        case UP_LEFT:
                            repeat(x_start - width - i, y_start - width + i, strength - reduction, effect);
                            break;
                        case DOWN_RIGHT:
                            repeat(x_start + width + i, y_start + width - i, strength - reduction, effect);
                            break;
                        case DOWN_LEFT:
                            repeat(x_start - width - i, y_start + width - i, strength - reduction, effect);
                            
                            break;
                    }

                }
            }
        }
        /**
         * Does area damage in a line
         *
         * @author Reed, John-Michael
         */
        public void effectAreaWithinLine(int length, int strength, Effect effect) {
            FacingDirection attack_direction = entity_.getFacingDirection();
            final int x_start = entity_.getMapRelation().getMyXCoordinate();
            final int y_start = entity_.getMapRelation().getMyYCoordinate();
            for (int i = 1; i <= length; ++i) {
                int reduction = 0;
                if (effect == Effect.HEAL || effect == Effect.HURT) {
                    reduction = i - 1;
                }
                switch (attack_direction) {
                    case UP:
                        repeat(x_start, y_start + i, strength - reduction, effect);
                        break;
                    case DOWN:
                        repeat(x_start, y_start - i, strength - reduction, effect);
                        break;
                    case RIGHT:
                        repeat(x_start + i, y_start, strength - reduction, effect);
                        break;
                    case LEFT:
                        repeat(x_start - i, y_start, strength - reduction, effect);
                        break;
                    case UP_RIGHT:
                        repeat(x_start + i, y_start + i, strength - reduction, effect);
                        break;
                    case UP_LEFT:
                        repeat(x_start - i, y_start + i, strength - reduction, effect);
                        break;
                    case DOWN_RIGHT:
                        repeat(x_start + i, y_start - i, strength - reduction, effect);
                        break;
                    case DOWN_LEFT:
                        repeat(x_start - i, y_start - i, strength - reduction, effect);
                        break;
                }
            }
        }
    };
    
    /**
     * This object is actually a function used to call area effects
     *
     * @author John-Michael Reed
     */
    public final AreaEffect areaEffectFunctor = new MapEntity_Relation.AreaEffect();

    /**
     * @author John-Michael Reed
     * @return -1 if no item can be dropped (inventory empty)
     */
    public int dropItem() {
        Item itemToBeDropped = entity_.pullLastItemOutOfInventory();
        if (itemToBeDropped != null) {
            current_map_reference_.addItem(itemToBeDropped, this.getMapTile().x_, this.getMapTile().y_,
                    itemToBeDropped.getMapRelation().isPassable(), itemToBeDropped.getMapRelation().isOneShot());
            Display.setMessage("Dropped item: " + itemToBeDropped.name_, 3);
            return 0;
        } else {
            Display.setMessage("You have no items to drop.", 3);
            return -1;
        }
    }

    private final Entity entity_;

    public MapEntity_Relation(Map m, Entity entity,
            int x_respawn_point, int y_respawn_point) {
        super(m);
        entity_ = entity;
        x_respawn_point_ = x_respawn_point;
        y_respawn_point_ = y_respawn_point;
    }

    /**
     * Moves the entity that this relation refers to over x and up y
     *
     * @param x x displacement
     * @param y y displacement
     * @return error codes: see function pushEntityInDirection() in
     * MapDrawableThing_Relation
     * @author John-Michael Reed
     */
    public int moveInDirection(int x, int y) {
        if (x == 0 && y == 0) {
            //nothing
        } else if (x == 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP);
        } else if (x == 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN);
        } else if (x > 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.RIGHT);
        } else if (x < 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.LEFT);
        } else if (x > 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_RIGHT);
        } else if (x > 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_RIGHT);
        } else if (x < 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_LEFT);
        } else if (x < 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_LEFT);
        }
        return super.pushEntityInDirection(entity_, x, y);
    }

    /**
     * An item underneath you can be picked up using the parameters 0,0. 0 if
     * item is picked up successfully, -1 if no item is on the specified tile.
     *
     * @author John-Michael Reed
     * @param x
     * @param y
     * @return error_code
     */
    public int pickUpItemInDirection(int x, int y) {
        int error_code = -1;

        Item itemToBePickedUp = current_map_reference_.removeTopItem(x + getMyXCoordinate(), y + getMyYCoordinate());
        if (itemToBePickedUp != null) {
            entity_.addItemToInventory(itemToBePickedUp);
            Display.setMessage("Picked up item: " + itemToBePickedUp.name_, 3);
            error_code = 0;
        } else {
            Display.setMessage("There is nothing here to pick up.", 3);
        }

        return error_code;
    }

    public void sendAttack(int x, int y) {

    }

    public void spawn(Entity toSpawn, int time_until_spawn) {
        //super.pushEntityInDirection(toSpawn, x_respawn_point_, y_respawn_point_);
    }

    /**
     * @author John-Michael Reed
     *
     * @param x - x coordinate of tele-port
     * @param y - y coordinate of tele-port
     * @return -1 if an entity is already there, -2 if tele-port location is invalid, 
     * -4 if destination is impassable
     */
    public int teleportTo(int new_x, int new_y) {
        MapTile destination = current_map_reference_.getTile(new_x, new_y);
        if (destination == null) {
            return -2;
        } else {
            int old_x = this.getMyXCoordinate();
            int old_y = this.getMyYCoordinate();
            current_map_reference_.getTile(old_x, old_y).removeEntity();
            if (destination.isPassable() == false) { // put the entity back in its place
                current_map_reference_.getTile(old_x, old_y).addEntity(entity_);
                return -4;
            } else { // move the entity
                int error_code = destination.addEntity(entity_);
                Item landed_on_item = destination.viewTopItem();
                if (landed_on_item != null) { // make the item walked on do stuff
                    landed_on_item.onWalkOver();
                }
                return error_code;
            }
        }
    }

    private final int x_respawn_point_;
    private final int y_respawn_point_;
}
