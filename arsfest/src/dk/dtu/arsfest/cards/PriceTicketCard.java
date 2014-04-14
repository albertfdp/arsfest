package dk.dtu.arsfest.cards;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class PriceTicketCard extends Card{

	private Typeface font;
	private Event event;
	
	public PriceTicketCard(Context context,Event event) {
        this(context, R.layout.card_price_inner_content);
        this.event = event;
        font = Typeface.createFromAsset(context.getAssets(), Constants.TYPEFONT_NEOSANS);
    }

    public PriceTicketCard(Context context, int innerLayout) {
        super(context, innerLayout);
    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

    	if (view != null) {
    		TextView title = (TextView) view.findViewById(R.id.card_price_inner_content_title);
	        TextView total = (TextView) view.findViewById(R.id.card_price_inner_content_total1);
	        TextView dance = (TextView) view.findViewById(R.id.card_price_inner_content_dance1);
	        TextView extra = (TextView) view.findViewById(R.id.card_price_inner_content_extra);
	
	        if (title != null)
	           	title.setTypeface(font);
	            title.setText(event.getTheme());
	
	        if (total != null)
	            total.setText(event.getPrice().get(0));
	
	        if (dance != null)
	            dance.setText(event.getPrice().get(1));
	            
	        if (extra != null)
	           extra.setText(event.getPrice().get(2));

            
        }
    }
}



