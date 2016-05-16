package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import utils.AssetLoader;

/**
 * @file src/gui/TextField.java
 * @author cchaine
 *
 * @brief Classe champ de texte
 */
public class TextField {

	private String currentText;
	private boolean active = false;
	private boolean hovered = false;
	private Rectangle2D bounds;
	
	/**
	 * @brief Constructeur
	 * 
	 * @param x		Position x
	 * @param y		Position y
	 * @param width		Longueur
	 * @param height		Largeur
	 */
	public TextField(int x, int y, int width, int height)
	{
		currentText = "Entrez votre nom...";
		bounds = new Rectangle(x, y, width, height);
	}
	
	/**
	 * @brief Appelée par l'état dans la fonction render
	 * 
	 * @param g		L'objet outil de dessin
	 */
	public void render(Graphics g)
	{
		g.setFont(AssetLoader.helvetica45);
		if (active) {
			g.setColor(Color.GRAY);
		} else if(hovered){
			g.setColor(Color.LIGHT_GRAY);
		}else{
			g.setColor(Color.DARK_GRAY);
		}
		g.drawRect((int)bounds.getX(), (int)bounds.getY() - 24, (int)bounds.getWidth(), (int)bounds.getHeight());
		
		if (active) {
			g.setColor(Color.DARK_GRAY);
		} else{
			g.setColor(Color.GRAY);
		}
		g.drawString(currentText, (int)bounds.getX() + 2, (int)bounds.getY() + 78 - 24);
	}
	
	/**
	 * @brief Gestion des évènement souris est appuyée
	 * 
	 * @param mouseX		La position x de la souris
	 * @param mouseY		La position y de la souris
	 */
	public void mousePressed(int mouseX, int mouseY)
	{
		if (bounds.contains(mouseX, mouseY)){
			active = true;
			if(currentText.equals("Entrez votre nom..."))
			{
				currentText = "";
			}
		}else
			active = false;
	}
	
	/**
	 * @brief Verifie si la souris est dans le bouton
	 * 
	 * @param mouseX		La position de la souris
	 * @param mouseY		La position de la souris
	 * @return	VRAI	Si la souris est dans le bouton
	 * @return	FAUX	Si la souris n'est pas dans le bouton
	 */
	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isHovered() {
		return hovered;
	}

	public void setHovered(boolean hovered) {
		this.hovered = hovered;
	}
	
	public String getText()
	{
		return currentText;
	}
	
	public void setText(String text)
	{
		this.currentText = text;
	}
	
	public boolean isEmpty()
	{
		return currentText.isEmpty();
	}
	
	/**
	 * @brief Efface la derniere lettre
	 */
	public void backspace()
	{
		currentText = currentText.substring(0, currentText.length() - 1);
	}
	
	/**
	 * @brief Ajoute une lettre
	 * @param c		La lettre à ajouter
	 */
	public void addLetter(char c)
	{
		currentText += c;
	}
}
