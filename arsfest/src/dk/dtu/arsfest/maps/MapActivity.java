package dk.dtu.arsfest.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.analytics.tracking.android.EasyTracker;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.alarms.AlarmHelper;
import dk.dtu.arsfest.context.ContextAwareHelper;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;

public class MapActivity extends SherlockActivity {

	private ImageButton imageButtonLocateMe;
	private TextView headerTitle, mapView;
	private WebView webView;
	private String myCurrentLocation;
	private AlarmHelper alarmHelper;
	private ContextAwareHelper contextAwareHelper;
	private ArrayList<Location> locations;
	private ArrayList<Bssid> bssids;
	private MapScroller myMapScroll;
	private int scale = 80;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		//super.startMenu(Constants.SCROLL_MENU_TIME);

//		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
//		headerTitle.setTypeface(Utils.getTypeface(this,
//				Constants.TYPEFONT_PROXIMANOVA));
//		headerTitle.setText(Constants.APP_NAME);

		mapView = (TextView) findViewById(R.id.mapTextViewMap);
		mapView.setTypeface(Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS));
		
		initiateLocationAwarness();
		Intent intent = getIntent();
		if (intent.getStringExtra(Constants.EXTRA_START) == null) {
			setWebView("mapDefault.html", "");
		} else {
			setWebView("mapDefault.html",
					intent.getStringExtra(Constants.EXTRA_START));
		}
		setLocateMeFuction();
	}

	/**
	 * Destroys broadcast receiver
	 * 
	 * @author AA
	 */
	@Override
	protected void onDestroy() {
		super.onStop();
		alarmHelper.unregisterAlarmManager();
	}

	/**
	 * Initiates location awareness
	 * 
	 * @author AA
	 */
	private void initiateLocationAwarness() {
		alarmHelper = new AlarmHelper(this.getApplicationContext());
		contextAwareHelper = new ContextAwareHelper(
				this.getApplicationContext(), bssids, locations);
	}

	/**
	 * Function scrolls to current location of the user
	 * 
	 * @author AA
	 */
	private void setLocateMeFuction() {
		imageButtonLocateMe = (ImageButton) findViewById(R.id.imageButtonLocateMe);

		imageButtonLocateMe.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					imageButtonLocateMe
							.setImageResource(R.drawable.map_locate_me_pressed);
					imageButtonLocateMe
							.setBackgroundResource(R.drawable.purple_bg);
					return false;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					imageButtonLocateMe
							.setImageResource(R.drawable.map_locate_me);
					imageButtonLocateMe.setBackgroundColor(getResources().getColor(R.color.trans));
					return false;
				}
				return false;
			}
		});

		imageButtonLocateMe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				alarmHelper.registerAlarmManager();
				contextAwareHelper.startContextAwareness();
				myCurrentLocation = contextAwareHelper.getCurrentLocation();
				scale = (int) (webView.getScale() * 100);
				if (myCurrentLocation != null) {
					myMapScroll = new MapScroller(myCurrentLocation, webView
							.getMeasuredWidth(), webView.getMeasuredHeight(),
							webView.getScale());
					setWebView(myMapScroll.getCurrentHTML(), myCurrentLocation);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"Unfortunately we cannot localize you in B101",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	/**
	 * Function scrolls to desired initial location
	 * 
	 * @author AA
	 */
	private void setWebView(String fileName, final String initialScroll) {
		// Setting basic parameters
		webView = (WebView) findViewById(R.id.mapWebViewMapOfThe101);
		webView.loadDataWithBaseURL("file:///android_asset/images/",
				getHtmlFromAsset(fileName), "text/html", "utf-8", null);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webView.getSettings().setBuiltInZoomControls(true);
				webView.getSettings().setUseWideViewPort(true);
				webView.setInitialScale(scale);
			}
		});

		// When content is loaded, scroll map to desired location
		webView.setPictureListener(new PictureListener() {
			@Override
			public void onNewPicture(WebView view, Picture picture) {
				myMapScroll = new MapScroller(initialScroll, webView
						.getMeasuredWidth(), webView.getMeasuredHeight(),
						webView.getScale());
				webView.scrollTo(myMapScroll.getBmpScrollX(),
						myMapScroll.getBmpScrollY());
			}
		});

		// When scroll is finished, any gesture user makes should not scroll
		// back to initial location
		webView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				webView.setPictureListener(null);
				return false;
			}
		});
	}

	/**
	 * 
	 * @return Function returns the content of the HTML file
	 * @author AA
	 */
	private String getHtmlFromAsset(String fileName) {
		InputStream is;
		StringBuilder builder = new StringBuilder();
		String htmlString = null;
		try {
			is = getAssets().open(fileName);
			if (is != null) {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}

				htmlString = builder.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return htmlString;
	}

	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);
	}
}