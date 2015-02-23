/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.entityThings.Terrain;

import java.io.Serializable;

/**
 *
 * @author JohnMichaelReed
 */
public class MapTerrain_Relation extends MapDrawableThing_Relation implements Serializable {

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    private static final long serialVersionUID = Long.parseLong("RELATIONMT", 35);
    // </editor-fold>
	
    public MapTerrain_Relation(Map m, Terrain terrain) {
        super(m);
        terrain_ = terrain;
    }
    
    private final Terrain terrain_;
}
