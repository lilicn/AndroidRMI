package edu.vanderbilt.cs390.androidrmi;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

import edu.vanderbilt.cs390.androidrmi.db.TestData;
import edu.vanderbilt.cs390.androidrmi.db.TestDataAdapter;
import edu.vanderbilt.cs390.androidrmi.db.TestSQLiteHelper;
import edu.vanderbilt.cs390.androidrmi.util.Utils;

public class GraphActivity extends BaseActivity {
	private LinearLayout layout;
	private GraphView graphView_;
	private EditText editDiff_;
	private int diff_;
	private final static String TAG = "GraphActivity";

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		layout = (LinearLayout) findViewById(R.id.linearlayout);
		editDiff_ = (EditText) findViewById(R.id.editDiff);
		graphView_ = new LineGraphView(this, "GraphViewDemo");
	}

	public void runShowGraph(View v) {
		diff_ = Utils.checkText(editDiff_);
		if (Utils.validIndex(diff_)) {
			addGraph(1, Color.CYAN);
			addGraph(0, Color.BLUE);
			showGraph();
		} else {
			Log.e(TAG, "input error");
			Toast.makeText(GraphActivity.this, "Please input an integer",
					Toast.LENGTH_LONG).show();
		}

	}

	public void addGraph(int isLocal, int color) {
		List<TestData> list = TestDataAdapter.getDatasByQuery(
				TestSQLiteHelper.COLUMN_LOCAL + " = " + isLocal + " AND "
						+ TestSQLiteHelper.COLUMN_DIFF + "=" + diff_,
				dbHelper_, TestSQLiteHelper.COLUMN_SPEED);
		int num = list.size();
		if (num == 0) {
			Log.e(TAG, "not data");
			Toast.makeText(GraphActivity.this,
					"No data for difficulty = " + diff_, Toast.LENGTH_LONG)
					.show();
			return;
		}
		GraphViewData[] data = new GraphViewData[num];
		for (int i = 0; i < num; i++) {
			TestData d = list.get(i);
			data[i] = new GraphViewData(d.getSpeed(), d.getTime());
		}
		GraphViewSeries series = new GraphViewSeries(isLocal == 1 ? "Local"
				: "Remote", null, data);
		series.getStyle().color = color;
		graphView_.addSeries(series);
		Log.d(TAG, "add graph " + isLocal);
	}

	public void showGraph() {
		// graphView.setViewPort(0, 10);
		graphView_.setScalable(true);
		// optional - legend
		graphView_.setShowLegend(true);
		graphView_.getGraphViewStyle().setGridColor(Color.GREEN);
		graphView_.getGraphViewStyle().setHorizontalLabelsColor(Color.RED);
		graphView_.getGraphViewStyle().setVerticalLabelsColor(Color.RED);
		layout.addView(graphView_);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

}
