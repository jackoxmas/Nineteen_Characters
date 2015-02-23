/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.entityThings.Avatar;
import src.entityThings.Occupation;

import java.io.Serializable;

/**
 * Handles interactions between map + avatar that neither should otherwise be othered from.
 * @author JohnMichaelReed
 */
public class MapAvatar_Relation extends MapEntity_Relation implements Serializable {

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("RELATIONMA", 35);
    // </editor-fold>
    
    private final Avatar avatar_;

    /**
     * Gets Avatar associated with the relation.
     * @return avatar
     */
    public Avatar getAvatar() {
        return avatar_;
    }

    /**
     * MapAvatar_Relation constructor. Inherits from MapEntity_Relation.
     * @param avatar
     * @param x_respawn_point
     * @param y_respawn_point
     */
    public MapAvatar_Relation(Map m, Avatar avatar,
            int x_respawn_point, int y_respawn_point) {
        super(m, avatar, x_respawn_point, y_respawn_point);
        avatar_ = avatar;
    }
}
