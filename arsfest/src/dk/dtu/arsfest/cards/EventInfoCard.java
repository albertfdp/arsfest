package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class EventInfoCard extends Card {
	
	private Event event;
	private Typeface font;
	
	public EventInfoCard(Context context, Event event) {
		super(context, R.layout.card_event_inner_content);
		this.event = event;
		font = Typeface.createFromAsset(context.getAssets(), Constants.TYPEFONT_NEOSANS);
	}
	
	@Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
		
		if (view != null) {
			
			TextView title = (TextView) view.findViewById(R.id.card_event_inner_content_title);
			TextView location = (TextView) view.findViewById(R.id.card_event_inner_content_location);
			TextView time = (TextView) view.findViewById(R.id.card_event_inner_content_time);
			
			if (title != null)
				title.setTypeface(font);
				title.setText(event.getName());
			
			if (location != null)
				location.setText(event.getParent().getName());
			
			if (time != null)
				time.setText(Utils.getEventTime(event.getStartTime()) + " - " + Utils.getEventTime(event.getEndTime()));
			
		}

    }

}
