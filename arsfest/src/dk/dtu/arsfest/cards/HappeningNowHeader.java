package dk.dtu.arsfest.cards;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.utils.Constants;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class HappeningNowHeader extends CardHeader {
	
	private Typeface font;

	public HappeningNowHeader(Context context) {
		super(context, R.layout.card_event_header_inner);
		font = Typeface.createFromAsset(getContext().getAssets(), Constants.TYPEFONT_NEOSANS);
	}
	
	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		if (view != null) {
			TextView textView = (TextView) view.findViewById(R.id.text_suggested_card1);
			textView.setTypeface(font);

			if (textView != null) {
				textView.setText(getTitle());
			}
		}
	}

}
