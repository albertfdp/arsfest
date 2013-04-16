package dk.dtu.arsfest.utils;

public class Constants {
	
	public final static String APP_NAME = "DTU ARSFEST 2013";
	
	public final static String TYPEFONT_WELLFLEET = "Wellfleet-Regular.ttf";
	public final static String TYPEFONT_NEOSANS = "NeoSans.ttf";
	public final static String TYPEFONT_PROXIMANOVA = "ProximaNova-Light.otf";

	public static final String TAG = "ARSFEST";
	
	public static final String JSON_TAG_LOCATIONS = "locations";
	public static final String JSON_TAG_LOCATION_ID = "id";
	public static final String JSON_TAG_LOCATION_NAME = "name";
	public static final String JSON_TAG_LOCATION_LATITUDE = "latitude";
	public static final String JSON_TAG_LOCATION_LONGITUDE = "longitude";
	public static final String JSON_TAG_LOCATION_DESCRIPTION = "description";
	public static final String JSON_TAG_LOCATION_IMAGE = "image";
	public static final String JSON_TAG_LOCATION_EVENTS = "events";
	
	public static final String JSON_TAG_EVENT_ID = "id";
	public static final String JSON_TAG_EVENT_NAME = "name";
	public static final String JSON_TAG_EVENT_IMAGE = "image";
	public static final String JSON_TAG_EVENT_START_TIME = "startTime";
	public static final String JSON_TAG_EVENT_END_TIME = "endTime";
	public static final String JSON_TAG_EVENT_DESCRIPTION = "description";
	public static final String JSON_TAG_EVENT_LOCATION = "location";
	public static final String JSON_TAG_EVENT_TYPE = "type";
	public static final String JSON_TAG_EVENT_THEME = "theme";
	public static final String JSON_TAG_EVENT_MENU = "menu";
	
	public static final String JSON_TAG_MENU_COURSE = "course";
	public static final String JSON_TAG_MENU_COURSE_NAME = "name";
	public static final String JSON_TAG_MENU_COURSE_DESCRIPTION = "description";

	public static final String JSON_TAG_BSSID_LOCATION = "location";
	public static final String JSON_TAG_BSSIDS = "bssids";
	public static final String JSON_TAG_BSSID_NAME = "bssid";

	public static final String EXTRA_EVENT = "dk.dtu.arsfest.Event";
	public static final String EXTRA_LOCATION = "dk.dtu.arsfest.Location";
	
	public static final String FEST_START_TIME = "03-05-2013:18:10";

	public static final Object EVENT_TYPE_MUSIC = "music";
	public static final Object EVENT_TYPE_OFFICIAL = "official";
	public static final Object EVENT_TYPE_FOOD = "food";
	public static final Object EVENT_TYPE_DANCE = "dance";
	
	public static final String PREFS_NAME = "ArsFestPrefsFile";
	public static final int ALARM_FREQUENCY = 60;

	public static final int SCROLL_MENU_TIME = 333;
	
	public static final String EXTRA_STARTX = "dk.dtu.arsfest.map.StartX";
	public static final String EXTRA_STARTY = "dk.dtu.arsfest.map.StartY";
	public static final String EXTRA_START = "dk.dtu.arsfest.map.Start";


}
