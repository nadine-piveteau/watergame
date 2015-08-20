package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;

public class NKSchutzClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	
	public NKSchutzClickHandler(WaterGame waterGame,
			GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		if((waterGame.budgetValueInt-waterGame.preisReformen)>0){

		// TODO Auto-generated method stub
		if(waterGame.naturkatastropheButton.getValue()==false){
			waterGame.activeNaturkatastrophen = false;
			System.out.println("Keine Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisNaturkatastrophen*(-1),
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
			if((Integer.parseInt(waterGame.budgetValue.toString())-waterGame.preisNaturkatastrophen)>0){

			waterGame.activeNaturkatastrophen = true;
			System.out.println("Massnahmen");
			greetingService.refreshBudget(waterGame.getPlayerID(),
					waterGame.preisNaturkatastrophen,
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
			}else{
				Window.alert("Du hast nicht genügend Ressourcen.");
			}
		}
		}else{
			waterGame.naturkatastropheButton.setValue(false);
			Window.alert("Du hast nicht genügend Ressourcen.");
		}
	}

}
