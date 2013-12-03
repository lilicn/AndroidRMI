package edu.vanderbilt.cs390.androidrmi.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import lipermi.handler.CallHandler;
import lipermi.net.Client;
import edu.vanderbilt.cs390.rmi.Compute;
import edu.vanderbilt.cs390.rmi.ComputeImp;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.EditText;

/**
 * Utility varibles and methods
 * 
 * @author Li
 * 
 */
public class Utils {
	public final static String remoteHost = "10.67.122.98";
	// public final static String remoteHost = "192.168.1.104";
	public final static int portWasBinded = 4455;
	private final static String TAG = "Utils";
	public final static String URL = "androidnetworktester.googlecode.com";

	/**
	 * get string speed from double number
	 * 
	 * @param speed
	 * @return
	 */
	public static String getSpeedFromDouble(double speed) {
		DecimalFormat df = new DecimalFormat("#.#");
		return speed != 0 ? df.format(speed) + " KB/sec" : "0 KB/sec";
	}

	/**
	 * get network speed
	 * 
	 * @param url
	 * @return
	 */
	public static double getSpeed(String url) {
		double result = 0;
		try {
			Socket sock = new Socket(url, 80);
			sock.getOutputStream().write(
					("GET /files/" + "100kb.txt" + " HTTP/1.0\r\n" + "Host: "
							+ url + "\r\n\r\n").getBytes("US-ASCII"));
			InputStream is = sock.getInputStream();
			byte[] b = new byte[4096];
			int read_count, total_read = 0;
			// circumvent a little bit speed progressing of TCP
			is.read(b, 0, 1024);

			long time_begin = SystemClock.uptimeMillis();
			while ((read_count = is.read(b, 0, 4096)) != -1) {
				total_read += read_count;
			}
			result = total_read / 1024.0
					/ ((SystemClock.uptimeMillis() - time_begin) / 1000.0);

			is.close();
			sock.close();
		} catch (UnknownHostException e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
		return result;
	}

	/**
	 * get Battery level
	 * 
	 * @return
	 */
	public static float getBatteryLevel(Context ctx) {
		Intent batteryIntent = ctx.registerReceiver(null, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

		// Error checking that probably isn't needed but I added just in case.
		if (level == -1 || scale == -1) {
			return 50.0f;
		}

		return ((float) level / (float) scale) * 100.0f;
	}

	/**
	 * call local compute function
	 * 
	 * @param param
	 * @return
	 */
	public static String local(Integer param) {
		return new ComputeImp().getPI(param);
	}

	/**
	 * call remote compute function
	 * 
	 * @param param
	 * @return
	 */
	public static String remote(Integer param) {
		CallHandler callHandler = new CallHandler();
		try {
			Client client = new Client(Utils.remoteHost, Utils.portWasBinded,
					callHandler);
			Log.d(TAG, "clinet success");
			Compute remoteObject = (Compute) client.getGlobal(Compute.class);
			Log.d(TAG, "get global success");
			return remoteObject.getPI(param);
		} catch (IOException e) {
			Log.e(TAG, e.toString() + ": " + e.getMessage());
		}
		return null;
	}

	/**
	 * check edittext is not null
	 * 
	 * @param text
	 * @return
	 */
	public static int checkText(EditText text) {
		String temp = text.getText().toString();
		return temp.equals("") ? 0 : Integer.parseInt(temp);
	}

	/**
	 * check edittext > 0
	 * 
	 * @param ind
	 * @return
	 */
	public static boolean validIndex(int ind) {
		return ind > 0 ? true : false;
	}

	/**
	 * setProgressVisible method will set the progress dialog visible and
	 * initialize the title and message.
	 * 
	 * @param progress
	 *            Progress dialog in UI thread
	 */
	public static void setProgressVisible(ProgressDialog progress, String msg,
			String title) {
		// Set progressdialog title
		progress.setTitle(title);
		// Set progressdialog message
		progress.setMessage(msg);
		progress.setIndeterminate(false);
		// Show progressdialog
		progress.show();
	}
}
