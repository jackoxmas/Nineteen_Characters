/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import src.model.MapAvatar_Relation;
import src.view.Display;
import src.view.AvatarCreationView;

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

    // holds the views
    private final Viewport current_viewport_;
    private final AvatarCreationView avatar_creation_view_;
    private final MapView map_view_;
    private final StatsView stats_view_;
    private char storedInput;
    
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
        avatar_creation_view_ = new AvatarCreationView();
        map_view_ = new MapView();
        stats_view_ = new StatsView();
        current_viewport_ = avatar_creation_view_;
    }

    private final Display display_ = new Display(new AvatarCreationView(this));

    public Display get_my_display() {
        return this.display_;
    }
    
    public void input( char current ) {
    	if (current_viewport_ != map_view_)
	    	current_viewport_.input( current );
    	else {
    		if (storedInput == 'p') {
    			switch (current) {
	    			case '1':
						map_relationship_.pickUpItemInDirection(0, -1);
						break;
					case '2':
						map_relationship_.pickUpItemInDirection(0, -1);
						break;
					case '3':
						map_relationship_.pickUpItemInDirection(1, -1);
						break;
					case '4':
						map_relationship_.pickUpItemInDirection(-1, 0);
						break;
					case '5':
						map_relationship_.pickUpItemInDirection(0, 0);
						break;
					case '6':
						map_relationship_.pickUpItemInDirection(1, 0);
						break;
					case '7':
						map_relationship_.pickUpItemInDirection(-1, 1);
						break;
					case '8':
						map_relationship_.pickUpItemInDirection(0, 1);
						break;
					case '9':
						map_relationship_.pickUpItemInDirection(1, 1);
						break;
    			}
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
    			}
    		}
    		
    	}
    	/* determine if input is not important
    	 * or if we already did something
    	 * then if true
    	 * 
    	 * storedInput = '~';
    	 */
    }

}
