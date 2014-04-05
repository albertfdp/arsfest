package dk.dtu.arsfest;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.navigation.SideNavigation;

import android.os.Bundle;

public class MapActivity extends BaseActivity {

	private SideNavigationView sideNavigationView;

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
}
