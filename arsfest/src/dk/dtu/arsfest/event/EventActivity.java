package dk.dtu.arsfest.event;


import java.util.Locale;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

import dk.dtu.arsfest.AboutActivity;
import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
import dk.dtu.arsfest.maps.MapActivity;
import dk.dtu.arsfest.model.Course;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends Activity implements
		OnSlideMenuItemClickListener {

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
	private SlideMenu slidemenu;
	private View eventMenu;

	private int bmpWidth, bmpHeight;
	private int scale = 50;
	private WebView myMapWebView;
	private int startX, startY;

	private Event event;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_event);

		// Initiate the layout
		initView();

		// Read event info from intent
		Intent intent = getIntent();
		this.event = (Event) intent.getParcelableExtra(Constants.EXTRA_EVENT);
		this.location = (Location) intent
				.getParcelableExtra(Constants.EXTRA_LOCATION);

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
		startMenu(Constants.SCROLL_MENU_TIME);
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
				bmpWidth = (int) findViewById(R.id.imageViewMapOfTheEvent).getMeasuredWidth()/2;
				bmpHeight = (int) findViewById(R.id.imageViewMapOfTheEvent).getMeasuredHeight()/2;
				if (location.getName().equals("Library")) {
					startX = 770*scale/100 - bmpWidth;
					startY = 560*scale/100 - bmpHeight;
					correctX(startX);
					correctY(startY);		
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Oticon")) {
					startX = 2100*scale/100 - bmpWidth;
					startY = 240*scale/100 - bmpHeight;
					correctX(startX);
					correctY(startY);				
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Kantine")) {
					startX = 1420*scale/100 - bmpWidth;
					startY = 590*scale/100 - bmpHeight;
					correctX(startX);
					correctY(startY);	
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Sportshal")) {
					startX = 2130*scale/100 - bmpWidth;
					startY = 1090*scale/100 - bmpHeight;
					correctX(startX);
					correctY(startY);
					myMapWebView.scrollTo(startX, startY);
				}
			}

			private void correctY(int Y) {
				if (Y < 0) {
					startY = 0;
				} else if (Y+bmpHeight*2 > 1671 * scale/100) {
					startY = 1671 * scale/100 - bmpHeight*2;
				}				
			}

			private void correctX(int X) {
				if (X < 0) {
					startX = 0;
				} else if (X+bmpWidth*2 > 2482 * scale/100) {
					startX = 2482 * scale/100 - bmpWidth*2;
				}				
			}
		});

		myMapWebView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startMap();
				}
				return (event.getAction() == MotionEvent.ACTION_MOVE);
			}
		});
		
		myMapWebView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startMap();			
			}});
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
		if(!event.getName().equals(Constants.JSON_TAG_MENU_DINNER)){
			eventMenu.setVisibility(View.GONE);
		}
		else{
			for(Course c : event.getMenu()){
				
				if(c.getCourse().equals(Constants.MENU_TYPE_FIRST)){
					courseFirst.setText(c.getCourse());
					courseFirstName.setText(c.getName());
				}
				else if(c.getCourse().equals(Constants.MENU_TYPE_MAIN)){
					courseMain.setText(c.getCourse());
					courseMainName.setText(c.getName());
				}	
				else if(c.getCourse().equals(Constants.MENU_TYPE_DESSERT)){
					courseDessert.setText(c.getCourse());
					courseDessertName.setText(c.getName());
				
				}
			}
		}
						
		// eventDescription.setText(event.getDescription());
		
	}

	/**
	 * Method showing the accordeon slide menu at the left hand side
	 * 
	 * @param durationOfAnimation
	 *            : Duration of Animation
	 * @author AA
	 */
	private void startMenu(int durationOfAnimation) {
		slidemenu = new SlideMenu(this, R.menu.slide, this, durationOfAnimation);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.slide, this, durationOfAnimation);
		slidemenu.setFont(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		ImageButton imageButtonAccordeon = (ImageButton) findViewById(R.id.actionBarAccordeon);
		imageButtonAccordeon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				slidemenu.show();
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
			finish();
			break;
		case R.id.item_map:
			Intent intentMap = new Intent(this, MapActivity.class);
			intentMap.putExtra(Constants.EXTRA_START, "");
			this.startActivity(intentMap);
			break;
		case R.id.item_settings:
			Toast.makeText(this, "Don't milk nipples when they are soft.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_about:
			Intent intentAbout = new Intent(this, AboutActivity.class);
			this.startActivity(intentAbout);
			finish();
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
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
