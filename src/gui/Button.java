package gui;

import java.awt.image.BufferedImage;

import com.sun.javafx.geom.Rectangle;
import java.awt.Graphics;

public class Button {

	Rectangle bounds;
	BufferedImage normal;
	BufferedImage pressed;
	BufferedImage hovered;
	boolean isPressed;
	
	public Button(int x, int y, int width, int height, BufferedImage normal, BufferedImage pressed, BufferedImage hovered)
	{
		bounds = new Rectangle(x, y, width,height);
		this.normal = normal;
		this.hovered = hovered;
		this.pressed = pressed;
	}
	
	public boolean contains(int x, int y)
	{
		return bounds.contains(x, y);
	}
	
	public void render(Graphics g)
	{
		
	}
}
