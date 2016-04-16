package state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import com.sun.javafx.geom.AreaOp.NZWindOp;

import core.Boat;
import core.BoatType;
import core.Computer;
import core.Player;
import graphics.AssetLoader;
import graphics.Display;

public class StartingState extends State {

	private StateManager stateManager;
	private int porteAvion = 0;
	private int croiseur = 0;
	private int contreTorpilleur = 0;
	private int sousMarin = 0;
	private int torpilleur = 0;
	private boolean name = false;
	private boolean namefirst = true;
	private int direction = 1;
	private float disparitionErreur = 1;

	private String currentName;
	private String errorMessage;

	private Color boatColor;
	private Color boatSelectedColor;
	private Color boatGrayedColor;
	private boolean trashPressed = false;
	private boolean trashClicked = false;
	private boolean randomPressed = false;
	private boolean randomClicked= false;

	private boolean continuePressed = false;

	private int mouseX, mouseY;

	private ArrayList<Boat> boats = new ArrayList<>();

	public StartingState(StateManager stateManager) {
		this.stateManager = stateManager;
		
		currentName = "Entrez votre nom...";
		errorMessage = "";
		boatColor = new Color(0, 35, 102, 255);
		boatSelectedColor = new Color(100, 135, 202, 255);
		boatGrayedColor = new Color(150, 185, 252, 255);
	}

	@Override
	public void update() {
		if (!errorMessage.isEmpty()) {
			disparitionErreur += 0.02;
			if (disparitionErreur >= 3) {
				disparitionErreur = 1;
				errorMessage = "";
			}
		}

		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;
	}

	@Override
	public void render(Graphics g) {

		g.setFont(AssetLoader.helvetica45);
		if (name) {
			g.setColor(Color.LIGHT_GRAY);
		} else {
			g.setColor(Color.GRAY);
		}
		g.drawRect(48, 72, 515, 114);
		if (name) {
			g.setColor(Color.DARK_GRAY);
		} else {
			g.setColor(Color.GRAY);
		}
		g.drawString(currentName, 50, 150);

		if (porteAvion == 1) {
			g.setColor(boatSelectedColor);
		} else if (porteAvion == 2) {
			g.setColor(boatGrayedColor);
		} else {
			g.setColor(boatColor);
		}
		g.fillRect(320, 213, 220, 40);
		g.setFont(AssetLoader.helvetica25);
		g.drawString("PORTE-AVION", 130, 241);

		if (croiseur == 1) {
			g.setColor(boatSelectedColor);
		} else if (croiseur == 2) {
			g.setColor(boatGrayedColor);
		} else {
			g.setColor(boatColor);
		}
		g.fillRect(320, 260, 176, 40);
		g.drawString("CROISSEUR", 155, 288);

		if (contreTorpilleur == 1) {
			g.setColor(boatSelectedColor);
		} else if (contreTorpilleur == 2) {
			g.setColor(boatGrayedColor);
		} else {
			g.setColor(boatColor);
		}
		g.fillRect(320, 307, 132, 40);
		g.drawString("CONTRE-TORPILLEUR", 33, 335);

		if (sousMarin == 1) {
			g.setColor(boatSelectedColor);
		} else if (sousMarin == 2) {
			g.setColor(boatGrayedColor);
		} else {
			g.setColor(boatColor);
		}
		g.fillRect(320, 354, 132, 40);
		g.drawString("SOUS-MARIN", 145, 382);

		if (torpilleur == 1) {
			g.setColor(boatSelectedColor);
		} else if (torpilleur == 2) {
			g.setColor(boatGrayedColor);
		} else {
			g.setColor(boatColor);
		}
		g.fillRect(320, 401, 88, 40);
		g.drawString("TORPILLEUR", 145, 429);

		g.drawImage(AssetLoader.grid, 600, 42, null);
		g.setFont(AssetLoader.helvetica35);
		g.setColor(new Color(0x333333, false));
		g.drawString("A", 655, 77);
		g.drawString("B", 700, 77);
		g.drawString("C", 745, 77);
		g.drawString("D", 790, 77);
		g.drawString("E", 835, 77);
		g.drawString("F", 881, 77);
		g.drawString("G", 924, 77);
		g.drawString("H", 970, 77);
		g.drawString("I", 1022, 77);
		g.drawString("J", 1063, 77);

		g.drawString("1", 612, 122);
		g.drawString("2", 612, 167);
		g.drawString("3", 612, 212);
		g.drawString("4", 612, 257);
		g.drawString("5", 612, 302);
		g.drawString("6", 612, 347);
		g.drawString("7", 612, 392);
		g.drawString("8", 612, 437);
		g.drawString("9", 612, 482);
		g.drawString("10", 602, 527);

		if (!errorMessage.isEmpty()) {
			g.setColor(new Color(252, 67, 73, (int) (255 / disparitionErreur)));
			g.setFont(AssetLoader.errorFont);
			g.drawString(errorMessage, 525, 25);
		}

		for (int i = 0; i < boats.size(); i++) {
			g.translate(600, 42);
			if (trashClicked) {
				g.setColor(new Color(232, 47, 53, 240));
			} else {
				g.setColor(new Color(0, 35, 102, 240));
			}

			Boat boat = boats.get(i);

			String firstCase = boat.getCases().get(0);
			String lastCase = boat.getCases().get(boat.getCases().size() - 1);

			int boatDirection = boats.get(i).getDirection();

			if (boatDirection == 1) {
				if (trashClicked && mouseX > (5 + ((firstCase.charAt(0) - 64) * 45)) + 600
						&& mouseX < 600
								+ (5 + ((firstCase.charAt(0) - 64) * 45) + 81 + (boat.getType().getSize() - 2) * 45)
						&& mouseY > 66 + (5 + (Integer.parseInt(firstCase.substring(1)) * 45))
						&& mouseY < 67 + (5 + 36 + (Integer.parseInt(firstCase.substring(1)) * 45)))
					g.setColor(new Color(255, 87, 93));
				g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
						81 + (boat.getType().getSize() - 2) * 45, 36);
				if (trashClicked)
					g.drawImage(AssetLoader.delete,
							5 + ((firstCase.charAt(0) - 64) * 45) + (81 + (boat.getType().getSize() - 2) * 45) / 2 - 15,
							5 + (Integer.parseInt(firstCase.substring(1)) * 45) + 18 - 15, 30, 30, null);
			} else if (boatDirection == 2) {
				if (trashClicked && mouseX > 5 + ((firstCase.charAt(0) - 64) * 45) + 600
						&& mouseX < 600 + 5 + ((firstCase.charAt(0) - 64) * 45) + 37
						&& mouseY > 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45)
						&& mouseY < 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45) + 81
								+ (boat.getType().getSize() - 2) * 45)
					g.setColor(new Color(255, 87, 93));
				g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(firstCase.substring(1)) * 45),
						36, 81 + (boat.getType().getSize() - 2) * 45);
				if (trashClicked)
					g.drawImage(AssetLoader.delete, 5 + ((firstCase.charAt(0) - 64) * 45) + 18 - 15,
							5 + (Integer.parseInt(firstCase.substring(1)) * 45)
									+ (81 + (boat.getType().getSize() - 2) * 45) / 2 - 15,
							30, 30, null);
			} else if (boatDirection == 3) {
				if (trashClicked && mouseX > (5 + ((lastCase.charAt(0) - 64) * 45)) + 600
						&& mouseX < 600
								+ (5 + ((lastCase.charAt(0) - 64) * 45) + 81 + (boat.getType().getSize() - 2) * 45)
						&& mouseY > 66 + (5 + (Integer.parseInt(lastCase.substring(1)) * 45))
						&& mouseY < 67 + (5 + 36 + (Integer.parseInt(lastCase.substring(1)) * 45)))
					g.setColor(new Color(255, 87, 93));
				g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
						81 + (boat.getType().getSize() - 2) * 45, 36);
				if (trashClicked)
					g.drawImage(AssetLoader.delete,
							5 + ((lastCase.charAt(0) - 64) * 45) + (81 + (boat.getType().getSize() - 2) * 45) / 2 - 15,
							5 + (Integer.parseInt(lastCase.substring(1)) * 45) + 18 - 15, 30, 30, null);
			} else if (boatDirection == 4) {
				if (trashClicked && mouseX > 5 + ((lastCase.charAt(0) - 64) * 45) + 600
						&& mouseX < 600 + 5 + ((lastCase.charAt(0) - 64) * 45) + 37
						&& mouseY > 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45)
						&& mouseY < 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45) + 81
								+ (boat.getType().getSize() - 2) * 45)
					g.setColor(new Color(255, 87, 93));
				g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45), 36,
						81 + (boat.getType().getSize() - 2) * 45);
				if (trashClicked)
					g.drawImage(AssetLoader.delete, 5 + ((lastCase.charAt(0) - 64) * 45) + 18 - 15,
							5 + (Integer.parseInt(lastCase.substring(1)) * 45)
									+ (81 + (boat.getType().getSize() - 2) * 45) / 2 - 15,
							30, 30, null);
			}

			g.translate(-600, -42);
		}

		if (!boats.isEmpty()) {
			if (trashPressed) {
				g.drawImage(AssetLoader.trashPressedImg, 525, 468, 70, 70, null);
			} else if (trashClicked) {
				g.drawImage(AssetLoader.trashClickedImg, 525, 443, 70, 95, null);
			} else {
				g.drawImage(AssetLoader.trash, 525, 468, 70, 70, null);
			}
		}

		if (boats.size() == 5) {
			if (continuePressed) {
				g.drawImage(AssetLoader.continueButtonPressed, 1115, 40, 60, 60, null);
			} else if (mouseX > 1116 && mouseX < 1174 && mouseY > 65 && mouseY < 123) {
				g.drawImage(AssetLoader.continueButtonHovered, 1115, 40, 60, 60, null);
			} else {
				g.drawImage(AssetLoader.continueButton, 1115, 40, 60, 60, null);
			}
		}

		this.mouseRender(g);
		
		if (randomPressed) {
			g.drawImage(AssetLoader.randomButtonPressed, 1115, 478, 60, 60, null);
		} else if (mouseX > 1116 && mouseX < 1174 && mouseY > 503 && mouseY < 561) {
			g.drawImage(AssetLoader.randomButtonHovered, 1115, 478, 60, 60, null);
		} else {
			g.drawImage(AssetLoader.randomButton, 1115, 478, 60, 60, null);
		}

	}
	
	public void mouseRender(Graphics g) {
		BoatType type = null;
		if (this.isPorteAvion()) {
			type = BoatType.PORTEAVION;
		} else if (this.isCroiseur()) {
			type = BoatType.CROISEUR;
		} else if (this.isContreTorpilleur()) {
			type = BoatType.CONTRETORPILLEUR;
		} else if (this.isSousMarin()) {
			type = BoatType.SOUSMARIN;
		} else if (this.isTorpilleur()) {
			type = BoatType.TORPILLEUR;
		}else{
			return;
		}

		int direction = this.getDirection();
		int x = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int y = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;

		g.translate(600, 42);

		int column;
		int line;
		
		if (x > 45 && x < 90) {
			column = 1;
		} else if (x > 90 && x < 135) {
			column = 2;
		} else if (x > 135 && x < 180) {
			column = 3;
		} else if (x > 180 && x < 225) {
			column = 4;
		} else if (x > 225 && x < 270) {
			column = 5;
		} else if (x > 270 && x < 315) {
			column = 6;
		} else if (x > 315 && x < 360) {
			column = 7;
		} else if (x > 360 && x < 405) {
			column = 8;
		} else if (x > 405 && x < 450) {
			column = 9;
		} else if (x > 450 && x < 495) {
			column = 10;
		} else {
			return;
		}

		if (y > 45 && y < 90) {
			line = 1;
		} else if (y > 90 && y < 135) {
			line = 2;
		} else if (y > 135 && y < 180) {
			line = 3;
		} else if (y > 180 && y < 225) {
			line = 4;
		} else if (y > 225 && y < 270) {
			line = 5;
		} else if (y > 270 && y < 315) {
			line = 6;
		} else if (y > 315 && y < 360) {
			line = 7;
		} else if (y > 360 && y < 405) {
			line = 8;
		} else if (y > 405 && y < 450) {
			line = 9;
		} else if (y > 450 && y < 495) {
			line = 10;
		} else {
			return;
		}

		int columnBuffer = column;
		int lineBuffer = line;

		switch (direction) {
		case 1:
			columnBuffer += type.getSize();
			if (columnBuffer > 10) {
				this.setDirection(3);
			}
			break;

		case 2:
			lineBuffer += type.getSize();
			if (lineBuffer > 10) {
				this.setDirection(4);
			}
			break;

		case 3:
			columnBuffer -= type.getSize();
			if (columnBuffer < 1) {
				this.setDirection(1);
			}
			break;

		case 4:
			lineBuffer -= type.getSize();
			if (lineBuffer < 1) {
				this.setDirection(2);
			}
			break;
		}
		
		g.setColor(new Color(22, 22, 22, 100));
		switch (direction) {
		case 1:
			g.fillRect(5 + 45 * column, 5 + 45 * line, 81 + 45 * (type.getSize() - 2), 36);
			break;

		case 2:
			g.fillRect(5 + 45 * column, 5 + 45 * line, 36, 81 + 45 * (type.getSize() - 2));
			break;

		case 3:
			g.fillRect(5 + 45 * column - (45 * (type.getSize() - 1)), 5 + 45 * line, 81 + 45 * (type.getSize() - 2), 36);
			break;

		case 4:
			g.fillRect(5 + 45 * column, 5 + 45 * line - (45 * (type.getSize() - 1)), 36, 81 + 45 * (type.getSize() - 2));
			break;
		}
	}

	private void addBoat(int x, int y) {
		Boat boat = null;
		String column = "";
		String line = "";

		if (x > 45 && x < 90) {
			column = "A";
		} else if (x > 90 && x < 135) {
			column = "B";
		} else if (x > 135 && x < 180) {
			column = "C";
		} else if (x > 180 && x < 225) {
			column = "D";
		} else if (x > 225 && x < 270) {
			column = "E";
		} else if (x > 270 && x < 315) {
			column = "F";
		} else if (x > 315 && x < 360) {
			column = "G";
		} else if (x > 360 && x < 405) {
			column = "H";
		} else if (x > 405 && x < 450) {
			column = "I";
		} else if (x > 450 && x < 495) {
			column = "J";
		} else {
			return;
		}

		if (y > 45 && y < 90) {
			line = "1";
		} else if (y > 90 && y < 135) {
			line = "2";
		} else if (y > 135 && y < 180) {
			line = "3";
		} else if (y > 180 && y < 225) {
			line = "4";
		} else if (y > 225 && y < 270) {
			line = "5";
		} else if (y > 270 && y < 315) {
			line = "6";
		} else if (y > 315 && y < 360) {
			line = "7";
		} else if (y > 360 && y < 405) {
			line = "8";
		} else if (y > 405 && y < 450) {
			line = "9";
		} else if (y > 450 && y < 495) {
			line = "10";
		} else {
			return;
		}

		if (isPorteAvion()) {
			boat = new Boat(BoatType.PORTEAVION, column + line, direction);
		} else if (isCroiseur()) {
			boat = new Boat(BoatType.CROISEUR, column + line, direction);
		} else if (isContreTorpilleur()) {
			boat = new Boat(BoatType.CONTRETORPILLEUR, column + line, direction);
		} else if (isSousMarin()) {
			boat = new Boat(BoatType.SOUSMARIN, column + line, direction);
		} else if (isTorpilleur()) {
			boat = new Boat(BoatType.TORPILLEUR, column + line, direction);
		}

		for (int i = 0; i < boats.size(); i++) {
			for (int j = 0; j < boat.getCases().size(); j++) {
				if (boats.get(i).isOnCase(boat.getCases().get(j))) {
					errorMessage = "Il y a déjà un bateau là";
					return;
				}
			}
		}

		boats.add(boat);
		switch (boat.getType()) {
		case PORTEAVION:
			porteAvion = 2;
			break;

		case CROISEUR:
			croiseur = 2;
			break;

		case CONTRETORPILLEUR:
			contreTorpilleur = 2;
			break;

		case SOUSMARIN:
			sousMarin = 2;
			break;

		case TORPILLEUR:
			torpilleur = 2;
			break;

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

		if (e.getX() > 535 && e.getX() < 585 && e.getY() > 471 && e.getY() < 537) {
			this.trashPressed = true;
		}

		if (boats.size() == 5 && e.getX() > 1117 && e.getX() < 1174 && e.getY() > 44 && e.getY() < 100) {
			this.continuePressed = true;
		}
		
		if (mouseX > 1116 && mouseX < 1174 && mouseY > 503 && mouseY < 561)
		{
			this.randomPressed = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		int screenX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int screenY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;

		if (porteAvion == 1 || croiseur == 1 || contreTorpilleur == 1 || sousMarin == 1 || torpilleur == 1) {
			if (screenX > 45 && screenX < 495 && screenY > 45 && screenY < 495) {
				addBoat(screenX, screenY);
			}
		}
		
		if (mouseX > 1116 && mouseX < 1174 && mouseY > 503 && mouseY < 561)
		{
			boats = Computer.generateBoatRandom();
			porteAvion = 2;
			croiseur = 2;
			contreTorpilleur = 2;
			sousMarin = 2;
			torpilleur = 2;
			randomPressed = false;
		}

		if (trashClicked) {
			for (int i = 0; i < boats.size(); i++) {
				Boat boat = boats.get(i);

				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);

				int boatDirection = boats.get(i).getDirection();

				if (boatDirection == 1) {
					if (mouseX > (5 + ((firstCase.charAt(0) - 64) * 45)) + 600
							&& mouseX < 600
									+ (5 + ((firstCase.charAt(0) - 64) * 45) + 81 + (boat.getType().getSize() - 2) * 45)
							&& mouseY > 66 + (5 + (Integer.parseInt(firstCase.substring(1)) * 45))
							&& mouseY < 67 + (5 + 36 + (Integer.parseInt(firstCase.substring(1)) * 45))) {
						boats.remove(i);
						switch (boat.getType()) {
						case PORTEAVION:
							porteAvion = 0;
							break;

						case CROISEUR:
							croiseur = 0;
							break;

						case CONTRETORPILLEUR:
							contreTorpilleur = 0;
							break;

						case SOUSMARIN:
							sousMarin = 0;
							break;

						case TORPILLEUR:
							torpilleur = 0;
							break;

						}
					}
				} else if (boatDirection == 2) {
					if (mouseX > 5 + ((firstCase.charAt(0) - 64) * 45) + 600
							&& mouseX < 600 + 5 + ((firstCase.charAt(0) - 64) * 45) + 37
							&& mouseY > 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45)
							&& mouseY < 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45) + 81
									+ (boat.getType().getSize() - 2) * 45) {
						boats.remove(i);
						switch (boat.getType()) {
						case PORTEAVION:
							porteAvion = 0;
							break;

						case CROISEUR:
							croiseur = 0;
							break;

						case CONTRETORPILLEUR:
							contreTorpilleur = 0;
							break;

						case SOUSMARIN:
							sousMarin = 0;
							break;

						case TORPILLEUR:
							torpilleur = 0;
							break;

						}
					}
				} else if (boatDirection == 3) {
					if (mouseX > (5 + ((lastCase.charAt(0) - 64) * 45)) + 600
							&& mouseX < 600
									+ (5 + ((lastCase.charAt(0) - 64) * 45) + 81 + (boat.getType().getSize() - 2) * 45)
							&& mouseY > 66 + (5 + (Integer.parseInt(lastCase.substring(1)) * 45))
							&& mouseY < 67 + (5 + 36 + (Integer.parseInt(lastCase.substring(1)) * 45))) {
						boats.remove(i);
						switch (boat.getType()) {
						case PORTEAVION:
							porteAvion = 0;
							break;

						case CROISEUR:
							croiseur = 0;
							break;

						case CONTRETORPILLEUR:
							contreTorpilleur = 0;
							break;

						case SOUSMARIN:
							sousMarin = 0;
							break;

						case TORPILLEUR:
							torpilleur = 0;
							break;

						}
					}
				} else if (boatDirection == 4) {
					if (trashClicked && mouseX > 5 + ((lastCase.charAt(0) - 64) * 45) + 600
							&& mouseX < 600 + 5 + ((lastCase.charAt(0) - 64) * 45) + 37
							&& mouseY > 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45)
							&& mouseY < 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45) + 81
									+ (boat.getType().getSize() - 2) * 45) {
						boats.remove(i);
						switch (boat.getType()) {
						case PORTEAVION:
							porteAvion = 0;
							break;

						case CROISEUR:
							croiseur = 0;
							break;

						case CONTRETORPILLEUR:
							contreTorpilleur = 0;
							break;

						case SOUSMARIN:
							sousMarin = 0;
							break;

						case TORPILLEUR:
							torpilleur = 0;
							break;

						}
					}
				}

			}
			if (screenX > 45 && screenX < 495 && screenY > 45 && screenY < 495) {
				return;
			} else {
				trashClicked = false;
				return;
			}
		}

		if (x > 535 && x < 585 && y > 471 && y < 537) {
			this.trashPressed = false;
			this.trashClicked = true;
		} else {
			this.trashPressed = false;
			this.trashClicked = false;
		}

		if (boats.size() == 5 && e.getX() > 1117 && e.getX() < 1174 && e.getY() > 44 && e.getY() < 100) {
			this.continuePressed = false;
			if(currentName.isEmpty() || currentName.equals("Entrez votre nom..."))
			{
				errorMessage = "Veuillez entrer votre nom...";
				return;
			}
			if(stateManager.getSettupIndex() == 2)
			{
				if(!stateManager.getMultiplayer() && stateManager.getCore().getPlayers().get(0).getName().equals(currentName))
				{
					errorMessage = "Ce nom est déjà pris...";
					return;
				}
			}
			stateManager.getCore().getPlayers().add(new Player(currentName, boats));
			if(stateManager.getSettupIndex() == 1)
			{
				stateManager.setSettupIndex(2);
				stateManager.setCurrentState(new StartingState(stateManager));
			}else if(stateManager.getSettupIndex() == 2)
			{
				if(stateManager.getMultiplayer())
				{
					stateManager.getCore().getPlayers().add(new Computer("Ordinateur"));
					stateManager.setCurrentPlayer(0);
					stateManager.setCurrentState(new PlayState(stateManager));
				}else{
					stateManager.setCurrentState(new ChangingState(stateManager));
				}
			}
		}

		if (x > 49 && x < 563 && y > 75 && y < 188) {
			if (namefirst) {
				currentName = "";
			}
			namefirst = false;
			name = true;
		} else {
			name = false;
		}

		if (x > 321 && x < 540 && y > 217 && y < 255 && porteAvion != 2) {
			porteAvion = 1;
			if (croiseur != 2)
				croiseur = 0;
			if (contreTorpilleur != 2)
				contreTorpilleur = 0;
			if (sousMarin != 2)
				sousMarin = 0;
			if (torpilleur != 2)
				torpilleur = 0;
			direction = 1;
			this.trashPressed = false;
			this.trashClicked = false;
		} else if (porteAvion != 2) {
			porteAvion = 0;
		}

		if (x > 321 && x < 497 && y > 262 && y < 302 && croiseur != 2) {
			if (porteAvion != 2)
				porteAvion = 0;
			croiseur = 1;
			if (contreTorpilleur != 2)
				contreTorpilleur = 0;
			if (sousMarin != 2)
				sousMarin = 0;
			if (torpilleur != 2)
				torpilleur = 0;
			direction = 1;
			this.trashPressed = false;
			this.trashClicked = false;
		} else if (croiseur != 2) {
			croiseur = 0;
		}

		if (x > 321 && x < 452 && y > 309 && y < 348 && contreTorpilleur != 2) {
			if (porteAvion != 2)
				porteAvion = 0;
			if (croiseur != 2)
				croiseur = 0;
			contreTorpilleur = 1;
			if (sousMarin != 2)
				sousMarin = 0;
			if (torpilleur != 2)
				torpilleur = 0;
			direction = 1;
			this.trashPressed = false;
			this.trashClicked = false;
		} else if (contreTorpilleur != 2) {
			contreTorpilleur = 0;
		}

		if (x > 321 && x < 452 && y > 356 && y < 396 && sousMarin != 2) {
			if (porteAvion != 2)
				porteAvion = 0;
			if (croiseur != 2)
				croiseur = 0;
			if (contreTorpilleur != 2)
				contreTorpilleur = 0;
			sousMarin = 1;
			if (torpilleur != 2)
				torpilleur = 0;
			direction = 1;
			this.trashPressed = false;
			this.trashClicked = false;
		} else if (sousMarin != 2) {
			sousMarin = 0;
		}

		if (x > 321 && x < 409 && y > 404 && y < 444 && torpilleur != 2) {
			if (porteAvion != 2)
				porteAvion = 0;
			if (croiseur != 2)
				croiseur = 0;
			if (contreTorpilleur != 2)
				contreTorpilleur = 0;
			if (sousMarin != 2)
				sousMarin = 0;
			torpilleur = 1;
			direction = 1;
			this.trashPressed = false;
			this.trashClicked = false;
		} else if (torpilleur != 2) {
			torpilleur = 0;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (name) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				name = false;
				return;
			}
			int keyCode = e.getKeyCode();
			if ((keyCode >= 65 && keyCode <= 90 || keyCode >= 97 && keyCode <= 122) && currentName.length() < 22) {
				currentName += e.getKeyChar();
			}
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !currentName.isEmpty()) {
				currentName = currentName.substring(0, currentName.length() - 1);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			direction = 2;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			direction = 3;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			direction = 4;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direction = 1;
		}
		if (porteAvion == 1 || croiseur == 1 || contreTorpilleur == 1 || sousMarin == 1 || torpilleur == 1
				|| trashClicked || name) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (porteAvion != 2)
					porteAvion = 0;
				if (croiseur != 2)
					croiseur = 0;
				if (contreTorpilleur != 2)
					contreTorpilleur = 0;
				if (sousMarin != 2)
					sousMarin = 0;
				if (torpilleur != 2)
					torpilleur = 0;
				trashClicked = false;
				name = false;
			}
		}

	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public boolean isPorteAvion() {
		if (porteAvion == 1) {
			return true;
		} else if (porteAvion == 0) {
			return false;
		}
		return false;
	}

	public boolean isCroiseur() {
		if (croiseur == 1) {
			return true;
		} else if (croiseur == 0) {
			return false;
		}
		return false;
	}

	public boolean isContreTorpilleur() {
		if (contreTorpilleur == 1) {
			return true;
		} else if (contreTorpilleur == 0) {
			return false;
		}
		return false;
	}

	public boolean isSousMarin() {
		if (sousMarin == 1) {
			return true;
		} else if (sousMarin == 0) {
			return false;
		}
		return false;
	}

	public boolean isTorpilleur() {
		if (torpilleur == 1) {
			return true;
		} else if (torpilleur == 0) {
			return false;
		}
		return false;
	}

}
