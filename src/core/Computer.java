package core;

import java.util.ArrayList;
import java.util.PrimitiveIterator.OfDouble;

import state.EndGameState;
import state.MenuState;
import state.StateManager;

public class Computer extends Player {

	private int currentDirection = 0;
	private boolean focused = false;
	private ArrayList<Integer> triedDirections = new ArrayList<>();
	private int deadCounter;
	private String firstFire = "";
	private boolean wrongDirection = false;
	private boolean firstDirectionGood = false;

	public Computer(String name) {
		super(name, generateBoatRandom());
	}

	private ArrayList<Boat> boats;
	private ArrayList<Boat> deadBoats;

	public static ArrayList<Boat> generateBoatRandom() {
		ArrayList<Boat> boat = new ArrayList<>();
		Boat boatBuffer;
		BoatType types[] = { BoatType.PORTEAVION, BoatType.CROISEUR, BoatType.CONTRETORPILLEUR, BoatType.SOUSMARIN,
				BoatType.TORPILLEUR };
		for (int i = 0; i < types.length; i++) {
			boolean isIn = false;
			boolean isAlready = true;
			do {
				int direction = (int) (1 + (Math.random() * (5 - 1)));
				char column = (char) ((int) (65 + (Math.random() * (75 - 65))));
				int line = (int) (1 + (Math.random() * (11 - 1)));

				char columnBuffer = column;
				int lineBuffer = line;
				switch (direction) {
				case 1:
					columnBuffer += types[i].getSize();
					if (columnBuffer > 'J') {
						continue;
					}
					break;

				case 2:
					lineBuffer += types[i].getSize();
					if (lineBuffer > 10) {
						continue;
					}
					break;

				case 3:
					columnBuffer -= types[i].getSize();
					if (columnBuffer < 'A') {
						continue;
					}
					break;

				case 4:
					lineBuffer -= types[i].getSize();
					if (lineBuffer < 1) {
						continue;
					}
					break;
				}
				boatBuffer = new Boat(types[i], (String) (column + Integer.toString(line)), direction);
				isAlready = isOnCase(boatBuffer, boat);
				if (!isAlready) {
					boat.add(boatBuffer);
					isIn = true;
				}
			} while (!isIn || isAlready);
		}

		return boat;
	}

	private boolean alreadyTriedDirection() {
		for (int i = 0; i < triedDirections.size(); i++) {
			if (currentDirection == triedDirections.get(i)) {
				return true;
			}
		}
		return false;
	}

	private void changeDirection() {
		boolean noAlreadyDirection = false;
		if (!alreadyTriedDirection() && currentDirection != 0) {
			triedDirections.add(currentDirection);
		}
		if (firstDirectionGood) {
			switch (currentDirection) {
			case 1:
				currentDirection = 3;
				break;

			case 2:
				currentDirection = 4;
				break;

			case 3:
				currentDirection = 1;
				break;

			case 4:
				currentDirection = 2;
				break;
			}
			return;
		}
		do {
			currentDirection = (int) (1 + (Math.random() * (5 - 1)));
			if (triedDirections.size() != 0) {
				for (int i = 0; i < triedDirections.size(); i++) {
					if (currentDirection == triedDirections.get(i)) {
						noAlreadyDirection = true;
						continue;
					}
				}
				noAlreadyDirection = false;
			} else {
				noAlreadyDirection = false;
			}
		} while (noAlreadyDirection);
	}

	private boolean isOnDeadBoat(String cases, Player opponent) {
		for (int i = 0; i < opponent.getDeadBoats().size(); i++) {
			if (opponent.getDeadBoats().get(i).isOnCase(cases)) {
				return true;
			}
		}
		return false;
	}

	private boolean checkBoatInjured(StateManager stateManager) {
		for (int i = 0; i < this.getWinHistory().size(); i++) {
			if (!isOnDeadBoat(this.getWinHistory().get(i), stateManager.getCore().getPlayers().get(0))) {
				focused = true;
				firstFire = this.getWinHistory().get(i);
				deadCounter = stateManager.getCore().getPlayers().get(0).getDeadBoats().size();
				return true;
			}
		}
		return false;
	}

	@Override
	public void play(StateManager stateManager) {

		boolean fired = false;
		do {
			if (focused) {
				if (currentDirection == 0)
					changeDirection();

				String nextShot = "";
				switch (currentDirection) {
				case 1:
					if (!wrongDirection) {
						nextShot = (String) ((char) (this.getWinHistory().get(this.getWinHistory().size() - 1).charAt(0)
								+ 1) + this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1));
					} else {
						nextShot = (String) ((char) (firstFire.charAt(0) + 1) + firstFire.substring(1));
					}
					break;

				case 2:
					if (!wrongDirection) {
						nextShot = (String) (this.getWinHistory().get(this.getWinHistory().size() - 1).substring(0, 1)
								+ Integer.toString(Integer.parseInt(
										this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1)) + 1));
					} else {
						nextShot = (String) (firstFire.substring(0, 1)
								+ Integer.toString(Integer.parseInt(firstFire.substring(1)) + 1));
					}
					break;

				case 3:
					if (!wrongDirection) {
						nextShot = (String) ((char) (this.getWinHistory().get(this.getWinHistory().size() - 1).charAt(0)
								- 1) + this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1));
					} else {
						nextShot = (String) ((char) (firstFire.charAt(0) - 1) + firstFire.substring(1));
					}
					break;

				case 4:
					if (!wrongDirection) {
						nextShot = (String) (this.getWinHistory().get(this.getWinHistory().size() - 1).substring(0, 1)
								+ Integer.toString(Integer.parseInt(
										this.getWinHistory().get(this.getWinHistory().size() - 1).substring(1)) - 1));
					} else {
						nextShot = (String) (firstFire.substring(0, 1)
								+ Integer.toString(Integer.parseInt(firstFire.substring(1)) - 1));
					}
					break;
				}

				if (nextShot.charAt(0) <= 'J' && nextShot.charAt(0) >= 'A'
						&& Integer.parseInt(nextShot.substring(1)) >= 1
						&& Integer.parseInt(nextShot.substring(1)) <= 10) {
					if (!alreadyPlayed(nextShot)) {
						if (stateManager.addFire(nextShot)) {
							fired = false;
							firstDirectionGood = true;
							wrongDirection = false;
							if (deadCounter < stateManager.getCore().getPlayers().get(0).getDeadBoats().size()) {
								focused = false;
								triedDirections = new ArrayList<>();
								currentDirection = 0;
								firstFire = "";
								firstDirectionGood = false;
							}
						} else {
							if (triedDirections.size() == 4) {
								focused = false;
								triedDirections = new ArrayList<>();
								currentDirection = 0;
								firstFire = "";
								firstDirectionGood = false;
							} else {
								changeDirection();
								wrongDirection = true;
								fired = true;
							}
						}
					} else {
						if (triedDirections.size() == 4) {
							focused = false;
							triedDirections = new ArrayList<>();
							currentDirection = 0;
							firstFire = "";
							firstDirectionGood = false;
						} else {
							changeDirection();
							wrongDirection = true;
						}
					}
				} else {
					if (triedDirections.size() == 4) {
						focused = false;
						triedDirections = new ArrayList<>();
						currentDirection = 0;
						firstFire = "";
						firstDirectionGood = false;
					} else {
						changeDirection();
						wrongDirection = true;
						fired = false;
					}
				}

			} else {
				if (checkBoatInjured(stateManager)) {
					continue;
				}
				char column = (char) ((int) (65 + (Math.random() * (75 - 65))));
				int line = (int) (1 + (Math.random() * (11 - 1)));
				if (!alreadyPlayed((String) (column + Integer.toString(line)))) {
					int luckValue = 0;
					int roundValue = 0;
					String aroundCases[] = new String[4];
					aroundCases[0] = (String) ((char) (column + 1) + Integer.toString(line));
					aroundCases[1] = (String) (column + Integer.toString(line + 1));
					aroundCases[2] = (String) ((char) (column - 1) + Integer.toString(line));
					aroundCases[3] = (String) (column + Integer.toString(line - 1));
					for (int i = 0; i < 4; i++) {
						if (alreadyPlayed(aroundCases[i])) {
							luckValue++;
						}
						char columnBuffer = aroundCases[i].charAt(0);
						int lineBuffer = Integer.parseInt(aroundCases[i].substring(1));
						if (columnBuffer < 'A' || columnBuffer > 'J' || lineBuffer > 10 || lineBuffer < 1) {
							roundValue++;
						}
					}
					int random = (int) (-20 + (Math.random() * (101 - 1)));

					switch (luckValue) {
					case 1:
						switch (roundValue) {
						case 0:
							if (random < 75) {
								continue;
							}
							break;

						case 1:
							if (random < 65) {
								continue;
							}
							break;

						case 2:
							if (random < 70) {
								continue;
							}
							break;
						}
						break;

					case 2:
						switch (roundValue) {
						case 0:
							if (random < 70) {
								continue;
							}
							break;

						case 1:
							if (random < 60) {
								continue;
							}
							break;

						case 2:
							if (random < 65) {
								continue;
							}
							break;
						}
						break;

					case 3:
						switch (roundValue) {
						case 0:
							if (random < 80) {
								continue;
							}
							break;

						case 1:
							if (random < 70) {
								continue;
							}
							break;

						case 2:
							if (random < 75) {
								continue;
							}
							break;
						}
						break;

					case 4:
						switch (roundValue) {
						case 0:
							if (random < 95) {
								continue;
							}
							break;

						case 1:
							if (random < 85) {
								continue;
							}
							break;

						case 2:
							if (random < 90) {
								continue;
							}
							break;
						}
						break;
					}

					System.out.println("LUCK VALUE " + luckValue);
					System.out.println("RANDOM " + random);
					if (stateManager.addFire((String) (column + Integer.toString(line)))) {
						fired = false;
						focused = true;
						firstFire = (String) (column + Integer.toString(line));
						deadCounter = stateManager.getCore().getPlayers().get(0).getDeadBoats().size();
					} else {
						fired = true;
					}
				}
			}
		} while (!fired);
		//stateManager.setCurrentPlayer(0);
	}

	public static boolean isOnCase(Boat boatBuffer, ArrayList<Boat> boat) {
		if (!boat.isEmpty()) {
			for (int k = 0; k < boat.size(); k++) {
				for (int j = 0; j < boatBuffer.getCases().size(); j++) {
					if (boat.get(k).isOnCase(boatBuffer.getCases().get(j))) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
