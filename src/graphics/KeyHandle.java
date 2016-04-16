package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import state.StateManager;

public class KeyHandle implements KeyListener{

	public StateManager stateManager;
	
	public KeyHandle(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		stateManager.getCurrentState().keyPressed(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		stateManager.getCurrentState().keyReleased(e);
		
	}

}
