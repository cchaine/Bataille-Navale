package state;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import core.Computer;

import utils.AssetLoader;
import graphics.Display;
import gui.TextButton;

/**
 * @file src/state/MenuState.java
 * @author cchaine
 *
 * @brief Classe définissant l'étape du menu du programme
 * @details Etape du menu, possibilité de choisir entre deux modes de jeu
 */
public class MenuState extends State {

	private TextButton playButton;
	private TextButton quitButton;
	private TextButton onePlayerButton;
	private TextButton twoPlayerButton;
	private float buttonOffset = 600;
	private float buttonAlpha = 0;

	private StateManager stateManager;

	private boolean modeSelection = false;

	private int wave1X = 0;
	private int wave2X = 1200;
	private int wave3X = 0;
	private int wave4X = 1200;

	private float timeEvolution = 0;
	private float boatY = 400;

	private int mouseX, mouseY;

	/**
	 * @brief Constructeur
	 * 
	 * @param stateManager		Le gestionnaire des étapes du jeu
	 */
	public MenuState(StateManager stateManager) {
		this.stateManager = stateManager;

		// Création des boutons
		playButton = new TextButton(Display.width / 2, (int) buttonOffset, "jouer", "JOUER", new Color(0x68D16A),
				AssetLoader.junebug50);
		quitButton = new TextButton(Display.width / 2, (int) buttonOffset + 160, "quitter", "QUITTER",
				new Color(0xE72623), AssetLoader.junebug50);
		onePlayerButton = new TextButton(Display.width / 2, (int) buttonOffset, "joueur vs ordinateur",
				"JOUEUR VS ORDINATEUR", new Color(0x76FF78), AssetLoader.junebug50);
		twoPlayerButton = new TextButton(Display.width / 2, (int) buttonOffset, "joueur vs joueur", "JOUEUR VS JOUEUR",
				new Color(0x76FF78), AssetLoader.junebug50);
	}

	/**
	 * @brief Mise a jour de la logique du jeu ordonnée par le gestionnaire des étapes du jeu
	 * 
	 * @details Met à jour l'état des boutons ainsi que la position des vagues et du bateau
	 */
	public void update() {
		// Récupère la position du pointeur sur l'écran et y soustrait la
		// position de la fenêtre sur l'écran pour avoir la position du pointeur
		// sur la fenêtre
		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;

		timeEvolution += 0.1f;
		boatY -= Math.cos(timeEvolution) / 3; // Varier la hauteur du bateau
												// suivant une fonction cosinus

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

		if (modeSelection) {
			if (onePlayerButton.contains(mouseX, mouseY))
				onePlayerButton.setHovered(true);
			else
				onePlayerButton.setHovered(false);
			onePlayerButton.setPosition(Display.width / 2 + 90, (int) buttonOffset); // Met à jour la position du bouton en fonction du déplacement vers le haut

			if (twoPlayerButton.contains(mouseX, mouseY))
				twoPlayerButton.setHovered(true);
			else
				twoPlayerButton.setHovered(false);
			twoPlayerButton.setPosition(Display.width / 2, (int) (buttonOffset) + 110);
		} else {
			if (playButton.contains(mouseX, mouseY))
				playButton.setHovered(true);
			else
				playButton.setHovered(false);
			playButton.setPosition(Display.width / 2, (int) buttonOffset);

			if (quitButton.contains(mouseX, mouseY))
				quitButton.setHovered(true);
			else
				quitButton.setHovered(false);
			quitButton.setPosition(Display.width / 2, (int) buttonOffset + 160);
		}
		
	}

	/**
	 * @brief Mise à jour du rendu ordonnée par le gestionnaire des étapes du programme
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	public void render(java.awt.Graphics g) {
		// Dessine les vagues de derrière
		g.drawImage(AssetLoader.waves, wave3X, 515, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave4X, 515, 1200, 100, null);

		// Dessine le bateau
		g.drawImage(AssetLoader.boat, 20, (int) boatY, 400, 200, null);

		// Dessine les vagues de devant
		g.drawImage(AssetLoader.waves, wave1X, 535, 1200, 100, null);
		g.drawImage(AssetLoader.waves, wave2X, 535, 1200, 100, null);

		// Dessine le titre au milieu de l'écran
		g.setFont(AssetLoader.junebug60);
		g.setColor(new Color(0x002366));
		g.drawString("BATAILLE NAVALE",
				(int) (600 - g.getFontMetrics().getStringBounds("BATAILLE NAVALE", g).getWidth() / 2), 100);

		g.setFont(AssetLoader.junebug50);

		if (modeSelection) {
			onePlayerButton.render(g, buttonAlpha);
			twoPlayerButton.render(g, buttonAlpha);
		} else {
			playButton.render(g, buttonAlpha);
			quitButton.render(g, buttonAlpha);
		}
	}

	/**
	 * @brief Gestion des évènement souris pressée
	 * 
	 * @param e		Information sur l'évènement
	 */
	public void mousePressed(MouseEvent e) {
		if (modeSelection) {
			// On verifie si la position de la souris est dans le bouton
			if (onePlayerButton.contains(mouseX, mouseY))
				onePlayerButton.setPressed(true);
			else
				onePlayerButton.setPressed(false);

			if (twoPlayerButton.contains(mouseX, mouseY))
				twoPlayerButton.setPressed(true);
			else
				twoPlayerButton.setPressed(true);
		} else {
			if (playButton.contains(mouseX, mouseY))
				playButton.setPressed(true);
			else
				playButton.setPressed(false);

			if (quitButton.contains(mouseX, mouseY))
				quitButton.setPressed(true);
			else
				quitButton.setPressed(false);
		}
	}

	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param e		Information sur l'évènement
	 */
	public void mouseReleased(MouseEvent e) {
		if (modeSelection) {
			// On verifie si la position de la souris est dans le bouton
			if (onePlayerButton.contains(mouseX, mouseY)) {
				stateManager.getCore().getPlayers().add(new Computer("Ordinateur"));
				stateManager.setCurrentState(new StartingState(stateManager));
			}

			if (twoPlayerButton.contains(mouseX, mouseY)) {
				stateManager.setMultiplayer(true);
				stateManager.setCurrentState(new StartingState(stateManager));
			}
		} else {
			if (quitButton.contains(mouseX, mouseY))
				System.exit(0);

			if (playButton.contains(mouseX, mouseY)) {
				modeSelection = true;
				buttonOffset = 600;
				buttonAlpha = 0;
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

}
