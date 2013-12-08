package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ReservationForm extends Activity {
	
	TextView name;
	//TODO: Get the time and stuff
	//TODO: Get the date and stuff
	Button reserveBtn;
		
    /*
     * Private Listener Class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        //Constructor
        public ButtonListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {
        	//TODO: add confirmation
        	Intent intent = new Intent(context, MyQActivity.class);
        	startActivity(intent);
        }
    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.reservation_form);
    	
    	name = (TextView) findViewById(R.id.name);
    	//TODO: get date and time values
    	reserveBtn = (Button) findViewById(R.id.reserveButton);
    
    	reserveBtn.setOnClickListener(new ButtonListener(this));
    }
    

    /*
     * Getters and Setters for outsiders
     */
    public void setName(String businessName) {
    	name.setText(businessName);
    }
    
    public String getName() {
    	return name.getText().toString();
    }
    
    public String getDate() {
    	//TODO: implement me
    	return null;
    }
    
    public String getTime() {
    	//TODO: implement me
    	return null;
    }
}
