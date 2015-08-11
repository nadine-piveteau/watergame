package ch.watergame.client;

import java.util.LinkedList;
import java.util.Queue;

import ch.watergame.shared.Trade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class ConcludeTradeClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	Queue<Trade> tradeList = new LinkedList<Trade>();
	
	
	public ConcludeTradeClickHandler(WaterGame waterGame, 	GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}



	@Override
	public void onClick(ClickEvent event) {
		
		for (Trade tradeToExecute:tradeList){
		greetingService.executeTradeContract(tradeToExecute, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				System.out.println("Executed");
				
			}
			
		});
		}
		greetingService.removeALLTradeContract(tradeList.peek(), new AsyncCallback<Void>(){
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				
			}
			
		}
				);
		RootPanel.get("gamefield").setVisible(true);
		RootPanel.get("validateButtonContainer").setVisible(true);
		RootPanel.get("tradeContainer").setVisible(false);
		RootPanel.get("tradeContainer").clear();
		waterGame.tradeBox.clear();
		
		
		
		
		
	}

}
