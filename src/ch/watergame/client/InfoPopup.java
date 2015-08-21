package ch.watergame.client;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class InfoPopup extends PopupPanel {
HTML message;

public InfoPopup(HTML message) {
	super(true);
	this.message = message;
	System.out.println("Info popup");
    setWidget(message);
    
}

}



