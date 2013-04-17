package dk.dtu.arsfest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Event;
//import dk.dtu.arsfest.model.Predicate;
//import dk.dtu.arsfest.model.Predicate.IPredicate;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Utils {
	
	public static Typeface getTypeface(Context context, String typeface) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/" + typeface);
  	}
	
	public static Date getCurrentDate() {
		//return new Date();
		return getStartDate("03-05-2013:21:10");
	}
	
	public static Date getStartDate(String stringDate) {
		return getFormattedDate(stringDate);
	}
	
	public static boolean hasFestStarted() {
		return getCurrentDate().after(getStartDate(Constants.FEST_START_TIME));
	}
	
	public static boolean hasFestFinished() {
		return getCurrentDate().after(getStartDate(Constants.FEST_END_TIME));
	}
	
	public static Date getFormattedDate(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm", Locale.FRANCE);
		//formatter.setTimeZone(TimeZone.getTimeZone("CEST"));
		Date startDate;
		try {
			startDate = (Date) formatter.parse(stringDate);
		} catch (ParseException e) {
			Log.e(Constants.TAG, "Error parsing date. Setting start date to current date");
			e.printStackTrace();
			startDate = new Date();
		}
		return startDate;
	}
	
	public static String getEventStringTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.FRANCE);
		return sdf.format(date);
	}
	
	public static Drawable getDrawable(Context context, String theme) {
		if (theme.equals(Constants.EVENT_TYPE_MUSIC)) {
			return context.getResources().getDrawable(R.drawable.music);
		} else if (theme.equals(Constants.EVENT_TYPE_OFFICIAL)) {
			return context.getResources().getDrawable(R.drawable.prize);
		} else if (theme.equals(Constants.EVENT_TYPE_FOOD)) {
			return context.getResources().getDrawable(R.drawable.shop);
		} else if (theme.equals(Constants.EVENT_TYPE_DANCE)) {
			return context.getResources().getDrawable(R.drawable.ticket);
		} else {
			return context.getResources().getDrawable(R.drawable.music);
		}
	}
	
	/*public static ArrayList<Event> filterEventsByTheme(ArrayList<Event> events, final String theme) {
		ArrayList<Event> selectedEvents = (ArrayList<Event>) Predicate.filter(events,
				new IPredicate<Event>() {

					@Override
					public boolean apply(Event event) {
						return event.getTheme().equalsIgnoreCase(theme);
					}
			
		});
		return selectedEvents;	
	}*/

}
