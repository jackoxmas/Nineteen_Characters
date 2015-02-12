/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Entity;
import src.controller.Item;
import src.controller.Occupation;
import src.controller.StatsPack;

/**
 *
 * @author JohnReedLOL
 */
public class MapEntity_Relation extends MapDrawableThing_Relation {

    private final Entity entity_;

    public MapEntity_Relation(Entity entity,
            int x_respawn_point, int y_respawn_point) {
        super(entity);
        entity_ = entity;
        x_respawn_point_ = x_respawn_point;
        y_respawn_point_ = y_respawn_point;
    }
    private final int x_respawn_point_;
    private final int y_respawn_point_;
    
	/**
	 * This function Spawns an Avatar at the specified Location
	 * 
	 * @author reidholsen
	 */
	public void spawn(Entity toSpawn, int time_until_spawn) {
		toSpawn.getMapRelation().map_reference_.addEntity(toSpawn, x_respawn_point_, y_respawn_point_);
	}

	/**
	 * This function will move an Entity in the specified direction with the
	 * specified amount.
	 * 
	 * @param toMove
	 * @param DeltaX
	 * @param DeltaY
	 * @author reidholsen
	 */
	public void moveInDirection(Entity toMove, int DeltaX, int DeltaY) {
		int currentX = this.getMyXCordinate();
		int currentY = this.getMyYCordinate();

		toMove.getMapRelation().map_reference_.removeEntity(toMove);
		toMove.getMapRelation().map_reference_.addEntity(toMove, currentX + DeltaX, currentY + DeltaY);
	}

    /**
	 * Sends an attack in the direction specified by x and y.
	 * 
	 * @param attacker
	 *            - Entity who is doing the attack.
	 * @param x
	 *            - x direction of attack
	 * @param y
	 *            - y direction of attack
	 * @author reidholsen
	 */
	public void sendAttack(Entity attacker, int x, int y) {
    	int attack_receiver_x  = attacker.getMapRelation().getMyXCordinate() + x;
    	int attack_receiver_y  = attacker.getMapRelation().getMyYCordinate() + y;
    	
    	try{
    		Entity attack_receiver = map_reference_.getTile(attack_receiver_x, attack_receiver_y).getEntity();
    		attack_receiver.getMapRelation().receiveAttack(attack_receiver, 666);
    	} catch (NullPointerException e){
    		
    	}
    }

    public void recieveAttack(Entity attack_reciever, int damage) {

    }

    public void levelUpEntity(Entity entity, StatsPack stats_pack) {

    }

    public void addStatsPackToEntity(Entity entity, StatsPack stats_pack) {

    }

    public void subtractStatsPackFromEntity(Entity entity, StatsPack stats_pack) {

    }
    
   	/**
	 * This function will make Entity entity pick up Item item. 
	 * Adding item to entity's inventory and deleting item from the map.
	 * @param entity
	 * @param item
	 * @author reidholsen
	 */
	public void entityPickUpItem(Entity entity, Item item) {
		entity.addToInventory(item);
		item.getMapRelation().removeItem(item);
	}

	public int getRespawnPointX() {
		return x_respawn_point_;
	}

	public int getRespawnPointY() {
		return y_respawn_point_;
	}
}
