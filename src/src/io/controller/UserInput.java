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

import src.model.map.constructs.Avatar;
import src.io.view.Display;
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
    public UserInput(Avatar avatar) {
        my_avatar_ = avatar;
        Display.getDisplay().addKeyListener(this);
        Display.getDisplay().addFocusListener(this);
    }

    private final Avatar my_avatar_;
    
    /**
     * Runs the game.
     */
 
    private void takeTurn(KeyEvent e) {
    	Display _display = Display.getDisplay(my_avatar_.getMyView());
    	System.out.println(e.getKeyChar());
    	//my_avatar_.getInput((char)input);
		//my_avatar_.getMapRelation().getSimpleAngle();//Example of simpleangle
		//my_avatar_.getMapRelation().getAngle();//Example of how to use getAngle
		_display.setView(my_avatar_.getMyView());
        _display.printView();
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
