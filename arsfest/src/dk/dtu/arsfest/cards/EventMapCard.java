package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import dk.dtu.arsfest.localization.MapActivity;
import dk.dtu.arsfest.model.Event;

public class EventMapCard extends EventImageCard {

	public EventMapCard(Context context, Event event) {
		super(context, event);
		
		setOnClickListener(new OnCardClickListener() {
			
			@Override
			public void onClick(Card card, View view) {
				Intent intent = new Intent(getContext(), MapActivity.class);
				
				// TODO: read coordinates
				//intent.putExtra(Constants.EXTRA_EVENT, event);
				getContext().startActivity(intent);
			}
		});
		
	}

}
