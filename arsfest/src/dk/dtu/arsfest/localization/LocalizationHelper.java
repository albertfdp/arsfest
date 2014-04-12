package dk.dtu.arsfest.localization;

import java.util.HashMap;
import java.util.Map;

import dk.dtu.arsfest.utils.Constants;

public class LocalizationHelper {

	private Map<String, int[]> mMap = new HashMap<String, int[]>();

	public LocalizationHelper() {
		populateMap();
	}
	
	private void populateMap() {
		mMap.put(Constants.Library, new int[] {320, 470});
		mMap.put(Constants.SportsHall, new int[] {1610, 980});
		mMap.put(Constants.Oticon, new int[] {1560, 130});
		mMap.put(Constants.Canteen, new int[] {870, 520});
		mMap.put(Constants.Tent, new int[] {1720, 1460});
		mMap.put(null, new int[] {0, 0});
	}
	
	public int[] getScroll(String initialLocation){
		if (mMap.containsKey(initialLocation)) {
			return mMap.get(initialLocation);	
		} else {
			return null;
		}
	}
}
