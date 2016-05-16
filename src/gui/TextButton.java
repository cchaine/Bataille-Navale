package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.awt.Font;

/**
 * @file src/gui/TextButton.java
 * @author cchaine
 *
 * @brief Classe bouton de texte
 */
public class TextButton {

	int x, y;
	Rectangle2D bounds;
	String normal;
	String hovered;
	Font font;
	Color pressed;
	boolean isPressed = false;
	boolean isHovered = false;

	/**
	 * @brief Constructeur
	 * 
	 * @param x		Position x
	 * @param y		Position y
	 * @param normal		La chaine de caractère normale
	 * @param hovered		La chaine de caractère quand la souris est sur le bouton
	 * @param pressed		La chaine de caractère quand la souris appuie sur le bouton
	 * @param font		La police du bouton
	 */
	public TextButton(int x, int y, String normal, String hovered, Color pressed, Font font) {
		this.normal = normal;
		this.hovered = hovered;
		this.pressed = pressed;
		this.font = font;
		bounds = new Rectangle(x, y, 0, 0);
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

	/**
	 * @brief Appelée par l'état dans la fonction render
	 * 
	 * @param g		L'objet outil de dessin
	 */
	public void render(Graphics g, float buttonAlpha) {
		if (bounds.getWidth() == 0) {
			g.setFont(font);
			bounds.setRect(bounds.getX() - g.getFontMetrics().getStringBounds(normal, g).getWidth() / 2, bounds.getY() - g.getFontMetrics().getStringBounds(normal, g).getHeight() / 2, g.getFontMetrics().getStringBounds(normal, g).getWidth(), 
					g.getFontMetrics().getStringBounds(normal, g).getHeight());
		}

		g.setFont(font);
		g.setColor(new Color(0, 35, 102, (int) (buttonAlpha * 1.5)));

		if (isPressed) {
			g.setColor(pressed);
			g.drawString(hovered, (int)bounds.getX(), (int)(bounds.getY() + bounds.getHeight() / 2 - 10));
		}else if (isHovered)
			g.drawString(hovered, (int)bounds.getX(), (int)(bounds.getY() + bounds.getHeight() / 2 - 10));
		else
			g.drawString(normal, (int)bounds.getX(), (int)(bounds.getY() + bounds.getHeight() / 2 - 10));
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
			isPressed = true;
		else
			isPressed = false;
	}
	
	/**
	 * @brief Gestion des évènement souris relachée
	 * 
	 * @param mouseX		La position x de la souris
	 * @param mouseY		La position y de la souris
	 */
	public boolean mouseReleased(int mouseX, int mouseY)
	{
		isPressed = false;
		return bounds.contains(mouseX, mouseY);
		
	}

	public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public boolean isHovered() {
		return isHovered;
	}

	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
	}
	
	public void setPosition(int x, int y)
	{
		bounds.setRect(x - bounds.getWidth() / 2, y - bounds.getHeight() / 2, bounds.getWidth(), bounds.getHeight());
	}
	
	public Rectangle2D getBounds()
	{
		return bounds;
	}
}
