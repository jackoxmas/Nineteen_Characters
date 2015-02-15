package src.controller;

import java.io.Serializable;
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
abstract public class DrawableThing implements Serializable {

    // names of items and terrain should be non-unique.
    // names of entities should be unique to fit in a hashmap.
    public final String name_;

    // For things that take up only  1 tile or need to appear on a minimap
    private final char single_character_representation_;

    private MapDrawableThing_Relation map_relationship_;

    /**
     * Use this to call functions contained within the MapDrawable relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    public MapDrawableThing_Relation getMapRelation() {
        return map_relationship_;
    }

    public void setMapRelation(MapDrawableThing_Relation d) {
        map_relationship_ = d;
    }

    protected DrawableThing(String name, char representation) {
        name_ = name;
        single_character_representation_ = representation;
        is_viewable_ = true;
        map_relationship_ = new MapDrawableThing_Relation(this);
    }

    private boolean is_viewable_;

    private final DrawableThingStatsPack stats_pack_ = new DrawableThingStatsPack();

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
    private static final long serialVersionUID = Long.parseLong("Drawable", 35);
    // </editor-fold>
}
