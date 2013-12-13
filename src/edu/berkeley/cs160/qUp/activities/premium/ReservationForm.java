package edu.berkeley.cs160.qUp.activities.premium;

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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.MyQActivity;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityForm.DatePickerFragment;

public class ReservationForm extends Activity {
	
	protected static final int reserve = 0;
	protected static final int date = 1;
	
	static int whichBtn;
	TextView name;
	//TODO: Get the time and stuff
	//TODO: Get the date and stuff
	static Button reserveBtn,datePickerBtn;

	
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
			switch(whichBtn) {
				case date:
					datePickerBtn.setText(month + "/" + day + "/" + year);
					break;
				case reserve:
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
        	//TODO: add confirmation
        	if (type.equals("reserve")) {
        		whichBtn = reserve;
        		Intent intent = new Intent(context, MyQActivity.class);
        		startActivity(intent);
        	}
        	else if (type.equals("datePicker")) {
        		whichBtn = date;
        		showDatePickerDialog(arg0);
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
    	setContentView(R.layout.reservation_form);
    	
    	name = (TextView) findViewById(R.id.name);
    	//TODO: get date and time values
    	reserveBtn = (Button) findViewById(R.id.reserveButton);
    	datePickerBtn = (Button) findViewById(R.id.datePicker);
    	reserveBtn.setOnClickListener(new ButtonListener(this,"reserve"));
    	datePickerBtn.setOnClickListener(new ButtonListener(this,"datePicker"));
    	
    	Intent intent = getIntent();
    	String businessName = intent.getStringExtra("Business");
    	name.setText(businessName);
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
    	//TODO: implement mee
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
