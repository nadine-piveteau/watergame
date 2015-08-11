package ch.watergame.server;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.watergame.client.GreetingService;
import ch.watergame.shared.FieldVerifier;
import ch.watergame.shared.Game;
import ch.watergame.shared.GameField;
import ch.watergame.shared.GameField.FieldType;
import ch.watergame.shared.Player;
import ch.watergame.shared.Trade;
import ch.watergame.shared.TradeResult;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {
	Game game = new Game();

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException("Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo + ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html
	 *            the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}

	@Override
	public String startGame() {
		String message;
		game.addPlayer();
		int numberOfPlayer = game.getPlayerlistSize();
		int maxPlayer = game.getMaxPlayer();
		// create session and store userid
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("UserID", numberOfPlayer);
		if (numberOfPlayer < maxPlayer) {
			message = "Bitte warten";
			String nr = Integer.toString(numberOfPlayer);
			return nr.concat(message);
		} else {
			message = "Das Spiel kann beginnen!";
			String nr = Integer.toString(numberOfPlayer);
			return nr.concat(message);
		}
	}

	@Override
	public String getPlayerID() {

		HttpServletRequest request = this.getThreadLocalRequest();
		// dont create a new one -> false
		HttpSession session = request.getSession(true);
		if (session.getAttribute("UserID") == null)
			return null;

		// session and userid is available, looks like user is logged in.
		Integer userId = (Integer) session.getAttribute("UserID");
		return userId.toString();
	}

	public String checkNrPlayer() {
		int nrPlayer = game.playerlist.size();
		String nrPlayerString = Integer.toString(nrPlayer);
		return nrPlayerString;
	}

	public TradeResult isMyTurn() {
		TradeResult trade = new TradeResult();
		if (game.getPlayingPlayer() == Integer.parseInt(this.getPlayerID())) {
			trade.myTurn = true;
			Player playingPlayer = game.playerlist.get(game.getPlayingPlayer() - 1);
			trade.tradeContractResult = playingPlayer.getTradePartner();
			return trade;
		} else {
			trade.myTurn = false;
			return trade;
		}
	}

	public void setNextPlayer() {
		if (game.getPlayingPlayer() < game.MAXPLAYER) {
			game.setNextPlayer(game.getPlayingPlayer() + 1);
		} else {
			game.setNextPlayer(1);
		}
	}

	public ArrayList<Integer> getRessource(int playerID) {
		ArrayList<Integer> ressourceList = new ArrayList<Integer>();
		// indicator
		int indicatorWirtschaft = getIndicatorWirtschaft(playerID);
		ressourceList.add(indicatorWirtschaft);
		int indicatorLebensquali = getIndicatorLebensquali(playerID);
		ressourceList.add(indicatorLebensquali);
		int indicatorUmwelt = getIndicatorUmwelt(playerID);
		ressourceList.add(indicatorUmwelt);
		// pop
		int pop = getPopulation(playerID);
		ressourceList.add(pop);
		// budget
		int budget = getBudget(playerID);
		ressourceList.add(budget);
		// lw
		int rize = getRize(playerID);
		ressourceList.add(rize);
		int the = getThe(playerID);
		ressourceList.add(the);
		int sugar = getSugar(playerID);
		ressourceList.add(sugar);
		int fish = getFish(playerID);
		ressourceList.add(fish);
		// industrie
		int leder = getLeder(playerID);
		ressourceList.add(leder);
		int textil = getTextil(playerID);
		ressourceList.add(textil);
		int it = getIT(playerID);
		ressourceList.add(it);
		int know = getKnowHow(playerID);
		ressourceList.add(know);

		return ressourceList;

	}

	public int getIndicatorWirtschaft(int playerID) {
		int indicator = game.playerlist.get(playerID - 1).getWirtschaftsKraft();
		return indicator;
	}

	public int getIndicatorLebensquali(int playerID) {
		int indicator = game.playerlist.get(playerID - 1).getLebensQuali();
		return indicator;
	}

	public int getIndicatorUmwelt(int playerID) {
		int indicator = game.playerlist.get(playerID - 1).getUmweltfreundlichketi();
		return indicator;
	}

	public int getRize(int playerID) {
		int rize = game.playerlist.get(playerID - 1).getRize();
		return rize;
	}

	public int getThe(int playerID) {
		int the = game.playerlist.get(playerID - 1).getThe();
		return the;
	}

	public int getSugar(int playerID) {
		int sugar = game.playerlist.get(playerID - 1).getSugar();
		return sugar;
	}

	public int getFish(int playerID) {
		int fish = game.playerlist.get(playerID - 1).getFish();
		return fish;
	}

	public int getLeder(int playerID) {
		int leder = game.playerlist.get(playerID - 1).getLeder();
		return leder;
	}

	public int getTextil(int playerID) {
		int textil = game.playerlist.get(playerID - 1).getTextil();
		return textil;
	}

	public int getIT(int playerID) {
		int it = game.playerlist.get(playerID - 1).getIt();
		return it;
	}

	public int getKnowHow(int playerID) {
		int know = game.playerlist.get(playerID - 1).getKnowhow();
		return know;
	}

	public int getPopulation(int playerID) {
		int pop = game.playerlist.get(playerID - 1).getPopulation();
		return pop;
	}

	public int getBudget(int playerID) {
		int budget = game.playerlist.get(playerID - 1).getBudget();
		return budget;
	}

	public void setBudget(int playerID, int budget) {
		game.playerlist.get(playerID - 1).setBudget(budget);

	}
	
	public void setKnowHow(int playerID, int amountknowhow){
		game.playerlist.get(playerID-1).setKnowhow(amountknowhow);
	}
	
	@Override
	public String checkBudgetAndKnowledge(int playerID, int rizePrice, int amountknowledge){
		String message;
		int budget = getBudget(playerID) - rizePrice;
		int knowledgeNew = getKnowHow(playerID)-amountknowledge;
		if(budget >=0 && knowledgeNew<0){
			message = "Your city has not enough Knowledge.";
			return message;
		}
		else if (budget <0){
			message = "Game Over...";
			return message;
		}else{
			return "OK";
		}
	}

	@Override
	public String refreshBudget(int playerID, int rizePrice) {
		int budget = getBudget(playerID) - rizePrice;
		setBudget(playerID, budget);
		String budgetString = Integer.toString(budget);
		return budgetString;

	}
	
	@Override
	public String refreshKnowledge(int playerID, int amountknowledge){
		int knowledgeNew = getKnowHow(playerID)-amountknowledge;
		if(knowledgeNew >=0){
		setKnowHow(playerID, knowledgeNew);
		String knowledgeString = Integer.toString(knowledgeNew);
		return knowledgeString;
		}else{
			return "notPossible";
		}
	}

	@Override
	public GameField getGameField(int playerID) {
		GameField gamefield = game.playerlist.get(playerID - 1).getGamefield();

		return gamefield;
	}

	@Override
	public void setFieldintoGameField(int playerID, int row, int col, String img) {
		GameField gamefield = game.playerlist.get(playerID - 1).getGamefield();
		if (img.equals("fisch.jpg"))
			gamefield.getGameField()[row][col] = FieldType.FISCH;
		if (img.equals("rizeField.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.RICE;
		if (img.equals("rizebio.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.RICEBIO;
		if (img.equals("tee.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.TEE;
		if (img.equals("teebio.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.TEEBIO;
		if (img.equals("zucker.jpg"))
			gamefield.getGameField()[row][col] = FieldType.ZUCKER;
		if (img.equals("leder.JPG"))
			gamefield.getGameField()[row][col] = FieldType.LEDER;
		if (img.equals("textil.jpg"))
			gamefield.getGameField()[row][col] = FieldType.TEXTIL;
		if (img.equals("it.jpg"))
			gamefield.getGameField()[row][col] = FieldType.IT;
		if (img.equals("siedlung.jpg")) {
			gamefield.getGameField()[row][col] = FieldType.SIEDLUNG;
		}
		if (img.equals("unterstufe.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.UNTERSTUFE;
		if (img.equals("oberstufe.jpeg"))
			gamefield.getGameField()[row][col] = FieldType.OBERSTUFE;
		if (img.equals("uni.jpg"))
			gamefield.getGameField()[row][col] = FieldType.UNI;
		if (img.equals("transparent_graphic.png")){
			gamefield.getGameField()[row][col] = FieldType.EMPTY;
		}
		//
		// for (int i = 0; i < gamefield.getGameField().length; i++) {
		// for (int j = 0; j < gamefield.getGameField()[0].length; j++) {
		// System.out.print(gamefield.getGameField()[i][j].getErtragBudget() +
		// "\t");
		// }
		// System.out.println();
		// }
	}

	@Override
	public ArrayList<Integer> updateAndGetRessources() {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		if (game.getPlayingPlayer() == 1) {
			calculateNewWissen();
			calculateNewBudget();
			calculateRessource();
			calculateIndicatorLebensquali();
			calculateIndicatorUmweltfreundlichkeit();
			calculateIndicatorWirtschaftskraft();
			calculatePopulation();
		}
		if (game.getPlayingPlayer() == 2) {
			// include influence of player 1
		
			calculateIndicatorUmweltfreundlichkeit(0);
		
			// own calculation
			calculateNewWissen();
			calculateNewBudget();
			calculateRessource();
			calculateIndicatorLebensquali();
			calculateIndicatorUmweltfreundlichkeit();
			calculateIndicatorWirtschaftskraft();
			calculatePopulation();
		}
		if (game.getPlayingPlayer() == 3) {
			// include influence of player 1
		
			calculateIndicatorUmweltfreundlichkeit(0);
	
			// include influence of player 2
	
			calculateIndicatorUmweltfreundlichkeit(1);
		
			// own calculation
			calculateNewWissen();
			calculateNewBudget();
			calculateRessource();
			calculateIndicatorLebensquali();
			calculateIndicatorUmweltfreundlichkeit();
			calculateIndicatorWirtschaftskraft();
			calculatePopulation();
		}
		if (game.getPlayingPlayer() == 4) {
			// include influence of player 1
	
			calculateIndicatorUmweltfreundlichkeit(0);
	
			// include influence of player 2
	
			calculateIndicatorUmweltfreundlichkeit(1);
			// include influence of player 3
			calculateIndicatorUmweltfreundlichkeit(2);
			// own calculation
			calculateNewWissen();
			calculateNewBudget();
			calculateRessource();
			calculateIndicatorLebensquali();
			calculateIndicatorUmweltfreundlichkeit();
			calculateIndicatorWirtschaftskraft();
			calculatePopulation();
		}

		arrayList = getRessource(game.getPlayingPlayer());

		return arrayList;
	}

	public void calculateNewBudget() {
		int sum = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getErtragBudget();
			}
		}
		player.setBudget(player.getBudget() + sum);

	}

	public void calculateNewBudget(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getErtragBudget();
				System.out.println(sum);
			}
		}
		player.setBudget(player.getBudget() + sum);
		System.out.println("New Budget: "+ player.getBudget());

	}

	public void calculateNewWissen() {
		int sum = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 7; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getErtragWissen();
			}
		}
		player.setKnowhow(player.getKnowhow() + sum);
	}

	public void calculateNewWissen(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 7; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getErtragWissen();
			}
		}
		player.setKnowhow(player.getKnowhow() + sum);
	}

	public void calculateIndicatorLebensquali() {
		int sum = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getLebenQuali();
			}
		}
		player.setLebensQuali(player.getInitialIndicatorLebensquali() + sum);

	}

	public void calculateIndicatorLebensquali(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getLebenQuali();
			}
		}
		
		player.setLebensQuali(player.getInitialIndicatorLebensquali() + sum);

	}

	public void calculateIndicatorWirtschaftskraft() {
		int sum = 0;
		int defizit;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getWirtschaftsKraft();
			}
		}
		if(player.getRize()<(player.getPopulation()/2)){
			defizit= ((player.getPopulation()/2)-player.getRize())/10;
			sum = sum + defizit;
		}
		if(player.getThe()<(player.getPopulation()/2)){
			defizit= ((player.getPopulation()/2)-player.getThe())/10;
			sum = sum + defizit;
		}
		if(player.getSugar()<(player.getPopulation()/2)){
			defizit= ((player.getPopulation()/2)-player.getSugar())/10;
			sum = sum + defizit;
		}
		if(player.getFish()<(player.getPopulation()/2)){
			defizit= ((player.getPopulation()/2)-player.getFish())/10;
			sum = sum + defizit;
		}
		if(player.getTextil()<(player.getPopulation()/3)){
			defizit= ((player.getPopulation()/3)-player.getTextil())/10;
			sum = sum + defizit;
		}
		if(player.getLeder()<(player.getPopulation()/3)){
			defizit= ((player.getPopulation()/3)-player.getLeder())/10;
			sum = sum + defizit;
		}if(player.getIt()<(player.getPopulation()/3)){
			defizit= ((player.getPopulation()/3)-player.getIt())/10;
			sum = sum + defizit;
		}
		
		player.setWirtschaftsKraft(player.getInitialIndicatorWirtschaft() + sum);
		

	}

	public void calculateIndicatorWirtschaftskraft(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getWirtschaftsKraft();
			}
		}
		player.setWirtschaftsKraft(player.getInitialIndicatorWirtschaft() + sum);

	}

	public void calculateIndicatorUmweltfreundlichkeit() {
		int sum = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getUmweltFreundlichkeit();
			}
		}
		player.setUmweltfreundlichketi(player.getInitialIndicatorUmwelt() + sum);

	}

	public void calculateIndicatorUmweltfreundlichkeit(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getUmweltFreundlichkeit();
			}
		}
		player.setUmweltfreundlichketi(player.getInitialIndicatorUmwelt() + sum);

	}

	public void calculatePopulation() {
		int sum = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getPopulation();
			}
		}
		player.setPopulation(player.getPopulation() + sum);
		;
	}

	public void calculatePopulation(int otherPlayer) {
		int sum = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getPopulation();
			}
		}
		player.setPopulation(player.getPopulation() + sum);
		;
	}

	public void calculateRessource() {
		int sumRICE = 0;
		int sumTEE = 0;
		int sumZUCKER = 0;
		int sumFISCH = 0;
		int sumLEDER = 0;
		int sumTEXTIL = 0;
		int sumIT = 0;
		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				if (cell.equals(FieldType.RICE)) {
					sumRICE = sumRICE + cell.getErtragRessourcen();
				}
				if (cell == FieldType.TEE) {
					sumTEE = sumTEE + cell.getErtragRessourcen();
				}
				if (cell == FieldType.ZUCKER) {
					sumZUCKER = sumZUCKER + cell.getErtragRessourcen();
				}
				if (cell == FieldType.FISCH) {
					sumFISCH = sumFISCH + cell.getErtragRessourcen();
				}
				if (cell == FieldType.LEDER) {
					sumLEDER = sumLEDER + cell.getErtragRessourcen();
				}
				if (cell == FieldType.TEXTIL) {
					sumTEXTIL = sumTEXTIL + cell.getErtragRessourcen();
				}
				if (cell == FieldType.IT) {
					sumIT = sumIT + cell.getErtragRessourcen();
				}
			}
		}
		player.setRize(player.getRize() + sumRICE);
		player.setThe(player.getThe() + sumTEE);
		player.setSugar(player.getSugar() + sumZUCKER);
		player.setFish(player.getFish() + sumFISCH);
		player.setLeder(player.getLeder() + sumLEDER);
		player.setTextil(player.getTextil() + sumTEXTIL);
		player.setIt(player.getIt() + sumIT);
	}

	public void calculateRessource(int otherPlayer) {
		int sumRICE = 0;
		int sumTEE = 0;
		int sumZUCKER = 0;
		int sumFISCH = 0;
		int sumLEDER = 0;
		int sumTEXTIL = 0;
		int sumIT = 0;
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				if (cell.equals(FieldType.RICE)) {
					sumRICE = sumRICE + cell.getErtragRessourcen();
				}
				if (cell == FieldType.TEE) {
					sumTEE = sumTEE + cell.getErtragRessourcen();
				}
				if (cell == FieldType.ZUCKER) {
					sumZUCKER = sumZUCKER + cell.getErtragRessourcen();
				}
				if (cell == FieldType.FISCH) {
					sumFISCH = sumFISCH + cell.getErtragRessourcen();
				}
				if (cell == FieldType.LEDER) {
					sumLEDER = sumLEDER + cell.getErtragRessourcen();
				}
				if (cell == FieldType.TEXTIL) {
					sumTEXTIL = sumTEXTIL + cell.getErtragRessourcen();
				}
				if (cell == FieldType.IT) {
					sumIT = sumIT + cell.getErtragRessourcen();
				}
			}
		}
		player.setRize(player.getRize() + sumRICE);
		player.setThe(player.getThe() + sumTEE);
		player.setSugar(player.getSugar() + sumZUCKER);
		player.setFish(player.getFish() + sumFISCH);
		player.setLeder(player.getLeder() + sumLEDER);
		player.setTextil(player.getTextil() + sumTEXTIL);
		player.setIt(player.getIt() + sumIT);
	}

	@Override
	public void createTradeContract(Trade trade) {
		Player partnerB = game.playerlist.get(trade.partnerBID - 1);
		partnerB.addTradeContract(trade);
	}
	
	@Override
	public void removeTradeContract(Trade trade) {
		Player partnerB = game.playerlist.get(trade.partnerBID - 1);
		partnerB.removeTradeContract();
	}

	@Override
	public void executeTradeContract(Trade tradeToExecute) {
		Player partnerA = game.playerlist.get(tradeToExecute.partnerAID - 1);
		Player partnerB = game.playerlist.get(tradeToExecute.partnerBID - 1);
		//IMPORT EXECUTED
		if(tradeToExecute.importGood.equals("Reis")){
			System.out.println("Old value reis partnerA: " + partnerA.getThe());
			System.out.println("Old value reis partnerB: " + partnerB.getThe());
			partnerA.setRize(partnerA.getRize()+tradeToExecute.importAmount);
			partnerB.setRize(partnerB.getRize()-tradeToExecute.importAmount);
			System.out.println("new value reis partnerA: " + partnerA.getThe());
			System.out.println("new value reis partnerB: " + partnerB.getThe());

		}
		if(tradeToExecute.importGood.equals("Tee")){
			System.out.println("Old value tea partnerA: " + partnerA.getThe());
			partnerA.setThe(partnerA.getThe()+tradeToExecute.importAmount);
			partnerB.setThe(partnerB.getThe()-tradeToExecute.importAmount);
			System.out.println("new value tea partnerA: " + partnerA.getThe());

		}
		if(tradeToExecute.importGood.equals("Zucker")){
			System.out.println("Old value reis partnerA: " + partnerA.getSugar());
			System.out.println("Old value reis partnerB: " + partnerB.getSugar());
			partnerA.setSugar(partnerA.getSugar()+tradeToExecute.importAmount);
			partnerB.setSugar(partnerB.getSugar()-tradeToExecute.importAmount);
			System.out.println("new value reis partnerA: " + partnerA.getSugar());
			System.out.println("new value reis partnerB: " + partnerB.getSugar());
		}
		if(tradeToExecute.importGood.equals("Fisch")){
			partnerA.setFish(partnerA.getFish()+tradeToExecute.importAmount);
			partnerB.setFish(partnerB.getFish()-tradeToExecute.importAmount);
		}
		if(tradeToExecute.importGood.equals("Textilien")){
			partnerA.setTextil(partnerA.getTextil()+tradeToExecute.importAmount);
			partnerB.setTextil(partnerB.getTextil()-tradeToExecute.importAmount);
		}
		if(tradeToExecute.importGood.equals("Leder")){
			partnerA.setLeder(partnerA.getLeder()+tradeToExecute.importAmount);
			partnerB.setLeder(partnerB.getLeder()-tradeToExecute.importAmount);
		}
		if(tradeToExecute.importGood.equals("IT")){
			partnerA.setIt(partnerA.getIt()+tradeToExecute.importAmount);
			partnerB.setIt(partnerB.getIt()-tradeToExecute.importAmount);
		}
		//EXPORT EXECUTED
		if(tradeToExecute.exportGood.equals("Reis")){
			partnerA.setRize(partnerA.getRize()-tradeToExecute.exportAmount);
			partnerB.setRize(partnerB.getRize()+tradeToExecute.exportAmount);
		}
		if(tradeToExecute.exportGood.equals("Tee")){
			partnerA.setThe(partnerA.getThe()-tradeToExecute.exportAmount);
			partnerB.setThe(partnerB.getThe()+tradeToExecute.exportAmount);
		}
		if(tradeToExecute.exportGood.equals("Zucker")){
			System.out.println("Old value Zucker Partner A: " +partnerA.getSugar());
			System.out.println("Old value Zucker partnerB: " + partnerB.getSugar());
			partnerA.setSugar(partnerA.getSugar()-tradeToExecute.exportAmount);
			partnerB.setSugar(partnerB.getSugar()+tradeToExecute.exportAmount);
			System.out.println("New value Zucker Partner A: " +partnerA.getSugar());
			System.out.println("New value Zucker Partner B: " +partnerB.getSugar());


		}
		if(tradeToExecute.exportGood.equals("Fisch")){
			partnerA.setFish(partnerA.getFish()-tradeToExecute.exportAmount);
			partnerB.setFish(partnerB.getFish()+tradeToExecute.exportAmount);
		}
		if(tradeToExecute.exportGood.equals("Textilien")){
			partnerA.setTextil(partnerA.getTextil()-tradeToExecute.exportAmount);
			partnerB.setTextil(partnerB.getTextil()+tradeToExecute.exportAmount);
		}
		if(tradeToExecute.exportGood.equals("Leder")){
			partnerA.setLeder(partnerA.getLeder()-tradeToExecute.exportAmount);
			partnerB.setLeder(partnerB.getLeder()+tradeToExecute.exportAmount);
		}
		if(tradeToExecute.exportGood.equals("IT")){
			partnerA.setIt(partnerA.getIt()-tradeToExecute.exportAmount);
			partnerB.setIt(partnerB.getIt()+tradeToExecute.exportAmount);
		}

	}
	
	public boolean checkTrade(Trade trade){
		Player tradePartnerA = game.playerlist.get(trade.partnerAID-1);
		Player tradePartnerB = game.playerlist.get(trade.partnerBID-1);
		int resourceA = tradePartnerA.getRessource(trade.exportGood);
		int resourceB = tradePartnerB.getRessource(trade.importGood);
		if((resourceA-trade.exportAmount)>=0 && (resourceB-trade.importAmount)>=0){
			return true;
		}else{
			return false;
		}
	}

}
