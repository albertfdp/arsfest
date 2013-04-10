package dk.dtu.arsfest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


import android.content.Context;
import android.graphics.Typeface;
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
	
	public static Date getFormattedDate(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm");
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
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

}
