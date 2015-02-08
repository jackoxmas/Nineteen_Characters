package src.view;
import java.io.Serializable;

/**
 * Abstract view class that the views inherit from.
 * @author Matthew B, JohnReedLOL
 */
abstract class Viewport implements Serializable {

    // Converts the class name into a base 35 number
    private static final long serialVersionUID = Long.parseLong("View", 35);
    public static final int length=40;
    public static final int width=80;
	
	private char[][] view_contents_;
	
	/**
	 * returns the contents of a view as a 2D array
	 * @return the 2D array of characters that represents the view
	 */
	public char[][] getContents() {
	return this.view_contents_;	
	}
}
