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
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author JohnReedLOL
 */
public class Terrain extends DrawableThing implements SaveData{

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

    public boolean hasDecal() {
        if (decal_ == '\u0000' || decal_ == ' ') {
            return false;
        } else {
            return true;
        }
    }

    public void addDecal(char decal) {
        decal_ = decal;
    }

    private boolean contains_water_;
    private boolean contains_mountain_;
    
    public void removeDecal(char decal) {
        decal_ = ' ';
    }
    
    @Override
    public char getRepresentation() {
        if(this.hasDecal()) {
            return decal_;
        } else {
            return super.getRepresentation();
        }
    }

    public Terrain(String name, char representation, boolean contains_mountain,
            boolean contains_water, char decal) {
        super(name, representation);
        //color_ = color;
        contains_water_ = contains_water;
        contains_mountain_ = contains_mountain;
        decal_ = decal;
        initializeMapRelationship();
    }

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
    @Override
    public String getSerTag() {
        return "TERRAIN";
    }

    protected void linkOther (ArrayDeque<SaveData> refs) {
        super.linkOther(refs);
    }

    protected void readOther (ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {
        super.readOther(ois, out_rels);
    }

    protected void writeOther (ObjectOutputStream oos, HashMap<SaveData, Boolean> saveMap) throws IOException {
        super.writeOther(oos, saveMap);
    }

    // </editor-fold>
}
