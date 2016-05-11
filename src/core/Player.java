package core;

import java.util.ArrayList;

import state.StateManager;

/**
 * @file src/core/Player.java
 * @author cchaine
 *
 * @brief Classe Maîtresse Joueur
 * @detail Stocke les informations sur le joueur et fourni des méthodes 
 * outils
 */
public class Player {
	
	private String name;
	private ArrayList<Boat> boats;
	private ArrayList<Boat> deadBoats;
	private ArrayList<String> failHistory;
	private ArrayList<String> winHistory;
	private boolean winner = false;
	
	/**
	 * @brief Constructeur
	 * 
	 * @param name		Nom du joueur
	 * @param boats		Une liste des bateaux du joueur
	 */
	public Player(String name, ArrayList<Boat> boats)
	{
		this.name = name;
		this.boats = boats;
		failHistory = new ArrayList<>();
		winHistory = new ArrayList<>();
		deadBoats = new ArrayList<>();
	}
	
	/**
	 * @brief Vérifie si un bateau occupe une case
	 * 
	 * @param cases		La case à vérifier
	 * @return Vrai si un bateau est dessus
	 * @return Faux si aucun bateau n'est dessus
	 */
	public boolean isOnCases(String cases)
	{
		for(int i = 0; i < boats.size(); i++)
		{
			if(boats.get(i).isOnCase(cases))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @brief Vérifie si une case à déjà été jouée
	 * 
	 * @param cases		La case à vérifier
	 * @return Vrai si la case est déjà jouée
	 * @return Faux si la case n'a pas été jouée
	 */
	public boolean alreadyPlayed(String cases)
	{
		for(int i = 0; i < failHistory.size(); i++)
		{
			if(failHistory.get(i).equals(cases))
			{
				return true;
			}
		}
		for(int i = 0; i < winHistory.size(); i++)
		{
			if(winHistory.get(i).equals(cases))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @brief Non utilisée, necessaire pour l'implémentation de l'ordinateur 
	 * et la compatibilité entre les types dans le gestionnaire des étapes 
	 * du programme
	 */
	public void play(StateManager stateManager) {}
	
	public void setWinner(boolean winner)
	{
		this.winner = winner;
	}
	
	public boolean isWinner()
	{
		return winner;
	}
	
	public String getName()
	{
		return name;
	}

	public ArrayList<String> getFailHistory() {
		return failHistory;
	}

	public ArrayList<String> getWinHistory() {
		return winHistory;
	}
	
	public ArrayList<Boat> getBoats()
	{
		return boats;
	}
	
	public ArrayList<Boat> getDeadBoats()
	{
		return deadBoats;
	}
}
