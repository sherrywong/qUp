package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyQActivity extends Activity {
    String getBusiness_name_0, getBusiness_time_0;
    String getBusiness_name_1, getBusiness_time_1;
    String getBusiness_name_2, getBusiness_time_2;

    TextView business_name_0, business_time_0;
    TextView business_name_1, business_time_1;
    TextView business_name_2, business_time_2;


    Button tagBtn;

    /*
     * Private Listener Class
     * onClick() will go to the URLHandler class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        //Constructor
        public ButtonListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {
            Intent intent = new Intent(context, TagInHandler.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_q);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            getBusiness_name_0 = extras.getString("biz_name_1");

        }
        //TODO Get id and client key
        //Parse.initialize(this,  id, clientKey);
        //ParseAnalytics.trackAppOpened(getIntent());

        /*
         * Initialize the UI elements
         */
        business_name_0 = (TextView) findViewById(R.id.business_name_0);
        business_name_1 = (TextView) findViewById(R.id.business_name_1);
        business_name_2 = (TextView) findViewById(R.id.business_name_2);

        business_time_0 = (TextView) findViewById(R.id.business_time_0);
        business_time_1 = (TextView) findViewById(R.id.business_time_1);
        business_time_2 = (TextView) findViewById(R.id.business_time_2);

        /*
         * Fill in the values based on the previous intent
         */
        Intent intent = getIntent();
    	//business_name_0.setText(intent.getStringExtra(MainActivity.BUSINESS_NAME_0));
    	//business_name_1.setText(intent.getStringExtra(MainActivity.BUSINESS_NAME_1));
    	//business_name_2.setText(intent.getStringExtra(MainActivity.BUSINESS_NAME_2));

    	//business_time_0.setText(intent.getStringExtra(MainActivity.BUSINESS_TIME_0));
    	//business_time_1.setText(intent.getStringExtra(MainActivity.BUSINESS_TIME_1));
    	//business_time_2.setText(intent.getStringExtra(MainActivity.BUSINESS_TIME_2));
        
    	business_name_0.setText(MainActivity.BUSINESS_NAME_0);
    	business_name_1.setText(MainActivity.BUSINESS_NAME_1);
    	business_name_2.setText(MainActivity.BUSINESS_NAME_2);

    	business_time_0.setText(MainActivity.BUSINESS_TIME_0);
    	business_time_1.setText(MainActivity.BUSINESS_TIME_1);
    	business_time_2.setText(MainActivity.BUSINESS_TIME_2);


        tagBtn = (Button) findViewById(R.id.app_tag);
        tagBtn.setOnClickListener((android.view.View.OnClickListener) new ButtonListener(this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
