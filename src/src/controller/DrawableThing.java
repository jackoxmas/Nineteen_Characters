package src.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;

import src.SaveData;
import src.SavedGame;
import src.model.MapDrawableThing_Relation;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JohnReedLOL
 */
abstract public class DrawableThing implements SaveData {

    // names of items and terrain should be non-unique.
    // names of entities should be unique to fit in a hashmap.
    public String name_;

    // For things that take up only  1 tile or need to appear on a minimap
    private char single_character_representation_;

    private boolean is_viewable_;

    private DrawableThingStatsPack stats_pack_ = new DrawableThingStatsPack();

    protected DrawableThing(String name, char representation) {
        name_ = name;
        single_character_representation_ = representation;
        is_viewable_ = true;
    }
    public void setRepresentation(char c){single_character_representation_ = c;}
    
    /**
     * Use this to call functions contained within the MapDrawable relationship
     * @return map_relationship_
     * @author Reed, John
     */
    abstract public MapDrawableThing_Relation getMapRelation();

    /**
     * returns the statspack(stats) without the items (default stats)
     *
     * @author Jessan
     */
    public DrawableThingStatsPack getStatsPack() {
        return this.stats_pack_;
    }

    abstract public boolean isPassable();

    public void onTurn() {

    }

    //representation changes for terrain with/without decal
    public char getRepresentation() {
        return this.single_character_representation_;
    }

    public void setViewable(boolean is_viewable) {
        is_viewable_ = is_viewable;
    }

    public boolean getViewable() {
        return this.is_viewable_;
    }

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    protected DrawableThing() {}

    @Override
    public String getSerTag() {
        return "DRAWABLETHING";
    }

    protected void readOther (ObjectInputStream ois, ArrayDeque<Integer> out_rels) throws IOException, ClassNotFoundException {
        name_ = ois.readUTF();
        single_character_representation_ = ois.readChar();
        is_viewable_ = ois.readBoolean();
        out_rels.addLast(ois.readInt());
    }

    protected void linkOther (ArrayDeque<SaveData> refs) {
        stats_pack_ = (DrawableThingStatsPack)refs.pop();
    }

    protected void writeOther (ObjectOutputStream oos, HashMap<SaveData, Boolean> saveMap) throws IOException {
        oos.writeUTF(name_);
        oos.writeChar(single_character_representation_);
        oos.writeBoolean(is_viewable_);
        oos.writeInt(SavedGame.getHash(stats_pack_));
        saveMap.putIfAbsent(stats_pack_, false);
    }
    // </editor-fold>
}
