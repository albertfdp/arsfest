package dk.dtu.arsfest.view;

import java.util.ArrayList;
import java.util.Date;

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
import android.os.CountDownTimer;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

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
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		if (this.events.size() > 0) {
			Event event = events.get(position);
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
					 Location clickedLocation = getLocation(clickedEvent.getLocation());
						
					 Intent intent = new Intent(mContext, EventActivity.class);
					 intent.putExtra(Constants.EXTRA_EVENT, clickedEvent);
					 intent.putExtra(Constants.EXTRA_LOCATION, clickedLocation);
					 mContext.startActivity(intent);
				}
				
			})	;
			
			((ViewPager) container).addView(view, 0);
				
			return view;
			
		} else if (Utils.hasFestFinished()) {
			View view = inflater.inflate(R.layout.event_finished, null);
			((ViewPager) container).addView(view, 0);
			return view;
		} else {
			View view = inflater.inflate(R.layout.countdown, null);
			showCountdown(view);
			((ViewPager) container).addView(view, 0);
			return view;
		}
	}
	
	private void showCountdown(final View view) {

		CountDownTimer cdt = new CountDownTimer(Utils.getStartDate(Constants.FEST_START_TIME).getTime()
				- Utils.getCurrentDate().getTime(), 1000) {
			
		TextView days = (TextView) view.findViewById(R.id.textDays);
		TextView hours = (TextView) view.findViewById(R.id.textHours);
		TextView mins = (TextView) view.findViewById(R.id.textMinutes);
			
		@Override
		public void onTick(long millisUntilFinished) {

			int daysValue = (int) (millisUntilFinished / 86400000);
			int hoursValue = (int) (((millisUntilFinished / 1000) - (daysValue * 86400)) / 3600);
			int minsValue = (int) (((millisUntilFinished / 1000) - ((daysValue * 86400) + (hoursValue * 3600))) / 60);
			days.setText(String.valueOf(daysValue));
			hours.setText(String.valueOf(hoursValue));
			mins.setText(String.valueOf(minsValue));

		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

		}
		};

		cdt.start();

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
		if (this.events.size() > 0) {
			return this.events.size();
		} else {
			return 1;
		}
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
