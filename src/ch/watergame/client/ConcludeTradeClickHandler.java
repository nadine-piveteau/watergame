package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.RootPanel;

public class ConcludeTradeClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	
	
	
	public ConcludeTradeClickHandler(WaterGame waterGame, 	GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}



	@Override
	public void onClick(ClickEvent event) {
		RootPanel.get("gamefield").setVisible(true);
		RootPanel.get("validateButtonContainer").setVisible(true);
		RootPanel.get("tradeContainer").setVisible(false);
		RootPanel.get("tradeContainer").clear();
		waterGame.tradeBox.clear();
		
		
		
		
	}

}
