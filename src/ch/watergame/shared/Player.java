package ch.watergame.shared;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import com.google.gwt.user.client.Window;

public class Player {
	//initial indicators
	int initialIndicatorWirtschaft;

	int initialIndicatorLebensquali;
	int initialIndicatorUmwelt;
	
	int wirtschaftsKraft;
	int lebensQuali;
	int umweltfreundlichkeit;
	
	int budget;
	int population;
	int id;
	

	//Landwirtschaft	
	int rize;
	int the;
	int fish;
	int sugar;
	int technology;
	int knowhow;
	
	//industrie
	int leder;
	int textil;
	int it;
	
	GameField gamefield;
	private Queue<Trade> tradePartners = new LinkedList<Trade>();

	public Queue<Trade> getTradePartner() {
		return tradePartners;
	}

	public void addTradeContract(Trade trade){
		this.getTradePartner().add(trade);
		System.out.println(trade);
	}
	
	public void removeTradeContract(){
		this.getTradePartner().poll();
	}

	public Player(int initialIndicatorWirtschaft, int indicatorWirtschaft, int initialIndicatorLebensquali, int indicatorLebensquali, int initialIndicatorUmwelt, int indicatorUmwelt, int budget, int population, int id,
			int rize, int the, int fish, int sugar, int technology, int knowhow, int leder, int textil, int it, GameField gamefield) {
		super();
		this.wirtschaftsKraft = indicatorWirtschaft;
		this.lebensQuali = indicatorLebensquali;
		this.umweltfreundlichkeit = indicatorUmwelt;
		this.initialIndicatorWirtschaft = initialIndicatorWirtschaft;
		this.initialIndicatorLebensquali = initialIndicatorLebensquali;
		this.initialIndicatorUmwelt = initialIndicatorUmwelt;
		this.budget = budget;
		this.population = population;
		this.id = id;
		this.rize = rize;
		this.the = the;
		this.fish = fish;
		this.sugar = sugar;
		this.technology = technology;
		this.knowhow = knowhow;
		this.leder = leder;
		this.textil = textil;
		this.it = it;
		this.gamefield = gamefield;
	}

	public int getWirtschaftsKraft() {
		return wirtschaftsKraft;
	}
	
	public void setWirtschaftsKraft(int wirtschaftsKraft) {
		this.wirtschaftsKraft = wirtschaftsKraft;
	}
	
	public int getLebensQuali() {
		return lebensQuali;
	}
	
	public void setLebensQuali(int lebensQuali) {
		this.lebensQuali = lebensQuali;
	}
	
	public int getUmweltfreundlichketi() {
		return umweltfreundlichkeit;
	}
	
	public void setUmweltfreundlichketi(int umweltfreundlichketi) {
		umweltfreundlichketi = umweltfreundlichketi;
	}
	public int getInitialIndicatorWirtschaft() {
		return initialIndicatorWirtschaft;
	}

	
	public int getInitialIndicatorLebensquali() {
		return initialIndicatorLebensquali;
	}

	
	public int getInitialIndicatorUmwelt() {
		return initialIndicatorUmwelt;
	}

	

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRize() {
		return rize;
	}

	public void setRize(int rize) {
		this.rize = rize;
	}

	public int getThe() {
		return the;
	}

	public void setThe(int the) {
		this.the = the;
	}

	public int getFish() {
		return fish;
	}

	public void setFish(int fish) {
		this.fish = fish;
	}

	public int getSugar() {
		return sugar;
	}

	public void setSugar(int sugar) {
		this.sugar = sugar;
	}

	public int getTechnology() {
		return technology;
	}

	public void setTechnology(int technology) {
		this.technology = technology;
	}

	public int getKnowhow() {
		return knowhow;
	}

	public void setKnowhow(int knowhow) {
		this.knowhow = knowhow;
	}

	public int getLeder() {
		return leder;
	}

	public void setLeder(int leder) {
		this.leder = leder;
	}

	public int getTextil() {
		return textil;
	}

	public void setTextil(int textil) {
		this.textil = textil;
	}

	public int getIt() {
		return it;
	}

	public void setIt(int it) {
		this.it = it;
	}

	public GameField getGamefield() {
		return gamefield;
	}

	public void setGamefield(GameField gamefield) {
		this.gamefield = gamefield;
	}
	
	public int getRessource(String ressource){
		if(ressource.equals("Reis")){
			return rize;
		}
		else if(ressource.equals("Tee")){
			return the;
		}
else if(ressource.equals("Zucker")){
			return sugar;
		}
else if(ressource.equals("Fisch")){
	return fish;
	
}else if(ressource.equals("Leder")){
	return leder;
	
}else if(ressource.equals("Textilien")){
	return textil;
	
}else if(ressource.equals("IT")){
	return it;
	
}else if(ressource.equals("Wissen")){
	return knowhow;
}else{
	Window.alert("Non Valid String");
	return 0;
}

	}
	

	
	
	

}
