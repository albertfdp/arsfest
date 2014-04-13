package dk.dtu.arsfest.cards;

import dk.dtu.arsfest.R;
import android.content.Context;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class PriceTicketCard extends Card{

	
	public PriceTicketCard(Context context) {
        this(context, R.layout.card_price_inner_content);
    }

    public PriceTicketCard(Context context, int innerLayout) {
        super(context, innerLayout);
        init();
    }
    
    private void init() {

        //Add a header
        SuggestedCardHeader header = new SuggestedCardHeader(getContext());
        addCardHeader(header);
    }
    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view != null) {
            TextView title = (TextView) view.findViewById(R.id.card_price_suggested_title);
            TextView member = (TextView) view.findViewById(R.id.card_price_suggested_memeber);
            TextView community = (TextView) view.findViewById(R.id.card_price_suggested_community);

            if (title != null)
                title.setText(R.string.card_price_suggested_title);

            if (member != null)
                member.setText(R.string.card_price_suggested_member);

            if (community != null)
                community.setText(R.string.card_price_suggested_community);
        }
    }
}

class SuggestedCardHeader extends CardHeader {

    public SuggestedCardHeader(Context context) {
        super(context, R.layout.card_sales_header_inner);
    }
    
    public SuggestedCardHeader(Context context, int innerLayout) {
        super(context, innerLayout);
    }

    
    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view != null) {
            TextView textView = (TextView) view.findViewById(R.id.text_suggested_card1);
            
            if (textView != null) {
                textView.setText(R.string.card_price_header);

            }
        }
    }
}


