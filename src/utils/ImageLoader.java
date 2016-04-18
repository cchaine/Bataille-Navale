package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.applet.Main;

/**
 * @file src/graphics/ImageLoader.java
 * @author cchaine
 *
 * @brief Classe utilitaire pour charger une image
 */
public class ImageLoader {
	
	/**
	 * @brief Récupère l'image et la stocke dans un BufferedImage
	 * @param path		Chemin vers l'image
	 * @return BufferedImage	L'image chargée
	 */
	public static BufferedImage loadImage(String path)
	{
		//Récupère l'image
		try {
			return ImageIO.read(Main.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace(); //Imprime l'erreur s'il ne la trouve pas
			System.exit(1); //Quitte le programme avec une erreur
		}
		return null;
	}
}
