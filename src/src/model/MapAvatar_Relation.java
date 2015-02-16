/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.SaveData;
import src.controller.Avatar;
import src.controller.Occupation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;

/**
 *
 * @author JohnMichaelReed
 */
public class MapAvatar_Relation extends MapEntity_Relation implements SaveData {

    private Avatar avatar_;

    /**
     * MapAvatar_Relation constructor. Inherits from MapEntity_Relation.
     * @param avatar
     * @param x_respawn_point
     * @param y_respawn_point
     */
    public MapAvatar_Relation(Avatar avatar,
            int x_respawn_point, int y_respawn_point) {
        super(avatar, x_respawn_point, y_respawn_point);
        avatar_ = avatar;
    }

    /**
     * Gets Avatar associated with the relation.
     * @return avatar
     */
    public Avatar getAvatar() {
        return avatar_;
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    public String getSerTag() {
        return "RELATION_MAP_AVATAR";
    }
    // </editor-fold>
}
