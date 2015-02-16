/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.controller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import src.model.MapAvatar_Relation;
import src.view.Display;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class AvatarController
{
    private final Avatar my_avatar_;
    
    /**
     * AvatarController Constructor
     * @param avatar
     */
    public AvatarController(Avatar avatar) {
        my_avatar_ = avatar;
    }
    
    /**
     * Runs the game.
     */
    public void runTheGame() {
    	Scanner sc = new Scanner(System.in);
    	char input = '`';
    	Display _display = new Display(my_avatar_.getMyView());
    	while ( (input = sc.next().charAt(0) ) != '`' ) {
			my_avatar_.getInput((char)input);
			_display.setView(my_avatar_.getMyView());
            _display.printView();
    	}
    	
    	sc.close();
    	
    }
    
}
