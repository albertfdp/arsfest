package dk.dtu.arsfest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class Utils {
	
	public static Typeface getTypeface(Context context, String typeface) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/" + typeface);
  	}
	
	public static Date getFormattedDate(String eventDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm", Locale.FRANCE);
		Date formattedDate;
		try {
			formattedDate = (Date) formatter.parse(eventDate);
		} catch (ParseException e) {
			Log.e(Constants.TAG, "Could not parse date.");
			formattedDate = new Date();
		}
		return formattedDate;
	}
	
	public static String getEventTime(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm", Locale.FRANCE);
		return formatter.format(date);
	}
}
