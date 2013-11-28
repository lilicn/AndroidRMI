package edu.vanderbilt.cs390.androidrmi.asynctask;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import edu.vanderbilt.cs390.androidrmi.util.Utils;

public class ComputeAsyncTask extends AsyncTask<Integer, Integer, String> {
	private TextView tView_;
	private TextView timeV_;
	private final static String TAG = "ComputeAsyncTask";
	private boolean isRemote_;
	private long start;
	public ComputeAsyncTask(TextView tView, TextView timeV, boolean isRemote) {
		this.tView_ = tView;
		this.timeV_ = timeV;
		this.isRemote_ = isRemote;
	}

	@Override
	protected String doInBackground(Integer... params) {
		// TODO Auto-generated method stub
		start = System.currentTimeMillis();
		Log.d(TAG,isRemote_+"");
		if(isRemote_) return Utils.remote(params[0]);
		else return Utils.local(params[0]);
		
	}

	@Override
	public void onPostExecute(String result) {
		if(result!=null){
			long end = System.currentTimeMillis();
			tView_.setText(result);
			timeV_.setText((end-start)+"");
		}
		else
			Log.e(TAG,"result is null");
	}

}
