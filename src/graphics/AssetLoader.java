package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import sun.applet.Main;

public class AssetLoader {
	
	public static BufferedImage boat;
	public static BufferedImage waves;
	public static BufferedImage grid;
	public static BufferedImage trash;
	public static BufferedImage trashClickedImg;
	public static BufferedImage trashPressedImg;
	public static BufferedImage delete;
	public static BufferedImage continueButton;
	public static BufferedImage continueButtonPressed;
	public static BufferedImage continueButtonHovered;
	public static BufferedImage attackButton;
	public static BufferedImage attackButtonHovered;
	public static BufferedImage attackButtonPressed;
	public static BufferedImage fireButton;
	public static BufferedImage fireButtonHovered;
	public static BufferedImage fireButtonPressed;
	public static BufferedImage scope;
	public static BufferedImage fire;
	public static BufferedImage water;
	public static Font junebug;
	public static Font junebug60;
	public static Font junebug50;
	public static Font junebug40;
	public static Font junebug35;
	public static Font helvetica25;
	public static Font helvetica30;
	public static Font helvetica45;
	public static Font helvetica35;
	public static Font helvetica20;
	public static Font errorFont;
	public static BufferedImage randomButton;
	public static BufferedImage randomButtonHovered;
	public static BufferedImage randomButtonPressed;
	
	public static void loadAssets(Display display) {
		boat = ImageLoader.loadImage("/textures/military.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		waves = ImageLoader.loadImage("/textures/waves.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		grid = ImageLoader.loadImage("/textures/fullGrid.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		trash = ImageLoader.loadImage("/textures/recycle.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		trashClickedImg = ImageLoader.loadImage("/textures/recycleClicked.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		trashPressedImg = ImageLoader.loadImage("/textures/recyclePressed.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		delete = ImageLoader.loadImage("/textures/delete.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		continueButton = ImageLoader.loadImage("/textures/addButton.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		continueButtonPressed = ImageLoader.loadImage("/textures/addButtonPressed.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		continueButtonHovered = ImageLoader.loadImage("/textures/addButtonHovered.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		attackButton = ImageLoader.loadImage("/textures/attackButton.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		attackButtonHovered = ImageLoader.loadImage("/textures/attackButtonHovered.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		attackButtonPressed = ImageLoader.loadImage("/textures/attackButtonPressed.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		fireButton = ImageLoader.loadImage("/textures/fireButton.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		fireButtonHovered = ImageLoader.loadImage("/textures/fireButtonHovered.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		fireButtonPressed = ImageLoader.loadImage("/textures/fireButtonPressed.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		scope = ImageLoader.loadImage("/textures/scope.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		fire = ImageLoader.loadImage("/textures/fire.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		water = ImageLoader.loadImage("/textures/water2.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		
		try {
			junebug = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/junebug.ttf"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		
		junebug60 = junebug.deriveFont(60F);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		junebug50 = junebug.deriveFont(50F);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		
		helvetica25 = new Font("Helvetica", Font.BOLD, 25);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		helvetica45 = new Font("Helvetica", Font.PLAIN, 45);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		helvetica35 = new Font("Helvetica", Font.BOLD, 35);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		helvetica20 = new Font("Helvetica", Font.BOLD, 20);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		helvetica30 = new Font("Helvetica", Font.BOLD, 30);
		
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		errorFont = new Font("Helvetica", Font.BOLD, 15);
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		
		randomButton = ImageLoader.loadImage("/textures/rdmButton.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		randomButtonHovered = ImageLoader.loadImage("/textures/rdmButtonHovered.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		randomButtonPressed = ImageLoader.loadImage("/textures/rdmButtonPressed.png");
		display.getProgressBar().setValue(display.getProgressBar().getValue() + 2);
		
		display.getProgressBar().setValue(100);
	}

}
