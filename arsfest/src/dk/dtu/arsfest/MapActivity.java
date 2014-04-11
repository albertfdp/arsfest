package dk.dtu.arsfest;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.sensors.BSSIDService;
import dk.dtu.arsfest.sensors.LocationService;
import dk.dtu.arsfest.sensors.NetworkHelper;
import dk.dtu.arsfest.sensors.OrientationService;
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
	private OrientationService mOrientationServiceBinder = null;

	private double mAzimuth = 0;

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

		BSSIDService bssid = new BSSIDService(getApplicationContext());
		// TODO
		// Deal with initial view
		// Intent intent = getIntent();
		// initView(intent.getParcelableExtra(Constants.EXTRA_MAP));

		//unregister receiver!!!
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Broadcast Receivers
		mStateReceiver = new SensorsReceiver();
		IntentFilter intentLocationFilter = new IntentFilter();
		intentLocationFilter.addAction(Constants.LocationActionTag);
		intentLocationFilter.addAction(Constants.OrientationActionTag);
		registerReceiver(mStateReceiver, intentLocationFilter);

		// Bind Services
		if (mLocationServiceBinder == null) {
			Intent mIntent = new Intent(this, LocationService.class);
			bindService(mIntent, mLocationConnection, Context.BIND_AUTO_CREATE);
		} else {
			mLocationServiceBinder.sendGPSIntent();
		}

		if (mOrientationServiceBinder == null) {
			Intent mIntent = new Intent(this, OrientationService.class);
			bindService(mIntent, mOrientationConnection,
					Context.BIND_AUTO_CREATE);
		} else {
			mLocationServiceBinder.sendGPSIntent();
		}
	}

	@Override
	protected void onDestroy() {
		unbindService(mLocationConnection);
		unbindService(mOrientationConnection);
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
				/*Toast.makeText(
						getApplicationContext(),
						"Latitude: "
								+ mReceivedIntent.getDoubleExtra(
										Constants.LocationFlagLatitude, 0)
								+ "Longitude: "
								+ mReceivedIntent.getDoubleExtra(
										Constants.LocationFlagLongitude, 0)
								+ "Accuracy: "
								+ mReceivedIntent.getFloatExtra(
										Constants.LocationFlagAccuracy, 0)
								+ "Azimuth:" + mAzimuth, Toast.LENGTH_LONG)
						.show();*/
			}

			if (mReceivedIntent.getAction().equals(
					Constants.OrientationActionTag)) {
				setAzimuth(mReceivedIntent.getDoubleExtra(
						Constants.OrientationFlagAzimuth, 0));
			}
		}
	}

	private ServiceConnection mLocationConnection = new ServiceConnection() {

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

	private ServiceConnection mOrientationConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mOrientationServiceBinder = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mOrientationServiceBinder = ((OrientationService.OrientationBinder) service)
					.getService();

		}
	};

	private void setAzimuth(double mAzimuth) {
		this.mAzimuth = mAzimuth;
	}
}
