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

public class EventDescriptionCard extends Card {

	private Event event;
	private Typeface font;
	
	public EventDescriptionCard(Context context, Event event) {
		super(context, R.layout.card_event_description_content);
		this.event = event;
		font = Typeface.createFromAsset(context.getAssets(), Constants.TYPEFONT_NEOSANS);
	}
	
	@Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
		
		if (view != null) {
			
			TextView description = (TextView) view.findViewById(R.id.card_event_description_text);
			
			if (description != null) {
				description.setTypeface(font);
				description.setText(event.getDescription());
			}
		}

    }
}
