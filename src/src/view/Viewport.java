package src.view;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import src.Vector2;
import src.model.MapDisplay_Relation;
import src.model.MapDrawableThing_Relation;

/**
 * Abstract view class that the views inherit from.
 * @author Matthew B, JohnReedLOL
 */
public abstract class Viewport implements Serializable {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("View", 35);
    public static final int height_=40;
    public static final int width_=80;
	private char[][] view_contents_;
	public abstract boolean getInput(char c);
	public Viewport(){
		map_relationship_ = new MapDisplay_Relation(this);
		view_contents_ = new char[width_][height_];
	}
	protected MapDisplay_Relation map_relationship_;
	    
	    /**
	     * Use this to call functions contained within the MapDrawable relationship
	     * @return map_relationship_
	     * @author M Bregg
	     */
    public MapDisplay_Relation getMapRelation() {
        return map_relationship_;
    }
	/**
	 * Tells the view to update it's array contents. 
	 */
	public abstract void renderToDisplay();
	/**
	 * returns the contents of a view as a 2D array
	 * @return the 2D array of characters that represents the view
	 */
	public char[][] getContents() {
	initGuard();
	return this.view_contents_;	
	}
	/**
	 * Load in ascii art from file
	 * @return Array list of the strings of the ascci art
	 * @param file path
	 * @example getAsciiArtFromFile("src/view/ASCIIART/stats.txt");
	 * @exception Prints to error line and returns empty ArrayList in event of failure.
	 */
	public ArrayList<String> getAsciiArtFromFile(String input){
		Path path = Paths.get(input);
		path = path.toAbsolutePath();
		ArrayList<String> art = new ArrayList<String>();
		try {
            InputStream in = Files.newInputStream(path);
		    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		        art.add(line);
		    }
		} catch (IOException x) {
		    System.err.println(x); //Sure, that works. 
		}
		
		return art;

	
	}
	protected void clear(){
		if(view_contents_==null){return;}//Avoid doing this on null array.
		for(int j = 0; j!=height_;++j){
			for(int i = 0; i!=width_;++i){
				{view_contents_[i][j]=' ';}
			}	
		}
	}
	private void initGuard(){
		if(view_contents_ == null){view_contents_=new char[width_][height_];
			clear();
		}
	}
	
	/**
	 * Right justifies a string
	 * @author Jack
	 */
	protected String rightAlign(int length, String text) {
		if (length < text.length())
			return text;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length-text.length(); i++)
			sb.append(' ');
		return sb.toString() + text;
	}
	
	/**
	 * Writes the string given into view from the given starting coords. 
	 * @returns false if not enough room/invalidposition
	 * @param int x, int y, String in. Starting coords, and string to write.
	 * 
	*/
	protected boolean writeStringToContents(int x, int y, String in) {
		initGuard();
		if(x+in.length()>= width_){return false;}
		if(y>=height_) {return false;}
		if(x<0 || y < 0){return false;}
		for(int i = 0; i!=in.length();++i){view_contents_[x+i][y] = in.charAt(i);}
		return true;
	}
	/**
	 * Writes the string given into view from the given starting coords. 
	 * @returns false if not enough room/invalidposition
	 * @param Vector2 coord, String in. Starting coords, and string to write.
	 * 
	*/
	protected boolean writeStringToContents(Vector2 coord, String in){
		return writeStringToContents(coord.x(),coord.y(),in);
	}
	  /**
     * @returns false if not enough room /invalid position
     * @param int x, int y, int length, int width. Starting position + size.
     *
     */
    protected boolean makeSquare(int x, int y, int length, int width){
    	initGuard();
    	//Bounds checking
    	if(x+length >= width_) {return false;}
    	if(y+width >= height_){return false;}
    	if(x<0 || y < 0){return false;}
    	//Begin filling the square
    	for(int i = x+1; i<x+length;++i){//offset by one to handle corners
    		view_contents_[i][y] = '═';
    		view_contents_[i][y+width] = '═';
    	}
    	for(int i = y+1; i<y+width;++i){//see above
    		view_contents_[x][i] = '║';
    		view_contents_[x+length][i] = '║';
    	}
    	//Corner time
    	view_contents_[x+length][y+width] = '╝';
    	view_contents_[x+length][y] = '╗';
    	view_contents_[x][y+width] = '╚';
    	view_contents_[x][y] = '╔';
    	return true;
    	
    }
	  /**
     * @returns false if not enough room /invalid position
     * @param Vector2 coord, int length, int width. Starting position + size.
     *
     */
  protected boolean makeSquare(Vector2 coord, Vector2 size){
	  return makeSquare(coord.x(),coord.y(),size.x(),size.y());
  }

	
}
