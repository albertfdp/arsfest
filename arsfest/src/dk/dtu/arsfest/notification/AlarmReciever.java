package dk.dtu.arsfest.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ArsfestNotification mNotication = new ArsfestNotification();
		mNotication.notificationBuilder(context);
	}

}
