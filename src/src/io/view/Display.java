/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.io.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.InputStream;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Represents a single player's display. Has a static game wide message.
 *
 * @author Matthew B, JohnReedLOL
 */
public class Display {

    // Converts the class name into a base 35 number
	private static String message_ = "";
	private static int counter_ = 0;
	static private Display display_ = null;
	private JTextPane pane_ = null;
	private JFrame frame_ = null;
	private float fontSize = 20f;
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
    private void setFont(){
    	Font font = getFont();
    	if(font == null){return;}//If we failed to load the font, do nothing
    	Font  resized = font.deriveFont(fontSize);//This line sets the size of the game, not sure how to make it dynamic atm
    	pane_.setFont(resized);
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
    	frame_.setBounds(0, 0, (int)fontSize*60, (int)fontSize*60); //Arbitary, but whatever.
    	pane_ = new JTextPane();
    	setFont();
    	pane_.setEditable(false);
    	StyledDocument doc = pane_.getStyledDocument();
    	try{
    	doc.insertString(0, "test", null);
    	}
    	catch(Exception x){
    		
    	}
    	frame_.add(pane_);
    	frame_.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    	frame_.setVisible(true);
    	frame_.setFocusable(true);

    }
    public void requestFocus(){
    	frame_.requestFocus();
    }
    static public Display getDisplay(){
    	if (display_ == null){
    		display_ = new Display();
    	}
    	return display_;
    }
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
    /**
     * Print the currently held view
     * 
     */
    public void printView() {
    	if(guard()){return;}
    	this.clearScreen();
        char[][] in = current_view_.getContents();
        StringBuilder out = new StringBuilder();
    		// Use this to print a 2D array
    		for(int j = 0; j!=current_view_.height_;++j){
    			for(int i = 0; i!=current_view_.width_;++i){
    				out.append(in[i][j]);
    			}
    			out.append(System.lineSeparator());
    		}
    		StyledDocument doc = pane_.getStyledDocument();
    		try{
    		doc.insertString(0,out.toString(),null);
    		}
    		catch(Exception e){System.err.println(e.toString());}
    		
		
		if(counter_ > 0){System.out.println(message_);--counter_;}
    }
    /**
     * Example of how to make a char that is printed a color
     */
    private void makeSamplePink(){
    	MutableAttributeSet attributes = new SimpleAttributeSet();
    	StyleConstants.setForeground(attributes, Color.pink);//Pink is easy to notice, good for debugging
    	//Can do a variaty of things aside from color...
    	colorChar(0,2,attributes);
    }
    /** 
     * Make the char at this point take on the given attributes.
     * @param x
     * @param y
     * @param attr
     */
    private void colorChar(int x, int y, AttributeSet attr){
    	pane_.getStyledDocument().setCharacterAttributes(y*(Viewport.width_+1)+x, 1, attr, false);
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
	public void addKeyListener(KeyListener listener) {
		frame_.addKeyListener(listener);	
	}
	public void addMouseListener(MouseListener listener) {
		frame_.addMouseListener(listener);	
	}
	public void addFocusListener(FocusListener listener) {
		frame_.addFocusListener(listener);	
	}
}
