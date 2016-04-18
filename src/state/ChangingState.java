package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import utils.AssetLoader;
import graphics.Display;

public class ChangingState extends State{

	private StateManager stateManager;
	private boolean continuePressed = false;
	
	private int x, y;
	
	public ChangingState(StateManager stateManager) {
		this.stateManager = stateManager;
		
	}
	
	@Override
	public void update() {
		x = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		y = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10, 1180, 580);
		g.setColor(Color.WHITE);
		g.setFont(AssetLoader.helvetica30);
		g.drawString("C'est au tour de " + stateManager.getCore().getPlayers().get(1 - stateManager.getCurrentPlayer()).getName() + "...", (int)(600 - g.getFontMetrics().getStringBounds("C'est au tour de " + stateManager.getCore().getPlayers().get(1 - stateManager.getCurrentPlayer()).getName() + "...", g).getWidth() / 2), 200);
		if (continuePressed) {
			g.drawImage(AssetLoader.continueButtonPressed, 550, 250, 100, 100, null);
		} else if (x > 551 && x < 650 && y > 273 && y < 374) {
			g.drawImage(AssetLoader.continueButtonHovered, 550, 250, 100, 100, null);
		} else {
			g.drawImage(AssetLoader.continueButton, 550, 250, 100, 100, null);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(x + " " + y);
		
		if (x > 551 && x < 650 && y > 273 && y < 374) {
			continuePressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (x > 551 && x < 650 && y > 273 && y < 374) {
			stateManager.changePlayers();
		}
		continuePressed = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
