package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.berkeley.cs160.qUp.R;

public class BusinessActivityMain extends Activity {

	protected static final String analyitcs = "analytics";
	protected static final String qpon = "qpon";
	
	TextView length;
	Button analyticsBtn, qPonBtn;
		
    /*
     * Private Listener Class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        String type; //Analytics or Offer qPon
        //Constructor
        public ButtonListener(Context context, String type) {
            this.context = context;
            this.type = type;
        }

        @Override
        public void onClick(View arg0) {
        	if (type.equals("analytics")) {
                //TODO: Start the Business Analytics intent        		
        	}
        	else if (type.equals("qpon")) {
                Intent intent = new Intent(context, BusinessActivityForm.class);
                startActivity(intent);
        	}
        }
    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.business_activity_main);
 
    	length       = (TextView) findViewById(R.id.length);
    	analyticsBtn = (Button) findViewById(R.id.analytics);
    	qPonBtn      = (Button) findViewById(R.id.qpon);	
    	
    	 //Set up the listeners for Analytics, offer qPon buttons
    	analyticsBtn.setOnClickListener(new ButtonListener(this, analyitcs));
    	qPonBtn.setOnClickListener(new ButtonListener(this,qpon));
    }
    
    /*
     * Once we get the data, we need to sent the length
     * for the owner to view.
     */
    public void setLength(int newLength) {
    	length.setText(newLength + "");
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
