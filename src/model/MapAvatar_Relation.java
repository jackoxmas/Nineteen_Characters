/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Avatar;
import src.controller.Occupation;

/**
 *
 * @author JohnMichaelReed
 */
public class MapAvatar_Relation extends MapEntity_Relation {

    private final Avatar avatar_;

    public MapAvatar_Relation(Avatar avatar,
            int x_respawn_point, int y_respawn_point) {
        super(avatar, x_respawn_point, y_respawn_point);
        avatar_ = avatar;
    }

    public Avatar getAvatar() {
        return avatar_;
    }

	/**
	 * This function Spawns an Avatar at the specified Location
	 * @author reidholsen
	 */
	@Override
	public void spawn(Entity toSpawn, int time_until_spawn) {
		toSpawn.getMapRelation().map_reference_.addAvatar((Avatar)toSpawn, this.getRespawnPointX(), this.getRespawnPointY());
	}
	
	/**
     * This function will move an Entity in the specified direction with the specified amount.
     * @param toMove
     * @param DeltaX
     * @param DeltaY
     * @author reidholsen
     */
	@Override
    public void moveInDirection(Entity toMove, int DeltaX, int DeltaY) {
    	int currentX = this.getMyXCordinate();
    	int currentY = this.getMyYCordinate();
    	
    	toMove.getMapRelation().map_reference_.removeAvatar((Avatar)toMove);
    	toMove.getMapRelation().map_reference_.addAvatar((Avatar)toMove, currentX + DeltaX, currentY + DeltaY);
    }
}
