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
    		TextView priceDanceDinner = (TextView) view.findViewById(R.id.card_price_item_price);
    		TextView priceDance = (TextView) view.findViewById(R.id.card_price_item_price_dance);
    		
    		priceDance.setTypeface(font);
    		priceDanceDinner.setTypeface(font);
    		
    		
    		/*TextView title = (TextView) view.findViewById(R.id.card_price_inner_content_title);
	        TextView total = (TextView) view.findViewById(R.id.card_price_inner_content_total1);
	        TextView dance = (TextView) view.findViewById(R.id.card_price_inner_content_dance1);
	        TextView extra = (TextView) view.findViewById(R.id.card_price_inner_content_extra);
	
	        if (title != null)
	           	title.setTypeface(font);
	            title.setText(event.getTheme());
	
	        if (total != null)
	            total.setText(event.getPrices().get(0).getName());
	
	        if (dance != null)
	            dance.setText(event.getPrices().get(1).getName());
	            
	        //if (extra != null)
	          // extra.setText(event.getPrices().get(2));

            */
        }
    }
}



