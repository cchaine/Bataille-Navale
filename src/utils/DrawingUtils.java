package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import core.Boat;
import core.Player;

public class DrawingUtils {

	public static Rectangle2D generateBoatBounds(Boat boat) {
		String firstCase = boat.getCases().get(0);
		String lastCase = boat.getCases().get(boat.getCases().size() - 1);
		int boatDirection = boat.getDirection();

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
			 * La longueur du bateau est égale à la longueur du torpilleur additionnée aux nombres de cases du bateau moins deux et multiplié par 45 (la taille d'une case)
			 * La largeur est de 36 pixels
			 */
			boatBounds = new Rectangle((firstCase.charAt(0) - 64) * 45 + 600 + 5,
					(Integer.parseInt(firstCase.substring(1)) * 45) + 66 + 5, 81 + (boat.getType().getSize() - 2) * 45,
					36);
			break;

		case 2:
			boatBounds = new Rectangle(5 + ((firstCase.charAt(0) - 64) * 45) + 600,
					(Integer.parseInt(firstCase.substring(1)) * 45) + 66 + 5, 37,
					81 + (boat.getType().getSize() - 2) * 45);
			break;

		case 3:
			boatBounds = new Rectangle((5 + ((lastCase.charAt(0) - 64) * 45)) + 600,
					(Integer.parseInt(lastCase.substring(1)) * 45) + 66 + 5, 81 + (boat.getType().getSize() - 2) * 45,
					36);
			break;

		case 4:
			boatBounds = new Rectangle(5 + ((lastCase.charAt(0) - 64) * 45) + 600,
					(Integer.parseInt(lastCase.substring(1)) * 45) + 66 + 5, 37,
					81 + (boat.getType().getSize() - 2) * 45);
			break;
		}

		return boatBounds;
	}

	/**
	 * @brief Dessine la grille de jeu
	 * @details Affiche la grille et écrit les lettres
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	public static void drawGrid(Graphics g) {
		g.drawImage(AssetLoader.grid, 0, 0, null);
		g.setFont(AssetLoader.helvetica35);
		g.setColor(new Color(0x333333, false));
		g.drawString("A", 55, 35);
		g.drawString("B", 100, 35);
		g.drawString("C", 145, 35);
		g.drawString("D", 190, 35);
		g.drawString("E", 235, 35);
		g.drawString("F", 281, 35);
		g.drawString("G", 324, 35);
		g.drawString("H", 370, 35);
		g.drawString("I", 422, 35);
		g.drawString("J", 463, 35);

		g.drawString("1", 12, 122 - 42);
		g.drawString("2", 12, 167 - 42);
		g.drawString("3", 12, 212 - 42);
		g.drawString("4", 12, 257 - 42);
		g.drawString("5", 12, 302 - 42);
		g.drawString("6", 12, 347 - 42);
		g.drawString("7", 12, 392 - 42);
		g.drawString("8", 12, 437 - 42);
		g.drawString("9", 12, 482 - 42);
		g.drawString("10", 2, 527 - 42);
	}

	public static void drawBoats(Graphics g, ArrayList<Boat> boats) {
		//Dessiné les bateaux déjà placés
		for (int i = 0; i < boats.size(); i++) {
			Boat boat = boats.get(i);

			//Le rectangle représentant le bateau. A placer sur la grille
			Rectangle2D boatBounds = DrawingUtils.generateBoatBounds(boat);

			//Dessine le bateau aux coordonnées précédentes, en enlevant la position de la grille, car l'origine du dessin est déplacé
			g.fillRect((int) (boatBounds.getX()) - 600, (int) (boatBounds.getY()) - 66, (int) boatBounds.getWidth(),
					(int) boatBounds.getHeight());
		}
	}

	public static void drawShotHistory(Graphics g, Player current) {
		//Affiche les cases touch�es par l'ennemi
		for (int i = 0; i < current.getWinHistory().size(); i++) {
			String cases = current.getWinHistory().get(i);

			g.drawImage(AssetLoader.fire, 3 + ((cases.charAt(0) - 64) * 45),
					4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
		}

		//Affiche les cases dans l'eau de l'ennemi
		for (int i = 0; i < current.getFailHistory().size(); i++) {
			String cases = current.getFailHistory().get(i);

			g.drawImage(AssetLoader.water, 3 + ((cases.charAt(0) - 64) * 45),
					4 + (Integer.parseInt(cases.substring(1)) * 45), 39, 39, null);
		}
	}

	public static void drawGridItems(Graphics g, Player current, Player opponent) {
		//Affiche les bateaux vivants sur la grille
		g.setColor(new Color(51, 51, 51, 240));//Gris fonc�
		drawBoats(g, current.getBoats());
		
		g.setColor(new Color(151, 151, 151, 240));//Gris clair
		drawBoats(g, current.getDeadBoats());

		drawShotHistory(g, opponent);
	}
	
	/**
	 * @brief Transforme une coordonnée en un index
	 *
	 * @param coord		La coordonnée à transformer
	 * @return		Renvoi l'index
	 */
	public static int guessCoords(int coord)
	{
		//Vérifie la position de la souris et détermine dans quelle colonne ou ligne elle est (une case fait 45 pixels) 
		if (coord > 45 && coord < 90)
			return 1;
		else if (coord > 90 && coord < 135)
			return 2;
		else if (coord > 135 && coord < 180)
			return 3;
		else if (coord > 180 && coord < 225)
			return 4;
		else if (coord > 225 && coord < 270)
			return 5;
		else if (coord > 270 && coord < 315)
			return 6;
		else if (coord > 315 && coord < 360)
			return 7;
		else if (coord > 360 && coord < 405)
			return 8;
		else if (coord > 405 && coord < 450)
			return 9;
		else if (coord > 450 && coord < 495)
			return 10;
		else return 0;
	}
}
