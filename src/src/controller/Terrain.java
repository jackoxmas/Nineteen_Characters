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
 *
 * @author JohnReedLOL
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

    protected Terrain (String name, char terrainChar) {
        super(name, terrainChar, false);
    }
    
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

    @Override
    public Terrain deserialize(ObjectInputStream ois, LinkedList<Integer> out_refHashes) throws ClassNotFoundException, IOException {
        SavedGame.defaultDataRead(this, ois, out_refHashes);
        // super class requirements
        String name = ois.readUTF();
        char rep = ois.readChar();
        Terrain t = new Terrain(name, rep);
        t.setViewable(ois.readBoolean());
        // primitives
        color_ = Color.valueOf(ois.readUTF());
        contains_mountain_ = ois.readBoolean();
        contains_water_ = ois.readBoolean();
        decal_ = ois.readChar();
        // references
        out_refHashes.add(ois.readInt()); // MapTerrain_Relation
        return t;
    }

    @Override
    public void relink(Object[] refs) {
        map_relationship_ = (MapTerrain_Relation)refs[0];
    }

    @Override
    public void serialize(ObjectOutputStream oos, HashMap<SaveData, Boolean> out_refMap) throws IOException {
        SavedGame.defaultDataWrite(this, oos);
        // super class requirements
        oos.writeChars(this.name_);
        oos.writeChar(this.getRepresentation());
        oos.writeBoolean(this.getViewable());
        // local primitives
        oos.writeChars(color_.name());
        oos.writeBoolean(contains_mountain_);
        oos.writeBoolean(contains_water_);
        oos.writeChar(decal_);
        oos.writeChars(name_);

        // references
        oos.writeInt(SavedGame.getHash(map_relationship_));
        out_refMap.putIfAbsent((SaveData)map_relationship_, false);
    }
    // </editor-fold>
}
