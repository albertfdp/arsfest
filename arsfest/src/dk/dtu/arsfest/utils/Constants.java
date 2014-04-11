package dk.dtu.arsfest.utils;

public class Constants {
	
	public final static String APP_NAME = "DTU \u00C5RSFEST 2013";
	
	public final static String TYPEFONT_WELLFLEET = "Wellfleet-Regular.ttf";
	public final static String TYPEFONT_NEOSANS = "fonts/NeoSans.ttf";
	public final static String TYPEFONT_PROXIMANOVA = "fonts/ProximaNova-Light.otf";
	public final static String TYPEFONT_MRDAFOE = "MrDafoe-Regular.ttf";
	public final static String TYPEFONT_ROBOTO = "Roboto-Condensed.ttf";
	public final static String TYPEFONT_BREE_SERIF = "fonts/BreeSerif-Regular.ttf";

	public static final String TAG = "ARSFEST";
	
	public static final String IMG_CONTENT_PROVIDER_URL = "https://dl.dropboxusercontent.com/u/3792125/arsfest2014/assets/img/";
	
	public static final String JSON_URL = "https://dl.dropboxusercontent.com/u/3792125/arsfest2014/assets/data/data.json";
	public static final String JSON_CACHE_FILENAME = "en.json";
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
	public static final String JSON_TAG_MENU_DINNER = "Dinner";
	
	public static final String JSON_TAG_MENU_COURSE = "course";
	public static final String JSON_TAG_MENU_COURSE_NAME = "name";

	public static final String JSON_TAG_BSSID_LOCATION = "location";
	public static final String JSON_TAG_BSSIDS = "bssids";
	public static final String JSON_TAG_BSSID_NAME = "bssid";
	
	public static final String MENU_TYPE_FIRST = "First";
	public static final String MENU_TYPE_MAIN = "Main";
	public static final String MENU_TYPE_DESSERT = "Dessert";
	public static final String MENU_TYPE_DRINK = "Drinks";

	public static final String EXTRA_EVENT = "dk.dtu.arsfest.Event";
	public static final String EXTRA_LOCATION = "dk.dtu.arsfest.Location";
	
	public static final String JSON_DATE_FORMAT = "dd-MM-yyyy:HH:mm";
	public static final String FEST_START_TIME = "03-05-2013:17:30";
	public static final String FEST_END_TIME = "04-05-2013:06:00";

	public static final String EVENT_TYPE_MUSIC = "music";
	public static final String EVENT_TYPE_OFFICIAL = "official";
	public static final String EVENT_TYPE_FOOD = "food";
	public static final String EVENT_TYPE_DANCE = "dance";
	
	public static final String PREFS_NAME = "ArsFestPrefsFile";
	public static final int ALARM_FREQUENCY = 60;

	public static final int SCROLL_MENU_TIME = 333;
	
	//public static final String EXTRA_STARTX = "dk.dtu.arsfest.map.StartX";
	//public static final String EXTRA_STARTY = "dk.dtu.arsfest.map.StartY";
	//public static final String EXTRA_START = "dk.dtu.arsfest.map.Start";
	public static final String EXTRA_MAP = "dk.dtu.arsfest.map.start";
	
	public static final int RESULT_SETTINGS = 1;
	public static final int RESULT_EVENT_INFO = 2;

	public static final String EXTRA_EVENT_INFO = "dk.dtu.arsfest.event";
	public static final String EXTRA_EVENT_SHOW_FINISHED = "dk.dtu.arsfest.event.show.finished";

	public static final String EXTRA_EVENT_ALL = "dk.dtu.arsfest.event.all";

	public static final String PREFS_POP_UP_CONNECTIVIY = "popUpConnectivityDiscard";
	public static final String ONE_TIME_NOTIFICATION = "oneTimeNotificationShow";
	public static final String PREFERENCES_VIBRATION = "VibrationOnAlarm";

	public static final String JSON_TAG_EVENT_REMARK = "remark";
	
	public static final int MAX_EVENT_INFO = 450;
	public static final int MIN_EVENT_INFO = 35;
	
	public static final String PREFS_LAST_UPDATED_KEY = "PREFS_LAST_UPDATED_KEY";

	// LocationService
	public static final String LocationActionTag = "ArsfestLocationRequest";
	public static final String LocationFlagLatitude = "ArsfestLatitude";
	public static final String LocationFlagLongitude = "ArsfestLongitude";
	public static final String LocationFlagAccuracy = "ArsfestAccuracy";
	public static final String ProvidersActionTag = "ArsfestProvidersRequest";
	public static final String GPSProvider = "ArsfestGPSProvider";
	public static final String NetworkProvider = "ArsfestNetworkProvider";
	public static final String OrientationActionTag = "ArsfestOrientationRequest";
	public static final String OrientationFlagAzimuth = "ArsfestAzimuth";
	
	public static final String Library = "Library";
	public static final String Oticon = "Oticon";
	public static final String SportsHall = "SportsHall";
	public static final String Canteen = "Canteen";
	
}
