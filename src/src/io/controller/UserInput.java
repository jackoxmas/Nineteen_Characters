/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.io.controller;
import java.util.Scanner;

import src.model.map.constructs.Avatar;
import src.io.view.Display;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class UserInput
{

    /**
     * AvatarController Constructor
     * @param avatar
     */
    public UserInput(Avatar avatar) {
        my_avatar_ = avatar;
    }

    private final Avatar my_avatar_;
    
    /**
     * Runs the game.
     */
    public void runTheGame() {
    	Scanner sc = new Scanner(System.in);
    	char input = '`';
    	Display _display = Display.getDisplay(my_avatar_.getMyView());
    	while ( (input = sc.next().charAt(0) ) != '`' ) {
			my_avatar_.getInput((char)input);
			//my_avatar_.getMapRelation().getSimpleAngle();//Example of simpleangle
			//my_avatar_.getMapRelation().getAngle();//Example of how to use getAngle
			_display.setView(my_avatar_.getMyView());
            _display.printView();
    	}
    	
    	sc.close();
    	
    }
    
}
