package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

public class OkEventAlertClickHandler implements ClickHandler {
	DialogBox messageBox;
	
	public OkEventAlertClickHandler(DialogBox messageBox) {
		super();
		this.messageBox = messageBox;
	}

	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		messageBox.hide();
	}

}
