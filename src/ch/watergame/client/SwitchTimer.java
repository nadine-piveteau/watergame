package ch.watergame.client;

import java.util.ArrayList;

import ch.watergame.shared.Trade;
import ch.watergame.shared.TradeResult;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SwitchTimer extends Timer {
	private GreetingServiceAsync greetingService;
	private WaterGame waterGame;

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
			public void onSuccess(TradeResult result) {
				if (result.myTurn == true) {
					
					RootPanel.get("NotYourTurn").setVisible(false);
					cancel();
					
					if(!result.tradeContractResult.isEmpty()){
						
						waterGame.tradeBoxTitle = new Label("Handel");
						waterGame.tradeBoxContent.add(waterGame.tradeBoxTitle);
						for(Trade trade : result.tradeContractResult){
							HorizontalPanel tradeMessagePanel = new HorizontalPanel();
							Label tradeMessage = new Label(trade.toString());
							System.out.println("TradeMessage: "+tradeMessage);
							tradeMessagePanel.add(tradeMessage);
							Button jaButton = new Button("JA");
							Button neinButton = new Button("NEIN");
							JaTradeButtonClickHandler jaHandler = new JaTradeButtonClickHandler(trade, greetingService);
							jaButton.addClickHandler(jaHandler);
							tradeMessagePanel.add(neinButton);
							tradeMessagePanel.add(jaButton);
							waterGame.tradeBoxContent.add(tradeMessagePanel);
						}
						ConcludeTradeClickHandler concludeHandler = new ConcludeTradeClickHandler(waterGame, greetingService);
						Button okButton = new Button("OK");
						okButton.addClickHandler(concludeHandler);
						waterGame.tradeBoxContent.add(okButton);
						waterGame.tradeBox.add(waterGame.tradeBoxContent);
						waterGame.tradeBox.setWidth("800px");
						RootPanel.get("tradeContainer").add(waterGame.tradeBox);
						RootPanel.get("tradeContainer").setVisible(true);
						RootPanel.get("gamefield").setVisible(false);
						RootPanel.get("validateButtonContainer").setVisible(false);
						
					}else{
						
						RootPanel.get("tradeContainer").setVisible(false);
						RootPanel.get("gamefield").setVisible(true);
						RootPanel.get("validateButtonContainer").setVisible(true);
						// refreshAndGetRessources: neue werte updaten und
						// zurückgeben methode hier aufrufen
						greetingService.updateAndGetRessources(new AsyncCallback<ArrayList<Integer>>() {
							
							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onSuccess(ArrayList<Integer> result) {
								int populationInt = result.get(3);
								
								// clear wirtschaft/lebensquali/umwelt/population/resourcen Panel
								clearPanels();
								// set new Values for indicators, ressources, knohow and population
								setIndicatorValue(result);
								setRessourceValueLW(result, populationInt);
								setRessourceValueIndustrie(result, populationInt);
								setKnowHowValue(result);
								setPopulationValue(populationInt);
								setBudgetValue(result.get(4));
								
								
								// fill the panels
								fillWirtschaftskraftPanel(); 
								fillLebensqualiPanel();
								fillUmweltPanel();
								fillPopulationPanel();
								fillRessourcePanel();
								fillBudgetPanel();
								
							}
							
							
						});
					}
				} else {
					RootPanel.get("gamefield").setVisible(false);
					RootPanel.get("validateButtonContainer").setVisible(false);
					RootPanel.get("NotYourTurn").setVisible(true);
				}
				
				
			}
		});
	}

	void clearPanels() {
		waterGame.ressourcePanel.clear();
		waterGame.populationPanel.clear();
		waterGame.lebensQualitaetPanel.clear();
		waterGame.wirtschaftsKraftPanel.clear();
		waterGame.umweltFreundlichkeitPanel.clear();
	}

	void setIndicatorValue(ArrayList<Integer>result) {
		int wirtschaftsIndicatorInt = result.get(0);
		int lebensqualiIndicatorInt = result.get(1);
		int umweltIndicatorInt = result.get(2);
		waterGame.wirtschaftIndicatorValue = Integer.toString(wirtschaftsIndicatorInt);
		waterGame.lebensqualiIndicatorValue = Integer.toString(lebensqualiIndicatorInt);
		waterGame.umweltIndicatorValue = Integer.toString(umweltIndicatorInt);
	}

	void setRessourceValueLW(ArrayList<Integer>result, int populationInt) {
		int rizeValueInt = result.get(5);
		int rizeNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int theValueInt = result.get(6);
		int theNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int sugarValueInt = result.get(7);
		int sugarNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int fishValueInt = result.get(8);
		int fishNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		waterGame.rizeValue = new Label("Reis: \t" + Integer.toString(rizeValueInt) + "/" + Integer.toString(rizeNeededInt));
		waterGame.fishValue = new Label("Fisch: \t" + Integer.toString(fishValueInt) + "/" + Integer.toString(fishNeededInt));
		waterGame.sugarValue = new Label("Zucker: \t" + Integer.toString(sugarValueInt) + "/" + Integer.toString(sugarNeededInt));
		waterGame.teaValue = new Label("Tee: \t" + Integer.toString(theValueInt) + "/" + Integer.toString(theNeededInt));

	}

	void setRessourceValueIndustrie(ArrayList<Integer>result, int populationInt) {
		int lederValueInt = result.get(9);
		int lederNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int textilValueInt = result.get(10);
		int textilNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int itValueInt = result.get(11);
		int itNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int budgetInt = result.get(4);
		waterGame.lederValue = new Label("Leder: \t" + Integer.toString(lederValueInt) + "/" + Integer.toString(lederNeededInt));
		waterGame.textilValue = new Label("Textilien: \t" + Integer.toString(textilValueInt) + "/" + Integer.toString(textilNeededInt));
		waterGame.itValue = new Label("IT: \t" + Integer.toString(itValueInt) + "/" + Integer.toString(itNeededInt));
	}

	void setKnowHowValue(ArrayList<Integer>result) {
		int knowHow = result.get(12);
		waterGame.knowhowValue = new Label("Wissen: \t" + Integer.toString(knowHow));
	}

	void setPopulationValue(int populationInt) {
		waterGame.populationValue = new Label(Integer.toString(populationInt));
		
	}
	
	void setBudgetValue(int budgetInt){
		waterGame.budgetValue = new Label(Integer.toString(budgetInt)); 
	}
	
	void fillBudgetPanel(){
		waterGame.budgetPanel.clear();
		waterGame.budgetPanel.add(waterGame.budgetLabel);
		waterGame.budgetPanel.add(waterGame.budgetValue);
	}

	void fillWirtschaftskraftPanel() {
		HTML wirtschaftHTML = new HTML("<progress align=\"center\" value=\"" + waterGame.wirtschaftIndicatorValue + "\" max=\"100\">"
				+ waterGame.wirtschaftIndicatorValue + "% </progress>");
		waterGame.wirtschaftsKraftPanel.add(waterGame.wirtschaftLabel);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(waterGame.wirtschaftLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(wirtschaftHTML);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(wirtschaftHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(new Label(waterGame.wirtschaftIndicatorValue + " %"));
		;
	}
	
	void fillLebensqualiPanel(){
		HTML lebensQualiHTML = new HTML("<progress value=\"" + waterGame.lebensqualiIndicatorValue + "\" max=\"100\">"
				+ waterGame.lebensqualiIndicatorValue + "% </progress>");
		waterGame.lebensQualitaetPanel.add(waterGame.lebensqualitaetLabel);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(waterGame.lebensqualitaetLabel,
				HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(lebensQualiHTML);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(lebensQualiHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(new Label(waterGame.lebensqualiIndicatorValue + " %"));
		
	}
	void fillUmweltPanel() {
		HTML umweltHTML = new HTML("<progress value=\"" + waterGame.umweltIndicatorValue + "\" max=\"100\">"
				+ waterGame.umweltIndicatorValue + "% </progress>");
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel
		.setCellHorizontalAlignment(waterGame.umweltLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel.add(umweltHTML);
		waterGame.umweltFreundlichkeitPanel.setCellHorizontalAlignment(umweltHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(new Label(waterGame.umweltIndicatorValue + " %"));							
	}
	
	void fillRessourcePanel(){
		waterGame.ressourcePanel.add(waterGame.ressourceTitle);
		waterGame.ressourcePanel.add(waterGame.rizeValue);
		waterGame.ressourcePanel.add(waterGame.fishValue);
		waterGame.ressourcePanel.add(waterGame.sugarValue);
		waterGame.ressourcePanel.add(waterGame.teaValue);
		waterGame.ressourcePanel.add(waterGame.lederValue);
		waterGame.ressourcePanel.add(waterGame.textilValue);
		waterGame.ressourcePanel.add(waterGame.itValue);
		waterGame.ressourcePanel.add(waterGame.knowhowValue);
		waterGame.ressourcePanel.setCellHorizontalAlignment(waterGame.budgetValue, HasHorizontalAlignment.ALIGN_LEFT);
	}
	
	void fillPopulationPanel(){
		waterGame.populationPanel.add(waterGame.populationLabel);
		waterGame.populationPanel.add(waterGame.populationValue);
		waterGame.populationPanel.setCellHorizontalAlignment(waterGame.populationValue, HasHorizontalAlignment.ALIGN_CENTER);
	}
}
