/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import src.model.MapAvatar_Relation;
import src.view.Display;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class AvatarController
{
    private final Avatar my_avatar_;
    
    public AvatarController(Avatar avatar) {
        my_avatar_ = avatar;
    }
    
    public void runTheGame() throws Exception {
    	
    	BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    	int input = -1;
    	Display _display = new Display(my_avatar_.getMyView());
    	while ( (input = in.read() ) != -1 ) {
			my_avatar_.getInput((char)input);
			_display.setView(my_avatar_.getMyView());
            _display.printView();
    	}
    	
    	in.close();
    }
    
}
