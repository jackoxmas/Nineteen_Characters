/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model.map;

import src.IO_Bundle;

/**
 *
 * @author JohnReedLOL
 */
public interface MapUser_Interface {
    public IO_Bundle sendCommandToMap(String username, char command);
    /**
     * Note that the IO_Bundle contains a 2D array of characters in which the 
     * y coordinates are stored in the first [] and the x_cordinates are in the second.
     * @param username
     * @param command
     * @param width_from_center
     * @param height_from_center
     * @return 
     */
    public IO_Bundle sendCommandToMap(String username, char command, int width_from_center, int height_from_center);
}
