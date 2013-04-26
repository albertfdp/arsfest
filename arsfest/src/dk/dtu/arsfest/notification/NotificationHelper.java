package dk.dtu.arsfest.notification;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.event.EventActivity;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.parser.JSONParser;
import dk.dtu.arsfest.utils.Constants;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationHelper {
	
	private NotificationManager myNotificationManager;
	private ArrayList<Location> myLocations;
	
	public void showEventAlert(Context currentContext, Event inputEvent) {
		
		myNotificationManager = (NotificationManager) currentContext.getSystemService(Context.NOTIFICATION_SERVICE);
		
		/* Clicking the notification */
		Intent intent = new Intent(currentContext, EventActivity.class);
		intent.putExtra(Constants.EXTRA_EVENT, inputEvent);
		intent.putExtra(Constants.EXTRA_LOCATION, giveMeLocation(inputEvent.getLocation(), currentContext));
		PendingIntent contentIntent = PendingIntent.getActivity(currentContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTimeInMillis(System.currentTimeMillis());
		Calendar calendarEvent = Calendar.getInstance();
		calendarEvent.setTime(inputEvent.getStartTime());
		int myMinutes = (int) ((calendarEvent.getTimeInMillis() - calendarNow.getTimeInMillis()) /60000) + 1;
		
		UnderstandableLocations myLocation = new UnderstandableLocations(inputEvent.getLocation());
		NotificationCompat.Builder myNotificationBuilder = new NotificationCompat.Builder(currentContext)
			.setWhen(inputEvent.getStartTime().getTime())
			.setContentText("Starting in "+ myMinutes +" minutes in " + myLocation.getResultLocation())
			.setContentTitle(inputEvent.getName())
			.setSmallIcon(R.drawable.content_event)
			.setAutoCancel(true)
			.setContentIntent(contentIntent);
		
		SharedPreferences sharedPrefs = currentContext.getSharedPreferences(Constants.PREFS_NAME, 0);
		
		if (sharedPrefs.getBoolean(Constants.PREFERENCES_VIBRATION, true)) {
			myNotificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
		} else {
			myNotificationBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
		}
		
		/* Create notification with builder */
		Notification currentNotification = myNotificationBuilder.build();
		myNotificationManager.notify(0, currentNotification);		
	}
	
	private Location giveMeLocation(String locationId, Context currentContext) {
		JSONParser jsonParser;
		try {
			InputStream is = currentContext.getAssets().open("data.JSON");
			jsonParser = new JSONParser(is);
			myLocations = jsonParser.readLocations();
		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}
		for (Location location : myLocations) {
			if (location.getId().equals(locationId)) {
				return location;
			}
		}
		return myLocations.get(0);
	}
}
