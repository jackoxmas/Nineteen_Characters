package src.view;
import java.io.Serializable;

/**
 * Abstract view class that the views inherit from.
 * @author Matthew B, JohnReedLOL
 */
abstract class Viewport implements Serializable {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("View", 35);
    public static final int length_=20;
    public static final int width_=20;
	
	private char[][] view_contents_;
	
	/**
	 * returns the contents of a view as a 2D array
	 * @return the 2D array of characters that represents the view
	 */
	public char[][] getContents() {
	initGuard();
	return this.view_contents_;	
	}
	private void initGuard(){
		if(view_contents_ == null){view_contents_=new char[length_][width_];
			for(int j = 0; j!=width_;++j){
				for(int i = 0; i!=length_;++i){
					{view_contents_[i][j]=' ';}
				}	
			}
		}
	}
	/**
	 * Writes the string given into view from the given starting coords. 
	 * @returns false if not enough room/invalidposition
	 * @param int x, int y, String in. Starting coords, and string to write.
	*/
	protected boolean writeStringToContents(int x, int y, String in) {
		initGuard();
		if(x+in.length()>= length_){return false;}
		if(y>=width_) {return false;}
		if(x<0 || y < 0){return false;}
		for(int i = 0; i!=in.length();++i){view_contents_[x+i][y] = in.charAt(i);}
		return true;
	}
	  /**
     * @returns false if not enough room /invalid position
     * @param int x, int y, int length, int width. Starting position + size.
     *
     */
    protected boolean makeSquare(int x, int y, int length, int width){
    	initGuard();
    	//Bounds checking
    	if(x+length >= length_) {return false;}
    	if(y+width >= width_){return false;}
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
  

	
}
