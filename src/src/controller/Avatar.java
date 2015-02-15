/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.SaveData;
import src.model.MapAvatar_Relation;
import src.model.MapMain_Relation;
import src.view.Display;
import src.view.AvatarCreationView;
import src.view.MapView;
import src.view.StatsView;
import src.view.Viewport;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Each avatar represents a player
 *
 *
 * @author JohnReedLOL
 */
public final class Avatar extends Entity{

    // map_relationship_ is used in place of a map_referance_
    private MapAvatar_Relation map_relationship_;

    /**
     * Accepts a key command from the map
     *
     * @param command
     * @return 0 on success, not zero if command cannot be accepted
     */
    public int acceptKeyCommand(char command) {
        return 0;
    }

    /**
     * Use this to call functions contained within the MapAvatar relationship
     *
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapAvatar_Relation getMapRelation() {
        return map_relationship_;
    }

    public void setMapRelation(MapAvatar_Relation a) {
        map_relationship_ = a;
    }

    public Avatar(String name, char representation, int x_respawn_point, int y_respawn_point) {
        super(name, representation, x_respawn_point, y_respawn_point);
        map_relationship_ = new MapAvatar_Relation(this, x_respawn_point, y_respawn_point);
        map_view_ = generateMapView();
        stats_view_ = generateStatsView();
    }

    private Viewport current_view_ = new AvatarCreationView(this);
    private MapView map_view_;
    private StatsView stats_view_;

    public Viewport getMyView() {
        return this.current_view_;
    }

    public void switchToMapView() {
        current_view_ = map_view_;
    }

    public void switchToStatsView() {
        current_view_ = stats_view_;
    }

    private MapView generateMapView() {
        MapMain_Relation map_main = new MapMain_Relation();
        map_main.bindToNewMapOfSize(Viewport.width_, Viewport.height_); //Can change these later if we so desire. 
        MapView map_view = new MapView(this);
        map_main.addViewToMap(map_view);
        map_main.addAvatar(this, 0, 0);
        return map_view;
    }

    private StatsView generateStatsView() {
        return new StatsView(this);
    }

    @Override
    public String toString() {
        String s = "Avatar name: " + name_;

        if (!(equipped_item_ == null)) {
            s += "\n equppied item: " + equipped_item_.name_;
        } else {
            s += "\n equppied item: null";
        }

        s += "\n Inventory " + "(" + inventory_.size() + ")" + ":";
        for (int i = 0; i < inventory_.size(); ++i) {
            s += " " + inventory_.get(i).name_;
        }

        s += "\n";

        s += " map_relationship_: ";
        if (map_relationship_ == null) {
            s += "null";
        } else {
            s += "Not null";
        }

        s += "\n associated with map:" + map_relationship_.isAssociatedWithMap();

        return s;
    }
    /*
     * Handles avatar input
     */
    public void getInput(char c){
    	if(current_view_ == map_view_){//If we currently have our mapview equipped(check by reference)
    		switch(c){
    		case '1'://Move SW
    			break;
    		case '2'://Move S
    			break;
    		case '3'://Move SE
    			break;
    		case '4': // Move W
    			break;
    		case '6'://Move E
    			break;
    		case '7'://Move NW
    			break;
    		case '8'://Move N
    			break;
    		case '9': //Move NE
    			break;
    		case 'S': //Save game
    			break;
    		case 'v': //Open stats
    			break;
    		case 'i': //Use item
    			break;
    		case 'q'://move NW
    			break;
    		case 'w': //move N
    			break;
    		case 'e'://move NE
    			break;
    		case 'a': //move W
    			break;
    		case 's'://Move stationary?
    			break;
    		case 'd'://Move E
    			break;
    		case 'z'://Move SW
    			break;
    		case 'x'://move s
    			break;
    		case 'c'://move SE
    			break;
    		default: //no valid input
    			break;
    		}
    		current_view_.renderToDisplay(); //See lower comment, maybe avatar should have a Display also to print it's views?
    	}
    	else{
    		current_view_.getInput(c);
    		current_view_.renderToDisplay();//Although printing with display already calls this, might just want to move the display into avatar or something, not really sure
    	}
    }



    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">
    @Override
    public String getSerTag() {
        return null;
    }

    @Override
    public Object deserialize(ObjectInputStream ois, LinkedList<Integer> out_refHashes) throws ClassNotFoundException, IOException {
        return null;
    }

    @Override
    public void relink(Object[] refs) {

    }

    @Override
    public void serialize(ObjectOutputStream oos, HashMap<SaveData, Boolean> refMap) throws IOException {

    }
    // </editor-fold>
}
