/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.controller.Avatar;
import src.controller.Occupation;

import java.io.Serializable;

/**
 *
 * @author JohnMichaelReed
 */
public class MapAvatar_Relation extends MapEntity_Relation implements Serializable {

    private Avatar avatar_;

    public MapAvatar_Relation(Avatar avatar,
            int x_respawn_point, int y_respawn_point) {
        super(avatar, x_respawn_point, y_respawn_point);
        avatar_ = avatar;
    }

    public Avatar getAvatar() {
        return avatar_;
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("RELATIONMA", 35);
    // </editor-fold>
}
