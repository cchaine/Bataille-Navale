package core;

import java.util.ArrayList;

public class Boat {
	
	private BoatType type;
	private ArrayList<String> cases = new ArrayList<>();
	private int direction;
	private boolean isAlive = true;
	private int PV;
	public Boat(BoatType type, String firstCase, String lastCase) {
		this.type = type;
		storeCases(firstCase, lastCase);
		PV = this.type.getSize();
	}

	public Boat(BoatType type, String firstCase, int direction) {
		this.type = type;
		this.direction = direction;
		storeCases(firstCase, direction);
		PV = this.type.getSize();
	}

	public void removePV() {
		if (isAlive) {
			PV--;
			if (PV <= 0) {
				isAlive = false;
			}
		}
	}

	private void storeCases(String first, int direction) {
		String lastCase = "";
		String lineFirst = first.substring(1);
		String columnFirst = first.substring(0, 1);

		switch (direction) {
		case 1:
			lastCase = String.valueOf((char) (columnFirst.charAt(0) + (type.getSize() - 1))) + lineFirst;
			break;

		case 2:
			lastCase = columnFirst + Integer.toString(Integer.parseInt(lineFirst) + (type.getSize() - 1));
			break;

		case 3:
			lastCase = String.valueOf((char) (columnFirst.charAt(0) - (type.getSize()) + 1) + lineFirst);
			break;

		case 4:
			lastCase = columnFirst + Integer.toString(Integer.parseInt(lineFirst) - (type.getSize() - 1));
			break;
		}
		storeCases(first, lastCase);
	}

	private void storeCases(String first, String last) {
		cases.add(first);
		String lineFirst = first.substring(1);
		String columnFirst = first.substring(0, 1);
		String lineLast = last.substring(1);
		String columnLast = last.substring(0, 1);
		if (lineFirst.equals(lineLast)) {
			if (columnFirst.charAt(0) < columnLast.charAt(0)) {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(String.valueOf((char) (columnFirst.charAt(0) + i)) + lineFirst);
				}
			} else {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(String.valueOf((char) (columnFirst.charAt(0) - i)) + lineFirst);
				}
			}
		} else {
			if (Integer.parseInt(lineFirst) < Integer.parseInt(lineLast)) {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(columnFirst + Integer.toString(Integer.parseInt(lineFirst) + i));
				}
			} else {
				for (int i = 1; i < type.getSize() - 1; i++) {
					cases.add(columnFirst + Integer.toString(Integer.parseInt(lineFirst) - i));
				}
			}
		}

		cases.add(last);
	}

	public boolean isOnCase(String cases) {
		for (int i = 0; i < this.cases.size(); i++) {
			if (this.cases.get(i).equals(cases)) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<String> getCases() {
		return cases;
	}

	public int getDirection() {
		return direction;
	}

	public BoatType getType() {
		return type;
	}
	
	public boolean isAlive()
	{
		return isAlive;
	}
	

}
