package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class GridClickHandler implements ClickHandler {
	String imgString;
	int row;
	int col;
	BudgetTimer budgetTimer;
	WaterGame game;
	GreetingServiceAsync greetingService;
	int price;
	int priceRemove;

	// public GridClickHandler(GreetingServiceAsync greetingService, WaterGame
	// wgame, String img, int row, int col, int price, int priceRemove) {

	public GridClickHandler(GreetingServiceAsync greetingService,
			WaterGame wgame, int row, int col) {
		this.row = row;
		this.col = col;
		this.game = wgame;
		this.greetingService = greetingService;
		this.price = WaterGame.getSelectedPrice();
		this.priceRemove = WaterGame.getSelectedPriceRemove();
	}

	@Override
	public void onClick(ClickEvent event) {
		if (WaterGame.getSelectedImage().contains("bio")) {
			Window.alert("Dieses Feld ist hier nicht möglich.");
		} else {
			final Image insertImage = new Image();
			this.imgString = WaterGame.getSelectedImage();
			insertImage.setSize("40px", "40px");
			insertImage.setUrl(imgString);
			GridClickHandler insertedImageGridHandler = new GridClickHandler(
					greetingService, game, row, col);
			// rizefelder können durch andere Felder ersetzt werden oder zu
			// einem Upgrade werden
			if (this.imgString.equals("rizeField.jpeg")) {
				
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"rizeFieldbio.jpeg", greetingService, game, row, col);
				//FieldDoubleClickHandler doubleclick = new FieldDoubleClickHandler(row, col, "transparent_graphic.png", game, greetingService);
				//insertImage.addDoubleClickHandler(doubleclick);
				insertImage.addClickHandler(upgradehandler);
				// insertImage.addClickHandler(insertedImageGridHandler);
			}
			// Teeferlder können durch andere Felder ersetzt werden oder zu
			// einem Upgrade werden
			else if (this.imgString.equals("tee.jpeg")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"teebio.jpeg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			// Teeferlder können durch andere Felder ersetzt werden oder zu
			// einem Upgrade werden
			else if (this.imgString.equals("zucker.jpg")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"zuckerbio.jpg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			else if (this.imgString.equals("fisch.jpg")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"fischbio.jpg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			else if (this.imgString.equals("leder.JPG")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"lederbio.jpg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			else if (this.imgString.equals("textil.jpg")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"textilbio.jpg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			else if (this.imgString.equals("it.jpg")) {
				UpgradeClickHandler upgradehandler = new UpgradeClickHandler(
						"itbio.jog.jpg", greetingService, game, row, col);
				insertImage.addClickHandler(upgradehandler);
			}
			
			else {
				insertImage.addClickHandler(insertedImageGridHandler);
			}
			// game.budgetValue = new Label
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
									if (result.equals("OK")) {
										game.grid.setWidget(row, col, insertImage);

										greetingService.setFieldintoGameField(
												userID, row, col, imgString,
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
									}
										else if(result.equals("Deine Stadt verfügt über nicht genügend Wissen.")){
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
			// greetingService.refreshBudget(game.rizePrice);
			// PROBLEM: it shall not be possible to remove at any time. once the
			// player validates, the position is fix. the
			// RemoveClickHandler removehandler = new
			// RemoveClickHandler(greetingService, game, insertImage,
			// priceRemove);
			// insertImage.addDoubleClickHandler(removehandler);
		}
	}
}
