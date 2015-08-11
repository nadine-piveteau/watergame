package ch.watergame.client;

import java.util.ArrayList;

import ch.watergame.shared.Trade;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class JaTradeButtonClickHandler implements ClickHandler {
	Trade tradeToExecute;
	GreetingServiceAsync greetingService;

	public JaTradeButtonClickHandler(Trade tradeToExecute, GreetingServiceAsync greetingService) {
		super();
		this.tradeToExecute = tradeToExecute;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		greetingService.executeTradeContract(tradeToExecute, new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
				System.out.println("Executed");
				/*
				greetingService.checkTrade(tradeToExecute,  new AsyncCallback<Boolean>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						
					}
					
				});*/
				// refreshAndGetRessources: neue werte updaten und
				// zurückgeben methode hier aufrufen
				
				
			}
			
		});
		
		
		
	}

}
