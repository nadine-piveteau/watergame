package ch.watergame.client;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RemoveClickHandler implements DoubleClickHandler {
	Image img;
	WaterGame game;
	GreetingServiceAsync greetingService;
	int price;

	public RemoveClickHandler(GreetingServiceAsync greetingService, WaterGame wgame, Image img, int price) {
		super();
		this.img = img;
		this.game = wgame;
		this.greetingService = greetingService;
		this.price = price;
	}

	@Override
	public void onDoubleClick(DoubleClickEvent event) {
		// TODO Auto-generated method stub
		game.grid.remove(img);
		greetingService.getPlayerID(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				int userID = Integer.parseInt(result);
				greetingService.refreshBudget(userID, price, new AsyncCallback<String>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Failed to refresh Budget");
					}

					@Override
					public void onSuccess(String result) {
						System.out.println(game.budgetValue);
						game.budgetPanel.remove(game.budgetValue);
						game.budgetValue = new Label(result);
						System.out.println(game.budgetValue);
						game.budgetPanel.add(game.budgetValue);
					}

				});
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
	}

}
