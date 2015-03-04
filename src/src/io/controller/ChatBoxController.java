package src.io.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.Function;
import src.io.view.Display;

public final class ChatBoxController implements Function<Void, String> {
	public ChatBoxController(){
	Display.getDisplay().addChatBoxFunctionEvent(this);
	}
	




	@Override
	public Void apply(String foo) {
		// React to text from chatbox here
		System.out.println("Chatboooox was "+foo);
		return null;
	}

}
