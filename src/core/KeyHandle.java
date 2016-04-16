package core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import state.StateManager;

/**
 * @file src/core/KeyHandle.java
 * @author cchaine
 *
 * @brief KeyListener personnalisé
 * @detail Implémente le KeyListener et informe le gestionnaire des étapes 
 * du programme si une touche est appuyée ou relachée
 */
public class KeyHandle implements KeyListener{

	public StateManager stateManager;
	
	/**
	 * @brief Constructeur
	 * @param stateManager		Gestionnaire des étapes du programme
	 */
	public KeyHandle(StateManager stateManager) {
		this.stateManager = stateManager;
	}

	/**
	 * @brief Lancée quand une touche est appuyée
	 * @details Informe le gestionnaire des étapes du programme qu'une 
	 * touche est appuyée en envoyant les informations sur la touche
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		stateManager.getCurrentState().keyPressed(e);
	}

	/**
	 * @brief Lancée quand une touche est relachée
	 * @details Informe le gestionnaire des étapes du programme qu'une 
	 * touche est relachée en envoyant les informations sur la touche
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		stateManager.getCurrentState().keyReleased(e);
	}

	/**
	 * @brief Lancée quand une touche est tapée
	 * @details Non utilisée, car necessité d'un controle sur la manière 
	 * dont la touche est utilisée
	 */
	@Override
	public void keyTyped(KeyEvent e) {}

}
