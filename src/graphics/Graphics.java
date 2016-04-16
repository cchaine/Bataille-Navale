package graphics;

import java.awt.image.BufferStrategy;
import state.StateManager;

public class Graphics{

	private Display display;
	
	private BufferStrategy bufferStrategy;
	private java.awt.Graphics g;
	
	private MouseHandle mouse;
	private KeyHandle key;
	
	private StateManager stateManager;
	
	public Graphics(String title, int width, int height, StateManager stateManager)
	{
		this.stateManager = stateManager;
		mouse = new MouseHandle(stateManager);
		key = new KeyHandle(stateManager);
		
		display = new Display(title, width, height);
		display.createDisplay();
		display.addMouseListener(mouse);
		display.addKeyListener(key);
	}
	
	public void update()
	{
		stateManager.getCurrentState().update();
	}
	
	public void render()
	{
		bufferStrategy = display.getCanvas().getBufferStrategy();
		if(bufferStrategy == null)
		{
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, Display.width, Display.height);
		
		stateManager.getCurrentState().render(g);
		
		bufferStrategy.show();
		g.dispose();
	}
	
	public Display getDisplay()
	{
		return display;
	}
}
