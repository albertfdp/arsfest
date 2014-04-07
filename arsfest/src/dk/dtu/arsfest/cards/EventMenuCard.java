package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;

public class EventMenuCard extends Card {
	
	private Event event;

	public EventMenuCard(Context context, Event event) {
		super(context, R.layout.card_event_menu_inner_content);
		this.event = event;
	}
	
	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {
		
		if (view != null) {
			TextView title = (TextView) view.findViewById(R.id.card_event_inner_content_title);
			TextView location = (TextView) view.findViewById(R.id.card_event_inner_content_location);
			
			if (title != null)
				title.setText(event.getName());
			
			if (location != null)
				location.setText(event.getParent().getName());
		}
		
	}

}
