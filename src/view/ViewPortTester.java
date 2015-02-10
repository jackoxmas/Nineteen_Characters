package src.view;


public class ViewPortTester extends Viewport{

	
	private static final long serialVersionUID = 8163676123852178045L;

	public ViewPortTester() {
		if(!makeSquare(5,6,6,6)){System.out.println("Error2123");}
		writeStringToContents(10,0,"Super");
		for(int j = 0; j!=width_;++j){
			for(int i = 0; i!=length_;++i){
				{System.out.print(getContents()[i][j]);}
			}
			System.out.println();
			
		}
		
	}
}