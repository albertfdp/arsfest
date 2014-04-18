package dk.dtu.arsfest.localization;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import dk.dtu.arsfest.R;

public class LocalizationHelper {

	private Map<String, int[]> mMap = new HashMap<String, int[]>();
	private Context mContext;

	public LocalizationHelper(Context c) {
		this.mContext = c;
		populateMap();
	}

	private void populateMap() {
		mMap.put(mContext.getString(R.string.library), new int[] { 320, 470 });
		mMap.put(mContext.getString(R.string.sportshall),
				new int[] { 1610, 980 });
		mMap.put(mContext.getString(R.string.oticon), new int[] { 1560, 130 });
		mMap.put(mContext.getString(R.string.canteen), new int[] { 870, 520 });
		mMap.put(mContext.getString(R.string.tent), new int[] { 1720, 1460 });
		mMap.put(null, new int[] { 0, 0 });
	}

	public int[] getScroll(String initialLocation) {
		if (mMap.containsKey(initialLocation)) {
			return mMap.get(initialLocation);
		}
		return null;
	}
}
