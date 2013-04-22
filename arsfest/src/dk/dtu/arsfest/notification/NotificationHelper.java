package dk.dtu.arsfest.notification;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.event.EventActivity;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class NotificationHelper {
	
	public static void showEventAlert(Context context, Event event, Location location) {
		
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		
		// what happens if the user clicks the notification
		Intent intent = new Intent(context, EventActivity.class);
		intent.putExtra(Constants.EXTRA_EVENT, event);
		intent.putExtra(Constants.EXTRA_LOCATION, location);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, 0);
		
		NotificationCompat.Builder nb = new NotificationCompat.Builder(context)
			.setWhen(event.getStartTime().getTime())
			.setContentText(location.getName())
			.setContentTitle(event.getName())
			.setSmallIcon(R.drawable.content_event)
			.setAutoCancel(true)
			.setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)
			.setContentIntent(contentIntent);
		
		/* Create notification with builder */
		Notification notification = nb.build();
		
		nm.notify(0, notification);
		
	}

}
