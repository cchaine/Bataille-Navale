package utils;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JProgressBar;

import com.sun.org.apache.bcel.internal.generic.INEG;

import sun.applet.Main;

/**
 * @file src/graphics/AssetLoader.java
 * @author cchaine
 *
 * @brief Classe de stockage des ressources
 * @details Charge et stocke les ressources en static, pour un acces rapide
 * sans recharger des ressources plusieurs fois
 */

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
	
	/**
	 * @brief Chargement de toutes les ressources necessaires
	 * @detail Chargement des images et polices necessaires pour éviter les 
	 * ralentissement. Les ressources sont donc accessible statiquement
	 * 
	 * @param progressBar	Barre de progression du splashscreen
	 */
	
	public static void loadAssets(JProgressBar progressBar) {
	    boat = scaleImage(ImageLoader.loadImage("/textures/military.png"), 800, 400);
	    
		progressBar.setValue(progressBar.getValue() + 2); //Incrémentation de la barre de progression de 2
		waves = ImageLoader.loadImage("/textures/waves.png");
		progressBar.setValue(progressBar.getValue() + 2);
		grid = ImageLoader.loadImage("/textures/fullGrid.png");
		progressBar.setValue(progressBar.getValue() + 2);
		trash = scaleImage(ImageLoader.loadImage("/textures/recycle.png"), 140, 140);
		progressBar.setValue(progressBar.getValue() + 2);
		trashClickedImg = scaleImage(ImageLoader.loadImage("/textures/recycleClicked.png"), 140, 185);
		progressBar.setValue(progressBar.getValue() + 2);
		trashPressedImg = scaleImage(ImageLoader.loadImage("/textures/recyclePressed.png"), 140, 140);
		progressBar.setValue(progressBar.getValue() + 2);
		delete = ImageLoader.loadImage("/textures/delete.png");
		progressBar.setValue(progressBar.getValue() + 2);
		continueButton = scaleImage(ImageLoader.loadImage("/textures/addButton.png"), 125, 125);
		progressBar.setValue(progressBar.getValue() + 2);
		continueButtonPressed = scaleImage(ImageLoader.loadImage("/textures/addButtonPressed.png"), 125, 125);
		progressBar.setValue(progressBar.getValue() + 2);
		continueButtonHovered = scaleImage(ImageLoader.loadImage("/textures/addButtonHovered.png"), 125, 125);
		progressBar.setValue(progressBar.getValue() + 10);
		fireButton = scaleImage(ImageLoader.loadImage("/textures/fireButton.png"), 200, 200);
		progressBar.setValue(progressBar.getValue() + 2);
		fireButtonHovered = scaleImage(ImageLoader.loadImage("/textures/fireButtonHovered.png"), 200, 200);
		progressBar.setValue(progressBar.getValue() + 2);
		fireButtonPressed = scaleImage(ImageLoader.loadImage("/textures/fireButtonPressed.png"), 200, 200);
		progressBar.setValue(progressBar.getValue() + 2);
		scope = scaleImage(ImageLoader.loadImage("/textures/scope.png"), 70, 70);
		progressBar.setValue(progressBar.getValue() + 2);
		fire = scaleImage(ImageLoader.loadImage("/textures/fire.png"), 80, 80);
		progressBar.setValue(progressBar.getValue() + 2);
		water = scaleImage(ImageLoader.loadImage("/textures/water2.png"), 80, 80);
		progressBar.setValue(progressBar.getValue() + 2);
		
		//Création d'une police si 
		try {
			junebug = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/junebug.ttf"));
		} catch (Exception e) {
			e.printStackTrace(); //Impression de l'erreur
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
		
		randomButton = scaleImage(ImageLoader.loadImage("/textures/rdmButton.png"), 125, 125);
		progressBar.setValue(progressBar.getValue() + 2);
		//randomButtonHovered = scaleImage(ImageLoader.loadImage("/textures/rdmButtonHovered.png"), 125, 125);
		randomButtonHovered = ImageLoader.loadImage("/textures/rdmButtonHovered.png");
		progressBar.setValue(progressBar.getValue() + 2);
		randomButtonPressed = scaleImage(ImageLoader.loadImage("/textures/rdmButtonPressed.png"), 125, 125);
		progressBar.setValue(progressBar.getValue() + 2);
	}
	
	private static BufferedImage scaleImage(BufferedImage source, int width, int height)
	{
		Image tmp = source.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();
	    return dimg;
	}

}
