package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
        		//TODO: Start the Offer qPon form intent
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
}
