package src.entityThings;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;


import src.SavedGame;
import src.model.MapDrawableThing_Relation;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Drawable things are things that may be drawn on the screen. Such as Entity/Item/etc.
 * @author JohnReedLOL
 */
abstract public class DrawableThing implements SaveData {

    protected DrawableThing(String name, char representation) {
        name_ = name;
        single_character_representation_ = representation;
        is_viewable_ = true;
    }
    
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
    
    private boolean is_viewable_;

    /**
     * Get if Drawable Thing is viewable
     * @return boolean
     */
    public boolean getViewable() {
        return this.is_viewable_;
    }

    /**
     * Set if Drawable is viewable
     * @param is_viewable
     */
    public void setViewable(boolean is_viewable) {
        is_viewable_ = is_viewable;
    }
    
    // names of items and terrain should be non-unique.
    // names of entities should be unique to fit in a hashmap.
    public final String name_;

    // For things that take up only  1 tile or need to appear on a minimap
    private char single_character_representation_;
    
    /**
     * Get character representation.
     * @return Character being used to represent this Drawable Thing.
     */
    public char getRepresentation() {
        return this.single_character_representation_;
    }

    /**
     * Set character representation.
     * @param c
     */
    public void setRepresentation(char c){single_character_representation_ = c;}

    private DrawableThingStatsPack stats_pack_ = new DrawableThingStatsPack();

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
