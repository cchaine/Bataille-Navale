package core;

import java.util.ArrayList;

/**
 * @file src/core/Boat.java
 * @author alexBrousse
 *
 * @brief Classe Maîtresse Bateau
 * @details Stocke les informations du bateau et fourni des méthodes 
 * utilitaires
 */
public class Boat {
	
	private BoatType type;
	private ArrayList<String> cases = new ArrayList<>();
	private int direction;
	private boolean isAlive = true;
	private int vie;
	
	/**
	 * @brief Constructeur
	 *  
	 * @param type		Le type du bateau
	 * @param firstCase	Première case du bateau
	 * @param lastCase	La dernière case du bateau
	 */
	public Boat(BoatType type, String firstCase, String lastCase) {
		this.type = type;
		storeCases(firstCase, lastCase);
		vie = this.type.getSize();
	}

	/**
	 * @brief Constructeur
	 * 
	 * @param type		Le type du bateau
	 * @param firstCase	Première case du bateau
	 * @param direction	Direction du bateau (1-droite, 2-bas, 3-gauche, 4-haut)
	 */
	public Boat(BoatType type, String firstCase, int direction) {
		this.type = type;
		this.direction = direction;
		storeCases(firstCase, direction);
		vie = this.type.getSize();
	}

	/**
	 * @brief Enregistre les cases qu'occupe le bateau
	 * @details Détermine la dernière case et appelle l'autre fonction storeCases
	 * 
	 * @param first		La première case du bateau
	 * @param direction	La direction
	 */
	private void storeCases(String first, int direction) {
		String lastCase = "";
		String lineFirst = first.substring(1); //Ligne de la première case
		String columnFirst = first.substring(0, 1); //Colonne de la première case

		switch (direction) {
		case 1:
			//Si à droite, la dernière case est la colonne additionné à la taille du bateau et la même ligne
			lastCase = String.valueOf((char) (columnFirst.charAt(0) + (type.getSize() - 1))) + lineFirst;
			break;

		case 2:
			//Si en bas, la dernière case est la même colonne et la ligne additionné à la taille du bateau
			lastCase = columnFirst + Integer.toString(Integer.parseInt(lineFirst) + (type.getSize() - 1));
			break;

		case 3:
			//Si à gauche, la dernière case est la colonne soustraite de la taille du bateau et la même ligne
			lastCase = String.valueOf((char) (columnFirst.charAt(0) - (type.getSize()) + 1) + lineFirst);
			break;

		case 4:
			//Si en haut, la dernière case est la même colonne et la ligne soustraite de la taille du bateau
			lastCase = columnFirst + Integer.toString(Integer.parseInt(lineFirst) - (type.getSize() - 1));
			break;
		}
		storeCases(first, lastCase);
	}

	/**
	 * @brief Enregistre les cases qu'occupe le bateau
	 * 
	 * @param first		La première case du bateau
	 * @param last		La direction
	 */
	private void storeCases(String first, String last) {
		cases.add(first);
		
		String lineFirst = first.substring(1);
		String columnFirst = first.substring(0, 1);
		String lineLast = last.substring(1);
		String columnLast = last.substring(0, 1);
		
		//On détermine la direction et on ajoute les cases entre la première et la dernière
		if (lineFirst.equals(lineLast)) {
			if (columnFirst.charAt(0) < columnLast.charAt(0)) {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(String.valueOf((char) (columnFirst.charAt(0) + i)) + lineFirst);
				}
			} else {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(String.valueOf((char) (columnFirst.charAt(0) - i)) + lineFirst);
				}
			}
		} else {
			if (Integer.parseInt(lineFirst) < Integer.parseInt(lineLast)) {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(columnFirst + Integer.toString(Integer.parseInt(lineFirst) + i));
				}
			} else {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(columnFirst + Integer.toString(Integer.parseInt(lineFirst) - i));
				}
			}
		}

		cases.add(last);
	}
	
	/**
	 * @brief Enlève un point de vie au bateau
	 * @details Decremente les points de vie et si il n'a plus de vie, il 
	 * n'est plus vivant
	 */
	public void removePV() {
		if (isAlive) {
			vie--;
			if (vie <= 0) {
				isAlive = false;
			}
		}
	}

	/**
	 * @brief Verifie si une case appartient à un bateau
	 * 
	 * @param cases		La case à vérifier
	 * @return	Vrai si la case appartient à un bateau
	 * @return	Faux si la case n'appartient pas à un bateau
	 */
	public boolean isOnCase(String cases) {
		for (int i = 0; i < this.cases.size(); i++) {
			if (this.cases.get(i).equals(cases)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getCases() {
		return cases;
	}

	public int getDirection() {
		return direction;
	}

	public BoatType getType() {
		return type;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	

}
