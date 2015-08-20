package ch.watergame.client;

import java.util.ArrayList;
import java.util.Timer;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class BudgetTimer extends Timer{
	private GreetingServiceAsync greetingService;
	private WaterGame wgame;

	public BudgetTimer(GreetingServiceAsync greetingService, WaterGame wgame) {
		super();
		this.greetingService = greetingService;
		this.wgame = wgame;
	}

	public void run(){
		greetingService.getRessource(wgame.playerID, new AsyncCallback<ArrayList<Integer>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("ressources not arrived");
			}

			@Override
			public void onSuccess(ArrayList<Integer> result) {
				int budgetInt = result.get(4);
				wgame.budgetValueInt = budgetInt;
				wgame.budgetValue = new Label (Integer.toString(budgetInt));
				
			}
			
		});
	}

	public void scheduleRepeating(int i) {
		// TODO Auto-generated method stub
		
	}
}
