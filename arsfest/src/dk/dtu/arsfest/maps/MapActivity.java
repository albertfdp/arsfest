package dk.dtu.arsfest.maps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;
import dk.dtu.arsfest.AboutActivity;
import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.alarms.AlarmHelper;
import dk.dtu.arsfest.context.ContextAwareHelper;
import dk.dtu.arsfest.model.Bssid;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.parser.JSONParser;
import dk.dtu.arsfest.preferences.UserSettings;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebView.PictureListener;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MapActivity extends Activity implements
		OnSlideMenuItemClickListener {

	private ImageButton imageButtonLocateMe;
	private SlideMenu slideMenu;
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
		startMenu(Constants.SCROLL_MENU_TIME);

		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);

		mapView = (TextView) findViewById(R.id.mapTextViewMap);
		mapView.setTypeface(Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS));

		initiateLocationAwarness();
		Intent intent = getIntent();
		setWebView("mapDefault.html", intent.getStringExtra(Constants.EXTRA_START));
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
		readJson();
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
							.setBackgroundResource(R.drawable.map_filter_background);
					return false;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					imageButtonLocateMe
							.setImageResource(R.drawable.map_locate_me);
					imageButtonLocateMe.setBackgroundColor(00000000);
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
							"404. Our Russian sattelite cannot localize you in B101.",
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

	private void readJson() {

		JSONParser jsonParser;

		try {
			InputStream is = getAssets().open("data.JSON");
			jsonParser = new JSONParser(is);

			this.locations = jsonParser.readLocations();
			this.bssids = jsonParser.readBssid();

			// all events
			ArrayList<Event> allEvents = new ArrayList<Event>();
			for (Location location : this.locations) {
				for (Event event : location.getEvents()) {
					if (event != null)
						allEvents.add(event);
				}
			}
			this.locations.add(new Location("0", "ALL", allEvents));

			// get position of 'all'
			int allPos = this.locations.size() - 1;
			Collections.swap(this.locations, allPos, 0);

			for (Location location : locations) {
				location.sortEventsByTime();
			}

		} catch (IOException e) {
			Log.i("ARSFEST", e.getMessage());
		}

	}

	/**
	 * Method showing the accordeon slide menu at the left hand side
	 * 
	 * @param durationOfAnimation
	 *            : Duration of Animation
	 * @author AA
	 */
	private void startMenu(int durationOfAnimation) {
		slideMenu = new SlideMenu(this, R.menu.slide, this, durationOfAnimation);
		slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
		slideMenu.init(this, R.menu.slide, this, durationOfAnimation);
		slideMenu.setFont(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		ImageButton imageButtonAccordeon = (ImageButton) findViewById(R.id.actionBarAccordeon);
		imageButtonAccordeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slideMenu.show();
			}
		});
	}

	/**
	 * Menu
	 */
	@Override
	public void onSlideMenuItemClick(int itemId) {
		switch (itemId) {
		case R.id.item_programme:
			Intent intentProgramme = new Intent(this, MainActivity.class);
			this.startActivity(intentProgramme);
			break;
		case R.id.item_map:
			break;
		case R.id.item_settings:
			Intent intentSettings = new Intent(this, UserSettings.class);
			this.startActivityForResult(intentSettings,
					Constants.RESULT_SETTINGS);
			break;
		case R.id.item_about:
			Intent intentAbout = new Intent(this, AboutActivity.class);
			this.startActivity(intentAbout);
			break;
		}
	}

	/**
	 * Menu
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.icon:
			slideMenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}