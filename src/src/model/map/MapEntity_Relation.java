/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map;

import src.FacingDirection;
import src.model.map.constructs.Entity;
import src.model.map.constructs.Item;
import src.io.view.Display;

/**
 * One line description
 * @author JohnReedLOL
 */
public class MapEntity_Relation extends MapDrawableThing_Relation {

    /**
     * @author John-Michael Reed
     * @return -1 if no item can be dropped (inventory empty)
     */
    public int dropItem() {
        Item itemToBeDropped = entity_.pullFirstItemOutOfInventory();
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
        if(x==0 && y == 0) {
            //nothing
        } else if(x == 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP);
        } else if(x == 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN);
        } else if(x > 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.RIGHT);
        } else if(x < 0 && y == 0) {
            entity_.setFacingDirection(FacingDirection.LEFT);
        } else if(x > 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_RIGHT);
        } else if(x > 0 && y < 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_RIGHT);
        } else if(x < 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.UP_LEFT);
        } else if(x < 0 && y > 0) {
            entity_.setFacingDirection(FacingDirection.DOWN_LEFT);
        }
        return super.pushEntityInDirection(entity_, x, y);
    }

    /**
     * An item underneath you can be picked up using the parameters 0,0. 0 if
     * item is picked up successfully, -1 if no item is on the specified tile.
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

    private final int x_respawn_point_;
    private final int y_respawn_point_;
}
