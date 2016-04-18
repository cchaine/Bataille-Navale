package state;

import core.Computer;
import core.Core;
import core.Player;

public class StateManager {

	private State currentState;
	private Core core;
	private int settupIndex = 1;
	private int currentPlayer = 1;
	private boolean multiplayer = false;
	
	public StateManager(Core core)
	{
		this.core = core;
	}
	
	public void init()
	{
		currentState = new MenuState(this);
	}
	
	public boolean addFire(String cases)
	{
		Player current = core.getPlayers().get(currentPlayer);
		Player opponent = core.getPlayers().get(1 - currentPlayer);
		if(checkCaseOpponent(current, cases))
		{
			current.getWinHistory().add(cases);
			declareBoatInjury(cases);
			return true;
		}else{
			current.getFailHistory().add(cases);
			return false;
		}
	}
	
	public void declareBoatInjury(String cases)
	{
		for(int i = 0; i < core.getPlayers().get(1 - this.currentPlayer).getBoats().size(); i++)
		{
			if(core.getPlayers().get(1 - this.currentPlayer).getBoats().get(i).isOnCase(cases))
			{
				core.getPlayers().get(1 - this.currentPlayer).getBoats().get(i).removePV();
				if(!core.getPlayers().get(1 - this.currentPlayer).getBoats().get(i).isAlive())
				{
					core.getPlayers().get(1 - this.currentPlayer).getDeadBoats().add(core.getPlayers().get(1 - this.currentPlayer).getBoats().get(i));
					core.getPlayers().get(1 - this.currentPlayer).getBoats().remove(i);
				}
			}
		}
	}
	
	public void changePlayers()
	{
		currentPlayer = 1 - currentPlayer;
		currentState = new PlayState(this);
	}
	
	public boolean checkCaseOpponent(Player currentPlayer, String cases)
	{
		return core.getPlayers().get(1 - this.currentPlayer).isOnCases(cases);
	}
	
	public void setCurrentState(State state)
	{
		this.currentState = state;
	}
	
	public State getCurrentState()
	{
		return currentState;
	}
	
	public Core getCore()
	{
		return core;
	}
	
	public int getSettupIndex()
	{
		return settupIndex;
	}
	
	public void setSettupIndex(int settupIndex)
	{
		this.settupIndex = settupIndex;
	}
	
	public int getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	public void setCurrentPlayer(int currentPlayer)
	{
		this.currentPlayer = currentPlayer;
	}
	
	public boolean getMultiplayer()
	{
		return multiplayer;
	}
	
	public void setMultiplayer(boolean value)
	{
		this.multiplayer = value;
	}
}
