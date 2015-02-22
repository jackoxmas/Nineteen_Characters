/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;
import src.entityThings.Item;
import src.entityThings.EntityStatsPack;
import src.model.MapTile;
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
