package dk.dtu.arsfest.event;

import java.util.Locale;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.SlideMenuSuper;
import dk.dtu.arsfest.maps.MapActivity;
import dk.dtu.arsfest.maps.MapScroller;
import dk.dtu.arsfest.model.Course;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.content.Intent;
import android.graphics.Picture;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends SlideMenuSuper {

	private TextView eventTitle;
	private TextView eventTime;
	private TextView eventLocation;
	private TextView eventDescription;
	private TextView headerTitle;
	private TextView seeItOnTheMap;
	private TextView courseFirst;
	private TextView courseFirstName;
	private TextView courseMain;
	private TextView courseMainName;
	private TextView courseDessert;
	private TextView courseDessertName;
	private TextView menuName;
	private View eventMenu;
	private ImageView eventImage;
	private LinearLayout layoutViewOfTheEvent;
	private LinearLayout layoutViewMapOfTheEvent;
	
	private int scale = 50;
	private WebView myMapWebView;
	private Event event;
	private Location location;
	private boolean comesFromAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_event);

		// Initiate the layout
		initView();

		// Read event info from intent
		Intent intent = getIntent();
		this.event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
		this.location = (Location) intent.getParcelableExtra(Constants.EXTRA_LOCATION);
		this.comesFromAll = intent.getBooleanExtra(Constants.EXTRA_EVENT_ALL, false);

		updateView();
	}

	private void initView() {
		eventTitle = (TextView) findViewById(R.id.event_title);
		eventTime = (TextView) findViewById(R.id.event_time);
		eventLocation = (TextView) findViewById(R.id.event_location);
		eventDescription = (TextView) findViewById(R.id.event_description);
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		seeItOnTheMap = (TextView) findViewById(R.id.seeItOnTheMap);
		courseFirst = (TextView) findViewById(R.id.first_course_type);
		courseFirstName = (TextView) findViewById(R.id.first_course_name);
		courseMain = (TextView) findViewById(R.id.main_course_type);
		courseMainName = (TextView) findViewById(R.id.main_course_name);
		courseDessert = (TextView) findViewById(R.id.dessert_course_type);
		courseDessertName = (TextView) findViewById(R.id.dessert_course_name);
		menuName = (TextView) findViewById(R.id.event_menu_name);
		eventMenu = findViewById(R.id.event_menu);
		eventImage = (ImageView) findViewById(R.id.event_image);
		layoutViewOfTheEvent = (LinearLayout) findViewById(R.id.layoutViewOfTheEvent);
		layoutViewMapOfTheEvent = (LinearLayout) findViewById(R.id.layoutViewMapOfTheEvent);
		
		Display display = getWindowManager().getDefaultDisplay();
		if (layoutViewOfTheEvent.getMeasuredHeight()>display.getHeight()) {
			LayoutParams myParameters = layoutViewMapOfTheEvent.getLayoutParams();
			int myHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
			myParameters.height = myHeight;
		} else {
			LayoutParams myParameters = layoutViewMapOfTheEvent.getLayoutParams();
			int myHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
			myParameters.height = myHeight;
		}

		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		eventTitle.setTypeface(dtuFont, Typeface.BOLD);
		eventTime.setTypeface(dtuFont);
		eventLocation.setTypeface(dtuFont);
		eventDescription.setTypeface(dtuFont);
		seeItOnTheMap.setTypeface(dtuFont);
		courseFirst.setTypeface(dtuFont);
		courseFirstName.setTypeface(dtuFont);
		courseMain.setTypeface(dtuFont);
		courseMainName.setTypeface(dtuFont);
		courseDessert.setTypeface(dtuFont);
		courseDessertName.setTypeface(dtuFont);
		menuName.setTypeface(dtuFont);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		headerTitle.setText(Constants.APP_NAME);
		super.startMenu(Constants.SCROLL_MENU_TIME);
		drawMap();
	}

	private void drawMap() {
		myMapWebView = (WebView) findViewById(R.id.imageViewMapOfTheEvent);
		myMapWebView.loadDataWithBaseURL("file:///android_asset/images/",
				"<html><body><img src=\"buildingmap.png\"></body></html>",
				"text/html", "utf-8", null);
		myMapWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				myMapWebView.setInitialScale(scale);
				myMapWebView.setScrollContainer(false);
				myMapWebView.setScrollbarFadingEnabled(true);
			}
		});
		myMapWebView.setPictureListener(new PictureListener() {
			@Override
			public void onNewPicture(WebView view, Picture picture) {
				MapScroller myMapScroll = new MapScroller(location.getName(),
						myMapWebView.getMeasuredWidth(), myMapWebView
								.getMeasuredHeight(), myMapWebView.getScale());
				myMapWebView.scrollTo(myMapScroll.getBmpScrollX(),
						myMapScroll.getBmpScrollY());
			}
		});

		myMapWebView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					startMap();
				}
				return (event.getAction() == MotionEvent.ACTION_MOVE);
			}
		});

		myMapWebView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startMap();
			}
		});
	}

	protected void startMap() {
		Intent intentMap = new Intent(this, MapActivity.class);
		intentMap.putExtra(Constants.EXTRA_START, location.getName());
		startActivity(intentMap);
	}

	private void updateView() {
		eventTitle.setText(event.getName().toUpperCase(Locale.ENGLISH));
		eventTime.setText(Utils.getEventStringTime(event.getStartTime())
				+ "\t-\t" + Utils.getEventStringTime(event.getEndTime()));
		eventLocation.setText(location.getName());
		if (!event.getName().equals(Constants.JSON_TAG_MENU_DINNER)) {
			eventMenu.setVisibility(View.GONE);
		} else {
			for (Course c : event.getMenu()) {

				if (c.getCourse().equals(Constants.MENU_TYPE_FIRST)) {
					courseFirst.setText(c.getCourse());
					courseFirstName.setText(c.getName());
				} else if (c.getCourse().equals(Constants.MENU_TYPE_MAIN)) {
					courseMain.setText(c.getCourse());
					courseMainName.setText(c.getName());
				} else if (c.getCourse().equals(Constants.MENU_TYPE_DESSERT)) {
					courseDessert.setText(c.getCourse());
					courseDessertName.setText(c.getName());

				}
			}
		}
		Drawable d = Utils.loadImageFromAsset(this, event.getImage());
		if (d != null)
			eventImage.setImageDrawable(d);
		eventDescription.setText(event.getDescription());

	}

	
	
	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra(Constants.EXTRA_EVENT_INFO, this.event.getLocation());
		returnIntent.putExtra(Constants.EXTRA_EVENT_ALL, this.comesFromAll);
		setResult(Constants.RESULT_EVENT_INFO, returnIntent);
		finish();
		return;
	}

	
}
