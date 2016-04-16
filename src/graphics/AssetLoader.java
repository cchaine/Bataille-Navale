package graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JProgressBar;

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
	
	public static void loadAssets(JProgressBar progressBar) {
		boat = ImageLoader.loadImage("/textures/military.png");
		progressBar.setValue(progressBar.getValue() + 2);
		waves = ImageLoader.loadImage("/textures/waves.png");
		progressBar.setValue(progressBar.getValue() + 2);
		grid = ImageLoader.loadImage("/textures/fullGrid.png");
		progressBar.setValue(progressBar.getValue() + 2);
		trash = ImageLoader.loadImage("/textures/recycle.png");
		progressBar.setValue(progressBar.getValue() + 2);
		trashClickedImg = ImageLoader.loadImage("/textures/recycleClicked.png");
		progressBar.setValue(progressBar.getValue() + 2);
		trashPressedImg = ImageLoader.loadImage("/textures/recyclePressed.png");
		progressBar.setValue(progressBar.getValue() + 2);
		delete = ImageLoader.loadImage("/textures/delete.png");
		progressBar.setValue(progressBar.getValue() + 2);
		continueButton = ImageLoader.loadImage("/textures/addButton.png");
		progressBar.setValue(progressBar.getValue() + 2);
		continueButtonPressed = ImageLoader.loadImage("/textures/addButtonPressed.png");
		progressBar.setValue(progressBar.getValue() + 2);
		continueButtonHovered = ImageLoader.loadImage("/textures/addButtonHovered.png");
		progressBar.setValue(progressBar.getValue() + 2);
		attackButton = ImageLoader.loadImage("/textures/attackButton.png");
		progressBar.setValue(progressBar.getValue() + 2);
		attackButtonHovered = ImageLoader.loadImage("/textures/attackButtonHovered.png");
		progressBar.setValue(progressBar.getValue() + 2);
		attackButtonPressed = ImageLoader.loadImage("/textures/attackButtonPressed.png");
		progressBar.setValue(progressBar.getValue() + 2);
		fireButton = ImageLoader.loadImage("/textures/fireButton.png");
		progressBar.setValue(progressBar.getValue() + 2);
		fireButtonHovered = ImageLoader.loadImage("/textures/fireButtonHovered.png");
		progressBar.setValue(progressBar.getValue() + 2);
		fireButtonPressed = ImageLoader.loadImage("/textures/fireButtonPressed.png");
		progressBar.setValue(progressBar.getValue() + 2);
		scope = ImageLoader.loadImage("/textures/scope.png");
		progressBar.setValue(progressBar.getValue() + 2);
		fire = ImageLoader.loadImage("/textures/fire.png");
		progressBar.setValue(progressBar.getValue() + 2);
		water = ImageLoader.loadImage("/textures/water2.png");
		progressBar.setValue(progressBar.getValue() + 2);
		
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
		progressBar.setValue(progressBar.getValue() + 2);
		
		junebug60 = junebug.deriveFont(60F);
		progressBar.setValue(progressBar.getValue() + 2);
		junebug50 = junebug.deriveFont(50F);
		progressBar.setValue(progressBar.getValue() + 2);
		
		helvetica25 = new Font("Helvetica", Font.BOLD, 25);
		progressBar.setValue(progressBar.getValue() + 2);
		helvetica45 = new Font("Helvetica", Font.PLAIN, 45);
		progressBar.setValue(progressBar.getValue() + 2);
		helvetica35 = new Font("Helvetica", Font.BOLD, 35);
		progressBar.setValue(progressBar.getValue() + 2);
		helvetica20 = new Font("Helvetica", Font.BOLD, 20);
		progressBar.setValue(progressBar.getValue() + 2);
		helvetica30 = new Font("Helvetica", Font.BOLD, 30);
		
		progressBar.setValue(progressBar.getValue() + 2);
		errorFont = new Font("Helvetica", Font.BOLD, 15);
		progressBar.setValue(progressBar.getValue() + 2);
		
		randomButton = ImageLoader.loadImage("/textures/rdmButton.png");
		progressBar.setValue(progressBar.getValue() + 2);
		randomButtonHovered = ImageLoader.loadImage("/textures/rdmButtonHovered.png");
		progressBar.setValue(progressBar.getValue() + 2);
		randomButtonPressed = ImageLoader.loadImage("/textures/rdmButtonPressed.png");
		progressBar.setValue(progressBar.getValue() + 2);
		
		progressBar.setValue(100);
	}

}
