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
        pop.setWidth("200px");
        pop.setPopupPosition(left-20, top-20);
        pop.show();
       /* pop.setPopupPositionAndShow(new PopupPanel.PositionCallback() {
            public void setPosition(int offsetWidth, int offsetHeight) {
              int left = event.getClientX();
              int top = event.getClientY();
              pop.setPopupPosition(left, top);
            }
          });  */      
	}

}
