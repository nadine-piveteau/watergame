package ch.watergame.shared;

import java.util.ArrayList;

import ch.watergame.shared.GameField.FieldType;

public class Game {
	public ArrayList<Player> playerlist = new ArrayList<Player>();
	public final int MAXPLAYER = 2;
	public int playingPlayer = 1;
	private int spielWechsel = 0;
	private int gameRound = 1;
	private int eventNumber;

	public int getPlayerlistSize() {
		return playerlist.size();
	}
//define initial situation of each player
	public void addPlayer() {
		if (playerlist.size() == 0) {
			GameField gameField1 = new GameField();
			gameField1.setGameFieldforPlayer(1);
			Player player = new Player(34, 34, 135, (((double)34/135)*100) ,68,68, 172, (( (double)68/172 )*100), 33,33,58,(((double)33/58)*100), 4000,500,1,250,250,250,250,250,250,250,250,250,gameField1, 500);
			playerlist.add(player);
			
		} else if (playerlist.size() == 1) {
			GameField gameField2 = new GameField();
			gameField2.setGameFieldforPlayer(2);
			Player player = new Player(60,60,150, (( (double)60/150)*100), 120,120,300,(((double)120/300)*100),40,40,100,(((double)40/100)*100),1000,1000,2,300,300,300,300,300,300,300,300,300, gameField2, 1000);
			playerlist.add(player);

		} else if (playerlist.size() == 3) {
			GameField gameField3 = new GameField();
			gameField3.setGameFieldforPlayer(3);
			Player player = new Player(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,1500,2000,3,500,500,500,500,500,500,500,500,500,gameField3, 2000);
			playerlist.add(player);
		} else if (playerlist.size() == 4) {
			GameField gameField4 = new GameField();
			gameField4.setGameFieldforPlayer(4);
			Player player = new Player(40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,3000,4000,4,600,600,600,600,600,600,600,600,600,gameField4, 4000);
			playerlist.add(player);
		}
	}

	public int getMaxPlayer() {
		return MAXPLAYER;
	}

	public void setNextPlayer(int playerID) {
		this.playingPlayer = playerID;
		this.spielWechsel = this.spielWechsel+1;
	}
	
	public int getPlayingPlayer(){
		return this.playingPlayer;
	}
	
	public int getGameRound(){
		setGameRound();
		return this.gameRound;
	}
	
	public void setGameRound(){
		this.gameRound = (this.spielWechsel/4)+1;
	}
	
	public void lossLW(Player player){
		
		GameField gameFieldGame = player.getGamefield();
		int nrOfRice = gameFieldGame.getNrofRice();
		int nrOfRiceBio = gameFieldGame.getNrofRiceBio();
		int nrOfTee = gameFieldGame.getNrofTee();
		int nrOfTeeBio= gameFieldGame.getNrofTeeBio();
		int nrOfZucker = gameFieldGame.getNrofZucker();
		int nrOfZuckerBio = gameFieldGame.getNrofZuckerBio();
		int nrOfFisch = gameFieldGame.getNrofFisch();
		int nrOfFischBio = gameFieldGame.getNrofFischBio();
		System.out.println("VOR DER DèRRE 2: "+player.getRize());
		System.out.println("Player ID: "+player.id);
		int test = player.getRize()-(nrOfRice*FieldType.RICE.getErtragRessourcen())-(nrOfRiceBio*FieldType.RICEBIO.getErtragRessourcen());
		int testGetRize = player.getRize();
		int testRice = nrOfRice*FieldType.RICE.getErtragRessourcen();
		int testRiceBio = nrOfRiceBio*FieldType.RICEBIO.getErtragRessourcen();
		
		System.out.println("TEST RICE: "+test+ " , getRize(): "+ testGetRize +" nrOfRice: "+ nrOfRice +", Ertrag Rice: "+FieldType.RICE.getErtragRessourcen()+", testRice: "+testRice+" , testRiceBio: "+testRiceBio );
		System.out.println();
		player.setRize(player.getRize()-(nrOfRice*FieldType.RICE.getErtragRessourcen())-(nrOfRiceBio*FieldType.RICEBIO.getErtragRessourcen()));
		System.out.println("NACH DER DèRRE 2: "+player.getRize());
		player.setThe(player.getThe()-(nrOfTee*FieldType.TEE.getErtragRessourcen())-(nrOfTeeBio*FieldType.RICEBIO.getErtragRessourcen()));
		player.setSugar(player.getSugar()-(nrOfZucker*FieldType.ZUCKER.getErtragRessourcen())-(nrOfZuckerBio*FieldType.ZUCKERBIO.getErtragRessourcen()));
		player.setFish(player.getFish()- (nrOfFisch*FieldType.FISCH.getErtragRessourcen())-(nrOfFischBio*FieldType.FISHBIO.getErtragRessourcen()));
	}
	

}
