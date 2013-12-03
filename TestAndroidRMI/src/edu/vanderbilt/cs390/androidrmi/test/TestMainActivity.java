package edu.vanderbilt.cs390.androidrmi.test;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import edu.vanderbilt.cs390.androidrmi.GraphActivity;
import edu.vanderbilt.cs390.androidrmi.MainActivity;

/**
 * test for main activity
 * 
 * @author Li
 * 
 */
public class TestMainActivity extends
		ActivityInstrumentationTestCase2<MainActivity> {

	private MainActivity activity_;

	public TestMainActivity() {
		super(MainActivity.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		setActivityInitialTouchMode(false);
		activity_ = getActivity();
	}

	/**
	 * test menu show button to change to graph activity
	 */
	public void testChangeActivity() {
		Instrumentation.ActivityMonitor monitor = getInstrumentation()
				.addMonitor(GraphActivity.class.getName(), null, false);
		getInstrumentation().sendKeyDownUpSync(KeyEvent.KEYCODE_MENU);
		getInstrumentation().invokeMenuActionSync(activity_,
				edu.vanderbilt.cs390.androidrmi.R.id.showGraph, 0);
		Activity graphActivity = getInstrumentation()
				.waitForMonitorWithTimeout(monitor, 10000);
		assertNotNull("not null", graphActivity);
		graphActivity.finish();
	}

}
