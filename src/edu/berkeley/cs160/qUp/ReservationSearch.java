package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class ReservationSearch extends Activity {
	
	EditText name;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.reservation_search);
 
    	name = (EditText) findViewById(R.id.businessName);
    }
}
