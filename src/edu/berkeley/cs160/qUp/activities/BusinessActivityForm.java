package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.MyQActivity;

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
        //Constructor
        public ButtonListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {
        	//TODO: Add toast confirmation
        	Intent intent = new Intent(context,BusinessActivityMain.class);
        	startActivity(intent);
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
    
    	sendBtn.setOnClickListener(new ButtonListener(this));
    	
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
    	//TODO: Give warning about bad args
    	return this.title.getText().toString();
    }
    
    public String getDescription() {
    	//TODO: Give warning about bad args
    	return this.description.getText().toString();
    }
    
    public String getStartDate() {
    	//TODO: Give warning about bad args
    	//TODO: implement me
    	return null;
    }
    
    public String getDurationValue() {
    	return this.durationValue.getText().toString();
    }
    
    public String getDurationUnits() {
    	return this.durationUnits.getSelectedItem().toString();
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

