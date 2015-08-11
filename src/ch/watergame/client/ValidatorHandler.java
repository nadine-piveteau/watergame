package ch.watergame.client;

import ch.watergame.shared.Trade;

import com.google.appengine.api.images.Image;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;

public class ValidatorHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	int counterRizeFields = 0;

	public ValidatorHandler(WaterGame waterGame,
			GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		/*
		 * for (int row = 0; row < 11; row++) { for (int col = 0; col < 15;
		 * col++) { Widget source = waterGame.grid.getWidget(row, col); String
		 * sourceText = source.toString(); if(sourceText.contains("rizeField")){
		 * //Add upgradeclickhandler to image UpgradeClickHandler upgradeHandler
		 * = new UpgradeClickHandler("rizeFieldbio.jpeg");
		 * source.addDomHandler(upgradeHandler, ClickEvent.getType()); } } }
		 */
		if (!waterGame.importAmountText1.getText().isEmpty()
				&& !waterGame.exportAmountText1.getText().isEmpty()) {
			int importAmount = Integer.parseInt(waterGame.importAmountText1
					.getText());
			int exportAmount = Integer.parseInt(waterGame.exportAmountText1
					.getText());
			int importGoodIndex = waterGame.importList1.getSelectedIndex();
			String importGood = waterGame.importList1
					.getItemText(importGoodIndex);
			int exportGoodIndex = waterGame.exportList1.getSelectedIndex();
			String exportGood = waterGame.exportList1
					.getItemText(exportGoodIndex);
			final Trade tradeContract1 = new Trade(waterGame.getPlayerID(), 1,
					importAmount, importGood, exportAmount, exportGood);
			greetingService.createTradeContract(tradeContract1,
										new AsyncCallback<Void>() {

											@Override
											public void onFailure(
													Throwable caught) {
												// TODO Auto-generated method
												// stub

											}

											@Override
											public void onSuccess(Void result) {
												// TODO Auto-generated method
												// stub
												System.out.println("Done nr 1");

											}
										});
							

						

		}
		System.out.println("Test 2");

		if (!waterGame.importAmountText2.getText().isEmpty()
				&& !waterGame.exportAmountText2.getText().isEmpty()) {
			int importAmount = Integer.parseInt(waterGame.importAmountText2
					.getText());
			int exportAmount = Integer.parseInt(waterGame.exportAmountText2
					.getText());
			int importGoodIndex = waterGame.importList2.getSelectedIndex();
			String importGood = waterGame.importList2
					.getItemText(importGoodIndex);
			int exportGoodIndex = waterGame.exportList2.getSelectedIndex();
			String exportGood = waterGame.exportList2
					.getItemText(exportGoodIndex);
			final Trade tradeContract2 = new Trade(waterGame.getPlayerID(), 2,
					importAmount, importGood, exportAmount, exportGood);
			
								greetingService.createTradeContract(tradeContract2,
										new AsyncCallback<Void>() {

											@Override
											public void onFailure(Throwable caught) {
												// TODO Auto-generated method stub

											}

											@Override
											public void onSuccess(Void result) {
												// TODO Auto-generated method stub
												System.out.println("Done nr 2");
											}
										});
						
			
		}
		System.out.println("Test 3");

		if (!waterGame.importAmountText3.getText().isEmpty()
				&& !waterGame.exportAmountText3.getText().isEmpty()) {
			int importAmount = Integer.parseInt(waterGame.importAmountText3
					.getText());
			int exportAmount = Integer.parseInt(waterGame.exportAmountText3
					.getText());
			int importGoodIndex = waterGame.importList3.getSelectedIndex();
			String importGood = waterGame.importList3
					.getItemText(importGoodIndex);
			int exportGoodIndex = waterGame.exportList3.getSelectedIndex();
			String exportGood = waterGame.exportList3
					.getItemText(exportGoodIndex);
			final Trade tradeContract3 = new Trade(waterGame.getPlayerID(), 3,
					importAmount, importGood, exportAmount, exportGood);
			
								greetingService.createTradeContract(tradeContract3,
										new AsyncCallback<Void>() {

											@Override
											public void onFailure(Throwable caught) {
												// TODO Auto-generated method stub

											}

											@Override
											public void onSuccess(Void result) {
												// TODO Auto-generated method stub

											}
										});
							
		}
		System.out.println("Test 4");

		if (!waterGame.importAmountText4.getText().isEmpty()
				&& !waterGame.exportAmountText4.getText().isEmpty()) {
			int importAmount = Integer.parseInt(waterGame.importAmountText4
					.getText());
			int exportAmount = Integer.parseInt(waterGame.exportAmountText4
					.getText());
			int importGoodIndex = waterGame.importList4.getSelectedIndex();
			String importGood = waterGame.importList4
					.getItemText(importGoodIndex);
			int exportGoodIndex = waterGame.exportList4.getSelectedIndex();
			String exportGood = waterGame.exportList4
					.getItemText(exportGoodIndex);
			final Trade tradeContract4 = new Trade(waterGame.getPlayerID(), 4,
					importAmount, importGood, exportAmount, exportGood);
			
								greetingService.createTradeContract(tradeContract4,
										new AsyncCallback<Void>() {

											@Override
											public void onFailure(Throwable caught) {
												// TODO Auto-generated method stub

											}

											@Override
											public void onSuccess(Void result) {
												// TODO Auto-generated method stub

											}
										});
							
			
		}
		System.out.println("Test 5");

		if (waterGame.importAmountText1.getText().isEmpty()
				&& waterGame.exportAmountText1.getText().isEmpty()
				|| waterGame.importAmountText2.getText().isEmpty()
				&& waterGame.exportAmountText2.getText().isEmpty()
				|| waterGame.importAmountText3.getText().isEmpty()
				&& waterGame.exportAmountText3.getText().isEmpty()
				|| waterGame.importAmountText4.getText().isEmpty()
				&& waterGame.exportAmountText4.getText().isEmpty()) {
			System.out.println("No new Trade contract.");

		} else {
			Window.alert("Trade not possible. Import and Export needed");
		}
		System.out.println("Test Test");
		greetingService.setNextPlayer(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Switch did not work.");
			}

			@Override
			public void onSuccess(Void result) {
				waterGame.startSwitchTimer();

			}
		});
	}

}
