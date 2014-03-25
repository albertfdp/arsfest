package dk.dtu.arsfest.cards;

import it.gmariotti.cardslib.library.internal.Card;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;

public class EventImageCard extends Card {
	
	private Event event;
	
	private ImageLoader imageLoader;

	public EventImageCard(Context context, Event event) {
		super(context, R.layout.card_event_image_layout);
		this.event = event;
		imageLoader = ImageLoader.getInstance();
	}
	
	@Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
		
		if (view != null) {
			ImageView eventImage = (ImageView) view.findViewById(R.id.carddemo_inside_image);
		
			imageLoader.displayImage(Constants.IMG_CONTENT_PROVIDER_URL + this.event.getImage() + ".jpg", (ImageView) eventImage);
		}
    }

}
