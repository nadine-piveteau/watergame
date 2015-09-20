package ch.watergame.shared;

import java.io.Serializable;

import ch.watergame.client.GreetingServiceAsync;
import ch.watergame.server.GreetingServiceImpl;

public class Trade implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// public Player partnerA;
	public int partnerAID;
	// public Player partnerB;
	public int partnerBID;
	public int importAmount;
	public String importGood;
	public int exportAmount;
	public String exportGood;

	public Trade() {

	}

	public Trade(int partnerAID, int partnerBID, int importAmount, String importGood, int exportAmount, String exportGood) {
		super();
		//Löst Handel aus
		this.partnerAID = partnerAID;
		//Handelspartner
		this.partnerBID = partnerBID;
		this.importAmount = importAmount;
		this.importGood = importGood;
		this.exportAmount = exportAmount;
		this.exportGood = exportGood;
	}

	void executeTrade() {
		String goodImport = importGood;
		if (goodImport.equals("Leder")) {
			// partnerA.setLeder(importAmount);
		}
		// etc...
	}

	public String toString() {
		if(partnerAID == 0){
			return "<p><strong>Chamoli möchte mit dir handeln:</strong><br>Export: " + importAmount + " Einheiten " + importGood + "<br>Import:  "
					+ exportAmount + " Einheiten " + " " + exportGood + ". <br>Willst du den Handel ausführen?<p>";
			
		}else if(partnerAID == 1){
			return "<p><strong>Kanpur möchte mit dir handeln:</strong><br>Export: " + importAmount + " Einheiten " + importGood + "<br>Import:  "
					+ exportAmount + " Einheiten " + " " + exportGood + ". <br>Willst du den Handel ausführen?<p>";
			
		}else if (partnerAID == 2){
			return "<p><strong>Varanasi möchte mit dir handeln:</strong><br>Export: " + importAmount + " Einheiten " + importGood + "<br>Import:  "
					+ exportAmount + " Einheiten " + " " + exportGood + ". <br>Willst du den Handel ausführen?<p>";
			
		}else {
			return "<p><strong>Kalkutta möchte mit dir handeln:</strong><br>Export: " + importAmount + " Einheiten " + importGood + "<br>Import:  "
					+ exportAmount + " Einheiten " + " " + exportGood + ". <br>Willst du den Handel ausführen?<p>";
			
		}
	}
	
}
