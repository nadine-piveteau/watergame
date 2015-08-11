package ch.watergame.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TradeResult implements Serializable {
	public boolean myTurn;
	public Queue<Trade> tradeContractResult = new LinkedList<Trade>();
	
	
	
}
