package ch.watergame.client;

import java.util.ArrayList;

import ch.watergame.shared.Trade;
import ch.watergame.shared.TradeResult;

import com.google.appengine.api.files.FileServicePb.ShuffleRequest.Callback;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.msg.Message;
import com.google.gwt.dev.util.msg.Message0;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SwitchTimer extends Timer {
	private GreetingServiceAsync greetingService;
	private WaterGame waterGame;
	private TradeResult traderesult;
	private static final int MAX_ROUNDS = 15;

	public SwitchTimer(GreetingServiceAsync greetingService, WaterGame waterGame) {
		this.greetingService = greetingService;
		this.waterGame = waterGame;
	}

	@Override
	public void run() {
		
		greetingService.isMyTurn(new AsyncCallback<TradeResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(final TradeResult result) {
				
				
				greetingService.getRoundNR(new AsyncCallback<Integer>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Integer numberOfRounds) {
						// TODO Auto-generated method stub
						waterGame.gameRound = numberOfRounds;
						waterGame.roundPanel.clear();
						waterGame.roundCounter = new HTML("Spiel "+waterGame.gameID+", "+numberOfRounds + ". Runde");
						waterGame.roundCounter.addStyleName("roundCounter");
						waterGame.roundCounter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

						waterGame.roundPanel.add(waterGame.roundCounter);
						
						if (numberOfRounds > MAX_ROUNDS){
							RootPanel.get("WIN").setVisible(false);
							RootPanel.get("GAMEOVER").setVisible(true);
							RootPanel.get("sendButtonContainer").setVisible(false);
							RootPanel.get("instructionButtonContainer").setVisible(false);
							RootPanel.get("waitingBoxContainer").setVisible(false);
							RootPanel.get("tradeContainer").setVisible(false);
							RootPanel.get("gamefield").setVisible(false);
							RootPanel.get("validateButtonContainer").setVisible(true);
							RootPanel.get("NotYourTurn").setVisible(false);
							RootPanel.get("validateButtonContainer").clear();
						}else{
							traderesult = result;
							//fillRessourcePanel();
//							System.out.println("SWITCHTIMER RESULT:MYTURN: "+result.myTurn);
							if (traderesult.myTurn == true) {
								fillRessourcePanel();
								waterGame.waitingBox.hide();
								RootPanel.get("NotYourTurn").setVisible(false);
								cancel();
								if (!traderesult.tradeContractResult.isEmpty()) {
									fillRessourcePanel();

									ConcludeTradeClickHandler concludeHandler = new ConcludeTradeClickHandler(
											waterGame, greetingService);
									// waterGame.tradeBox.remove(waterGame.tradeBoxContent);
									RootPanel.get("tradeContainer").clear();
									// waterGame.tradeBox.center();
									// waterGame.tradeBoxTitle = new Label("Handel");
									// waterGame.tradeBoxContent.add(waterGame.tradeBoxTitle);

									waterGame.tradeBox = new DialogBox();
									VerticalPanel tradeBoxContent = new VerticalPanel();
									Label tradeBoxTitel = new Label("Handel");
									tradeBoxContent.add(tradeBoxTitel);
									HTML frage = new HTML("Kreuze jeden Handel, den du annehmen möchtest.");
									tradeBoxContent.add(frage);
									for (Trade trade : traderesult.tradeContractResult) {
										fillRessourcePanel();

										HorizontalPanel tradeMessagePanel = new HorizontalPanel();
										HTML tradeMessage = new HTML(trade.toString());
										tradeMessagePanel.add(tradeMessage);
										CheckBox jaButton = new CheckBox();
										// Button neinButton = new Button("NEIN");
										JaTradeButtonClickHandler jaHandler = new JaTradeButtonClickHandler(
												trade, greetingService, concludeHandler,
												jaButton);
										jaButton.addClickHandler(jaHandler);
										// tradeMessagePanel.add(neinButton);
										tradeMessagePanel.add(jaButton);
										tradeBoxContent.add(tradeMessagePanel);
									}
									greetingService.emptyTradeResult(new AsyncCallback<Void>() {

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub
											
										}

										@Override
										public void onSuccess(Void result) {
											// TODO Auto-generated method stub
											
										}
									});
									Button okButton = new Button("OK");
									okButton.addClickHandler(concludeHandler);
									tradeBoxContent.add(okButton);
									fillRessourcePanel();

									waterGame.tradeBox.add(tradeBoxContent);
									waterGame.tradeBox.center();
									RootPanel.get("gamefield").setVisible(true);
									RootPanel.get("validateButtonContainer").setVisible(
											true);
									RootPanel.get("WIN").setVisible(false);
									RootPanel.get("GAMEOVER").setVisible(false);

								} else {
									waterGame.tradeBox.hide();
									//RootPanel.get("tradeContainer").setVisible(false);
									RootPanel.get("gamefield").setVisible(true);
									RootPanel.get("validateButtonContainer").setVisible(
											true);
									RootPanel.get("WIN").setVisible(false);
									RootPanel.get("GAMEOVER").setVisible(false);
									// Zufallsereigniss hier aufrufen
									System.out.println("BEFORE EVENT EXECUTED");
									greetingService.executeEvent(waterGame.playerID,
											new AsyncCallback<String>() {

												@Override
												public void onFailure(Throwable caught) {
													// TODO Auto-generated method stub

												}

												@Override
												public void onSuccess(String result) {
													// TODO Auto-generated method stub
													if (result != null) {
														System.out.println("Event : "+ result);
														// Window.alert(result);
														DialogBox alertEvent = new DialogBox();
														VerticalPanel contentAlertMessage = new VerticalPanel();
														HTML message = new HTML(result);
														OkEventAlertClickHandler okClickHandler = new OkEventAlertClickHandler(alertEvent);
														Button okAlertMessageButton = new Button("OK");
														okAlertMessageButton.addClickHandler(okClickHandler);
														contentAlertMessage.add(message);
														contentAlertMessage.add(okAlertMessageButton);
														contentAlertMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
														alertEvent.add(contentAlertMessage);
														alertEvent.center();
														alertEvent.show();
													}

													greetingService
															.updateAndGetRessources(new AsyncCallback<ArrayList<Integer>>() {

																@Override
																public void onFailure(
																		Throwable caught) {
																	// TODO Auto-generated
																	// method stub

																}

																@Override
																public void onSuccess(
																		ArrayList<Integer> result) {
																	System.out
																			.println("Event method: "
																					+ result);
																	System.out.println("New Rice amount client side: "
																			+ result.get(5));
																	int populationInt = result
																			.get(3);

																	// clear
																	// wirtschaft/lebensquali/umwelt/population/resourcen
																	// Panel
																	clearPanels();
																	// set new Values for
																	// indicators,
																	// ressources, knohow
																	// and population
																	setIndicatorValue(result);
																	setRessourceValueLW(
																			result,
																			populationInt);
																	setRessourceValueIndustrie(
																			result,
																			populationInt);
																	setKnowHowValue(result);
																	setPopulationValue(populationInt);
																	setBudgetValue(result
																			.get(4));

																	// fill the panels
																	fillWirtschaftskraftPanel();
																	fillLebensqualiPanel();
																	fillUmweltPanel();
																	fillPopulationPanel();
																	fillRessourcePanel();
																	fillBudgetPanel();
																	fillMeasuresPanel();
																	greetingService
																			.getCommonIndicator(new AsyncCallback<Integer>() {

																				@Override
																				public void onFailure(Throwable t) {
																					t.printStackTrace();
																					Window.alert("common indicator failure: " + t.getMessage());
																				}

																				@Override
																				public void onSuccess(Integer result) {
																					if(result < waterGame.WINNINGINDIKATOR){
																					
																					HTML commonIndicatorHTML = new HTML(
																							"<progress value=\""
																									+ result
																									+ "\" max=\"100\">"
																									+ result
																									+ "</progress>");
																					waterGame.commonIndikatorPanel
																							.add(waterGame.commonIndikatorLabel);
																					waterGame.commonIndikatorPanel
																							.add(commonIndicatorHTML);
																					Label percentage = new Label(
																							Integer.toString(result));
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
																					}else{
																						RootPanel.get("WIN").setVisible(true);
																						RootPanel.get("GAMEOVER").setVisible(false);
																						RootPanel.get("sendButtonContainer").setVisible(false);
																						RootPanel.get("instructionButtonContainer").setVisible(false);
																						RootPanel.get("waitingBoxContainer").setVisible(false);
																						RootPanel.get("tradeContainer").setVisible(false);
																						RootPanel.get("gamefield").setVisible(false);
																						RootPanel.get("NotYourTurn").setVisible(false);
																						RootPanel.get("validateButtonContainer").clear();
																					}
																				}
																			});
																}
															});
												}
											});
									// refreshAndGetRessources: neue werte updaten und
									// zurückgeben methode hier aufrufen

								}
									
									
									} else {
								RootPanel.get("gamefield").setVisible(true);
								RootPanel.get("validateButtonContainer").setVisible(true);
								//RootPanel.get("NotYourTurn").setVisible(false);
								waterGame.waitingBox.show();
								// waterGame.startEndTimer();
								
							}
						}
					}

				});
			}
					
		
	});
	
	}

	void clearPanels() {
		waterGame.ressourcePanel.clear();
		waterGame.populationPanel.clear();
		waterGame.lebensQualitaetPanel.clear();
		waterGame.wirtschaftsKraftPanel.clear();
		waterGame.umweltFreundlichkeitPanel.clear();
		waterGame.measuresPanel.clear();
		waterGame.commonIndikatorPanel.clear();
	}

	void setIndicatorValue(ArrayList<Integer> result) {
		int wirtschaftsIndicatorInt = result.get(0);
		int lebensqualiIndicatorInt = result.get(1);
		int umweltIndicatorInt = result.get(2);
		waterGame.wirtschaftIndicatorValue = Integer
				.toString(wirtschaftsIndicatorInt);
		waterGame.lebensqualiIndicatorValue = Integer
				.toString(lebensqualiIndicatorInt);
		waterGame.umweltIndicatorValue = Integer.toString(umweltIndicatorInt);
	}

	void setRessourceValueLW(ArrayList<Integer> result, int populationInt) {
		int rizeValueInt = result.get(5);
		waterGame.rizeNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int theValueInt = result.get(6);
		waterGame.theNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int sugarValueInt = result.get(7);
		waterGame.sugarNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int fishValueInt = result.get(8);
		waterGame.fishNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		waterGame.rizeValue = new HTML(Integer.toString(rizeValueInt) + "/"
				+ Integer.toString(waterGame.rizeNeededInt));
		waterGame.fishValue = new HTML(Integer.toString(fishValueInt) + "/"
				+ Integer.toString(waterGame.fishNeededInt));
		waterGame.sugarValue = new HTML(Integer.toString(sugarValueInt) + "/"
				+ Integer.toString(waterGame.sugarNeededInt));
		waterGame.teaValue = new HTML(Integer.toString(theValueInt) + "/"
				+ Integer.toString(waterGame.theNeededInt));
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
		waterGame.itNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int budgetInt = result.get(4);
		waterGame.lederValue = new HTML( Integer.toString(lederValueInt) + "/"
				+ Integer.toString(waterGame.lederNeededInt));
		waterGame.textilValue = new HTML( Integer.toString(textilValueInt) + "/"
				+ Integer.toString(waterGame.textilNeededInt));
		waterGame.itValue = new HTML(Integer.toString(itValueInt)
				+ "/" + Integer.toString(waterGame.itNeededInt));
		waterGame.lederValueInteger = lederValueInt;
		waterGame.textilValueInteger = lederValueInt;
		waterGame.itValueInteger = itValueInt;
	}

	void setKnowHowValue(ArrayList<Integer> result) {
		int knowHow = result.get(12);
		waterGame.knowhowValue = new HTML(Integer.toString(knowHow));
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
		//Label ressourcenTitel = new Label("Ressourcenstand");
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
		//waterGame.ressourcePanel.add(ressourcenTitel);
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
		measuresTable.setWidget(0, 0, waterGame.umweltSchutzLabel);
		// measuresTable.setWidget(0, 0, waterGame.umweltSchutzBeschreibung);
		measuresTable.setWidget(0, 1, waterGame.umweltSchutzButton);
		measuresTable.setWidget(2, 0, waterGame.reformen);
		// measuresTable.setWidget(2, 0, waterGame.reformenBeschreibung);
		measuresTable.setWidget(2, 1, waterGame.reformenButton);
		measuresTable.setWidget(3, 0, waterGame.naturgefahrenSchutz);
		// measuresTable.setWidget(3, 0,
		// waterGame.naturkatastropheBeschreibung);
		measuresTable.setWidget(3, 1, waterGame.naturkatastropheButton);
		waterGame.measuresPanel.add(waterGame.titleMeasures);
		waterGame.measuresPanel.add(measuresTable);
		

	}
}
