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
    public IO_Bundle sendCommandToMap(String username, char command, int width_from_center, int height_from_center);
}
