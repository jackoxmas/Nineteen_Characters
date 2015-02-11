package src.view;

import java.util.ArrayList;


public class ViewPortTester extends Viewport{

	
	private static final long serialVersionUID = 8163676123852178045L;

	public ViewPortTester() {
		if(!makeSquare(0,0,39,19)){System.out.println("Size Error");}
		if(!makeSquare(1,10,37,8)){System.out.println("Size Error");}
		//Example of nested squares.
		ArrayList<String> temp = getAsciiArtFromFile("src/view/ASCIIART/stats.txt");
		for(int i = 0; i!=temp.size();++i){
			writeStringToContents(1,i+1,temp.get(i));
		}
	}

	@Override
	public void renderToDisplay() {
		// TODO Auto-generated method stub
		
	}
}