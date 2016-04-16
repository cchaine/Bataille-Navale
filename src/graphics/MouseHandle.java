package graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import state.StateManager;

public class MouseHandle implements MouseListener{

	public StateManager stateManager;
	
	public MouseHandle(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		stateManager.getCurrentState().mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		stateManager.getCurrentState().mouseReleased(e);
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
