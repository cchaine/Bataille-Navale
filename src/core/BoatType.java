package core;

/**
 * @file src/core/BoatType.java
 * @author cchaine
 *
 * @brief Défini les types de bateaux et leur taille
 */
public enum BoatType {
	
	PORTEAVION(5),
	CROISEUR(4),
	CONTRETORPILLEUR(3),
	SOUSMARIN(3),
	TORPILLEUR(2);
	
	private int size;
	
	/**
	 * @brief Constructeur privé
	 * @param size		Taille du bateau
	 */
	private BoatType(int size)
	{
		this.size = size;
	}
	
	public int getSize(){
		return size;
	}
}
