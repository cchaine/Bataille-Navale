package utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import core.Boat;

public class DrawingUtils {
	
	public static Rectangle2D generateBoatBounds(Boat boat)
	{
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
		
		return boatBounds;
	}
	
	/**
	 * @brief Dessine la grille de jeu
	 * @details Affiche la grille et écrit les lettres
	 * 
	 * @param g		Objet utilitaire de dessin
	 */
	public static void drawGrid(Graphics g, int x, int y)
	{
		g.drawImage(AssetLoader.grid, x, y, null);
		g.setFont(AssetLoader.helvetica35);
		g.setColor(new Color(0x333333, false));
		g.drawString("A", 55 + x, y + 35);
		g.drawString("B", 100 + x, y + 35);
		g.drawString("C", 145 + x, y + 35);
		g.drawString("D", 190 + x, y + 35);
		g.drawString("E", 235 + x, y + 35);
		g.drawString("F", 281 + x, y + 35);
		g.drawString("G", 324 + x, y + 35);
		g.drawString("H", 370 + x, y + 35);
		g.drawString("I", 422 + x, y + 35);
		g.drawString("J", 463 + x, y + 35);

		g.drawString("1", x + 12, 122 + y - 42);
		g.drawString("2", x + 12, 167 + y - 42);
		g.drawString("3", x + 12, 212 + y - 42);
		g.drawString("4", x + 12, 257 + y - 42);
		g.drawString("5", x + 12, 302 + y - 42);
		g.drawString("6", x + 12, 347 + y - 42);
		g.drawString("7", x + 12, 392 + y - 42);
		g.drawString("8", x + 12, 437 + y - 42);
		g.drawString("9", x + 12, 482 + y - 42);
		g.drawString("10", x + 2, 527 + y - 42);
	}
}
