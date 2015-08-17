package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class UmweltverschmutzungClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	ValidatorHandler validator;

	
	
	public UmweltverschmutzungClickHandler(WaterGame waterGame, GreetingServiceAsync greetingService ) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
		
	}


	@Override
	public void onClick(ClickEvent event) {
		
		// TODO Auto-generated method stub
		//checkbox nicht angewählt
		if(waterGame.umweltSchutzButton.getValue()==false){
			waterGame.activeUmweltschutz = false;
			System.out.println("Keine Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisUmweltverschmutzung*(-1),
					new AsyncCallback<String>() {

						@Override
						public void onFailure(
								Throwable caught) {
							System.out
									.println("Failed to refresh Budget");
						}

						@Override
						public void onSuccess(
								String result) {
							waterGame.budgetPanel
									.remove(waterGame.budgetValue);
							waterGame.budgetValue = new Label(
									result);
							waterGame.budgetPanel
									.add(waterGame.budgetValue);
						}

					});
			
		}
		//checkbox angewählt
		else{
			waterGame.activeUmweltschutz = true;
			System.out.println("Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisUmweltverschmutzung,
					new AsyncCallback<String>() {

						@Override
						public void onFailure(
								Throwable caught) {
							System.out
									.println("Failed to refresh Budget");
						}

						@Override
						public void onSuccess(
								String result) {
							waterGame.budgetPanel
									.remove(waterGame.budgetValue);
							waterGame.budgetValue = new Label(
									result);
							waterGame.budgetPanel
									.add(waterGame.budgetValue);
						}

					});
		}
	
	}

}
