/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;
import src.model.map.constructs.Item;
import src.model.map.constructs.EntityStatsPack;
import src.model.map.MapTile;
/**
 * Contains contents of data for IO to user.
 * @author JohnReedLOL
 */
public class IO_Package {
    public MapTile[][] visible_maptiles_for_display = null;
    public Item inventory_item_for_display = null;
    public Item equipped_item_for_display = null;
    public EntityStatsPack stats_for_display = null;
}
