package ch.watergame.shared;

import java.util.ArrayList;

import sun.misc.Perf.GetPerfAction;
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
			Player player = new Player(34, 34, 135, (((double)34/135)*100) ,68,68, 172, (( (double)68/172 )*100), 33,33,58,(((double)33/58)*100), 400,500,1,250,250,250,250,250,250,250,250,250,gameField1, 500);
			playerlist.add(player);
			
		} else if (playerlist.size() == 1) {
			GameField gameField2 = new GameField();
			gameField2.setGameFieldforPlayer(2);
			Player player = new Player(60,60,150, (( (double)60/150)*100), 120,120,300,(((double)120/300)*100),40,40,100,(((double)40/100)*100),1000,700,2,300,300,300,300,300,300,300,300,300, gameField2, 1000);
			playerlist.add(player);

		} else if (playerlist.size() == 2) {
			GameField gameField3 = new GameField();
			gameField3.setGameFieldforPlayer(3);
			Player player = new Player(164, 164, 410, (( (double)164/410)*100),32,32,82,(( (double)32/82)*100), 131,131,328, (( (double)131/328)*100),1000,1500,3,500,500,500,500,500,500,500,500,500,gameField3, 2000);
			playerlist.add(player);
		} else if (playerlist.size() == 3) {
			GameField gameField4 = new GameField();
			gameField4.setGameFieldforPlayer(4);
			Player player = new Player(196, 196, 496, (( (double)196/496)*100), 32,32,82,(( (double)32/82)*100), 131,131,328, (( (double)131/328)*100),1500,4000,4,600,600,600,600,600,600,600,600,600,gameField4, 4000);
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
	
	public int getNrOfSpielwechsel(){
		return this.spielWechsel;
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
		player.setBudget(player.getBudget() - (nrOfRice*(FieldType.RICE.getErtragBudget()))-(nrOfRiceBio*(FieldType.RICEBIO.getErtragBudget()))- (nrOfTee*(FieldType.TEE.getErtragBudget())) - (nrOfTeeBio*(FieldType.TEEBIO.getErtragBudget()))- (nrOfZucker*(FieldType.ZUCKER.getErtragBudget()))-(nrOfZuckerBio*(FieldType.ZUCKERBIO.getErtragBudget()))- (nrOfFisch*(FieldType.FISCH.getErtragBudget()))- (nrOfFischBio*(FieldType.FISHBIO.getErtragBudget())));

	}
	
	public void halfLossLW(Player player){
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
		player.setRize(player.getRize()-(nrOfRice*(FieldType.RICE.getErtragRessourcen()/2))-(nrOfRiceBio*(FieldType.RICEBIO.getErtragRessourcen()/2)));
		System.out.println("NACH DER DèRRE 2: "+player.getRize());
		player.setThe(player.getThe()-(nrOfTee*(FieldType.TEE.getErtragRessourcen()/2))-(nrOfTeeBio*(FieldType.RICEBIO.getErtragRessourcen()/2)));
		player.setSugar(player.getSugar()-(nrOfZucker*(FieldType.ZUCKER.getErtragRessourcen()/2))-(nrOfZuckerBio*(FieldType.ZUCKERBIO.getErtragRessourcen()/2)));
		player.setFish(player.getFish()- (nrOfFisch*(FieldType.FISCH.getErtragRessourcen()/2))-(nrOfFischBio*(FieldType.FISHBIO.getErtragRessourcen()/2)));
		player.setBudget(player.getBudget() - (nrOfRice*(FieldType.RICE.getErtragBudget()/2))-(nrOfRiceBio*(FieldType.RICEBIO.getErtragBudget()/2))- (nrOfTee*(FieldType.TEE.getErtragBudget()/2)) - (nrOfTeeBio*(FieldType.TEEBIO.getErtragBudget()/2))- (nrOfZucker*(FieldType.ZUCKER.getErtragBudget()/2))-(nrOfZuckerBio*(FieldType.ZUCKERBIO.getErtragBudget()/2))- (nrOfFisch*(FieldType.FISCH.getErtragBudget()/2))- (nrOfFischBio*(FieldType.FISHBIO.getErtragBudget()/2)));
	}
	
	
	public int deduceBudget(double percentage, Player player){
		int oldBudget=player.getBudget();
		System.out.println("Old Budget: "+oldBudget);
		player.setBudget((int) (oldBudget-(oldBudget*percentage)));
		System.out.println("New Budget: "+ player.getBudget());
		return oldBudget-player.getBudget();
		
	}
	
	public int addBudget(double percentage, Player player){
		int oldBudget=player.getBudget();
		System.out.println("Old Budget: "+oldBudget);
		player.setBudget((int) (oldBudget+(oldBudget*percentage)));
		System.out.println("New Budget: "+ player.getBudget());
		return player.getBudget()-oldBudget;
	}
	
	public int deduceWirtschaftskraft(double percentage, Player player){
		int oldWirtschaftskraft=player.getInitialIndicatorWirtschaft();
		System.out.println("Old Wirtschaftskraft: "+oldWirtschaftskraft);
		player.initialIndicatorWirtschaft = ((int) (oldWirtschaftskraft-(oldWirtschaftskraft*percentage)));
		System.out.println("New Wirtschaftskraft: "+ player.getPercentualIndicatorWirtschaft());
		return (int)((player.initialIndicatorWirtschaft-oldWirtschaftskraft)/player.getMaxWirtschaft());
	}
	
	public void addUmweltIndikator(double percentage, Player player){
		int oldUmweltIndikator = player.getInitialIndicatorUmwelt();
		System.out.println("Old UmweltFreundlichkeit: "+oldUmweltIndikator);
		player.initialIndicatorUmwelt = ((int)(oldUmweltIndikator+(oldUmweltIndikator*percentage)));
		System.out.println("new UmweltFreundlichkeit: "+player.getUmweltfreundlichkeit());

	}
	public void addLebensquali(double percentage, Player player){
		int oldLebensquali = player.getInitialIndicatorLebensquali();
		System.out.println("oldLebensquali: "+oldLebensquali);
		player.initialIndicatorLebensquali = ((int)(oldLebensquali+(oldLebensquali*percentage)));
		System.out.println("new Lebensquali: "+player.getLebensQuali());

	}
	
	public void setNaturkatastrophenSchutz(boolean isprotected, Player player){
		player.setNaturkatastrophenSchutz(isprotected);
	}
	
	public double getCommonIndicator(){
		int counter = 0;
		int sum = 0;
		for(Player player : playerlist){
			sum = sum + player.getPercentualIndicatorWirtschaft() + player.getPercentualLebensquali() + player.getPercentualUmwelt();
			counter = counter + 3;
		}
		double indicator = (double) sum/ counter;
		return indicator;
	}

}
