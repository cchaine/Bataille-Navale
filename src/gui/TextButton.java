package gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import java.awt.Color;
import java.awt.Font;

public class TextButton {

	int x, y;
	Rectangle2D bounds;
	String normal;
	String hovered;
	Font font;
	Color pressed;
	boolean isPressed = false;
	boolean isHovered = false;

	public TextButton(int x, int y, String normal, String hovered, Color pressed, Font font) {
		this.normal = normal;
		this.hovered = hovered;
		this.pressed = pressed;
		this.font = font;
		bounds = new Rectangle(x, y, 0, 0);
	}

	public boolean contains(int x, int y) {
		return bounds.contains(x, y);
	}

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
