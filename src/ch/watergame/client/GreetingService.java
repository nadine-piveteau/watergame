package ch.watergame.client;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import ch.watergame.shared.*;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.ui.HTML;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	String startGame();
	String checkNrPlayer();
	TradeResult isMyTurn();
	String getPlayerID();
	ArrayList<Integer> getRessource(int playerID);
	void setNextPlayer();
	String refreshBudget(int userID, int rizePrice);
	GameField getGameField(int playerID);
	void setFieldintoGameField(int playerID, int row, int col, String img);
	ArrayList<Integer> updateAndGetRessources();
	void createTradeContract(Trade tradeContract1);
	void executeTradeContract(Trade tradeToExecute);
	String refreshKnowledge(int playerID, int amountknowledge);
	String checkBudgetAndKnowledge(int playerID, int rizePrice,
			int amountknowledge);
	boolean checkTrade(Trade tradeContract1);
	void removeALLTradeContract();
	boolean checkTradePartnerA(Trade trade);
	boolean checkTradePartnerB(Trade trade);
	void executeMeasures(int[] measures);
	int getRoundNR();
	String executeEvent(int playerID);
	int getCommonIndicator();
	void setNaturkatastrophenSchutz(int playerID, boolean b);
	ArrayList<Integer> getNrOfSpielwechsel();
	int getGameID();
	void emptyTradeResult();


}
