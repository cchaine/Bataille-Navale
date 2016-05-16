package core;

import java.util.ArrayList;

import graphics.Graphics;
import state.StateManager;

/**
 * @file src/core/Core.java
 * @author cchaine
 *
 * @brief Coeur du programme
 * @details Implémente Runnable pour la gestion des threads.
 */
public class Core implements Runnable{

	private boolean running = false;
	private Thread thread;
	
	private Graphics graphics;
	private static ArrayList<Player> players;
	
	private StateManager stateManager;
	
	private static final int TARGET_FPS = 60;
	private static final int TARGET_UPS = 30;
	private Timer timer;
	
	/**
	 * @brief Initialise les variables
	 */
	private void init()
	{
		players = new ArrayList<>();
		stateManager = new StateManager(this);
		graphics = new Graphics("Bataille Navalle", 1200, 600, stateManager);
		
		timer = new Timer();
		timer.init();
		
		stateManager.init(); //Créé le premier état de jeu
	}
	
	/**
	 * @bief Fonction principale du jeu
	 * @detail Met en place la boucle de jeu principale avec la régulation 
	 * de vitesse. Cela permet de faire tourner le jeu à vitesse constante sur 
	 * n'importe quelle machine
	 */
	@Override
	public void run() {
		init();
		
		float ellapsedTime;
		float accumulator = 0f;
		float interval = 1f / TARGET_UPS;

		boolean running = true;
		while (running) {
			ellapsedTime = timer.getEllapsedTime(); //Récupère le temps depuis la dernière mise à jour
			accumulator += ellapsedTime; //L'ajoute à l'accumulateur

			//Tant que le programme est en retard, mettre à jour la logique sans faire de rendu
			while (accumulator >= interval) {
				update();
				accumulator -= interval;
			}

			render(); //Si le programme à le temps, faire le rendu

			sync();
		}
		
		stop();
	}
	
	/**
	 * @brief S'assure que le programme va pas trop vite
	 * @details Si le programme a mis à jour le rendu, alors attendre que le 
	 * programme soit à l'heure pour recommencer la boucle.
	 */
	private void sync() {
		float loopSlot = 1f / TARGET_FPS; //Le temps supposé de la boucle avec rendu
		double endTime = timer.getLastLoopTime() + loopSlot; //Le temps supposé après un passage de boucle

		//Tant que le programme est en avance, mettre le thread en pause
		while (timer.getTime() < endTime) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException ie) {
			}
		}
	}
	
	/**
	 * @brief Informe la classe Graphics qu'il faut mettre à jour la logique
	 */
	private void update()
	{
		graphics.update();
	}
	
	/**
	 * @brief Informe la classe Graphics qu'il faut faire le rendu
	 */
	private void render()
	{
		graphics.render();
	}
	
	/**
	 * @brief Appelée lorsque l'on lance la classe (Runnable)
	 */
	public synchronized void start()
	{
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * @brief Appelée lorsque l'on quitte la classe (Runnable)
	 */
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
	
	public ArrayList<Player> getPlayers()
	{
		return players;
	}
}
