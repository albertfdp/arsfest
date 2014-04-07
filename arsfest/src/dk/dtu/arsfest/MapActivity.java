package dk.dtu.arsfest;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.sensors.LocationService;
import dk.dtu.arsfest.utils.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

public class MapActivity extends BaseActivity {

	//TODO manage the service
	//TODO manage the broadcast receiver
	
	private SideNavigationView sideNavigationView;
	private SensorsReceiver mStateReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		SideNavigation mSideNavigation = new SideNavigation(sideNavigationView,
				getApplicationContext());
		mSideNavigation.getSideNavigation(Mode.LEFT);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("Map");

		mStateReceiver = new SensorsReceiver();
		IntentFilter intentLocationFilter = new IntentFilter();
		intentLocationFilter.addAction(Constants.LocationActionTag);
		// intentLocationFilter.addAction(Constants.OrientationActionTag);
		registerReceiver(mStateReceiver, intentLocationFilter);

		startService(new Intent(getApplicationContext(), LocationService.class));
		// Intent intent = getIntent();
		// initView(intent.getParcelableExtra(Constants.EXTRA_MAP));
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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

			if (mReceivedIntent.getAction()
					.equals(Constants.LocationActionTag)) {
				Toast.makeText(
						getApplicationContext(),
						""
								+ mReceivedIntent.getDoubleExtra(
										Constants.LocationFlagLatitude, 0)
								/*+ mReceivedIntent.getBooleanExtra(
										Constants.LocationGPSProvider, true)
								+ mReceivedIntent.getBooleanExtra(
										Constants.LocationNetworkProvider, false)*/,
						Toast.LENGTH_LONG).show();
			}

		}
	}
}
