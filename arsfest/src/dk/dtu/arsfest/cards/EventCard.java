package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dk.dtu.arsfest.EventActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;

public class EventCard extends Card {
	
	protected Event event;

	public EventCard(Context context, Event event) {
		super(context, R.layout.card_event_inner);
		this.event = event;
		init();
	}
	
	public EventCard(Context context, int innerLayout, Event event) {
		super(context, innerLayout);
		this.event = event;
		init();
	}
	
	private void init() {
		
		setOnClickListener(new OnCardClickListener() {
			
			@Override
			public void onClick(Card card, View view) {
				Intent intent = new Intent(getContext(), EventActivity.class);
				intent.putExtra(Constants.EXTRA_EVENT, event);
				getContext().startActivity(intent);
			}
		});
				
	}
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		if (view != null) {
			TextView title = (TextView) view
					.findViewById(R.id.carddemo_suggested_title);
			
			if (title != null)
				title.setText(getTitle());
		}
	}

}
