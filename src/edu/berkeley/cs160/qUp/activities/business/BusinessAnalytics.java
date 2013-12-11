package edu.berkeley.cs160.qUp.activities.business;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.MyQActivity;
import org.joda.time.Period;


/**
 * Purpose of Class:
 * <p/>
 * qUp ==> edu.berkeley.cs160.qUp.activities.business
 * Date: 12/9/13
 * Time: 3:25 PM
 * Version: 1.0
 */
public class BusinessAnalytics extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_charting);

        // Find the chart view
        ChartView chartView = (ChartView) findViewById(R.id.business_charting);

        // Create the arriving queue points (Blue)
        LinearSeries series_arriving = new LinearSeries();
        series_arriving.setLineColor(0xff0000ff);
        series_arriving.setLineWidth(6);



        for (double i = 0d; i <= (2d); i += 0.1d) {
            series_arriving.addPoint(new LinearSeries.LinearPoint(i, 3 * i));
        }

        // Create the departures queue points (Yellow)
        LinearSeries series_departures = new LinearSeries();
        series_departures.setLineColor(0xffffff00);
        series_departures.setLineWidth(6);



        for (double i = 0d; i <= (2d); i += 0.1d) {
            series_departures.addPoint(new LinearSeries.LinearPoint(i, 1.5 * i ));
        }

        // Add chart view data
        chartView.addSeries(series_arriving);
        chartView.addSeries(series_departures);

        chartView.setLeftLabelAdapter(new ValueLabelAdapter(this, ValueLabelAdapter.LabelOrientation.VERTICAL));
        chartView.setBottomLabelAdapter(new ValueLabelAdapter(this, ValueLabelAdapter.LabelOrientation.HORIZONTAL));

    }

    /*
	 * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
   
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case R.id.personal:
            Intent intent = new Intent(this, MyQActivity.class);
            startActivity(intent);
    		return true;
    	case R.id.business:
            intent = new Intent(this, BusinessActivityMain.class);
            startActivity(intent);
            return true;
    	default:
            return super.onOptionsItemSelected(item);
      }
    }



}

