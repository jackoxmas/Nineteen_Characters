/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.io.controller;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;

import src.model.map.MapUser_Interface;
import src.model.map.constructs.Avatar;
import src.io.view.AvatarCreationView;
import src.io.view.Display;
import src.io.view.MapView;
import src.io.view.StatsView;
import src.io.view.Viewport;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class UserInput implements KeyListener, FocusListener
{

    /**
     * UserInput Constructor
     * @param avatar
     */
    public UserInput(MapUser_Interface mui, String uName) {
        Display.getDisplay().addKeyListener(this);
        Display.getDisplay().addFocusListener(this);
        MapUserAble_ = mui;
        userName_ = uName;
        setView(nullChar);
    	Display.getDisplay().setView(currentView_);
        Display.getDisplay().printView();
        
    }

    private MapUser_Interface MapUserAble_;
    private final String userName_;
    private Viewport currentView_ = new AvatarCreationView(); 
    private final char nullChar = (char)0;
    private void setView(char c){
    	boolean taken = false;
    	if(currentView_ instanceof AvatarCreationView){
    		if(c == 'Z' || c == 'C' || c == 'X' || c == 'V'){
    			currentView_ = new MapView(); 
    		}
    	}	
    	if(currentView_ instanceof MapView){
    		if(c == 'i'){currentView_ = new StatsView(userName_); taken = true;}
    	}
    	else if(currentView_ instanceof StatsView){
    		if(c == 'i'){currentView_ = new MapView(); taken = true;}
    	}
    	if(!taken){
    	currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, c, Viewport.width_/2,Viewport.height_/2));
    	}
    	else{
    		currentView_.renderToDisplay(MapUserAble_.sendCommandToMap(userName_, nullChar, Viewport.width_/2,Viewport.height_/2));
    		//I need to get this info without sending a command, sending ' ' is a hack for now.
    	}
    }
    private void takeTurn(KeyEvent e) {

    	setView(e.getKeyChar());

    	//my_avatar_.getInput((char)input);
		//my_avatar_.getMapRelation().getSimpleAngle();//Example of simpleangle
		//my_avatar_.getMapRelation().getAngle();//Example of how to use getAngle
    	Display.getDisplay().setView(currentView_);
        Display.getDisplay().printView();
    	}
    	
    	

	@Override
	public void keyPressed(KeyEvent e) {
		//Nothing to do here
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//We do nothing in this situation
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		takeTurn(e);
		
	}








	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void focusLost(FocusEvent arg0) {
		Display.getDisplay().requestFocus();//Required to work around a bug in swing
		//Otherwise, focus is never regained.
		
	}
    
}
