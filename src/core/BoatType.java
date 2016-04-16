package core;

public enum BoatType {
	
	PORTEAVION(5),
	CROISEUR(4),
	CONTRETORPILLEUR(3),
	SOUSMARIN(3),
	TORPILLEUR(2);
	
	private int size;
	
	private BoatType(int size)
	{
		this.size = size;
	}
	
	public int getSize(){
		return size;
	}
}
