package edu.berkeley.cs160.qUp.activities.business;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import com.fima.chartview.ChartView;
import com.fima.chartview.LinearSeries;
import edu.berkeley.cs160.qUp.R;

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

        // Create the data points
        LinearSeries series = new LinearSeries();
        series.setLineColor(0xFF0099CC);
        series.setLineWidth(2);

        for (double i = 0d; i <= (2d * Math.PI); i += 0.1d) {
            series.addPoint(new LinearSeries.LinearPoint(i, Math.sin(i)));
        }

        // Add chart view data
        chartView.addSeries(series);
        chartView.setLeftLabelAdapter(new ValueLabelAdapter(this, ValueLabelAdapter.LabelOrientation.VERTICAL));
        chartView.setBottomLabelAdapter(new ValueLabelAdapter(this, ValueLabelAdapter.LabelOrientation.HORIZONTAL));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.business_main, menu);
        return true;
    }

}
