package edu.vanderbilt.cs390.androidrmi;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import edu.vanderbilt.cs390.androidrmi.asynctask.AllTestAsyncTask;
import edu.vanderbilt.cs390.androidrmi.asynctask.ComputeAsyncTask;
import edu.vanderbilt.cs390.androidrmi.asynctask.NetworkAsyncTask;
import edu.vanderbilt.cs390.androidrmi.util.Utils;

/**
 * Main activity for all kinds of tests
 * 
 * @author Li
 * 
 */
public class MainActivity extends BaseActivity {
	private TextView speedText_;
	private TextView batteryText_;
	private TextView rmiText_;
	private TextView localText_;
	private TextView timeText_;
	private Context ctx_;
	private final String TAG = "MainActivity";
	private ProgressDialog progress_;
	// progress title
	private final String TESTSPEED = "test speed";
	private final String TESTBATTERY = "Test battery status";
	private final String TESTREMOTE = "Test remote function";
	private final String TESTLOCAL = "Test local function";
	private final String TESTALL = "Test all";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		speedText_ = (TextView) findViewById(R.id.speedText);
		batteryText_ = (TextView) findViewById(R.id.batteryText);
		rmiText_ = (TextView) findViewById(R.id.textViewRmi);
		localText_ = (TextView) findViewById(R.id.textviewLocal);
		timeText_ = (TextView) findViewById(R.id.textviewTime);
		ctx_ = MainActivity.this;
		progress_ = new ProgressDialog(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.showGraph:
			runShowGraph();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * test current speed when testspeed button is clicked
	 * 
	 * @param v
	 */
	public void runTestSpeed(View v) {
		Utils.setProgressVisible(progress_, TESTSPEED, TESTSPEED);
		new NetworkAsyncTask(speedText_, progress_).execute(Utils.URL);
	}

	/**
	 * test current battery status when batterystatus button is clicked
	 * 
	 * @param v
	 */
	public void runBatteryStatus(View v) {
		Utils.setProgressVisible(progress_, TESTBATTERY, TESTBATTERY);
		batteryText_.setText(Utils.getBatteryLevel(this) + "%");
		progress_.dismiss();
	}

	/**
	 * test remote function when remote button is clicked
	 * 
	 * @param v
	 */
	public void runRemote(View v) {
		Utils.setProgressVisible(progress_, TESTREMOTE, TESTREMOTE);
		showDialog(true, rmiText_, timeText_, false);
	}

	/**
	 * test local function when local button is clicked
	 * 
	 * @param v
	 */
	public void runLocal(View v) {
		Utils.setProgressVisible(progress_, TESTLOCAL, TESTLOCAL);
		showDialog(false, localText_, timeText_, false);
	}

	/**
	 * run all tests when test button is clicked
	 * 
	 * @param v
	 */
	public void runAllTest(View v) {
		Utils.setProgressVisible(progress_, TESTALL, TESTALL);
		showDialog(true, null, null, true);
	}

	/**
	 * show the graph about the relationship between network speed and run time
	 * when menu item is clicked
	 */
	public void runShowGraph() {
		Intent intent = new Intent(this, GraphActivity.class);
		startActivity(intent);
	}

	/**
	 * show send warning dialog
	 */
	public void showDialog(final boolean isRemote, final TextView textV,
			final TextView timeV, final boolean isAllTest) {
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Specify an integer");
		Button send = (Button) dialog.findViewById(R.id.sendButton);
		final EditText specify = (EditText) dialog.findViewById(R.id.specify);
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final int result = Utils.checkText(specify);
				if (Utils.validIndex(result)) {
					runOnUiThread(new Runnable() {
						public void run() {
							Log.d(TAG, "ready to run: " + result);
							if (isAllTest) {
								Log.d(TAG, "all test");
								new AllTestAsyncTask(ctx_, dbHelper_, progress_)
										.execute(result);
							} else {
								new ComputeAsyncTask(textV, timeV, isRemote,
										progress_).execute(result);
							}
						}
					});
					dialog.dismiss();
				} else {
					Log.e(TAG, "input error");
					Toast.makeText(MainActivity.this,
							"Please input an integer", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		Button cancel = (Button) dialog.findViewById(R.id.cancelButton);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				progress_.dismiss();
			}
		});
		dialog.show();
	}
}
