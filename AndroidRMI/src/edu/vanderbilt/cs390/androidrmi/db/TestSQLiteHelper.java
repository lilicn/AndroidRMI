package edu.vanderbilt.cs390.androidrmi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestSQLiteHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "datas.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_DATA = "data";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_SPEED = "speed";
	public static final String COLUMN_BATTERY = "battery";
	public static final String COLUMN_DIFF = "diff";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_LOCAL = "islocal";
	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table " + TABLE_DATA
			+ " (" + COLUMN_ID + " INTEGER primary key autoincrement, "
			+ COLUMN_SPEED + " REAL not null, " + COLUMN_BATTERY
			+ " REAL not null, " + COLUMN_DIFF + " REAL not null, "
			+ COLUMN_TIME + " REAL not null," + COLUMN_LOCAL + " INTEGER not null);";

	public TestSQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TestSQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
		onCreate(db);
	}
}
