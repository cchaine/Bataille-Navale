package state;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * @file src/state/State.java
 * @author cchaine
 *
 * @brief Classe abstraite "Etat", pour l'implémentation du gestionnaire des 
 * étapes du jeu
 */
public abstract class State {

	public abstract void update();
	
	public abstract void render(Graphics g);
	
	public abstract void mousePressed(MouseEvent e);
	
	public abstract void mouseReleased(MouseEvent e);
	
	public abstract void mouseClicked(MouseEvent e);
	
	public abstract void keyTyped(KeyEvent e);
	
	public abstract void keyReleased(KeyEvent e);
	
	public abstract void keyPressed(KeyEvent e);
}
