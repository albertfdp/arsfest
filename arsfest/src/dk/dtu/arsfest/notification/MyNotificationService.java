package dk.dtu.arsfest.notification;

import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.utils.Constants;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyNotificationService extends Service {

	@Override
	public int onStartCommand(Intent receivedIntent, int myFlags, int startId) {
		NotificationHelper myNotificationHelper = new NotificationHelper();
		myNotificationHelper.showEventAlert(getApplicationContext(), (Event) receivedIntent.getParcelableExtra(Constants.EXTRA_EVENT));		
		return super.onStartCommand(receivedIntent, myFlags, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
}