package dk.dtu.arsfest.rest;

import android.util.Log;

import com.google.resting.Resting;
import com.google.resting.component.RequestParams;
import com.google.resting.component.impl.BasicRequestParams;
import com.google.resting.component.impl.ServiceResponse;

public class RestClient {
	
	private static final String URL = "http://82.211.198.134";
	private static final int PORT = 8081;
	
	public static String getEvent(int id) {
		RequestParams params = new BasicRequestParams(); 
		params.add("id", String.valueOf(id));
		ServiceResponse response = Resting.get(URL + "/event/", PORT, params);
		System.out.println(response);
		return response.getResponseString();
	}
	
	public static String getEvents() {
		ServiceResponse response = Resting.get(URL + "/events/", PORT);
		Log.i("REST", response.getResponseString());
		return response.getResponseString();
	}
	
	public static String doSomething() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			
		}
		return "yeah";
	}

}
