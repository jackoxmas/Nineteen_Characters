/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.SaveData;
import src.SavedGame;
import src.model.MapAvatar_Relation;
import src.model.MapDrawableThing_Relation;
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
public final class Avatar extends Entity {

    // map_relationship_ is used in place of a map_referance_
    private MapAvatar_Relation map_relationship_;

    // holds the views
    private Viewport current_viewport_;
    private final MapView map_view_;
    private final StatsView stats_view_;
    private char storedInput;

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
    /* Make sure to call set map after this!
     * 
     */

    public Avatar(String name, char representation, int x_respawn_point, int y_respawn_point) {
        super(name, representation, x_respawn_point, y_respawn_point);
        map_relationship_ = new MapAvatar_Relation(this, x_respawn_point, y_respawn_point);
        map_view_ = generateMapView();
        stats_view_ = generateStatsView();
        current_viewport_ = new AvatarCreationView(this);
    }

    public Viewport getMyView() {
        return this.current_viewport_;
    }

    public void switchToMapView() {
        current_viewport_ = map_view_;
    }

    public void switchToStatsView() {
        current_viewport_ = stats_view_;
    }

    private MapView generateMapView() {
        MapView map_view = new MapView();
        return map_view;
    }

    private StatsView generateStatsView() {
        return new StatsView(this);
    }

    public void setMap(MapMain_Relation map_main) {
        map_main.addViewToMap(map_view_);
        map_main.addAvatar(this, 0, 0);
    }
    
	/* determine if input is not important
	 * or if we already did something
	 * then if true
	 * 
	 * storedInput = '~';
	 */
	public void sendInput( char current ) {
		if (map_relationship_ == null) {
			System.out.println("Avatar cannot be controlled without a MapAvatar_Relation");
			return;
		}
		else if (current_viewport_ != map_view_) {
	    	current_viewport_.getInput(current);
    		current_viewport_.renderToDisplay(); //See lower comment, maybe avatar should have a Display also to print it's views?
		}
		else {
			map_view_.setCenter(map_relationship_.getMyXCoordinate(),map_relationship_.getMyYCoordinate());
			if (storedInput == 'p') {
    			int error_code = 0;
				switch (current) {
					case '1':
						error_code = map_relationship_.pickUpItemInDirection(0, -1);
						break;
					case '2':
						error_code = map_relationship_.pickUpItemInDirection(0, -1);
						break;
					case '3':
						error_code = map_relationship_.pickUpItemInDirection(1, -1);
						break;
					case '4':
						error_code = map_relationship_.pickUpItemInDirection(-1, 0);
						break;
					case '5':
						error_code = map_relationship_.pickUpItemInDirection(0, 0);
						break;
					case '6':
						error_code = map_relationship_.pickUpItemInDirection(1, 0);
						break;
					case '7':
						error_code = map_relationship_.pickUpItemInDirection(-1, 1);
						break;
					case '8':
						error_code = map_relationship_.pickUpItemInDirection(0, 1);
						break;
					case '9':
						error_code = map_relationship_.pickUpItemInDirection(1, 1);
						break;
				}
				storedInput = '~';
				if (error_code != -1)
					System.out.println("pickUpItem function failed to get an item");
			} else if (storedInput == ' ') {
				switch (current) {
	    			case '1':
						map_relationship_.sendAttack(0, -1);
						break;
					case '2':
						map_relationship_.sendAttack(0, -1);
						break;
					case '3':
						map_relationship_.sendAttack(1, -1);
						break;
					case '4':
						map_relationship_.sendAttack(-1, 0);
						break;
					case '5':
						map_relationship_.sendAttack(0, 0);
						break;
					case '6':
						map_relationship_.sendAttack(1, 0);
						break;
					case '7':
						map_relationship_.sendAttack(-1, 1);
						break;
					case '8':
						map_relationship_.sendAttack(0, 1);
						break;
					case '9':
						map_relationship_.sendAttack(1, 1);
						break;
				}
				storedInput = '~';
			} else if (storedInput == '~') {
				switch (current) {
	    			case '1':
						map_relationship_.moveInDirection(0, -1);
						break;
					case '2':
						map_relationship_.moveInDirection(0, -1);
						break;
					case '3':
						map_relationship_.moveInDirection(1, -1);
						break;
					case '4':
						map_relationship_.moveInDirection(-1, 0);
						break;
					case '5':
						map_relationship_.moveInDirection(0, 0);
						break;
					case '6':
						map_relationship_.moveInDirection(1, 0);
						break;
					case '7':
						map_relationship_.moveInDirection(-1, 1);
						break;
					case '8':
						map_relationship_.moveInDirection(0, 1);
						break;
					case '9':
						map_relationship_.moveInDirection(1, 1);
						break;
					case 'z':
						map_relationship_.moveInDirection(0, -1);
						break;
					case 'x':
						map_relationship_.moveInDirection(0, -1);
						break;
					case 'c':
						map_relationship_.moveInDirection(1, -1);
						break;
					case 'a':
						map_relationship_.moveInDirection(-1, 0);
						break;
					case 's':
						map_relationship_.moveInDirection(0, 0);
						break;
					case 'd':
						map_relationship_.moveInDirection(1, 0);
						break;
					case 'q':
						map_relationship_.moveInDirection(-1, 1);
						break;
					case 'w':
						map_relationship_.moveInDirection(0, 1);
						break;
					case 'e':
						map_relationship_.moveInDirection(1, 1);
						break;
					case 'S':
						break;
					case 'v':
						break;
					case 'i':
						break;
					case 'D':
						 int error_code_D = map_relationship_.dropItem();
						 if(error_code_D != 0) {
						 	System.out.println("dropItem function failed to drop an item");
						 }
						 break;
				}
				storedInput = '~';
			}
    		current_viewport_.renderToDisplay(); //See lower comment, maybe avatar should have a Display also to print it's views?
		}
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

    // <editor-fold desc="SERIALIZATION" defaultstate="collapsed">

    // </editor-fold>
}
