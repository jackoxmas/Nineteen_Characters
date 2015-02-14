/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.model.MapAvatar_Relation;
import src.model.MapMain_Relation;
import src.view.Display;
import src.view.AvatarCreationView;
import src.view.MapView;
import src.view.StatsView;
import src.view.Viewport;

/**
 * Each avatar represents a player
 *
 *
 * @author JohnReedLOL
 */
public final class Avatar extends Entity {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("Avatar", 35);

    // map_relationship_ is used in place of a map_referance_
    private final MapAvatar_Relation map_relationship_;
    
    
    /**
     * Accepts a key command from the map
     * @param command
     * @return 0 on success, not zero if command cannot be accepted
     */
    public int acceptKeyCommand(char command) {
        return 0;
    }
    
    /**
     * Use this to call functions contained within the MapAvatar relationship
     * @return map_relationship_
     * @author Reed, John
     */
    @Override
    public MapAvatar_Relation getMapRelation() {
        return map_relationship_;
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
    public void switchToMapView(){
    	current_view_ = map_view_;
    }
    public void switchToStatsView(){
    	current_view_ = stats_view_;
    }
    
    private MapView generateMapView(){
		MapMain_Relation map_main = new MapMain_Relation();
		map_main.bindToNewMapOfSize(Viewport.width_,Viewport.height_); //Can change these later if we so desire. 
		MapView map_view = new MapView(this);
		map_main.addViewToMap(map_view);
		map_main.addAvatar(this, 0, 0);
		return map_view;
	}
    private StatsView generateStatsView(){
    	return new StatsView(this);
    }

    
    @Override
    public String toString(){
    	String s = "Avatar name: " + name_;
    	
    	if(!(equipped_item_ == null))
    		s += "\n equppied item: " + equipped_item_.name_;
    	else
    		s += "\n equppied item: null";
    	
    	s+= "\n Inventory " + "(" + inventory_.size() + ")" + ":";
    	for(int i = 0; i < inventory_.size(); ++i){
    		s+= " " + inventory_.get(i).name_;
    	}
    	
    	s+="\n";
    	
    	s+=" map_relationship_: ";
    	if(map_relationship_ == null)
    		s += "null";
    	else 
    		s += "Not null" ;
    		
    	s += "\n associated with map:" + map_relationship_.isAssociatedWithMap();
    	
    	return s;
    }
}
