/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.SaveData;
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
     * This function is necessary because the constructor cannot safely build
     * the map_relationship. Make sure that this function uses a subclass this.
     */
    private void initializeMapRelationship() {
        this.map_relationship_ = new MapTerrain_Relation(this);
    }

    public enum Color {
        GREEN, BLUE, GRAY //grass, water, mountain
    }
    Color color_;
    private char decal_ = '\u0000'; // null character

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

    /**
     * Sets Terrain's decal.
     * @param decal
     */
    public void addDecal(char decal) {
        decal_ = decal;
    }

    private boolean contains_water_;
    private boolean contains_mountain_;
    
    public void removeDecal(char decal) {
        decal_ = ' ';
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
        initializeMapRelationship();
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
        initializeMapRelationship();
    }

    @Override
    public boolean isPassable() {
        if (contains_water_ || contains_mountain_) {
            return false;
        }
        return true;
    }

    void activate() {

    }

    void applyTerrainEffect(Entity entity) {

    }

    boolean determineIfCanPass(Entity entity) {
        if (contains_water_ || contains_mountain_) {
            return false;
        } else {
            return true;
        }
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">

    // </editor-fold>
}
