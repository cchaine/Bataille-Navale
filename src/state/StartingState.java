package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import core.Boat;
import core.BoatType;
import core.Computer;
import core.Player;
import utils.AssetLoader;
import utils.DrawingUtils;
import graphics.Display;
import gui.BoatButton;
import gui.Button;
import gui.TextField;
import gui.TriStateButton;

/**
 * @file src/state/StartingState.java
 * @author cchaine
 *
 * @brief Classe définissant l'étape de placement des bateaux
 * @details Création du joueur et placement des bateaux
 */
public class StartingState extends State {

	private StateManager stateManager;

	private BoatButton porteAvion;
	private BoatButton croiseur;
	private BoatButton contreTorpilleur;
	private BoatButton sousMarin;
	private BoatButton torpilleur;

	private TextField nameField;

	private Button randomButton;
	private TriStateButton trashButton;

	private String errorMessage;
	private float errorTimeEvolution = 1;

	private Button continueButton;

	private int direction = 1;
	private int changedDirection = 1;

	private int mouseX, mouseY;

	private ArrayList<Boat> boats = new ArrayList<>();

	/**
	 * @brief Constructeur
	 * 
	 * @param stateManager		Le gestionnaire des étapes du jeu
	 */
	public StartingState(StateManager stateManager) {
		this.stateManager = stateManager;

		//Création des boutons de choix du type de bateau
		porteAvion = new BoatButton(320, 213, BoatType.PORTEAVION);
		croiseur = new BoatButton(320, 260, BoatType.CROISEUR);
		contreTorpilleur = new BoatButton(320, 307, BoatType.CONTRETORPILLEUR);
		sousMarin = new BoatButton(320, 354, BoatType.SOUSMARIN);
		torpilleur = new BoatButton(320, 401, BoatType.TORPILLEUR);

		nameField = new TextField(48, 72, 515, 114);

		randomButton = new Button(1145, 525, 60, 60, AssetLoader.randomButton, AssetLoader.randomButtonHovered,
				AssetLoader.randomButtonPressed);
		trashButton = new TriStateButton(555, 518, 70, 70, AssetLoader.trash, AssetLoader.trashClickedImg,
				AssetLoader.trashPressedImg);

		continueButton = new Button(1145, 90, 60, 60, AssetLoader.continueButton, AssetLoader.continueButtonHovered,
				AssetLoader.continueButtonPressed);

		errorMessage = "";
	}

	/**
	 * @brief Mise a jour de la logique du jeu ordonnée par le gestionnaire des étapes du jeu
	 * 
	 * @details Met à jour l'état des boutons ainsi que l'affichage du bateau fantome sous la souris
	 */
	@Override
	public void update() {
		// Récupère la position du pointeur sur l'écran et y soustrait la
		// position de la fenêtre sur l'écran pour avoir la position du pointeur
		// sur la fenêtre
		mouseX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x;
		mouseY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y;

		porteAvion.update(mouseX, mouseY);
		croiseur.update(mouseX, mouseY);
		contreTorpilleur.update(mouseX, mouseY);
		sousMarin.update(mouseX, mouseY);
		torpilleur.update(mouseX, mouseY);

		randomButton.update(mouseX, mouseY);
		continueButton.update(mouseX, mouseY);

		if (nameField.contains(mouseX, mouseY))
			nameField.setHovered(true);
		else
			nameField.setHovered(false);

		//Si il y a un message d'erreur, incrémenter le temps d'évolution pour faire disparaitre le message
		if (!errorMessage.isEmpty()) {
			errorTimeEvolution += 0.02;
			if (errorTimeEvolution >= 3) {
				errorTimeEvolution = 1;
				errorMessage = "";
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
		//Fait un rendu des boutons
		porteAvion.render(g);
		croiseur.render(g);
		contreTorpilleur.render(g);
		sousMarin.render(g);
		torpilleur.render(g);

		nameField.render(g);

		randomButton.render(g);

		if (!boats.isEmpty()) {
			trashButton.render(g);
		}

		if (!errorMessage.isEmpty()) {
			//Défini la couleur du message d'erreur. L'alpha dépend de l'evolution du temps du message
			//Ainsi, plus le temps augmente, plus le message disparait
			g.setColor(new Color(252, 67, 73, (int) (255 / errorTimeEvolution)));//Rouge
			g.setFont(AssetLoader.errorFont);
			g.drawString(errorMessage, 525, 25);
		}

		//Déplace l'origine du dessin aux coordonnées de la grille
		g.translate(600, 42);
		
		//Dessine la grille
		DrawingUtils.drawGrid(g);

		//Dessiné les bateaux déjà placés
		for (int i = 0; i < boats.size(); i++) {
			if (trashButton.isActive())
				g.setColor(new Color(232, 47, 53, 240));//Rouge
			else
				g.setColor(new Color(0, 35, 102, 240));//Gris

			Boat boat = boats.get(i);
			
			//Le rectangle représentant le bateau. A placer sur la grille
			Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);

			//Si la poubelle et que la sourie est dessus
			if (trashButton.isActive() && boatBounds.contains(mouseX, mouseY))
				g.setColor(new Color(255, 87, 93));//Rouge clair

			//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
			g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
					(int) boatBounds.getHeight());

			if (trashButton.isActive())
				//Dessine un croix
				g.drawImage(AssetLoader.delete, (int) (boatBounds.getX() - 600 + boatBounds.getWidth() / 2 - 15),
						(int) (boatBounds.getY() - 66 + boatBounds.getHeight() / 2 - 15), 30, 30, null);
		}
		
		//Redéplace l'origine du dessin à sa position précédente
		g.translate(-600, -42);

		//Si tous les bateaux sont placés ont peut continuer
		if (boats.size() == 5) {
			continueButton.render(g);
		}

		this.mouseRender(g);
	}

	/**
	 * @brief Fait un rendu des rectangles fantomes à l'écran
	 * @details En fonction de la position de la souris / bateau sélectionné, dessiner rectangle fantome
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	public void mouseRender(Graphics g) {
		BoatType type = null;
		
		//Détermine le type de bateau qui est selectionné
		if (porteAvion.isActive())
			type = BoatType.PORTEAVION;
		else if (croiseur.isActive())
			type = BoatType.CROISEUR;
		else if (contreTorpilleur.isActive())
			type = BoatType.CONTRETORPILLEUR;
		else if (sousMarin.isActive())
			type = BoatType.SOUSMARIN;
		else if (torpilleur.isActive())
			type = BoatType.TORPILLEUR;
		else return;//Si aucun bateau n'est sélectionné, ne pas afficher de fantome

		//Si le joueur a changé la direction, l'utiliser
		if(direction != changedDirection)
			direction = changedDirection;
		
		//Les coordonnées de la souris dont l'origine est en haut à gauche de la case A1
		int gridX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int gridY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;

		//Déplace l'origine du dessin en haut à gauche de la case A1
		g.translate(600, 42);

		int column;
		int line;

		//Récupère la colonne et la ligne en fonction de la position de la souris
		column = DrawingUtils.guessCoords(gridX);
		line = DrawingUtils.guessCoords(gridY);
		if(column == 0 || line == 0)
			return;

		switch (direction) {
		case 1:
			//Calcule la dernière case du bateau et vérifie qu'elle est dans la grille
			if (column + type.getSize() - 1 > 10)
				//Sinon le bateau ne rentre pas dans ce sens et donc l'inverser
				this.setDirection(3);
			break;

		case 2:
			if (line + type.getSize() - 1 > 10)
				this.setDirection(4);
			break;

		case 3:
			if (column - type.getSize() + 1 < 1)
				this.setDirection(1);
			break;

		case 4:
			if (line - type.getSize() + 1 < 1)
				this.setDirection(2);
			break;
		}

		g.setColor(new Color(22, 22, 22, 100));
		
		switch (direction) {
		case 1:
			/*
			 * Pour x et y:
			 * 		-Le rectangle à un leger offset de 5 pixels
			 * 		-Chaque cases font 45 pixels
			 * La longueur:
			 * 		-longueur du torpilleur additionnée aux nombres de cases du bateau moins deux et multiplié par 45 (la taille d'une case)
			 */
			g.fillRect(5 + 45 * column, 5 + 45 * line, 81 + 45 * (type.getSize() - 2), 36);
			break;

		case 2:
			g.fillRect(5 + 45 * column, 5 + 45 * line, 36, 81 + 45 * (type.getSize() - 2));
			break;

		case 3:
			//On enlève la taille du bateau au x pour qu'il apparaisse à gauche
			g.fillRect(5 + 45 * column - (45 * (type.getSize() - 1)), 5 + 45 * line, 81 + 45 * (type.getSize() - 2),
					36);
			break;

		case 4:
			//On enlève la taille du bateau au y pour qu'il apparaisse en haut
			g.fillRect(5 + 45 * column, 5 + 45 * line - (45 * (type.getSize() - 1)), 36,
					81 + 45 * (type.getSize() - 2));
			break;
		}
		
		g.translate(-600, -42);
	}

	/**
	 * @brief Ajoute un bateau
	 * @details Déduit de la position de la souris les coordonnées du bateau et l'ajoute
	 * 
	 * @param x		L'abscisse de la souris
	 * @param y		L'ordonnée de la souris
	 * @param type		Le type du bateau à ajouter
	 */
	private void addBoat(int gridX, int gridY, BoatType type) {
		Boat boat = null;
		int column;
		int line;

		//Récupère la colonne et la ligne en fonction de la position de la souris
		column = DrawingUtils.guessCoords(gridX);
		line = DrawingUtils.guessCoords(gridY);
		if(column == 0 || line == 0)
			return;

		//Crée un nouveau bateau à ces coordonnées (le x est la transformation de la column en une lettre ex: la colonne 1 doit etre A qui en ASCII est 65)
		boat = new Boat(type,(char)(column + 64) + Integer.toString(line), direction);

		//On verrifie que aucun bateau n'est déjà la
		for (int i = 0; i < boats.size(); i++) {
			for (int j = 0; j < boat.getCases().size(); j++) {
				if (boats.get(i).isOnCase(boat.getCases().get(j))) {
					errorMessage = "Il y a déjà un bateau là";
					return;
				}
			}
		}

		boats.add(boat);
		
		//Marque le bouton du bateau comme utilisé
		switch (boat.getType()) {
		case PORTEAVION:
			porteAvion.setUsed(true);
			break;
			
		case CROISEUR:
			croiseur.setUsed(true);
			break;

		case CONTRETORPILLEUR:
			contreTorpilleur.setUsed(true);
			break;

		case SOUSMARIN:
			sousMarin.setUsed(true);
			break;

		case TORPILLEUR:
			torpilleur.setUsed(true);
			break;
		}
	}

	/**
	 * @brief Gestion des évènement souris pressée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		//Mise a jour des boutons
		randomButton.mousePressed(mouseX, mouseY);
		trashButton.mousePressed(mouseX, mouseY);
		
		nameField.mousePressed(mouseX, mouseY);
		
		if (boats.size() == 5)
			continueButton.mousePressed(mouseX, mouseY);
	}

	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		//Les coordonnées de la souris avec l'origine en haut à gauche de la case A1
		int gridX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int gridY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;
		
		//Si la souris est dans la grille et que un bouton de bateau est actif, ajouter un bateau avec le type correspondant
		if ((gridX > 0 && gridX < 1096 && gridY > 0 && gridY < 538) && porteAvion.isActive())
			addBoat(gridX, gridY, BoatType.PORTEAVION);
		else if ((gridX > 0 && gridX < 1096 && gridY > 0 && gridY < 538) && croiseur.isActive())
			addBoat(gridX, gridY, BoatType.CROISEUR);
		else if ((gridX > 0 && gridX < 1096 && gridY > 0 && gridY < 538) && contreTorpilleur.isActive())
			addBoat(gridX, gridY, BoatType.CONTRETORPILLEUR);
		else if ((gridX > 0 && gridX < 1096 && gridY > 0 && gridY < 538) && sousMarin.isActive())
			addBoat(gridX, gridY, BoatType.SOUSMARIN);
		else if ((gridX > 0 && gridX < 1096 && gridY > 0 && gridY < 538) && torpilleur.isActive())
			addBoat(gridX, gridY, BoatType.TORPILLEUR);

		//Met a jour les boutons
		porteAvion.mouseReleased(mouseX, mouseY);
		croiseur.mouseReleased(mouseX, mouseY);
		contreTorpilleur.mouseReleased(mouseX, mouseY);
		sousMarin.mouseReleased(mouseX, mouseY);
		torpilleur.mouseReleased(mouseX, mouseY);

		if (randomButton.contains(mouseX, mouseY)) {
			//Crée des bateaux aléatoirement
			boats = Computer.generateBoatRandom();
			
			//Défini les boutons de bateau comme usés
			porteAvion.setUsed(true);
			croiseur.setUsed(true);
			contreTorpilleur.setUsed(true);
			sousMarin.setUsed(true);
			torpilleur.setUsed(true);
			randomButton.setPressed(false);
		}

		if (trashButton.contains(mouseX, mouseY))
			trashButton.setActive(true);
		//Si la souris clique en dehors de la grille, desactiver la poubelle
		else if (!(gridX > 45 && gridX < 495 && gridY > 45 && gridY < 495)) {
			trashButton.setActive(false);
			trashButton.setPressed(false);
		}

		if (trashButton.isActive()) {
			for (int i = 0; i < boats.size(); i++) {
				Boat boat = boats.get(i);

				Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);
				
				//Si la souris est a l'intérieur du rectangle du bateau, le supprimer
				if (boatBounds.contains(mouseX, mouseY)){
					boats.remove(i);
					
					//Réactive le bouton du type supprimé
					switch (boat.getType()) {
					case PORTEAVION:
						porteAvion.setUsed(false);
						break;

					case CROISEUR:
						croiseur.setUsed(false);
						break;

					case CONTRETORPILLEUR:
						contreTorpilleur.setUsed(false);
						break;

					case SOUSMARIN:
						sousMarin.setUsed(false);
						break;

					case TORPILLEUR:
						torpilleur.setUsed(false);
						break;
					}
				}
			}
		}
		
		//Si tous les bateaux sont placés
		if(boats.size() == 5){
			if(continueButton.mouseReleased(mouseX, mouseY))
			{
				if(nameField.isEmpty() || nameField.getText().equals("Entrez votre nom..."))
				{
					errorMessage = "Veuillez entrer votre nom...";
					return;
				}
				
				//Si il y a déjà un joueur créé et que on est en multijoueur, vérifier que le nouveau n'a pas le même nom
				if(stateManager.getMultiplayer() && !stateManager.getCore().getPlayers().isEmpty())
				{
					if(stateManager.getCore().getPlayers().get(0).getName().equals(nameField.getText())){
						errorMessage = "Ce nom est déjà pris...";
						return;
					}
				}
				
				stateManager.getCore().getPlayers().add(new Player(nameField.getText(), boats));
				
				//Si en multijoueur et que seulement un joueur enregistré (le nouveau), recréer un joueur
				if(stateManager.getCore().getPlayers().size() == 1 && stateManager.getMultiplayer())
				{
					stateManager.setCurrentState(new StartingState(stateManager));
				}else
				{
					if(stateManager.getMultiplayer())
					{
						stateManager.setCurrent(stateManager.getCore().getPlayers().get(0));
						stateManager.setOpponent(stateManager.getCore().getPlayers().get(1));
						
						//Crée l'état changement de joueur, car sinon le dernier voit le jeu du premier
						stateManager.setCurrentState(new ChangingState(stateManager));
					}else{
						stateManager.setCurrent(stateManager.getCore().getPlayers().get(1));
						stateManager.setOpponent(stateManager.getCore().getPlayers().get(0));
						stateManager.setCurrentState(new PlayState(stateManager));
					}
				}
			}
		}
	}
	
	/**
	 * @brief Gestion des évènements de touche appuyée
	 * 
	 * @param e		Information sur l'évènement
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		if (nameField.isActive()) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				nameField.setActive(false);

			int keyCode = e.getKeyCode();
			//Si les lettres sont entre a et z et entre A et Z (ASCII) et que le texte fait pas plus de 22 caractères
			if ((keyCode >= 65 && keyCode <= 90 || keyCode >= 97 && keyCode <= 122)
					&& nameField.getText().length() < 22)
				nameField.addLetter(e.getKeyChar());
			
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !nameField.getText().isEmpty())
				nameField.backspace();
		}
		
		//Change la direction avec les flèches
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
			changedDirection = 2;
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			changedDirection = 3;
		if (e.getKeyCode() == KeyEvent.VK_UP)
			changedDirection = 4;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			changedDirection = 1;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
		this.changedDirection = direction;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
