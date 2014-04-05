package dk.dtu.arsfest.cards;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class HappenningNowCard extends EventCard{
	
	private String member;
	private String subtitle;
	private String community;
	
	private ImageView locationImage;
	
	private Typeface font;
	
	public HappenningNowCard(Context context, Event event) {
		super(context, R.layout.card_event_inner, event);
		init();
		
	}
	
	private void init() {
		initEventFields();
		initFont();
	}
	
	private void initFont() {
		font = Typeface.createFromAsset(getContext().getAssets(), Constants.TYPEFONT_NEOSANS);
	}

	public HappenningNowCard(Context context, int innerLayout, Event event) {
		super(context, innerLayout, event);
		init();
	}
	
	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}
	
	private void initEventFields() {
		if (event != null) {
			setTitle(event.getName());
			this.member = Utils.getEventTime(event.getStartTime());
			this.community = event.getParent().getName();
			
			if (event.hasStarted()) {
				setSubtitle("Ends " + DateUtils.getRelativeTimeSpanString(event.getEndTime().getTime()).toString());
			} else {
				setSubtitle("Starts " + DateUtils.getRelativeTimeSpanString(event.getStartTime().getTime()).toString());
			}
			
		}
	}

	@Override
	public void setupInnerViewElements(ViewGroup parent, View view) {

		if (view != null) {
			TextView title = (TextView) view
					.findViewById(R.id.carddemo_suggested_title);
			TextView member = (TextView) view
					.findViewById(R.id.carddemo_suggested_memeber);
			TextView subtitle = (TextView) view
					.findViewById(R.id.carddemo_suggested_subtitle);
			TextView community = (TextView) view
					.findViewById(R.id.carddemo_suggested_community);
			
			ImageView locationImage = (ImageView) view.findViewById(R.id.colorBorder);
			
			title.setTypeface(font);

			if (title != null)
				title.setText(getTitle());

			if (member != null)
				member.setText(getMember());

			if (subtitle != null)
				subtitle.setText(getSubtitle());

			if (community != null)
				community.setText(getCommunity());
			
			if (locationImage != null) {
				GradientDrawable bgShape = (GradientDrawable) locationImage.getBackground();
				int color = Color.parseColor(event.getParent().getColor());
				bgShape.setColor(color);
			}
			
		}
	}
	
	@Override
	public String toString(){
		return this.getTitle()+" "+this.member+" "+this.subtitle+" "+this.community;
	}
}


