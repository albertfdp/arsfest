/*
 * To start map in a specific location, to the intent please add:
 * String extra (Constants.MapStartLocation, Constants.<STARTING LOCATION>)
 * boolean extra (Constants.MapShowPin, Constants.<TRUE/FALSE IF YOU WANT TO PLACE A PIN>)
 */

package dk.dtu.arsfest.localization;

import com.actionbarsherlock.view.MenuItem;
import com.devspark.sidenavigation.SideNavigationView;
import com.devspark.sidenavigation.SideNavigationView.Mode;

import dk.dtu.arsfest.BaseActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.navigation.SideNavigation;
import dk.dtu.arsfest.sensors.BSSIDService;
import dk.dtu.arsfest.sensors.LocationService;
import dk.dtu.arsfest.sensors.OrientationService;
import dk.dtu.arsfest.utils.Constants;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Picture;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MapActivity extends BaseActivity {

	private SideNavigationView sideNavigationView;
	private SensorsReceiver mStateReceiver;
	private WebView mMapWebView;
	private ImageButton mLocateButton;
	private ProgressBar mProgressBar;
	private LocalizationHelper mLocalization;
	private ScrollHelper mMapScroll;
	private double mLatitude = 0, mLongitude = 0, mAccuracy = 0, mAzimuth = 0;
	private boolean mProviders = false;
	private SharedPreferences mPreferences;
	private Handler uCantHandleThat = new Handler();
	private Runnable runForYourLife = new Runnable() {
		public void run() {
			mProgressBar.setVisibility(View.INVISIBLE);
			Toast.makeText(getApplicationContext(),
					Constants.UnsuccessfulLocatization, Toast.LENGTH_LONG)
					.show();
		}
	};
	private Intent mIntentOrientation, mIntentLocation;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		mProgressBar.setVisibility(View.VISIBLE);

		mIntentOrientation = new Intent(getApplicationContext(),
				OrientationService.class);
		mIntentLocation = new Intent(getApplicationContext(),
				LocationService.class);

		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		SideNavigation mSideNavigation = new SideNavigation(sideNavigationView,
				getApplicationContext());
		mSideNavigation.getSideNavigation(Mode.LEFT);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setTitle("Map");
		mLocalization = new LocalizationHelper();

		Intent i = getIntent();
		setWebView(i.getStringExtra(Constants.MapStartLocation),
				i.getBooleanExtra(Constants.MapShowPin, false));
		mPreferences = getApplicationContext().getSharedPreferences(
				Constants.SharedPreferences, 0);
		setLocateMeButton();
	}

	private void setLocateMeButton() {
		mLocateButton = (ImageButton) findViewById(R.id.imageButtonLocateMe);
		mLocateButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mLocateButton
							.setImageResource(R.drawable.map_locate_me_pressed);
					mLocateButton.setBackgroundResource(R.drawable.purple_bg);
					return false;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mLocateButton.setImageResource(R.drawable.map_locate_me);
					mLocateButton.setBackgroundColor(getResources().getColor(
							R.color.trans));
					return false;
				}
				return false;
			}
		});

		mLocateButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mProgressBar.setVisibility(View.VISIBLE);
				startService(mIntentLocation);
				startService(mIntentOrientation);

				if (!mProviders) {
					BSSIDService mBSSID = new BSSIDService(
							getApplicationContext());
					mBSSID.getUpdate();
					if (mBSSID.isNetworkAvailable()) {

						// TODO Handling the BSSID data

						Toast.makeText(
								getApplicationContext(),
								"Your location based on BSSID:"
										+ mBSSID.getCurrentLocation(),
								Toast.LENGTH_LONG).show();

					} else {
						if (!mProviders) {
							if (!mPreferences.getBoolean(
									Constants.NetworkRequest, false)) {
								buildAlertMessage(
										Constants.NetworkInfo,
										android.provider.Settings.ACTION_WIFI_SETTINGS,
										Constants.NetworkRequest);
							}
						}
					}
				}
				uCantHandleThat.postDelayed(runForYourLife,
						Constants.LocateMeTreshold);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mStateReceiver = new SensorsReceiver();
		IntentFilter intentLocationFilter = new IntentFilter();
		intentLocationFilter.addAction(Constants.LocationActionTag);
		intentLocationFilter.addAction(Constants.OrientationActionTag);
		intentLocationFilter.addAction(Constants.ProvidersActionTag);
		registerReceiver(mStateReceiver, intentLocationFilter);
		mLatitude = 0;
		mLongitude = 0;
		mAccuracy = 0;
		mAzimuth = 0;
		mMapWebView.loadUrl("javascript:hide()");
	}

	@Override
	protected void onPause() {
		if (isMyServiceRunning(LocationService.class.getName())) {
			stopService(mIntentLocation);
		}
		if (isMyServiceRunning(OrientationService.class.getName())) {
			stopService(mIntentOrientation);
		}
		unregisterReceiver(mStateReceiver);
		super.onPause();
	}

	private boolean isMyServiceRunning(String s) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager
				.getRunningServices(Integer.MAX_VALUE)) {
			if (s.equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
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

				mAccuracy = mReceivedIntent.getFloatExtra(
						Constants.LocationFlagAccuracy, 0);
				mLongitude = mReceivedIntent.getDoubleExtra(
						Constants.LocationFlagLongitude, 0);
				mLatitude = mReceivedIntent.getDoubleExtra(
						Constants.LocationFlagLatitude, 0);

				mProgressBar.setVisibility(View.INVISIBLE);

				// TODO Handling GPS/Network data

				Toast.makeText(
						getApplicationContext(),
						"Accuracy: " + mAccuracy + "; Latitude: " + mLatitude
								+ "; Longitude " + mLongitude + "; Azimuth: "
								+ mAzimuth, Toast.LENGTH_LONG).show();

				mMapWebView.loadUrl("javascript:position(370, 420)");

				uCantHandleThat.removeCallbacks(runForYourLife);
			}

			if (mReceivedIntent.getAction().equals(
					Constants.OrientationActionTag)) {
				setAzimuth(mReceivedIntent.getDoubleExtra(
						Constants.OrientationFlagAzimuth, 0));
				mMapWebView.loadUrl("javascript:rotation(" + mAzimuth + ")");
			}

			if (mReceivedIntent.getAction()
					.equals(Constants.ProvidersActionTag)) {
				mProviders = mReceivedIntent.getBooleanExtra(
						Constants.GPSProvider, false)
						|| mReceivedIntent.getBooleanExtra(
								Constants.NetworkProvider, false);

				if (!mProviders) {
					if (!mPreferences.getBoolean(Constants.GPSRequest, false)) {
						buildAlertMessage(
								Constants.GPSInfo,
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS,
								Constants.GPSRequest);
					}
				}
			}
		}
	}

	private void setAzimuth(double mAzimuth) {
		this.mAzimuth = mAzimuth - Constants.AzimuthTreshold;
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void setWebView(final String mInitialLocation, boolean ifPIN) {
		mMapWebView = (WebView) findViewById(R.id.webViewMap);
		GenerateHTML mHTML = new GenerateHTML(
				mLocalization.getScroll(mInitialLocation), ifPIN);
		mMapWebView.loadDataWithBaseURL("file:///android_asset/images/",
				mHTML.getmHTML(), "text/html", "utf-8", null);
		mMapWebView.getSettings().setJavaScriptEnabled(true);

		mMapWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView v, String url) {
				super.onPageFinished(v, url);
				v.getSettings().setUseWideViewPort(true);
				v.getSettings().setLoadWithOverviewMode(true);
				v.getSettings().setBuiltInZoomControls(false);
				v.getSettings().setDisplayZoomControls(false);
				v.getSettings().setSupportZoom(false);
				v.setInitialScale(100);
			}
		});

		mMapWebView.setPictureListener(new PictureListener() {

			@Override
			@Deprecated
			public void onNewPicture(WebView view, Picture picture) {
				// view.getSettings().setDefaultZoom(ZoomDensity.FAR);
				view.getSettings().setUseWideViewPort(false);
				mMapScroll = new ScrollHelper(mLocalization
						.getScroll(mInitialLocation), new int[] {
						view.getMeasuredWidth(), view.getMeasuredHeight() },
						view.getScale());
				view.scrollTo(mMapScroll.getScroll()[0],
						mMapScroll.getScroll()[1]);
				mProgressBar.setVisibility(View.INVISIBLE);
			}
		});

		mMapWebView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent e) {
				mMapWebView.setPictureListener(null);
				mProgressBar.setVisibility(View.INVISIBLE);
				return false;
			}
		});
	}

	private void buildAlertMessage(String mMessage, final String mIntent,
			final String mKey) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(mMessage)
				.setCancelable(false)
				.setPositiveButton(Constants.AlertDialogEnable,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callSettingIntent = new Intent(mIntent);
								startActivity(callSettingIntent);
							}
						});
		alertDialogBuilder.setNeutralButton(Constants.AlertDialogCancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		alertDialogBuilder.setNegativeButton(Constants.AlertDialogStopAsking,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						SharedPreferences mPreferences = getApplicationContext()
								.getSharedPreferences(
										Constants.SharedPreferences, 0);
						Editor mEditor = mPreferences.edit();
						mEditor.putBoolean(mKey, true);
						mEditor.commit();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}
}