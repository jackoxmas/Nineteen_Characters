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
    
    public void spawn(Entity toSpawn, int time_until_spawn) {

    }

    public void moveInDirection(Entity toMove, int DeltaX, int DeltaY) {

    }

    public void sendAttack(Entity attacker, int x, int y) {

    }

    public void recieveAttack(Entity attack_reciever, int damage) {

    }

    public void levelUpEntity(Entity entity, StatsPack stats_pack) {

    }

    public void addStatsPackToEntity(Entity entity, StatsPack stats_pack) {

    }

    public void subtractStatsPackFromEntity(Entity entity, StatsPack stats_pack) {

    }
    
    public void entityPickUpItem(Entity entity, Item item) {
        
    }
}
