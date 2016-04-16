package core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import state.StateManager;

/**
 * @file src/core/MouseHandle.java
 * @author cchaine
 *
 * @brief MouseListener personnalisé
 * @detail Implémente le MouseListener et informe le gestionnaire des étapes 
 * du programme des actions de la souris
 */
public class MouseHandle implements MouseListener{

	public StateManager stateManager;
	
	/**
	 * @brief Constructeur
	 * @param stateManager		Gestionnaire des étapes du programme
	 */
	public MouseHandle(StateManager stateManager) {
		this.stateManager = stateManager;
	}
	
	/**
	 * @brief Lancée quand un bouton de la souris est appuyé
	 * @details Informe le gestionnaire des étapes du programme qu'un 
	 * bouton de la souris est appuyé en envoyant les informations sur le 
	 * bouton
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		stateManager.getCurrentState().mousePressed(e);
	}

	/**
	 * @brief Lancée quand un bouton de la souris est relaché
	 * @details Informe le gestionnaire des étapes du programme qu'un 
	 * bouton de la souris est relaché en envoyant les informations sur le 
	 * bouton
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		stateManager.getCurrentState().mouseReleased(e);
	}
	
	/**
	 * @brief Lancée quand un bouton est utilisé
	 * @details Non utilisée, car necessité d'un controle sur les durées 
	 * d'interaction
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	/**
	 * @brief Non utilisé mais necessité d'implémenter
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @brief Non utilisé mais necessité d'implémenter
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
