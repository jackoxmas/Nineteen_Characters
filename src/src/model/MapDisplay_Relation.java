/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import src.view.Display;

/**
 *
 * @author JohnMichaelReed
 */
public class MapDisplay_Relation {

    private final Map map_reference_ = Map.getMyReferanceToTheMapGrid(this);
    private final Display display_;

    public MapDisplay_Relation(Display display) {
        display_ = display;
    }

    /**
     * Gets the character representation of a tile
     * @author John-Michael Reed
     * @param x
     * @param y
     * @return error code: returns empty space if the tile is off the map
     */
    public char getTileRepresentation(int x, int y) {
        MapTile tile_at_x_y = map_reference_.getTile(x, y);
        if (tile_at_x_y == null) {
            return ' ';
        } else {
        return tile_at_x_y.getTopCharacter();
        }
    }
}
