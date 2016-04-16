import core.Core;

/**
 * @file src/Launcher.java
 * @author cchaine
 *
 * @brief Initialise le programme et le lance
 */
public class Launcher {

	public static void main(String[] args)
	{
		//Créé et lance le coeur
		Core core = new Core();
		core.start();
	}
}
