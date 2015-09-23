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
	int[] measures = {0,0,0};
	boolean checkTrade1 = false;
	boolean checkTrade2 = false;
	boolean checkTrade3 = false;
	boolean checkTrade4 = false;
	// Landwirtschaft
	int rize;
	int the;
	int fish;
	int sugar;
	int technology;
	int knowhow;

	// industrie
	int leder;
	int textil;
	int it;

	public ValidatorHandler(WaterGame waterGame,
			GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;

	}

	public void copyRessources() {
		this.rize = waterGame.rizeValueInteger;
		System.out.println("Reis copy: " + this.rize);
		this.the = waterGame.teaValueInteger;
		System.out.println("Tee copy: " + this.the);
		this.fish = waterGame.fishValueInteger;
		System.out.println("Fisch copy: " + this.fish);
		this.sugar = waterGame.sugarValueInteger;
		System.out.println("Sugar copy: " + this.sugar);
		this.leder = waterGame.lederValueInteger;
		System.out.println("Leder copy: " + this.leder);
		this.textil = waterGame.textilValueInteger;
		System.out.println("Textil copy: " + this.textil);
		this.it = waterGame.itValueInteger;
		System.out.println("IT copy: " + this.it);
		this.knowhow = waterGame.knowhowValueInteger;
		System.out.println("Knowledge copy: " + this.knowhow);
	}

	public Trade prepareTrade1() {
		int importAmount = Integer.parseInt(waterGame.importAmountText1
				.getText());
		int exportAmount = Integer.parseInt(waterGame.exportAmountText1
				.getText());
		int importGoodIndex = waterGame.importList1.getSelectedIndex();
		String importGood = waterGame.importList1.getItemText(importGoodIndex);
		int exportGoodIndex = waterGame.exportList1.getSelectedIndex();
		String exportGood = waterGame.exportList1.getItemText(exportGoodIndex);
		Trade trade = new Trade(waterGame.getPlayerID(), 0, importAmount,
				importGood, exportAmount, exportGood);
		return trade;
	}

	public Trade prepareTrade2() {
		int importAmount = Integer.parseInt(waterGame.importAmountText2
				.getText());
		int exportAmount = Integer.parseInt(waterGame.exportAmountText2
				.getText());
		int importGoodIndex = waterGame.importList2.getSelectedIndex();
		String importGood = waterGame.importList2.getItemText(importGoodIndex);
		int exportGoodIndex = waterGame.exportList2.getSelectedIndex();
		String exportGood = waterGame.exportList2.getItemText(exportGoodIndex);
		Trade trade = new Trade(waterGame.getPlayerID(), 1, importAmount,
				importGood, exportAmount, exportGood);
		return trade;
	}

	public Trade prepareTrade3() {
		int importAmount = Integer.parseInt(waterGame.importAmountText3
				.getText());
		int exportAmount = Integer.parseInt(waterGame.exportAmountText3
				.getText());
		int importGoodIndex = waterGame.importList3.getSelectedIndex();
		String importGood = waterGame.importList3.getItemText(importGoodIndex);
		int exportGoodIndex = waterGame.exportList3.getSelectedIndex();
		String exportGood = waterGame.exportList3.getItemText(exportGoodIndex);
		Trade trade = new Trade(waterGame.getPlayerID(), 2, importAmount,
				importGood, exportAmount, exportGood);
		return trade;
	}

	public Trade prepareTrade4() {
		int importAmount = Integer.parseInt(waterGame.importAmountText4
				.getText());
		int exportAmount = Integer.parseInt(waterGame.exportAmountText4
				.getText());
		int importGoodIndex = waterGame.importList4.getSelectedIndex();
		String importGood = waterGame.importList4.getItemText(importGoodIndex);
		int exportGoodIndex = waterGame.exportList4.getSelectedIndex();
		String exportGood = waterGame.exportList4.getItemText(exportGoodIndex);
		Trade trade = new Trade(waterGame.getPlayerID(), 3, importAmount,
				importGood, exportAmount, exportGood);
		return trade;
	}

	public boolean checkTrade1() {
		if (!waterGame.exportAmountText1.getText().equals("")) {
			int exportAmountRessource = Integer
					.parseInt(waterGame.exportAmountText1.getText());
			String exportAmountString = waterGame.exportList1
					.getValue(waterGame.exportList1.getSelectedIndex());

			if (exportAmountString.contains("Reis")) {
				if ((this.rize - exportAmountRessource) >= 0) {
					this.rize = this.rize - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Tee")) {
				System.out.println("Import: TEE");
				if ((this.the - exportAmountRessource) >= 0) {
					this.the = this.the - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Fisch")) {
				if ((this.fish - exportAmountRessource) >= 0) {
					this.fish = this.fish - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Zucker")) {
				if ((this.sugar - exportAmountRessource) >= 0) {
					this.sugar = this.sugar - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Leder")) {
				if ((this.leder - exportAmountRessource) >= 0) {
					this.leder = this.leder - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Textilien")) {
				if ((this.textil - exportAmountRessource) >= 0) {
					this.textil = this.textil - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("IT")) {
				System.out.println("Import: IT");
				if ((this.it - exportAmountRessource) >= 0) {
					this.it = this.it - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Wissen")) {
				System.out.println("Import: WISSEN");
				if ((this.knowhow - exportAmountRessource) >= 0) {
					this.knowhow = this.knowhow - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean checkTrade2() {
		if (!waterGame.exportAmountText2.getText().equals("")) {
			int exportAmountRessource = Integer
					.parseInt(waterGame.exportAmountText2.getText());
			String exportAmountString = waterGame.exportList2
					.getValue(waterGame.exportList2.getSelectedIndex());

			if (exportAmountString.contains("Reis")) {
				

				if ((this.rize - exportAmountRessource) >= 0) {
					this.rize = this.rize - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Tee")) {
				System.out.println("Import: TEE");
				if ((this.the - exportAmountRessource) >= 0) {
					this.the = this.the - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Fisch")) {
				System.out.println("Import: FISCH");
				if ((this.fish - exportAmountRessource) >= 0) {
					this.fish = this.fish - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Zucker")) {
				System.out.println("Import: ZUCKER");
				if ((this.sugar - exportAmountRessource) >= 0) {
					this.sugar = this.sugar - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Leder")) {
				System.out.println("Import: LEDER");
				if ((this.leder - exportAmountRessource) >= 0) {
					this.leder = this.leder - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Textilien")) {
				System.out.println("Import: TEXTILIEN");
				if ((this.textil - exportAmountRessource) >= 0) {
					this.textil = this.textil - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("IT")) {
				System.out.println("Import: IT");
				if ((this.it - exportAmountRessource) >= 0) {
					this.it = this.it - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else if (exportAmountString.contains("Wissen")) {
				System.out.println("Import: WISSEN");
				if ((this.knowhow - exportAmountRessource) >= 0) {
					this.knowhow = this.knowhow - exportAmountRessource;
					return true;
				} else {
					return false;
				}
			} else {
				return true;

			}
		} else {
			return true;
		}
	}

	public boolean checkTrade3() {
		if (!waterGame.exportAmountText3.getText().equals("")) {
			int exportAmountRessource = Integer
					.parseInt(waterGame.exportAmountText3.getText());
			String exportAmountString = waterGame.exportList3
					.getValue(waterGame.exportList3.getSelectedIndex());

			if (exportAmountString.contains("Reis")) {
				System.out.println("Export: REIS");
				if ((this.rize - exportAmountRessource) >= 0) {
					this.rize = this.rize - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Tee")) {
				System.out.println("Import: TEE");
				if ((this.the - exportAmountRessource) >= 0) {
					this.the = this.the - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Fisch")) {
				System.out.println("Import: FISCH");
				if ((this.fish - exportAmountRessource) >= 0) {
					this.fish = this.fish - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Zucker")) {
				System.out.println("Import: ZUCKER");
				if ((this.sugar - exportAmountRessource) >= 0) {
					this.sugar = this.sugar - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Leder")) {
				System.out.println("Import: LEDER");
				if ((this.leder - exportAmountRessource) >= 0) {
					this.leder = this.leder - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Textilien")) {
				System.out.println("Import: TEXTILIEN");
				if ((this.textil - exportAmountRessource) >= 0) {
					this.textil = this.textil - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("IT")) {
				System.out.println("Import: IT");
				if ((this.it - exportAmountRessource) >= 0) {
					this.it = this.it - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Wissen")) {
				System.out.println("Import: WISSEN");
				if ((this.knowhow - exportAmountRessource) >= 0) {
					this.knowhow = this.knowhow - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else {
				System.out.println("No export given.");
				return true;
			}
		} else {
			return true;
		}
	}

	public boolean checkTrade4() {
		if (!waterGame.exportAmountText4.getText().equals("")) {
			int exportAmountRessource = Integer
					.parseInt(waterGame.exportAmountText4.getText());
			String exportAmountString = waterGame.exportList4
					.getValue(waterGame.exportList4.getSelectedIndex());
			if (exportAmountString.contains("Reis")) {
				System.out.println("Export: REIS");
				if ((this.rize - exportAmountRessource) >= 0) {
					this.rize = this.rize - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Tee")) {
				System.out.println("Import: TEE");
				if ((this.the - exportAmountRessource) >= 0) {
					this.the = this.the - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Fisch")) {
				System.out.println("Import: FISCH");
				if ((this.fish - exportAmountRessource) >= 0) {
					this.fish = this.fish - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Zucker")) {
				System.out.println("Import: ZUCKER");
				if ((this.sugar - exportAmountRessource) >= 0) {
					this.sugar = this.sugar - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Leder")) {
				System.out.println("Import: LEDER");
				if ((this.leder - exportAmountRessource) >= 0) {
					this.leder = this.leder - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Textilien")) {
				System.out.println("Import: TEXTILIEN");
				if ((this.textil - exportAmountRessource) >= 0) {
					this.textil = this.textil - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("IT")) {
				System.out.println("Import: IT");
				if ((this.it - exportAmountRessource) >= 0) {
					this.it = this.it - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else if (exportAmountString.contains("Wissen")) {
				System.out.println("Import: WISSEN");
				if ((this.knowhow - exportAmountRessource) >= 0) {
					this.knowhow = this.knowhow - exportAmountRessource;
					System.out.println("Trade Check : True");
					return true;
				} else {
					System.out.println("Trade Check : False");
					return false;
				}
			} else {
				System.out.println("No export given.");
				return true;
			}
		} else {
			return true;
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		System.out.println("clicked");
		copyRessources();
		System.out.println("ressources added");
		checkTrade1 = checkTrade1();
		System.out.println("CheckTrade 1: " + checkTrade1);
		checkTrade2 = checkTrade2();
		System.out.println("CheckTrade 2: " + checkTrade2);
		checkTrade3 = checkTrade3();
		System.out.println("CheckTrade 3: " + checkTrade3);
		checkTrade4 = checkTrade4();
		System.out.println("CheckTrade 4: " + checkTrade4);

		// Wenn Felder für Handel mit Spieler 1 leer sind
		if (!waterGame.importAmountText1.getText().equals("")
				&& !waterGame.exportAmountText1.getText().equals("")
				&& checkTrade1) {
			Trade tradeContract1 = prepareTrade1();
			greetingService.createTradeContract(tradeContract1,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							System.out.println("Done nr 1");
						}
					});
		}
		System.out.println("Test 2");

		if (!waterGame.importAmountText2.getText().equals("")
				&& !waterGame.exportAmountText2.getText().equals("")
				&& checkTrade2) {
			Trade tradeContract2 = prepareTrade2();
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

		if (!waterGame.importAmountText3.getText().equals("")
				&& !waterGame.exportAmountText3.getText().equals("")
				&& checkTrade3) {
			final Trade tradeContract3 = prepareTrade3();
			greetingService.createTradeContract(tradeContract3,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							System.out.println("Done nr 3");
						}
					});
		}
		System.out.println("Test 4");

		if (!waterGame.importAmountText4.getText().equals("")
				&& !waterGame.exportAmountText4.getText().equals("")
				&& checkTrade4) {
			final Trade tradeContract4 = prepareTrade4();

			greetingService.createTradeContract(tradeContract4,
					new AsyncCallback<Void>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							System.out.println("Done nr 4");
						}
					});
		}

		if (waterGame.importAmountText1.getText().isEmpty()
				&& waterGame.exportAmountText1.getText().equals("")
				&& waterGame.importAmountText2.getText().equals("")
				&& waterGame.exportAmountText2.getText().equals("")
				&& waterGame.importAmountText3.getText().equals("")
				&& waterGame.exportAmountText3.getText().equals("")
				&& waterGame.importAmountText4.getText().equals("")
				&& waterGame.exportAmountText4.getText().equals("")) {
			System.out.println("No new Trade contract.");
			checkTrade1 = true;
			checkTrade2 = true;
			checkTrade3 = true;
			checkTrade4 = true;

		}/*
		 * else { Window.alert("Trade not possible. Import and Export needed");
		 * }
		 */

		System.out.println("Test 5");

		if (!(checkTrade1 && checkTrade2 && checkTrade3 && checkTrade4)) {
			Window.alert("Trade not possible. You don't have enough ressources.");
			copyRessources();
			return;
		}

		System.out.println("Entered: all checkTrade passed");

		if (waterGame.activeUmweltschutz) {
			measures[0] = 1;
		}
		waterGame.umweltSchutzButton.setValue(false);
		
		if (waterGame.activeReformen) {
			measures[0] = 2;
		}
		waterGame.reformenButton.setValue(false);

		if (waterGame.activeNaturkatastrophen) {
			measures[0] = 3;
		}
		waterGame.naturkatastropheButton.setValue(false);


		greetingService.executeMeasures(measures, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Switch did not work.");
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Measures executed");
				waterGame.activeNaturkatastrophen = false;
				waterGame.activeReformen = false;
				waterGame.activeUmweltschutz = false;
			}
		});

		greetingService.setNextPlayer(new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Switch did not work.");
			}

			@Override
			public void onSuccess(Void result) {
				System.out.println("Switchtimer started! for player"+ waterGame.getPlayerID());
				//SwitchTimer tx = new SwitchTimer(greetingService, waterGame);
				//tx.scheduleRepeating(500);
				waterGame.startSwitchTimer();
				//waterGame.startEndTimer();

			}
		});

	}

}
