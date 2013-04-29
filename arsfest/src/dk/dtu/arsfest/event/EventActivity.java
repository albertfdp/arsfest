package dk.dtu.arsfest.event;

import java.util.Calendar;
import java.util.Locale;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.SlideMenuSuper;
import dk.dtu.arsfest.maps.MapActivity;
import dk.dtu.arsfest.maps.MapScroller;
import dk.dtu.arsfest.model.Course;
import dk.dtu.arsfest.model.Event;
import dk.dtu.arsfest.model.Location;
import dk.dtu.arsfest.notification.MyNotificationService;
import dk.dtu.arsfest.notification.NotificationActivity;
import dk.dtu.arsfest.utils.Constants;
import dk.dtu.arsfest.utils.Utils;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Picture;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebView.PictureListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class EventActivity extends SlideMenuSuper {

	private TextView eventTitle;
	private TextView eventTime;
	private TextView eventLocation;
	private TextView eventDescription;
	private TextView eventShowMore;
	private TextView headerTitle;
	private TextView seeItOnTheMap;
	private TextView courseFirst;
	private TextView courseFirstName;
	private TextView courseMain;
	private TextView courseMainName;
	private TextView courseDessert;
	private TextView courseDessertName;
	private TextView courseDrink;
	private TextView courseDrinkName;
	private TextView menuName;
	private View eventMenu;
	private ImageView eventImage;
	private LinearLayout layoutViewOfTheEvent;
	private LinearLayout layoutViewMapOfTheEvent;

	private TextView viewEventNotificationsTitle, textViewEventNotifications;
	private LinearLayout viewEventNotifications;
	private RelativeLayout viewEventNotificationsTextView;

	private int scale;
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
		this.location = (Location) intent
				.getParcelableExtra(Constants.EXTRA_LOCATION);
		this.comesFromAll = intent.getBooleanExtra(Constants.EXTRA_EVENT_ALL,
				false);

		updateView();
	}

	private void initView() {
		eventTitle = (TextView) findViewById(R.id.event_title);
		eventTime = (TextView) findViewById(R.id.event_time);
		eventLocation = (TextView) findViewById(R.id.event_location);
		eventDescription = (TextView) findViewById(R.id.event_description);
		eventShowMore = (TextView) findViewById(R.id.event_show_more);
		headerTitle = (TextView) findViewById(R.id.actionBarTitle);
		seeItOnTheMap = (TextView) findViewById(R.id.seeItOnTheMap);
		courseFirst = (TextView) findViewById(R.id.first_course_type);
		courseFirstName = (TextView) findViewById(R.id.first_course_name);
		courseMain = (TextView) findViewById(R.id.main_course_type);
		courseMainName = (TextView) findViewById(R.id.main_course_name);
		courseDessert = (TextView) findViewById(R.id.dessert_course_type);
		courseDessertName = (TextView) findViewById(R.id.dessert_course_name);
		courseDrink = (TextView) findViewById(R.id.drink_course_type);
		courseDrinkName = (TextView) findViewById(R.id.drink_course_name);
		menuName = (TextView) findViewById(R.id.event_menu_name);
		eventMenu = findViewById(R.id.event_menu);
		eventImage = (ImageView) findViewById(R.id.event_image);
		layoutViewOfTheEvent = (LinearLayout) findViewById(R.id.layoutViewOfTheEvent);
		layoutViewMapOfTheEvent = (LinearLayout) findViewById(R.id.layoutViewMapOfTheEvent);

		final ViewTreeObserver observer = layoutViewOfTheEvent
				.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				Display display = getWindowManager().getDefaultDisplay();
				if (layoutViewOfTheEvent.getMeasuredHeight() > display
						.getHeight()) {
					LayoutParams myParameters = layoutViewMapOfTheEvent
							.getLayoutParams();
					int myHeight = (int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_DIP, 200, getResources()
									.getDisplayMetrics());
					myParameters.height = myHeight;
				}
				if (observer.isAlive()) {
					observer.removeGlobalOnLayoutListener(this);
				}
			}
		});

		Typeface dtuFont = Utils.getTypeface(this, Constants.TYPEFONT_NEOSANS);
		Typeface menuFont = Utils.getTypeface(this, Constants.TYPEFONT_MRDAFOE);
		eventTitle.setTypeface(dtuFont, Typeface.BOLD);
		eventTime.setTypeface(dtuFont);
		eventLocation.setTypeface(dtuFont);
		eventDescription.setTypeface(dtuFont);
		eventShowMore.setTypeface(dtuFont, Typeface.BOLD);
		seeItOnTheMap.setTypeface(dtuFont);
		courseFirst.setTypeface(menuFont);
		courseFirstName.setTypeface(dtuFont);
		courseMain.setTypeface(menuFont);
		courseMainName.setTypeface(dtuFont);
		courseDessert.setTypeface(menuFont);
		courseDessertName.setTypeface(dtuFont);
		courseDrink.setTypeface(menuFont);
		courseDrinkName.setTypeface(dtuFont);
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
		scale = (int) getApplicationContext().getResources()
				.getDisplayMetrics().widthPixels / 8;
		myMapWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				myMapWebView.setInitialScale(scale);
				myMapWebView.setInitialScale(scale);
				myMapWebView.setScrollContainer(false);
				myMapWebView.setScrollbarFadingEnabled(true);
			}
		});
		myMapWebView.setPictureListener(new PictureListener() {
			@Override
			public void onNewPicture(WebView view, Picture picture) {
				myMapWebView.setInitialScale(scale);
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
				} else if (c.getCourse().equals(Constants.MENU_TYPE_DRINK)) {
					courseDrink.setText(c.getCourse());
					courseDrinkName.setText(c.getName());

				}
			}
		}
		Drawable d = Utils.loadImageFromAsset(this, event.getImage());
		if (d != null)
			eventImage.setImageDrawable(d);

		if (event.getDescription().length() > Constants.MAX_EVENT_INFO) {

			eventDescription.setText(event.getDescription().substring(0,
					Constants.MAX_EVENT_INFO));

			eventShowMore.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (eventDescription.getText().toString()
							.equals(event.getDescription())) {
						eventDescription.setText(event.getDescription()
								.substring(0, Constants.MAX_EVENT_INFO));
						eventShowMore.setText(getResources().getString(
								R.string.show_more));
					} else {
						eventDescription.setText(event.getDescription());
						eventShowMore.setText(getResources().getString(
								R.string.show_less));
					}
				}
			});

		} else {
			eventDescription.setText(event.getDescription());
			eventShowMore.setVisibility(View.GONE);
		}

		if (event.getDescription().length() <= Constants.MIN_EVENT_INFO) {
			LinearLayout cardDescription = (LinearLayout) findViewById(R.id.event_card_description);
			cardDescription.setVisibility(View.GONE);
		}
		viewNotifications();
	}

	private void viewNotifications() {
		viewEventNotifications = (LinearLayout) findViewById(R.id.viewEventNotifications);
		Calendar calendarNow = Calendar.getInstance();
		calendarNow.setTimeInMillis(System.currentTimeMillis());
		calendarNow.add(Calendar.MINUTE, 15);
		final SharedPreferences sharedPrefs = getSharedPreferences(
				Constants.PREFS_NAME, 0);
		if (event.hasStarted(calendarNow.getTime())) {
			viewEventNotifications.setVisibility(View.GONE);
		} else {
			viewEventNotificationsTitle = (TextView) findViewById(R.id.viewEventNotificationsTitle);
			textViewEventNotifications = (TextView) findViewById(R.id.textViewEventNotifications);
			viewEventNotificationsTextView = (RelativeLayout) findViewById(R.id.viewEventNotificationsTextView);
			Typeface dtuFont = Utils.getTypeface(this,
					Constants.TYPEFONT_NEOSANS);
			viewEventNotificationsTitle.setTypeface(dtuFont);
			textViewEventNotifications.setTypeface(dtuFont);
			if (sharedPrefs.getBoolean("Alarm" + event.getId(), false)) {
				textViewEventNotifications
						.setText("Unset alarm for this event.");
				viewEventNotificationsTextView
						.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.selector_grey));
			} else {
				textViewEventNotifications.setText("Set alarm for this event.");
				viewEventNotificationsTextView
						.setBackgroundDrawable(getResources().getDrawable(
								R.drawable.selector_rate));
			}

			viewEventNotificationsTextView
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							SharedPreferences.Editor editor = sharedPrefs
									.edit();
							editor.putBoolean(
									"Alarm" + event.getId(),
									!sharedPrefs.getBoolean(
											"Alarm" + event.getId(), false));
							editor.commit();

							if (sharedPrefs.getBoolean("Alarm" + event.getId(),
									false)) {
								textViewEventNotifications
										.setText("Unset alarm for this event.");
								viewEventNotificationsTextView
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.selector_grey));

								setMyPendingIntent();
							} else {
								textViewEventNotifications
										.setText("Set alarm for this event.");
								viewEventNotificationsTextView
										.setBackgroundDrawable(getResources()
												.getDrawable(
														R.drawable.selector_rate));
								cancelMyPendingIntent();
							}
						}
					});
		}
	}

	@Override
	public void onBackPressed() {
		Intent returnIntent = new Intent();
		returnIntent.putExtra(Constants.EXTRA_EVENT_INFO,
				this.event.getLocation());
		returnIntent.putExtra(Constants.EXTRA_EVENT_ALL, this.comesFromAll);
		setResult(Constants.RESULT_EVENT_INFO, returnIntent);
		finish();
		return;
	}

	private void setMyPendingIntent() {
		Context currentContext = NotificationActivity.getAppContext();
		int myFlagForIntent = Integer.valueOf(event.getId().substring(1));
		Intent myIntent = new Intent(currentContext,
				MyNotificationService.class);
		myIntent.putExtra(Constants.EXTRA_EVENT, event);
		PendingIntent myPendingIntent = PendingIntent.getService(
				currentContext, myFlagForIntent, myIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		AlarmManager alarmManager = (AlarmManager) currentContext
				.getSystemService(Context.ALARM_SERVICE);
		Calendar calendarEvent = Calendar.getInstance();
		calendarEvent.setTime(event.getStartTime());
		calendarEvent.add(Calendar.MINUTE, -15);
		alarmManager.set(AlarmManager.RTC_WAKEUP,
				calendarEvent.getTimeInMillis(), myPendingIntent);

	}

	private void cancelMyPendingIntent() {
		Context currentContext = NotificationActivity.getAppContext();
		int myFlagForIntent = Integer.valueOf(event.getId().substring(1));
		AlarmManager alarmManager = (AlarmManager) currentContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent myIntent = new Intent(currentContext,
				MyNotificationService.class);
		PendingIntent myPendingIntent = PendingIntent.getService(
				currentContext, myFlagForIntent, myIntent, 0);
		alarmManager.cancel(myPendingIntent);
	}
}
