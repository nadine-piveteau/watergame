package ch.watergame.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RemoveClickHandler implements ClickHandler {
	Image img;
	WaterGame game;
	GreetingServiceAsync greetingService;
	int price;
	ArrayList<VerticalPanel> imagePanels = new ArrayList<VerticalPanel>();


	public RemoveClickHandler(GreetingServiceAsync greetingService, WaterGame wgame) {
		super();
		this.game = wgame;
		this.greetingService = greetingService;
		imagePanels.add(game.rizePanel);
		imagePanels.add(game.rizeBioPanel);
		imagePanels.add(game.teePanel);
		imagePanels.add(game.teeBioPanel);
		imagePanels.add(game.zuckerPanel);
		imagePanels.add(game.zuckerBioPanel);
		imagePanels.add(game.fischPanel);
		imagePanels.add(game.fischBioPanel);
		imagePanels.add(game.lederPanel);
		imagePanels.add(game.lederBioPanel);
		imagePanels.add(game.textilPanel);
		imagePanels.add(game.textilBioPanel);
		imagePanels.add(game.itPanel);
		imagePanels.add(game.itBioPanel);
		imagePanels.add(game.itBioPanel);
		imagePanels.add(game.siedlungPanel);
		imagePanels.add(game.unterstufePanel);
		imagePanels.add(game.oberstufePanel);
		imagePanels.add(game.uniPanel);
		imagePanels.add(game.removeImagePanel);
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		WaterGame.setSelectedImage("transparent_graphic.png");
		WaterGame.setSelectedPrice(0);
		WaterGame.setSelectedPriceRemove(0);
		removeStyles();
		game.removeImagePanel.setStyleName("selectedImage");
	}
	
	public void removeStyles(){
		for(VerticalPanel vp:imagePanels){
			vp.removeStyleName("selectedImage");
		}
	}

	
	

}
