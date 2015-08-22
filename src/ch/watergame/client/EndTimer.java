package ch.watergame.client;
import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;


public class EndTimer extends Timer {
	private GreetingServiceAsync greetingService;
	private WaterGame wgame;
	
	public EndTimer(GreetingServiceAsync greetingService, WaterGame wgame){
		this.greetingService = greetingService;
		this.wgame = wgame;
			
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		greetingService.getNrOfSpielwechsel(new AsyncCallback<ArrayList<Integer>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<Integer> result) {
				// TODO Auto-generated method stub
				if(result.get(0)>4&&result.get(1)<wgame.WINNINGINDIKATOR){
					System.out.println("Winningindikator"+ result.get(0));
					System.out.println("Winningindikator"+ result.get(1));

					RootPanel.get("WIN").setVisible(false);
					RootPanel.get("GAMEOVER").setVisible(true);
					RootPanel.get("sendButtonContainer").setVisible(false);
					RootPanel.get("instructionButtonContainer").setVisible(false);
					RootPanel.get("waitingBoxContainer").setVisible(false);
					RootPanel.get("tradeContainer").setVisible(false);
					RootPanel.get("gamefield").setVisible(false);
					RootPanel.get("validateButtonContainer").setVisible(false);
					RootPanel.get("NotYourTurn").setVisible(false);
					cancel();
				}else if(result.get(0)>=wgame.WINNINGINDIKATOR&&result.get(1)<60){
					RootPanel.get("WIN").setVisible(true);
					RootPanel.get("GAMEOVER").setVisible(false);
					RootPanel.get("sendButtonContainer").setVisible(false);
					RootPanel.get("instructionButtonContainer").setVisible(false);
					RootPanel.get("waitingBoxContainer").setVisible(false);
					RootPanel.get("tradeContainer").setVisible(false);
					RootPanel.get("gamefield").setVisible(false);
					RootPanel.get("validateButtonContainer").setVisible(false);
					RootPanel.get("NotYourTurn").setVisible(false);
				}
			}
		});
				
		
	
	}

}