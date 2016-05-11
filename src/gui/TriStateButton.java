package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import core.BoatType;
import utils.AssetLoader;

public class TriStateButton {
	Rectangle2D bounds;
	
	BufferedImage normalImg;
	BufferedImage activatedImg;
	BufferedImage pressedImg;
	boolean pressed = false;
	boolean active = false;
	
	BoatType type;

	public TriStateButton(int x, int y, int width, int height, BufferedImage normal, BufferedImage activated, BufferedImage pressed) {
		this.normalImg = normal;
		this.activatedImg = activated;
		this.pressedImg = pressed;
		bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
	}

	public boolean contains(int mouseX, int mouseY) {
		return bounds.contains(mouseX, mouseY);
	}

	public void render(Graphics g) {
		if (active) {
			g.drawImage(activatedImg, (int)bounds.getX(), (int)bounds.getY() - 22 - 25, (int)bounds.getWidth(), (int)bounds.getHeight() + 25, null);
		} else if (pressed){
			g.drawImage(pressedImg, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		}else{
			g.drawImage(normalImg, (int)bounds.getX(), (int)bounds.getY() - 22, (int)bounds.getWidth(), (int)bounds.getHeight(), null);
		}
	}
	
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
