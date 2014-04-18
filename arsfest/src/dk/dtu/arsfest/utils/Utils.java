package dk.dtu.arsfest.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.model.Location;

public class Utils {

	public static Typeface getTypeface(Context context, String typeface) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/"
				+ typeface);
	}

	public static String getJSON(Context context) {
		return context.getString(R.string.json);
	}

	public static ArrayList<Location> getProgramme(Context context) {
		ArrayList<Location> locations = new ArrayList<Location>();

		try {
			locations = FileCache
					.readCacheFile(context, Utils.getJSON(context));
		} catch (IOException e) {
			Log.e(Constants.TAG,
					"Could not read file from cache. Reading from assets, could be not updated");
			Log.e(Constants.TAG, e.getMessage());
			locations = FileCache.readFromAssets(context);
		}

		return locations;
	}

	public static Date getFormattedDate(String eventDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy:HH:mm",
				Locale.ENGLISH);
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
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm",
				Locale.ENGLISH);
		return formatter.format(date);
	}

	public static String getEventFullDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy HH:mm",
				Locale.ENGLISH);
		return formatter.format(date);
	}
}
