package src.io.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import src.io.view.Display;

public final class ChatBoxController implements KeyListener {
	public ChatBoxController(){
	Display.getDisplay().addChatBoxKeyListener(this);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// Put the code here

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// DO NOT USE, this is when the chat box clears itself.

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Nothing to do here

	}

}
