package core;

import java.util.ArrayList;

import state.StateManager;

public class Player {
	
	private ArrayList<Boat> boats;
	private ArrayList<Boat> deadBoats;
	private String name;
	private ArrayList<String> failHistory;
	private ArrayList<String> winHistory;
	private boolean won = false;
	
	public Player(String name, ArrayList<Boat> boats)
	{
		this.name = name;
		this.boats = boats;
		failHistory = new ArrayList<>();
		winHistory = new ArrayList<>();
		deadBoats = new ArrayList<>();
	}
	
	public ArrayList<Boat> getBoats()
	{
		return boats;
	}
	
	public ArrayList<Boat> getDeadBoats()
	{
		return deadBoats;
	}
	
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
	
	public void setWon(boolean won)
	{
		this.won = won;
	}
	
	public void play(StateManager stateManager)
	{
		
	}
	
	
}
