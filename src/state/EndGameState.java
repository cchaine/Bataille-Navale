package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import core.Boat;
import core.Player;
import graphics.AssetLoader;
import graphics.Display;
import sun.applet.Main;

public class EndGameState extends State {

	private StateManager stateManager;
	private boolean showingGrid = false;

	private int wave1X = 0;
	private int wave2X = 1200;
	private int wave3X = 0;
	private int wave4X = 1200;
	private float timeEvolution = 0;
	private float boatX = 20, boatY = 400;
	private float buttonOffset = 600;
	private float buttonAlpha = 0;
	private Player winner;
	private String menuStr = "menu";
	private String quitStr = "quitter";
	private boolean menuPressed = false;
	private boolean quitPressed = false;
	private Player currentPlayer;
	private Player opponentPlayer;
	private String resultStr = "grilles";
	private boolean resultPressed = false;
	private boolean continuePressed = false;

	private int x, y;

	public EndGameState(StateManager stateManager, Player winner) {
		this.stateManager = stateManager;

		currentPlayer = stateManager.getCore().getPlayers().get(stateManager.getCurrentPlayer());
		opponentPlayer = stateManager.getCore().getPlayers().get(1 - stateManager.getCurrentPlayer());
		this.winner = winner;
	}

	@Override
	public void update() {
		x = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		y = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;

		timeEvolution += 0.1f;
		boatY -= Math.cos(timeEvolution) / 3;

		if (!showingGrid || buttonOffset == 250) {
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
		}

		if (wave1X <= -1200) {
			wave1X = 1198;
		} else if (wave1X > -1200) {
			wave1X -= 2;
		}
		if (wave2X <= -1200) {
			wave2X = 1198;
		} else if (wave2X > -1200) {
			wave2X -= 2;
		}
		if (wave3X <= -1200) {
			wave3X = 1198;
		} else if (wave3X > -1200) {
			wave3X -= 1;
		}
		if (wave4X <= -1200) {
			wave4X = 1198;
		} else if (wave4X > -1200) {
			wave4X -= 1;
		}

	}

	@Override
	public void render(Graphics g) {
		g.drawImage(AssetLoader.waves, wave3X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave4X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.boat, (int) boatX, (int) boatY, 400, 200, null);
		g.drawImage(AssetLoader.waves, wave1X, 535, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave2X, 535, 1200, 100, null);

		if (!showingGrid) {
			if (g.getFontMetrics().getStringBounds(winner.getName().toUpperCase() + " EST LE GAGNANT", g).getWidth()
					/ 2 > 100) {
				g.setFont(AssetLoader.junebug35);
			} else {
				g.setFont(AssetLoader.junebug50);
			}
			
			g.setColor(new Color(0x002366));

			g.drawString(winner.getName().toUpperCase() + " EST LE GAGNANT", (int) (600
					- g.getFontMetrics().getStringBounds(winner.getName().toUpperCase() + " EST LE GAGNANT", g).getWidth()
							/ 2),
					90);
			
			g.setFont(AssetLoader.junebug40);
			
			if (resultPressed && buttonOffset == 250) {
				resultStr = "RESULTATS";
				g.setColor(new Color(0x68D16A));
			} else if (x > 401 && x < 797 && y > 202 && y < 250 && buttonOffset == 250) {
				resultStr = "RESULTATS";
				g.setColor(new Color(0x002366));
			} else {
				resultStr = "resultats";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}
			g.drawString(resultStr, (int) (600 - (g.getFontMetrics().getStringBounds(resultStr, g).getWidth() / 2)), (int) buttonOffset - 30);

			if (menuPressed && buttonOffset == 250) {
				menuStr = "MENU";
				g.setColor(new Color(0x68D16A));
			} else if (x > 503 && x < 697 && y > 312 && y < 357 && buttonOffset == 250) {
				menuStr = "MENU";
				g.setColor(new Color(0x002366));
			} else {
				menuStr = "menu";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}
			g.drawString(menuStr, (int) (600 - (g.getFontMetrics().getStringBounds(menuStr, g).getWidth() / 2)), (int) buttonOffset + 80);
			
			if (quitPressed && buttonOffset == 250) {
				quitStr = "QUITTER";
				g.setColor(new Color(0xE72623));
			} else if (x > 450 && x < 752 && y > 433 && y < 478 && buttonOffset == 250) {
				quitStr = "QUITTER";
				g.setColor(new Color(0x002366));
			} else {
				quitStr = "quitter";
				g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));
			}

			g.drawString(quitStr, (int) (600 - (g.getFontMetrics().getStringBounds(quitStr, g).getWidth() / 2)), (int) buttonOffset + 200);
		}else{
			g.setColor(new Color(255, 255, 255, 190));
			g.fillRect(10, 10, 1180, 580);
			
			g.setColor(new Color(0, 35, 102, 240));
			g.setFont(AssetLoader.helvetica25);
			
			g.drawString(currentPlayer.getName(), (int) (280 - g.getFontMetrics().getStringBounds(currentPlayer.getName(), g).getWidth() / 2), 50);
			g.drawString(opponentPlayer.getName(), (int) (920 - g.getFontMetrics().getStringBounds(currentPlayer.getName(), g).getWidth() / 2), 50);
			
			if (continuePressed) {
				g.drawImage(AssetLoader.continueButtonPressed, 550, 100, 100, 100, null);
			} else if (x > 551 && x < 650 && y > 123 && y < 224) {
				g.drawImage(AssetLoader.continueButtonHovered, 550, 100, 100, 100, null);
			} else {
				g.drawImage(AssetLoader.continueButton, 550, 100, 100, 100, null);
			}
			
			g.drawImage(AssetLoader.grid, 29, 75, null);
			g.setFont(AssetLoader.helvetica35);
			g.setColor(new Color(0x333333, false));
			g.drawString("A", 55 + 29, 110);
			g.drawString("B", 100 + 29, 110);
			g.drawString("C", 145 + 29, 110);
			g.drawString("D", 190 + 29, 110);
			g.drawString("E", 235 + 29, 110);
			g.drawString("F", 281 + 29, 110);
			g.drawString("G", 324 + 29, 110);
			g.drawString("H", 370 + 29, 110);
			g.drawString("I", 422 + 29, 110);
			g.drawString("J", 463 + 29, 110);

			g.drawString("1", 41, 122 + 33);
			g.drawString("2", 41, 167 + 33);
			g.drawString("3", 41, 212 + 33);
			g.drawString("4", 41, 257 + 33);
			g.drawString("5", 41, 302 + 33);
			g.drawString("6", 41, 347 + 33);
			g.drawString("7", 41, 392 + 33);
			g.drawString("8", 41, 437 + 33);
			g.drawString("9", 41, 482 + 33);
			g.drawString("10", 31, 527 + 33);

			g.translate(29, 75);
			for (int i = 0; i < currentPlayer.getBoats().size(); i++) {
				Boat boat = currentPlayer.getBoats().get(i);

				if (boat.isAlive()) {
					g.setColor(new Color(51, 51, 51, 240));
				} else {
					g.setColor(new Color(151, 151, 151, 240));
				}
				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);

				int boatDirection = currentPlayer.getBoats().get(i).getDirection();

				if (boatDirection == 1) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 2) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							36, 81 + (boat.getType().getSize() - 2) * 45);
				} else if (boatDirection == 3) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 4) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45), 36,
							81 + (boat.getType().getSize() - 2) * 45);
				}
			}

			for (int i = 0; i < currentPlayer.getDeadBoats().size(); i++) {
				g.setColor(new Color(151, 151, 151, 240));
				if (!currentPlayer.getDeadBoats().get(i).isAlive()) {
					Boat boat = currentPlayer.getDeadBoats().get(i);
					if (boat.getDirection() == 1) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 2) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45), 36,
								81 + (boat.getType().getSize() - 2) * 45);
					} else if (boat.getDirection() == 3) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 4) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								36, 81 + (boat.getType().getSize() - 2) * 45);
					}
				}
			}

			for (int i = 0; i < opponentPlayer.getWinHistory().size(); i++) {
				String cases = opponentPlayer.getWinHistory().get(i);
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			for (int i = 0; i < opponentPlayer.getFailHistory().size(); i++) {
				String cases = opponentPlayer.getFailHistory().get(i);
				g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}

			g.translate(-29, -75);
			
			g.drawImage(AssetLoader.grid, 675, 75, null);
			g.setFont(AssetLoader.helvetica35);
			g.setColor(new Color(0x333333, false));
			g.drawString("A", 730, 110);
			g.drawString("B", 775, 110);
			g.drawString("C", 820, 110);
			g.drawString("D", 865, 110);
			g.drawString("E", 910, 110);
			g.drawString("F", 956, 110);
			g.drawString("G", 999, 110);
			g.drawString("H", 1045, 110);
			g.drawString("I", 1097, 110);
			g.drawString("J", 1138, 110);

			g.drawString("1", 687, 122 + 33);
			g.drawString("2", 687, 167 + 33);
			g.drawString("3", 687, 212 + 33);
			g.drawString("4", 687, 257 + 33);
			g.drawString("5", 687, 302 + 33);
			g.drawString("6", 687, 347 + 33);
			g.drawString("7", 687, 392 + 33);
			g.drawString("8", 687, 437 + 33);
			g.drawString("9", 687, 482 + 33);
			g.drawString("10", 677, 527 + 33);

			g.translate(675, 75);
			for (int i = 0; i < opponentPlayer.getBoats().size(); i++) {
				Boat boat = opponentPlayer.getBoats().get(i);

				if (boat.isAlive()) {
					g.setColor(new Color(51, 51, 51, 240));
				} else {
					g.setColor(new Color(151, 151, 151, 240));
				}
				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);

				int boatDirection = opponentPlayer.getBoats().get(i).getDirection();

				if (boatDirection == 1) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 2) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							36, 81 + (boat.getType().getSize() - 2) * 45);
				} else if (boatDirection == 3) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 4) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45), 36,
							81 + (boat.getType().getSize() - 2) * 45);
				}
			}

			for (int i = 0; i < opponentPlayer.getDeadBoats().size(); i++) {
				g.setColor(new Color(151, 151, 151, 240));
				if (!opponentPlayer.getDeadBoats().get(i).isAlive()) {
					Boat boat = opponentPlayer.getDeadBoats().get(i);
					if (boat.getDirection() == 1) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 2) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45), 36,
								81 + (boat.getType().getSize() - 2) * 45);
					} else if (boat.getDirection() == 3) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 4) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								36, 81 + (boat.getType().getSize() - 2) * 45);
					}
				}
			}

			for (int i = 0; i < currentPlayer.getWinHistory().size(); i++) {
				String cases = currentPlayer.getWinHistory().get(i);
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			for (int i = 0; i < currentPlayer.getFailHistory().size(); i++) {
				String cases = currentPlayer.getFailHistory().get(i);
				g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			g.translate(-675, -75);
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(x + " " + y);

		if (!showingGrid) {
			if (buttonOffset == 250) {
				if (x > 401 && x < 797 && y > 202 && y < 250) {
					resultPressed = true;
				}else{
					resultPressed = false;
				}
				
				if (x > 503 && x < 697 && y > 312 && y < 357) {
					menuPressed = true;
				} else {
					menuPressed = false;
				}

				if (x > 450 && x < 752 && y > 433 && y < 478) {
					quitPressed = true;
				} else {
					quitPressed = false;
				}
			}
		}else{
			if (x > 551 && x < 650 && y > 123 && y < 224) {
				continuePressed = true;
			}else{
				continuePressed = false;
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!showingGrid) {
			if (buttonOffset == 250) {
				if (x > 401 && x < 797 && y > 202 && y < 250) {
					showingGrid = true;
				}
				
				if (x > 503 && x < 697 && y > 312 && y < 357) {
					stateManager.getCore().getPlayers().remove(1);
					stateManager.getCore().getPlayers().remove(0);
					stateManager.setSettupIndex(1);
					stateManager.setCurrentPlayer(0);
					stateManager.setCurrentState(new MenuState(stateManager));
				}

				if (x > 450 && x < 752 && y > 433 && y < 478) {
					System.exit(0);
				}

				menuPressed = false;
				resultPressed = false;
				quitPressed = false;
			}
		}else{
			if (x > 551 && x < 650 && y > 123 && y < 224) {
				showingGrid = false;
			}
			continuePressed = false;
		}

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
