package gui;

import java.awt.image.BufferedImage;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Button {

	Rectangle2D bounds;
	
	BufferedImage normal;
	BufferedImage hovered;
	BufferedImage pressed;
	
	boolean isPressed = false;
	boolean isHovered = false;

	public Button(int x, int y, int width, int height, BufferedImage normal, BufferedImage hovered, BufferedImage pressed) {
		this.normal = normal;
		this.hovered = hovered;
		this.pressed = pressed;
		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}
	
	public void update(int mouseX, int mouseY)
	{
		//On verifie si la position de la souris est dans le bouton
		if (bounds.contains(mouseX, mouseY))
			isHovered = true;
		else
			isHovered = false;
	}

	public void render(Graphics g) {

		if (isPressed) {
			g.drawImage(pressed, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		}else if (isHovered)
			g.drawImage(hovered, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		else
			g.drawImage(normal, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
	}
	
	public void mousePressed(int mouseX, int mouseY)
	{
		if (bounds.contains(mouseX, mouseY))
			isPressed = true;
		else
			isPressed = false;
	}
	
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
