package dk.dtu.arsfest.cards;

import dk.dtu.arsfest.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class SaleCardHeader extends CardHeader{

	public SaleCardHeader(Context context) {
		super(context,R.layout.card_sales_header_inner);
	}
	
	public SaleCardHeader(Context context, int innerLayout) {
		super(context,innerLayout);
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
