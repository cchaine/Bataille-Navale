package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import utils.AssetLoader;
import graphics.Display;
import gui.Button;

/**
 * @file src/state/ChangingState.java
 * @author cchaine
 *
 * @brief Etape de changement de joueur
 * @details Etape permettant la transition entre les deux 
 * joueurs sur un même écran
 */
public class ChangingState extends State{

	private StateManager stateManager;
	private Button continueButton;
	
	private int mouseX, mouseY;
	
	/**
	 * @brief Constructeur
	 * 
	 * @param stateManager		Gestionnaire des étapes du jeu
	 */
	public ChangingState(StateManager stateManager) {
		this.stateManager = stateManager;
		continueButton = new Button(Display.width / 2, Display.height / 2, 100, 100, AssetLoader.continueButton, AssetLoader.continueButtonHovered, AssetLoader.continueButtonPressed);
	}
	
	/**
	 * @brief Mise a jour de la logique du jeu ordonnée par le 
	 * gestionnaire des étapes du jeu
	 * 
	 * @details Met à jour l'état des boutons et la position du curseur
	 */
	@Override
	public void update() {
		// Récupère la position du pointeur sur l'écran et y soustrait la
		// position de la fenêtre sur l'écran pour avoir la position du pointeur
		// sur la fenêtre
		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;
		
		continueButton.update(mouseX, mouseY);
	}

	/**
	 * @brief Mise à jour du rendu ordonnée par le gestionnaire des 
	 * étapes du programme
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	@Override
	public void render(Graphics g) {
		//Dessine l'arrière plan
		g.setColor(Color.DARK_GRAY);
		g.fillRect(10, 10, 1180, 580);
		
		//Affiche le nom du joueur suivant
		g.setColor(Color.WHITE);
		g.setFont(AssetLoader.helvetica30);
		g.drawString("C'est au tour de " + stateManager.getCurrentPlayer().getName() + "...", (int)(Display.width / 2 - g.getFontMetrics().getStringBounds("C'est au tour de " + stateManager.getCurrentPlayer().getName() + "...", g).getWidth() / 2), 200);
		
		continueButton.render(g);
	}

	/**
	 * @brief Gestion des évènement souris pressée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(mouseX + " " + mouseY);
		
		//On verifie si la position de la souris est dans le bouton
		if (continueButton.contains(mouseX, mouseY))
			continueButton.setPressed(true);
		else
			continueButton.setPressed(false);
	}

	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		//On verifie si la position de la souris est dans le bouton
		if (continueButton.contains(mouseX, mouseY))
			stateManager.setCurrentState(new PlayState(stateManager));
		continueButton.setPressed(false);
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
