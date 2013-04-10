package dk.dtu.arsfest.utils;

public class Constants {
	
	public final static String APP_NAME = "DTU ARSFEST 2013";
	
	public final static String TYPEFONT_WELLFLEET = "Wellfleet-Regular.ttf";
	public final static String TYPEFONT_NEOSANS = "NeoSans.ttf";
	public final static String TYPEFONT_PROXIMANOVA = "ProximaNova-Light.otf";
	public static final int FEST_START_TIME_MINS = 0;
	public static final int FEST_START_TIME_HOURS = 18;
	public static final int FEST_START_TIME_DAY = 3;
	public static final int FEST_START_TIME_MONTH = 5;
	public static final int FEST_START_TIME_YEAR = 2013;

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

	public static final String JSON_TAG_BSSID_LOCATION = "location";
	public static final String JSON_TAG_BSSIDS = "bssids";
	public static final String JSON_TAG_BSSID_NAME = "name";

	public static final String FEST_START_TIME = FEST_START_TIME_DAY + "-" + FEST_START_TIME_MONTH + "-" + 
			FEST_START_TIME_YEAR + ":" + FEST_START_TIME_HOURS + ":" + FEST_START_TIME_MINS;


}
