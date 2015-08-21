package ch.watergame.client;

import ch.watergame.shared.TradeResult;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class StartTimer extends Timer {
	private GreetingServiceAsync greetingService;
	private WaterGame wgame;
	
	public StartTimer(GreetingServiceAsync greetingService, WaterGame wgame){
		this.greetingService = greetingService;
		this.wgame = wgame;
			
	}

	@Override
	public void run() {
		greetingService.checkNrPlayer(new AsyncCallback<String>() {
			public void onSuccess(String result) {
				if (result.equals("2")) {
					greetingService.isMyTurn(new AsyncCallback<TradeResult>() {

						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(TradeResult result) {
							System.out.println("IS MY TURN"+ result.myTurn);
							if (result.myTurn == true) {
								RootPanel.get("sendButtonContainer").clear();
								RootPanel.get("instructionButtonContainer").clear();
								RootPanel.get("waitingBoxContainer").clear();
								RootPanel.get("tradeContainer").setVisible(false);
								RootPanel.get("gamefield").setVisible(true);
								RootPanel.get("validateButtonContainer").setVisible(true);
								RootPanel.get("NotYourTurn").setVisible(false);

							} else {
								RootPanel.get("sendButtonContainer").clear();
								RootPanel.get("instructionButtonContainer").clear();
								RootPanel.get("waitingBoxContainer").clear();;
								RootPanel.get("gamefield").setVisible(false);
								RootPanel.get("validateButtonContainer").setVisible(false);
								RootPanel.get("NotYourTurn").setVisible(true);
								RootPanel.get("NotYourTurn").setVisible(false);
								System.out.println("Switchtimer has been added to player     "+wgame.getPlayerID());
								wgame.startSwitchTimer();
							}
						}

					});
					cancel();
				}

			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("False.");

			}
		});
	}

}
