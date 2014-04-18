package dk.dtu.arsfest.notification;

import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class ArsfestNotification {

	public void setAlarm(Context c) {
		if (System.currentTimeMillis() < Utils.getFormattedDate(
				Constants.NotificationTime).getTime()) {
			AlarmManager alarmManager = (AlarmManager) c
					.getSystemService(Context.ALARM_SERVICE);
			Long time = Utils.getFormattedDate(Constants.NotificationTime)
					.getTime();
			Intent intentAlarm = new Intent(c, AlarmReciever.class);
			alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent
					.getBroadcast(c, 1, intentAlarm,
							PendingIntent.FLAG_UPDATE_CURRENT));
		}
	}

	public void notificationBuilder(Context c) {
		Intent resultIntent = new Intent(c, MainActivity.class);
		PendingIntent mPendingIntent = PendingIntent.getActivity(c, 0,
				resultIntent, 0);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c)
				.setContentTitle(c.getString(R.string.notification_header))
				.setContentText(c.getString(R.string.notification_subtext))
				.setSmallIcon(R.drawable.ic_action_event)
				.setContentIntent(mPendingIntent).setAutoCancel(true)
				.setDefaults(Notification.DEFAULT_ALL);
		NotificationManager mNotificationManager = (NotificationManager) c
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(0, mBuilder.build());
	}
}
