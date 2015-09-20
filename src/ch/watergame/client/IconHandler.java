package ch.watergame.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

public class IconHandler implements ClickHandler{
	String imgUrlString;
	int price;
	int priceRemove;
	int knowledge;
	WaterGame game;
	GreetingServiceAsync greetingService;
	ArrayList<VerticalPanel> imagePanels = new ArrayList<VerticalPanel>();

	public IconHandler(GreetingServiceAsync greetingService, WaterGame wgame, String img, int price, int priceRemove, int knowledge) {
		super();
		this.imgUrlString = img;
		this.game = wgame;
		this.greetingService = greetingService;
		this.price = price;
		this.priceRemove = priceRemove;
		this.knowledge = knowledge;
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
	}

	@Override
	public void onClick(ClickEvent event) {
		WaterGame.setSelectedImage(imgUrlString);
		WaterGame.selectedPrice = price;
		WaterGame.selectedPriceRemove = priceRemove;
		WaterGame.selectedKnowledge = this.knowledge;
		System.out.println("Selected Image: " +this.imgUrlString);
		System.out.println("boolean value: "+imgUrlString.equals("rizeField.jpeg"));
		removeStyles();
		if(imgUrlString.equals("rizeField.jpeg")){
			game.rizePanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("rizeFieldbio.jpeg")){
			game.rizeBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("tee.jpeg")){
			game.teePanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("teebio.jpeg")){
			game.teeBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("zucker.jpg")){
			game.zuckerPanel.setStyleName("selectedImage");
		}
		else if(imgUrlString.equals("zuckerbio.jpg")){
			game.zuckerBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("fisch.jpg")){
			game.fischPanel.setStyleName("selectedImage");
		}
		else if(imgUrlString.equals("fischbio.jpg")){
			game.fischBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("leder.JPG")){
			game.lederPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("lederbio.jpg")){
			game.lederBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("textil.jpg")){
			game.textilPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("textil.jpg")){
			game.textilPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("textilbio.jpg")){
			game.textilBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("it.jpg")){
			game.itPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("itbio.jog.jpg")){
			game.itBioPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("siedlung.jpg")){
			game.siedlungPanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("unterstufe.jpeg")){
			game.unterstufePanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("oberstufe.jpeg")){
			game.oberstufePanel.setStyleName("selectedImage");
		}else if(imgUrlString.equals("uni.jpg")){
			game.uniPanel.setStyleName("selectedImage");
		}
		game.removeImagePanel.removeStyleName("selectedImage");

	}
	
	public void removeStyles(){
		int i = 0;
		for(VerticalPanel vp:imagePanels){
			i=i+1;
			vp.removeStyleName("selectedImage");
		}
	}
}
