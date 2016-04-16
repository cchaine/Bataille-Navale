package graphics;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.UIManager;

/**
 * @file src/graphics/Display.java
 * @author cchaine
 * 
 * @brief Cette classe se charge de créer une fenêtre
 * @details Cette classe créer un splashscreen pour charger toutes les 
 * ressources puis crée une fenêtre et ajoute la gestion de la souris et du 
 * clavier
 */

public class Display {
	
	public static JFrame frame;
	public static JWindow splashScreen;
	private Canvas canvas;
	
	private String title;
	public static int width, height;
	private JProgressBar progressBar;
	
	/**
	 * @brief Constructeur
	 * 
	 * @param title 	Titre de la fenêtre
	 * @param width		Longueur de la fenêtre
	 * @param height	Largeur de la fenêtre
	 */
	public Display(String title, int width, int height)
	{
		this.title = title;
		Display.width = width;
		Display.height = height;
		
		loadResources();
	}
	
	/**
	 * @brief Charge les ressources
	 * @details Créé un splashcreen affichant un barre de progression et 
	 * charge les ressources necessaires
	 */
	private void loadResources()
	{
		splashScreen = new JWindow();
		
		//Définir le thème MetalLookAndFeel si il est disponible
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		}catch(Exception e)
		{
			e.printStackTrace(); //Imprime l'erreur
		}
		
		splashScreen.setSize(width / 2, height / 2); //Défini la taille du splashScreen
		splashScreen.setLocationRelativeTo(null); //Le positionne au milieu
		
		
		progressBar = new JProgressBar(); //Créé une barre de chargement
		
		//Ordonne à la barre de progression de remplir le splashscreen
		Dimension prefSize = progressBar.getPreferredSize();
		prefSize.height = height;
		progressBar.setPreferredSize(prefSize);
		
		progressBar.setStringPainted(true); //Affiche le pourcentage au milieu
		progressBar.setValue(0);
		
		splashScreen.add(progressBar); //Ajoute la barre de progression au splashscreen
		splashScreen.setVisible(true);	//Affiche le splashscreen
		
		AssetLoader.loadAssets(progressBar); //Charge les ressources
		
		splashScreen.dispose(); //Une fois fini, ferme le splashscreen
	}
	
	/**
	 * @brief Créé la fenêtre principale
	 * @detail Initialise la fenêtre principale et y ajoute une toile 
	 * pour dessiner
	 */
	public void createDisplay()
	{
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Arreter le programme quand la fenêtre se ferme
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); //Positionner la fenêtre au milieu de l'écran
		frame.setVisible(true); //Affiche la fenêtre
		
		canvas = new Canvas(); //Créé une surface d'affichage
		
		//Remplir la fenêtre avec cette surface
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setMaximumSize(new Dimension(width, height));
		canvas.setMinimumSize(new Dimension(width, height));
		
		canvas.setFocusable(false); //Ne change pas l'état de la fenêtre quand la surface est cliqué
		
		frame.add(canvas);	//Ajoute la surface à la fenêtre
		frame.pack();	//Met à jour la fenêtre
	}
	
	/**
	 * @brief Controle de la souris
	 * @detail Défini quel outil se chargera de traiter les entrées de la 
	 * souris reçu par la fenêtre
	 * 
	 * @param mouse		Outil de gestion de la souris
	 */
	public void addMouseListener(MouseListener mouse)
	{
		canvas.addMouseListener(mouse);
	}
	
	/**
	 * @brief Controle du clavier
	 * @detail Défini quel outil se chargera de traiter les entrées du 
	 * clavier reçu par la fenêtre
	 * 
	 * @param key		Outil de gestion du clavier
	 */
	public void addKeyListener(KeyListener key)
	{
		frame.addKeyListener(key);
	}
	
	/**
	 * @return canvas	La surface de dessin
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}
}
