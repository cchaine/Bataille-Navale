package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.Boat;
import core.Player;
import utils.AssetLoader;
import graphics.Display;
import gui.Button;
import gui.TextButton;

public class EndGameState extends State {

	private StateManager stateManager;
	private Player winner;
	private Player loser;

	private boolean showingGrid = false;

	private int wave1X = 0;
	private int wave2X = 1200;
	private int wave3X = 0;
	private int wave4X = 1200;

	private float timeEvolution = 0;
	private float boatY = 400;

	private TextButton menuButton;
	private TextButton resultButton;
	private TextButton quitButton;
	private float buttonOffset = 600;
	private float buttonAlpha = 0;

	private Button continueButton;

	private int mouseX, mouseY;

	public EndGameState(StateManager stateManager) {
		this.stateManager = stateManager;

		if(stateManager.getCore().getPlayers().get(0).isWinner()){
			winner = stateManager.getCore().getPlayers().get(0);
			loser = stateManager.getCore().getPlayers().get(1);
		}else{
			winner = stateManager.getCore().getPlayers().get(1);
			loser = stateManager.getCore().getPlayers().get(0);
		}

		menuButton = new TextButton(Display.width / 2, (int) buttonOffset + 80, "menu", "MENU", new Color(0x68D16A),
				AssetLoader.junebug50);
		resultButton = new TextButton(Display.width / 2, (int) buttonOffset - 30, "result", "RESULT",
				new Color(0x68D16A), AssetLoader.junebug50);
		quitButton = new TextButton(Display.width / 2, (int) buttonOffset + 200, "quitter", "QUITTER",
				new Color(0xE72623), AssetLoader.junebug50);
		
		continueButton = new Button(Display.width / 2, 140, 100, 100, AssetLoader.continueButton, AssetLoader.continueButtonHovered, AssetLoader.continueButtonPressed);
	}

	@Override
	public void update() {
		// Récupère la position du pointeur sur l'écran et y soustrait la
		// position de la fenêtre sur l'écran pour avoir la position du pointeur
		// sur la fenêtre
		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;

		timeEvolution += 0.1f;
		boatY -= Math.cos(timeEvolution) / 3; // Varier la hauteur du bateau suivant une fonction cosinus

		// Effectue un déplacement des boutons de l'extérieur de la fenêtre à Y = 250
		if (buttonOffset > 250 && timeEvolution > 0.8)
			buttonOffset -= Math.pow(2, buttonOffset / 130);
		else if (buttonOffset <= 250)
			buttonOffset = 250;

		// Effectue un fondu des boutons
		if (buttonAlpha >= 165)
			buttonAlpha = 170;
		else if (buttonAlpha < 255 && buttonOffset < 400)
			buttonAlpha += Math.pow(2, buttonOffset / 130);

		// Déplace les vagues de la droite vers la gauche
		if (wave1X <= -1200) // Si la vague de devant est hors de l'écran, la mettre à droite
			wave1X = 1198;
		else if (wave1X > -1200)
			wave1X -= 2;

		if (wave2X <= -1200)
			wave2X = 1198;
		else if (wave2X > -1200)
			wave2X -= 2;

		if (wave3X <= -1200) // Si la vague de derrière est hors de l'écran, la mettre à droite
			wave3X = 1198;
		else if (wave3X > -1200)
			wave3X -= 1;

		if (wave4X <= -1200)
			wave4X = 1198;
		else if (wave4X > -1200)
			wave4X -= 1;
		
		if(!showingGrid)
		{
			if(resultButton.contains(mouseX, mouseY))
				resultButton.setHovered(true);
			else
				resultButton.setHovered(false);
			resultButton.setPosition(Display.width / 2, (int)buttonOffset - 30); //Met à jour la position du bouton en fonction du déplacement vers le haut
			
			if(menuButton.contains(mouseX, mouseY))
				menuButton.setHovered(true);
			else
				menuButton.setHovered(false);
			menuButton.setPosition(Display.width / 2, (int)buttonOffset + 80);
			
			if(quitButton.contains(mouseX, mouseY))
				quitButton.setHovered(true);
			else
				quitButton.setHovered(false);
			quitButton.setPosition(Display.width / 2, (int)buttonOffset + 200);
		}else{
			if(continueButton.contains(mouseX, mouseY))
				continueButton.setHovered(true);
			else
				continueButton.setHovered(false);
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(AssetLoader.waves, wave3X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave4X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.boat, 20, (int) boatY, 400, 200, null);
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

			g.drawString(winner.getName().toUpperCase() + " EST LE GAGNANT",
					(int) (600 - g.getFontMetrics()
							.getStringBounds(winner.getName().toUpperCase() + " EST LE GAGNANT", g).getWidth() / 2),
					90);

			g.setFont(AssetLoader.junebug40);

			resultButton.render(g, buttonAlpha);
			menuButton.render(g, buttonAlpha);
			quitButton.render(g, buttonAlpha);
		} else {
			g.setColor(new Color(255, 255, 255, 190));
			g.fillRect(10, 10, 1180, 580);

			g.setColor(new Color(0, 35, 102, 240));
			g.setFont(AssetLoader.helvetica25);

			g.drawString(winner.getName(),
					(int) (280 - g.getFontMetrics().getStringBounds(winner.getName(), g).getWidth() / 2), 50);
			g.drawString(loser.getName(),
					(int) (920 - g.getFontMetrics().getStringBounds(winner.getName(), g).getWidth() / 2), 50);

			continueButton.render(g);

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
			for (int i = 0; i < winner.getBoats().size(); i++) {
				Boat boat = winner.getBoats().get(i);

				if (boat.isAlive()) {
					g.setColor(new Color(51, 51, 51, 240));
				} else {
					g.setColor(new Color(151, 151, 151, 240));
				}
				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);

				int boatDirection = winner.getBoats().get(i).getDirection();

				if (boatDirection == 1) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 2) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(firstCase.substring(1)) * 45), 36,
							81 + (boat.getType().getSize() - 2) * 45);
				} else if (boatDirection == 3) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 4) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							36, 81 + (boat.getType().getSize() - 2) * 45);
				}
			}

			for (int i = 0; i < winner.getDeadBoats().size(); i++) {
				g.setColor(new Color(151, 151, 151, 240));
				if (!winner.getDeadBoats().get(i).isAlive()) {
					Boat boat = winner.getDeadBoats().get(i);
					if (boat.getDirection() == 1) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 2) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45), 36,
								81 + (boat.getType().getSize() - 2) * 45);
					} else if (boat.getDirection() == 3) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45), 5
								+ (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 4) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45), 5
								+ (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								36, 81 + (boat.getType().getSize() - 2) * 45);
					}
				}
			}

			for (int i = 0; i < loser.getWinHistory().size(); i++) {
				String cases = loser.getWinHistory().get(i);
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			for (int i = 0; i < loser.getFailHistory().size(); i++) {
				String cases = loser.getFailHistory().get(i);
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
			for (int i = 0; i < loser.getBoats().size(); i++) {
				Boat boat = loser.getBoats().get(i);

				if (boat.isAlive()) {
					g.setColor(new Color(51, 51, 51, 240));
				} else {
					g.setColor(new Color(151, 151, 151, 240));
				}
				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);

				int boatDirection = loser.getBoats().get(i).getDirection();

				if (boatDirection == 1) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(firstCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 2) {
					g.fillRect(5 + ((firstCase.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(firstCase.substring(1)) * 45), 36,
							81 + (boat.getType().getSize() - 2) * 45);
				} else if (boatDirection == 3) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							81 + (boat.getType().getSize() - 2) * 45, 36);
				} else if (boatDirection == 4) {
					g.fillRect(5 + ((lastCase.charAt(0) - 64) * 45), 5 + (Integer.parseInt(lastCase.substring(1)) * 45),
							36, 81 + (boat.getType().getSize() - 2) * 45);
				}
			}

			for (int i = 0; i < loser.getDeadBoats().size(); i++) {
				g.setColor(new Color(151, 151, 151, 240));
				if (!loser.getDeadBoats().get(i).isAlive()) {
					Boat boat = loser.getDeadBoats().get(i);
					if (boat.getDirection() == 1) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 2) {
						g.fillRect(5 + ((boat.getCases().get(0).charAt(0) - 64) * 45),
								5 + (Integer.parseInt(boat.getCases().get(0).substring(1)) * 45), 36,
								81 + (boat.getType().getSize() - 2) * 45);
					} else if (boat.getDirection() == 3) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45), 5
								+ (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								81 + (boat.getType().getSize() - 2) * 45, 36);
					} else if (boat.getDirection() == 4) {
						g.fillRect(5 + ((boat.getCases().get(boat.getCases().size() - 1).charAt(0) - 64) * 45), 5
								+ (Integer.parseInt(boat.getCases().get(boat.getCases().size() - 1).substring(1)) * 45),
								36, 81 + (boat.getType().getSize() - 2) * 45);
					}
				}
			}

			for (int i = 0; i < winner.getWinHistory().size(); i++) {
				String cases = winner.getWinHistory().get(i);
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			for (int i = 0; i < winner.getFailHistory().size(); i++) {
				String cases = winner.getFailHistory().get(i);
				g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			g.translate(-675, -75);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(mouseX + " " + mouseX);

		if (!showingGrid) {
			if (buttonOffset == 250) {
				// On verifie si la position de la souris est dans le bouton
				if (resultButton.contains(mouseX, mouseY))
					resultButton.setPressed(true);
				else
					resultButton.setPressed(false);

				if (menuButton.contains(mouseX, mouseY))
					menuButton.setPressed(true);
				else
					menuButton.setPressed(false);

				if (quitButton.contains(mouseX, mouseY))
					quitButton.setPressed(true);
				else
					quitButton.setPressed(false);
			}
		} else {
			if (continueButton.contains(mouseX, mouseY))
				continueButton.setPressed(true);
			else
				continueButton.setPressed(true);
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!showingGrid) {
			if (buttonOffset == 250) {
				if (resultButton.contains(mouseX, mouseY))
					showingGrid = true;

				if (menuButton.contains(mouseX, mouseY)) {
					stateManager.getCore().getPlayers().remove(1);
					stateManager.getCore().getPlayers().remove(0);
					/*stateManager.setSettupIndex(1);
					stateManager.setCurrentPlayer(0);*/
					stateManager.setCurrentState(new MenuState(stateManager));
				}

				if (quitButton.contains(mouseX, mouseY))
					System.exit(0);
			}
		} else {
			if (continueButton.contains(mouseX, mouseY))
				showingGrid = false;
		}
		
		continueButton.setPressed(false);
		resultButton.setPressed(false);
		menuButton.setPressed(false);
		quitButton.setPressed(false);

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
