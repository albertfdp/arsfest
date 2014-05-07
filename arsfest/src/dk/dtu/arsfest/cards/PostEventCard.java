package dk.dtu.arsfest.cards;

import dk.dtu.arsfest.R;
import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PostEventCard extends Card{

	public PostEventCard(Context context) {
		super(context, R.layout.card_postevent_inner);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {
		
		if (view != null) {
			
			TextView title = (TextView) view.findViewById(R.id.card_postevent_title);
			TextView location = (TextView) view.findViewById(R.id.card_postevent_subtitle);
			
			if (title != null)
				title.setText(R.string.card_finished_message);
			
			if (location != null)
				location.setText(R.string.card_finished_submessage);
			
		}

    }

	
}
