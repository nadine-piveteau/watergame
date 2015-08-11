package ch.watergame.client;

import com.google.gwt.event.dom.client.ContextMenuEvent;
import com.google.gwt.event.dom.client.ContextMenuHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;

public class FieldDoubleClickHandler implements DoubleClickHandler {
	private int row;
	private int col;
	private String imgString;
	WaterGame game;
	GreetingServiceAsync greetingService;
	
	

	public FieldDoubleClickHandler(int row, int col, String imgString,
			WaterGame game, GreetingServiceAsync greetingService) {
		super();
		this.row = row;
		this.col = col;
		this.imgString = imgString;
		this.game = game;
		this.greetingService = greetingService;
	}



@Override
public void onDoubleClick(DoubleClickEvent event) {
	// TODO Auto-generated method stub
	final Image replaceImage = new Image();
	replaceImage.setSize("40px", "40px");
	replaceImage.setUrl(imgString);
	Window.alert("Do you want to delete it?" + imgString);
	GridClickHandler replaceImageGridHandler = new GridClickHandler(
			greetingService, game, row, col);
	replaceImage.addClickHandler(replaceImageGridHandler);
	
	greetingService.getPlayerID(new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(String result) {
			// TODO Auto-generated method stub
			game.grid.setWidget(row, col, replaceImage);
			Window.alert("Deleted. Set: " +replaceImage);
			final int userID = Integer.parseInt(result);
			greetingService.setFieldintoGameField(
					userID, row, col,
					imgString,
					new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(Void result) {
							System.out.println("Field has been removed in Gamefield.");							
						}
						
						
					});
		}
		
		
		
	});
}



}
