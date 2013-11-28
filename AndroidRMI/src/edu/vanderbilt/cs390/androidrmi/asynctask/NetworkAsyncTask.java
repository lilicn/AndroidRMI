package edu.vanderbilt.cs390.androidrmi.asynctask;

import edu.vanderbilt.cs390.androidrmi.util.Utils;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
	private final static String TAG = "NetworkAsyncTask";
	private TextView speedText_;

	public NetworkAsyncTask(TextView textV) {
		this.speedText_ = textV;
	}

	/**
	 * url[0] = "androidnetworktester.googlecode.com"
	 */
	@Override
	protected String doInBackground(String... url) {
		return Utils.getSpeedFromDouble(Utils.getSpeed(url[0]));
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null)
			speedText_.setText(result);
		else {
			Log.e(TAG, "Sorry test failed");
		}
	}
}
