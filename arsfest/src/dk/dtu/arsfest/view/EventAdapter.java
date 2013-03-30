package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
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
	private Context context;
	
	public EventAdapter (Context context, int layoutResourceId, ArrayList<Event> events) {
		super(context, layoutResourceId, events);
		this.context = context;
		this.events = events;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		if (view == null) {
			LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = layoutInflater.inflate(R.layout.event_item, null);
		}
		Event event = events.get(position);
		if (event != null) {
			TextView eventTitle = (TextView) view.findViewById(R.id.eventName);
			TextView eventTimeAndLocation = (TextView) view.findViewById(R.id.eventTimeAndLocation);
			Typeface typeface = Utils.getTypeface(this.context, Constants.TYPEFONT_NEOSANS);
			if (eventTitle != null) {
				eventTitle.setText(event.getName());
				eventTitle.setTypeface(typeface);
				eventTimeAndLocation.setTypeface(typeface);
			}
		}
		return view;
	}

}
