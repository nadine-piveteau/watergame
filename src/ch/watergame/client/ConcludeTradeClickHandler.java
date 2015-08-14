package ch.watergame.client;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ch.watergame.shared.Trade;
import ch.watergame.shared.TradeResult;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class ConcludeTradeClickHandler implements ClickHandler {
	WaterGame waterGame;
	GreetingServiceAsync greetingService;
	Queue<Trade> tradeList = new LinkedList<Trade>();
	Queue<CheckBox> selectedCheckboxes = new LinkedList<CheckBox>();
	boolean checkTrade1= false;
	boolean checkTrade2= false;
	boolean checkTrade3= false;
	boolean checkTrade4= false;
	//Landwirtschaft	
	int rize;
	int the;
	int fish;
	int sugar;
	int technology;
	int knowhow;
	
	//industrie
	int leder;
	int textil;
	int it; 
	
	
	public ConcludeTradeClickHandler(WaterGame waterGame, 	GreetingServiceAsync greetingService) {
		super();
		this.waterGame = waterGame;
		this.greetingService = greetingService;
	}

	public void copyRessources(){
		this.rize = waterGame.rizeValueInteger;
		this.the = waterGame.teaValueInteger;
		this.fish = waterGame.fishValueInteger;
		this.sugar = waterGame.sugarValueInteger;
		this.leder = waterGame.lederValueInteger;
		this.textil = waterGame.textilValueInteger;
		this.it = waterGame.itValueInteger;
		this.knowhow = waterGame.knowhowValueInteger;
	}

	@Override
	public void onClick(ClickEvent event) {
		copyRessources();
		/*checkTrade1 = checkTrade1();
		System.out.println("CheckTrade 1: "+checkTrade1);
		checkTrade2 = checkTrade2();
		System.out.println("CheckTrade 2: "+checkTrade2);
		checkTrade3 = checkTrade3();
		System.out.println("CheckTrade 3: "+checkTrade3);
		checkTrade4 = checkTrade4();
		System.out.println("CheckTrade 4: "+checkTrade4);*/
		
		//if(!(checkTrade1&&checkTrade2&&checkTrade3&&checkTrade4)){
		System.out.println("Nr of Trades to accept: "+ selectedCheckboxes.size() + " / "+ tradeList.size() );
		
		if(!checkTrades()){
			Window.alert("Trade not possible. You don't have enough ressources.");
			copyRessources();
			for(CheckBox checkbox: selectedCheckboxes){
				checkbox.setValue(false);
			}
			this.selectedCheckboxes.clear();
			this.tradeList.clear();
			return;
		}
		
		for (Trade tradeToExecute:tradeList){
		greetingService.executeTradeContract(tradeToExecute, new AsyncCallback<Void>(){

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
		
		greetingService.isMyTurn(new AsyncCallback<TradeResult>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(TradeResult result) {
				if (result.myTurn == true) {
					greetingService.removeALLTradeContract(new AsyncCallback<Void>() {

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
			}
		});
		
			
		
		RootPanel.get("gamefield").setVisible(true);
		RootPanel.get("validateButtonContainer").setVisible(true);
		RootPanel.get("tradeContainer").setVisible(false);
		waterGame.tradeBox.clear();
		waterGame.tradeRessourceBox.clear();
		RootPanel.get("tradeContainer").clear();
		
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

	public boolean checkTrade1(){
		System.out.println("ImportAMountText1"+ waterGame.importAmountText1.getText());
		System.out.println("Import Good: "+ waterGame.importList1.getValue(waterGame.importList1.getSelectedIndex()));
		System.out.println("Empty check: "+(waterGame.importAmountText1.getText().equals("")));
		if(!waterGame.importAmountText1.getText().equals("")){
			int importAmountRessource = Integer.parseInt(waterGame.importAmountText1.getText());
			String importAmountString = waterGame.importList1.getValue(waterGame.importList1.getSelectedIndex());

		if(importAmountString.contains("Reis")){
			System.out.println("import: REIS");
			if((this.rize-importAmountRessource)>=0){
				this.rize = this.rize-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Tee")){
			System.out.println("Import: TEE");
			if((this.the-importAmountRessource)>=0){
				this.the = this.the-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Fisch")){
			System.out.println("Import: FISCH");
			if((this.fish-importAmountRessource)>=0){
				this.fish = this.fish-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Zucker")){
			System.out.println("Import: ZUCKER");
			if((this.sugar-importAmountRessource)>=0){
				this.sugar = this.sugar-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Leder")){
			System.out.println("Import: LEDER");
			if((this.leder-importAmountRessource)>=0){
				this.leder = this.leder-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Textilien")){
			System.out.println("Import: TEXTILIEN");
			if((this.textil-importAmountRessource)>=0){
				this.textil = this.textil-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("IT")){
			System.out.println("Import: IT");
			if((this.it-importAmountRessource)>=0){
				this.it = this.it-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Wissen")){
			System.out.println("Import: WISSEN");
			if((this.knowhow-importAmountRessource)>=0){
				this.knowhow = this.knowhow-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else{
			System.out.println("No import given.");
			return true;
		}
	}else{
	return true;
	}
	}
	public boolean checkTrade2(){
		if(!waterGame.importAmountText2.getText().equals("")){
			int importAmountRessource = Integer.parseInt(waterGame.importAmountText2.getText());
			String importAmountString = waterGame.importList2.getValue(waterGame.importList2.getSelectedIndex());

		if(importAmountString.contains("Reis")){
			System.out.println("import: REIS");
			
			if((this.rize-importAmountRessource)>=0){
				this.rize = this.rize-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Tee")){
			System.out.println("Import: TEE");
			if((this.the-importAmountRessource)>=0){
				this.the = this.the-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Fisch")){
			System.out.println("Import: FISCH");
			if((this.fish-importAmountRessource)>=0){
				this.fish = this.fish-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Zucker")){
			System.out.println("Import: ZUCKER");
			if((this.sugar-importAmountRessource)>=0){
				this.sugar = this.sugar-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Leder")){
			System.out.println("Import: LEDER");
			if((this.leder-importAmountRessource)>=0){
				this.leder = this.leder-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Textilien")){
			System.out.println("Import: TEXTILIEN");
			if((this.textil-importAmountRessource)>=0){
				this.textil = this.textil-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("IT")){
			System.out.println("Import: IT");
			if((this.it-importAmountRessource)>=0){
				this.it = this.it-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Wissen")){
			System.out.println("Import: WISSEN");
			if((this.knowhow-importAmountRessource)>=0){
				this.knowhow = this.knowhow-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else{
			System.out.println("No import given.");
			return true;
		
		}
		}else{
			return true;
		}
	}	
	
	public boolean checkTrade3(){
		if(!waterGame.importAmountText3.getText().equals("")){
			int importAmountRessource = Integer.parseInt(waterGame.importAmountText3.getText());
			String importAmountString = waterGame.importList3.getValue(waterGame.importList3.getSelectedIndex());

		if(importAmountString.contains("Reis")){
			System.out.println("import: REIS");
			if((this.rize-importAmountRessource)>=0){
				this.rize = this.rize-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Tee")){
			System.out.println("Import: TEE");
			if((this.the-importAmountRessource)>=0){
				this.the = this.the-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Fisch")){
			System.out.println("Import: FISCH");
			if((this.fish-importAmountRessource)>=0){
				this.fish = this.fish-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Zucker")){
			System.out.println("Import: ZUCKER");
			if((this.sugar-importAmountRessource)>=0){
				this.sugar = this.sugar-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Leder")){
			System.out.println("Import: LEDER");
			if((this.leder-importAmountRessource)>=0){
				this.leder = this.leder-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Textilien")){
			System.out.println("Import: TEXTILIEN");
			if((this.textil-importAmountRessource)>=0){
				this.textil = this.textil-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("IT")){
			System.out.println("Import: IT");
			if((this.it-importAmountRessource)>=0){
				this.it = this.it-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Wissen")){
			System.out.println("Import: WISSEN");
			if((this.knowhow-importAmountRessource)>=0){
				this.knowhow = this.knowhow-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else{
			System.out.println("No import given.");
			return true;
		}
	}else{
		return true;
	}
	}

	public boolean checkTrade4(){
		if(!waterGame.importAmountText4.getText().equals("")){
			int importAmountRessource = Integer.parseInt(waterGame.importAmountText4.getText());
			String importAmountString = waterGame.importList4.getValue(waterGame.importList4.getSelectedIndex());
			if(importAmountString.contains("Reis")){
			System.out.println("import: REIS");
			if((this.rize-importAmountRessource)>=0){
				this.rize = this.rize-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Tee")){
			System.out.println("Import: TEE");
			if((this.the-importAmountRessource)>=0){
				this.the = this.the-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Fisch")){
			System.out.println("Import: FISCH");
			if((this.fish-importAmountRessource)>=0){
				this.fish = this.fish-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Zucker")){
			System.out.println("Import: ZUCKER");
			if((this.sugar-importAmountRessource)>=0){
				this.sugar = this.sugar-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else if(importAmountString.contains("Leder")){
			System.out.println("Import: LEDER");
			if((this.leder-importAmountRessource)>=0){
				this.leder = this.leder-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Textilien")){
			System.out.println("Import: TEXTILIEN");
			if((this.textil-importAmountRessource)>=0){
				this.textil = this.textil-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("IT")){
			System.out.println("Import: IT");
			if((this.it-importAmountRessource)>=0){
				this.it = this.it-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}else if(importAmountString.contains("Wissen")){
			System.out.println("Import: WISSEN");
			if((this.knowhow-importAmountRessource)>=0){
				this.knowhow = this.knowhow-importAmountRessource;
				System.out.println("Trade Check : True");
				return true;
			}else {
				System.out.println("Trade Check : False");
				return false;
				}
		}
		else{
			System.out.println("No import given.");
			return true;
		}
		}else{
			return true;
		}
	}
	
	public boolean checkTrades(){
		boolean isChecked = true;
		for (Trade trade: this.tradeList){
				int importAmountRessource = trade.importAmount;
				String importAmountString = trade.importGood;
				if(importAmountString.contains("Reis")){
				System.out.println("import: REIS");
				if((this.rize-importAmountRessource)>=0){
					this.rize = this.rize-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked= true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}else if(importAmountString.contains("Tee")){
				System.out.println("Import: TEE");
				if((this.the-importAmountRessource)>=0){
					this.the = this.the-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}
			else if(importAmountString.contains("Fisch")){
				System.out.println("Import: FISCH");
				if((this.fish-importAmountRessource)>=0){
					this.fish = this.fish-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}
			else if(importAmountString.contains("Zucker")){
				System.out.println("Import: ZUCKER");
				if((this.sugar-importAmountRessource)>=0){
					this.sugar = this.sugar-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}
			else if(importAmountString.contains("Leder")){
				System.out.println("Import: LEDER");
				if((this.leder-importAmountRessource)>=0){
					this.leder = this.leder-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}else if(importAmountString.contains("Textilien")){
				System.out.println("Import: TEXTILIEN");
				if((this.textil-importAmountRessource)>=0){
					this.textil = this.textil-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked= false;
					}
			}else if(importAmountString.contains("IT")){
				System.out.println("Import: IT");
				if((this.it-importAmountRessource)>=0){
					this.it = this.it-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}else if(importAmountString.contains("Wissen")){
				System.out.println("Import: WISSEN");
				if((this.knowhow-importAmountRessource)>=0){
					this.knowhow = this.knowhow-importAmountRessource;
					System.out.println("Trade Check : True");
					isChecked=  true;
				}else {
					System.out.println("Trade Check : False");
					isChecked=  false;
					}
			}
			else{
				System.out.println("No import given.");
				isChecked=  true;
			}
			
			}
		return isChecked;
		}
	


}
