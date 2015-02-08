/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;

import src.controller.Avatar;
import src.controller.Entity;
/**
 * Players see the AvatarCreationView when they chose their occupation.
 * @author Matthew B, Jessan, JohnReedLOL
 */
final class AvatarCreationView extends Viewport
{
    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("CharacterCreationView", 35);
	

    private final Avatar avatar_reference_;
    private char[][] view_contents_;
    /*
     * Generates a new ChaAvatarCreationViewses avatar_reference_ to modify the avatar.
     */
    public AvatarCreationView(Avatar my_avatar) {
    	avatar_reference_ = my_avatar;
    	view_contents_=new char[length_][width_];
    }
}
