package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import core.Boat;
import core.Computer;
import core.Player;
import graphics.AssetLoader;
import graphics.Display;

public class PlayState extends State {

	private FontMetrics metrics;
	private Rectangle2D nameMetrics;

	private boolean firePressed = false;
	private String currentTarget = "";
	private boolean fired = false;
	private boolean continuePressed = false;
	private String errorMessage = "";
	private float disparitionErreur = 1;
	private int boatLeft = 5;

	private Player currentPlayer;
	private Player opponentPlayer;

	private StateManager stateManager;

	private int x, y;

	private int mouseX, mouseY;

	public PlayState(StateManager stateManager) {
		this.stateManager = stateManager;
		currentPlayer = stateManager.getCore().getPlayers().get(stateManager.getCurrentPlayer());
		if (stateManager.getCurrentPlayer() == 0) {
			opponentPlayer = stateManager.getCore().getPlayers().get(1);
		} else {
			opponentPlayer = stateManager.getCore().getPlayers().get(0);
		}
	}

	@Override
	public void update() {
		if (!errorMessage.isEmpty()) {
			disparitionErreur += 0.02;
			if (disparitionErreur >= 3) {
				errorMessage = "";
				disparitionErreur = 1;
				currentTarget = "";
			}
		}
		x = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		y = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;
			mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 676;
			mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 99;
		boatLeft = opponentPlayer.getBoats().size();
		if (opponentPlayer.getBoats().size() == 0) {
			fired = true;
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 35, 102, 240));
		g.setFont(AssetLoader.helvetica25);
		metrics = g.getFontMetrics();
		nameMetrics = metrics.getStringBounds(currentPlayer.getName(), g);
		g.drawString(currentPlayer.getName(), (int) (600 - nameMetrics.getWidth() / 2), 40);
		g.setFont(AssetLoader.helvetica20);
		g.drawString("Bateaux Adverses Restants : " + Integer.toString(boatLeft), 717, 40);

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

		if (fired) {
			if (continuePressed) {
				g.drawImage(AssetLoader.continueButtonPressed, 550, 100, 100, 100, null);
			} else if (x > 551 && x < 650 && y > 123 && y < 224) {
				g.drawImage(AssetLoader.continueButtonHovered, 550, 100, 100, 100, null);
			} else {
				g.drawImage(AssetLoader.continueButton, 550, 100, 100, 100, null);
			}
		} else {
			if (firePressed) {
				g.drawImage(AssetLoader.fireButtonPressed, 550, 100, 100, 100, null);
			} else if (x > 551 && x < 650 && y > 123 && y < 224) {
				g.drawImage(AssetLoader.fireButtonHovered, 550, 100, 100, 100, null);
			} else {
				g.drawImage(AssetLoader.fireButton, 550, 100, 100, 100, null);
			}

			g.translate(675, 75);
			g.setColor(new Color(22, 22, 22, 100));

			int column = 0;
			int line = 0;

			if (mouseX > 45 && mouseX < 90) {
				column = 1;
			} else if (mouseX > 90 && mouseX < 135) {
				column = 2;
			} else if (mouseX > 135 && mouseX < 180) {
				column = 3;
			} else if (mouseX > 180 && mouseX < 225) {
				column = 4;
			} else if (mouseX > 225 && mouseX < 270) {
				column = 5;
			} else if (mouseX > 270 && mouseX < 315) {
				column = 6;
			} else if (mouseX > 315 && mouseX < 360) {
				column = 7;
			} else if (mouseX > 360 && mouseX < 405) {
				column = 8;
			} else if (mouseX > 405 && mouseX < 450) {
				column = 9;
			} else if (mouseX > 450 && mouseX < 495) {
				column = 10;
			} else {
				column = 0;
			}

			if (mouseY > 45 && mouseY < 90) {
				line = 1;
			} else if (mouseY > 90 && mouseY < 135) {
				line = 2;
			} else if (mouseY > 135 && mouseY < 180) {
				line = 3;
			} else if (mouseY > 180 && mouseY < 225) {
				line = 4;
			} else if (mouseY > 225 && mouseY < 270) {
				line = 5;
			} else if (mouseY > 270 && mouseY < 315) {
				line = 6;
			} else if (mouseY > 315 && mouseY < 360) {
				line = 7;
			} else if (mouseY > 360 && mouseY < 405) {
				line = 8;
			} else if (mouseY > 405 && mouseY < 450) {
				line = 9;
			} else if (mouseY > 450 && mouseY < 495) {
				line = 10;
			} else {
				line = 0;
			}

			if (line != 0 && column != 0) {
				g.fillRect(5 + 45 * column, 5 + 45 * line, 36, 36);
			}

			if (!currentTarget.isEmpty()) {
				if (!currentPlayer.alreadyPlayed(currentTarget)) {
					g.drawImage(AssetLoader.scope, 5 + ((currentTarget.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(currentTarget.substring(1)) * 45), 36, 36, null);
				} else {
					errorMessage = "Vous avez déjà joué cette case";
				}
			}
		}

		if (!errorMessage.isEmpty()) {
			g.translate(-675, -75);
			g.setColor(new Color(252, 67, 73, (int) (255 / disparitionErreur)));
			g.setFont(AssetLoader.errorFont);
			g.drawString(errorMessage, (int) (600 - g.getFontMetrics().getStringBounds(errorMessage, g).getWidth() / 2),
					60);
		}

	}

	public void fire() {
		if (stateManager.addFire(currentTarget)) {
			currentTarget = "";
			fired = false;
		} else {
			fired = true;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(x + " " + y);
		System.out.println(mouseX + " " + mouseY);

		if (x > 551 && x < 650 && y > 123 && y < 224) {
			if (fired) {
				continuePressed = true;
			} else{
				firePressed = true;
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (x > 551 && x < 650 && y > 123 && y < 224 && fired) {
			if (opponentPlayer.getBoats().size() == 0) {
				currentPlayer.setWinner(true);
				stateManager.setCurrentState(new EndGameState(stateManager, currentPlayer));
			} else if (stateManager.getMultiplayer()) {
				stateManager.setCurrentPlayer(1);
				opponentPlayer.play(stateManager);
				if (stateManager.getCore().getPlayers().get(0).getDeadBoats().size() == 5) {
					stateManager.setCurrentState(new EndGameState(stateManager, opponentPlayer));
				} else {
					stateManager.setCurrentState(new PlayState(stateManager));
				}
			} else {
				stateManager.setCurrentState(new ChangingState(stateManager));
			}
		}
		if (!currentTarget.isEmpty() && x > 551 && x < 650 && y > 123 && y < 224) {
			fire();
		}

		if (!(mouseX > 45 && mouseX < 495 && mouseY > 45 && mouseY < 495)) {
			currentTarget = "";
		}
		
		if (mouseX > 45 && mouseX < 495 && mouseY > 45 && mouseY < 495) {
			String column = "";
			String line = "";

			if (mouseX > 45 && mouseX < 90) {
				column = "A";
			} else if (mouseX > 90 && mouseX < 135) {
				column = "B";
			} else if (mouseX > 135 && mouseX < 180) {
				column = "C";
			} else if (mouseX > 180 && mouseX < 225) {
				column = "D";
			} else if (mouseX > 225 && mouseX < 270) {
				column = "E";
			} else if (mouseX > 270 && mouseX < 315) {
				column = "F";
			} else if (mouseX > 315 && mouseX < 360) {
				column = "G";
			} else if (mouseX > 360 && mouseX < 405) {
				column = "H";
			} else if (mouseX > 405 && mouseX < 450) {
				column = "I";
			} else if (mouseX > 450 && mouseX < 495) {
				column = "J";
			} else {
				return;
			}

			if (mouseY > 45 && mouseY < 90) {
				line = "1";
			} else if (mouseY > 90 && mouseY < 135) {
				line = "2";
			} else if (mouseY > 135 && mouseY < 180) {
				line = "3";
			} else if (mouseY > 180 && mouseY < 225) {
				line = "4";
			} else if (mouseY > 225 && mouseY < 270) {
				line = "5";
			} else if (mouseY > 270 && mouseY < 315) {
				line = "6";
			} else if (mouseY > 315 && mouseY < 360) {
				line = "7";
			} else if (mouseY > 360 && mouseY < 405) {
				line = "8";
			} else if (mouseY > 405 && mouseY < 450) {
				line = "9";
			} else if (mouseY > 450 && mouseY < 495) {
				line = "10";
			} else {
				return;
			}

			currentTarget = column + line;

		}
		continuePressed = false;
		firePressed = false;
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
