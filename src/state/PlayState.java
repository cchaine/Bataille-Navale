package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.Player;
import utils.AssetLoader;
import utils.DrawingUtils;
import graphics.Display;
import gui.Button;

/**
 * @file src/state/PlayState.java
 * @author cchaine
 *
 * @brief Etape de jeu
 * @details Etape où le joueur attaque son adversaire
 */
public class PlayState extends State {
	private Player current;
	private Player opponent;

	private String currentTarget = "";
	private boolean finished = false;
	
	private Button fireButton;
	private Button continueButton;
	
	private String errorMessage = "";
	private float disparitionErreur = 1;
	
	private int boatLeft = 5;

	private StateManager stateManager;

	private int mouseX, mouseY;
	private int gridX, gridY;

	/**
	 * @brief Constructeur
	 * 
	 * @param stateManager		Gestionnaire des étapes du programme
	 */
	public PlayState(StateManager stateManager) {
		this.stateManager = stateManager;
		
		current = stateManager.getCurrent();
		opponent = stateManager.getOpponent();
		
		continueButton = new Button(Display.width / 2, 150, 100, 100, AssetLoader.continueButton, AssetLoader.continueButtonHovered,
				AssetLoader.continueButtonPressed);
		
		fireButton = new Button(Display.width / 2, 150, 100, 100, AssetLoader.fireButton, AssetLoader.fireButtonHovered, 
				AssetLoader.fireButtonPressed);
	}

	/**
	 * @brief Mise a jour de la logique du jeu ordonnée par le gestionnaire des étapes du jeu
	 * 
	 * @details Met à jour l'état des boutons ainsi que la position du pointeur
	 */
	@Override
	public void update() {
		// Récupère la position du pointeur sur l'écran et y soustrait la
		// position de la fenêtre sur l'écran pour avoir la position du pointeur
		// sur la fenêtre
		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;
		
		//Les coordonnées de la souris dont l'origine est en haut à gauche de la case A1
		gridX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 676;
		gridY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 99;
		
		boatLeft = stateManager.getOpponent().getBoats().size();
		
		if (stateManager.getOpponent().getBoats().size() == 0)
			finished = true;
		
		//Si il y a un message d'erreur, incrémenter le temps d'évolution pour faire disparaitre le message
		if (!errorMessage.isEmpty()) {
			disparitionErreur += 0.02;
			if (disparitionErreur >= 3) {
				errorMessage = "";
				disparitionErreur = 1;
				currentTarget = "";
			}
		}
	}

	/**
	 * @brief Mise à jour du rendu ordonnée par le gestionnaire des étapes du programme
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(new Color(0, 35, 102, 240));
		g.setFont(AssetLoader.helvetica25);
		//A la position est soustraite la moitié de la taille du texte, car Graphics dessine le texte non centré
		g.drawString(current.getName(), (int) (600 - g.getFontMetrics().getStringBounds(current.getName(), g).getWidth() / 2), 40);
		
		g.setFont(AssetLoader.helvetica20);
		g.drawString("Bateaux Adverses Restants : " + Integer.toString(boatLeft), 717, 40);

		if(finished)
			continueButton.render(g);
		else
			fireButton.render(g);
		
		//Déplace l'origine du dessin aux coordonnées de la grille
		g.translate(29, 75);
		
		//Dessine la grille
		DrawingUtils.drawGrid(g);
		//Dessine les bateaux et l'historique des tirs sur la grille
		DrawingUtils.drawGridItems(g, current, opponent);
		
		//Redéplace l'origine du dessin à sa position d'origine
		g.translate(-29, -75);

		
		g.translate(675, 75);
		
		DrawingUtils.drawGrid(g);
	
		g.setColor(new Color(151, 151, 151, 240));//Gris clair
		DrawingUtils.drawBoats(g, opponent.getDeadBoats());//Dessine seulement les bateaux déjà détruits
		DrawingUtils.drawShotHistory(g, current);
		
		g.translate(-675, -75);

		if (finished) {
			continueButton.update(mouseX, mouseY);
		} else {
			fireButton.update(mouseX, mouseY);

			//Déplace l'origine du dessin en haut à gauche de la case A1 de la grille de droite
			g.translate(675, 75);
			g.setColor(new Color(22, 22, 22, 100));//Gris fantome

			//Détermine la colonne et la ligne en fonction de la position de la souris sur la grille
			int column = DrawingUtils.guessCoords(gridX);
			int line = DrawingUtils.guessCoords(gridY);
			if (line != 0 && column != 0)
				g.fillRect(5 + 45 * column, 5 + 45 * line, 36, 36); //On dessine une case fantome si la souris est sur la grille

			if (!currentTarget.isEmpty()) {
				if (!current.alreadyPlayed(currentTarget))
					//Dessine la cible
					g.drawImage(AssetLoader.scope, 5 + ((currentTarget.charAt(0) - 64) * 45),
							5 + (Integer.parseInt(currentTarget.substring(1)) * 45), 36, 36, null);
				else 
					errorMessage = "Vous avez déjà joué cette case";
			}
			
			//Redéplace l'origine du dessin à sa position de départ
			g.translate(-675, -75);
		}
		
		//Dessine l'erreur si il y en a une
		if (!errorMessage.isEmpty()) {
			g.setColor(new Color(252, 67, 73, (int) (255 / disparitionErreur)));
			g.setFont(AssetLoader.errorFont);
			g.drawString(errorMessage, (int) (600 - g.getFontMetrics().getStringBounds(errorMessage, g).getWidth() / 2),
					60);
		}

	}

	/**
	 * @brief Appelée quand le joueur tire
	 * @details On ajoute le tir et verrifie si le tir est validé, alors on peut rejouer
	 */
	public void fire() {
		if (stateManager.addFire(currentTarget)) {
			currentTarget = "";
			finished = false;
		} else {
			finished = true;
		}
	}

	/**
	 * @brief Gestion des évènement souris pressée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if(fireButton.contains(mouseX, mouseY)){
			if (finished) {
				continueButton.mousePressed(mouseX, mouseY);
			} else{
				fireButton.mousePressed(mouseX, mouseY);
			}
		}
	}

	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (continueButton.mouseReleased(mouseX, mouseY) && finished) {
			if (opponent.getBoats().size() == 0) {
				current.setWinner(true);
				stateManager.setCurrentState(new EndGameState(stateManager));
			} else if (!stateManager.getMultiplayer()) {
				opponent.play(stateManager); //L'ordinateur joue
				if (current.getDeadBoats().size() == 5)
					stateManager.setCurrentState(new EndGameState(stateManager));
				else
					stateManager.setCurrentState(new PlayState(stateManager));
			} else {
				stateManager.changePlayers();
				stateManager.setCurrentState(new ChangingState(stateManager));
			}
		}
		
		if (fireButton.mouseReleased(mouseX, mouseY) && !currentTarget.isEmpty() && !finished) {
			fire();
		}

		//Si la souris est en dehors de la grille
		if (!(gridX > 45 && gridX < 495 && gridY > 45 && gridY < 495)) {
			currentTarget = "";
		}
		
		//Si la souris est dans la grille
		if (gridX > 45 && gridX < 495 && gridY > 45 && gridY < 495) {
			//Détermine la colonne et la ligne en fonction de la position de la souris sur la grille
			int columnInt = DrawingUtils.guessCoords(gridX);
			int lineInt = DrawingUtils.guessCoords(gridY);
			if(columnInt == 0 || lineInt == 0)
				return;

			//On transforme le nombre de colonne et le nombre de ligne en une chaine de caractère (ex: IN(1, 1), OUT("A1"))
			char column = (char)(columnInt + 64);
			String line = Integer.toString(lineInt);

			currentTarget = column + line;
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
