/*
 * This service provides information about azimuth, pitch and roll.
 * This service is running constantly in the background as the response of the application has to be immediate and the process of communicating with the server takes enough time already.
 */
package dk.dtu.arsfest.sensors;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class OrientationService {//extends Service implements SensorEventListener {

	/* RAW
	 * TODO orientation service would be nice for the mapview
	 */
	
	/*
	private SensorManager sensorManager;
	private Sensor sensorAccelerometer, sensorMagneticField;
	private float[] valuesAccelerometer, valuesMagneticField;
	private float[] matrixR, matrixI, matrixValues;
	private double azimuthON = -1, pitchON = -1, rollON = -1;

	@Override
	public void onCreate() {
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// we use two types of sensors to ensure accuracy
		sensorAccelerometer = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorMagneticField = sensorManager
				.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		valuesAccelerometer = new float[3];
		valuesMagneticField = new float[3];
		matrixR = new float[9];
		matrixI = new float[9];
		matrixValues = new float[3];

		sensorManager.registerListener(this, sensorAccelerometer,
				SensorManager.SENSOR_DELAY_NORMAL);
		sensorManager.registerListener(this, sensorMagneticField,
				SensorManager.SENSOR_DELAY_NORMAL);

		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		if (getAzimuth() != -1 && getPitch() != -1 && getRoll() != -1) {
			sendOrientationIntent();
		}
	}

	private void sendOrientationIntent() {
		Intent onOrientationIntent = new Intent();
		onOrientationIntent.setAction(Constants.OrientationActionTag);
		onOrientationIntent.putExtra(Constants.OrientationFlagAzimuth,
				getAzimuth());
		onOrientationIntent.putExtra(Constants.OrientationFlagRoll, getRoll());
		onOrientationIntent
				.putExtra(Constants.OrientationFlagPitch, getPitch());
		sendBroadcast(onOrientationIntent);
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		switch (event.sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			for (int i = 0; i < 3; i++) {
				valuesAccelerometer[i] = event.values[i];
			}
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			for (int i = 0; i < 3; i++) {
				valuesMagneticField[i] = event.values[i];
			}
			break;
		}

		try {
			boolean success = SensorManager.getRotationMatrix(matrixR, matrixI,
					valuesAccelerometer, valuesMagneticField);
			if (success) {
				SensorManager.getOrientation(matrixR, matrixValues);
				setAzimuth(Math.toDegrees(matrixValues[0]));
				setPitch(Math.toDegrees(matrixValues[1]));
				setRoll(Math.toDegrees(matrixValues[2]));
			}
		} catch (Exception e) {
			Log.e("onOrientationManager",
					"Failed to request orientation update" + e);
		}
	}

	private double getAzimuth() {
		return azimuthON;
	}

	private void setAzimuth(double azimuthON) {
		this.azimuthON = azimuthON;
	}

	private double getPitch() {
		return pitchON;
	}

	private void setPitch(double pitchON) {
		this.pitchON = pitchON;
	}

	private double getRoll() {
		return rollON;
	}

	private void setRoll(double rollON) {
		this.rollON = rollON;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sensorManager.unregisterListener(this, sensorAccelerometer);
		sensorManager.unregisterListener(this, sensorMagneticField);
		sensorManager.unregisterListener(this);
	}*/
}