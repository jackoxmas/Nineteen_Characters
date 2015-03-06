/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view.display;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelListener;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import src.Function;
import src.io.view.Viewport;

/**
 * Represents a single player's display. Has a static game wide message.
 *
 * @author Matthew B, JohnReedLOL
 */
public class Display {
	/**
	 * The chatbox.
	 * It is it's own listener, and on the enter keypress, it flushes the input box into the output. 
	 * Has methods to add messages, and can take in anything that implements the Function interface
	 * Things passed in that implement Function interface are called with a String containing the contents of input, 
	 * before it is cleared when input is hit. 
	 * @author Mbregg
	 *
	 */

    // Converts the class name into a base 35 number
	static private Display display_ = null;
	private JTextPane pane_ = null;
	private JFrame frame_ = null;
	private float fontSize_ = 14f;

	/**
	 * Puts the given message in the chatboxes output box
	 * @param m The string to output
	 */
	public void setMessage(String m){
		EventQueue.invokeLater(new messageRunnable(m));
	}
	class messageRunnable implements Runnable{
		String message_;
		public messageRunnable(String m) {
			message_ = m;
		}
		@Override
		public void run() {
			Key_Listener_GUI.getGUI().addMessage(message_);
		}
	}
    private static final long serialVersionUID = Long.parseLong("Display", 35);

    /**
     * Create a display from a Viewport
     * @author Matthew B
     * @param Viewport
     * @return Display
     */
    private Display(){
    	java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() { Key_Listener_GUI.getGUI().setVisible(true);}});

    }
    /**
     * Gets the display
     * @return Returns a reference to the Display
     */
    static public Display getDisplay(){
    	if (display_ == null){
    		display_ = new Display();
    	}
    	return display_;
    }
    /**
     * Does the same as above, but also sets the Displays view to be the given view
     * @param _view
     * @return
     */
    static public Display getDisplay(Viewport _view){
    	Display _display = getDisplay();
    	_display.setView(_view);
    	return _display;
    }
    
    private boolean guard(){
    	if (current_view_ == null){ System.err.println("DISPLAY VIEW NULL"); return true;}
    	return false;
    }
    private Viewport current_view_;
    
    private class setDocumentRunnable implements Runnable{
    	private StyledDocument doc_;
    	public setDocumentRunnable(StyledDocument d){
    		doc_ = d;
    	}
    	@Override
    	public void run() {
    		Key_Listener_GUI.getGUI().setGameContent(doc_);
    	}
    }
    /**
     * Print the currently held view
     * 
     */
    public void printView() {
    	if(guard()){return;}
        char[][] in = current_view_.getCharContents();
        Color[][] colors = current_view_.getColorContents();
        StringBuilder out = new StringBuilder();
    		// Use this to print a 2D array
    		for(int j = 0; j!=current_view_.getHeight();++j){
    			for(int i = 0; i!=current_view_.getWidth();++i){
    				if(!(Color.white.equals(colors[i][j]))){
    					out.append(in[i][j]);
    				}
    				else {out.append(' ');}//Append a space rather than coloring white. 

    			}
    			out.append(System.lineSeparator());
    		}
    		StyledDocument doc = new DefaultStyledDocument();
    		try{
    		doc.insertString(0,out.toString(),null);
    		}
    		catch(Exception e){System.err.println(e.toString());}
    		
    		for(int j = 0; j!=current_view_.getHeight();++j){
    			Color currColor = null;
    			int currColorCount = 0;
    			int oldIndex = 0;
    			/* 
    			 * What's going on here: Also: Yes, I profiled and tested, it makes a big difference.
    			 * Color assigning for a single char is inefficient, so to avoid that, since colors often appear lots in a row, we count that row up, and then render it
    			 * in one big block when the color changes. So, when currColor no longer equals the colors[i][j], it is rendered, with the oldIndex and count, 
    			 * which are then reset. 
    			 *   black is the default, no need to do that. That optimization is done in the colorChar method. 
    			 *   Right now white is ignored and replaced with a space. , since it'd just make stuff invisible.
    			 */
    			for(int i = 0; i!=current_view_.getWidth();++i){
    				if(colors[i][j] != null && colors[i][j].equals(currColor)){currColorCount++;}
    				else if(colors[i][j] == null && currColor == null){currColorCount++;}
    				else{
    					if(currColor != null){
    						colorChar(doc,oldIndex,j,currColor,currColorCount);
    					}
						oldIndex = i;
						currColorCount = 1;
						currColor = colors[i][j];
    						
    				}
    			}
			if(currColor != null){
				colorChar(doc,oldIndex,j,currColor,currColorCount);
			}
		}
    		EventQueue.invokeLater(new setDocumentRunnable(doc));
    }
   
    /** 
     * Make the char at this point take on the given attributes.
     * @param x
     * @param y
     * @param attr
     */
    private void colorChar(StyledDocument doc, int x, int y, Color color, int length){
    	if(color.equals(color.white)){return;}
    	if(color.equals(color.black)){return;}//White is only used for space, so no need to render it. 
    	MutableAttributeSet attr = new SimpleAttributeSet();
    	StyleConstants.setForeground(attr, color);
    	doc.setCharacterAttributes(y*(current_view_.getWidth()+(System.lineSeparator().length()))+x,
    			length, attr, false);
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
    public void addInputerHandler(Function<Void,Character> foo){
    	java.awt.EventQueue.invokeLater(new inputHandlerRunnable(foo));
	}
    class inputHandlerRunnable implements Runnable{
    	private Function<Void,Character> handler_;
    	public inputHandlerRunnable(Function<Void,Character> foo) {
			handler_ = foo;
		}
		@Override
		public void run() {
			Key_Listener_GUI.getGUI().addGameInputerHandler(handler_);	
		}
    	
    }
	/**
	 * Adds a Function<Void,String> object to the list of things called by chatbox on enter
	 * @param Function<Void,String> listen
	 */
	public void addChatBoxFunctionEvent(Function<Void,String> listen){
		//chat_.addFunction(listen);
	}

}
