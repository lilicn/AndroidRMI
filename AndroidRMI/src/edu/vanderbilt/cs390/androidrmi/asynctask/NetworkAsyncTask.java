package edu.vanderbilt.cs390.androidrmi.asynctask;

import edu.vanderbilt.cs390.androidrmi.util.Utils;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

/**
 * asynctask to get network speed
 * 
 * @author Li
 * 
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, String> {
	private final String TAG = "NetworkAsyncTask";
	private TextView speedText_;
	private ProgressDialog progress_;

	public NetworkAsyncTask(TextView textV, ProgressDialog progress) {
		this.speedText_ = textV;
		this.progress_ = progress;
	}

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
		progress_.dismiss();
	}
}
