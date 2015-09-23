package ch.watergame.client;

import java.util.ArrayList;

import ch.watergame.shared.GameField;
import ch.watergame.shared.GameField.FieldType;

import com.gargoylesoftware.htmlunit.javascript.host.Popup;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MyStartButtonHandler implements ClickHandler {
	private WaterGame waterGame;
	private GreetingServiceAsync greetingService;
	private Grid gridSiedlung = new Grid(1, 3);

	public MyStartButtonHandler(GreetingServiceAsync greetingService,
			WaterGame waterGame) {
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		RootPanel.get("sendButtonContainer").setVisible(false);
		RootPanel.get("instructionButtonContainer").setVisible(false);
		RootPanel.get("gamefield").setVisible(true);
		// RootPanel.get("gamefield").setStyleName("blocking");
		greetingService.startGame(new AsyncCallback<String>() {
			public void onFailure(Throwable t) {
				t.printStackTrace();
				Window.alert("Connection problem1" + t.getMessage());
			}

			public void onSuccess(String result) {
				waterGame.t = new StartTimer(greetingService, waterGame);
				System.out.println("Start Timer added to player"+ waterGame.getPlayerID());
				waterGame.t.scheduleRepeating(500);

				waterGame.setplayerID(Integer.parseInt(result.substring(0, 1)));
				waterGame.gameID = Integer.parseInt(result.substring(1, 2));
				System.out.println("RESULT AFTER STARTGAME:" + result);
				waterGame.waitingBox = new PopupPanel();
				HTML waitingBoxText = new HTML(result.substring(2));
				waitingBoxText.setStyleName("h1");
				waterGame.waitingBox.add(waitingBoxText);
				waterGame.waitingBox.setGlassEnabled(true);
				// waitingBox.setPopupPosition(Window.getClientWidth()/2,
				// Window.getClientHeight()/2);
				waterGame.waitingBox.center();
				waterGame.waitingBox.show();

				// RootPanel.get("gamefield").add(waitingBox);
				HTML name = null;
				System.out.println("STRING RETURNED " + result.substring(0, 1));
				if (Integer.parseInt(result.substring(0, 1)) == 0) {
					name = new HTML("Chamoli     ");
				} else if (Integer.parseInt(result.substring(0, 1)) == 1) {
					name = new HTML("Kanpur     ");
				} else if (Integer.parseInt(result.substring(0, 1)) == 2) {
					name = new HTML("Varanasi     ");
				} else if (Integer.parseInt(result.substring(0, 1)) == 3) {
					name = new HTML("Kalkutta     ");
				}
				name.setStyleName("nameCity");
				name.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				waterGame.roundPanel.clear();
				waterGame.roundCounter = new HTML("Spiel " + waterGame.gameID
						+ ", 1. Runde");
				waterGame.roundCounter.setStyleName("roundCounter");
				waterGame.roundPanel.add(waterGame.roundCounter);
				waterGame.roundPanel.setWidth("100%");
				waterGame.roundPanel
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				// roundPanel.setHeight("70px");
				// roundPanel.setWidth("350px");
				// fieldsAndRoundCounter.add(roundPanel);
				waterGame.nameAndRoundPanel.add(name);
				waterGame.nameAndRoundPanel.add(waterGame.roundPanel);
				waterGame.nameAndRoundPanel.setWidth("100%");
				waterGame.nameAndRoundPanel
						.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				System.out.println("GET GAMFIELD: " + waterGame.getPlayerID());
				
				greetingService.getGameField(waterGame.getPlayerID(),
						new AsyncCallback<GameField>() {

							@Override
							public void onFailure(Throwable t) {
								t.printStackTrace();
								System.out.println("Gamefield not created");
								Window.alert("Connection Problem 2 " + t.getMessage());
							}

							@Override
							public void onSuccess(GameField result) {
								for (int row = 0; row < 15; row++) {
									for (int col = 0; col < 15; col++) {
										GridClickHandler gridclickhandler = new GridClickHandler(
												greetingService, waterGame,
												row, col);
										if (result.getGameField()[row][col] == FieldType.RICE) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"rizeFieldbio.jpeg",
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"rizeField.jpeg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.TEE) {
											System.out
													.println("Upgrade for Rizefield now possible");
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"teebio.jpeg",
													greetingService, waterGame,
													row, col);
											Image image = new Image("tee.jpeg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.ZUCKER) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"zuckerbio.jpg",
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"zucker.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.FISCH) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"fischbio.jpg",
													greetingService, waterGame,
													row, col);
											Image image = new Image("fisch.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.TEXTIL) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"textilbio.jpg",
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"textil.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.LEDER) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"lederbio.jpg",
													greetingService, waterGame,
													row, col);
											Image image = new Image("leder.JPG");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.IT) {
											UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler(
													"itbio.jog.jpg",
													greetingService, waterGame,
													row, col);
											Image image = new Image("it.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(upgradeclickhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.SIEDLUNG) {
											GridClickHandler gridhandler = new GridClickHandler(
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"siedlung.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(gridhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.UNTERSTUFE) {
											GridClickHandler gridhandler = new GridClickHandler(
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"unterstufe.jpeg");
											image.setSize("40px", "40px");
											image.addClickHandler(gridhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.OBERSTUFE) {
											GridClickHandler gridhandler = new GridClickHandler(
													greetingService, waterGame,
													row, col);
											Image image = new Image(
													"oberstufe.jpeg");
											image.setSize("40px", "40px");
											image.addClickHandler(gridhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.UNI) {
											GridClickHandler gridhandler = new GridClickHandler(
													greetingService, waterGame,
													row, col);
											Image image = new Image("uni.jpg");
											image.setSize("40px", "40px");
											image.addClickHandler(gridhandler);
											waterGame.grid.setWidget(row, col,
													image);
										}
										// nicht veränderbar.
										if (result.getGameField()[row][col] == FieldType.TEMPEL) {
											Image image = new Image(
													"tempel.jpg");
											image.setSize("40px", "40px");
											waterGame.grid.setWidget(row, col,
													image);
										}
										if (result.getGameField()[row][col] == FieldType.NATIONALPARK) {
											Image image = new Image(
													"np.jpeg");
											image.setSize("40px", "40px");
											waterGame.grid.setWidget(row, col,
													image);
										}
									}
								}
								greetingService.getRessource(
										waterGame.getPlayerID(),
										new AsyncCallback<ArrayList<Integer>>() {

											@Override
											public void onFailure(
													Throwable caught) {
												System.out
														.println("ressources not arrived");
											}

											@Override
											public void onSuccess(
													ArrayList<Integer> result) {
												fillMeasuresPanel();
												// Store the values of the
												// ArrayList into integer
												int wirtschaftsIndicatorInt = result
														.get(0);
												System.out
														.println("ClientSide Wirtschafts indikator: "
																+ wirtschaftsIndicatorInt);

												int lebensqualiIndicatorInt = result
														.get(1);
												int umweltIndicatorInt = result
														.get(2);
												int populationInt = result
														.get(3);
												int budgetInt = result.get(4);

												// landwirtschaft
												int rizeValueInt = result
														.get(5);
												int rizeNeededInt = populationInt
														/ waterGame.grundbedarfProKopfLW;// Grundbedarf
												int theValueInt = result.get(6);
												int theNeededInt = populationInt
														/ waterGame.grundbedarfProKopfLW;
												int sugarValueInt = result
														.get(7);
												int sugarNeededInt = populationInt
														/ waterGame.grundbedarfProKopfLW;// Grundbedarf
												int fishValueInt = result
														.get(8);
												int fishNeededInt = populationInt
														/ waterGame.grundbedarfProKopfLW;// Grundbedarf
												// industrie
												int lederValueInt = result
														.get(9);
												int lederNeededInt = populationInt
														/ waterGame.grundbedarfProKopfIndustrie;
												int textilValueInt = result
														.get(10);
												int textilNeededInt = populationInt
														/ waterGame.grundbedarfProKopfIndustrie;
												int itValueInt = result.get(11);
												int itNeededInt = populationInt
														/ waterGame.grundbedarfProKopfIndustrie;
												int knowHow = result.get(12);
												waterGame.rizeValueInteger = rizeValueInt;
												waterGame.teaValueInteger = theValueInt;
												waterGame.fishValueInteger = fishValueInt;
												waterGame.sugarValueInteger = sugarValueInt;
												waterGame.lederValueInteger = lederValueInt;
												waterGame.textilValueInteger = lederValueInt;
												waterGame.itValueInteger = itValueInt;
												waterGame.knowhowValueInteger = knowHow;

												// add the information of the
												// Arraylist to the
												// corresponding panel
												waterGame.wirtschaftIndicatorValue = Integer
														.toString(wirtschaftsIndicatorInt);
												waterGame.lebensqualiIndicatorValue = Integer
														.toString(lebensqualiIndicatorInt);
												waterGame.umweltIndicatorValue = Integer
														.toString(umweltIndicatorInt);
												// waterGame.rizeValue = new
												// HTML("Reis: \t" +
												// Integer.toString(rizeValueInt)
												// + "/" +
												// Integer.toString(rizeNeededInt));
												// waterGame.fishValue = new
												// HTML("Fisch: \t" +
												// Integer.toString(fishValueInt)
												// + "/" +
												// Integer.toString(fishNeededInt));
												// waterGame.sugarValue = new
												// HTML("Zucker: \t" +
												// Integer.toString(sugarValueInt)
												// + "/" +
												// Integer.toString(sugarNeededInt));
												// waterGame.teaValue = new
												// HTML("Tee: \t" +
												// Integer.toString(theValueInt)
												// + "/" +
												// Integer.toString(theNeededInt));
												// waterGame.lederValue = new
												// HTML("Leder: \t" +
												// Integer.toString(lederValueInt)
												// + "/" +
												// Integer.toString(lederNeededInt));
												// waterGame.textilValue = new
												// HTML("Textilien: \t" +
												// Integer.toString(textilValueInt)
												// + "/"
												// +
												// Integer.toString(textilNeededInt));
												// waterGame.itValue = new
												// HTML("IT: \t" +
												// Integer.toString(itValueInt)
												// + "/" +
												// Integer.toString(itNeededInt));
												// waterGame.knowhowValue = new
												// HTML("Wissen: \t" +
												// Integer.toString(knowHow));
												waterGame.rizeValue = new HTML(
														Integer.toString(rizeValueInt)
																+ "/"
																+ Integer
																		.toString(rizeNeededInt));
												waterGame.fishValue = new HTML(
														Integer.toString(fishValueInt)
																+ "/"
																+ Integer
																		.toString(fishNeededInt));
												waterGame.sugarValue = new HTML(
														Integer.toString(sugarValueInt)
																+ "/"
																+ Integer
																		.toString(sugarNeededInt));
												waterGame.teaValue = new HTML(
														Integer.toString(theValueInt)
																+ "/"
																+ Integer
																		.toString(theNeededInt));
												waterGame.lederValue = new HTML(
														Integer.toString(lederValueInt)
																+ "/"
																+ Integer
																		.toString(lederNeededInt));
												waterGame.textilValue = new HTML(
														Integer.toString(textilValueInt)
																+ "/"
																+ Integer
																		.toString(textilNeededInt));
												waterGame.itValue = new HTML(
														Integer.toString(itValueInt)
																+ "/"
																+ Integer
																		.toString(itNeededInt));
												waterGame.knowhowValue = new HTML(
														Integer.toString(knowHow));

												System.out.println("Ladebalken: "
														+ Integer
																.toString(wirtschaftsIndicatorInt));
												waterGame.populationValue = new Label(
														Integer.toString(populationInt));
												HTML wirtschaftsHTML = new HTML(
														"<progress value=\""
																+ Integer
																		.toString(wirtschaftsIndicatorInt)
																+ "\" max=\"100\">"
																+ Integer
																		.toString(wirtschaftsIndicatorInt)
																+ "</progress>");
												Label percentageWirtschaft = new Label(
														Integer.toString(wirtschaftsIndicatorInt));
												percentageWirtschaft
														.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
												System.out.println("Integer.toString(wirtschaftsIndicatorInt): "
														+ Integer
																.toString(wirtschaftsIndicatorInt));
												waterGame.wirtschaftsKraftPanel
														.add(wirtschaftsHTML);
												waterGame.wirtschaftsKraftPanel
														.add(percentageWirtschaft);
												waterGame.wirtschaftsKraftPanel
														.setCellHorizontalAlignment(
																wirtschaftsHTML,
																HasHorizontalAlignment.ALIGN_CENTER);
												HTML lebensQualiHTML = new HTML(
														"<progress value=\""
																+ Integer
																		.toString(lebensqualiIndicatorInt)
																+ "\" max=\"100\">"
																+ Integer
																		.toString(lebensqualiIndicatorInt)
																+ "</progress>");
												Label percentageLebensquali = new Label(
														Integer.toString(lebensqualiIndicatorInt));
												percentageLebensquali
														.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
												waterGame.lebensQualitaetPanel
														.add(lebensQualiHTML);
												waterGame.lebensQualitaetPanel
														.add(percentageLebensquali);
												waterGame.lebensQualitaetPanel
														.setCellHorizontalAlignment(
																lebensQualiHTML,
																HasHorizontalAlignment.ALIGN_CENTER);
												HTML umweltHTML = new HTML(
														"<progress value=\""
																+ Integer
																		.toString(umweltIndicatorInt)
																+ "\" max=\"100\">"
																+ Integer
																		.toString(umweltIndicatorInt)
																+ "</progress>");
												Label percentageUmwelt = new Label(
														Integer.toString(umweltIndicatorInt));
												percentageUmwelt
														.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
												waterGame.umweltFreundlichkeitPanel
														.add(umweltHTML);
												waterGame.umweltFreundlichkeitPanel
														.add(percentageUmwelt);
												waterGame.umweltFreundlichkeitPanel
														.setCellHorizontalAlignment(
																umweltHTML,
																HasHorizontalAlignment.ALIGN_CENTER);

												waterGame.budgetValue = new Label(
														Integer.toString(budgetInt));
												waterGame.budgetValueInt = budgetInt;
												fillRessourcePanel();
/*		
												waterGame.ressourcePanel
														.setSpacing(0);
												waterGame.ressourcePanel
														.add(waterGame.rizeValue);
												waterGame.ressourcePanel
														.add(waterGame.fishValue);
												waterGame.ressourcePanel
														.add(waterGame.sugarValue);
												waterGame.ressourcePanel
														.add(waterGame.teaValue);
												waterGame.ressourcePanel
														.add(waterGame.lederValue);
												waterGame.ressourcePanel
														.add(waterGame.textilValue);
												waterGame.ressourcePanel
														.add(waterGame.itValue);
												waterGame.ressourcePanel
														.add(waterGame.knowhowValue);
												waterGame.ressourcePanel
														.setCellHorizontalAlignment(
																waterGame.budgetValue,
																HasHorizontalAlignment.ALIGN_LEFT);
												*/
												Grid tradeGrid = new Grid(9, 2);
												waterGame.tradePanel
														.add(new Label("Handel"));
												HorizontalPanel smallTradeFieldPanel1a = new HorizontalPanel();
												HorizontalPanel smallTradeFieldPanel1b = new HorizontalPanel();

												HorizontalPanel smallTradeFieldPanel2a = new HorizontalPanel();
												HorizontalPanel smallTradeFieldPanel2b = new HorizontalPanel();

												HorizontalPanel smallTradeFieldPanel3a = new HorizontalPanel();
												HorizontalPanel smallTradeFieldPanel3b = new HorizontalPanel();

												HorizontalPanel smallTradeFieldPanel4a = new HorizontalPanel();
												HorizontalPanel smallTradeFieldPanel4b = new HorizontalPanel();

												waterGame.importAmountText1 = new TextBox();
												waterGame.importAmountText1
														.setSize("65px", "15px");
												waterGame.importList1 = new ListBox();
												setListBox(waterGame.importList1);
												waterGame.exportAmountText1 = new TextBox();
												waterGame.exportAmountText1
														.setSize("65px", "15px");
												waterGame.exportList1 = new ListBox();
												setListBox(waterGame.exportList1);
												/*
												 * smallTradeFieldPanel1.add(
												 * waterGame.importAmountText1);
												 * smallTradeFieldPanel1
												 * .add(waterGame.importList1);
												 * smallTradeFieldPanel1
												 * .add(waterGame
												 * .exportAmountText1);
												 * smallTradeFieldPanel1
												 * .add(waterGame.exportList1);
												 */
												smallTradeFieldPanel1a
														.add(waterGame.importAmountText1);
												smallTradeFieldPanel1a
														.add(waterGame.importList1);
												smallTradeFieldPanel1b
														.add(waterGame.exportAmountText1);
												smallTradeFieldPanel1b
														.add(waterGame.exportList1);
												waterGame.importAmountText2 = new TextBox();
												waterGame.importAmountText2
														.setSize("65px", "15px");
												waterGame.importList2 = new ListBox();
												setListBox(waterGame.importList2);
												waterGame.exportAmountText2 = new TextBox();
												waterGame.exportAmountText2
														.setSize("65px", "15px");
												waterGame.exportList2 = new ListBox();
												setListBox(waterGame.exportList2);
												smallTradeFieldPanel2a
														.add(waterGame.importAmountText2);
												smallTradeFieldPanel2a
														.add(waterGame.importList2);
												smallTradeFieldPanel2b
														.add(waterGame.exportAmountText2);
												smallTradeFieldPanel2b
														.add(waterGame.exportList2);
												waterGame.importAmountText3 = new TextBox();
												waterGame.importAmountText3
														.setSize("65px", "15px");
												waterGame.importList3 = new ListBox();
												setListBox(waterGame.importList3);
												waterGame.exportAmountText3 = new TextBox();
												waterGame.exportAmountText3
														.setSize("65px", "15px");
												waterGame.exportList3 = new ListBox();
												setListBox(waterGame.exportList3);
												smallTradeFieldPanel3a
														.add(waterGame.importAmountText3);
												smallTradeFieldPanel3a
														.add(waterGame.importList3);
												smallTradeFieldPanel3b
														.add(waterGame.exportAmountText3);
												smallTradeFieldPanel3b
														.add(waterGame.exportList3);
												waterGame.importAmountText4 = new TextBox();
												waterGame.importAmountText4
														.setSize("65px", "15px");
												waterGame.importList4 = new ListBox();
												setListBox(waterGame.importList4);
												waterGame.exportAmountText4 = new TextBox();
												waterGame.exportAmountText4
														.setSize("65px", "15px");
												waterGame.exportList4 = new ListBox();
												setListBox(waterGame.exportList4);
												smallTradeFieldPanel4a
														.add(waterGame.importAmountText4);
												smallTradeFieldPanel4a
														.add(waterGame.importList4);
												smallTradeFieldPanel4b
														.add(waterGame.exportAmountText4);
												smallTradeFieldPanel4b
														.add(waterGame.exportList4);
												if (waterGame.getPlayerID() == 0) {
													tradeGrid
															.setWidget(
																	0,
																	0,
																	waterGame.tradePlayer2);
													tradeGrid.setWidget(1, 0,
															new HTML("Import"));
													tradeGrid.setWidget(1, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(2, 0,
																	smallTradeFieldPanel2a);
													tradeGrid
															.setWidget(2, 1,
																	smallTradeFieldPanel2b);
													tradeGrid
															.setWidget(
																	3,
																	0,
																	waterGame.tradePlayer3);
													tradeGrid.setWidget(4, 0,
															new HTML("Import"));
													tradeGrid.setWidget(4, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(5, 0,
																	smallTradeFieldPanel3a);
													tradeGrid
															.setWidget(5, 1,
																	smallTradeFieldPanel3b);
													tradeGrid
															.setWidget(
																	6,
																	0,
																	waterGame.tradePlayer4);
													tradeGrid.setWidget(7, 0,
															new HTML("Import"));
													tradeGrid.setWidget(7, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(8, 0,
																	smallTradeFieldPanel4a);
													tradeGrid
															.setWidget(8, 1,
																	smallTradeFieldPanel4b);
													/*
													 * waterGame.tradePanel.add(
													 * waterGame.tradePlayer2);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel2);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer3);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel3);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer4);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel4);
													 */
												} else if (waterGame
														.getPlayerID() == 1) {
													tradeGrid
															.setWidget(
																	0,
																	0,
																	waterGame.tradePlayer1);
													tradeGrid.setWidget(1, 0,
															new HTML("Import"));
													tradeGrid.setWidget(1, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(2, 0,
																	smallTradeFieldPanel1a);
													tradeGrid
															.setWidget(2, 1,
																	smallTradeFieldPanel1b);
													tradeGrid
															.setWidget(
																	3,
																	0,
																	waterGame.tradePlayer3);
													tradeGrid.setWidget(4, 0,
															new HTML("Import"));
													tradeGrid.setWidget(4, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(5, 0,
																	smallTradeFieldPanel3a);
													tradeGrid
															.setWidget(5, 1,
																	smallTradeFieldPanel3b);
													tradeGrid
															.setWidget(
																	6,
																	0,
																	waterGame.tradePlayer4);
													tradeGrid.setWidget(7, 0,
															new HTML("Import"));
													tradeGrid.setWidget(7, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(8, 0,
																	smallTradeFieldPanel4a);
													tradeGrid
															.setWidget(8, 1,
																	smallTradeFieldPanel4b);

													/*
													 * waterGame.tradePanel.add(
													 * waterGame.tradePlayer1);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel1);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer3);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel3);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer4);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel4);
													 */
												} else if (waterGame
														.getPlayerID() == 2) {
													tradeGrid
															.setWidget(
																	0,
																	0,
																	waterGame.tradePlayer1);
													tradeGrid.setWidget(1, 0,
															new HTML("Import"));
													tradeGrid.setWidget(1, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(2, 0,
																	smallTradeFieldPanel1a);
													tradeGrid
															.setWidget(2, 1,
																	smallTradeFieldPanel1b);
													tradeGrid
															.setWidget(
																	3,
																	0,
																	waterGame.tradePlayer2);
													tradeGrid.setWidget(4, 0,
															new HTML("Import"));
													tradeGrid.setWidget(4, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(5, 0,
																	smallTradeFieldPanel2a);
													tradeGrid
															.setWidget(5, 1,
																	smallTradeFieldPanel2b);
													tradeGrid
															.setWidget(
																	6,
																	0,
																	waterGame.tradePlayer4);
													tradeGrid.setWidget(7, 0,
															new HTML("Import"));
													tradeGrid.setWidget(7, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(8, 0,
																	smallTradeFieldPanel4a);
													tradeGrid
															.setWidget(8, 1,
																	smallTradeFieldPanel4b);
													/*
													 * waterGame.tradePanel.add(
													 * waterGame.tradePlayer1);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel1);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer2);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel2);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer4);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel4);
													 */

												} else if (waterGame
														.getPlayerID() == 3) {
													tradeGrid
															.setWidget(
																	0,
																	0,
																	waterGame.tradePlayer1);
													tradeGrid.setWidget(1, 0,
															new HTML("Import"));
													tradeGrid.setWidget(1, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(2, 0,
																	smallTradeFieldPanel1a);
													tradeGrid
															.setWidget(2, 1,
																	smallTradeFieldPanel1b);
													tradeGrid
															.setWidget(
																	3,
																	0,
																	waterGame.tradePlayer2);
													tradeGrid.setWidget(4, 0,
															new HTML("Import"));
													tradeGrid.setWidget(4, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(5, 0,
																	smallTradeFieldPanel2a);
													tradeGrid
															.setWidget(5, 1,
																	smallTradeFieldPanel2b);
													tradeGrid
															.setWidget(
																	6,
																	0,
																	waterGame.tradePlayer3);
													tradeGrid.setWidget(7, 0,
															new HTML("Import"));
													tradeGrid.setWidget(7, 1,
															new HTML("Export"));
													tradeGrid
															.setWidget(8, 0,
																	smallTradeFieldPanel3a);
													tradeGrid
															.setWidget(8, 1,
																	smallTradeFieldPanel3b);
													/*
													 * waterGame.tradePanel.add(
													 * waterGame.tradePlayer1);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel1);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer2);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel2);
													 * waterGame
													 * .tradePanel.add(waterGame
													 * .tradePlayer3);
													 * waterGame.tradePanel.add(
													 * smallTradeFieldPanel3);
													 */
												}
												waterGame.tradePanel
														.add(tradeGrid);
												waterGame.ressourceTradePanel
														.add(waterGame.ressourcePanel);
												waterGame.ressourceTradePanel
														.add(waterGame.tradePanel);
												waterGame.ressourceTradePanel
														.add(waterGame.measuresPanel);

												waterGame.budgetPanel
														.add(waterGame.budgetValue);
												waterGame.budgetPanel
														.setCellHorizontalAlignment(
																waterGame.budgetValue,
																HasHorizontalAlignment.ALIGN_CENTER);
												waterGame.populationPanel
														.add(waterGame.populationValue);
												waterGame.populationPanel
														.setCellHorizontalAlignment(
																waterGame.populationValue,
																HasHorizontalAlignment.ALIGN_CENTER);

												StackLayoutPanel tp = new StackLayoutPanel(
														Unit.PX);

												// Add icons to FliedschoiceList
												// and provide function to
												if (waterGame.playerID == 0) {
													setIconSIEDLUNG();
													setIconTEE();
													setIconTEEBIO();
													setIconRICE();
													setIconRICEBIO();
													setIconsBildung();
													waterGame.gridLW = new Grid(
															4, 3);
													waterGame.gridLW
															.setWidget(
																	0,
																	0,
																	waterGame.rizePanel);
													waterGame.rizeHTML = new HTML(
															"<b>Reis<b>");
													waterGame.gridLW.setWidget(
															0, 1,
															waterGame.rizeHTML);
													Image infoRiceLogo = new Image(
															"infoLogo.jpg");
													infoRiceLogo.setSize(
															"25px", "25px");
													InfoClickHandler riceINfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Reisfeld bebauen:</i><br></strong>Preis: "
																			+ waterGame.rizeBioPrice
																			+ "<br>Reisertrag pro Runde: "
																			+ FieldType.RICE
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.RICE
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> +++ </span><br>Lebensqualität: <span style=\"color:blue;\"> 0 </span><br>Umweltfreundlichkeit: <span style=\"color:red;\"> -- </span>"));
													infoRiceLogo
															.addClickHandler(riceINfoHandler);
													waterGame.gridLW.setWidget(
															0, 2, infoRiceLogo);
													// gridLW.setWidget(0,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridLW
															.setWidget(
																	1,
																	0,
																	waterGame.rizeBioPanel);
													waterGame.gridLW
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Bioreis<b>"));
													InfoClickHandler riceBioINfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Biologischer Reisanbau:</i> <br>Das Reisfeld wird auf biologische Produktion umgestellt. Innovative und nachhaltige Beackerungstechniken erlauben den Wasserbedarf zu senken. Die Umwelt wird verschont, indem vollständig auf Pflanzenschutzmittel und Kunstdünger verzichtet wird. Dieses Feld kann nur auf Reisfelder gesetzt werden.</strong><br>Preis: "
																			+ waterGame.rizeBioPrice
																			+ " <br>Reisertrag pro Runde: "
																			+ FieldType.RICEBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.RICEBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> + </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													Image infoRiceBioLogo = new Image(
															"infoLogo.jpg");
													infoRiceBioLogo.setSize(
															"25px", "25px");
													infoRiceBioLogo
															.addClickHandler(riceBioINfoHandler);
													waterGame.gridLW.setWidget(
															1, 2,
															infoRiceBioLogo);
													// gridLW.setWidget(1,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridLW.setWidget(
															2, 0,
															waterGame.teePanel);
													waterGame.gridLW
															.setWidget(
																	2,
																	1,
																	new HTML(
																			"<b>Tee<b>"));
													Image infoTeeLogo = new Image(
															"infoLogo.jpg");
													infoTeeLogo.setSize("25px",
															"25px");
													InfoClickHandler teeInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Teefeld bebauen:</i></strong><br>Preis: "
																			+ waterGame.teePrice
																			+ " <br>Teeertrag pro Runde: "
																			+ FieldType.TEE
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.TEE
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> + </span> <br>Lebensqualität:<span style=\"color:blue;\"> 0 </span> <br>Umweltfreundlichkeit: <span style=\"color:red;\"> - </span>"));
													infoTeeLogo
															.addClickHandler(teeInfoHandler);
													waterGame.gridLW.setWidget(
															2, 2, infoTeeLogo);
													// gridLW.setWidget(2,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridLW
															.setWidget(
																	3,
																	0,
																	waterGame.teeBioPanel);
													waterGame.gridLW
															.setWidget(
																	3,
																	1,
																	new HTML(
																			"<b>Biotee<b>"));
													Image infoTeeBioLogo = new Image(
															"infoLogo.jpg");
													infoTeeBioLogo.setSize(
															"25px", "25px");
													InfoClickHandler teeBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Biologischer Teeanbau:</i><br>Das Teefeld wird auf biologische Produktion umgestellt. Innovative und nachhaltige Beackerungstechniken erlauben den Wasserbedarf zu senken. Die Umwelt wird verschont, indem vollständig auf Pflanzenschutzmittel und Kunstdünger verzichtet wird. Dieses Feld kann nur auf Teefelder gesetzt werden.</strong><br>Preis: "
																			+ waterGame.teeBioPrice
																			+ " <br>Teeertrag pro Runde: "
																			+ FieldType.TEEBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.TEEBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> + </span><br>Lebensqualität: <span style=\"color:green;\"> + </span><br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													infoTeeBioLogo
															.addClickHandler(teeBioInfoHandler);
													waterGame.gridLW.setWidget(
															3, 2,
															infoTeeBioLogo);

													// gridLW.setWidget(3,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie = new Grid(
															1, 3);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	0,
																	new HTML(
																			"	Kein Industriezweig vorhanden.	"));

												}
												if (waterGame.playerID == 1) {
													setIconSIEDLUNG();
													setIconLEDER();
													setIconLEDERBIO();
													setIconZUCKER();
													setIconZUCKEREBIO();
													setIconsBildung();
													waterGame.gridLW = new Grid(
															2, 3);
													waterGame.gridLW
															.setWidget(
																	0,
																	0,
																	waterGame.zuckerPanel);
													waterGame.gridLW
															.setWidget(
																	0,
																	1,
																	new HTML(
																			"<b>Zuckerrohr<b>"));
													Image infoZuckerLogo = new Image(
															"infoLogo.jpg");
													infoZuckerLogo.setSize(
															"25px", "25px");
													InfoClickHandler zuckerInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Zuckerfeld bebauen:</i><br></strong><br>Preis: "
																			+ waterGame.zuckrePrice
																			+ " <br>Zucker Ertrag pro Runde: "
																			+ FieldType.ZUCKER
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.ZUCKER
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> +++ </span><br>Lebensqualität: <span style=\"color:blue;\"> 0 </span><br>Umweltfreundlichkeit: <span style=\"color:red;\"> -- </span>"));
													infoZuckerLogo
															.addClickHandler(zuckerInfoHandler);
													waterGame.gridLW.setWidget(
															0, 2,
															infoZuckerLogo);
													// gridLW.setWidget(0,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridLW
															.setWidget(
																	1,
																	0,
																	waterGame.zuckerBioPanel);
													waterGame.gridLW
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Biozucker<b>"));
													Image infoZuckerBioLogo = new Image(
															"infoLogo.jpg");
													infoZuckerBioLogo.setSize(
															"25px", "25px");
													InfoClickHandler zuckerBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Biologischer Zuckerrohranbau:</i><br>Das Zuckerfeld wird auf biologische Produktion umgestellt. Innovative und nachhaltige Beackerungstechniken erlauben den Wasserbedarf zu senken. Die Umwelt wird verschont, indem vollständig auf Pflanzenschutzmittel und Kunstdünger verzichtet wird. Dieses Feld kann nur auf Teefelder gesetzt werden.<br></strong><br>Preis: "
																			+ waterGame.zuckreBioPrice
																			+ " <br>Biozucker Wrtrag pro Runde: "
																			+ FieldType.ZUCKERBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.ZUCKERBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> + </span><br>Lebensqualität: <span style=\"color:green;\"> + </span><br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													infoZuckerBioLogo
															.addClickHandler(zuckerBioInfoHandler);
													waterGame.gridLW.setWidget(
															1, 2,
															infoZuckerBioLogo);
													// gridLW.setWidget(1,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie = new Grid(
															2, 3);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	0,
																	waterGame.lederPanel);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	1,
																	new HTML(
																			"<b>Leder<b>"));
													Image infoLederLogo = new Image(
															"infoLogo.jpg");
													infoLederLogo.setSize(
															"25px", "25px");
													InfoClickHandler lederInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Ledergerberei:</i><br></strong><br>Preis: "
																			+ waterGame.lederPrice
																			+ " <br>Biozucker Wrtrag pro Runde: "
																			+ FieldType.LEDER
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.LEDER
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> +++++ </span><br>Lebensqualität: <span style=\"color:blue;\"> 0 </span><br>Umweltfreundlichkeit: <span style=\"color:red;\"> ----- </span>"));
													infoLederLogo
															.addClickHandler(lederInfoHandler);
													waterGame.gridIndustrie
															.setWidget(0, 2,
																	infoLederLogo);
													// gridIndustrie.setWidget(0,
													// 2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskraft: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	0,
																	waterGame.lederBioPanel);
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Nachhaltige Ledergerberei<b>"));
													Image infoLederBioLogo = new Image(
															"infoLogo.jpg");
													infoLederBioLogo.setSize(
															"25px", "25px");
													InfoClickHandler lederBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Nachhaltige Lederproduktion:</i><br>Die Lederproduktion wird nachhaltig. Das Leder wird auf umweltverträglicher und chemikalienarmer Art behandelt. Eine Kläranlage sorgt dafür, dass die Sauberkeit des Flusses aufrechterhalten bleibt. Soziale und faire Arbeitsbedingungen werden sichergestellt. <br></strong><br>Preis: "
																			+ waterGame.lederPrice
																			+ " <br>Biozucker Wrtrag pro Runde: "
																			+ FieldType.LEDER
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.LEDER
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> ++ </span><br>Lebensqualität: <span style=\"color:green;\"> ++ </span><br>Umweltfreundlichkeit: <span style=\"color:green;\"> ++ </span>"));
													infoLederBioLogo
															.addClickHandler(lederBioInfoHandler);
													waterGame.gridIndustrie
															.setWidget(1, 2,
																	infoLederBioLogo);
													// gridIndustrie.setWidget(1,
													// 2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskraft: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
												}
												if (waterGame.playerID == 2) {
													setIconSIEDLUNG();
													// setIconTEE();
													// setIconTEEBIO();
													setIconRICE();
													setIconRICEBIO();
													setIconTEXTIL();
													setIconTEXTILBIO();
													setIconsBildung();
													waterGame.gridLW = new Grid(
															4, 3);
													waterGame.gridLW
															.setWidget(
																	0,
																	0,
																	waterGame.rizePanel);
													waterGame.gridLW
															.setWidget(
																	0,
																	1,
																	new HTML(
																			"<b>Reis<b>"));
													Image infoRiceLogo = new Image(
															"infoLogo.jpg");
													infoRiceLogo.setSize(
															"25px", "25px");
													InfoClickHandler riceINfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Reisfeld bebauen:</i><br><strong>Preis: "
																			+ waterGame.rizeBioPrice
																			+ "<br>Reisertrag pro Runde: "
																			+ FieldType.RICE
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.RICE
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft: <span style=\"color:green;\"> +++ </span><br>Lebensqualität: <span style=\"color:blue;\"> 0 </span><br>Umweltfreundlichkeit: <span style=\"color:red;\"> -- </span>"));
													infoRiceLogo
															.addClickHandler(riceINfoHandler);
													waterGame.gridLW.setWidget(
															0, 2, infoRiceLogo);
													// gridLW.setWidget(0,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridLW
															.setWidget(
																	1,
																	0,
																	waterGame.rizeBioPanel);
													waterGame.gridLW
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Bioreis<b>"));
													InfoClickHandler riceBioINfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Biologischer Reisanbau:</i> <br>Das Reisfeld wird auf biologische Produktion umgestellt. Innovative und nachhaltige Beackerungstechniken erlauben den Wasserbedarf zu senken. Die Umwelt wird verschont, indem vollständig auf Pflanzenschutzmittel und Kunstdünger verzichtet wird. Dieses Feld kann nur auf Reisfelder gesetzt werden.</strong><br>Preis: "
																			+ waterGame.rizeBioPrice
																			+ " <br>Reisertrag pro Runde: "
																			+ FieldType.RICEBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.RICEBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> + </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													Image infoRiceBioLogo = new Image(
															"infoLogo.jpg");
													infoRiceBioLogo.setSize(
															"25px", "25px");
													infoRiceBioLogo
															.addClickHandler(riceBioINfoHandler);
													waterGame.gridLW.setWidget(
															1, 2,
															infoRiceBioLogo);
												
													waterGame.gridIndustrie = new Grid(
															2, 3);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	0,
																	waterGame.textilPanel);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	1,
																	new HTML(
																			"<b>Textilien<b>"));
													Image infoTextilLogo = new Image(
															"infoLogo.jpg");
													infoTextilLogo.setSize(
															"25px", "25px");
													InfoClickHandler textilInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Textilindustrie:</i></strong><br>Preis: "
																			+ waterGame.textilPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.TEXTIL
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.TEXTIL
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> +++++ </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:red;\"> --- </span>"));
													infoTextilLogo
															.addClickHandler(textilInfoHandler);
													waterGame.gridIndustrie
															.setWidget(0, 2,
																	infoTextilLogo);
													// gridIndustrie.setWidget(0,2,new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	0,
																	waterGame.textilBioPanel);
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Nachhaltige Textilien<b>"));
													Image infoTextilBioLogo = new Image(
															"infoLogo.jpg");
													infoTextilBioLogo.setSize(
															"25px", "25px");
													InfoClickHandler textilBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Nachhaltige Textilproduktion:</i><br>Die Textilproduktion wird nachhaltig. Die Textilien werden auf umweltverträglicher und giftfreier Art gefertigt. Eine Kläranlage sorgt dafür, dass die Sauberkeit des Flusses aufrechterhalten bleibt. Soziale und faire Arbeitsbedingungen werden sichergestellt. </strong><br>Preis: "
																			+ waterGame.textilPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.TEXTIL
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.TEXTIL
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> + </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:green;\"> ++ </span>"));
													infoTextilBioLogo
															.addClickHandler(textilBioInfoHandler);
													waterGame.gridIndustrie
															.setWidget(1, 2,
																	infoTextilBioLogo);
													// gridIndustrie.setWidget(1,2,new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
												}
												if (waterGame.playerID == 3) {
													setIconSIEDLUNG();
													setIconFISCH();
													setIconFISCHBIO();
													setIconIT();
													setIconITBIO();
													setIconsBildung();
													waterGame.gridLW = new Grid(
															2, 3);
													// HorizontalPanel
													// labelInfo1 = new
													// HorizontalPanel();
													HTML label1 = new HTML(
															"<b>Fischerei<b>");
													// labelInfo1.add(label1);
													// labelInfo1.add(waterGame.infoLogo);
													waterGame.gridLW
															.setWidget(
																	0,
																	0,
																	waterGame.fischPanel);
													waterGame.gridLW.setWidget(
															0, 1, label1);
													Image infoFischLogo = new Image(
															"infoLogo.jpg");
													infoFischLogo.setSize(
															"25px", "25px");
													InfoClickHandler fischInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Fischfang:</i></strong><br>Preis: "
																			+ waterGame.fischPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.FISCH
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.FISCH
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> +++ </span> <br>Lebensqualität:<span style=\"color:blue;\"> 0 </span> <br>Umweltfreundlichkeit: <span style=\"color:red;\"> -- </span>"));
													infoFischLogo
															.addClickHandler(fischInfoHandler);
													waterGame.gridLW
															.setWidget(0, 2,
																	infoFischLogo);
													// gridLW.setWidget(0,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));

													waterGame.gridLW
															.setWidget(
																	1,
																	0,
																	waterGame.fischBioPanel);
													// HorizontalPanel
													// labelInfo2 = new
													// HorizontalPanel();
													HTML label2 = new HTML(
															"<b>Nachhaltige Fischerei<b>");
													// labelInfo2.add(label2);
													// labelInfo2.add(waterGame.infoLogo);
													waterGame.gridLW.setWidget(
															1, 1, label2);
													Image infoFischBioLogo = new Image(
															"infoLogo.jpg");
													infoFischBioLogo.setSize(
															"25px", "25px");
													InfoClickHandler fischBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Nachaltiger Fischfang:</i><br>Die Fangmengen werden so reguliert, dass sich die Fischbestände sich wieder erholen können. Strenge Regelungen für umweltfreundliche und nachhaltige Fangmethoden sind vorgegeben. </strong><br>Preis: "
																			+ waterGame.fischBioPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.FISHBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.FISHBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> + </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													infoFischBioLogo
															.addClickHandler(fischBioInfoHandler);
													waterGame.gridLW.setWidget(
															1, 2,
															infoFischBioLogo);
													// gridLW.setWidget(1,2, new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie = new Grid(
															2, 3);
													waterGame.gridIndustrie
															.setWidget(
																	0,
																	0,
																	waterGame.itPanel);
													// HorizontalPanel
													// labelInfo3 = new
													// HorizontalPanel();
													HTML label3 = new HTML(
															"<b>IT<b>");
													// labelInfo3.add(label3);
													// labelInfo3.add(waterGame.infoLogo);
													waterGame.gridIndustrie
															.setWidget(0, 1,
																	label3);
													Image infoITLogo = new Image(
															"infoLogo.jpg");
													infoITLogo.setSize("25px",
															"25px");
													InfoClickHandler itInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Informationstechnologie (IT):</i></strong><br>Preis: "
																			+ waterGame.itPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.IT
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.IT
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> ++++++ </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:red;\"> --- </span>"));
													infoITLogo
															.addClickHandler(itInfoHandler);
													waterGame.gridIndustrie
															.setWidget(0, 2,
																	infoITLogo);
													// gridIndustrie.setWidget(0,2,new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	0,
																	waterGame.itBioPanel);
													waterGame.gridIndustrie
															.setWidget(
																	1,
																	1,
																	new HTML(
																			"<b>Nachhaltige IT<b>"));
													Image infoITBioLogo = new Image(
															"infoLogo.jpg");
													infoITBioLogo.setSize(
															"25px", "25px");
													// TEXT
													// AUSFÜLLEN!!!!!!!!!!!!!!!!!!!
													InfoClickHandler itBioInfoHandler = new InfoClickHandler(
															new HTML(
																	"<strong><i>Nachhaltige Informationstechnologie:</i>In der Herstellung werden die Schadstoff-Emissionen, Energie- und Materialverbrauch reduziert. Soziale und faire Arbeitsbedingungen werden sichergestellt. </strong><br>Preis: "
																			+ waterGame.itBioPrice
																			+ " <br>Textilienertrag pro Runde: "
																			+ FieldType.ITBIO
																					.getErtragRessourcen()
																			+ "<br>Budget-Ertrag pro Runde: "
																			+ FieldType.ITBIO
																					.getErtragBudget()
																			+ "<br>Wirtschaftskraft:<span style=\"color:green;\"> +++ </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:green;\"> + </span>"));
													infoITBioLogo
															.addClickHandler(itBioInfoHandler);
													waterGame.gridIndustrie
															.setWidget(1, 2,
																	infoITBioLogo);
													// gridIndustrie.setWidget(1,2,new
													// HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));

												}
			
												waterGame.gridLW
														.setWidth("300px");
												waterGame.gridIndustrie
														.setWidth("300px");
												gridSiedlung.setWidth("300px");
												waterGame.gridBildung
														.setWidth("300px");

												tp.add(waterGame.gridLW,
														"Landwirtschaft", 40);
												tp.add(waterGame.gridIndustrie,
														"Industrie", 40);
												tp.add(gridSiedlung,
														"Siedlung", 40);
												tp.add(waterGame.gridBildung,
														"Bildung", 40);
												tp.setWidth("300px");
												tp.setHeight("600px");
												HorizontalPanel removeFieldButton = new HorizontalPanel();
												waterGame.removeImagePanel = new VerticalPanel();
												Image removeField = new Image();
												removeField
														.setUrl("remove.jpg");
												removeField.setSize("50px",
														"50px");
												RemoveClickHandler removeClickHandler = new RemoveClickHandler(
														greetingService,
														waterGame);
												removeField
														.addClickHandler(removeClickHandler);
												waterGame.removeImagePanel
														.add(removeField);
												waterGame.removeImagePanel
														.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
												removeFieldButton
														.add(waterGame.removeImagePanel);
												removeFieldButton
														.setWidth("300px");
												removeFieldButton
														.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
												// removeFieldButton.setStyleName("panel");
												waterGame.fieldsPanel.add(tp);
												waterGame.fieldsPanel
														.add(removeFieldButton);
												waterGame.fieldsPanel
														.setCellVerticalAlignment(
																tp,
																HasVerticalAlignment.ALIGN_TOP);
												waterGame.fieldsPanel
														.setCellHorizontalAlignment(
																tp,
																HasHorizontalAlignment.ALIGN_CENTER);
												
												greetingService
														.getCommonIndicator(new AsyncCallback<Integer>() {

															@Override
															public void onFailure(
																	Throwable caught) {
																// TODO
																// Auto-generated
																// method stub

															}

															@Override
															public void onSuccess(
																	Integer result) {
																// TODO
																// Auto-generated
																// method stub
																HTML commonIndicatorHTML = new HTML(
																		"<progress value=\""
																				+ result
																				+ "\" max=\"100\">"
																				+ result
																				+ "</progress>");
																Label percentage = new Label(
																		Integer.toString(result));
																waterGame.commonIndikatorPanel
																		.add(commonIndicatorHTML);
																waterGame.commonIndikatorPanel
																		.add(percentage);
																waterGame.commonIndikatorPanel
																		.setCellVerticalAlignment(
																				waterGame.commonIndikatorLabel,
																				HasVerticalAlignment.ALIGN_MIDDLE);
																waterGame.commonIndikatorPanel
																		.setCellHorizontalAlignment(
																				waterGame.commonIndikatorLabel,
																				HasHorizontalAlignment.ALIGN_CENTER);
																waterGame.commonIndikatorPanel
																		.setCellVerticalAlignment(
																				commonIndicatorHTML,
																				HasVerticalAlignment.ALIGN_MIDDLE);
																waterGame.commonIndikatorPanel
																		.setCellHorizontalAlignment(
																				commonIndicatorHTML,
																				HasHorizontalAlignment.ALIGN_CENTER);
																waterGame.commonIndikatorPanel
																		.setCellVerticalAlignment(
																				percentage,
																				HasVerticalAlignment.ALIGN_MIDDLE);
																waterGame.commonIndikatorPanel
																		.setCellHorizontalAlignment(
																				percentage,
																				HasHorizontalAlignment.ALIGN_CENTER);

															}
														});

												// We can add style names to
												// widgets
												waterGame.ressourcePanel.setWidth("330px");
												waterGame.measuresPanel.setWidth("330px");
												waterGame.tradePanel.setWidth("330px");
												waterGame.gamegridPanel.setWidth("500px");
												waterGame.fieldsPanel.setWidth("300px");
												waterGame.commonIndikatorPanel.setWidth("300px");
												waterGame.ressourcePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);;

												waterGame.startButton
														.addStyleName("startButton");
												waterGame.instructionButton
														.addStyleName("startButton");
												waterGame.validateButton
														.addStyleName("startButton");
												waterGame.wirtschaftsKraftPanel
														.addStyleName("panel");
												waterGame.lebensQualitaetPanel
														.addStyleName("panel");
												waterGame.umweltFreundlichkeitPanel
														.addStyleName("panel");
												waterGame.populationPanel
														.addStyleName("panel");
												waterGame.budgetPanel
														.addStyleName("panel");
												waterGame.ressourcePanel
														.addStyleName("panel");
												waterGame.gamegridPanel
														.addStyleName("gridpanel");
												waterGame.fieldsPanel
														.addStyleName("panel");
												waterGame.tradePanel
														.addStyleName("panel");
												waterGame.measuresPanel
														.addStyleName("panel");
												waterGame.commonIndikatorPanel
														.addStyleName("panel");
												waterGame.validateButtonPanel
														.addStyleName("panel");
											}

										});
							}

						});
			}
		});

		
	}

	private void setIconRICE() {
		String rizeImageString = "rizeField.jpeg";
		waterGame.rizeImage.setUrl(rizeImageString);
		waterGame.rizeImage.setSize("50px", "50px");
		IconHandler iconhandlerRIZE = new IconHandler(greetingService,
				waterGame, rizeImageString, waterGame.rizePrice,
				waterGame.removeRizePrice, 0);
		waterGame.rizeImage.addClickHandler(iconhandlerRIZE);
		waterGame.rizePanel.add(waterGame.rizeImage);
	}

	private void setIconRICEBIO() {
		String riceBioImageString = "rizeFieldbio.jpeg";
		waterGame.rizeBioImage.setUrl(riceBioImageString);
		waterGame.rizeBioImage.setSize("50px", "50px");
		IconHandler iconhandlerRICEBIO = new IconHandler(greetingService,
				waterGame, riceBioImageString, waterGame.rizeBioPrice,
				waterGame.removeRizeBioPrice, 100);
		waterGame.rizeBioImage.addClickHandler(iconhandlerRICEBIO);
		waterGame.rizeBioPanel.add(waterGame.rizeBioImage);
	}

	private void setIconTEE() {
		String teeImageString = "tee.jpeg";
		waterGame.teeImage.setUrl(teeImageString);
		waterGame.teeImage.setSize("50px", "50px");
		IconHandler iconhandlerTEE = new IconHandler(greetingService,
				waterGame, teeImageString, waterGame.teePrice,
				waterGame.teePriceRemove, 0);
		waterGame.teeImage.addClickHandler(iconhandlerTEE);
		waterGame.teePanel.add(waterGame.teeImage);
	}

	private void setIconTEEBIO() {
		String teeBioImageString = "teebio.jpeg";
		waterGame.teeBioImage.setUrl(teeBioImageString);
		waterGame.teeBioImage.setSize("50px", "50px");
		IconHandler iconhandlerTEEBIO = new IconHandler(greetingService,
				waterGame, teeBioImageString, waterGame.teeBioPrice,
				waterGame.teeBioPriceRemove, 100);
		waterGame.teeBioImage.addClickHandler(iconhandlerTEEBIO);
		waterGame.teeBioPanel.add(waterGame.teeBioImage);
	}

	private void setIconZUCKER() {
		String zuckerImageString = "zucker.jpg";
		waterGame.zuckerImage.setUrl(zuckerImageString);
		waterGame.zuckerImage.setSize("50px", "50px");
		IconHandler iconhandlerZUCKER = new IconHandler(greetingService,
				waterGame, zuckerImageString, waterGame.zuckrePrice,
				waterGame.zuckerPriceRemove, 0);
		waterGame.zuckerImage.addClickHandler(iconhandlerZUCKER);
		waterGame.zuckerPanel.add(waterGame.zuckerImage);
	}

	private void setIconZUCKEREBIO() {
		String zuckerBioImageString = "zuckerbio.jpg";
		waterGame.zuckerBioImage.setUrl(zuckerBioImageString);
		waterGame.zuckerBioImage.setSize("50px", "50px");
		System.out.println("Zuckerbio BEFORE");
		IconHandler iconhandlerZUCKEREBIO = new IconHandler(greetingService,
				waterGame, zuckerBioImageString, waterGame.zuckreBioPrice,
				waterGame.zuckerBioPriceRemove, 100);
		System.out.println("Zuckerbio AFTER");

		waterGame.zuckerBioImage.addClickHandler(iconhandlerZUCKEREBIO);
		waterGame.zuckerBioPanel.add(waterGame.zuckerBioImage);
	}

	private void setIconFISCH() {
		String fischImageString = "fisch.jpg";
		waterGame.fischImage.setUrl(fischImageString);
		waterGame.fischImage.setSize("50px", "50px");
		IconHandler iconhandlerFISCH = new IconHandler(greetingService,
				waterGame, fischImageString, waterGame.fischPrice,
				waterGame.fischPriceRemove, 0);
		waterGame.fischImage.addClickHandler(iconhandlerFISCH);
		waterGame.fischPanel.add(waterGame.fischImage);
	}

	private void setIconFISCHBIO() {
		String fischBIOImageString = "fischbio.jpg";
		waterGame.fischBioImage.setUrl(fischBIOImageString);
		waterGame.fischBioImage.setSize("50px", "50px");
		IconHandler iconhandlerFISCHBIO = new IconHandler(greetingService,
				waterGame, fischBIOImageString, waterGame.fischBioPrice,
				waterGame.fischBioPriceRemove, 100);
		waterGame.fischBioImage.addClickHandler(iconhandlerFISCHBIO);
		waterGame.fischBioPanel.add(waterGame.fischBioImage);
	}

	private void setIconTEXTIL() {
		String textilImageString = "textil.jpg";
		waterGame.textilImage.setUrl(textilImageString);
		waterGame.textilImage.setSize("50px", "50px");
		IconHandler iconhandlerTEXTIL = new IconHandler(greetingService,
				waterGame, textilImageString, waterGame.textilPrice,
				waterGame.textilPriceRemove, 0);
		waterGame.textilImage.addClickHandler(iconhandlerTEXTIL);
		waterGame.textilPanel.add(waterGame.textilImage);
	}

	private void setIconTEXTILBIO() {
		String textilBioImageString = "textilbio.jpg";
		waterGame.textilBioImage.setUrl(textilBioImageString);
		waterGame.textilBioImage.setSize("50px", "50px");
		IconHandler iconhandlerTEXTILBIO = new IconHandler(greetingService,
				waterGame, textilBioImageString, waterGame.textilBioPrice,
				waterGame.textilBioPriceRemove, 120);
		waterGame.textilBioImage.addClickHandler(iconhandlerTEXTILBIO);
		waterGame.textilBioPanel.add(waterGame.textilBioImage);
	}

	private void setIconLEDER() {
		String lederImageString = "leder.JPG";
		waterGame.lederImage.setUrl(lederImageString);
		waterGame.lederImage.setSize("50px", "50px");
		IconHandler iconhandlerLEDER = new IconHandler(greetingService,
				waterGame, lederImageString, waterGame.lederPrice,
				waterGame.lederPriceRemove, 0);
		waterGame.lederImage.addClickHandler(iconhandlerLEDER);
		waterGame.lederPanel.add(waterGame.lederImage);
	}

	private void setIconLEDERBIO() {
		String lederBioImageString = "lederbio.jpg";
		waterGame.lederBioImage.setUrl(lederBioImageString);
		waterGame.lederBioImage.setSize("50px", "50px");
		IconHandler iconhandlerLEDERBIO = new IconHandler(greetingService,
				waterGame, lederBioImageString, waterGame.lederBioPrice,
				waterGame.lederBioPriceRemove, 120);
		waterGame.lederBioImage.addClickHandler(iconhandlerLEDERBIO);
		waterGame.lederBioPanel.add(waterGame.lederBioImage);
	}

	private void setIconIT() {
		String itImageString = "it.jpg";
		waterGame.itImage.setUrl(itImageString);
		waterGame.itImage.setSize("50px", "50px");
		IconHandler iconhandlerIT = new IconHandler(greetingService, waterGame,
				itImageString, waterGame.itPrice, waterGame.itPriceRemove, 0);
		waterGame.itImage.addClickHandler(iconhandlerIT);
		waterGame.itPanel.add(waterGame.itImage);
	}

	private void setIconITBIO() {
		String itBioImageString = "itbio.jog.jpg";
		waterGame.itBioImage.setUrl(itBioImageString);
		waterGame.itBioImage.setSize("50px", "50px");
		IconHandler iconhandlerITBIO = new IconHandler(greetingService,
				waterGame, itBioImageString, waterGame.itBioPrice,
				waterGame.itBioPriceRemove, 120);
		waterGame.itBioImage.addClickHandler(iconhandlerITBIO);
		waterGame.itBioPanel.add(waterGame.itBioImage);
	}

	private void setIconSIEDLUNG() {
		String siedlungImageString = "siedlung.jpg";
		waterGame.siedlungImage.setUrl(siedlungImageString);
		waterGame.siedlungImage.setSize("50px", "50px");
		IconHandler iconhandlerSIEDLUNG = new IconHandler(greetingService,
				waterGame, siedlungImageString, waterGame.siedlungPrice,
				waterGame.siedlungPriceRemove, 0);
		waterGame.siedlungImage.addClickHandler(iconhandlerSIEDLUNG);
		waterGame.siedlungPanel.add(waterGame.siedlungImage);
		gridSiedlung.setWidget(0, 0, waterGame.siedlungPanel);
		gridSiedlung.setWidget(0, 1, new HTML("<b>Siedlung<b>"));
		Image infoSiedlungLogo = new Image("infoLogo.jpg");
		infoSiedlungLogo.setSize("25px", "25px");
		InfoClickHandler siedlungInfoHandler = new InfoClickHandler(
				new HTML(
						"<strong><i>Siedlungsbau:</i><br>Die Bevölkerungzahl der Stadt steigt in der nächsten Runde um 100 Einwohner.</strong><br>Preis: "
								+ waterGame.siedlungPrice));
		infoSiedlungLogo.addClickHandler(siedlungInfoHandler);
		gridSiedlung.setWidget(0, 2, infoSiedlungLogo);
		// gridSiedlung.setWidget(0,2, new
		// HTML("<small>Die Bevölkerungzahl der Stadt steigt um 100 Einwohner.<small>"));
	}

	private void setIconUNTERSTUFE() {
		String unterStufeImageString = "unterstufe.jpeg";
		waterGame.unterStufeImage.setUrl(unterStufeImageString);
		waterGame.unterStufeImage.setSize("50px", "50px");
		IconHandler iconhandlerUNTERSTUFE = new IconHandler(greetingService,
				waterGame, unterStufeImageString, waterGame.unterStufePrice,
				waterGame.unterStufePriceRemove, 0);
		waterGame.unterStufeImage.addClickHandler(iconhandlerUNTERSTUFE);
		waterGame.unterstufePanel.add(waterGame.unterStufeImage);
	}

	private void setIconOBREERSTUFE() {
		String oberStufeImageString = "oberstufe.jpeg";
		waterGame.oberStufeImage.setUrl(oberStufeImageString);
		waterGame.oberStufeImage.setSize("50px", "50px");
		IconHandler iconhandlerOBERSTUFE = new IconHandler(greetingService,
				waterGame, oberStufeImageString, waterGame.oberStufePrice,
				waterGame.oberStufeRemove, 0);
		waterGame.oberStufeImage.addClickHandler(iconhandlerOBERSTUFE);
		waterGame.oberstufePanel.add(waterGame.oberStufeImage);

	}

	private void setIconsBildung() {
		setIconUNTERSTUFE();
		setIconOBREERSTUFE();
		setIconUNI();
		waterGame.gridBildung.setWidget(0, 0, waterGame.unterstufePanel);
		waterGame.gridBildung.setWidget(0, 1, new HTML("<b>Unterstufe<b>"));
		Image infoUnterstufeLogo = new Image("infoLogo.jpg");
		infoUnterstufeLogo.setSize("25px", "25px");
		InfoClickHandler unterStufeInfoHandler = new InfoClickHandler(
				new HTML(
						"<strong><i>Unterstufe:</i><br></strong>Preis: "
								+ waterGame.unterStufePrice
								+ "<br>Neues Wissen pro Runde: "
								+ FieldType.UNTERSTUFE.getErtragWissen()
								+ "Wirtschaftskraft:<span style=\"color:blue;\"> 0 </span> <br>Lebensqualität:<span style=\"color:green;\"> + </span> <br>Umweltfreundlichkeit: <span style=\"color:blue;\"> 0 </span>"));
		infoUnterstufeLogo.addClickHandler(unterStufeInfoHandler);
		waterGame.gridBildung.setWidget(0, 2, infoUnterstufeLogo);
		// gridBildung.setWidget(0,2, new
		// HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));
		Image infoOberstufeLogo = new Image("infoLogo.jpg");
		infoOberstufeLogo.setSize("25px", "25px");
		InfoClickHandler oberStufeInfoHandler = new InfoClickHandler(
				new HTML(
						"<strong><i>Oberstufe:</i><br></strong><br>Preis: "
								+ waterGame.oberStufePrice
								+ "<br>Neues Wissen pro Runde: "
								+ FieldType.OBERSTUFE.getErtragWissen()
								+ "Wirtschaftskraft:<span style=\"color:blue;\"> 0 </span> <br>Lebensqualität:<span style=\"color:green;\"> ++ </span> <br>Umweltfreundlichkeit: <span style=\"color:blue;\"> 0 </span>"));
		infoOberstufeLogo.addClickHandler(oberStufeInfoHandler);
		waterGame.gridBildung.setWidget(1, 0, waterGame.oberstufePanel);
		waterGame.gridBildung.setWidget(1, 1, new HTML("<b>Oberstufe<b>"));
		waterGame.gridBildung.setWidget(1, 2, infoOberstufeLogo);
		// gridBildung.setWidget(1,2, new
		// HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));
		waterGame.gridBildung.setWidget(2, 0, waterGame.uniPanel);
		waterGame.gridBildung.setWidget(2, 1, new HTML("<b>Universität<b>"));
		Image infoUniLogo = new Image("infoLogo.jpg");
		infoUniLogo.setSize("25px", "25px");
		InfoClickHandler uniInfoHandler = new InfoClickHandler(
				new HTML(
						"<strong><i>Universität:</i><br></strong><br>Preis: "
								+ waterGame.uniPrice
								+ "<br>Neues Wissen pro Runde: "
								+ FieldType.UNI.getErtragWissen()
								+ "Wirtschaftskraft:<span style=\"color:blue;\"> 0 </span> <br>Lebensqualität:<span style=\"color:green;\"> +++ </span> <br>Umweltfreundlichkeit: <span style=\"color:blue;\"> 0 </span>"));
		infoUniLogo.addClickHandler(uniInfoHandler);
		waterGame.gridBildung.setWidget(2, 2, infoUniLogo);
		// gridBildung.setWidget(2,2, new
		// HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));
	}

	private void setIconUNI() {
		String uniImageString = "uni.jpg";
		waterGame.uniImage.setUrl(uniImageString);
		waterGame.uniImage.setSize("50px", "50px");
		IconHandler iconhandlerUNI = new IconHandler(greetingService,
				waterGame, uniImageString, waterGame.uniPrice,
				waterGame.uniPriceRemove, 0);
		waterGame.uniImage.addClickHandler(iconhandlerUNI);
		waterGame.uniPanel.add(waterGame.uniImage);
	}

	private void setListBox(ListBox listbox) {
		listbox.addItem("Reis");
		listbox.addItem("Fisch");
		listbox.addItem("Zucker");
		listbox.addItem("Tee");
		listbox.addItem("Leder");
		listbox.addItem("Textilien");
		listbox.addItem("IT");
		listbox.addItem("Wissen");
	}

	void setIndicatorValue(ArrayList<Integer> result) {
		int wirtschaftsIndicatorInt = result.get(0);
		System.out.println("WIRTSCHAFTSINDIKATOR" + wirtschaftsIndicatorInt);
		int lebensqualiIndicatorInt = result.get(1);
		System.out.println("LEBENSQUALITàT " + lebensqualiIndicatorInt);
		int umweltIndicatorInt = result.get(2);
		System.out.println("UMWELTINDIKATOR " + umweltIndicatorInt);
		waterGame.wirtschaftIndicatorValue = Integer
				.toString(wirtschaftsIndicatorInt);
		waterGame.lebensqualiIndicatorValue = Integer
				.toString(lebensqualiIndicatorInt);
		waterGame.umweltIndicatorValue = Integer.toString(umweltIndicatorInt);
	}

	void setRessourceValueLW(ArrayList<Integer> result, int populationInt) {
		int rizeValueInt = result.get(5);
		waterGame.rizeNeededInt = populationInt
				/ waterGame.grundbedarfProKopfLW;
		int theValueInt = result.get(6);
		waterGame.theNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int sugarValueInt = result.get(7);
		waterGame.sugarNeededInt = populationInt
				/ waterGame.grundbedarfProKopfLW;
		int fishValueInt = result.get(8);
		waterGame.fishNeededInt = populationInt
				/ waterGame.grundbedarfProKopfLW;
		waterGame.rizeValue = new HTML("Reis: \t"
				+ Integer.toString(rizeValueInt) + "/"
				+ Integer.toString(waterGame.rizeNeededInt));
		waterGame.fishValue = new HTML("Fisch: \t"
				+ Integer.toString(fishValueInt) + "/"
				+ Integer.toString(waterGame.fishNeededInt));
		waterGame.sugarValue = new HTML("Zucker: \t"
				+ Integer.toString(sugarValueInt) + "/"
				+ Integer.toString(waterGame.sugarNeededInt));
		waterGame.teaValue = new HTML("Tee: \t" + Integer.toString(theValueInt)
				+ "/" + Integer.toString(waterGame.theNeededInt));
		waterGame.rizeValueInteger = rizeValueInt;
		waterGame.teaValueInteger = theValueInt;
		waterGame.fishValueInteger = fishValueInt;
		waterGame.sugarValueInteger = sugarValueInt;

	}

	void setRessourceValueIndustrie(ArrayList<Integer> result, int populationInt) {
		int lederValueInt = result.get(9);
		waterGame.lederNeededInt = populationInt
				/ waterGame.grundbedarfProKopfIndustrie;
		int textilValueInt = result.get(10);
		waterGame.textilNeededInt = populationInt
				/ waterGame.grundbedarfProKopfIndustrie;
		int itValueInt = result.get(11);
		waterGame.itNeededInt = populationInt
				/ waterGame.grundbedarfProKopfIndustrie;
		int budgetInt = result.get(4);
		waterGame.lederValue = new HTML("Leder: \t"
				+ Integer.toString(lederValueInt) + "/"
				+ Integer.toString(waterGame.lederNeededInt));
		waterGame.textilValue = new HTML("Textilien: \t"
				+ Integer.toString(textilValueInt) + "/"
				+ Integer.toString(waterGame.textilNeededInt));
		waterGame.itValue = new HTML("IT: \t" + Integer.toString(itValueInt)
				+ "/" + Integer.toString(waterGame.itNeededInt));
		waterGame.lederValueInteger = lederValueInt;
		waterGame.textilValueInteger = lederValueInt;
		waterGame.itValueInteger = itValueInt;
	}

	void setKnowHowValue(ArrayList<Integer> result) {
		int knowHow = result.get(12);
		waterGame.knowhowValue = new HTML("Wissen: \t"
				+ Integer.toString(knowHow));
		waterGame.knowhowValueInteger = knowHow;
	}

	void setPopulationValue(int populationInt) {
		waterGame.populationValue = new Label(Integer.toString(populationInt));

	}

	void setBudgetValue(int budgetInt) {
		waterGame.budgetValue = new Label(Integer.toString(budgetInt));
		waterGame.budgetValueInt = budgetInt;
	}

	void fillBudgetPanel() {
		waterGame.budgetPanel.clear();
		waterGame.budgetPanel.add(waterGame.budgetLabel);
		waterGame.budgetPanel.add(waterGame.budgetValue);
	}

	void fillWirtschaftskraftPanel() {
		HTML wirtschaftHTML = new HTML("<progress align=\"center\" value=\""
				+ waterGame.wirtschaftIndicatorValue + "\" max=\"100\">"
				+ waterGame.wirtschaftIndicatorValue + "</progress>");
		waterGame.wirtschaftsKraftPanel.add(waterGame.wirtschaftLabel);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(
				waterGame.wirtschaftLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(wirtschaftHTML);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(
				wirtschaftHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(new Label(
				waterGame.wirtschaftIndicatorValue));
		;
	}

	void fillLebensqualiPanel() {
		HTML lebensQualiHTML = new HTML("<progress value=\""
				+ waterGame.lebensqualiIndicatorValue + "\" max=\"100\">"
				+ waterGame.lebensqualiIndicatorValue + "</progress>");
		waterGame.lebensQualitaetPanel.add(waterGame.lebensqualitaetLabel);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(
				waterGame.lebensqualitaetLabel,
				HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(lebensQualiHTML);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(
				lebensQualiHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(new Label(
				waterGame.lebensqualiIndicatorValue));

	}

	void fillUmweltPanel() {
		HTML umweltHTML = new HTML("<progress value=\""
				+ waterGame.umweltIndicatorValue + "\" max=\"100\">"
				+ waterGame.umweltIndicatorValue + "</progress>");
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel.setCellHorizontalAlignment(
				waterGame.umweltLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel.add(umweltHTML);
		waterGame.umweltFreundlichkeitPanel.setCellHorizontalAlignment(
				umweltHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(new Label(
				waterGame.umweltIndicatorValue));
	}

	void fillRessourcePanel() {
		waterGame.ressourcePanel.clear();

		waterGame.ressourcePanel.add(waterGame.ressourceTitle);

		Grid ressourcePanelGrid = new Grid(8, 2);
		ressourcePanelGrid.setWidget(0, 0, waterGame.rizeLabel);
		ressourcePanelGrid.setWidget(1, 0, waterGame.fishLabel);
		ressourcePanelGrid.setWidget(2, 0, waterGame.sugarLabel);
		ressourcePanelGrid.setWidget(3, 0, waterGame.theLabel);
		ressourcePanelGrid.setWidget(4, 0, waterGame.lederLabel);
		ressourcePanelGrid.setWidget(5, 0, waterGame.textilLabel);
		ressourcePanelGrid.setWidget(6, 0, waterGame.itLabel);
		ressourcePanelGrid.setWidget(7, 0, waterGame.wissenLabel);
		ressourcePanelGrid.setWidget(0, 1, waterGame.rizeValue);
		ressourcePanelGrid.setWidget(1, 1, waterGame.fishValue);
		ressourcePanelGrid.setWidget(2, 1, waterGame.sugarValue);
		ressourcePanelGrid.setWidget(3, 1, waterGame.teaValue);
		ressourcePanelGrid.setWidget(4, 1, waterGame.lederValue);
		ressourcePanelGrid.setWidget(5, 1, waterGame.textilValue);
		ressourcePanelGrid.setWidget(6, 1, waterGame.itValue);
		ressourcePanelGrid.setWidget(7, 1, waterGame.knowhowValue);
		waterGame.ressourcePanel.add(ressourcePanelGrid);
		
		waterGame.ressourcePanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);;
	}

	void fillPopulationPanel() {
		waterGame.populationPanel.add(waterGame.populationLabel);
		waterGame.populationPanel.add(waterGame.populationValue);
		waterGame.populationPanel.setCellHorizontalAlignment(
				waterGame.populationValue, HasHorizontalAlignment.ALIGN_CENTER);
	}

	void fillMeasuresPanel() {
		// UmweltSchutz
		Grid measuresTable = new Grid(4, 2);
		VerticalPanel umweltSchutzpanel = new VerticalPanel();

		umweltSchutzpanel.add(waterGame.umweltSchutzLabel);
		measuresTable.setWidget(0, 0, umweltSchutzpanel);
		// umweltSchutzpanel.addStyleName("measures");
		// measuresTable.setWidget(0, 0, waterGame.umweltSchutzBeschreibung);
		UmweltverschmutzungClickHandler umweltClick = new UmweltverschmutzungClickHandler(
				waterGame, greetingService);
		waterGame.umweltSchutzButton.addClickHandler(umweltClick);
		measuresTable.setWidget(0, 1, waterGame.umweltSchutzButton);

		measuresTable.setWidget(2, 0, waterGame.reformen);
		// measuresTable.setWidget(2, 0, waterGame.reformenBeschreibung);
		ReformenClickHandler reformClickHandler = new ReformenClickHandler(
				waterGame, greetingService);
		waterGame.reformenButton.addClickHandler(reformClickHandler);
		measuresTable.setWidget(2, 1, waterGame.reformenButton);

		measuresTable.setWidget(3, 0, waterGame.naturgefahrenSchutz);
		// measuresTable.setWidget(3, 0,
		// waterGame.naturkatastropheBeschreibung);
		NKSchutzClickHandler nkClickhandler = new NKSchutzClickHandler(
				waterGame, greetingService);
		waterGame.naturkatastropheButton.addClickHandler(nkClickhandler);
		measuresTable.setWidget(3, 1, waterGame.naturkatastropheButton);

		//waterGame.measuresPanel.addStyleName("measures");
		waterGame.measuresPanel.add(waterGame.titleMeasures);

		waterGame.measuresPanel.add(measuresTable);
//		waterGame.measuresPanel.setHeight("300px");
		/*
		 * HorizontalPanel hp1 = new HorizontalPanel();
		 * hp1.add(waterGame.umweltSchutzLabel);
		 * hp1.add(waterGame.umweltSchutzButton);
		 * waterGame.measuresPanel.add(hp1);
		 * waterGame.measuresPanel.add(waterGame.umweltSchutzBeschreibung); //
		 * Subventionen HorizontalPanel hp2 = new HorizontalPanel();
		 * hp2.add(waterGame.subventionenLabel);
		 * hp2.add(waterGame.subventionenButton);
		 * waterGame.measuresPanel.add(hp2);
		 * waterGame.measuresPanel.add(waterGame.subventionenBeschreibung);
		 * //Reformen HorizontalPanel hp3 = new HorizontalPanel();
		 * hp3.add(waterGame.reformen); hp3.add(waterGame.reformenButton);
		 * waterGame.measuresPanel.add(hp3);
		 * waterGame.measuresPanel.add(waterGame.reformenBeschreibung);
		 * //Naturkatastrophen HorizontalPanel hp4 = new HorizontalPanel();
		 * hp4.add(waterGame.naturgefahrenSchutz);
		 * hp4.add(waterGame.naturkatastropheButton);
		 * waterGame.measuresPanel.add(hp3);
		 * waterGame.measuresPanel.add(waterGame.naturkatastropheBeschreibung);
		 */

	}
}
