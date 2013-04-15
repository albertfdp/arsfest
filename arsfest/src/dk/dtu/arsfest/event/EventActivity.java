package dk.dtu.arsfest.event;

import java.util.Locale;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;

import dk.dtu.arsfest.AboutActivity;
import dk.dtu.arsfest.MainActivity;
import dk.dtu.arsfest.R;
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
	private SlideMenu slidemenu;

	private int bmpWidth, bmpHeight;
	private int scale = 50;
	private WebView myMapWebView;

	private Event event;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Hide title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		eventTitle.setTypeface(dtuFont, Typeface.BOLD);
		eventTime.setTypeface(dtuFont);
		eventLocation.setTypeface(dtuFont);
		eventDescription.setTypeface(dtuFont);
		seeItOnTheMap.setTypeface(dtuFont);
		headerTitle.setTypeface(Utils.getTypeface(this,
				Constants.TYPEFONT_PROXIMANOVA));
		startMenu(333);
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
					int startX = 770*scale/100 - bmpWidth;
					int startY = 560*scale/100 - bmpHeight;
					if (startX < 0) {
						startX = 0;
					} else if (startX+bmpWidth*2 > 2482 * scale/100) {
						startX = 2482 * scale/100 - bmpWidth*2;
					}
					if (startY < 0) {
						startY = 0;
					} else if (startY+bmpHeight*2 > 1671 * scale/100) {
						startY = 1671 * scale/100 - bmpHeight*2;
					}				
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Oticon")) {
					int startX = 2100*scale/100 - bmpWidth;
					int startY = 240*scale/100 - bmpHeight;
					if (startX < 0) {
						startX = 0;
					} else if (startX+bmpWidth*2 > 2482 * scale/100) {
						startX = 2482 * scale/100 - bmpWidth*2;
					}
					if (startY < 0) {
						startY = 0;
					} else if (startY+bmpHeight*2 > 1671 * scale/100) {
						startY = 1671 * scale/100 - bmpHeight*2;
					}				
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Kantine")) {
					int startX = 1420*scale/100 - bmpWidth;
					int startY = 590*scale/100 - bmpHeight;
					if (startX < 0) {
						startX = 0;
					} else if (startX+bmpWidth*2 > 2482 * scale/100) {
						startX = 2482 * scale/100 - bmpWidth*2;
					}
					if (startY < 0) {
						startY = 0;
					} else if (startY+bmpHeight*2 > 1671 * scale/100) {
						startY = 1671 * scale/100 - bmpHeight*2;
					}			
					myMapWebView.scrollTo(startX, startY);
				} else if (location.getName().equals("Sportshal")) {
					int startX = 2130*scale/100 - bmpWidth;
					int startY = 1090*scale/100 - bmpHeight;
					if (startX < 0) {
						startX = 0;
					} else if (startX+bmpWidth*2 > 2482 * scale/100) {
						startX = 2482 * scale/100 - bmpWidth*2;
					}
					if (startY < 0) {
						startY = 0;
					} else if (startY+bmpHeight*2 > 1671 * scale/100) {
						startY = 1671 * scale/100 - bmpHeight*2;
					}			
					myMapWebView.scrollTo(startX, startY);
				}
			}
		});

		myMapWebView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return (event.getAction() == MotionEvent.ACTION_MOVE);
			}
		});
	}

	private void updateView() {
		eventTitle.setText(event.getName().toUpperCase(Locale.ENGLISH));
		eventTime.setText(Utils.getEventStringTime(event.getStartTime())
				+ "\t-\t" + Utils.getEventStringTime(event.getEndTime()));
		eventLocation.setText(location.getName());
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
			Intent intent1 = new Intent(this, MainActivity.class);
			this.startActivity(intent1);
			finish();
			break;
		case R.id.item_map:
			Toast.makeText(this, "Jamal, please paint the wall.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_settings:
			Toast.makeText(this, "Don't milk nipples when they are soft.",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.item_about:
			Intent intent4 = new Intent(this, AboutActivity.class);
			this.startActivity(intent4);
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