package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
																		game.ressourcePanel.clear();
																		//game.ressourcePanel.remove(game.knowhowValue);
																		game.knowhowValue = new HTML(result);
																			fillRessourcePanel();			
																		//game.ressourcePanel.add(game.knowhowValue);
																		
																	}
																}
																
															});
														}
														
													});
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

	}
	void fillRessourcePanel() {
		game.ressourcePanel.clear();
		game.ressourcePanel.add(game.ressourceTitle);

		Grid ressourcePanelGrid = new Grid(8, 2);
		//Label ressourcenTitel = new Label("Ressourcenstand");
		ressourcePanelGrid.setWidget(0, 0, game.rizeLabel);
		ressourcePanelGrid.setWidget(1, 0, game.fishLabel);
		ressourcePanelGrid.setWidget(2, 0, game.sugarLabel);
		ressourcePanelGrid.setWidget(3, 0, game.theLabel);
		ressourcePanelGrid.setWidget(4, 0, game.lederLabel);
		ressourcePanelGrid.setWidget(5, 0, game.textilLabel);
		ressourcePanelGrid.setWidget(6, 0, game.itLabel);
		ressourcePanelGrid.setWidget(7, 0, game.wissenLabel);
		ressourcePanelGrid.setWidget(0, 1, game.rizeValue);
		ressourcePanelGrid.setWidget(1, 1, game.fishValue);
		ressourcePanelGrid.setWidget(2, 1, game.sugarValue);
		ressourcePanelGrid.setWidget(3, 1, game.teaValue);
		ressourcePanelGrid.setWidget(4, 1, game.lederValue);
		ressourcePanelGrid.setWidget(5, 1, game.textilValue);
		ressourcePanelGrid.setWidget(6, 1, game.itValue);
		ressourcePanelGrid.setWidget(7, 1, game.knowhowValue);
		//waterGame.ressourcePanel.add(ressourcenTitel);
		game.ressourcePanel.add(ressourcePanelGrid);
		game.ressourcePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);;
	}

}
