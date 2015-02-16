/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.SaveData;
import src.controller.Terrain;
import java.io.Serializable;

/**
 *
 * @author JohnMichaelReed
 */
public class MapTerrain_Relation extends MapDrawableThing_Relation implements SaveData {

    private final Terrain terrain_;

    public MapTerrain_Relation(Terrain terrain) {
        terrain_ = terrain;
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    public String getSerTag() {
        return "RELATION_MAP_TERRAIN";
    }
    // </editor-fold>
}
