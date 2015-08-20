package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class ReformenClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	
	public ReformenClickHandler(WaterGame waterGame,
			GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		System.out.println("waterGame.budgetValue.toString(): "+ waterGame.budgetValue);
		if((waterGame.budgetValueInt-waterGame.preisReformen)>0){

		if(waterGame.reformenButton.getValue()==false){
			waterGame.activeReformen = false;
			System.out.println("Keine Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisReformen*(-1),
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
			waterGame.activeReformen = true;
			System.out.println("Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisReformen,
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
		}else{
			waterGame.reformenButton.setValue(false);
			Window.alert("Du hast nicht genügend Ressourcen. ");
		}
	}

}
