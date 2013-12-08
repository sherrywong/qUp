package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BusinessActivityForm extends Activity {
	
	Spinner location, durationUnits;
	EditText title, description, durationValue;	
	Button sendBtn;	
	//TODO: Hook up the date picker
    
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
    	setContentView(R.layout.business_activity_form);
 
    	location      = (Spinner)  findViewById(R.id.locationSpinner);
    	durationUnits = (Spinner)  findViewById(R.id.durationUnits);
    	title         = (EditText) findViewById(R.id.titleText);
    	description   = (EditText) findViewById(R.id.descriptionText);
    	durationValue = (EditText) findViewById(R.id.durationValue);
    	sendBtn       = (Button)   findViewById(R.id.sendButton);
    
    	//Set the possible duration values
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
    			R.array.duration_array,android.R.layout.simple_spinner_item);
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	durationUnits.setAdapter(adapter);
    }
    
    /*
     * Each business have different business locations
     * So we will give the option to select one or all
     */
    public void setLocations() {
    	//TODO: implement me
    }

    
    /*
     * Get the values of the form
     */
    public String getLocation() {
    	return this.location.getSelectedItem().toString();
    }
    
    public String getFormTitle() {
    	//TODO: Give warning when bad args
    	return this.title.getText().toString();
    }
    
    public String getDescription() {
    	//TODO: Give warning when bad args
    	return this.description.getText().toString();
    }
    
    public String getStartDate() {
    	//TODO: Give warning when bad args
    	//TODO: implement me
    	return null;
    }
    
    public String getDurationValue() {
    	return this.durationValue.getText().toString();
    }
    
    public String getDurationUnits() {
    	return this.durationUnits.getSelectedItem().toString();
    }
}

