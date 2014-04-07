package dk.dtu.arsfest;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.sensors.LocationService;
import dk.dtu.arsfest.utils.Constants;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MapActivity extends BaseActivity {

	private SideNavigationView sideNavigationView;
	private SensorsReceiver mStateReceiver;

	private LocationService mLocationServiceBinder = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		// Site Navigation
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		SideNavigation mSideNavigation = new SideNavigation(sideNavigationView,
				getApplicationContext());
		mSideNavigation.getSideNavigation(Mode.LEFT);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("Map");

		// TODO
		// Deal with initial view
		// Intent intent = getIntent();
		// initView(intent.getParcelableExtra(Constants.EXTRA_MAP));
	}

	@Override
	protected void onResume() {
		// Broadcast Receivers
		mStateReceiver = new SensorsReceiver();
		IntentFilter intentLocationFilter = new IntentFilter();
		intentLocationFilter.addAction(Constants.LocationActionTag);
		// intentLocationFilter.addAction(Constants.OrientationActionTag);
		registerReceiver(mStateReceiver, intentLocationFilter);

		// Bind Service
		if (mLocationServiceBinder == null) {
			Intent mIntent = new Intent(this, LocationService.class);
			bindService(mIntent, mConnection, Context.BIND_AUTO_CREATE);
		} else {
			mLocationServiceBinder.sendGPSIntent();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unbindService(mConnection);
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sideNavigationView.toggleMenu();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private class SensorsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent mReceivedIntent) {

			if (mReceivedIntent.getAction().equals(Constants.LocationActionTag)) {
				Toast.makeText(
						getApplicationContext(),
						""
								+ mReceivedIntent.getDoubleExtra(
										Constants.LocationFlagLatitude, 0)
								+ mReceivedIntent.getDoubleExtra(
										Constants.LocationFlagLongitude, 0)
								+ mReceivedIntent.getFloatExtra(
										Constants.LocationFlagAccuracy, 0),
						Toast.LENGTH_LONG).show();
			}

		}
	}

	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mLocationServiceBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mLocationServiceBinder = ((LocationService.LocationBinder) service)
					.getService();
		}
	};
}
