package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import core.Boat;
import core.Player;
import utils.AssetLoader;
import utils.DrawingUtils;
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

	/**
	 * @brief Constructeur
	 * 
	 * @param stateManager		Gestionnaire des étapes du programme
	 */
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
		resultButton = new TextButton(Display.width / 2, (int) buttonOffset - 30, "resultat", "RESULTAT",
				new Color(0x68D16A), AssetLoader.junebug50);
		quitButton = new TextButton(Display.width / 2, (int) buttonOffset + 200, "quitter", "QUITTER",
				new Color(0xE72623), AssetLoader.junebug50);
		
		continueButton = new Button(Display.width / 2, 140, 100, 100, AssetLoader.continueButton, AssetLoader.continueButtonHovered, AssetLoader.continueButtonPressed);
	}

	/**
	 * @brief Mise a jour de la logique du jeu ordonnée par le gestionnaire des étapes du jeu
	 * 
	 * @details Met à jour l'état des boutons ainsi que la position des vagues et du bateau
	 */
	@Override
	public void update() {
		/* Récupère la position du pointeur sur l'écran et y soustrait la
		 * position de la fenêtre sur l'écran pour avoir la position du pointeur
		 * sur la fenêtre
		 */
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

	/**
	 * @brief Mise à jour du rendu ordonnée par le gestionnaire des étapes du programme
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	@Override
	public void render(Graphics g) {
		g.drawImage(AssetLoader.waves, wave3X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave4X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.boat, 20, (int) boatY, 400, 200, null);
		g.drawImage(AssetLoader.waves, wave1X, 535, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave2X, 535, 1200, 100, null);

		if (!showingGrid) {
			//Choisie la taille de la police en fonction de la taille du texte a afficher
			if (g.getFontMetrics().getStringBounds(winner.getName().toUpperCase() + " EST LE GAGNANT", g).getWidth() / 2 > 100)
				g.setFont(AssetLoader.junebug35);
			else
				g.setFont(AssetLoader.junebug50);

			g.setColor(new Color(0x002366));

			//A la position est soustraite la moitié de la taille du texte, car Graphics dessine le texte non centré
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
			g.fillRect(10, 10, 1180, 580); //Background

			g.setColor(new Color(0, 35, 102, 240));
			g.setFont(AssetLoader.helvetica25);

			g.drawString(winner.getName(),
					(int) (280 - g.getFontMetrics().getStringBounds(winner.getName(), g).getWidth() / 2), 50);
			g.drawString(loser.getName(),
					(int) (920 - g.getFontMetrics().getStringBounds(winner.getName(), g).getWidth() / 2), 50);

			continueButton.render(g);

			DrawingUtils.drawGrid(g, 29, 75);

			//Déplace l'origine du dessin en haut à gauche de A1 de la grille de gauche
			g.translate(29, 75);
			
			//Affiche les bateaux vivants du gagnant sur la grille
			for (int i = 0; i < winner.getBoats().size(); i++) {
				Boat boat = winner.getBoats().get(i);

				g.setColor(new Color(51, 51, 51, 240));//Gris fonc�
				
				Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);
				
				//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
				g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
						(int) boatBounds.getHeight());
			}

			//Affiche les bateaux morts du gagnant sur la grille
			for (int i = 0; i < winner.getDeadBoats().size(); i++) {
				
				g.setColor(new Color(151, 151, 151, 240));//Gris clair
				
				Boat boat = winner.getDeadBoats().get(i);
				
				Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);
				
				//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
				g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
						(int) boatBounds.getHeight());
			}

			//Affiche les cases touch�es par l'ennemi
			for (int i = 0; i < loser.getWinHistory().size(); i++) {
				String cases = loser.getWinHistory().get(i);
				
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			
			//Affiche les cases dans l'eau de l'ennemi
			for (int i = 0; i < loser.getFailHistory().size(); i++) {
				String cases = loser.getFailHistory().get(i);
				
				g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}

			//Red�place l'origine du rep�re � sa position initiale
			g.translate(-29, -75);

			
			DrawingUtils.drawGrid(g, 675, 75);

			g.translate(675, 75);
			
			//Affiche les bateaux vivants du perdant sur la grille
			for (int i = 0; i < loser.getBoats().size(); i++) {
				Boat boat = loser.getBoats().get(i);

				g.setColor(new Color(51, 51, 51, 240));//Gris fonc�
				
				Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);
				
				//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
				g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
						(int) boatBounds.getHeight());
			}

			//Affiche les bateaux morts du perdant sur la grille
			for (int i = 0; i < loser.getDeadBoats().size(); i++) {
				
				g.setColor(new Color(151, 151, 151, 240));//Gris clair
				
				Boat boat = loser.getDeadBoats().get(i);
				
				Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);
				
				//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
				g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
						(int) boatBounds.getHeight());
			}

			//Affiche les cases touch�es par l'ennemi
			for (int i = 0; i < winner.getWinHistory().size(); i++) {
				String cases = winner.getWinHistory().get(i);
				
				g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			
			//Affiche les cases dans l'eau de l'ennemi
			for (int i = 0; i < winner.getFailHistory().size(); i++) {
				String cases = winner.getFailHistory().get(i);
				
				g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
						4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
			}
			
			//Redeplace l'origine du dessin � sa position initiale
			g.translate(-675, -75);
		}

	}

	/**
	 * @brief Gestion des évènement souris pressée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (!showingGrid) {
			if (buttonOffset == 250) {
				// On verifie si la position de la souris est dans le bouton
				resultButton.mousePressed(mouseX, mouseY);
				menuButton.mousePressed(mouseX, mouseY);
				quitButton.mousePressed(mouseX, mouseY);
			}
		} else {
			if (continueButton.contains(mouseX, mouseY))
				continueButton.setPressed(true);
			else
				continueButton.setPressed(true);
		}
	}

	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!showingGrid) {
			if (buttonOffset == 250) {
				if (resultButton.mouseReleased(mouseX, mouseY))
					showingGrid = true;

				if (menuButton.mouseReleased(mouseX, mouseY)) {
					//Efface les joueurs pr�c�dents pour revenir au menu
					stateManager.getCore().getPlayers().remove(1);
					stateManager.getCore().getPlayers().remove(0);
					stateManager.setCurrentState(new MenuState(stateManager));
				}

				if (quitButton.mouseReleased(mouseX, mouseY))
					System.exit(0);
			}
		} else {
			if (continueButton.mouseReleased(mouseX, mouseY))
				showingGrid = false;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}
}
