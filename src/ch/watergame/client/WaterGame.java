package ch.watergame.client;

import java.util.Arrays;
import java.util.List;

import ch.watergame.shared.FieldVerifier;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
/**
 * ------------------------------------------------------------------------------------------------------------------------------------------
 * ------------------------------------------------------------------------------------------------------------------------------------------
 * @author nadinepiveteau
 *TODO LISTE
 *- Indikator geht über 100%: verhältnis population und arbeitsplätze
 *- überprüfen ob genügend Ressourcen für Handel vorhanden ist.
 *- option zum Felder löschen. Bugging...
 *- Zufallsereigniss
 *- mehrere Spiele aufs Mal laufen lassen können.
 * ------------------------------------------------------------------------------------------------------------------------------------------
 * ------------------------------------------------------------------------------------------------------------------------------------------
 *FRAGEN
 * - wie stark soll Defizit eines Gutes die Wirtschaftskraft beeinflussen?

 */
public class WaterGame implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

		final int WINNINGINDIKATOR = 60;
		int gameID;
		PopupPanel waitingBox;
		PopupPanel tradeBox;
	
	
	// initial page
		final Button startButton = new Button("Spiel starten");
		final Button instructionButton = new Button("Spielanleitung");
		VerticalPanel removeImagePanel;
		//TradeBox
		//public DialogBox tradeBox ;
		public DialogBox tradeRessourceBox;
		public Label tradeBoxTitle;
		public VerticalPanel tradeBoxContent = new VerticalPanel();

		// HEADER PANEL--------------------------------------------
		HorizontalPanel headerPanel = new HorizontalPanel();
		VerticalPanel wirtschaftsKraftPanel = new VerticalPanel();
		VerticalPanel space = new VerticalPanel();
		VerticalPanel lebensQualitaetPanel = new VerticalPanel();
		VerticalPanel umweltFreundlichkeitPanel = new VerticalPanel();
		VerticalPanel populationPanel = new VerticalPanel();
		VerticalPanel budgetPanel = new VerticalPanel();
		VerticalPanel fieldsAndRoundCounter = new VerticalPanel();
		VerticalPanel nameAndRoundPanel = new VerticalPanel();
		

		// indicators Label
		Label budgetLabel = new Label("Budget");
		Label budgetValue;
		int budgetValueInt;
		Label populationLabel = new Label("Bevölkerung");
		Label populationValue;
		Label wirtschaftLabel = new Label("Wirtschaftskraft");
		Label wirtschaftsValue;
		Label lebensqualitaetLabel = new Label("Lebensqualität");
		Label lebensqualitaetValue;
		Label umweltLabel = new Label("Reinheit des Ganges");
		Label umweltValue;
		final int grundbedarfProKopfLW = 2;
		final int grundbedarfProKopfIndustrie = 3;


		// MAIN PANEL --------------------------------------------
		HorizontalPanel mainPanel = new HorizontalPanel();
		VerticalPanel ressourceTradePanel = new VerticalPanel();
		VerticalPanel ressourcePanel = new VerticalPanel();
		VerticalPanel gamegridPanel = new VerticalPanel();
		VerticalPanel fieldsPanel = new VerticalPanel();
		VerticalPanel tradePanel = new VerticalPanel();
		VerticalPanel measuresPanel = new VerticalPanel();
		HorizontalPanel importPanel = new HorizontalPanel();
		HorizontalPanel exportPanel = new HorizontalPanel();
		// Ressourcen Panel
		Label ressourceTitle = new Label("Ressourcenstand");
		HTML tradePlayer1 = new HTML("<strong>Chamoli</strong>");
		HTML tradePlayer2 = new HTML("<strong>Kanpur</strong>");
		HTML tradePlayer3 = new HTML("<strong>Varanasi</strong>");
		HTML tradePlayer4 = new HTML("<strong>Kalkutta</strong>");
		HTML importTitle = new HTML("<p><h5>Import                         </h5></p>");
		HTML exportTitle = new HTML("<p><h5>Export</h5></p>");
		// Trade field
		TextBox importAmountText1;
		TextBox importAmountText2;
		TextBox importAmountText3;
		TextBox importAmountText4;
		TextBox exportAmountText1;
		TextBox exportAmountText2;
		TextBox exportAmountText3;
		TextBox exportAmountText4;
		ListBox importList1;
		ListBox importList2;
		ListBox importList3;
		ListBox importList4;
		ListBox exportList1;
		ListBox exportList2;
		ListBox exportList3;
		ListBox exportList4;
		// political measures field
		int preisUmweltverschmutzung= 500;
		boolean activeUmweltschutz;
		int preisReformen = 500;
		boolean activeReformen;
		int preisNaturkatastrophen = 500;
		boolean activeNaturkatastrophen;
		Label titleMeasures = new Label("Massnahmen");
		HTML umweltSchutzLabel = new HTML("<strong>Säuberung des Flusses</strong><br>Preis: "+  preisUmweltverschmutzung);
		CheckBox umweltSchutzButton = new CheckBox ();
		//HTML umweltSchutzBeschreibung = new HTML("<h5>Flusssäuberung, Energiesparmassnahmen<br> Preis: XXXX<h5>");
		HTML reformen = new HTML("<strong>Förderung der Demokratie</strong><br>Preis: "+preisReformen);
		CheckBox reformenButton = new CheckBox ();
		//HTML reformenBeschreibung = new HTML("<h5>Verbesserung des politischen Systems <br> Preis: XXXX<h5>");
		HTML naturgefahrenSchutz = new HTML("<strong>Schutz vor Naturkatastrophen</strong><br>Preis: "+preisNaturkatastrophen);
		CheckBox naturkatastropheButton = new CheckBox ();
		//HTML naturkatastropheBeschreibung = new HTML("<h5>Schutzmassnahmen gegen die nächste Naturkatstrophe <br> Preis: XXXX<h5>");

 


		// grid field
		Grid grid = new Grid(15, 15);
		// Fields
		static String selectedImage = null;
		static int selectedPrice;
		static int selectedPriceRemove;
		static int selectedKnowledge;
		Label fieldsTitle = new Label("Funktionsfelder");
		Label lwLabel = new Label("Landwirtschaft");
		Label indLabel = new Label("Industrie");
		Label siedlungLabel = new Label("Siedlung");
		Label bildungLabel = new Label("Bildung");
		Image lwImage = new Image();
		Image industrieImage = new Image();
		VerticalPanel rizePanel = new VerticalPanel();
		Image rizeImage = new Image();
		final int rizePrice = 60;
		final int removeRizePrice = -60;
		VerticalPanel rizeBioPanel = new VerticalPanel();
		Image rizeBioImage = new Image();
		final int rizeBioPrice = 60;
		final int removeRizeBioPrice = -60;
		VerticalPanel teePanel = new VerticalPanel();
		Image teeImage = new Image();
		final int teePrice = 60;
		final int teePriceRemove = -60;
		VerticalPanel teeBioPanel = new VerticalPanel();
		Image teeBioImage = new Image();
		final int teeBioPrice = 60;
		final int teeBioPriceRemove = -60;
		VerticalPanel zuckerPanel = new VerticalPanel();
		Image zuckerImage = new Image();
		final int zuckrePrice = 60;
		final int zuckerPriceRemove = -60;
		VerticalPanel zuckerBioPanel = new VerticalPanel();
		Image zuckerBioImage = new Image();
		final int zuckreBioPrice = 60;
		final int zuckerBioPriceRemove = -60;	
		VerticalPanel fischPanel = new VerticalPanel();
		Image fischImage = new Image();
		final int fischPrice = 60;
		final int fischPriceRemove = -60;
		VerticalPanel fischBioPanel = new VerticalPanel();
		Image fischBioImage = new Image();
		final int fischBioPrice = 60;
		final int fischBioPriceRemove = -60;
		VerticalPanel lederPanel = new VerticalPanel();
		Image lederImage = new Image();
		final int lederPrice = 80;
		final int lederPriceRemove = -80;
		VerticalPanel lederBioPanel = new VerticalPanel();
		Image lederBioImage = new Image();
		final int lederBioPrice = 80;
		final int lederBioPriceRemove = -80;
		VerticalPanel textilPanel = new VerticalPanel();
		Image textilImage = new Image();
		final int textilPrice = 80;
		final int textilPriceRemove = -80;
		VerticalPanel textilBioPanel = new VerticalPanel();
		Image textilBioImage = new Image();
		final int textilBioPrice = 80;
		final int textilBioPriceRemove = -80;
		VerticalPanel itPanel = new VerticalPanel();
		Image itImage = new Image();
		final int itPrice = 90;
		final int itPriceRemove = -90;
		VerticalPanel itBioPanel = new VerticalPanel();
		Image itBioImage = new Image();
		final int itBioPrice = 90;
		final int itBioPriceRemove = -90;
		VerticalPanel siedlungPanel = new VerticalPanel();
		Image siedlungImage = new Image();
		final int siedlungPrice = 50;
		final int siedlungPriceRemove = -50;
		VerticalPanel unterstufePanel = new VerticalPanel();
		Image unterStufeImage = new Image();
		final int unterStufePrice = 50;
		final int unterStufePriceRemove = -50;
		VerticalPanel oberstufePanel = new VerticalPanel();
		Image oberStufeImage = new Image();
		final int oberStufePrice = 75;
		final int oberStufeRemove = -75;
		VerticalPanel uniPanel = new VerticalPanel();
		Image uniImage = new Image();
		final int uniPrice = 100;
		final int uniPriceRemove = -100;
		Image infoLogo = new Image("infoLogo.jpg");
		public Grid gridLW;
		public Grid gridIndustrie;
		public Grid gridBildung = new Grid(3,3);
		
		//expected ressources
		int rizeNeededInt;
		int theNeededInt;
		int sugarNeededInt;
		int fishNeededInt;
		int lederNeededInt;
		int textilNeededInt;
		int itNeededInt;
		

		// BOTTOM PANEL-----------------------------------------
		VerticalPanel validateButtonPanel = new VerticalPanel();
		final Button validateButton = new Button("Abschliessen");
		HorizontalPanel bottomPanel = new HorizontalPanel();
		VerticalPanel commonIndikatorPanel = new VerticalPanel();
		Label commonIndikatorLabel = new Label("Gesamtleistung");
		int commonIndikator;
		

		// ressourcenstand
		String wirtschaftIndicatorValue;
		String lebensqualiIndicatorValue;
		String umweltIndicatorValue;
		final HTML rizeLabel = new HTML("Reis: ");
		HTML rizeValue;
		int rizeValueInteger;
		final HTML fishLabel = new HTML("Fisch: ");
		HTML fishValue;
		int fishValueInteger;
		final HTML sugarLabel = new HTML("Zuckerrohr: ");
		HTML sugarValue;
		int sugarValueInteger;
		final HTML theLabel = new HTML("Tee: ");
		HTML teaValue;
		int teaValueInteger;
		final HTML lederLabel = new HTML("Leder: ");
		HTML lederValue;
		int lederValueInteger;
		final HTML textilLabel = new HTML("Textil: ");
		HTML textilValue;
		int textilValueInteger;
		final HTML itLabel = new HTML("IT: ");
		HTML itValue;
		int itValueInteger;
		HTML knowhowValue;
		final HTML wissenLabel = new HTML("Wissen: ");

		int knowhowValueInteger;
		
		// RUNDEN ZÄhler!!!
		int gameRound;
		VerticalPanel roundPanel = new VerticalPanel();
		HTML roundCounter = new HTML();
		
		int eventNR;
		
		
		/**
		 * This is the entry point method.
		 */
		Timer t;
		Timer t2;
		Timer t3;
		public int playerID;


		public HTML rizeHTML;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//just for testing
		//RootPanel.get().add(instructionButton);
		infoLogo.setSize("25px", "25px");
		roundCounter.setStyleName("roundCounter");
		roundCounter.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		RootPanel.get("WIN").setVisible(false);
		RootPanel.get("GAMEOVER").setVisible(false);
		
		RootPanel.get("tradeContainer").setVisible(false);
		RootPanel.get("gamefield").setVisible(false);
		RootPanel.get("NotYourTurn").setVisible(false);
		RootPanel.get("validateButtonContainer").setVisible(false);
		
	
		
		
		RootPanel.get("name").add(nameAndRoundPanel);
		// put Gamefield together
				// Header Panel
				putWirtschaftskraftPanel();
				putQualitaetPanel();
				putUmweltfreundlichkeitPanel();
				putPopulationPanel();
				putBudgetPanel();

				headerPanel.add(wirtschaftsKraftPanel);
				headerPanel.add(lebensQualitaetPanel);
				headerPanel.add(umweltFreundlichkeitPanel);
				headerPanel.add(populationPanel);
				headerPanel.add(budgetPanel);

				// MainPanel
				// ressourcePanel.setSize("200px", "650px");
				ressourcePanel.add(ressourceTitle);
				ressourcePanel.setCellHorizontalAlignment(ressourceTitle, HasHorizontalAlignment.ALIGN_CENTER);
				grid.setSize("650px", "650px");
				// Put some size of grid cells.
				for (int row = 0; row < 15; ++row) {
					for (int col = 0; col < 15; ++col) {
						grid.getCellFormatter().setHeight(row, col, "40px");
						grid.getCellFormatter().setWidth(row, col, "40px");
						grid.setBorderWidth(1);
						if (row < 11) {
							Image lwImageGrid = new Image();
							lwImageGrid.setUrl("transparent_graphic.png");
							lwImageGrid.setSize("40px", "40px");
							GridClickHandler gridHandler = new GridClickHandler(greetingService,this, row, col);
							lwImageGrid.addClickHandler(gridHandler);
							grid.setWidget(row, col, lwImageGrid);
						}
					}
				}
				gamegridPanel.add(grid);

				fieldsPanel.add(fieldsTitle);
				fieldsPanel.setCellHorizontalAlignment(fieldsTitle, HasHorizontalAlignment.ALIGN_CENTER);
				fieldsPanel.setSize("350px", "650px");
				fieldsAndRoundCounter.add(fieldsPanel);
				
				mainPanel.add(ressourcePanel);
				mainPanel.add(ressourceTradePanel);
				mainPanel.add(gamegridPanel);
				mainPanel.add(fieldsAndRoundCounter);

				// Add the nameField and sendButton to the RootPanel
				// Use RootPanel.get() to get the entire body element
				RootPanel.get("sendButtonContainer").add(startButton);
				RootPanel.get("instructionButtonContainer").add(instructionButton);
				commonIndikatorPanel.add(commonIndikatorLabel);
				commonIndikatorPanel.setCellVerticalAlignment(commonIndikatorLabel, HasVerticalAlignment.ALIGN_MIDDLE);
				commonIndikatorPanel.setCellHorizontalAlignment(commonIndikatorLabel, HasHorizontalAlignment.ALIGN_CENTER);

				commonIndikatorPanel.setWidth("25%");
//				commonIndikatorPanel.setWidth("300px");

				//bottomPanel.add(commonIndikatorPanel);
				fieldsAndRoundCounter.add(commonIndikatorPanel);
				
				validateButtonPanel.add(validateButton);
				bottomPanel.add(validateButtonPanel);
				bottomPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
				bottomPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
				RootPanel.get("validateButtonContainer").add(bottomPanel);
				RootPanel.get("gamefield").add(headerPanel);
				RootPanel.get("gamefield").add(mainPanel);
			
				RootPanel.get("sendButtonContainer").setVisible(true);
				RootPanel.get("instructionButtonContainer").setVisible(true);

		//add Trade container
				tradeBox = new DialogBox();
				tradeRessourceBox = new DialogBox();
				RootPanel.get("tradeContainer").add(tradeBox);
				RootPanel.get("tradeContainer").add(tradeRessourceBox);
				

			
				// Add a handler to send the name to the server
				MyStartButtonHandler handler = new MyStartButtonHandler(greetingService, this);
				startButton.addClickHandler(handler);


				// Add a Clickhandler to Validator Button
				ValidatorHandler validatorHandler = new ValidatorHandler(this, greetingService);
				validateButton.addClickHandler(validatorHandler);
				
			

	}
	
	public static String getSelectedImage() {
		return selectedImage;
	}

	public static void setSelectedImage(String selectedImage) {
		WaterGame.selectedImage = selectedImage;
	}

	public static int getSelectedPrice() {
		return selectedPrice;
	}

	public static void setSelectedPrice(int selectedPrice) {
		WaterGame.selectedPrice = selectedPrice;
	}

	public static int getSelectedPriceRemove() {
		return selectedPriceRemove;
	}

	public static void setSelectedPriceRemove(int selectedPriceRemove) {
		WaterGame.selectedPriceRemove = selectedPriceRemove;
	}

	// Timer until all player are in
	public void startSwitchTimer() {
		t2 = new SwitchTimer(greetingService, this);
		t2.scheduleRepeating(500);
	}
	
	public void startEndTimer(){
		t3 = new EndTimer(greetingService, this);
		t3.scheduleRepeating(500);
	}

	// set Panel for Lebensqualitaet Indicator
	public void putQualitaetPanel() {
		lebensQualitaetPanel.add(lebensqualitaetLabel);
		lebensQualitaetPanel.setSize("300px", "70px");
		lebensQualitaetPanel.setCellHorizontalAlignment(lebensqualitaetLabel, HasHorizontalAlignment.ALIGN_CENTER);
	}

	// set Panel for Wirtschaftskraft Indicator
	public void putWirtschaftskraftPanel() {
		wirtschaftsKraftPanel.add(wirtschaftLabel);
		wirtschaftsKraftPanel.setSize("300px", "70px");
		wirtschaftsKraftPanel.setCellHorizontalAlignment(wirtschaftLabel, HasHorizontalAlignment.ALIGN_CENTER);
	}

	// set Panel for Umweltfreundlichkeit Indicator
	public void putUmweltfreundlichkeitPanel() {
		umweltFreundlichkeitPanel.add(umweltLabel);
		umweltFreundlichkeitPanel.setSize("300px", "70px");
		umweltFreundlichkeitPanel.setCellHorizontalAlignment(umweltLabel, HasHorizontalAlignment.ALIGN_CENTER);
	}

	// set Panel for Population Indication
	public void putPopulationPanel() {
		populationPanel.add(populationLabel);
		populationPanel.setSize("150px", "70px");
		populationPanel.setCellHorizontalAlignment(populationLabel, HasHorizontalAlignment.ALIGN_CENTER);
	}

	// set Panel for Budget Indication
	private void putBudgetPanel() {
		budgetPanel.add(budgetLabel);
		budgetPanel.setSize("150px", "70px");
		budgetPanel.setCellHorizontalAlignment(budgetLabel, HasHorizontalAlignment.ALIGN_CENTER);
	}

	// set playerID
	public void setplayerID(int id) {
		this.playerID = id;
	}

	public int getPlayerID() {
		return this.playerID;
	}
	
	
//am ende löschen.
	private class MyClientHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			greetingService.getPlayerID(new AsyncCallback<String>() {

				@Override
				public void onFailure(Throwable caught) {
					System.out.println("Failed to identify player.");
				}

				@Override
				public void onSuccess(String result) {
					System.out.println(result);

				}

			});
		}

	}
}
