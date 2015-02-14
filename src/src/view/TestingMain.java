package src.view;

import src.controller.Avatar;

public class TestingMain {

	public TestingMain() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void printArray(Viewport view){
		char[][] in = view.getContents();
		for(int j = 0; j!=view.height_;++j){
			for(int i = 0; i!=view.length_;++i){
				{System.out.print(in[i][j]);}
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Avatar avatar = new Avatar("avatar", 'x', 0, 0);
		ViewPortTester Tester = new ViewPortTester();
		printArray(Tester);
		System.out.println("Done with viewportTester, ccviewtime!");
		Display _display = new Display(new AvatarCreationView(avatar));
		_display.printView();

	}

}
