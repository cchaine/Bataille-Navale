package core;

import java.util.ArrayList;

import state.StateManager;

public class Computer extends Player {
	private Player opponent;
	
	private ArrayList<Boat> boats;
	private ArrayList<Boat> deadBoats;

	private int currentDirection = 0;
	private boolean focused = false;
	private ArrayList<Integer> triedDirections = new ArrayList<>();
	private int deadCounter;
	private String firstFire = "";
	private boolean directionGood = false;

	public Computer(String name) {
		super(name, generateBoatRandom());
	}

	@Override
	public void play(StateManager stateManager) {
		stateManager.changePlayers();
		if(opponent == null)
			opponent = stateManager.getOpponent();
		
		boolean finished = false;
		
		do {
			if (focused) {
				//Si on commence le suivi, alors on choisi une direction
				if (currentDirection == 0)
					changeDirection();

				String nextShot = "";
				
				switch (currentDirection) {
				case 1:
					//Si on était déjà en suivi et que au dernier tir, on a fait juste
					if (directionGood) {
						//Alors on continue dans cette direction
						//On prend le dernier bon tir et on incrémente
						nextShot = (String) ((char) (this.getWinHistory().get(this.getWinHistory().size() - 1).charAt(0)
								+ 1) + this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1));
					} else {
						//Sinon on vient juste de changer de direction, donc on prend la première case touchée et on incrémente
						nextShot = (String) ((char) (firstFire.charAt(0) + 1) + firstFire.substring(1));
					}
					break;

				case 2:
					if (directionGood) {
						nextShot = (String) (this.getWinHistory().get(this.getWinHistory().size() - 1).substring(0, 1)
								+ Integer.toString(Integer.parseInt(
										this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1)) + 1));
					} else {
						nextShot = (String) (firstFire.substring(0, 1)
								+ Integer.toString(Integer.parseInt(firstFire.substring(1)) + 1));
					}
					break;

				case 3:
					if (directionGood) {
						nextShot = (String) ((char) (this.getWinHistory().get(this.getWinHistory().size() - 1).charAt(0)
								- 1) + this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1));
					} else {
						nextShot = (String) ((char) (firstFire.charAt(0) - 1) + firstFire.substring(1));
					}
					break;

				case 4:
					if (directionGood) {
						nextShot = (String) (this.getWinHistory().get(this.getWinHistory().size() - 1).substring(0, 1)
								+ Integer.toString(Integer.parseInt(
										this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1)) - 1));
					} else {
						nextShot = (String) (firstFire.substring(0, 1)
								+ Integer.toString(Integer.parseInt(firstFire.substring(1)) - 1));
					}
					break;
				}
				
				//Si le tir généré est dans la grille
				if (nextShot.charAt(0) <= 'J' && nextShot.charAt(0) >= 'A'
						&& Integer.parseInt(nextShot.substring(1)) >= 1
						&& Integer.parseInt(nextShot.substring(1)) <= 10) {
					//Si on l'a pas déjà joué
					if (!alreadyPlayed(nextShot)) {
						//On tire et verrifie si on a juste
						if (stateManager.addFire(nextShot)) {
							//On a juste donc on va pouvoir rejouer
							finished = false;
							//On définie cette direction comme étant dans le bon sens (pour le changement de direction)
							directionGood = true;
							//Si on vient de détruire le bateau
							if (deadCounter < stateManager.getCore().getPlayers().get(0).getDeadBoats().size()) {
								//On ne suit plus
								focused = false;
								//On efface les directions déjà essayées
								triedDirections = new ArrayList<>();
								//Aucune direction actuelle
								currentDirection = 0;
								firstFire = "";
								directionGood = false;
							}
						} else {
							//On n'a pas touché
							//Si on essayé toutes les directions, alors on sort du mode suivi
							if (triedDirections.size() == 4) {
								focused = false;
								triedDirections = new ArrayList<>();
								currentDirection = 0;
								firstFire = "";
								directionGood = false;
							} else {
								//Sinon on change de direction
								changeDirection();
								directionGood = false;
								//On a fini le tour
								finished = true;
							}
						}
					} else {
						//On a déjà joué cette case
						//Si on a essayé toutes les directions, on quitte le mode suivi
						if (triedDirections.size() == 4) {
							focused = false;
							triedDirections = new ArrayList<>();
							currentDirection = 0;
							firstFire = "";
							directionGood = false;
						} else {
							//Sinon on change de direction et on rejoue
							changeDirection();
							directionGood = false;
							finished = false;
						}
					}
				} else {
					//Si la case est pas dans la grille
					//Si on a essayé toutes les directions, on quitte le mode suivi
					if (triedDirections.size() == 4) {
						focused = false;
						triedDirections = new ArrayList<>();
						currentDirection = 0;
						firstFire = "";
						directionGood = false;
					} else {
						//Sinon on change de direction et on rejoue
						changeDirection();
						directionGood = false;
						finished = false;
					}
				}
			} else {
				if (checkBoatInjured())
					continue;
				
				//Colonne aléatoire entre 'A' et 'J' (ASCII)
				char column = (char) ((int) (65 + (Math.random() * (75 - 65))));
				//Ligne aléatoire entre 1 et 10
				int line = (int) (1 + (Math.random() * (10)));
				
				if (!alreadyPlayed((String) (column + Integer.toString(line)))) {
					int caseValue = 0;
					int roundValue = 0;
					
					String aroundCases[] = new String[4];
					aroundCases[0] = (String) ((char) (column + 1) + Integer.toString(line));
					aroundCases[1] = (String) (column + Integer.toString(line + 1));
					aroundCases[2] = (String) ((char) (column - 1) + Integer.toString(line));
					aroundCases[3] = (String) (column + Integer.toString(line - 1));
					
					for (int i = 0; i < 4; i++) {
						//On incrémente caseValue si on a déjà joué sur une des cases autour
						if (alreadyPlayed(aroundCases[i]))
							caseValue++;
						
						char columnBuffer = aroundCases[i].charAt(0);
						int lineBuffer = Integer.parseInt(aroundCases[i].substring(1));
						//On incrémente roundValue si on a un mur à coté de la case
						if (columnBuffer < 'A' || columnBuffer > 'J' || lineBuffer > 10 || lineBuffer < 1)
							roundValue++;
					}
					
					if(!luckValue(caseValue, roundValue))
						continue;
					
					//On tire et si on touche
					if (stateManager.addFire((String) (column + Integer.toString(line)))) {
						//On passe en mode suivi
						finished = false;
						focused = true;
						firstFire = (String) (column + Integer.toString(line));
						deadCounter = stateManager.getCore().getPlayers().get(0).getDeadBoats().size();
						directionGood = false;
					} else {
						//Si on loupe, on a fini le tour
						finished = true;
					}
				}
			}
		} while (!finished);
		stateManager.changePlayers();
	}
	
	private boolean luckValue(int caseValue, int roundValue)
	{
		int random = (int) (-20 + (Math.random() * (101 - 1)));
		int luckValue = 0;
		luckValue += caseValue * 2;
		luckValue += roundValue * 1;
		
		if(random > 90 - luckValue * 7)
		{
			return false;
		}
		return true;
	}
	
	/**
	 * @brief Verifie si tous les tirs touchés appartiennent à un bateau mort
	 * 
	 * @return	VRAI	Si le un tir appartient à un bateau vivant
	 * @return	FAUX	Si aucun tir appartient à un bateau vivant
	 */
	private boolean checkBoatInjured() {
		for (int i = 0; i < this.getWinHistory().size(); i++) {
			if (!isOnDeadBoat(this.getWinHistory().get(i), opponent)) {
				//Si un tir appartient à un bateau vivant, alors on repasse en mode suivi à partir de cette case
				focused = true;
				firstFire = this.getWinHistory().get(i);
				deadCounter = opponent.getDeadBoats().size();
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @brief Verifie si la direction actuelle à été testé ou non
	 * 
	 * @return	VRAI	Si elle a été utilisée
	 * @return	FAUX	Si elle n'a pas été utilisée
	 */
	private boolean alreadyTriedDirection() {
		for (int i = 0; i < triedDirections.size(); i++) {
			if (currentDirection == triedDirections.get(i))
				return true;
		}
		return false;
	}

	/**
	 * @brief Change la direction de visée de l'ordinateur
	 * @details Quand l'ordinateur est en mode suivi, cela change la direction actuelle par une nouvelle direction jamais essayée
	 */
	private void changeDirection() {
		boolean noAlreadyDirection = false;
		
		if (!alreadyTriedDirection() && currentDirection != 0) {
			triedDirections.add(currentDirection);
		}
		
		//Si on était déjà en suivi et que la direction précédente était bonne, alors on veut la direction inverse
		if (directionGood) {
			switch (currentDirection) {
			case 1:
				currentDirection = 3;
				break;

			case 2:
				currentDirection = 4;
				break;

			case 3:
				currentDirection = 1;
				break;

			case 4:
				currentDirection = 2;
				break;
			}
			if(!alreadyTriedDirection()){
				directionGood = false;
				return;
			}
		}
		
		do {
			//Génère une direction entre 1 et 4
			currentDirection = (int) (1 + (Math.random() * (4)));
			
			//On verifie qu'elle a pas déjà été essayée
			if (triedDirections.size() != 0) {
				for (int i = 0; i < triedDirections.size(); i++) {
					if (currentDirection == triedDirections.get(i)) {
						noAlreadyDirection = true;
						continue;
					}
				}
				noAlreadyDirection = false;
			} else {
				noAlreadyDirection = false;
			}
		} while (noAlreadyDirection);
	}

	/**
	 * @brief Vérifie si une case appartient à un bateau mort de l'ennemie
	 * 
	 * @param cases		La case à vérifier
	 * @param opponent		L'adversaire
	 * 
	 * @return	VRAI	Si la case appartient à un bateau mort
	 * @return	FALSE	Si la case n'appartient pas à un bateau mort
	 */
	private boolean isOnDeadBoat(String cases, Player opponent) {
		for (int i = 0; i < opponent.getDeadBoats().size(); i++) {
			if (opponent.getDeadBoats().get(i).isOnCase(cases))
				return true;
		}
		return false;
	}
	
	/**
	 * @brief Crée un tableau de bateaux aléatoirement
	 * @details Le tableau crée des 5 types de bateaux est directement exploitable par la classe Player et Computer
	 * 
	 * @return		Un tableau de bateaux
	 */
	public static ArrayList<Boat> generateBoatRandom() {
		ArrayList<Boat> boat = new ArrayList<>();
		
		BoatType types[] = { BoatType.PORTEAVION, BoatType.CROISEUR, BoatType.CONTRETORPILLEUR, BoatType.SOUSMARIN,
				BoatType.TORPILLEUR };
		
		Boat boatBuffer;
		
		for (int i = 0; i < types.length; i++) {
			boolean isIn = false;
			boolean isAlready = true;
			do {
				//Direction aléatoire entre 1 et 4
				int direction = (int) (1 + (Math.random() * (4)));
				//Colonne aléatoire entre "A" et "J" (ASCII)
				char column = (char) ((int) (65 + (Math.random() * (75 - 65))));
				//Ligne aléatoire entre 1 et 10
				int line = (int) (1 + (Math.random() * (10)));

				switch (direction) {
				case 1:
					//Calcule la dernière case du bateau et vérifie qu'elle est dans la grille
					if (column + types[i].getSize() - 1 > 'J')
						//Sinon le bateau ne rentre pas dans ce sens et donc l'inverser
						continue;
					break;

				case 2:
					if (line + types[i].getSize() - 1 > 10)
						continue;
					break;

				case 3:
					if (column - types[i].getSize() + 1 < 'A')
						continue;
					break;

				case 4:
					if (line - types[i].getSize() + 1 < 1)
						continue;
					break;
				}
				
				boatBuffer = new Boat(types[i], (String) (column + Integer.toString(line)), direction);
				
				isAlready = isOnCase(boatBuffer, boat);
				if (!isAlready) {
					boat.add(boatBuffer);
					isIn = true;
				}
			} while (!isIn || isAlready);
		}
		return boat;
	}

	/**
	 * @brief Verifie si un bateau est sur l'emplacement d'un autre bateau d'un ArrayList
	 * 
	 * @param boatBuffer		Le bateau à verifier
	 * @param boat		Le tableau dans lequel on va verifier
	 * 
	 * @return	VRAI	Si le bateau empiète sur un autre bateau
	 * @return	FAUX	Si le bateau n'est pas sur un autre bateau
	 */
	public static boolean isOnCase(Boat boatBuffer, ArrayList<Boat> boat) {
		if (!boat.isEmpty()) {
			//On boucle à travers les bateaux de l'ArrayList
			for (int k = 0; k < boat.size(); k++) {
				//On boucle à travers les cases du bateau a vérifier
				for (int j = 0; j < boatBuffer.getCases().size(); j++) {
					//On verifie si ces cases la ne sont pas sur une des cases d'un des bateaux de l'ArrayList
					if (boat.get(k).isOnCase(boatBuffer.getCases().get(j))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
