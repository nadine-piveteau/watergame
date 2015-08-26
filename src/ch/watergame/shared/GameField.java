package ch.watergame.shared;

import java.io.Serializable;
import java.util.Arrays;

public class GameField implements Serializable {
	public enum FieldType {
		// ertrag ressource, ertrag Budget, wissen, lebensqualitaet, Umwelt,
		// Wirtschaftskraft, Bevölkerung
		TEE(10, 20, 0, 0, 3, -2, 0), TEEBIO(10, 20, 0, 1, 1, 1, 0), RICE(10, 20, 0, 0, 3, -2, 0), RICEBIO(10, 20, 0, 1, 1, 1, 0), ZUCKER(10, 20, 0,
				0, 3, -2, 0), ZUCKERBIO(10, 20, 0, 1, 1, 1, 0), FISCH(10, 20, 0, 0, 3, -2, 0), FISHBIO(10, 20, 0, 1,1, 1, 0), LEDER(30, 60, 0, 0, 5, -5, 0), 
				LEDERBIO(30,60,0,2,2,2,0), TEXTIL(30, 60, 0, 1, 5, -3, 0), TEXTILBIO(30,60,0,1,1,2,0),IT(40, 60, 0, 1, 6, -3, 0), ITBIO(30,60,0,1,1,1,0),
				SIEDLUNG(0, 0, 0, 0, 0, -1, 100), SIEDLUNGBIO(0,0,0,0,0,0,100), UNTERSTUFE(0, 0, 20, 2, 0, 0,
				0), OBERSTUFE(0, 0, 40, 3, 0, 0, 0), UNI(0, 0, 60, 4, 0, 0, 0), NATIONALPARK(0, 0, 0, 0, 0.25, 0, 0), TEMPEL(0, 0, 0, 1, 0, 0, 0), WATER(
				0, 0, 0, 0, 0, 0, 0), EMPTY(0, 0, 0, 0, 0, 0, 0);

		private int ertragRessourcen;
		private int ertragBudget;
		private int ertragWissen;
		private int lebenQuali;
		private double wirtschaftsKraft;
		private int umweltFreundlichkeit;
		private int population;

		private FieldType(int ertragRessourcen, int ertragBudget, int ertragWissen, int quali, double wirtschaft, int umwelt, int pop) {
			this.ertragRessourcen = ertragRessourcen;
			this.ertragBudget = ertragBudget;
			this.ertragWissen = ertragWissen;
			this.lebenQuali = quali;
			this.wirtschaftsKraft = wirtschaft;
			this.umweltFreundlichkeit = umwelt;
			this.population = pop;
		}

		public int getLebenQuali() {
			return lebenQuali;
		}

		public void setLebenQuali(int lebenQuali) {
			this.lebenQuali = lebenQuali;
		}

		public double getWirtschaftsKraft() {
			return wirtschaftsKraft;
		}

		public int getPopulation() {
			return population;
		}

		public void setPopulation(int population) {
			this.population = population;
		}

		public void setWirtschaftsKraft(int wirtschaftsKraft) {
			this.wirtschaftsKraft = wirtschaftsKraft;
		}

		public int getUmweltFreundlichkeit() {
			return umweltFreundlichkeit;
		}

		public void setUmweltFreundlichkeit(int umweltFreundlichkeit) {
			this.umweltFreundlichkeit = umweltFreundlichkeit;
		}

		public void setErtragRessourcen(int ertragRessourcen) {
			this.ertragRessourcen = ertragRessourcen;
		}

		public void setErtragBudget(int ertragBudget) {
			this.ertragBudget = ertragBudget;
		}

		public void setErtragWissen(int ertragWissen) {
			this.ertragWissen = ertragWissen;
		}

		public int getErtragRessourcen() {
			return ertragRessourcen;
		}

		public int getErtragBudget() {
			return ertragBudget;
		}

		public int getErtragWissen() {
			return ertragWissen;
		}
	};

	private FieldType[][] gameField = new FieldType[15][15];

	public GameField() {

		// initialize game field
		for (int i = 0; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				gameField[i][j] = FieldType.EMPTY;
			}
		}

		// set custom game fields

		// set water fields
		// 11 is the limit to the water, may change
		for (int i = 11; i < gameField.length; i++) {
			for (int j = 0; j < gameField[0].length; j++) {
				gameField[i][j] = FieldType.WATER;
			}
		}
	}

	void setGameFieldforPlayer(int playerId) {
		if (playerId == 0) {
			// fill nationalpark
			for (int row = 0; row < 7; row++) {
				for (int col = 8; col < 15; col++) {
					gameField[row][col] = FieldType.NATIONALPARK;
				}
			}
			// fill sieldung
			gameField[3][5] = FieldType.SIEDLUNG;
			gameField[3][6] = FieldType.TEMPEL;
			gameField[2][5] = FieldType.TEE;
			gameField[2][6] = FieldType.RICE;
			gameField[3][7] = FieldType.UNTERSTUFE;
		}
		if (playerId == 1) {
			gameField[6][6] = FieldType.SIEDLUNG;
			gameField[6][7] = FieldType.SIEDLUNG;
			gameField[6][8] = FieldType.SIEDLUNG;
			gameField[6][9] = FieldType.SIEDLUNG;
			gameField[5][7] = FieldType.SIEDLUNG;
			gameField[5][8] = FieldType.SIEDLUNG;
			gameField[5][9] = FieldType.SIEDLUNG;
			gameField[4][8] = FieldType.SIEDLUNG;
			gameField[4][9] = FieldType.SIEDLUNG;
			gameField[5][6] = FieldType.TEMPEL;
			gameField[4][7] = FieldType.TEMPEL;
			gameField[6][5] = FieldType.ZUCKER;
			gameField[5][5] = FieldType.ZUCKER;
			gameField[6][4] = FieldType.LEDER;
			gameField[5][4] = FieldType.LEDER;
			gameField[5][6] = FieldType.UNTERSTUFE;
			gameField[4][6] = FieldType.UNTERSTUFE;
			gameField[4][7] = FieldType.OBERSTUFE;
			gameField[4][5] = FieldType.OBERSTUFE;

		}
		
		if(playerId==2){
			gameField[6][6] = FieldType.SIEDLUNG;
			gameField[6][7] = FieldType.SIEDLUNG;
			gameField[6][8] = FieldType.SIEDLUNG;
			gameField[6][9] = FieldType.SIEDLUNG;
			gameField[5][7] = FieldType.SIEDLUNG;
			gameField[5][8] = FieldType.SIEDLUNG;
			gameField[5][9] = FieldType.RICE;
			gameField[4][8] = FieldType.TEXTIL;
			gameField[4][9] = FieldType.TEXTIL;
			gameField[5][6] = FieldType.UNTERSTUFE;
			gameField[4][6] = FieldType.OBERSTUFE;
			gameField[4][7] = FieldType.TEMPEL;
			gameField[4][5] = FieldType.TEMPEL;
			gameField[5][6] = FieldType.TEMPEL;
			gameField[6][5] = FieldType.TEMPEL;
			gameField[5][5] = FieldType.TEMPEL;
			gameField[4][4] = FieldType.UNTERSTUFE;
			gameField[5][4] = FieldType.UNTERSTUFE;


		}
		if(playerId==3){
			gameField[3][4] = FieldType.TEMPEL;
			gameField[3][5] = FieldType.TEMPEL;
			gameField[3][6] = FieldType.TEMPEL;
			gameField[4][5] = FieldType.IT;
			gameField[4][6] = FieldType.UNTERSTUFE;
			gameField[4][7] = FieldType.IT;
			gameField[4][8] = FieldType.OBERSTUFE;
			gameField[4][9] = FieldType.OBERSTUFE;
			gameField[5][6] = FieldType.IT;
			gameField[5][7] = FieldType.SIEDLUNG;
			gameField[5][8] = FieldType.SIEDLUNG;
			gameField[5][9] = FieldType.UNTERSTUFE;
			gameField[6][5] = FieldType.SIEDLUNG;
			gameField[6][6] = FieldType.SIEDLUNG;
			gameField[6][7] = FieldType.SIEDLUNG;
			gameField[6][8] = FieldType.SIEDLUNG;
			gameField[6][9] = FieldType.SIEDLUNG;
			gameField[7][5] = FieldType.SIEDLUNG;
			gameField[7][6] = FieldType.SIEDLUNG;
			gameField[7][7] = FieldType.SIEDLUNG;
			gameField[7][8] = FieldType.SIEDLUNG;
			gameField[7][9] = FieldType.UNTERSTUFE;



			gameField[10][5] = FieldType.FISCH;
			gameField[10][6] = FieldType.FISCH;

			


			
		}
	}

	public FieldType[][] getGameField() {
		return gameField;
	}

	public void setGameField(FieldType[][] gameField) {
		this.gameField = gameField;
	}

	public int getNrofSiedlung() {
		int nrSiedlung = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.SIEDLUNG) {
					nrSiedlung++;
				}
			}
		}
		return nrSiedlung;
	}

	public int getNrofRice() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.RICE) {
					nr++;
					System.out.println();
				}
			}
		}
		return nr;
	}
	public int getNrofRiceBio() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.RICEBIO) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofTee() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.TEE) {
					nr++;
				}
			}
		}
		return nr;
	}
	public int getNrofTeeBio() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.TEEBIO) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofZucker() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.ZUCKER) {
					nr++;
				}
			}
		}
		return nr;
	}
	public int getNrofZuckerBio() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.ZUCKERBIO) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofFisch() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.FISCH) {
					nr++;
				}
			}
		}
		return nr;
	}
	public int getNrofFischBio() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.FISHBIO) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofLeder() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.LEDER) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofTextil() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.TEXTIL) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofIT() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.IT) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofUnterstufe() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.UNTERSTUFE) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofOberstufe() {
		int nr = 0;
		for (int row = 0; row < 7; row++) {
			for (int col = 8; col < 15; col++) {
				if (gameField[row][col] == FieldType.OBERSTUFE) {
					nr++;
				}
			}
		}
		return nr;
	}

	public int getNrofUni() {
		int nr = 0;
		for (int row = 0; row < gameField.length; row++) {
			for (int col = 0; col < gameField[0].length; col++) {
				if (gameField[row][col] == FieldType.UNI) {
					nr++;
				}
			}
		}
		return nr;
	}

}
