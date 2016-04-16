package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import sun.applet.Main;

public class ImageLoader {
	
	public static BufferedImage loadImage(String path)
	{
		try {
			return ImageIO.read(Main.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
}
