package src.io.controller;

import java.util.Scanner;

import src.HardCodedStrings;
import src.enumHandler;

class CommandMiniController {
	KeyRemapper remap_  = null;
	public CommandMiniController(KeyRemapper remap) {
		remap_ = remap;
	}
	private static final String man = "man";
	private static final String pwd = "pwd";
	private static final String help = "help";
	private static final String controls = "controls";
	private static final String loadControls = "load-controls";
	private static final String saveControls = "save-controls";
	private static final String save = "save";
	private static final String load = "load";
	private static final String rebind = "rebind";
	
	
	private static final String commandKey = "/";

	public String processCommand(String foo) {
		if(!foo.startsWith(commandKey)){System.err.println("This isn't a command!"); return "Error in the CommandMini";}
		if(foo.startsWith(commandKey + rebind)){return this.processRebind(foo);}
		if(foo.startsWith(commandKey + save)){return this.processSave(foo);}
		if(foo.startsWith(commandKey + load)){return this.processLoad(foo);}
		if(foo.startsWith(commandKey + saveControls)){return this.processSaveControls(foo);}
		if(foo.startsWith(commandKey + loadControls)){return this.processLoadControls(foo);}
		if(foo.startsWith(commandKey + controls)){return this.processCommands();}
		if(foo.startsWith(commandKey + help)){return this.processHelp();}
		if(foo.startsWith(commandKey + pwd)){return System.getProperty("user.dir");}
		if(foo.startsWith(commandKey + "cat")){return "meow";}
		if(foo.startsWith(commandKey + "tiger")){return "ROAR!";}
		if(foo.startsWith(commandKey + man)){return this.processManCommand(foo);}

		return "No valid command given!";
	}

	private String processManCommand(String foo) {
		Scanner sc = new Scanner(foo);
		String in = "";
		try{
			sc.next(); //Get rid of the command /man
			in = sc.next();
		}
		catch(Exception e){sc.close(); return HardCodedStrings.command_error;}
		try{
			if(in.equals(rebind)){return HardCodedStrings.rebindHelp;}
			if(in.equals(save)){return HardCodedStrings.saveHelp;}
			if(in.equals(load)){return HardCodedStrings.loadHelp;}
			if(in.equals(saveControls)){return HardCodedStrings.saveControlsHelp;}
			if(in.equals(loadControls)){return HardCodedStrings.loadControlsHelp;}
			if(in.equals(controls)){return HardCodedStrings.controlsHelp;}
			if(in.equals(help)){return HardCodedStrings.helpHelp;}
			if(in.equals(pwd)){return HardCodedStrings.pwdHelp;}
			if(in.equals(man)){return HardCodedStrings.manHelp;}
		}
		finally{
			sc.close();
		}
		return HardCodedStrings.command_error;
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
		return HardCodedStrings.help;
	}

	private String processRebind(String foo) {
		String error = HardCodedStrings.command_error + System.lineSeparator()+HardCodedStrings.rebindHelp;
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
