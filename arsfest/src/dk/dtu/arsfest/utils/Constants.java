package dk.dtu.arsfest.utils;

public class Constants {

	public final static String TYPEFONT_NEOSANS = "fonts/NeoSans.ttf";
	public static final String TAG = "ARSFEST";
	public static final String IMG_CONTENT_PROVIDER_URL = "https://dl.dropboxusercontent.com/u/3792125/arsfest2014/assets/img/";
	public static final String JSON_URL = "https://dl.dropboxusercontent.com/u/3792125/arsfest2014/assets/data/data.json";
	public static final String JSON_CACHE_FILENAME = "en.json";
	public static final String EXTRA_EVENT = "dk.dtu.arsfest.Event";
	public static final String JSON_DATE_FORMAT = "dd-MM-yyyy:HH:mm";
	public static final String EVENT_TYPE_SALE = "ticket sale";
	public static final String EXTRA_EVENT_SHOW_FINISHED = "dk.dtu.arsfest.event.show.finished";	
	public static final String EXTRA_TICKET_SALE_ACTIVITY = "dk.dtu.arsfest.event";
	public static final String PREFS_LAST_UPDATED_KEY = "PREFS_LAST_UPDATED_KEY";
	public static final String LocationActionTag = "ArsfestLocationRequest";
	public static final String LocationFlagLatitude = "ArsfestLatitude";
	public static final String LocationFlagLongitude = "ArsfestLongitude";
	public static final String LocationFlagAccuracy = "ArsfestAccuracy";
	public static final String ProvidersActionTag = "ArsfestProvidersRequest";
	public static final String GPSProvider = "ArsfestGPSProvider";
	public static final String NetworkProvider = "ArsfestNetworkProvider";
	public static final String OrientationActionTag = "ArsfestOrientationRequest";
	public static final String OrientationFlagAzimuth = "ArsfestAzimuth";
	public static final String MapStartLocation = "MapStartLocation";
	public static final String MapShowPin = "MapShowPin";
	public static final int[] MapDimentions = { 1900, 1560 };
	public static final int LocateMeTreshold = 20000;	
	public static final String SharedPreferences = "SharedPreferences";
	public static final String GPSRequest = "SharedPreferencesGPS";
	public static final String NetworkRequest = "SharedPreferencesNetwork";
	public static final double AzimuthTreshold = 25;
	/* Change this with scripts/generate_coordinates.py */
	public static final double aLeftCoefficientMap = 5.80162601623;
	public static final double bLeftCoefficientMap = -16.8665276907;
	public static final double leftRightLengthMap = 0.0033;
	public static final double aTopCoefficientMap = -0.172365470853;
	public static final double bTopCoefficientMap = 57.9460962716;
	public static final double topDownLengthMap = 0.0018;
	public static final double EstimatedBSSIDAccuracy = 50.0;
	public static double meterToPixel = 7.8;
}
