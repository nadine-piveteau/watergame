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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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

	// initial page
		final Button startButton = new Button("Spiel starten");
		final Button instructionButton = new Button("Spielanleitung");
		final Button clientCheckButton = new Button("check Client");
		
		//TradeBox
		public DialogBox tradeBox ;
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
		

		// indicators Label
		Label budgetLabel = new Label("Budget");
		Label budgetValue;
		Label populationLabel = new Label("Bevoelkerung");
		Label populationValue;
		Label wirtschaftLabel = new Label("Wirtschaftskraft");
		Label wirtschaftsValue;
		Label lebensqualitaetLabel = new Label("Lebensqualitaet");
		Label lebensqualitaetValue;
		Label umweltLabel = new Label("Umweltfreundlichkeit");
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
		HorizontalPanel importPanel = new HorizontalPanel();
		HorizontalPanel exportPanel = new HorizontalPanel();
		// Ressourcen Panel
		Label ressourceTitle = new Label("Ressourcenstand");
		HTML tradePlayer1 = new HTML("<p><h4>Spieler 1</h4></p>");
		HTML tradePlayer2 = new HTML("<p><h4>Spieler 2</h4></p>");
		HTML tradePlayer3 = new HTML("<p><h4>Spieler 3</h4></p>");
		HTML tradePlayer4 = new HTML("<p><h4>Spieler 4</h4></p>");
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
		// grid field
		Grid grid = new Grid(15, 15);
		// Fields
		static String selectedImage = null;
		static int selectedPrice;
		static int selectedPriceRemove;
		static int selectedKnowledge;
		Label fieldsTitle = new Label("Felder");
		Label lwLabel = new Label("Landwirtschaft");
		Label indLabel = new Label("Industrie");
		Label siedlungLabel = new Label("Siedlung");
		Label bildungLabel = new Label("Bildung");
		Image lwImage = new Image();
		Image industrieImage = new Image();
		Image rizeImage = new Image();
		final int rizePrice = 60;
		final int removeRizePrice = -60;
		Image rizeBioImage = new Image();
		final int rizeBioPrice = 60;
		final int removeRizeBioPrice = -60;
		Image teeImage = new Image();
		final int teePrice = 60;
		final int teePriceRemove = -60;
		Image teeBioImage = new Image();
		final int teeBioPrice = 60;
		final int teeBioPriceRemove = -60;
		Image zuckerImage = new Image();
		final int zuckrePrice = 60;
		final int zuckerPriceRemove = -60;
		Image zuckerBioImage = new Image();
		final int zuckreBioPrice = 60;
		final int zuckerBioPriceRemove = -60;	
		Image fischImage = new Image();
		final int fischPrice = 60;
		final int fischPriceRemove = -60;
		Image lederImage = new Image();
		final int lederPrice = 80;
		final int lederPriceRemove = -80;
		Image textilImage = new Image();
		final int textilPrice = 80;
		final int textilPriceRemove = -80;
		Image itImage = new Image();
		final int itPrice = 90;
		final int itPriceRemove = -90;
		Image siedlungImage = new Image();
		final int siedlungPrice = 50;
		final int siedlungPriceRemove = -50;
		Image unterStufeImage = new Image();
		final int unterStufePrice = 50;
		final int unterStufePriceRemove = -50;
		Image oberStufeImage = new Image();
		final int oberStufePrice = 75;
		final int oberStufeRemove = -75;
		Image uniImage = new Image();
		final int uniPrice = 100;
		final int uniPriceRemove = -100;
		

		// BOTTOM PANEL-----------------------------------------
		final Button validateButton = new Button("Validieren");

		// ressourcenstand
		String wirtschaftIndicatorValue;
		String lebensqualiIndicatorValue;
		String umweltIndicatorValue;
		final Label rizeLabel = new Label("Reis: ");
		Label rizeValue;
		final Label fishLabel = new Label("Fisch: ");
		Label fishValue;
		final Label sugarLabel = new Label("Zucker: ");
		Label sugarValue;
		final Label theLabel = new Label("The: ");
		Label teaValue;
		Label lederValue;
		Label textilValue;
		Label itValue;
		Label knowhowValue;
		/**
		 * This is the entry point method.
		 */
		Timer t;
		Timer t2;
		public int playerID;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//just for testing
		//RootPanel.get().add(instructionButton);
		
		RootPanel.get("tradeContainer").setVisible(false);
		RootPanel.get("gamefield").setVisible(false);
		RootPanel.get("NotYourTurn").setVisible(false);
		RootPanel.get("validateButtonContainer").setVisible(false);
		
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
				mainPanel.add(ressourcePanel);
				mainPanel.add(ressourceTradePanel);
				mainPanel.add(gamegridPanel);
				mainPanel.add(fieldsPanel);

				// Add the nameField and sendButton to the RootPanel
				// Use RootPanel.get() to get the entire body element
				RootPanel.get("sendButtonContainer").add(startButton);
				RootPanel.get("instructionButtonContainer").add(instructionButton);
				RootPanel.get("validateButtonContainer").add(validateButton);
				RootPanel.get("gamefield").add(headerPanel);
				RootPanel.get("gamefield").add(mainPanel);
				RootPanel.get().add(clientCheckButton);
			
				RootPanel.get("sendButtonContainer").setVisible(true);
				RootPanel.get("instructionButtonContainer").setVisible(true);

		//add Trade container
				tradeBox = new DialogBox();
				RootPanel.get("tradeContainer").add(tradeBox);

			
				// Add a handler to send the name to the server
				MyStartButtonHandler handler = new MyStartButtonHandler(greetingService, this);
				startButton.addClickHandler(handler);

				// Add a handler to check Client
				MyClientHandler clientHandler = new MyClientHandler();
				clientCheckButton.addClickHandler(clientHandler);

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
