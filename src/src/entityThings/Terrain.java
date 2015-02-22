/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.entityThings;


import src.SavedGame;
import src.model.MapTerrain_Relation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Terrain for MapTile.
 */
public class Terrain extends DrawableThing {

    void activate() {

    }

    void applyTerrainEffect(Entity entity) {

    }
	
    public enum Color {
        GREEN, BLUE, GRAY //grass, water, mountain
    }
    Color color_;

    private boolean contains_water_;
    private boolean contains_mountain_;

    private char decal_ = '\u0000'; // null character
    /**
     * Sets Terrain's decal.
     * @param decal
     */
    public void addDecal(char decal) {
        decal_ = decal;
    }
    
    public char getDecal() {
    	return decal_;
    }
    
    /**
     * Checks if terrain has decal.
     * @return true if terrain has decal. False if not.
     */
    public boolean hasDecal() {
        if (decal_ == '\u0000' || decal_ == ' ') {
            return false;
        } else {
            return true;
        }
    }

    public void removeDecal(char decal) {
        decal_ = ' ';
    }

    //potential duplicate of isPassable
    boolean determineIfCanPass(Entity entity) {
        if (contains_water_ || contains_mountain_) {
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Gets char that represents terrain on map.
     */
    @Override
    public char getRepresentation() {
        if(this.hasDecal()) {
            return decal_;
        } else {
            return super.getRepresentation();
        }
    }
    
    public String hasMountain() {
    	if (contains_mountain_)
    		return "true";
    	return "false";
    }
    
    public String hasWater() {
    	if (contains_water_)
    		return "true";
    	return "false";
    }

    @Override
    public boolean isPassable() {
        if (contains_water_ || contains_mountain_) {
            return false;
        }
        return true;
    }

    // map_relationship_ is used in place of a map_referance_
    private MapTerrain_Relation map_relationship_;

    /**
     * Use this to call functions contained within the MapTerrain relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    public MapTerrain_Relation getMapRelation() {
        return map_relationship_;
    }

    public void setMapRelation(MapTerrain_Relation t) {
        map_relationship_ = t;
    }

    /**
     * Contains extra parameter, decal. 
     * @param name
     * @param representation
     * @param contains_mountain
     * @param contains_water
     * @param decal - Character that will represent terrain on map.
     */
    public Terrain(String name, char representation, boolean contains_mountain,
            boolean contains_water, char decal) {
        super(name, representation);
        //color_ = color;
        contains_water_ = contains_water;
        contains_mountain_ = contains_mountain;
        decal_ = decal;
    }

    /**
     * Constructor for Terrain. Decal set to null.
     * @param name
     * @param representation
     * @param contains_mountain
     * @param contains_water
     */
    public Terrain(String name, char representation, boolean contains_mountain,
            boolean contains_water) {
        super(name, representation);
        //color_ = color;
        contains_water_ = contains_water;
        contains_mountain_ = contains_mountain;
    }
    
    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">

    // </editor-fold>
}
