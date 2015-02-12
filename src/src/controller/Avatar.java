/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.model.MapAvatar_Relation;
import src.view.Display;

/**
 * Each avatar represents a player
 *
 *
 * @author JohnReedLOL
 */
public final class Avatar extends Entity {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("Avatar", 35);

    // map_relationship_ is used in place of a map_referance_
    private final MapAvatar_Relation map_relationship_;
    
    /**
     * Use this to call functions contained within the MapAvatar relationship
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapAvatar_Relation getMapRelation() {
        return map_relationship_;
    }

    public Avatar(String name, char representation, int x_respawn_point, int y_respawn_point) {
        super(name, representation, x_respawn_point, y_respawn_point);
        map_relationship_ = new MapAvatar_Relation(this, x_respawn_point, y_respawn_point);
    }

    private final Display display_ = new Display(this);

    public Display get_my_display() {
        return this.display_;
    }
}
