/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.view.Display;
import src.view.Viewport;

/**
 *
 * @author JohnMichaelReed
 */
public class MapDisplay_Relation {

    private Map current_map_reference_;
    private final Viewport view_;

    public MapDisplay_Relation(Viewport view) {
        view_ = view;
    }

    /**
     * This function must be called to associate a map_relation with a map.
     */
    public void associateWithMap(Map m) {
        current_map_reference_ = m;
    }

    /**
     * Gets the character representation of a tile
     *
     * @author John-Michael Reed
     * @param x
     * @param y
     * @return error code: returns empty space if the tile is off the map
     */
    public char getTileRepresentation(int x, int y) {
        MapTile tile_at_x_y = current_map_reference_.getTile(x, y);
        if (tile_at_x_y == null) {
            return ' ';
        } else {
            return tile_at_x_y.getTopCharacter();
        }
    }
}
