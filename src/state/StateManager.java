package state;

import core.Core;
import core.Player;

/**
 * @file src/state/StateManager.java
 * @author cchaine
 *
 * @brief Gestionnaire des étapes du programme
 * @details Gère le déroulement des étapes du programme, de la création à la 
 * mise à jour et au rendu, jusqu'à la destruction
 */
public class StateManager {

	private Core core;
	private State currentState;

	private Player current;
	private Player opponent;

	private boolean multiplayer = false;

	/**
	 * @briefConstructeur
	 * 
	 * @param core		Le coeur du jeu
	 */
	public StateManager(Core core) {
		this.core = core;
	}

	/**
	 * @brief Initialise les variables une fois le jeu lancé
	 */
	public void init() {
		currentState = new MenuState(this);
	}

	/**
	 * @brief Ajoute un tir
	 * @details Appelée quand le joueur tire
	 * 
	 * @param cases		La case où le joueur à tiré
	 * @return	TRUE	Si la case a touché un bateau
	 * @return	FALSE	Si la case n'a touché aucun bateau
	 */
	public boolean addFire(String cases) {
		if (checkCaseOpponent(cases)) {
			//Ajoute ce tir à l'historique des touché du joueur
			current.getWinHistory().add(cases);
			declareBoatInjury(cases);
			return true;
		} else {
			//Ajoute ce tir à l'historique des ratés du joueur
			current.getFailHistory().add(cases);
			return false;
		}
	}

	/**
	 * @brief Déclare qu'un bateau à été touché
	 * @details Appelé quand le joueur à touché un bateau pour réduire les 
	 * points de vie du bateau ou le détruire
	 * 
	 * @param cases		La case qui à été touchée
	 */
	public void declareBoatInjury(String cases) {
		//Cherche à quel bateau appartient la case
		for (int i = 0; i < opponent.getBoats().size(); i++) {
			if (opponent.getBoats().get(i).isOnCase(cases)) {
				opponent.getBoats().get(i).removePV();
				if (!opponent.getBoats().get(i).isAlive()) {
					//Déplace le bateau des bateaux vivants aux morts de l'adversaire
					opponent.getDeadBoats().add(opponent.getBoats().get(i));
					opponent.getBoats().remove(i);
				}
				return;
			}
		}
	}

	/**
	 * @brief Echange les joueurs
	 * @details Appelée à la fin du tour pour changer de joueur
	 */
	public void changePlayers() {
		Player playerBuf;
		playerBuf = current;
		current = opponent;
		opponent = playerBuf;

		if (multiplayer)
			currentState = new ChangingState(this);
		else
			currentState = new PlayState(this);
	}

	/**
	 * @brief Vérifie si la case appartient à un bateau ennemi
	 * 
	 * @param cases		La case à vérifier
	 * @return	TRUE	Si la case appartient à un bateau
	 * @return	FALSE	Si la case n'appartient à aucun bateau
	 */
	public boolean checkCaseOpponent(String cases) {
		return opponent.isOnCases(cases);
	}

	public void setCurrentState(State state) {
		this.currentState = state;
	}

	public State getCurrentState() {
		return currentState;
	}

	public Core getCore() {
		return core;
	}

	public Player getCurrent() {
		return current;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setCurrent(Player current) {
		this.current = current;
	}
	
	public void setOpponent(Player opponent)
	{
		this.opponent = opponent;
	}

	public boolean getMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(boolean value) {
		this.multiplayer = value;
	}
}
