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
			
			TextView starter = (TextView) view.findViewById(R.id.card_event_menu_starter_text);
			TextView main = (TextView) view.findViewById(R.id.card_event_menu_main_text);
			TextView coffee = (TextView) view.findViewById(R.id.card_event_menu_coffee_text);
			TextView drinks = (TextView) view.findViewById(R.id.card_event_menu_drinks_text);
			
			if (starter != null) {
				starter.setText(event.getMenu().get(0).getName());
			}
			
			if (main != null) {
				main.setText(event.getMenu().get(1).getName());
			}
			
			if (coffee != null) {
				coffee.setText(event.getMenu().get(2).getName());
			}
			
			if (drinks != null) {
				drinks.setText(event.getMenu().get(3).getName());
			}
		}

    }

}
