package edu.vanderbilt.cs390.androidrmi.asynctask;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import edu.vanderbilt.cs390.androidrmi.db.TestData;
import edu.vanderbilt.cs390.androidrmi.db.TestDataHelper;
import edu.vanderbilt.cs390.androidrmi.util.Utils;

/**
 * asysctask for alltest
 * 
 * @author Li
 * 
 */
public class AllTestAsyncTask extends AsyncTask<Integer, integer, String> {

	private int diff_;
	private Context ctx_;
	private final String URL = "androidnetworktester.googlecode.com";
	private final String TAG = "AllTestAsyncTask";
	private SQLiteOpenHelper dbHelper_;
	private ProgressDialog progress_;

	public AllTestAsyncTask(Context ctx, SQLiteOpenHelper dbHelper,
			ProgressDialog progress) {
		this.ctx_ = ctx;
		this.dbHelper_ = dbHelper;
		this.progress_ = progress;
	}

	@Override
	protected String doInBackground(Integer... diff) {
		this.diff_ = diff[0];

		// get speed and battery status
		double speed = Utils.getSpeed(URL);
		double battery = Utils.getBatteryLevel(ctx_);

		// get remote
		long start = System.currentTimeMillis();
		Utils.remote(diff_);
		long end = System.currentTimeMillis();
		long remoteTime = end - start;
		saveInDB(speed, battery, remoteTime, 0);

		// get local
		start = System.currentTimeMillis();
		Utils.local(diff_);
		end = System.currentTimeMillis();
		long localTime = end - start;
		saveInDB(speed, battery, localTime, 1);
		return "speed: "
				+ Utils.getSpeedFromDouble(speed)
				+ ", battery: "
				+ (battery + "%" + ", run time in local: " + localTime
						+ ", run time in remote: " + remoteTime
						+ ", difficulty is: " + diff_);
	}

	/**
	 * save alltest into database
	 * 
	 * @param speed
	 * @param battery
	 * @param time
	 * @param isLocal
	 */
	public void saveInDB(double speed, double battery, long time, int isLocal) {
		TestData data = TestDataHelper.createData(speed, battery, diff_, time,
				isLocal, dbHelper_);
		Log.d(TAG, "save in db: " + data.getID() + "," + data.getLocal());
	}

	@Override
	public void onPostExecute(String result) {
		Log.d(TAG, result);
		Toast.makeText(ctx_, result, Toast.LENGTH_LONG).show();
		ctx_ = null;
		progress_.dismiss();
	}
}
