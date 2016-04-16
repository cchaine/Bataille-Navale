package core;

import java.util.ArrayList;

import graphics.Graphics;
import state.StateManager;

public class Core implements Runnable{

	private boolean running = false;
	private Thread thread;
	
	private Graphics graphics;
	private static ArrayList<Player> players;
	
	private StateManager stateManager;
	
	private static final int TARGET_FPS = 30;
	private static final int TARGET_UPS = 30;
	private Timer timer;
	
	private void init()
	{
		players = new ArrayList<>();
		/*ArrayList<Boat> boats1 = new ArrayList<>();
		boats1.add(new Boat(BoatType.PORTEAVION, "B3", 1));
		boats1.add(new Boat(BoatType.CROISEUR, "A4", 1));
		boats1.add(new Boat(BoatType.CONTRETORPILLEUR, "E5", 1));
		boats1.add(new Boat(BoatType.SOUSMARIN, "G1", 1));
		boats1.add(new Boat(BoatType.TORPILLEUR, "J10", 3));
		ArrayList<Boat> boats2 = new ArrayList<>();
		boats2.add(new Boat(BoatType.PORTEAVION, "B3", 1));
		boats2.add(new Boat(BoatType.CROISEUR, "A4", 1));
		boats2.add(new Boat(BoatType.CONTRETORPILLEUR, "E5", 1));
		boats2.add(new Boat(BoatType.SOUSMARIN, "G1", 1));
		boats2.add(new Boat(BoatType.TORPILLEUR, "J10", 3));
		players.add(new Player("PlayerOne", boats1));
		players.add(new Player("PlayerTwo", boats2));*/
		stateManager = new StateManager(this);
		graphics = new Graphics("Bataille Navalle", 1200, 600, stateManager);
		timer = new Timer();
		timer.init();
	}
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
	
	private void update()
	{
		graphics.update();
	}
	
	private void render()
	{
		graphics.render();
	}

	@Override
	public void run() {
		init();
		
		float ellapsedTime;
        float accumulator = 0f;
        float interval = 1f / TARGET_UPS;

        boolean running = true;
        while (running) {
            ellapsedTime = timer.getEllapsedTime();
            accumulator += ellapsedTime;

            while (accumulator >= interval) {
                update();
                accumulator -= interval;
            }

            render();

            sync();
        }
		
		stop();
	}
	
	private void sync() {
        float loopSlot = 1f / TARGET_FPS;
        double endTime = timer.getLastLoopTime() + loopSlot;
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException ie) {
            }
        }
    }
	
	public synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop()
	{
		if(!running)
			return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
