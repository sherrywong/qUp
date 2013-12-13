package edu.berkeley.cs160.qUp.activities.business;

import java.util.Calendar;

import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.MyQActivity;
import edu.berkeley.cs160.qUp.activities.premium.ReservationForm.DatePickerFragment;
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

public class QPonListExample extends Activity {
	
	Button newQponBtn;
	
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
        	Intent intent = new Intent(context, BusinessActivityForm.class);
        	startActivity(intent);
        }        
    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.qpon_list);
    	
    	newQponBtn = (Button) findViewById(R.id.newQponBtn);
    	newQponBtn.setOnClickListener(new ButtonListener(this));
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
