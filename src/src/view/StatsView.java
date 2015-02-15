/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src.view;
import java.util.*;

import src.controller.Avatar;
import src.controller.Entity;
import src.controller.EntityStatsPack;
import src.controller.Item;

import java.lang.Character;
/**
 * Players see the StatsView when they are checking their stats
 * @author Matthew B, Jessan, Jack C,JohnReedLOL
 */
public final class StatsView extends Viewport
{
    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("StatsView", 35);
	
    private char[][] view_contents_;
    private ArrayList< ArrayList<Character>> render;
    private final Avatar avatar_reference_;

    private ArrayList<String> template_;    
    private boolean display_index = false;
    
    /**
     * Generates a new StatsView using the avatar_reference.
     */
    public StatsView(Avatar my_avatar) {
    	super();
    	avatar_reference_ = my_avatar;

    	view_contents_= getContents();
		template_ = getAsciiArtFromFile("src/view/ASCIIART/statsview.txt");
		renderArray();
	}
    
	@Override
	public void renderToDisplay() {
		render = new ArrayList< ArrayList<Character>>(width_);
		for(ArrayList<Character> i : render){
			i = new ArrayList<Character>(height_);
		}
	}
	
    private void renderArray() {
    	for (int i = 0; i < template_.size(); i++)
    		writeStringToContents(0, 0, template_.get(i));
    	renderStats();
    	renderInventory();
    }

    private void renderStats() {
    	if (avatar_reference_.getOccupation() == null)
    		return;
    	
    	writeStringToContents(5, 6, avatar_reference_.name_ + ",");
    	
    	int level = avatar_reference_.getStatsPack().cached_current_level_;
    	if (level == 1)
    		writeStringToContents(5, 8, level + "st Level " + avatar_reference_.getOccupation().toString());
    	else if (level == 2)
    		writeStringToContents(5, 8, level + "nd Level " + avatar_reference_.getOccupation().toString());
    	else if (level == 3)
    		writeStringToContents(5, 8, level + "rd Level " + avatar_reference_.getOccupation().toString());
    	else
    		writeStringToContents(5, 8, level + "th Level " + avatar_reference_.getOccupation().toString());

    	writeStringToContents(18, 10, rightAlign(3, "" + avatar_reference_.getStatsPack().strength_level_));
    	writeStringToContents(18, 11, rightAlign(3, "" + avatar_reference_.getStatsPack().agility_level_));
    	writeStringToContents(18, 12, rightAlign(3, "" + avatar_reference_.getStatsPack().intellect_level_));
    	writeStringToContents(18, 12, rightAlign(3, "" + avatar_reference_.getStatsPack().hardiness_level_));
    	
    	StringBuilder hearts = new StringBuilder();
    	for (int i = 0; i < (avatar_reference_.getStatsPack().current_life_/avatar_reference_.getStatsPack().life_)*10; i++)
    		hearts.append("♥");
    	writeStringToContents(38, 6, rightAlign(10, hearts.toString()));
    	writeStringToContents(40, 7, rightAlign(3, "" + avatar_reference_.getStatsPack().current_life_));
    	writeStringToContents(44, 7, rightAlign(3, "" + avatar_reference_.getStatsPack().life_));

    	StringBuilder diamonds = new StringBuilder();
    	for (int i = 0; i < avatar_reference_.getStatsPack().current_mana_/avatar_reference_.getStatsPack().mana_*10; i++)
    		diamonds.append("♦");
    	writeStringToContents(38, 9, rightAlign(10, diamonds.toString()));
    	writeStringToContents(40, 9, rightAlign(3, "" + avatar_reference_.getStatsPack().current_mana_));
    	writeStringToContents(44, 9, rightAlign(3, "" + avatar_reference_.getStatsPack().mana_));

    	StringBuilder spades = new StringBuilder();
    	for (int i = 0; i < (avatar_reference_.getStatsPack().quantity_of_experience_
    			- ((avatar_reference_.getStatsPack().cached_current_level_-1)*100))/10; i++)
    		spades.append("♠");
    	writeStringToContents(38, 12, rightAlign(10, spades.toString()));
    	writeStringToContents(44, 9, "" + 100);

    	writeStringToContents(6, 68, rightAlign(3, "" + avatar_reference_.getStatsPack().lives_left_));
    	writeStringToContents(8, 68, rightAlign(3, "" + avatar_reference_.getStatsPack().moves_left_in_turn_));
    	writeStringToContents(8, 72, rightAlign(3, "" + avatar_reference_.getStatsPack().movement_level_));
    	writeStringToContents(11, 72, rightAlign(3, "" + avatar_reference_.getStatsPack().offensive_rating_));
    	writeStringToContents(12, 72, rightAlign(3, "" + avatar_reference_.getStatsPack().defensive_rating_));
    	writeStringToContents(13, 72, rightAlign(3, "" + avatar_reference_.getStatsPack().current_armor_rating_));
    }

    private void renderInventory() {
    	ArrayList<Item> inventory = avatar_reference_.getInventory();
    	for (int i = 0; i < inventory.size(); i++) {
    		String item_name = inventory.get(i).name_;
    		if (item_name.length() > 22)
    			item_name = item_name.substring(0, 21);
    		if (i < 10) {
    			if (display_index)
    				writeStringToContents(19+i, 2, "" + (char)(48+i));
    			writeStringToContents(19+i, 4, item_name);
    		} else if (i < 12) {
    			if (display_index)
    				writeStringToContents(19+i, 2, "" + (char)(97+i));
    			writeStringToContents(19+i, 4, item_name);
    		} else if (i < 24) {
    			if (display_index)
    				writeStringToContents(19+i, 2, "" + (char)(97+i));
    			writeStringToContents(19+i, 30, item_name);
    		} else if (i < 36) {
    			if (display_index)
    				writeStringToContents(19+i, 2, "" + (char)(97+i));
    			writeStringToContents(19+i, 56, item_name);
    		}
    	}
    }

	@Override
	public boolean getInput(char c) {
		// TODO Auto-generated method stub
		return false;
	}
		
}


