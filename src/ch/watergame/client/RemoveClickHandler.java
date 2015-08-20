package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RemoveClickHandler implements ClickHandler {
	Image img;
	WaterGame game;
	GreetingServiceAsync greetingService;
	int price;

	public RemoveClickHandler(GreetingServiceAsync greetingService, WaterGame wgame) {
		super();
		this.game = wgame;
		this.greetingService = greetingService;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		WaterGame.setSelectedImage("transparent_graphic.png");
		WaterGame.setSelectedPrice(0);
		WaterGame.setSelectedPriceRemove(0);

	}

	
	

}
