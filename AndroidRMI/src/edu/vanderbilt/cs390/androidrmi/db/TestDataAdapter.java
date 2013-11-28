package edu.vanderbilt.cs390.androidrmi.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TestDataAdapter {
	private final static String TAG = "TestDataAdapter";
	
	private static String[] allColumns_ = { TestSQLiteHelper.COLUMN_ID,
			TestSQLiteHelper.COLUMN_SPEED, TestSQLiteHelper.COLUMN_BATTERY,
			TestSQLiteHelper.COLUMN_DIFF, TestSQLiteHelper.COLUMN_TIME,
			TestSQLiteHelper.COLUMN_LOCAL };

	public static TestData createData(double speed, double battery, long diff,
			long time, int isLocal, SQLiteOpenHelper dbHelper) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TestSQLiteHelper.COLUMN_SPEED, speed);
		values.put(TestSQLiteHelper.COLUMN_BATTERY, battery);
		values.put(TestSQLiteHelper.COLUMN_DIFF, diff);
		values.put(TestSQLiteHelper.COLUMN_TIME, time);
		values.put(TestSQLiteHelper.COLUMN_LOCAL, isLocal);
		long insertId = database.insert(TestSQLiteHelper.TABLE_DATA, null,
				values);
		Log.d(TAG, "insert new " + insertId);
		database.close();
		
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(TestSQLiteHelper.TABLE_DATA,
				allColumns_, TestSQLiteHelper.COLUMN_ID + " = " + insertId,
				null, null, null, null);
		cursor.moveToFirst();
		TestData data = cursorToData(cursor);
		cursor.close();
		database.close();
		return data;
	}

	public static TestData cursorToData(Cursor cursor) {
		TestData data = new TestData();
		data.setID(cursor.getLong(0));
		data.setSpeed(cursor.getDouble(1));
		data.setBattery(cursor.getDouble(2));
		data.setDiff(cursor.getInt(3));
		data.setTime(cursor.getLong(4));
		data.setLocal(cursor.getInt(5));
		return data;
	}

	public static List<TestData> getDatasByQuery(String query,
			SQLiteOpenHelper dbHelper, String orderBy) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(TestSQLiteHelper.TABLE_DATA,
				allColumns_, query, null, null, null, orderBy);
		cursor.moveToFirst();
		List<TestData> list = new ArrayList<TestData>();
		while (!cursor.isAfterLast()) {
			list.add(cursorToData(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		database.close();
		return list;
	}

}
