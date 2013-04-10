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
			Typeface typeface = Utils.getTypeface(this.context, Constants.TYPEFONT_NEOSANS);
			if (eventTitle != null) {
				eventTitle.setText(event.getName());
				eventTitle.setTypeface(typeface);
				eventTime.setTypeface(typeface);
				eventLocation.setTypeface(typeface);
				eventLocation.setText(getLocationName(event.getLocation()));
				eventTime.setText(Utils.getEventStringTime(event.getStartTime()));
				if (event.hasFinished(Utils.getCurrentDate())) {
					view.setBackgroundColor(context.getResources().getColor(R.color.flat_grey));
				}
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
