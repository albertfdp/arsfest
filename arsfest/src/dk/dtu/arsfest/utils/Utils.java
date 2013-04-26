package dk.dtu.arsfest.utils;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Location;


import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class Utils {
	
	public static Typeface getTypeface(Context context, String typeface) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/" + typeface);
  	}
	
	public static Date getCurrentDate() {
		//return getStartDate("03-05-2013:17:29"); // just before
		//return new Date(); // now
		return getStartDate("03-05-2013:21:20"); // during
		//return getStartDate("03-06-2013:21:10"); // over
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
	
	public static int getDrawable(String theme) {
		if (theme.equals(Constants.EVENT_TYPE_MUSIC)) {
			return R.drawable.guitar;
		} else if (theme.equals(Constants.EVENT_TYPE_OFFICIAL)) {
			return R.drawable.official;
		} else if (theme.equals(Constants.EVENT_TYPE_FOOD)) {
			return R.drawable.dinner_icon2;
		} else if (theme.equals(Constants.EVENT_TYPE_DANCE)) {
			return R.drawable.dancing;
		} else {
			return R.drawable.note;
		}
	}
	
	public static Drawable loadImageFromAsset(Context context, String filename) {
        // load image
        try {
            // get input stream
            InputStream ims = context.getAssets().open("images/" + filename);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            return d;
        }
        catch(IOException ex) {
            return null;
        }
 
    }
	
	public static Drawable getDrawable(Context context, String theme) {
		if (theme.equals(Constants.EVENT_TYPE_MUSIC)) {
			return context.getResources().getDrawable(R.drawable.guitar);
		} else if (theme.equals(Constants.EVENT_TYPE_OFFICIAL)) {
			return context.getResources().getDrawable(R.drawable.calendar);
		} else if (theme.equals(Constants.EVENT_TYPE_FOOD)) {
			return context.getResources().getDrawable(R.drawable.dinner_icon2);
		} else if (theme.equals(Constants.EVENT_TYPE_DANCE)) {
			return context.getResources().getDrawable(R.drawable.dancing);
		} else {
			return context.getResources().getDrawable(R.drawable.note);
		}
	}
	
	public static Location getLocationById(ArrayList<Location> locations, String id) {
		for (Location location : locations) {
			if (location.getId().equals(id))
				return location;
		}
		return null;
	}
}
