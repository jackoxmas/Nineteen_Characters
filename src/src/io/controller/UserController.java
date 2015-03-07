/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.io.controller;
import java.util.HashMap;

import src.Function;
import src.Key_Commands;
import src.io.view.AvatarCreationView;
import src.io.view.MapView;
import src.io.view.StatsView;
import src.io.view.Viewport;
import src.io.view.display.Display;
import src.model.map.MapUser_Interface;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class UserController implements Function<Void, Character>
{
	private class KeyRemapper{
		private HashMap<Character,Key_Commands> remap_ = new HashMap<Character,Key_Commands>();
		public KeyRemapper(){
			initBindings();
		}
		private void initBindings(){
			//Character Creation
			remap_.put('Z',Key_Commands.BECOME_SMASHER );
			remap_.put('X',Key_Commands.BECOME_SUMMONER);
			remap_.put('C',Key_Commands.BECOME_SNEAK);
			//Directions NUMPAD
			remap_.put('1',Key_Commands.MOVE_DOWNLEFT);
			remap_.put('2',Key_Commands.MOVE_DOWN);
			remap_.put('3',Key_Commands.MOVE_DOWNRIGHT);
			remap_.put('4',Key_Commands.MOVE_LEFT);
			remap_.put('5',Key_Commands.STANDING_STILL);
			remap_.put('6',Key_Commands.MOVE_RIGHT);
			remap_.put('7',Key_Commands.MOVE_UPLEFT);
			remap_.put('8',Key_Commands.MOVE_UP);
			remap_.put('9',Key_Commands.MOVE_UPRIGHT);
			//Directions Keyboard
			remap_.put('z',Key_Commands.MOVE_DOWNLEFT);
			remap_.put('x',Key_Commands.MOVE_DOWN);
			remap_.put('c',Key_Commands.MOVE_DOWNRIGHT);
			remap_.put('a',Key_Commands.MOVE_LEFT);
			remap_.put('s',Key_Commands.STANDING_STILL);
			remap_.put('d',Key_Commands.MOVE_RIGHT);
			remap_.put('q',Key_Commands.MOVE_UPLEFT);
			remap_.put('w',Key_Commands.MOVE_UP);
			remap_.put('e',Key_Commands.MOVE_UPRIGHT);
			//Interact up bindings.
			remap_.put('p',Key_Commands.PICK_UP_ITEM);
			remap_.put('D', Key_Commands.DROP_LAST_ITEM);
			remap_.put('E',Key_Commands.EQUIP_LAST_ITEM);
			remap_.put('U', Key_Commands.UNEQUIP_EVERYTHING);
			remap_.put('i', Key_Commands.TOGGLE_VIEW);
			remap_.put('S', Key_Commands.SAVE_GAME);
			remap_.put('u',Key_Commands.USE_LAST_ITEM);
		}
		public void setMap(HashMap<Character, Key_Commands> remap) {
			remap_ = remap;
		}
		public HashMap<Character, Key_Commands> getMap() {
			return remap_;
		}
		public Key_Commands mapInput(char input){
			return remap_.get(input);
		}
	}

    public UserController(MapUser_Interface mui, String uName) {
        MapUserAble_ = mui;
        userName_ = uName;
        setView(null);
    	Display.getDisplay().addGameInputerHandler(this);
    	Display.getDisplay().setView(currentView_);
        Display.getDisplay().printView();
        
    }

    private MapUser_Interface MapUserAble_;
    private final String userName_;
    private Viewport currentView_ = new AvatarCreationView(); 
    private KeyRemapper remap_ = new KeyRemapper();
    
    //Handles the view switching, uses the  instance of operator in a slightly evil way, 
    //ideally we should look into refactoring this to not
    private void setView(Key_Commands input){
    	boolean taken = false;
    	if(currentView_ instanceof AvatarCreationView){
    			if(Key_Commands.BECOME_SNEAK.equals(input)||Key_Commands.BECOME_SMASHER.equals(input)
    					||Key_Commands.BECOME_SUMMONER.equals(input)){
    				currentView_ = new MapView();
    			}
    	}	
    	if(currentView_ instanceof MapView){
    		if(Key_Commands.TOGGLE_VIEW.equals(input)){currentView_ = new StatsView(userName_); taken = true;}
    	}
    	else if(currentView_ instanceof StatsView){
    		if(Key_Commands.TOGGLE_VIEW.equals(input)){currentView_ = new MapView(); taken = true;}
    	}
    	if(!taken){
    	currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, input, currentView_.getWidth()/2,currentView_.getHeight()/2));
    	}
    	else{
    		currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, Key_Commands.STANDING_STILL, currentView_.getWidth()/2,currentView_.getHeight()/2));
    		//I need to get this info without sending a command, sending ' ' is a hack for now.
    	}
    }
    private void takeTurn(char foo) {
    	Key_Commands input = remap_.mapInput(foo);
    	setView(input);
    	//my_avatar_.getInput((char)input);
		//my_avatar_.getMapRelation().getSimpleAngle();//Example of simpleangle
		//my_avatar_.getMapRelation().getAngle();//Example of how to use getAngle
    	Display.getDisplay().setView(currentView_);
        Display.getDisplay().printView();
    	}

    // FIELD ACCESSORS
    /**
     * Gets this UserController's user name value
     * <p>Used for saving. Loading is done through the constructor</p>
     * @return A String object with this UserController's user name
     * @author Alex Stewart
     */
    public String getUserName() { return userName_; }

    /**
     * Gets the underlying key remapping values
     * @return A HashMap with the remapped key values in it
     * @author Alex Stewart
     */
    public HashMap<Character, Key_Commands> getRemap() {
        if (remap_ == null) return null;
        return remap_.getMap();
    }

    /**
     * Sets the underlying key remapping
     * @param remap The new key remapping to be applied
     * @author Alex Stewart
     */
    public void setRemap(HashMap<Character, Key_Commands> remap) {
        if (remap_ == null) remap_ = new KeyRemapper();
        remap_.setMap(remap);
    }



	@Override
	public Void apply(Character foo) {
		takeTurn(foo);
		return null;
	}
    
}
