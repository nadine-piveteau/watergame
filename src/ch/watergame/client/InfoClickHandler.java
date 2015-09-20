package ch.watergame.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

public class InfoClickHandler implements ClickHandler {
HTML message;

	public InfoClickHandler(HTML message) {
	super();
	this.message = message;
}

	@Override
	public void onClick(final ClickEvent event) {
		// TODO Auto-generated method stub
		System.out.println("In OnCLick");
        final InfoPopup pop=new InfoPopup(message);
        //new InfoPopup().show();
        int left = event.getClientX();
        int top = event.getClientY();
        pop.setWidth("400px");
        pop.setPopupPosition(left-5, top-5);
        pop.center();
        pop.show();
         
	}

}
