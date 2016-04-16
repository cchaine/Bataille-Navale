package graphics;

import java.awt.image.BufferStrategy;
import state.StateManager;

/**
 * @file src/graphics/Graphics.java
 * @author cchaine
 *
 * @brief Classe maîtresse du graphisme
 * @detail Cette classe gère tout le graphisme, de la création de la fenêtre
 * au rendu
 */
public class Graphics{

	private BufferStrategy bufferStrategy;
	private java.awt.Graphics g;
	
	private StateManager stateManager;
	
	/**
	 * @brief Contructeur
	 * @detail Création de la fenêtre et récupération des éléments pour le 
	 * rendu (dessin)
	 * 
	 * @param title		Intitulé de la fenêtre
	 * @param width		Longueur de la fenêtre
	 * @param height	Largeur de la fenêtre
	 * @param stateManager		Gestionnaire des étapes du programme
	 */
	public Graphics(String title, int width, int height, StateManager stateManager)
	{
		this.stateManager = stateManager;
		
		Display display = new Display(title, width, height);
		display.createDisplay();
		display.addMouseListener(new MouseHandle(stateManager)); //Ajout d'un gestionnaire de souris
		display.addKeyListener(new KeyHandle(stateManager)); //Ajout d'un gestionnaire de clavier
		
		//Création des outils pour le rendu
		display.getCanvas().createBufferStrategy(3);
		bufferStrategy = display.getCanvas().getBufferStrategy();
	}
	
	/**
	 * @brief Met à jour la logique du jeu
	 * @details Envoi au gestionnaire des étapes l'ordre de mise à jour de la logique du jeu
	 */
	public void update()
	{
		stateManager.getCurrentState().update();
	}
	
	/**
	 * @brief Met à jour le graphisme du jeu
	 * @details Récupération des outils pour le rendu et envoi l'odre au 
	 * gestionnaire des étapes de mettre à jour le graphisme
	 */
	public void render()
	{
		g = bufferStrategy.getDrawGraphics(); //Récupère l'objet permettant de dessiner
		
		g.clearRect(0, 0, Display.width, Display.height); //Efface l'image précédente pour dessiner la nouvelle
		
		stateManager.getCurrentState().render(g);
		
		bufferStrategy.show(); //Affiche le dessin
		g.dispose();
	}
}
