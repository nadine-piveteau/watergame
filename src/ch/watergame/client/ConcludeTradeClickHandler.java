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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

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
			Window.alert("Handel nicht möglich. Du hast nicht genügend Ressourcen.");
			copyRessources();
			for(CheckBox checkbox: selectedCheckboxes){
				checkbox.setValue(false);
			}
			this.selectedCheckboxes.clear();
			this.tradeList.clear();
			return;
		}
		
		for (Trade tradeToExecute:tradeList){
			System.out.println("TRADE");
			System.out.println(tradeToExecute.partnerAID);
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
				waterGame.tradeBox.hide();
				if (result.myTurn == true) {
					greetingService.removeALLTradeContract(new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							RootPanel.get("gamefield").setVisible(true);
							RootPanel.get("validateButtonContainer").setVisible(true);
							//RootPanel.get("tradeContainer").setVisible(false);

							RootPanel.get("WIN").setVisible(false);
							RootPanel.get("GAMEOVER").setVisible(false);


							
							//RootPanel.get("tradeContainer").setVisible(false);
							RootPanel.get("gamefield").setVisible(true);
							RootPanel.get("validateButtonContainer").setVisible(true);
							//Zufallsereigniss hier aufrufen
						
						}
						
					});
				}
			}
		});
		greetingService.executeEvent(waterGame.playerID, new AsyncCallback<String>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				//Window.alert(result);
				if(result!=null){
				DialogBox alertEvent = new DialogBox();
				VerticalPanel contentAlertMessage = new VerticalPanel();
				HTML message = new HTML(result);
				OkEventAlertClickHandler okClickHandler = new OkEventAlertClickHandler(alertEvent);
				Button okAlertMessageButton = new Button("OK");
				okAlertMessageButton.addClickHandler(okClickHandler);
				contentAlertMessage.add(message);;
				contentAlertMessage.add(okAlertMessageButton);
				contentAlertMessage.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				alertEvent.add(contentAlertMessage);
				alertEvent.center();
				alertEvent.show();
				}

				greetingService.updateAndGetRessources(new AsyncCallback<ArrayList<Integer>>() {
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(ArrayList<Integer> result) {
						System.out.println("Event method: "+ result);
						System.out.println("New Rice amount client side: "+result.get(5));
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
						fillMeasuresPanel();
						
						greetingService.getCommonIndicator(new AsyncCallback<Integer>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onSuccess(Integer result) {
								// TODO Auto-generated method stub
								
								HTML commonIndicatorHTML = new HTML("<progress value=\"" +  result + "\" max=\"100\">"
										+  result + "</progress>");
								Label percentage = new Label(Integer.toString(result));
								waterGame.commonIndikatorPanel.clear();
								waterGame.commonIndikatorPanel.add(waterGame.commonIndikatorLabel);
								waterGame.commonIndikatorPanel.add(commonIndicatorHTML);
								waterGame.commonIndikatorPanel.add(percentage);
								waterGame.commonIndikatorPanel.setCellVerticalAlignment(waterGame.commonIndikatorLabel, HasVerticalAlignment.ALIGN_MIDDLE);
								waterGame.commonIndikatorPanel.setCellHorizontalAlignment(waterGame.commonIndikatorLabel, HasHorizontalAlignment.ALIGN_CENTER);
								waterGame.commonIndikatorPanel.setCellVerticalAlignment(commonIndicatorHTML, HasVerticalAlignment.ALIGN_MIDDLE);
								waterGame.commonIndikatorPanel.setCellHorizontalAlignment(commonIndicatorHTML, HasHorizontalAlignment.ALIGN_CENTER);
								waterGame.commonIndikatorPanel.setCellVerticalAlignment(percentage, HasVerticalAlignment.ALIGN_MIDDLE);
								waterGame.commonIndikatorPanel.setCellHorizontalAlignment(percentage, HasHorizontalAlignment.ALIGN_CENTER);

								

								
							}
						});
					}
				});
			}
		});
			
		
		waterGame.tradeBox.hide();
		waterGame.tradeBox.clear();
		waterGame.tradeRessourceBox.clear();
		RootPanel.get("tradeContainer").clear();
	}
	void clearPanels() {
		waterGame.ressourcePanel.clear();
		waterGame.populationPanel.clear();
		waterGame.lebensQualitaetPanel.clear();
		waterGame.wirtschaftsKraftPanel.clear();
		waterGame.umweltFreundlichkeitPanel.clear();
		waterGame.commonIndikatorPanel.clear();
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
		waterGame.rizeNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int theValueInt = result.get(6);
		waterGame.theNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int sugarValueInt = result.get(7);
		waterGame.sugarNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		int fishValueInt = result.get(8);
		waterGame.fishNeededInt = populationInt / waterGame.grundbedarfProKopfLW;
		waterGame.rizeValue = new HTML(Integer.toString(rizeValueInt) + "/" + Integer.toString(waterGame.rizeNeededInt));
		waterGame.fishValue = new HTML( Integer.toString(fishValueInt) + "/" + Integer.toString(waterGame.fishNeededInt));
		waterGame.sugarValue = new HTML( Integer.toString(sugarValueInt) + "/" + Integer.toString(waterGame.sugarNeededInt));
		waterGame.teaValue = new HTML(Integer.toString(theValueInt) + "/" + Integer.toString(waterGame.theNeededInt));

	}

	void setRessourceValueIndustrie(ArrayList<Integer>result, int populationInt) {
		int lederValueInt = result.get(9);
		waterGame.lederNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int textilValueInt = result.get(10);
		waterGame.textilNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int itValueInt = result.get(11);
		waterGame.itNeededInt = populationInt / waterGame.grundbedarfProKopfIndustrie;
		int budgetInt = result.get(4);
		waterGame.lederValue = new HTML(Integer.toString(lederValueInt) + "/" + Integer.toString(waterGame.lederNeededInt));
		waterGame.textilValue = new HTML(Integer.toString(textilValueInt) + "/" + Integer.toString(waterGame.textilNeededInt));
		waterGame.itValue = new HTML(Integer.toString(itValueInt) + "/" + Integer.toString(waterGame.itNeededInt));
	}

	void setKnowHowValue(ArrayList<Integer>result) {
		int knowHow = result.get(12);
		waterGame.knowhowValue = new HTML( Integer.toString(knowHow));
	}

	void setPopulationValue(int populationInt) {
		waterGame.populationValue = new Label(Integer.toString(populationInt));
		
	}
	
	void setBudgetValue(int budgetInt){
		waterGame.budgetValue = new Label(Integer.toString(budgetInt)); 
		waterGame.budgetValueInt = budgetInt;
	}
	
	void fillBudgetPanel(){
		waterGame.budgetPanel.clear();
		waterGame.budgetPanel.add(waterGame.budgetLabel);
		waterGame.budgetPanel.add(waterGame.budgetValue);
	}

	void fillWirtschaftskraftPanel() {
		HTML wirtschaftHTML = new HTML("<progress align=\"center\" value=\"" + waterGame.wirtschaftIndicatorValue + "\" max=\"100\">"
				+ waterGame.wirtschaftIndicatorValue + "</progress>");
		waterGame.wirtschaftsKraftPanel.add(waterGame.wirtschaftLabel);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(waterGame.wirtschaftLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(wirtschaftHTML);
		waterGame.wirtschaftsKraftPanel.setCellHorizontalAlignment(wirtschaftHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.wirtschaftsKraftPanel.add(new Label(waterGame.wirtschaftIndicatorValue));
		;
	}
	
	void fillLebensqualiPanel(){
		HTML lebensQualiHTML = new HTML("<progress value=\"" + waterGame.lebensqualiIndicatorValue + "\" max=\"100\">"
				+ waterGame.lebensqualiIndicatorValue + "</progress>");
		waterGame.lebensQualitaetPanel.add(waterGame.lebensqualitaetLabel);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(waterGame.lebensqualitaetLabel,
				HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(lebensQualiHTML);
		waterGame.lebensQualitaetPanel.setCellHorizontalAlignment(lebensQualiHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.lebensQualitaetPanel.add(new Label(waterGame.lebensqualiIndicatorValue));
		
	}
	void fillUmweltPanel() {
		HTML umweltHTML = new HTML("<progress value=\"" + waterGame.umweltIndicatorValue + "\" max=\"100\">"
				+ waterGame.umweltIndicatorValue + "</progress>");
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel
		.setCellHorizontalAlignment(waterGame.umweltLabel, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(waterGame.umweltLabel);
		waterGame.umweltFreundlichkeitPanel.add(umweltHTML);
		waterGame.umweltFreundlichkeitPanel.setCellHorizontalAlignment(umweltHTML, HasHorizontalAlignment.ALIGN_CENTER);
		waterGame.umweltFreundlichkeitPanel.add(new Label(waterGame.umweltIndicatorValue));							
	}
	
	void fillRessourcePanel(){
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
	
	void fillMeasuresPanel(){
		//UmweltSchutz
				Grid measuresTable = new Grid(4, 2);
				measuresTable.setWidget(0, 0, waterGame.umweltSchutzLabel);
				//measuresTable.setWidget(0, 0, waterGame.umweltSchutzBeschreibung);
				measuresTable.setWidget(0, 1, waterGame.umweltSchutzButton);
				measuresTable.setWidget(2, 0, waterGame.reformen);
				//measuresTable.setWidget(2, 0, waterGame.reformenBeschreibung);
				measuresTable.setWidget(2, 1, waterGame.reformenButton);
				measuresTable.setWidget(3, 0, waterGame.naturgefahrenSchutz);
				//measuresTable.setWidget(3, 0, waterGame.naturkatastropheBeschreibung);
				measuresTable.setWidget(3, 1, waterGame.naturkatastropheButton);
				waterGame.measuresPanel.add(waterGame.titleMeasures);
				waterGame.measuresPanel.add(measuresTable);
		

	}

}
