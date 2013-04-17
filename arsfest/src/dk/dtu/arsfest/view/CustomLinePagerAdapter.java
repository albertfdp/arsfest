package dk.dtu.arsfest.view;

import java.util.ArrayList;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.event.EventActivity;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomLinePagerAdapter extends PagerAdapter {
	
	protected transient Activity mContext;
	private ArrayList<Location> locations;
	private ArrayList<Event> events;
	
	public CustomLinePagerAdapter (Activity context, ArrayList<Location> locations, ArrayList<Event> events) {
		this.mContext = context;
		this.events = events;
		this.locations = locations;
	}
	
	@Override
	public void destroyItem(View container, int position, Object view) {
		((ViewPager) container).removeView((View) view);
	}
	
	@Override
	public Object instantiateItem(View container, int position) {
		
		Event event = events.get(position);
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (this.locations.size() > 0) {
		
			View view = inflater.inflate(R.layout.event_item, null);
			view.setTag(position);
			
			/* init style */
			TextView eventTitle = (TextView) view.findViewById(R.id.event_title);
			TextView eventLocation = (TextView) view.findViewById(R.id.event_location);
			TextView eventTime = (TextView) view.findViewById(R.id.event_time);
			TextView eventStatus = (TextView) view.findViewById(R.id.event_status);
			ImageView eventImage = (ImageView) view.findViewById(R.id.event_image);
			Typeface neoType = Utils.getTypeface(mContext, Constants.TYPEFONT_NEOSANS);
			eventTitle.setTypeface(neoType);
			eventLocation.setTypeface(neoType);
			eventTime.setTypeface(neoType);
			eventStatus.setTypeface(neoType);
			
			eventTitle.setText(event.getName());
			eventLocation.setText(getLocationName(event.getLocation()));
			eventTime.setText(Utils.getEventStringTime(event.getStartTime()));
			eventImage.setImageDrawable(Utils.getDrawable(mContext, event.getTheme()));	
			eventStatus.setText(mContext.getResources().getString(R.string.event_happening_now));
			
			view.setOnClickListener(new OnClickListener() {
	
				@Override
				public void onClick(View v) {
					 int position = (Integer) v.getTag();
					 
					 Event clickedEvent = events.get(position);
						
					 // get location of the clicked event
					 Location clickedLocation = getLocation(clickedEvent.getId());
						
					 Intent intent = new Intent(mContext, EventActivity.class);
					 intent.putExtra(Constants.EXTRA_EVENT, clickedEvent);
					 intent.putExtra(Constants.EXTRA_LOCATION, clickedLocation);
					 mContext.startActivity(intent);
				}
				
			})	;
			
			((ViewPager) container).addView(view, 0);
				
			return view;
			
		} else if (Utils.hasFestFinished()) {
			View view = inflater.inflate(R.layout.event_item, null);
			((ViewPager) container).addView(view, 0);
			return view;
		}
		View view = inflater.inflate(R.layout.countdown, null);
		((ViewPager) container).addView(view, 0);
		return view;
		
	}
	
	private Location getLocation(String locationId) {
		for (Location location : locations) {
			if (location.getId().equals(locationId)) {
				return location;
			}
		}
		return locations.get(0);
	}
	
	private String getLocationName(String locationId) {
		for (Location location : locations) {
			if (location.getId().equals(locationId)) {
				return location.getName();
			}
		}
		return "Unknown";
	}

	@Override
	public int getCount() {
		return this.events.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((View) object);
	}
	
	public float getPageWidth(int position) {
		return 1f;
	}
	
	@Override
	public void finishUpdate(View container) {}
	
	@Override
	public void restoreState(Parcelable state, ClassLoader loader) {}
	
	@Override
	public Parcelable saveState() {
		return null;
	}
	
	@Override
	public void startUpdate(View container) {}

}
