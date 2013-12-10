package edu.berkeley.cs160.qUp.activities.business;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.MyQActivity;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;

public class BusinessActivityForm extends Activity {
	
	protected static final int from = 0;
	protected static final int to = 1 ;
	protected static final int send = 2 ;

	static int whichBtn;
	Spinner location;
	EditText title, description;	
	static Button sendBtn, dateFrom, dateTo;
	
	//TODO: Hook up the date picker
    
	
	/*
	 * Date picker class
	 * From android developer Picker tutorial
	 */
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			switch (whichBtn) {
				case from:
					dateFrom.setText(month + "/" + day + "/" + year);
					break;
				case to:
					dateTo.setText(month + "/" + day + "/" + year);
					break;
				case send:
				default:
					break;
			}
		}
	}

	/*
     * Private Listener Class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        String type;
        //Constructor
        public ButtonListener(Context context,String type) {
            this.context = context;
            this.type = type;
        }

        @Override
        public void onClick(View arg0) {
        	//TODO: Add toast confirmation
        	if (this.type.equals("send")) {
        		whichBtn = from;
        		Intent intent = new Intent(context,BusinessActivityMain.class);
        		startActivity(intent);
        	}
        	else if (this.type.equals("dateFrom")) {
        		whichBtn = from;
        		showDatePickerDialog(arg0);
        		//TODO: display the chosen date
        	}
        	else if (this.type.equals("dateTo")) {
        		whichBtn = to;
        		showDatePickerDialog(arg0);        		
        		//TODO: display the chosen date
        	}
        }
        
        public void showDatePickerDialog(View v) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getFragmentManager(), "datePicker");
        }
    }
    	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.business_activity_form);
 
    	location      = (Spinner)  findViewById(R.id.locationSpinner);
    	title         = (EditText) findViewById(R.id.titleText);
    	description   = (EditText) findViewById(R.id.descriptionText);
    	sendBtn       = (Button)   findViewById(R.id.sendButton);
    	dateFrom      = (Button)   findViewById(R.id.dateFrom);
    	dateTo        = (Button)   findViewById(R.id.dateTo);
    	
    	sendBtn.setOnClickListener(new ButtonListener(this,"send"));
    	dateFrom.setOnClickListener(new ButtonListener(this,"dateFrom"));
    	dateTo.setOnClickListener(new ButtonListener(this,"dateTo"));
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

