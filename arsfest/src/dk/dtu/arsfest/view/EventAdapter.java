package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter <Event>{
	
	private ArrayList<Event> events;
	private ArrayList<Location> locations;
	private Context context;
	
	public EventAdapter (Context context, int layoutResourceId, ArrayList<Event> events, ArrayList<Location> locations) {
		super(context, layoutResourceId, events);
		this.context = context;
		this.events = events;
		this.locations = locations;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.event_item, null);
		}
		Event event = events.get(position);
		if (event != null) {
			TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
			TextView eventLocation = (TextView) view.findViewById(R.id.event_location);
			TextView eventTime = (TextView) view.findViewById(R.id.event_time);
			TextView eventStatus = (TextView) view.findViewById(R.id.event_status);
			ImageView eventImage = (ImageView) view.findViewById(R.id.event_image);
			Typeface typeface = Utils.getTypeface(this.context, Constants.TYPEFONT_NEOSANS);
			if (eventTitle != null) {
				eventTitle.setText(event.getName());
				eventTitle.setTypeface(typeface);
				eventTime.setTypeface(typeface);
				eventLocation.setTypeface(typeface);
				eventLocation.setText(getLocationName(event.getLocation()));
				eventTime.setText(Utils.getEventStringTime(event.getStartTime()));
				if (event.hasFinished(Utils.getCurrentDate())) {
					eventTitle.setTextColor(context.getResources().getColor(R.color.grey));
					eventLocation.setTextColor(context.getResources().getColor(R.color.grey));
					eventTime.setTextColor(context.getResources().getColor(R.color.grey));
					view.setBackgroundColor(context.getResources().getColor(R.color.flat_greyed));
					eventStatus.setText(context.getResources().getString(R.string.event_finished));
				} else {
					eventTitle.setTextColor(context.getResources().getColor(R.color.flat_grey));
					eventLocation.setTextColor(context.getResources().getColor(R.color.flat_grey));
					eventTime.setTextColor(context.getResources().getColor(R.color.flat_blue));
					view.setBackgroundColor(context.getResources().getColor(R.color.white));
					eventStatus.setText("");
					if (event.isRemark())
						view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.customborder));
				}
				eventImage.setImageDrawable(Utils.getDrawable(context, event.getTheme()));
			}
			
		}
		return view;
	}
	
	private String getLocationName(String locationId) {
		for (Location location : locations) {
			if (location.getId().equalsIgnoreCase(locationId))
				return location.getName();
		}
		return "Unknown";
	}

}
