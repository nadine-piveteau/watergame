package ch.watergame.client;

import java.util.ArrayList;

import ch.watergame.shared.GameField;
import ch.watergame.shared.GameField.FieldType;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;

public class MyStartButtonHandler implements ClickHandler {
	private WaterGame waterGame;
	private GreetingServiceAsync greetingService;
	private Grid gridSiedlung = new Grid(1, 3);
	private Grid gridLW;
	private Grid gridIndustrie;
	private Grid gridBildung = new Grid(3,3);


	public MyStartButtonHandler(GreetingServiceAsync greetingService, WaterGame waterGame) {
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		greetingService.startGame(new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("Failed");
			}

			public void onSuccess(String result) {
				waterGame.setplayerID(Integer.parseInt(result.substring(0, 1)));
				DialogBox waitingBox = new DialogBox();
				waitingBox.setText(result.substring(1));
				RootPanel.get("waitingBoxContainer").add(waitingBox);
				greetingService.getGameField(waterGame.getPlayerID(), new AsyncCallback<GameField>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Gamefield not created");
					}

					@Override
					public void onSuccess(GameField result) {
						for (int row = 0; row < 15; row++) {
							for (int col = 0; col < 15; col++) {
								GridClickHandler gridclickhandler = new GridClickHandler(greetingService, waterGame, row, col);
								if (result.getGameField()[row][col] == FieldType.RICE) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("rizeFieldbio.jpeg", greetingService, waterGame, row, col);
									Image image = new Image("rizeField.jpeg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.TEE) {
									System.out.println("Upgrade for Rizefield now possible");
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("teebio.jpeg", greetingService, waterGame, row, col);
									Image image = new Image("tee.jpeg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.ZUCKER) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("zuckerbio.jpg", greetingService, waterGame, row, col);
									Image image = new Image("zucker.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.FISCH) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("fischbio.jpg", greetingService, waterGame, row, col);
									Image image = new Image("fisch.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.TEXTIL) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("textilbio.jpg", greetingService, waterGame, row, col);
									Image image = new Image("textil.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.LEDER) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("lederbio.jpg", greetingService, waterGame, row, col);
									Image image = new Image("leder.JPG");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.IT) {
									UpgradeClickHandler upgradeclickhandler = new UpgradeClickHandler("itbio.jpg", greetingService, waterGame, row, col);
									Image image = new Image("it.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(upgradeclickhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.SIEDLUNG) {
									GridClickHandler gridhandler = new GridClickHandler(greetingService, waterGame, row, col);
									Image image = new Image("siedlung.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(gridhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.UNTERSTUFE) {
									GridClickHandler gridhandler = new GridClickHandler(greetingService, waterGame, row, col);
									Image image = new Image("unterstufe.jpeg");
									image.setSize("40px", "40px");
									image.addClickHandler(gridhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.OBERSTUFE) {
									GridClickHandler gridhandler = new GridClickHandler(greetingService, waterGame, row, col);
									Image image = new Image("oberstufe.jpeg");
									image.setSize("40px", "40px");
									image.addClickHandler(gridhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.UNI) {
									GridClickHandler gridhandler = new GridClickHandler(greetingService, waterGame, row, col);
									Image image = new Image("uni.jpg");
									image.setSize("40px", "40px");
									image.addClickHandler(gridhandler);
									waterGame.grid.setWidget(row, col, image);
								}
								//nicht veränderbar.
								if (result.getGameField()[row][col] == FieldType.TEMPEL) {
									Image image = new Image("tempel.jpg");
									image.setSize("40px", "40px");
									waterGame.grid.setWidget(row, col, image);
								}
								if (result.getGameField()[row][col] == FieldType.NATIONALPARK) {
									Image image = new Image("transparent_graphic.png");
									image.setSize("40px", "40px");
									waterGame.grid.setWidget(row, col, image);
								}
							}
						}
					}

				});

				greetingService.getRessource(waterGame.getPlayerID(), new AsyncCallback<ArrayList<Integer>>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("ressources not arrived");
					}

					@Override
					public void onSuccess(ArrayList<Integer> result) {

						// Store the values of the ArrayList into integer
						int wirtschaftsIndicatorInt = result.get(0);
						int lebensqualiIndicatorInt = result.get(1);
						int umweltIndicatorInt = result.get(2);
						int populationInt = result.get(3);
						int budgetInt = result.get(4);

						// landwirtschaft
						int rizeValueInt = result.get(5);
						int rizeNeededInt = populationInt / waterGame.grundbedarfProKopfLW;// Grundbedarf
						int theValueInt = result.get(6);
						int theNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
						int sugarValueInt = result.get(7);
						int sugarNeededInt = populationInt / waterGame.grundbedarfProKopfLW;// Grundbedarf
						int fishValueInt = result.get(8);
						int fishNeededInt = populationInt / waterGame.grundbedarfProKopfLW;// Grundbedarf
						// industrie
						int lederValueInt = result.get(9);
						int lederNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
						int textilValueInt = result.get(10);
						int textilNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
						int itValueInt = result.get(11);
						int itNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
						int knowHow = result.get(12);

						// add the information of the Arraylist to the
						// corresponding panel
						waterGame.wirtschaftIndicatorValue = Integer.toString(wirtschaftsIndicatorInt);
						waterGame.lebensqualiIndicatorValue = Integer.toString(lebensqualiIndicatorInt);
						waterGame.umweltIndicatorValue = Integer.toString(umweltIndicatorInt);
						waterGame.rizeValue = new Label("Reis: \t" + Integer.toString(rizeValueInt) + "/" + Integer.toString(rizeNeededInt));
						waterGame.fishValue = new Label("Fisch: \t" + Integer.toString(fishValueInt) + "/" + Integer.toString(fishNeededInt));
						waterGame.sugarValue = new Label("Zucker: \t" + Integer.toString(sugarValueInt) + "/" + Integer.toString(sugarNeededInt));
						waterGame.teaValue = new Label("Tee: \t" + Integer.toString(theValueInt) + "/" + Integer.toString(theNeededInt));
						waterGame.lederValue = new Label("Leder: \t" + Integer.toString(lederValueInt) + "/" + Integer.toString(lederNeededInt));
						waterGame.textilValue = new Label("Textilien: \t" + Integer.toString(textilValueInt) + "/"
								+ Integer.toString(textilNeededInt));
						waterGame.itValue = new Label("IT: \t" + Integer.toString(itValueInt) + "/" + Integer.toString(itNeededInt));
						waterGame.knowhowValue = new Label("Wissen: \t" + Integer.toString(knowHow));

						waterGame.populationValue = new Label(Integer.toString(populationInt));
						HTML wirtschaftHTML = new HTML("<progress align=\"center\" value=\"" + waterGame.wirtschaftIndicatorValue + "\" max=\"100\">"
								+ waterGame.wirtschaftIndicatorValue + "% </progress>");
						waterGame.wirtschaftsKraftPanel.add(wirtschaftHTML);
						waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(wirtschaftHTML, HasHorizontalAlignment.ALIGN_CENTER);
						HTML lebensQualiHTML = new HTML("<progress value=\"" + waterGame.lebensqualiIndicatorValue + "\" max=\"100\">"
								+ waterGame.lebensqualiIndicatorValue + "% </progress>");
						waterGame.lebensQualitaetPanel.add(lebensQualiHTML);
						waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(lebensQualiHTML, HasHorizontalAlignment.ALIGN_CENTER);
						HTML umweltHTML = new HTML("<progress value=\"" + waterGame.umweltIndicatorValue + "\" max=\"100\">"
								+ waterGame.umweltIndicatorValue + "% </progress>");
						waterGame.umweltFreundlichkeitPanel.add(umweltHTML);
						waterGame.umweltFreundlichkeitPanel.setCellHorizontalAlignment(umweltHTML, HasHorizontalAlignment.ALIGN_CENTER);

						waterGame.budgetValue = new Label(Integer.toString(budgetInt));

						waterGame.ressourcePanel.setSpacing(0);
						waterGame.ressourcePanel.add(waterGame.rizeValue);
						waterGame.ressourcePanel.add(waterGame.fishValue);
						waterGame.ressourcePanel.add(waterGame.sugarValue);
						waterGame.ressourcePanel.add(waterGame.teaValue);
						waterGame.ressourcePanel.add(waterGame.lederValue);
						waterGame.ressourcePanel.add(waterGame.textilValue);
						waterGame.ressourcePanel.add(waterGame.itValue);
						waterGame.ressourcePanel.add(waterGame.knowhowValue);
						waterGame.ressourcePanel.setCellHorizontalAlignment(waterGame.budgetValue, HasHorizontalAlignment.ALIGN_LEFT);
						

						waterGame.tradePanel.add(new Label("Trade"));
						HorizontalPanel smallTradeFieldPanel1 = new HorizontalPanel();
						HorizontalPanel smallTradeFieldPanel2 = new HorizontalPanel();
						HorizontalPanel smallTradeFieldPanel3 = new HorizontalPanel();
						HorizontalPanel smallTradeFieldPanel4 = new HorizontalPanel();
						waterGame.importAmountText1 = new TextBox();
						waterGame.importAmountText1.setSize("65px", "15px");
						waterGame.importList1 = new ListBox();
						setListBox(waterGame.importList1);
						waterGame.exportAmountText1 = new TextBox();
						waterGame.exportAmountText1.setSize("65px", "15px");
						waterGame.exportList1 = new ListBox();
						setListBox(waterGame.exportList1);
						smallTradeFieldPanel1.add(waterGame.importAmountText1);
						smallTradeFieldPanel1.add(waterGame.importList1);
						smallTradeFieldPanel1.add(waterGame.exportAmountText1);
						smallTradeFieldPanel1.add(waterGame.exportList1);
						waterGame.importAmountText2 = new TextBox();
						waterGame.importAmountText2.setSize("65px", "15px");
						waterGame.importList2 = new ListBox();
						setListBox(waterGame.importList2);
						waterGame.exportAmountText2 = new TextBox();
						waterGame.exportAmountText2.setSize("65px", "15px");
						waterGame.exportList2 = new ListBox();
						setListBox(waterGame.exportList2);
						smallTradeFieldPanel2.add(waterGame.importAmountText2);
						smallTradeFieldPanel2.add(waterGame.importList2);
						smallTradeFieldPanel2.add(waterGame.exportAmountText2);
						smallTradeFieldPanel2.add(waterGame.exportList2);
						waterGame.importAmountText3 = new TextBox();
						waterGame.importAmountText3.setSize("65px", "15px");
						waterGame.importList3 = new ListBox();
						setListBox(waterGame.importList3);
						waterGame.exportAmountText3 = new TextBox();
						waterGame.exportAmountText3.setSize("65px", "15px");
						waterGame.exportList3 = new ListBox();
						setListBox(waterGame.exportList3);
						smallTradeFieldPanel3.add(waterGame.importAmountText3);
						smallTradeFieldPanel3.add(waterGame.importList3);
						smallTradeFieldPanel3.add(waterGame.exportAmountText3);
						smallTradeFieldPanel3.add(waterGame.exportList3);
						waterGame.importAmountText4 = new TextBox();
						waterGame.importAmountText4.setSize("65px", "15px");
						waterGame.importList4 = new ListBox();
						setListBox(waterGame.importList4);
						waterGame.exportAmountText4 = new TextBox();
						waterGame.exportAmountText4.setSize("65px", "15px");
						waterGame.exportList4 = new ListBox();
						setListBox(waterGame.exportList4);
						smallTradeFieldPanel4.add(waterGame.importAmountText4);
						smallTradeFieldPanel4.add(waterGame.importList4);
						smallTradeFieldPanel4.add(waterGame.exportAmountText4);
						smallTradeFieldPanel4.add(waterGame.exportList4);
						if (waterGame.getPlayerID() == 1) {
							waterGame.tradePanel.add(waterGame.tradePlayer2);
							waterGame.tradePanel.add(smallTradeFieldPanel2);
							waterGame.tradePanel.add(waterGame.tradePlayer3);
							waterGame.tradePanel.add(smallTradeFieldPanel3);
							waterGame.tradePanel.add(waterGame.tradePlayer4);
							waterGame.tradePanel.add(smallTradeFieldPanel4);
						} else if (waterGame.getPlayerID() == 2) {
							waterGame.tradePanel.add(waterGame.tradePlayer1);
							waterGame.tradePanel.add(smallTradeFieldPanel1);
							waterGame.tradePanel.add(waterGame.tradePlayer3);
							waterGame.tradePanel.add(smallTradeFieldPanel3);
							waterGame.tradePanel.add(waterGame.tradePlayer4);
							waterGame.tradePanel.add(smallTradeFieldPanel4);
						} else if (waterGame.getPlayerID() == 3) {
							waterGame.tradePanel.add(waterGame.tradePlayer1);
							waterGame.tradePanel.add(smallTradeFieldPanel1);
							waterGame.tradePanel.add(waterGame.tradePlayer2);
							waterGame.tradePanel.add(smallTradeFieldPanel2);
							waterGame.tradePanel.add(waterGame.tradePlayer4);
							waterGame.tradePanel.add(smallTradeFieldPanel4);
						} else if (waterGame.getPlayerID() == 4) {
							waterGame.tradePanel.add(waterGame.tradePlayer1);
							waterGame.tradePanel.add(smallTradeFieldPanel1);
							waterGame.tradePanel.add(waterGame.tradePlayer2);
							waterGame.tradePanel.add(smallTradeFieldPanel2);
							waterGame.tradePanel.add(waterGame.tradePlayer3);
							waterGame.tradePanel.add(smallTradeFieldPanel3);
						}

						waterGame.ressourceTradePanel.add(waterGame.ressourcePanel);
						waterGame.ressourceTradePanel.add(waterGame.tradePanel);

						waterGame.budgetPanel.add(waterGame.budgetValue);
						waterGame.budgetPanel.setCellHorizontalAlignment(waterGame.budgetValue, HasHorizontalAlignment.ALIGN_CENTER);
						waterGame.populationPanel.add(waterGame.populationValue);
						waterGame.populationPanel.setCellHorizontalAlignment(waterGame.populationValue, HasHorizontalAlignment.ALIGN_CENTER);
						
						StackLayoutPanel tp = new StackLayoutPanel(Unit.PX );



						// Add icons to FliedschoiceList and provide function to
						if (waterGame.playerID == 1) {
							setIconSIEDLUNG();
							setIconTEE();
							setIconTEEBIO();
							setIconRICE();
							setIconRICEBIO();
							setIconsBildung();
							gridLW = new Grid(4,3);
							gridLW.setWidget(0, 0, waterGame.rizeImage);
							gridLW.setWidget(0, 1, new HTML("<b>Reis:<b>"));
							gridLW.setWidget(0,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(1, 0, waterGame.rizeBioImage);
							gridLW.setWidget(1, 1, new HTML("<b>Bio-Reis:<b>"));
							gridLW.setWidget(1,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(2, 0, waterGame.teeImage);
							gridLW.setWidget(2, 1, new HTML("<b>Tee:<b>"));
							gridLW.setWidget(2,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(3, 0, waterGame.teeBioImage);
							gridLW.setWidget(3, 1, new HTML("<b>Bio-Tee:<b>"));
							gridLW.setWidget(3,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridIndustrie = new Grid(1,3);
							gridIndustrie.setWidget(0, 0, new HTML("	Kein Industriezweig vorhanden.	"));
								
						}
						if (waterGame.playerID == 2) {
							setIconSIEDLUNG();
							setIconLEDER();
							setIconZUCKER();
							setIconZUCKEREBIO();
							setIconsBildung();
							gridLW = new Grid(2,3);
							gridLW.setWidget(0, 0, waterGame.zuckerImage);
							gridLW.setWidget(0, 1, new HTML("<b>Zucker:<b>"));
							gridLW.setWidget(0,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(1, 0, waterGame.zuckerBioImage);
							gridLW.setWidget(1, 1, new HTML("<b>Bio-Zucker:<b>"));
							gridLW.setWidget(1,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));

							gridIndustrie = new Grid(1,3);
							gridIndustrie.setWidget(0, 0, waterGame.lederImage);
							gridIndustrie.setWidget(0, 1, new HTML("<b>Leder-gerberei:<b>"));
							gridIndustrie.setWidget(0, 2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							
						}
						if (waterGame.playerID == 3) {
							setIconSIEDLUNG();
							setIconTEE();
							setIconTEEBIO();
							setIconRICE();
							setIconRICEBIO();
							setIconTEXTIL();
							setIconsBildung();
							gridLW = new Grid(4,3);
							gridLW.setWidget(0, 0, waterGame.rizeImage);
							gridLW.setWidget(0, 1, new HTML("<b>Reis:<b>"));
							gridLW.setWidget(0,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(1, 0, waterGame.rizeBioImage);
							gridLW.setWidget(1, 1, new HTML("<b>Bio-Reis:<b>"));
							gridLW.setWidget(1,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(2, 0, waterGame.teeImage);
							gridLW.setWidget(2, 1, new HTML("<b>Tee:<b>"));
							gridLW.setWidget(2,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridLW.setWidget(3, 0, waterGame.teeBioImage);
							gridLW.setWidget(3, 1, new HTML("<b>Bio-Tee:<b>"));
							gridLW.setWidget(3,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
							gridIndustrie = new Grid(1,3);
							gridIndustrie.setWidget(0, 0, waterGame.textilImage);
							gridIndustrie.setWidget(0, 1, new HTML("<b>Textil-industrie<b>"));
							gridIndustrie.setWidget(0,1,new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
						}
						if (waterGame.playerID == 4) {
							setIconSIEDLUNG();
							setIconFISCH();
							setIconIT();
							setIconsBildung();
							gridLW = new Grid(1,3);
							gridLW.setWidget(0, 0, waterGame.fischImage);
							gridLW.setWidget(0, 1, new HTML("<b>Fisch:<b>"));
							gridLW.setWidget(0,2, new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));						
							gridIndustrie = new Grid(1,3);
							gridIndustrie.setWidget(0, 0, waterGame.itImage);
							gridIndustrie.setWidget(0, 1, new HTML("<b>IT<b>"));
							gridIndustrie.setWidget(0,1,new HTML("<small>Preis: XXXXX <br>Ertrag pro Runde: XXXXX<br>Wirtschaftskrafe: XXXXX<br>Lebensqualität: XXXXXX<br>Umweltfreundlichkeit: XXXX<small>"));
						
						}
						// fill in fieldsPanel
						// Landwirtschaft
						//waterGame.fieldsPanel.add(new HTML("<h3>Landwirtschaft<h3>"));
						//HorizontalPanel lwPanel = new HorizontalPanel();
						/*lwPanel.add(waterGame.rizeImage);
						lwPanel.add(waterGame.rizeBioImage);
						lwPanel.add(waterGame.teeImage);
						lwPanel.add(waterGame.teeBioImage);
						lwPanel.add(waterGame.zuckerImage);
						lwPanel.add(waterGame.fischImage);*/
						gridLW.setWidth("300px");
						gridIndustrie.setWidth("300px");
						gridSiedlung.setWidth("300px");
						gridBildung.setWidth("300px");

						tp.add(gridLW, "Landwirtschaft", 40);
						tp.add(gridIndustrie, "Industrie", 40);
						tp.add(gridSiedlung, "Siedlung", 40);
						tp.add(gridBildung, "Bildung", 40);
						tp.setWidth("300px");
						tp.setHeight("600px");
						waterGame.fieldsPanel.add(tp);
						waterGame.fieldsPanel.setCellVerticalAlignment(tp, HasVerticalAlignment.ALIGN_TOP);
						waterGame.fieldsPanel.setCellHorizontalAlignment(tp,HasHorizontalAlignment.ALIGN_CENTER);

						
						//waterGame.fieldsPanel.setCellVerticalAlignment(tp, HasVerticalAlignment.ALIGN_TOP);
						//lwPanel.add(gridLW);
						//waterGame.fieldsPanel.add(lwPanel);
/*
						// industrie
						waterGame.fieldsPanel.add(new HTML("<h3>Industrie<h3>"));
						HorizontalPanel IndPanel = new HorizontalPanel();
						IndPanel.add(gridIndustrie);
						/*IndPanel.add(waterGame.textilImage);
						IndPanel.add(waterGame.lederImage);
						IndPanel.add(waterGame.itImage);
						waterGame.fieldsPanel.add(IndPanel);

						// siedlung
						waterGame.fieldsPanel.add(new HTML("<h3>Siedlung<h3>"));
						HorizontalPanel SiedlungPanel = new HorizontalPanel();
						//SiedlungPanel.add(waterGame.siedlungImage);
						SiedlungPanel.add(gridSiedlung);
						waterGame.fieldsPanel.add(SiedlungPanel);

						// bildung
						waterGame.fieldsPanel.add(new HTML("<h3>Bildung<h3>"));
						HorizontalPanel BildungPanel = new HorizontalPanel();
						BildungPanel.add(gridBildung);
						/*BildungPanel.add(waterGame.unterStufeImage);
						BildungPanel.add(waterGame.oberStufeImage);
						BildungPanel.add(waterGame.uniImage);
						waterGame.fieldsPanel.add(BildungPanel);
						*/
						
						// We can add style names to widgets
						waterGame.startButton.addStyleName("startButton");
						waterGame.instructionButton.addStyleName("startButton");
						waterGame.clientCheckButton.addStyleName("startButton");
						waterGame.validateButton.addStyleName("startButton");
						waterGame.wirtschaftsKraftPanel.addStyleName("panel");
						waterGame.lebensQualitaetPanel.addStyleName("panel");
						waterGame.umweltFreundlichkeitPanel.addStyleName("panel");
						waterGame.populationPanel.addStyleName("panel");
						waterGame.budgetPanel.addStyleName("panel");
						waterGame.ressourcePanel.addStyleName("panel");
						waterGame.gamegridPanel.addStyleName("gridpanel");
						waterGame.fieldsPanel.addStyleName("panel");
						waterGame.tradePanel.addStyleName("panel");

					}

				});

			}
		});
		waterGame.t = new StartTimer(greetingService, waterGame);
		waterGame.t.scheduleRepeating(500);
	}

	private void setIconRICE() {
		String rizeImageString = "rizeField.jpeg";
		waterGame.rizeImage.setUrl(rizeImageString);
		waterGame.rizeImage.setSize("50px", "50px");
		IconHandler iconhandlerRIZE = new IconHandler(greetingService, waterGame, rizeImageString, waterGame.rizePrice, waterGame.removeRizePrice, 0);
		waterGame.rizeImage.addClickHandler(iconhandlerRIZE);
	}
	
	private void setIconRICEBIO() {
		String riceBioImageString = "rizeFieldbio.jpeg";
		waterGame.rizeBioImage.setUrl(riceBioImageString);
		waterGame.rizeBioImage.setSize("50px", "50px");
		IconHandler iconhandlerRICEBIO = new IconHandler(greetingService, waterGame, riceBioImageString, waterGame.rizeBioPrice,
				waterGame.removeRizeBioPrice, 100);
		waterGame.rizeBioImage.addClickHandler(iconhandlerRICEBIO);
	}

	private void setIconTEE() {
		String teeImageString = "tee.jpeg";
		waterGame.teeImage.setUrl(teeImageString);
		waterGame.teeImage.setSize("50px", "50px");
		IconHandler iconhandlerTEE = new IconHandler(greetingService, waterGame, teeImageString, waterGame.teePrice, waterGame.teePriceRemove, 0);
		waterGame.teeImage.addClickHandler(iconhandlerTEE);
	}

	private void setIconTEEBIO() {
		String teeBioImageString = "teebio.jpeg";
		waterGame.teeBioImage.setUrl(teeBioImageString);
		waterGame.teeBioImage.setSize("50px", "50px");
		IconHandler iconhandlerTEEBIO = new IconHandler(greetingService, waterGame, teeBioImageString, waterGame.teeBioPrice,
				waterGame.teeBioPriceRemove, 100);
		waterGame.teeBioImage.addClickHandler(iconhandlerTEEBIO);
	}

	private void setIconZUCKER() {
		String zuckerImageString = "zucker.jpg";
		waterGame.zuckerImage.setUrl(zuckerImageString);
		waterGame.zuckerImage.setSize("50px", "50px");
		IconHandler iconhandlerZUCKER = new IconHandler(greetingService, waterGame, zuckerImageString, waterGame.zuckrePrice,
				waterGame.zuckerPriceRemove, 0);
		waterGame.zuckerImage.addClickHandler(iconhandlerZUCKER);
	}
	private void setIconZUCKEREBIO() {
		String zuckerBioImageString = "zuckerbio.jpg";
		waterGame.zuckerBioImage.setUrl(zuckerBioImageString);
		waterGame.zuckerBioImage.setSize("50px", "50px");
		System.out.println("Zuckerbio BEFORE");
		IconHandler iconhandlerZUCKEREBIO = new IconHandler(greetingService, waterGame, zuckerBioImageString, waterGame.zuckreBioPrice,
				waterGame.zuckerBioPriceRemove, 100);
		System.out.println("Zuckerbio AFTER");

		waterGame.zuckerBioImage.addClickHandler(iconhandlerZUCKEREBIO);
	}
	

	private void setIconFISCH() {
		String fischImageString = "fisch.jpg";
		waterGame.fischImage.setUrl(fischImageString);
		waterGame.fischImage.setSize("50px", "50px");
		IconHandler iconhandlerFISCH = new IconHandler(greetingService, waterGame, fischImageString, waterGame.fischPrice, waterGame.fischPriceRemove, 0);
		waterGame.fischImage.addClickHandler(iconhandlerFISCH);
	}

	private void setIconTEXTIL() {
		String textilImageString = "textil.JPG";
		waterGame.textilImage.setUrl(textilImageString);
		waterGame.textilImage.setSize("50px", "50px");
		IconHandler iconhandlerTEXTIL = new IconHandler(greetingService, waterGame, textilImageString, waterGame.textilPrice,
				waterGame.textilPriceRemove, 0);
		waterGame.textilImage.addClickHandler(iconhandlerTEXTIL);
	}

	private void setIconLEDER() {
		String lederImageString = "leder.JPG";
		waterGame.lederImage.setUrl(lederImageString);
		waterGame.lederImage.setSize("50px", "50px");
		IconHandler iconhandlerLEDER = new IconHandler(greetingService, waterGame, lederImageString, waterGame.lederPrice, waterGame.lederPriceRemove, 0);
		waterGame.lederImage.addClickHandler(iconhandlerLEDER);
	}

	private void setIconIT() {
		String itImageString = "it.jpg";
		waterGame.itImage.setUrl(itImageString);
		waterGame.itImage.setSize("50px", "50px");
		IconHandler iconhandlerIT = new IconHandler(greetingService, waterGame, itImageString, waterGame.itPrice, waterGame.itPriceRemove, 0);
		waterGame.itImage.addClickHandler(iconhandlerIT);
	}

	private void setIconSIEDLUNG() {
		String siedlungImageString = "siedlung.jpg";
		waterGame.siedlungImage.setUrl(siedlungImageString);
		waterGame.siedlungImage.setSize("50px", "50px");
		IconHandler iconhandlerSIEDLUNG = new IconHandler(greetingService, waterGame, siedlungImageString, waterGame.siedlungPrice,
				waterGame.siedlungPriceRemove, 0);
		waterGame.siedlungImage.addClickHandler(iconhandlerSIEDLUNG);
		gridSiedlung.setWidget(0, 0, waterGame.siedlungImage);
		gridSiedlung.setWidget(0, 1, new HTML("<b>Siedlung:<b>"));
		gridSiedlung.setWidget(0,2, new HTML("<small>Die Bevölkerungzahl der Stadt steigt um 100 Einwohner.<small>"));
	}

	private void setIconUNTERSTUFE() {
		String unterStufeImageString = "unterstufe.jpeg";
		waterGame.unterStufeImage.setUrl(unterStufeImageString);
		waterGame.unterStufeImage.setSize("50px", "50px");
		IconHandler iconhandlerUNTERSTUFE = new IconHandler(greetingService, waterGame, unterStufeImageString, waterGame.unterStufePrice,
				waterGame.unterStufePriceRemove, 10);
		waterGame.unterStufeImage.addClickHandler(iconhandlerUNTERSTUFE);
	}

	private void setIconOBREERSTUFE() {
		String oberStufeImageString = "oberstufe.jpeg";
		waterGame.oberStufeImage.setUrl(oberStufeImageString);
		waterGame.oberStufeImage.setSize("50px", "50px");
		IconHandler iconhandlerOBERSTUFE = new IconHandler(greetingService, waterGame, oberStufeImageString, waterGame.oberStufePrice,
				waterGame.oberStufeRemove, 50);
		waterGame.oberStufeImage.addClickHandler(iconhandlerOBERSTUFE);
		
	}
	
	private void setIconsBildung(){
		setIconUNTERSTUFE();
		setIconOBREERSTUFE();
		setIconUNI();
		gridBildung.setWidget(0, 0, waterGame.unterStufeImage);
		gridBildung.setWidget(0, 1, new HTML("<b>Unterstufe:<b>"));
		gridBildung.setWidget(0,2, new HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));	
		gridBildung.setWidget(1, 0, waterGame.oberStufeImage);
		gridBildung.setWidget(1, 1, new HTML("<b>Oberstufe:<b>"));
		gridBildung.setWidget(1,2, new HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));	
		gridBildung.setWidget(2, 0, waterGame.uniImage);
		gridBildung.setWidget(2, 1, new HTML("<b>Universität:<b>"));
		gridBildung.setWidget(2,2, new HTML("<small>Wissen: XXXXX<br>Lebensqualität: XXXX<small>"));
	}

	private void setIconUNI() {
		String uniImageString = "uni.jpg";
		waterGame.uniImage.setUrl(uniImageString);
		waterGame.uniImage.setSize("50px", "50px");
		IconHandler iconhandlerUNI = new IconHandler(greetingService, waterGame, uniImageString, waterGame.uniPrice, waterGame.uniPriceRemove, 100);
		waterGame.uniImage.addClickHandler(iconhandlerUNI);
	}

	private void setListBox(ListBox listbox) {
		listbox.addItem("Reis");
		listbox.addItem("Tee");
		listbox.addItem("Zucker");
		listbox.addItem("Fisch");
		listbox.addItem("Leder");
		listbox.addItem("Textilien");
		listbox.addItem("IT");
		listbox.addItem("Wissen");
	}
}
