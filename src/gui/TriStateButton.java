package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import core.BoatType;

/**
 * @file src/gui/TriStateButton.java
 * @author cchaine
 *
 * @brief Classe bouton à trois états
 */
public class TriStateButton {
	Rectangle2D bounds;
	
	BufferedImage normalImg;
	BufferedImage activatedImg;
	BufferedImage pressedImg;
	boolean pressed = false;
	boolean active = false;
	
	BoatType type;

	/**
	 * @brief Constructeur
	 * 
	 * @param x		Position x
	 * @param y		Position y
	 * @param width		Longueur
	 * @param height		Largeur
	 * @param normal		L'image normale du bouton
	 * @param activated		L'image quand le bouton est en action
	 * @param pressed		L'image quand la souris appuie sur le bouton
	 */
	public TriStateButton(int x, int y, int width, int height, BufferedImage normal, BufferedImage activated, BufferedImage pressed) {
		this.normalImg = normal;
		this.activatedImg = activated;
		this.pressedImg = pressed;
		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	/**
	 * @brief Verifie si la souris est dans le bouton
	 * 
	 * @param mouseX		La position de la souris
	 * @param mouseY		La position de la souris
	 * @return	VRAI	Si la souris est dans le bouton
	 * @return	FAUX	Si la souris n'est pas dans le bouton
	 */
	public boolean contains(int mouseX, int mouseY) {
		return bounds.contains(mouseX, mouseY);
	}

	/**
	 * @brief Appelée par l'état dans la fonction render
	 * 
	 * @param g		L'objet outil de dessin
	 */
	public void render(Graphics g) {
		if (active) {
			g.drawImage(activatedImg, (int)bounds.getX(), (int)bounds.getY() - 22 - 25, (int)bounds.getWidth(), (int)bounds.getHeight() + 25, null);
		} else if (pressed){
			g.drawImage(pressedImg, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		}else{
			g.drawImage(normalImg, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		}
	}
	
	/**
	 * @brief Gestion des évènement souris est appuyée
	 * 
	 * @param mouseX		La position x de la souris
	 * @param mouseY		La position y de la souris
	 */
	public void mousePressed(int mouseX, int mouseY)
	{
		if (bounds.contains(mouseX, mouseY))
			pressed = true;
		else
			pressed = false;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isPressed()
	{
		return pressed;
	}
	
	public void setPressed(boolean pressed)
	{
		this.pressed = pressed;
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}
}
