/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;

import java.util.ArrayList;

import src.controller.Avatar;
import src.controller.Entity;
/**
 * Players see the AvatarCreationView when they chose their occupation.
 * @author Matthew B, Jessan, JohnReedLOL
 */
final class AvatarCreationView extends Viewport
{
    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("CCView", 35);
	

    private final Avatar avatar_reference_;
    private char[][] view_contents_;
    private ArrayList<String> title_;
    private void renderArray(){
    	for(int i = 0; i!=title_.size();++i){
    		writeStringToContents(5,1+i,title_.get(i));
    	}
    }
    /*
     * Generates a new ChaAvatarCreationViewses avatar_reference_ to modify the avatar.
     */
    public AvatarCreationView(Avatar my_avatar) {
    	avatar_reference_ = my_avatar;
    	view_contents_=new char[length_][width_];
    	makeSquare(0,0,length_-1,width_-1);//This is a static view, no need to dynamically render it each turn.
		title_ = getAsciiArtFromFile("src/view/ASCIIART/class.txt");
		renderArray();
    }
}
