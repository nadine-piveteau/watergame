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
		if ((waterGame.budgetValueInt - waterGame.preisNaturkatastrophen) > 0) {

			// TODO Auto-generated method stub
			if (waterGame.naturkatastropheButton.getValue() == false) {
				waterGame.activeNaturkatastrophen = true;
				final int playerID = waterGame.getPlayerID();
				System.out.println("Keine Massnahmen");
				greetingService.setNaturkatastrophenSchutz(playerID, false,
						new AsyncCallback<Void>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
								greetingService
										.refreshBudget(
												playerID,
												waterGame.preisNaturkatastrophen
														* (-1),
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
						});

			}
			// checkbox angewählt
			else {
				if ((waterGame.budgetValueInt - waterGame.preisNaturkatastrophen) > 0) {

					waterGame.activeNaturkatastrophen = false;
					final int playerID = waterGame.getPlayerID();
					System.out.println("Massnahmen");
					greetingService.setNaturkatastrophenSchutz(playerID, true,
							new AsyncCallback<Void>() {

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub
									greetingService.refreshBudget(playerID,
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
								}
							});
				} else {
					Window.alert("Du hast nicht genügend Ressourcen.");
				}
			}
		} else {
			waterGame.naturkatastropheButton.setValue(false);
			Window.alert("Du hast nicht genügend Ressourcen.");
		}
	}

}
