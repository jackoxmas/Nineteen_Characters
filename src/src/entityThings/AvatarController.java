/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.entityThings;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import src.model.MapAvatar_Relation;
import src.userIO.Display;
/**
 * Uses keyboard input to control the avatar
 * @author JohnReedLOL
 */
public final class AvatarController
{

    /**
     * AvatarController Constructor
     * @param avatar
     */
    public AvatarController(Avatar avatar) {
        my_avatar_ = avatar;
    }

    private final Avatar my_avatar_;
    
    /**
     * Runs the game.
     */
    public void runTheGame() {
    	Scanner sc = new Scanner(System.in);
    	char input = '`';
    	Display _display = new Display(my_avatar_.getMyView());
    	while ( (input = sc.next().charAt(0) ) != '`' ) {
			my_avatar_.getInput((char)input);
			//my_avatar_.getMapRelation().getAngle();//Example of how to use getAngle
			_display.setView(my_avatar_.getMyView());
            _display.printView();
    	}
    	
    	sc.close();
    	
    }
    
}
