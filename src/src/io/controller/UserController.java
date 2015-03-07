/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.io.controller;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

import src.CharacterCreationEnum;
import src.CompassEnum;
import src.Function;
import src.InteractEnum;
import src.model.map.MapUser_Interface;
import src.io.view.AvatarCreationView;
import src.io.view.MapView;
import src.io.view.StatsView;
import src.io.view.Viewport;
import src.io.view.display.Display;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class UserController implements Function<Void, Character>
{
	private class KeyRemapper{
		private HashMap<Character,Enum> remap_ = new HashMap<Character,Enum>();
		public KeyRemapper(){
			initBindings();
		}
		private void initBindings(){
			//Character Creation
			remap_.put('Z',CharacterCreationEnum.SMASHER);
			remap_.put('X',CharacterCreationEnum.SUMMONER);
			remap_.put('C',CharacterCreationEnum.SNEAKER);
			//Directions NUMPAD
			remap_.put('1',CompassEnum.SOUTH_WEST);
			remap_.put('2',CompassEnum.SOUTH);
			remap_.put('3',CompassEnum.SOUTH_EAST);
			remap_.put('4',CompassEnum.WEST);
			remap_.put('5',CompassEnum.STANDING_STILL);
			remap_.put('6',CompassEnum.EAST);
			remap_.put('7',CompassEnum.NORTH_WEST);
			remap_.put('8',CompassEnum.NORTH);
			remap_.put('9',CompassEnum.NORTH_EAST);
			//Directions Keyboard
			remap_.put('z',CompassEnum.SOUTH_WEST);
			remap_.put('x',CompassEnum.SOUTH);
			remap_.put('c',CompassEnum.SOUTH_EAST);
			remap_.put('a',CompassEnum.WEST);
			remap_.put('s',CompassEnum.STANDING_STILL);
			remap_.put('d',CompassEnum.EAST);
			remap_.put('q',CompassEnum.NORTH_WEST);
			remap_.put('w',CompassEnum.NORTH);
			remap_.put('e',CompassEnum.NORTH_EAST);
			//Interact up bindings.
			remap_.put('p',InteractEnum.PICK_UP);
			remap_.put('D', InteractEnum.DROP);
			remap_.put('E',InteractEnum.EQUIP);
			remap_.put('U', InteractEnum.UNEQUIP);
			remap_.put('i', InteractEnum.TOGGLE_VIEW);
			remap_.put('S', InteractEnum.SAVE_GAME);
			remap_.put('u',InteractEnum.USE_ITEM);
		}
		public void setMap(HashMap<Character, Enum> remap) {
			remap_ = remap;
		}
		public HashMap<Character, Enum> getMap() {
			return remap_;
		}
		public Enum mapInput(char input){
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
    private final char nullChar_ = (char)0;
    private final char stillChar_ = 'M';
    private KeyRemapper remap_ = new KeyRemapper();
    
    //Handles the view switching, uses the  instanceof operator in a slightly evil way, 
    //ideally we should look into refactoring this to not
    private void setView(Enum input){
    	boolean taken = false;
    	if(currentView_ instanceof AvatarCreationView){
    		for(Enum e : CharacterCreationEnum.values()){
    			if(e.equals(input)){
    				currentView_ = new MapView();
    			}
    		}
    	}	
    	if(currentView_ instanceof MapView){
    		if(InteractEnum.TOGGLE_VIEW.equals(input)){currentView_ = new StatsView(userName_); taken = true;}
    	}
    	else if(currentView_ instanceof StatsView){
    		if(InteractEnum.TOGGLE_VIEW.equals(input)){currentView_ = new MapView(); taken = true;}
    	}
    	if(!taken){
    	currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, input, currentView_.getWidth()/2,currentView_.getHeight()/2));
    	}
    	else{
    		currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, CompassEnum.STANDING_STILL, currentView_.getWidth()/2,currentView_.getHeight()/2));
    		//I need to get this info without sending a command, sending ' ' is a hack for now.
    	}
    }
    private void takeTurn(char foo) {
    	Enum input = remap_.mapInput(foo);
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
    public HashMap<Character, Enum> getRemap() {
        if (remap_ == null) return null;
        return remap_.getMap();
    }

    /**
     * Sets the underlying key remapping
     * @param remap The new key remapping to be applied
     * @author Alex Stewart
     */
    public void setRemap(HashMap<Character, Enum> remap) {
        if (remap_ == null) remap_ = new KeyRemapper();
        remap_.setMap(remap);
    }



	@Override
	public Void apply(Character foo) {
		takeTurn(foo);
		return null;
	}
    
}
