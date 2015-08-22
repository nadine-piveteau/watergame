package ch.watergame.server;

import java.util.ArrayList;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ch.watergame.client.GreetingService;
import ch.watergame.client.WaterGame;
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
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	Game game = new Game();

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid.
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back
			// to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script
		// vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
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
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String startGame() {
		String message;
		game.addPlayer();
		int numberOfPlayer = game.getPlayerlistSize();
		int maxPlayer = game.getMaxPlayer();
		System.out.println("MAX PLAYER: "+maxPlayer);
		System.out.println("NUMBER OF PLAYER: "+numberOfPlayer);

		// create session and store userid
		HttpServletRequest request = this.getThreadLocalRequest();
		HttpSession session = request.getSession(true);
		session.setAttribute("UserID", numberOfPlayer);
		System.out.println("USERID :"+session.getAttribute("UserID"));
		if (numberOfPlayer < maxPlayer) {
			message = "Bitte warten";
			String nr = Integer.toString(numberOfPlayer);
			return nr.concat(message);
		} else {
			message = "Das Spiel kann beginnen!";
			System.out.println(message);
			String nr = Integer.toString(numberOfPlayer);
			return nr.concat(message);
		}
	}

	@Override
	public String getPlayerID() {

		HttpServletRequest request = this.getThreadLocalRequest();
		// dont create a new one -> false
		HttpSession session = request.getSession(true);
		if (session == null||session.getAttribute("UserID") == null)
			return null;

		// session and userid is available, looks like user is logged in.
		Integer userId = (Integer) session.getAttribute("UserID");
		//System.out.println("USERID: "+Integer.toString(userId));
		return userId.toString();
	}

	public String checkNrPlayer() {
		System.out.println("Player : " + getPlayerID());
		int nrPlayer = game.playerlist.size();
		String nrPlayerString = Integer.toString(nrPlayer);
		return nrPlayerString;
	}

	public TradeResult isMyTurn() {
		TradeResult trade = new TradeResult();
		//System.out.println("game.getPlayingPlayer = "+game.getPlayingPlayer());
		//System.out.println("this.getPalyerID = "+Integer.parseInt(this.getPlayerID()));

		//System.out.println("IS MY TURN TRADE.MY TURN = "+(game.getPlayingPlayer() == Integer.parseInt(this.getPlayerID())));
		if (game.getPlayingPlayer() == Integer.parseInt(this.getPlayerID())) {
			trade.myTurn = true;
			Player playingPlayer = game.playerlist
					.get(game.getPlayingPlayer() - 1);
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
		int indicator = game.playerlist.get(playerID - 1)
				.getPercentualIndicatorWirtschaft();

		return indicator;
	}

	public int getIndicatorLebensquali(int playerID) {
		int indicator = game.playerlist.get(playerID - 1)
				.getPercentualLebensquali();
		return indicator;
	}

	public int getIndicatorUmwelt(int playerID) {
		int indicator = game.playerlist.get(playerID - 1).getPercentualUmwelt();
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

	public void setKnowHow(int playerID, int amountknowhow) {
		game.playerlist.get(playerID - 1).setKnowhow(amountknowhow);
	}

	@Override
	public String checkBudgetAndKnowledge(int playerID, int rizePrice,
			int amountknowledge) {
		String message;
		int budget = getBudget(playerID) - rizePrice;
		int knowledgeNew = getKnowHow(playerID) - amountknowledge;
		if (budget >= 0 && knowledgeNew < 0) {
			message = "Deine Stadt verfügt über nicht genügend wissen.";
			return message;
		} else if (budget < 0) {
			message = "Du hast nicht genügend Budget.";
			return message;
		} else {
			return "OK";
		}
	}

	@Override
	public String refreshBudget(int playerID, int rizePrice) {
		System.out.println("OLD BUDGET: "+getBudget(playerID));
		int budget = getBudget(playerID) - rizePrice;
		setBudget(playerID, budget);
		String budgetString = Integer.toString(budget);
		System.out.println("NEW BUDGET: "+ budgetString);
		return budgetString;

	}
	
	@Override 
	public void setNaturkatastrophenSchutz(int playerID, boolean isProtected){
		Player player = game.playerlist.get(playerID - 1);
		player.setNaturkatastrophenSchutz(isProtected);
		
	}

	@Override
	public String refreshKnowledge(int playerID, int amountknowledge) {
		int knowledgeNew = getKnowHow(playerID) - amountknowledge;
		if (knowledgeNew >= 0) {
			setKnowHow(playerID, knowledgeNew);
			String knowledgeString = Integer.toString(knowledgeNew);
			return knowledgeString;
		} else {
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
		if (img.equals("transparent_graphic.png")) {
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
			}
		}
		player.setBudget(player.getBudget() + sum);

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
		int newLebensquali = player.getInitialIndicatorLebensquali() + sum;
		player.setLebensQuali(newLebensquali);
		double percentage = ((double) newLebensquali / player
				.getMaxLebensquali()) * 100;
		if (player.getMaxLebensquali() < newLebensquali) {
			player.setPercentualLebensquali(100);
		} else if (newLebensquali <= 0) {
			player.setPercentualLebensquali(0);

		} else {
			player.setPercentualLebensquali((int) (percentage));

		}
	}

	public void calculateIndicatorWirtschaftskraft() {
		double sum = 0;

		Player player = game.playerlist.get(game.getPlayingPlayer() - 1);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getWirtschaftsKraft();
			}
		}
		int countSiedlung = player.getGamefield().getNrofSiedlung();
		int countSiedlungCopy = countSiedlung;

		int[] countsLWRessources = { player.getGamefield().getNrofRice(),
				player.getGamefield().getNrofTee(),
				player.getGamefield().getNrofZucker(),
				player.getGamefield().getNrofFisch() };
		int[] countsINDRessources = { player.getGamefield().getNrofTextil(),
				player.getGamefield().getNrofLeder(),
				player.getGamefield().getNrofIT() };
		// 4 LW per house!!!!!!!!!
		for (int nr : countsLWRessources) {
			int expectedNrSiedlung = (nr / 4) + 1;
			countSiedlungCopy = countSiedlungCopy - expectedNrSiedlung;
		}
		// 2 Industries per house!!!!!!!!!!
		for (int nr : countsINDRessources) {
			int expectedNrSiedlung = (nr / 2) + 1;
			countSiedlungCopy = countSiedlungCopy - expectedNrSiedlung;
		}

		// if not enough houses, remove 10%
		if (countSiedlungCopy < 0) {
			sum = sum - (sum / 10);
		}
		// wenn grundbedarf nicht gedeckt, wird 5% der wirtschaftskraft
		// abgezogen.
		if (player.getRize() < (player.getPopulation() / player
				.getGrundbedarfProKopfLW())) {
			sum = sum - (sum / 20);
		}
		if (player.getThe() < (player.getPopulation() / player
				.getGrundbedarfProKopfLW())) {
			sum = sum - (sum / 20);
		}
		if (player.getSugar() < (player.getPopulation() / player
				.getGrundbedarfProKopfLW())) {
			sum = sum - (sum / 20);
		}
		if (player.getFish() < (player.getPopulation() / player
				.getGrundbedarfProKopfLW())) {
			sum = sum - (sum / 20);
		}
		if (player.getLeder() < (player.getPopulation() / player
				.getGrundbedarfProKopfIndustrie())) {
			sum = sum - (sum / 20);
		}
		if (player.getTextil() < (player.getPopulation() / player
				.getGrundbedarfProKopfIndustrie())) {
			sum = sum - (sum / 20);
		}
		if (player.getIt() < (player.getPopulation() / player
				.getGrundbedarfProKopfIndustrie())) {
			sum = sum - (sum / 20);
		}

		int newWirtschaftskraft = player.getInitialIndicatorWirtschaft()
				+ (int) sum;
		player.setWirtschaftsKraft(newWirtschaftskraft);
		double percentage = ((double) newWirtschaftskraft / player
				.getMaxWirtschaft()) * 100;
		if (player.getMaxWirtschaft() < newWirtschaftskraft) {
			player.setPercentualIndicatorWirtschaft(100);
		} else if (newWirtschaftskraft <= 0) {
			player.setPercentualIndicatorWirtschaft(0);

		} else {
			player.setPercentualIndicatorWirtschaft((int) (percentage));

		}
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
		int newUmwelt = player.getInitialIndicatorUmwelt() + sum;
		player.setUmweltfreundlichketi(newUmwelt);
		double percentage = ((double) newUmwelt / player.getMaxUmwelt()) * 100;
		if (player.getMaxUmwelt() < newUmwelt) {
			player.setPercentualUmwelt(100);
		} else if (newUmwelt <= 0) {
			player.setPercentualUmwelt(0);

		} else {

			player.setPercentualUmwelt((int) (percentage));
		}
	}

	public void calculateIndicatorUmweltfreundlichkeit(int otherPlayer) {
		int sum = 0;
		Player playingPlayer = game.playerlist.get(game.getPlayingPlayer() - 1);
		Player player = game.playerlist.get(otherPlayer);
		FieldType[][] field = player.getGamefield().getGameField();
		for (int row = 0; row < 11; row++) {
			for (int col = 0; col < 15; col++) {
				FieldType cell = field[row][col];
				sum = sum + cell.getUmweltFreundlichkeit();
			}
		}
		int newUmwelt = playingPlayer.getInitialIndicatorUmwelt() + sum;
		playingPlayer.setUmweltfreundlichketi(newUmwelt);
		double percentage = ((double) newUmwelt / player.getMaxUmwelt()) * 100;
		if (player.getMaxUmwelt() < newUmwelt) {
			player.setPercentualUmwelt(100);
		} else if (newUmwelt <= 0) {
			player.setPercentualUmwelt(0);

		} else {
			player.setPercentualUmwelt((int) (percentage));
		}

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
		player.setPopulation(player.getBasepopulation() + sum);
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
				if (cell == FieldType.ZUCKER
						&& player.getPercentualUmwelt() > 10) {
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
	public void removeALLTradeContract() {
		Player partnerB = game.playerlist.get(game.getPlayingPlayer() - 1);
		for (Trade trade : partnerB.getTradePartner()) {
			Player partnerA = game.playerlist.get((trade.partnerAID - 1));
			partnerB.removeTradeContract();
		}
	}

	@Override
	public void executeTradeContract(Trade tradeToExecute) {
		Player partnerA = game.playerlist.get(tradeToExecute.partnerAID - 1);
		Player partnerB = game.playerlist.get(tradeToExecute.partnerBID - 1);
		// IMPORT EXECUTED
		if (tradeToExecute.importGood.equals("Reis")) {
			partnerA.setRize(partnerA.getRize() + tradeToExecute.importAmount);
			partnerB.setRize(partnerB.getRize() - tradeToExecute.importAmount);

		}
		if (tradeToExecute.importGood.equals("Tee")) {
			partnerA.setThe(partnerA.getThe() + tradeToExecute.importAmount);
			partnerB.setThe(partnerB.getThe() - tradeToExecute.importAmount);

		}
		if (tradeToExecute.importGood.equals("Zucker")) {
			partnerA.setSugar(partnerA.getSugar() + tradeToExecute.importAmount);
			partnerB.setSugar(partnerB.getSugar() - tradeToExecute.importAmount);
		}
		if (tradeToExecute.importGood.equals("Fisch")) {
			partnerA.setFish(partnerA.getFish() + tradeToExecute.importAmount);
			partnerB.setFish(partnerB.getFish() - tradeToExecute.importAmount);
		}
		if (tradeToExecute.importGood.equals("Textilien")) {
			partnerA.setTextil(partnerA.getTextil()
					+ tradeToExecute.importAmount);
			partnerB.setTextil(partnerB.getTextil()
					- tradeToExecute.importAmount);
		}
		if (tradeToExecute.importGood.equals("Leder")) {
			partnerA.setLeder(partnerA.getLeder() + tradeToExecute.importAmount);
			partnerB.setLeder(partnerB.getLeder() - tradeToExecute.importAmount);
		}
		if (tradeToExecute.importGood.equals("IT")) {
			partnerA.setIt(partnerA.getIt() + tradeToExecute.importAmount);
			partnerB.setIt(partnerB.getIt() - tradeToExecute.importAmount);
		}
		// EXPORT EXECUTED
		if (tradeToExecute.exportGood.equals("Reis")) {
			partnerA.setRize(partnerA.getRize() - tradeToExecute.exportAmount);
			partnerB.setRize(partnerB.getRize() + tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("Tee")) {
			partnerA.setThe(partnerA.getThe() - tradeToExecute.exportAmount);
			partnerB.setThe(partnerB.getThe() + tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("Zucker")) {
			partnerA.setSugar(partnerA.getSugar() - tradeToExecute.exportAmount);
			partnerB.setSugar(partnerB.getSugar() + tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("Fisch")) {
			partnerA.setFish(partnerA.getFish() - tradeToExecute.exportAmount);
			partnerB.setFish(partnerB.getFish() + tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("Textilien")) {
			partnerA.setTextil(partnerA.getTextil()
					- tradeToExecute.exportAmount);
			partnerB.setTextil(partnerB.getTextil()
					+ tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("Leder")) {
			partnerA.setLeder(partnerA.getLeder() - tradeToExecute.exportAmount);
			partnerB.setLeder(partnerB.getLeder() + tradeToExecute.exportAmount);
		}
		if (tradeToExecute.exportGood.equals("IT")) {
			partnerA.setIt(partnerA.getIt() - tradeToExecute.exportAmount);
			partnerB.setIt(partnerB.getIt() + tradeToExecute.exportAmount);
		}

	}

	public boolean checkTrade(Trade trade) {
		Player tradePartnerA = game.playerlist.get(trade.partnerAID - 1);
		Player tradePartnerB = game.playerlist.get(trade.partnerBID - 1);
		int resourceA = tradePartnerA.getRessource(trade.exportGood);
		int resourceB = tradePartnerB.getRessource(trade.importGood);
		if ((resourceA - trade.exportAmount) >= 0
				&& (resourceB - trade.importAmount) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkTradePartnerA(Trade trade) {
		Player tradePartner = game.playerlist.get(trade.partnerAID - 1);
		int resourceA = tradePartner.getRessource(trade.exportGood);
		if ((resourceA - trade.exportAmount) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkTradePartnerB(Trade trade) {
		Player tradePartner = game.playerlist.get(trade.partnerBID - 1);
		int resourceA = tradePartner.getRessource(trade.exportGood);
		if ((resourceA - trade.importAmount) >= 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void executeMeasures(int[] measures) {
		Player player = game.playerlist.get(game.playingPlayer - 1);
		for (int measure : measures) {
			// Erhöht die Umweltfreundlichkeit um 10%
			System.out.println("Measure = "+ measure);
			if (measure == 1) {
				game.addLebensquali(0.15, player);
			}
			// erhöht die Lebensqualität um 10%
			else if (measure == 2) {
				game.addLebensquali(0.15, player);
			} else if (measure == 3) {
				game.setNaturkatastrophenSchutz(true, player);
			}
		}
	
	}

	@Override
	public int getRoundNR() {
		return game.getGameRound();
	}

	@Override
	public String executeEvent(int playerID) {
		Player player = game.playerlist.get(playerID - 1);
		System.out.println("PLAYER NATURKATASTROPHEN SCHUTZ: "+player.isNaturkatastrophenSchutz());
		Random rand = new Random();
		// Test
		//int randomNum = 2;
		// Min + (int)(Math.random() * ((Max - Min) + 1))
		int randomNum = 1 + (int)(Math.random() * ((30 - 1) + 1));
		// Dürre: Kein Ertrag in der Landwirtschaft
		// Lebensqualität nimmt ab
		if (player.isNaturkatastrophenSchutz() == false) {
			if (randomNum == 1) {
				game.lossLW(player);
				return "<h2>Dürre</h2><br><h3>Eine Dürre lässt den Pegel des Flusses sinken. Aufgrund der anhaltenden Trockenheit hast du in dieser Runde keinen Ertrag in der Landwirtschaft.</h3>";
			}
			// Überschwemmung, MonsunRegen
			// Dürre: Kein Ertrag,
			// Budget: Beschädigte Häuser
			else if (randomNum == 2) {
				game.halfLossLW(player);
				int lossBudget = game.deduceBudget(0.15, player);
				return "<h2>Dürre<h2><br><h3>Überschwemmungen durch heftige Monsunregen haben einen Teil der Ernte in deiner Stadt zerstört. Du erhältst in dieser Runde nur die Hälfte des Ertrages in der Landwirtschaft. Viele Häuser wurden beschädigt oder zerstört und müssen repariert werden. </h3>";

			}
			// Grundwasservergiftung
			// Keine: Erträge
			// Budget:
			else if (randomNum == 3) {
				if (player.getId() == 2 || player.getId() == 3) {
					game.lossLW(player);
					int lossBudget = game.deduceBudget(0.05, player);
					return "<h2>Grund- und Trinkwasservergiftung</h2><br><h3>Düngemittel und Abwässer haben das Grund- und Trinkwasser vergiftet. Deshalb erhältst du in dieser Runde keinen Ertrag in der Landwirtschaft. 5% des Budgets werden Benötigt, um das Wasser wieder zu säubern.</h3>"; 
				} else {
					return null;
				}
			}

			// Zyklone und dadurch bedingte Flutwellen, nur für Kalkuta
			else if (randomNum == 4) {
				if (player.getId() == 4) {
					int lossBudget = game.deduceBudget(0.3, player);
					int lossWirtschaft = game.deduceWirtschaftskraft(0.05,
							player);
					return "<h2>Flutwelle</h2><br><h3>Ein Erdbeben im Indischen Ozean hat eine Flutwelle ausgelöst, die deine Stadt getroffen hat. 30% des Budgets wird benötigt, um den enormen Schaden zu beheben. Auch die Wirtschaftskraft leidet an den Folgen dieses Ereignisses. </h3>";
				} else {
					return null;
				}
			}
			// Erdbeben
			else if (randomNum == 5) {
				if (player.getId() == 1) {
					int lossBudget = game.deduceBudget(0.3, player);
					int lossWirtschaft = game.deduceWirtschaftskraft(0.05,
							player);
					return "<h2>Erdbeben</h2><br><h3>Deine Stadt ist von einem heftigen Erdbeben im Himalaya-Gebirge  getroffen. 30% des Budgets wird benötigt, um den enormen Schaden zu beheben. Auch die Wirtschaftskraft leidet an den Folgen dieses Ereignisses.</h3>";
				} else {
					return null;
				}
			}
			// Erdrutsch
			else if (randomNum == 6) {
				if (player.getId() == 1) {
					int lossBudget = game.deduceBudget(0.3, player);
					int lossWirtschaft = game.deduceWirtschaftskraft(0.05,
							player);// Begründung Tourismus: Ihre
									// Wirtschaftskraft leidet unter den
									// fehlenden TOuristen.
					return "<h2>Erdrutsch</h2><br><h3>Heftige Monsunregenfälle haben Erdrutsche ausgelöst. Deine Stadt ist Betroffen. 30% des Budgets wird benötigt, um den enormen Schaden zu beheben. Auch die Wirtschaftskraft leidet an den Folgen dieses Ereignisses.</h3>";
				} else {
					return null;
				}
			} else if (randomNum == 7) {
				if (player.getId() == 2 || player.getId() == 3) {
					game.lossLW(player);
					return "<h2>Zu tiefer Grundwasserspiegel</h2><br><h3>Durch die extensive Bewässerung deiner Landwirtschaftsfelder ist der Grundwasserspiegel so tief gesunken, dass du in dieser Runde keinen Landwirtschaftsertrag hast. Das Grundwasser muss sich eine Runde lang erholen. </h3>";
				} else {
					return null;
				}
			} /*else if (randomNum == 8) {
				if (player.getId() == 1) {
					game.addBudget(0.2, player);
					return "finanzielleUnterstützung von Dehli";
				} else {
					return null;
				}
			}*/ else {
				return null;
			}
		} else {
			player.setNaturkatastrophenSchutz(false);
			if (randomNum == 1) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor Dürre<h2><br><h3>Du hast in der letzten Runde Massnahmen ergriffen, um dich vor Naturkatastrophen zu Schützen. Deine angelegten Wasserreserven haben dir erlaubt eine Dürre ohne Ernteverlust in der Landwirtschaft zu überstehen. </h3>";
			} else if (randomNum == 2) {
				player.setNaturkatastrophenSchutz(false);
				System.out.println(player.isNaturkatastrophenSchutz());
				return "<h2>Geschützt vor Überschwemmung</h2><br><h3>Du hast in der letzten Runde Massnahmen ergriffen, um dich vor Naturkatastrophen zu Schützen. Die Hochwasserschutzmassnahmen haben deine Stadt vor grossen Schäden bewahrt. </h3>";
			} else if (randomNum == 3) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor Grund- und Trinkwasservergiftung</h2><br><h3>Dank des ausgeklügelten Trinkwasserreinigungssystems und der Kläranlagen in deiner Stadt, ist das Grund- und Trinkwasser trotz Abwässer und Düngemittelkonsum nicht vergiftet worden. </h3>";
			} else if (randomNum == 4) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor Flutwelle</h2><br><h3>Die präventiven Naturkatastrophenschutzmassnahmen, die du in der letzten Runde ergriffen hast, haben dich vor einer Flutwelle geschützt. Ein Erdbeben im indischen Ozean war die Ursache. </h3>";
			}
			// Erdbeben
			else if (randomNum == 5) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor Erdbeben</h2><br><h3>Die präventiven Naturkatastrophenschutzmassnahmen, die du in der letzten Runde ergriffen hast, haben dich vor einem Erbeben im Himalaya-Gebirge geschützt.</h3>";
			}
			// Erdrutsch
			else if (randomNum == 6) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor Erdrutsch</h2><br><h3>Die präventiven Naturkatastrophenschutzmassnahmen, die du in der letzten Runde ergriffen hast, haben dich vor einem Erdrutsch geschützt, der durch schwere Monsunregenfällen ausgelöst wurde.</h3>";
			} else if (randomNum == 7) {
				player.setNaturkatastrophenSchutz(false);
				return "<h2>Geschützt vor zu tiefem Grundwasserspiegel</h2><br><h3>Du hast in der letzten Runde Massnahmen ergriffen, um dich vor Naturkatastrophen zu Schützen. Die nachhaltige Nutzung des Grundwassers hat verhindert, dass der Grundwasserspiegel zu tief sinkt. </h3>";
			} /*else if (randomNum == 8) {
				player.setNaturkatastrophenSchutz(false);
				return "Geschützt vor finanzielleUnterstützung von Dehli";
			} */else {
				return null;
			}
		}

	}

	@Override
	public int getCommonIndicator() {
		int indicator = (int) game.getCommonIndicator();
		return indicator;
	}
	@Override
	public ArrayList<Integer> getNrOfSpielwechsel(){
		ArrayList<Integer> result = new ArrayList<Integer>(2);
		result.add(game.getNrOfSpielwechsel());
		result.add((int)game.getCommonIndicator());
		return result;
	}

}
