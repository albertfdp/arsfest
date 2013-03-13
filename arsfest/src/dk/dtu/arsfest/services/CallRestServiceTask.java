package dk.dtu.arsfest.services;

import dk.dtu.arsfest.R;
import dk.dtu.arsfest.rest.RestClient;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class CallRestServiceTask extends AsyncTask {
	
	private ProgressDialog progressDialog;
	protected Context context;
	
	public CallRestServiceTask(Context context) {
		super();
		this.context = context;
	}
	
	@Override
	protected void onPreExecute() {
		this.progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.rest_task_title),
				context.getResources().getString(R.string.rest_task_message));
	}
	
	@Override
	protected String doInBackground(Object... params) {
		//return RestClient.getEvents();
		return RestClient.doSomething();
	}
	
	protected void onPostExecute(String result) {
		this.progressDialog.cancel();
	}
	

}
