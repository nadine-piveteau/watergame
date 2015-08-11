package ch.watergame.shared;

import java.util.ArrayList;

public class Game {
	public ArrayList<Player> playerlist = new ArrayList<Player>();
	public final int MAXPLAYER = 2;
	public int playingPlayer = 1;

	public int getPlayerlistSize() {
		return playerlist.size();
	}
//define initial situation of each player
	public void addPlayer() {
		if (playerlist.size() == 0) {
			GameField gameField1 = new GameField();
			gameField1.setGameFieldforPlayer(1);
			Player player = new Player(40, 40,40,40, 40, 40, 4000,500,1,250,250,250,250,250,250,250,250,250,gameField1);
			playerlist.add(player);
			
		} else if (playerlist.size() == 1) {
			GameField gameField2 = new GameField();
			gameField2.setGameFieldforPlayer(2);
			Player player = new Player(40,40,40,40,40,40,1000,1000,2,300,300,300,300,300,300,300,300,300, gameField2);
			playerlist.add(player);
		} else if (playerlist.size() == 3) {
			GameField gameField3 = new GameField();
			gameField3.setGameFieldforPlayer(3);
			Player player = new Player(40,40,40,40,40,40,1500,2000,3,500,500,500,500,500,500,500,500,500,gameField3);
			playerlist.add(player);
		} else if (playerlist.size() == 4) {
			GameField gameField4 = new GameField();
			gameField4.setGameFieldforPlayer(4);
			Player player = new Player(40,40,40,40,40,40,3000,4000,4,600,600,600,600,600,600,600,600,600,gameField4);
			playerlist.add(player);
		}
	}

	public int getMaxPlayer() {
		return MAXPLAYER;
	}

	public void setNextPlayer(int playerID) {
		this.playingPlayer = playerID;
	}
	
	public int getPlayingPlayer(){
		return this.playingPlayer;
	}

}
