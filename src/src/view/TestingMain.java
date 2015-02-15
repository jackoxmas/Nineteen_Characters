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
			for(int i = 0; i!=view.width_;++i){
				{System.out.print(in[i][j]);}
			}
			System.out.println();
		}
	}
	public static void oldtest(){
		Avatar avatar = new Avatar("avatar", 'x', 0, 0);
		ViewPortTester Tester = new ViewPortTester();
		printArray(Tester);
		System.out.println("Done with viewportTester, ccviewtime!");
		Display _display = new Display(new AvatarCreationView(avatar));
		_display.printView();
	}
	public static void main(String[] args) {

		// TODO Auto-generated method stub
		Avatar avatar = new Avatar("avatar", 'x', 0, 0);
		Display _display = new Display(avatar.getMyView());
		avatar.getMyView().messageBox("This is a test of the emergency broadcasting system.",1);
		_display.printView();
		_display.printView();
		avatar.getMyView().getInput('C');
		_display.setView(avatar.getMyView());
		_display.printView();
		
		System.out.println(System.getProperty("java.class.path"));

	}

}
