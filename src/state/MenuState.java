package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.MouseInfo;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import graphics.AssetLoader;
import graphics.Display;
import sun.applet.Main;

public class MenuState extends State {

	private boolean playPressed = false;
	private boolean helpPressed = false;
	private boolean quitPressed = false;
	private String playStr = "jouer";
	private String helpStr = "aide";
	private String quitStr = "quitter";
	private String onePlayerStr = "joeuur vs ordinateur";
	private String twoPlayerStr = "joueur vs joueur";
	private boolean onePlayerPressed = false;
	private boolean twoPlayerPressed = false;

	private int wave1X = 0;
	private int wave2X = 1200;
	private int wave3X = 0;
	private int wave4X = 1200;

	private float timeEvolution = 0;
	private float boatX = 20, boatY = 400;
	private float buttonOffset = 600;
	private float buttonAlpha = 0;
	private boolean isPlay = false;
	private boolean isHelp = false;
	
	private StateManager stateManager;

	private int x, y;

	public MenuState(StateManager stateManager) {
		this.stateManager = stateManager;
	}

	public void update() {
		x = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		y = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;

		timeEvolution += 0.1f;
		boatY -= Math.cos(timeEvolution) / 3;

		if (buttonOffset > 250 && timeEvolution > 0.8) {
			buttonOffset -= Math.pow(2, buttonOffset / 130);
		} else if (buttonOffset <= 250) {
			buttonOffset = 250;
		}

		if (buttonAlpha >= 165) {
			buttonAlpha = 170;
		} else if (buttonAlpha < 255 && buttonOffset < 400) {
			buttonAlpha += Math.pow(2, buttonOffset / 130);
		}
		
		if(wave1X <= -1200)
		{
			wave1X = 1198;
		}else if(wave1X > -1200)
		{
			wave1X -= 2;
		}
		if(wave2X <= -1200)
		{
			wave2X = 1198;
		}else if(wave2X > -1200)
		{
			wave2X -= 2;
		}
		if(wave3X <= -1200)
		{
			wave3X = 1198;
		}else if(wave3X > -1200)
		{
			wave3X -= 1;
		}
		if(wave4X <= -1200)
		{
			wave4X = 1198;
		}else if(wave4X > -1200)
		{
			wave4X -= 1;
		}

	}

	public void render(java.awt.Graphics g) {
		g.drawImage(AssetLoader.waves, wave3X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave4X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.boat, (int) boatX, (int) boatY, 400, 200, null);
		g.drawImage(AssetLoader.waves, wave1X, 535, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave2X, 535, 1200, 100, null);
		
		g.setFont(AssetLoader.junebug60);
		g.setColor(new Color(0x002366));
		g.drawString("BATAILLE NAVALE", (int)(600 - g.getFontMetrics().getStringBounds("BATAILLE NAVALE", g).getWidth() / 2), 100);

		g.setFont(AssetLoader.junebug50);

		if (isPlay) {
			if (onePlayerPressed && buttonOffset == 250) {
				onePlayerStr = "JOUEUR VS ORDINATEUR";
				g.setColor(new Color(0x76FF78));
			} else if (x > 271 && x < 1118 && y > 233 && y < 280 && buttonOffset == 250) {
				onePlayerStr = "JOUEUR VS ORDINATEUR";
				g.setColor(new Color(0x002366));
			} else {
				onePlayerStr = "joueur vs ordinateur";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}
			g.drawString(onePlayerStr, 265, (int) buttonOffset);

			if (twoPlayerPressed && buttonOffset == 250) {
				twoPlayerStr = "JOUEUR VS JOUEUR";
				g.setColor(new Color(0x76FF78));
			} else if (x > 272 && x < 939 && y > 344 && y < 390 && buttonOffset == 250) {
				twoPlayerStr = "JOUEUR VS JOUEUR";
				g.setColor(new Color(0x002366));
			} else {
				twoPlayerStr = "joueur vs joueur";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}
			g.drawString(twoPlayerStr, 265, (int) buttonOffset + 110);
		} else {

			if (playPressed && buttonOffset == 250) {
				playStr = "JOUER";
				g.setColor(new Color(0x68D16A));
			} else if (x > 487 && x < 695 && y > 234 && y < 280 && buttonOffset == 250) {
				playStr = "JOUER";
				g.setColor(new Color(0x002366));
			} else {
				playStr = "jouer";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}
			g.drawString(playStr, 480, (int) buttonOffset);

			if (quitPressed && buttonOffset == 250) {
				quitStr = "QUITTER";
				g.setColor(new Color(0xE72623));
			} else if (x > 444 && x < 744 && y > 396 && y < 435 && buttonOffset == 250) {
				quitStr = "QUITTER";
				g.setColor(new Color(0x002366));
			} else {
				quitStr = "quitter";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}

			g.drawString(quitStr, 440, (int) buttonOffset + 160);
		}
	}

	public void mousePressed(MouseEvent e) {
		System.out.println(x + " " + y);
		if (buttonOffset == 250) {
			if (isPlay) {
				if (x > 271 && x < 1118 && y > 233 && y < 280) {
					onePlayerPressed = true;
				} else {
					onePlayerPressed = false;
				}

				if (x > 272 && x < 939 && y > 344 && y < 390) {
					twoPlayerPressed = true;
				} else {
					twoPlayerPressed = false;
				}
			} else {
				if (x > 487 && x < 695 && y > 234 && y < 280) {
					playPressed = true;
				} else {
					playPressed = false;
				}

				if (x > 444 && x < 744 && y > 396 && y < 435) {
					quitPressed = true;
				} else {
					quitPressed = false;
				}
			}
		}

	}

	public void mouseReleased(MouseEvent e) {
		if (isPlay) {
			if (x > 271 && x < 1118 && y > 233 && y < 280) {
				stateManager.setSettupIndex(2);
				stateManager.setMultiplayer(true);
				stateManager.setCurrentState(new StartingState(stateManager));
			}

			if (x > 272 && x < 939 && y > 344 && y < 390) {
				stateManager.setSettupIndex(1);
				stateManager.setCurrentState(new StartingState(stateManager));
			}

			onePlayerPressed = false;
			twoPlayerPressed = false;
		} else if (buttonOffset == 250) {
			if (x > 444 && x < 744 && y > 396 && y < 435) {
				System.exit(0);
			}

			if (x > 487 && x < 695 && y > 234 && y < 280) {
				isPlay = true;
				buttonOffset = 600;
				buttonAlpha = 0;
			}

			playPressed = false;
			helpPressed = false;
			quitPressed = false;
		}
	}

	public void mouseClicked(MouseEvent e) {

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
