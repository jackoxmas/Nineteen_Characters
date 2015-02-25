package src.io;

import java.util.ArrayList;

import src.io.view.Viewport;
import src.model.Vector2;
/* 
 * Another (useless) testing class, goes through the basic functions of ViewPort to ensure they work.
 */

public class ViewPortTester extends Viewport {

	
	private static final long serialVersionUID = 8163676123852178045L;

	public ViewPortTester() {
		Vector2 A = new Vector2(1,10);
		Vector2 B = new Vector2(37,8);
		if(!makeSquare(0,0,39,19)){System.out.println("Size Error");}
		if(!makeSquare(A,B)){System.out.println("Size Error");}
		//Example of nested squares.
		ArrayList<String> temp = getAsciiArtFromFile("src/view/ASCIIART/stats.txt");
		for(int i = 0; i!=temp.size();++i){
		
				writeStringToContents(new Vector2(1,i+1),temp.get(i));
		}
	}

	@Override
	public void renderToDisplay() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getInput(char c) {
		// TODO Auto-generated method stub
		return false;
	}
}