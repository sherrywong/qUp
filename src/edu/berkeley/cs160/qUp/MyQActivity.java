package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;


public class MyQActivity extends Activity {

    TextView business_name_0, business_time_0;
    TextView business_name_1, business_time_1;
    TextView business_name_2, business_time_2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_q);
        
        /*
         * Initialize the UI elements
         */
        business_name_0 = (TextView) findViewById(R.id.business_name_0);
        business_name_1 = (TextView) findViewById(R.id.business_name_1);
        business_name_2 = (TextView) findViewById(R.id.business_name_2);
        
        business_time_0 = (TextView) findViewById(R.id.business_time_0);
        business_time_1 = (TextView) findViewById(R.id.business_time_1);
        business_time_2 = (TextView) findViewById(R.id.business_time_2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
