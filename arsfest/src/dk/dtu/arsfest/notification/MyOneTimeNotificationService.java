package dk.dtu.arsfest.notification;

import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyOneTimeNotificationService extends Service {
	
	private NotificationManager myNotificationManager;
	
	@Override
	public int onStartCommand(Intent receivedIntent, int myFlags, int startId) {
		myNotificationManager = (NotificationManager) getApplicationContext()
				.getSystemService(Context.NOTIFICATION_SERVICE);

		/* Clicking the notification */
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
				0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder myNotificationBuilder = new NotificationCompat.Builder(
				getApplicationContext())
				.setContentText("It's today! See you at the party!")
				.setContentTitle("DTU Arsfest 2013")
				.setSmallIcon(R.drawable.content_event)
				.setAutoCancel(true)
				.setContentIntent(contentIntent)

				.setDefaults(
						Notification.DEFAULT_LIGHTS
								| Notification.DEFAULT_VIBRATE);
		/* Create notification with builder */
		Notification currentNotification = myNotificationBuilder.build();
		myNotificationManager.notify(0, currentNotification);
		
		
		
		return super.onStartCommand(receivedIntent, myFlags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}
