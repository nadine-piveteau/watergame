package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class UpgradeClickHandler implements ClickHandler {
	String upgradeImageString;
	int row;
	int col;
	BudgetTimer budgetTimer;
	WaterGame game;
	GreetingServiceAsync greetingService;
	int price;
	int priceRemove;

	UpgradeClickHandler(String upgradeName,
			GreetingServiceAsync greetingService, WaterGame wgame, int row,
			int col) {
		this.upgradeImageString = upgradeName;
		this.row = row;
		this.col = col;
		this.game = wgame;
		this.greetingService = greetingService;
		this.price = WaterGame.getSelectedPrice();
		this.priceRemove = WaterGame.getSelectedPriceRemove();
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		final Image insertImage = new Image();
		// this.upgradeImageString = WaterGame.getSelectedImage();
		System.out.println("UpgradeHandler: " + upgradeImageString);
		insertImage.setSize("40px", "40px");
		insertImage.setUrl(WaterGame.getSelectedImage());
		if (WaterGame.selectedImage.contains(upgradeImageString)) {
			GridClickHandler insertedImageGridHandler = new GridClickHandler(
					greetingService, game, row, col);
			insertImage.addClickHandler(insertedImageGridHandler);
			// erst nachdem überprüft wurde

		}
		// für nicht upgradeFelder
		else if (!WaterGame.selectedImage.contains("bio")) {
			// GridClickHandler insertedImageGridHandler = new
			// GridClickHandler(greetingService, game, row, col);
			UpgradeClickHandler insertedImageGridHandler = new UpgradeClickHandler(
					WaterGame.getSelectedImage(), greetingService, game, row,
					col);
			insertImage.addClickHandler(insertedImageGridHandler);

		}
		// für andere upgradeFelder
		else if (WaterGame.selectedImage.contains("bio")) {
			Window.alert("Dieses Upgrade ist auf diesem Feld nicht möglich. ");
			return;
		}

		// änderungen auf server übertragen.
		greetingService.getPlayerID(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				final int userID = Integer.parseInt(result);
				greetingService.checkBudgetAndKnowledge(userID,
						WaterGame.getSelectedPrice(),
						WaterGame.selectedKnowledge,
						new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub

							}

							@Override
							public void onSuccess(String result) {
								// TODO Auto-generated method stub
								if (result.equals("OK")) {
									game.grid.setWidget(row, col, insertImage);
									System.out.println(insertImage + " has been added to the gamefield.");
									greetingService.setFieldintoGameField(
											userID, row, col,
											upgradeImageString,
											new AsyncCallback<Void>() {

												@Override
												public void onFailure(
														Throwable caught) {
													// TODO Auto-generated
													// method stub

												}

												@Override
												public void onSuccess(
														Void result) {
													// TODO Auto-generated
													// method stub

												}
											});

									greetingService.refreshBudget(userID,
											WaterGame.selectedPrice,
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
													game.budgetPanel
															.remove(game.budgetValue);
													game.budgetValue = new Label(
															result);
													game.budgetPanel
															.add(game.budgetValue);
												}

											});

									greetingService.refreshKnowledge(userID,
											WaterGame.selectedKnowledge,

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
													if (result
															.equals("notPossible")) {
														Window.alert("Deine Stadt hat nicht genug Wissen");
													} else {
														game.ressourcePanel
																.remove(game.knowhowValue);
														game.knowhowValue = new Label(
																"Wissen: "
																		+ result);
														game.ressourcePanel
																.add(game.knowhowValue);
													}
												}

											});

								}
								else if(result.equals("Deine Stadt verfügt über nicht genügend wissen.")){
									Window.alert(result);
								}else if(result.equals("Du hast nicht genügend Budget.")){
									Window.alert(result);
								}

							}
						});
			}

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Exception");
			}
		});

	}

}
