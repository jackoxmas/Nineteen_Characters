/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view;

import java.io.Serializable;

/**
 * Represents a single player's display. Has a static game wide message.
 *
 * @author Matthew B, JohnReedLOL
 */
public class Display implements Serializable {

    // Converts the class name into a base 35 number
	private static String message_ = "";
	private static int counter_ = 0;
	/* 
	 * Static method, sets to what is being output the given string, for counter frames
	 * Note that is handles multiline strings, but pushes the view up for each line.
	 * Don't abuse please
	 * @params String m : The message string, int counter : The frames to display it for
	 */
	public static void setMessage(String m, int counter){
		message_ = m;
		counter_ = counter;
	}
    private static final long serialVersionUID = Long.parseLong("Display", 35);
    /**
     * Create a display from a Viewport
     * @author Matthew B
     * @param Viewport
     * @return Display
     */
    /* Constructor, requires the view to render. 
     * A display without a view might as well not exist.
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
		if(counter_ > 0){System.out.println(message_);--counter_;}
    }
    /* 
     * Helper method to handle 'clearing' the screen
     */
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
