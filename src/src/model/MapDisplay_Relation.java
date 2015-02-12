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
    private final MapTile[][] tiles_ = Map.getMyReferanceToTheMapGrid(this);
    private final Display display_;
    public MapDisplay_Relation(Display display) {
        display_ = display;
    }
}
