package dk.dtu.arsfest.sensors;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkHelper {

	private Context mContext = null;
	private WifiManager mWifiManager;

	public NetworkHelper(Context c) {
		mContext = c;
		mWifiManager = (WifiManager) mContext
				.getSystemService(Context.WIFI_SERVICE);
	}

	public boolean isWiFiTurnedOn() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return mNetworkInfo.isAvailable();
	}

	public String getScanResults() {
		for (int mIndex = 0; mIndex < mWifiManager.getScanResults().size(); mIndex++) {
			if (mWifiManager.getScanResults().get(mIndex).SSID
					.equals("eduroam")) {
				return mWifiManager.getScanResults().get(mIndex).BSSID;
			}
		}
		return null;
	}

	public boolean resetNetwork() {
		return mWifiManager.startScan();
	}
}
