package ch.watergame.client;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import ch.watergame.shared.GameField;
import ch.watergame.shared.Trade;
import ch.watergame.shared.TradeResult;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback) throws IllegalArgumentException;
	void startGame(AsyncCallback<String> asyncCallback);
	void checkNrPlayer(AsyncCallback<String> asyncCallback);
	void getPlayerID(AsyncCallback<String> callback);
	void isMyTurn(AsyncCallback<TradeResult> callback);
	void getRessource(int playerID, AsyncCallback<ArrayList<Integer>> callback);
	void setNextPlayer(AsyncCallback<Void> callback);
	void refreshBudget(int userID, int rizePrice, AsyncCallback<String> asyncCallback);
	void getGameField(int playerID, AsyncCallback<GameField> asyncCallback);
	void setFieldintoGameField(int playerID, int row, int col, String img, AsyncCallback<Void> callback);
	void updateAndGetRessources(AsyncCallback<ArrayList<Integer>> asyncCallback);
	void createTradeContract(Trade tradeContract1, AsyncCallback<Void> asyncCallback);
	void executeTradeContract(Trade tradeToExecute,AsyncCallback<Void> asyncCallback);
	void refreshKnowledge(int playerID, int amountknowledge,
			AsyncCallback<String> callback);
	void checkBudgetAndKnowledge(int playerID, int rizePrice,
			int amountknowledge, AsyncCallback<String> asyncCallback);
	void checkTrade(Trade tradeContract1, AsyncCallback<Boolean> asyncCallback);
	void removeTradeContract(Trade trade, AsyncCallback<Void> callback);
	

}
