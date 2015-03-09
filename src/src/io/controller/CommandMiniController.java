package src.io.controller;

import java.util.Scanner;

import src.enumHandler;

public class CommandMiniController {
	KeyRemapper remap_  = null;
	public CommandMiniController(KeyRemapper remap) {
		remap_ = remap;
	}

	public String processCommand(String foo) {
		if(!foo.startsWith("/")){System.err.println("This isn't a command!"); return "Error in the CommandMini";}
		if(foo.startsWith("/rebind")){return this.processRebind(foo);}
		if(foo.startsWith("/save")){return this.processSave(foo);}
		if(foo.startsWith("/load")){return this.processLoad(foo);}
		if(foo.startsWith("/save-controls")){return this.processSaveControls(foo);}
		if(foo.startsWith("/load-controls")){return this.processLoadControls(foo);}
		if(foo.startsWith("/commands")){return this.processCommands();}
		if(foo.startsWith("/help")){return this.processHelp();}
		if(foo.startsWith("/pwd")){return System.getProperty("user.dir");}

		return "No valid command given!";
	}

	private String processLoad(String foo) {
		// TODO Auto-generated method stub
		return "Not implemented yet";
	}

	private String processLoadControls(String foo) {
		// TODO Auto-generated method stub
		return "Not implemented yet";
	}

	private String processSaveControls(String foo) {
		// TODO Auto-generated method stub
		return "Not implemneted yet";
	}

	private String processCommands() {
		return enumHandler.getAllEnums();
		//The output box seems a bit bugged here....
		//Scrolling horizontally horrifically garbles the text. This should be avoided.
	}

	private String processHelp() {
		return "Type /command to list all commands" +System.lineSeparator()+
				"Type /save filename to save" + System.lineSeparator() +
				"TYpe /save-controls filename to save the current control layout" + System.lineSeparator()+
				"Note that both save games will save with a date format if no filename is given" + System.lineSeparator()+
				"Type /rebind command char to rebind a key" + System.lineSeparator()+
				"Type /load filename to load a file" + System.lineSeparator()+
				"Type /load-controls filename to load the current controls" + System.lineSeparator()+
				"Type /help for this dialog"+System.lineSeparator()+
				"Type /pwd to print the directory the game will look in";
	}

	private String processRebind(String foo) {
		String error = "Invalid rebind command given, " + System.lineSeparator()+ 
				"Format is : /rebind COMMAND char" + System.lineSeparator() +
				"Example : /rebind MOVE_UP u" + System.lineSeparator()+
				"For commands available for rebinding, type /commands , or type /help for help.";
		Scanner sc = new Scanner(foo);

		String command;
		char c= '\0';
		try{
			sc.next();
			command = sc.next();
			String temp = sc.next();
			if(temp.length() != 1){sc.close(); return error;}
			c = temp.charAt(0);
			remap_.bind(c, enumHandler.stringToEnum(command));
		}catch(Exception e){ sc.close(); return error;}
		sc.close();
		return "Success, Rebound : " + command + " To " + String.valueOf(c);
	}

	private String processSave(String foo) {
		// TODO Auto-generated method stub
		return "Not implemented yet";
	}

}
