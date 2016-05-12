package state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import core.Boat;
import core.BoatType;
import core.Computer;
import core.Player;
import utils.AssetLoader;
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

		randomButton = new Button(1145, 518, 60, 60, AssetLoader.randomButton, AssetLoader.randomButtonHovered,
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

		drawGrid(g);

		//Dessiné les bateaux déjà placés
		for (int i = 0; i < boats.size(); i++) {
			//Déplace l'origine du dessin en haut à gauche de la grille
			g.translate(600, 42);

			if (trashButton.isActive())
				g.setColor(new Color(232, 47, 53, 240));//Rouge
			else
				g.setColor(new Color(0, 35, 102, 240));//Gris

			Boat boat = boats.get(i);

			String firstCase = boat.getCases().get(0);
			String lastCase = boat.getCases().get(boat.getCases().size() - 1);
			int boatDirection = boats.get(i).getDirection();
			
			//Le rectangle représentant le bateau. A placer sur la grille
			Rectangle2D boatBounds = null;

			switch (boatDirection) {
			case 1:
				/* Le rectangle à des coordonnées précises :
				 * Pour x:
				 * 		-Déduit l'index de la colonne grâce à la table ASCII (La valeur du caractère soustrait à la valeur de A)
				 * 		-Multiplie par 45 car chaque case fait 45 pixels
				 * 		-Ajoute 600 car la grille est à 605 pixels en x
				 * 		-Ajoute 5 car le rectangle à un léger offset
				 * Pour y:
				 * 		-Transforme la colonne alors stockée en String en un Integer
				 * 		-Multiplie par 45 car chaque case fait 45 pixels
				 * 		-Ajoute 66 car la grille est à 66 pixels en y
				 * 		-Ajoute 5 car le rectangle à un léger offset
				 * La largeur du bateau est égale à la largeur du torpilleur additionnée aux nombres de cases du bateau moins deux et multiplié par 45 (la taille d'une case)
				 * L'épaisseur est de 36 pixels
				 */
				boatBounds = new Rectangle((firstCase.charAt(0) - 64) * 45 + 600 + 5,
						(Integer.parseInt(firstCase.substring(1)) * 45) + 66 + 5,
						81 + (boat.getType().getSize() - 2) * 45, 36);
				break;

			case 2:
				boatBounds = new Rectangle(5 + ((firstCase.charAt(0) - 64) * 45) + 600,
						(Integer.parseInt(firstCase.substring(1)) * 45) + 66 + 5, 37,
						81 + (boat.getType().getSize() - 2) * 45);
				break;

			case 3:
				boatBounds = new Rectangle((5 + ((lastCase.charAt(0) - 64) * 45)) + 600,
						(Integer.parseInt(lastCase.substring(1)) * 45) + 66 + 5,
						81 + (boat.getType().getSize() - 2) * 45, 36);
				break;

			case 4:
				boatBounds = new Rectangle(5 + ((lastCase.charAt(0) - 64) * 45) + 600,
						(Integer.parseInt(lastCase.substring(1)) * 45) + 66 + 5, 37,
						81 + (boat.getType().getSize() - 2) * 45);
				break;
			}

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

			//Redéplace l'origine du dessin à sa position précédente
			g.translate(-600, -42);
		}

		//Si tous les bateaux sont placés ont peut continuer
		if (boats.size() == 5) {
			continueButton.render(g);
		}

		this.mouseRender(g);
	}

	/**
	 * @brief Dessine la grille de jeu
	 * @details Affiche la grille et écrit les lettres
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	private void drawGrid(Graphics g) {
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
	}

	public void mouseRender(Graphics g) {
		BoatType type = null;
		if (porteAvion.isActive()) {
			type = BoatType.PORTEAVION;
		} else if (croiseur.isActive()) {
			type = BoatType.CROISEUR;
		} else if (contreTorpilleur.isActive()) {
			type = BoatType.CONTRETORPILLEUR;
		} else if (sousMarin.isActive()) {
			type = BoatType.SOUSMARIN;
		} else if (torpilleur.isActive()) {
			type = BoatType.TORPILLEUR;
		} else {
			return;
		}

		int direction = this.getDirection();
		int gridX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int gridY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;

		g.translate(600, 42);

		int column;
		int line;

		if (gridX > 45 && gridX < 90) {
			column = 1;
		} else if (gridX > 90 && gridX < 135) {
			column = 2;
		} else if (gridX > 135 && gridX < 180) {
			column = 3;
		} else if (gridX > 180 && gridX < 225) {
			column = 4;
		} else if (gridX > 225 && gridX < 270) {
			column = 5;
		} else if (gridX > 270 && gridX < 315) {
			column = 6;
		} else if (gridX > 315 && gridX < 360) {
			column = 7;
		} else if (gridX > 360 && gridX < 405) {
			column = 8;
		} else if (gridX > 405 && gridX < 450) {
			column = 9;
		} else if (gridX > 450 && gridX < 495) {
			column = 10;
		} else {
			return;
		}

		if (gridY > 45 && gridY < 90) {
			line = 1;
		} else if (gridY > 90 && gridY < 135) {
			line = 2;
		} else if (gridY > 135 && gridY < 180) {
			line = 3;
		} else if (gridY > 180 && gridY < 225) {
			line = 4;
		} else if (gridY > 225 && gridY < 270) {
			line = 5;
		} else if (gridY > 270 && gridY < 315) {
			line = 6;
		} else if (gridY > 315 && gridY < 360) {
			line = 7;
		} else if (gridY > 360 && gridY < 405) {
			line = 8;
		} else if (gridY > 405 && gridY < 450) {
			line = 9;
		} else if (gridY > 450 && gridY < 495) {
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
			g.fillRect(5 + 45 * column - (45 * (type.getSize() - 1)), 5 + 45 * line, 81 + 45 * (type.getSize() - 2),
					36);
			break;

		case 4:
			g.fillRect(5 + 45 * column, 5 + 45 * line - (45 * (type.getSize() - 1)), 36,
					81 + 45 * (type.getSize() - 2));
			break;
		}
	}

	private void addBoat(int x, int y, BoatType type) {
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

		boat = new Boat(type, column + line, direction);

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

	@Override
	public void mousePressed(MouseEvent e) {
		randomButton.mousePressed(mouseX, mouseY);
		trashButton.mousePressed(mouseX, mouseY);
		
		nameField.mousePressed(mouseX, mouseY);
		
		if (boats.size() == 5)
			continueButton.mousePressed(mouseX, mouseY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int gridX = MouseInfo.getPointerInfo().getLocation().x - Display.frame.getLocationOnScreen().x - 601;
		int gridY = MouseInfo.getPointerInfo().getLocation().y - Display.frame.getLocationOnScreen().y - 66;

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

		porteAvion.mouseReleased(mouseX, mouseY);
		croiseur.mouseReleased(mouseX, mouseY);
		contreTorpilleur.mouseReleased(mouseX, mouseY);
		sousMarin.mouseReleased(mouseX, mouseY);
		torpilleur.mouseReleased(mouseX, mouseY);

		if (randomButton.contains(mouseX, mouseY)) {
			boats = Computer.generateBoatRandom();
			porteAvion.setUsed(true);
			croiseur.setUsed(true);
			contreTorpilleur.setUsed(true);
			sousMarin.setUsed(true);
			torpilleur.setUsed(true);
			randomButton.setPressed(false);
		}

		if (trashButton.contains(mouseX, mouseY))
			trashButton.setActive(true);
		else if (!(gridX > 45 && gridX < 495 && gridY > 45 && gridY < 495)) {
			trashButton.setActive(false);
			trashButton.setPressed(false);
		}

		if (trashButton.isActive()) {
			for (int i = 0; i < boats.size(); i++) {
				Boat boat = boats.get(i);
				int boatsSizeBefore = boats.size();

				String firstCase = boat.getCases().get(0);
				String lastCase = boat.getCases().get(boat.getCases().size() - 1);
				int boatDirection = boats.get(i).getDirection();

				switch (boatDirection) {
				case 1:
					if (mouseX > (firstCase.charAt(0) - 64) * 45 + 605
							&& mouseX < (firstCase.charAt(0) - 64) * 45 + 605 + 81 + (boat.getType().getSize() - 2) * 45
							&& mouseY > 66 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45)
							&& mouseY < 66 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45) + 36) {
						boats.remove(i);
					}
					break;

				case 2:
					if (mouseX > 5 + ((firstCase.charAt(0) - 64) * 45) + 600
							&& mouseX < 5 + ((firstCase.charAt(0) - 64) * 45) + 600 + 37
							&& mouseY > 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45)
							&& mouseY < 67 + 5 + (Integer.parseInt(firstCase.substring(1)) * 45) + 81 + (boat.getType().getSize() - 2) * 45) {
						boats.remove(i);
					}
					break;

				case 3:
					if (mouseX > (5 + ((lastCase.charAt(0) - 64) * 45)) + 600
							&& mouseX < (5 + ((lastCase.charAt(0) - 64) * 45)) + 600 + 81 + (boat.getType().getSize() - 2) * 45
							&& mouseY > 66 + (5 + (Integer.parseInt(lastCase.substring(1)) * 45))
							&& mouseY < 66 + (5 + (Integer.parseInt(lastCase.substring(1)) * 45)) + 36) {
						boats.remove(i);
					}
					break;

				case 4:
					if (mouseX > 5 + ((lastCase.charAt(0) - 64) * 45) + 600
							&& mouseX < 5 + ((lastCase.charAt(0) - 64) * 45) + 600 + 37
							&& mouseY > 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45)
							&& mouseY < 67 + 5 + (Integer.parseInt(lastCase.substring(1)) * 45) + 81 + (boat.getType().getSize() - 2) * 45) {
						boats.remove(i);
					}
					break;
				}

				if (boatsSizeBefore > boats.size()) {
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
		
		if(boats.size() == 5){
			if(continueButton.mouseReleased(mouseX, mouseY))
			{
				if(nameField.isEmpty() || nameField.getText().equals("Entrez votre nom..."))
				{
					errorMessage = "Veuillez entrer votre nom...";
					return;
				}
				if(stateManager.getMultiplayer() && !stateManager.getCore().getPlayers().isEmpty())
				{
					if(stateManager.getCore().getPlayers().get(0).getName().equals(nameField.getText())){
						errorMessage = "Ce nom est déjà pris...";
						return;
					}
				}
				
				stateManager.getCore().getPlayers().add(new Player(nameField.getText(), boats));
				
				if(stateManager.getCore().getPlayers().size() == 1 && stateManager.getMultiplayer())
				{
					stateManager.setCurrentState(new StartingState(stateManager));
				}else
				{
					if(stateManager.getMultiplayer())
					{
						stateManager.setCurrent(stateManager.getCore().getPlayers().get(0));
						stateManager.setCurrentState(new ChangingState(stateManager));
					}else{
						stateManager.setCurrent(stateManager.getCore().getPlayers().get(1));
						stateManager.setCurrentState(new PlayState(stateManager));
						
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (nameField.isActive()) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				nameField.setActive(false);
			}

			int keyCode = e.getKeyCode();
			if ((keyCode >= 65 && keyCode <= 90 || keyCode >= 97 && keyCode <= 122)
					&& nameField.getText().length() < 22) {
				nameField.addLetter(e.getKeyChar());
			}
			if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !nameField.getText().isEmpty()) {
				nameField.backspace();
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
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
