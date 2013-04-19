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
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.graphics.Typeface;
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

	private int bmpScrollX, bmpScrollY = 0;
	private int bmpWidth, bmpHeight = 0;
	private ImageButton imageButtonLocateMe;
	private SlideMenu slideMenu;
	private TextView headerTitle, mapView;
	private WebView webView;
	private String myCurrentLocation;
	private AlarmHelper alarmHelper;
	private ContextAwareHelper contextAwareHelper;
	private ArrayList<Location> locations;
	private ArrayList<Bssid> bssids;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		startMenu(Constants.SCROLL_MENU_TIME);

		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		mapView = (TextView) findViewById(R.id.mapTextViewMap);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		mapView.setTypeface(dtuFont);
		init();
		setWebView();
		setLocateMe();
	}

	private void init() {
		readJson();
		alarmHelper = new AlarmHelper(this.getApplicationContext());
		contextAwareHelper = new ContextAwareHelper(
				this.getApplicationContext(), bssids, locations);
	}

	@Override
	protected void onDestroy() {
		super.onStop();
		alarmHelper.unregisterAlarmManager();
	}

	private void setLocateMe() {
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
				myCurrentLocation = "a";
				if (myCurrentLocation.equals("l1")) {
					setInitialScroll("Library");					
					webView.scrollTo(bmpScrollX, bmpScrollY);
				} else if (myCurrentLocation.equals("l2")) {
					setInitialScroll("Oticon");
					webView.scrollTo(bmpScrollX, bmpScrollY);
				} else if (myCurrentLocation.equals("l3")) {
					setInitialScroll("Kantine");
					webView.scrollTo(bmpScrollX, bmpScrollY);
				} else if (myCurrentLocation.equals("l0")) {
					setInitialScroll("Sportshal");
					webView.scrollTo(bmpScrollX, bmpScrollY);
				} else {
					Toast.makeText(
							getApplicationContext(),
							"404. Our Russian sattelite cannot localize you in B101.",
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	private void setInitialScroll(String stringExtra) {
		bmpWidth = (int) findViewById(R.id.mapWebViewMapOfThe101)
				.getMeasuredWidth() / 2;
		bmpHeight = (int) findViewById(R.id.mapWebViewMapOfThe101)
				.getMeasuredHeight() / 2;
		if (stringExtra.equals("Library")) {
			bmpScrollX = 770 - bmpWidth;
			bmpScrollY = 560 - bmpHeight;
			Toast.makeText(
					getApplicationContext(),
					"1:" + bmpScrollX + "&" + bmpScrollY,
					Toast.LENGTH_SHORT).show();
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Oticon")) {
			bmpScrollX = 2100 - bmpWidth;
			bmpScrollY = 240 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Kantine")) {
			bmpScrollX = 1420 - bmpWidth;
			bmpScrollY = 590 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		} else if (stringExtra.equals("Sportshal")) {
			bmpScrollX = 2130 - bmpWidth;
			bmpScrollY = 1090 - bmpHeight;
			correctX(bmpScrollX);
			correctY(bmpScrollY);
		}
	}

	private void correctY(int Y) {
		if (Y < 0) {
			bmpScrollY = 0;
		} else if (Y + bmpHeight * 2 > 1671) {
			bmpScrollY = 1671 - bmpHeight * 2;
		}
	}

	private void correctX(int X) {
		if (X < 0) {
			bmpScrollX = 0;
		} else if (X + bmpWidth * 2 > 2482) {
			bmpScrollX = 2482 - bmpWidth * 2;
		}
	}

	private void setWebView() {
		webView = (WebView) findViewById(R.id.mapWebViewMapOfThe101);
		webView.loadDataWithBaseURL("file:///android_asset/images/",
				getHtmlFromAsset(), "text/html", "utf-8", null);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webView.getSettings().setBuiltInZoomControls(false);
				webView.getSettings().setUseWideViewPort(true);
				webView.setInitialScale(100);
			}
		});

		webView.setPictureListener(new PictureListener() {
			@Override
			public void onNewPicture(WebView view, Picture picture) {
				Intent intent = getIntent();
				setInitialScroll(intent.getStringExtra(Constants.EXTRA_START));
				webView.scrollTo(bmpScrollX, bmpScrollY);
			}
		});
	}

	private String getHtmlFromAsset() {
		InputStream is;
		StringBuilder builder = new StringBuilder();
		String htmlString = null;
		try {
			is = getAssets().open("map.html");
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
			Toast.makeText(this, "Don't milk nipples when they are soft.",
					Toast.LENGTH_SHORT).show();
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