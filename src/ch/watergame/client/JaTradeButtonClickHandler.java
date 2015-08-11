package ch.watergame.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ch.watergame.shared.Trade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class JaTradeButtonClickHandler implements ClickHandler {
	Trade tradeToExecute;
	GreetingServiceAsync greetingService;
	ConcludeTradeClickHandler okButton;

	public JaTradeButtonClickHandler(Trade tradeToExecute, GreetingServiceAsync greetingService, ConcludeTradeClickHandler okButton) {
		super();
		this.tradeToExecute = tradeToExecute;
		this.greetingService = greetingService;
		this.okButton = okButton;
		
	}

	@Override
	public void onClick(ClickEvent event) {
		okButton.tradeList.add(tradeToExecute);
		System.out.println("Trade has been added: " + tradeToExecute.toString());
		
		
		
	}

}
