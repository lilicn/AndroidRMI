package edu.vanderbilt.cs390.androidrmi;

import edu.vanderbilt.cs390.androidrmi.db.TestSQLiteHelper;
import android.app.Activity;
import android.database.sqlite.SQLiteOpenHelper;

public class BaseActivity extends Activity {
	protected static SQLiteOpenHelper dbHelper_;

	@Override
	protected void onPause() {
		super.onPause();
		dbHelper_.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
		dbHelper_ = new TestSQLiteHelper(this);
	}

}
