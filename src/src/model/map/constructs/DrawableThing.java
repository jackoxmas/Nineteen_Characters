package src.model.map.constructs;

import java.awt.Color;

import src.model.map.MapDrawableThing_Relation;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Drawable things are things that may be drawn on the screen. Such as
 * Entity/Item/etc.
 *
 * @author JohnReedLOL
 */
abstract public class DrawableThing {
    // names of items and terrain should be non-unique.
    // names of entities should be unique to fit in a hashmap.
    public final String name_;

    public String getName() { return name_; }

    public char getDChar() { return single_character_representation_; }

    protected DrawableThing(String name, char representation) {
        name_ = name;
        single_character_representation_ = representation;
        is_visible_ = true;
    }

    protected DrawableThing(String name, char representation, boolean is_visible) {
        name_ = name;
        single_character_representation_ = representation;
        is_visible_ = is_visible;
    }
    protected DrawableThing(String name, char representation, Color col_) {
        name_ = name;
        single_character_representation_ = representation;
        is_visible_ = true;
        color_ = col_;
    }

    protected DrawableThing(String name, char representation, Color col_, boolean is_visible) {
        name_ = name;
        single_character_representation_ = representation;
        is_visible_ = is_visible;
        color_ = col_;
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

    private boolean is_visible_;

    /**
     * Get if Drawable Thing is viewable
     *
     * @return boolean
     */
    public boolean isVisible() {
        return this.is_visible_;
    }

    /**
     * Set if Drawable is viewable
     *
     * @param is_visible
     */
    public void setViewable(boolean is_visible) {
        is_visible_ = is_visible;
    }

    // For things that take up only  1 tile or need to appear on a minimap
    private char single_character_representation_;
    private Color color_ = Color.BLACK;//Default color to black
    public Color getColor(){return color_;}
    protected void setColor(Color col_){color_ = col_;}

    /**
     * Get character representation.
     *
     * @return Character being used to represent this Drawable Thing.
     */
    public char getRepresentation() {
        return this.single_character_representation_;
    }

    /**
     * Set character representation.
     *
     * @param c
     */
    public void setRepresentation(char c) {
        single_character_representation_ = c;
    }

    private DrawableThingStatsPack stats_pack_ = new DrawableThingStatsPack();
}
