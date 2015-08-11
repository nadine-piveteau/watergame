package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class IconHandler implements ClickHandler{
	String imgUrlString;
	int price;
	int priceRemove;
	int knowledge;
	WaterGame game;
	GreetingServiceAsync greetingService;

	public IconHandler(GreetingServiceAsync greetingService, WaterGame wgame, String img, int price, int priceRemove, int knowledge) {
		super();
		this.imgUrlString = img;
		this.game = wgame;
		this.greetingService = greetingService;
		this.price = price;
		this.priceRemove = priceRemove;
		this.knowledge = knowledge;
	}

	@Override
	public void onClick(ClickEvent event) {
		WaterGame.setSelectedImage(imgUrlString);
		WaterGame.selectedPrice = price;
		WaterGame.selectedPriceRemove = priceRemove;
		WaterGame.selectedKnowledge = this.knowledge;
		System.out.println("Selected Image: " +this.imgUrlString);
	}
}
