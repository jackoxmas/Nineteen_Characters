/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.model.MapTerrain_Relation;

/**
 *
 * @author JohnReedLOL
 */
public class Terrain extends DrawableThing {

    // Converts a class name [which must be unique] into a unique base 35 number
    private static final long serialVersionUID = Long.parseLong("Terrain", 35);

    // map_relationship_ is used in place of a map_referance_
    private MapTerrain_Relation map_relationship_;
    
    /**
     * Use this to call functions contained within the MapTerrain relationship
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapTerrain_Relation getMapRelation() {
        return map_relationship_;
    }

    /**
     * This function is necessary because the constructor cannot safely build
     * the map_relationship. Make sure that this function uses a subclass this.
     */
    private void initializeMapRelationship() {					
        map_relationship_ = new MapTerrain_Relation(this);
    }

    public enum Color {

        GREEN, BLUE, GRAY //grass, water, mountain
    }
    Color color_;
    private char decal_;
    private final boolean contains_water_;
    private final boolean contains_mountain_;

    public Terrain(String name, char representation, boolean is_passable,
            Color color, char decal, boolean contains_water, boolean contains_mountain) {
        super(name, representation);
        color_ = color;
        decal_ = decal;
        contains_water_ = contains_water;
        contains_mountain_ = is_passable;
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
}
