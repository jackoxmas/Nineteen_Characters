/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view;

import java.awt.Color;
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
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import src.Function;

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
	private class ChatBox implements KeyListener{
		private JTextField inputBox_;
		private JTextArea outputBox_;
		private JScrollPane scroll_;
		private int width_ = 100;
		private ArrayList<Function<Void,String>> functions_ = new ArrayList<Function<Void,String>>();
		public ChatBox(JFrame frame_){
			inputBox_ = new JTextField(width_);
			outputBox_ = new JTextArea(7, width_);
			outputBox_.setEditable(false);
			JScrollPane scroll = new JScrollPane(outputBox_);

			frame_.add(scroll);			
	     	frame_.add(inputBox_);
	     	inputBox_.addKeyListener(this);
		}
		private void updateScroll(){
			outputBox_.setCaretPosition(outputBox_.getText().length());
		}
		/**
		 * Puts a string in the output box
		 * @param message The string to display in a new line
		 */
		public void addMessage(String message){
			outputBox_.append(System.lineSeparator()+message);
			updateScroll();
		}
		/** 
		 * Sets the font, including font size.
		 * Used for scrolling, and is kept in sync with displays font.
		 */
		public void setFont(){
			Display.getDisplay().setFont(inputBox_);
			Display.getDisplay().setFont(outputBox_);
		}
		/** 
		 * On key press
		 * run through all the Function objects, calling apply
		 */
		@Override
		public void keyPressed(KeyEvent e) {
			// Run through the functions we were given
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				String S = inputBox_.getText();
				for(Function<Void,String> functor : functions_){
					functor.apply(S);
				}
			}
			
		}
		/**
		 * On key release, put the input text into the output box.
		 */
		@Override
		public void keyReleased(KeyEvent e) {
			//On release,  
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				outputBox_.append(System.lineSeparator()+inputBox_.getText());
				inputBox_.setText("");//Upon enter, clear the input box, and move it's text to output
				updateScroll();
			}	

		}
		@Override
		public void keyTyped(KeyEvent e) {
			//Nothing to do here.

			
		}
		/**
		 * Adds a Function to the list.
		 * @param listen
		 */
		public void addFunction(Function<Void,String> listen){
			functions_.add(listen);
		}
	}
    // Converts the class name into a base 35 number
	static private Display display_ = null;
	private JTextPane pane_ = null;
	private JFrame frame_ = null;
	private float fontSize_ = 18f;
	private ChatBox chat_;

	/**
	 * Puts the given message in the chatboxes output box
	 * @param m The string to output
	 */
	public void setMessage(String m){
		chat_.addMessage(m);
	}
    private static final long serialVersionUID = Long.parseLong("Display", 35);
    private Font getFont(){
    	InputStream in = this.getClass().getResourceAsStream("Font/DejaVuSansMono.ttf");
    	try{
    		return Font.createFont(Font.TRUETYPE_FONT, in);
    	}
    	catch(Exception e){
    		System.err.println(e.toString());
    		return null;
    	}
    }
    private void setFont(JComponent object){
    	Font font = getFont();
    	if(font == null){return;}//If we failed to load the font, do nothing
    	Font  resized = font.deriveFont(fontSize_);//This line sets the size of the game, not sure how to make it dynamic atm
    	object.setFont(resized);
    	return;
    }
    /**
     * Create a display from a Viewport
     * @author Matthew B
     * @param Viewport
     * @return Display
     */
    private Display(){
    	 frame_ = new JFrame("NineTeen Characters");
    	frame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame_.setJMenuBar(new JMenuBar());
    	frame_.setBounds(0, 0, (int)fontSize_*60, (int)fontSize_*60); //Arbitary, but whatever.
    	pane_ = new JTextPane();
    	setFont(pane_);

    	frame_.getContentPane().setLayout(new FlowLayout());
    	frame_.add(pane_);
    	chat_ = new ChatBox(frame_);
    	frame_.setExtendedState(JFrame.MAXIMIZED_BOTH); 

    	pane_.setEditable(false);
    	frame_.setVisible(true);
    	frame_.setFocusable(true);
    	pane_.requestFocus();

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
    /** 
     * Zooms in slightly(increases font size)
     */
    public void zoomIn(){
    	fontSize_++;
    	setFont(pane_);
    	chat_.setFont();
    }
    /** 
     * Zooms out slightly(Decreases font size)
     */
    public void zoomOut(){
    	if(fontSize_ < 2){return;}//Don't let the font get too small!
    	fontSize_--;
    	setFont(pane_);
    	chat_.setFont();
    }
    private boolean guard(){
    	if (current_view_ == null){ System.err.println("DISPLAY VIEW NULL"); return true;}
    	return false;
    }
    private Viewport current_view_;
    /**
     * Print the currently held view
     * 
     */
    public void printView() {
    	if(guard()){return;}
    	this.clearScreen();
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
    		StyledDocument doc = pane_.getStyledDocument();
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
    						colorChar(oldIndex,j,currColor,currColorCount);
    					}
						oldIndex = i;
						currColorCount = 1;
						currColor = colors[i][j];
    						
    				}
    			}
			if(currColor != null){
				colorChar(oldIndex,j,currColor,currColorCount);
			}
		}
		
    }
   
    /** 
     * Make the char at this point take on the given attributes.
     * @param x
     * @param y
     * @param attr
     */
    private void colorChar(int x, int y, Color color, int length){
    	if(color.equals(color.white)){return;}
    	if(color.equals(color.black)){return;}//White is only used for space, so no need to render it. 
    	MutableAttributeSet attr = new SimpleAttributeSet();
    	StyleConstants.setForeground(attr, color);
    	pane_.getStyledDocument().setCharacterAttributes(y*(current_view_.getWidth()+1)+x, length, attr, false);
    }
    /** 
     * Helper method to handle 'clearing' the screen
     */
    private void clearScreen(){
    	pane_.setText("");
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
    /**
     * Add a Keylistener to the main game pane
     * 
     * @param listener
     */
	public void addGameKeyListener(KeyListener listener) {
		pane_.addKeyListener(listener);	
	}
	/** 
	 * Adds a Mouselistener to the main game pane
	 * @param listener
	 */
	public void addGameMouseWheelListener(MouseWheelListener listener){
		pane_.addMouseWheelListener(listener);
	}
	/**
	 * Adds a Function<Void,String> object to the list of things called by chatbox on enter
	 * @param Function<Void,String> listen
	 */
	public void addChatBoxFunctionEvent(Function<Void,String> listen){
		chat_.addFunction(listen);
	}

}
