/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.io.Serializable;
import java.util.Arrays;

import src.controller.Avatar;
import src.model.MapDisplay_Relation;

/**
 * Represents a single player's display
 *
 * @author Matthew B, JohnReedLOL
 */
public class Display implements Serializable {

    // Converts the class name into a base 35 number

    private static final long serialVersionUID = Long.parseLong("Display", 35);
    /**
     * Create a display from a Viewport
     * @author Matthew B
     * @param Viewport
     * @return Display
     */
    public Display(Viewport _view){
    	current_view_ = _view;
    }
    private Viewport current_view_;
    /**
     * Print the currently held view
     * 
     */
    public void printView() {
    	current_view_.renderToDisplay();
    	this.clearScreen();
        char[][] in = current_view_.getContents();
        // Use this to print a 2D array
		for(int j = 0; j!=current_view_.height_;++j){
			for(int i = 0; i!=current_view_.width_;++i){
				{System.out.print(in[i][j]);}
			}
			System.out.print(System.lineSeparator());
		}
    }
    private void clearScreen(){
    	//Create the illusion of clearing the screen.
    	for(int i = 0; i!=2*current_view_.height_;++i){
    		System.out.print(System.lineSeparator());
    	}
    }
    /**
     * Change the viewport held by the display
     * @author Matthew B
     * @param Viewport
     * @return Display
     */
    public void setView(Viewport _view){
    	current_view_ = _view;
    }
    /*
     * Does nothing atm, int incase we want to return error codes later.
     */
    public int open(){
    	return 1;
    }
    /*
     * Does nothing atm, int incase we want to return error codes later.
     */
    public int close(){
    	return 0;
    }
}
