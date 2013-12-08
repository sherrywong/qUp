package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ShortestWaitingMap extends Activity {

	EditText start, end;
	Button searchBtn;
	
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
            //TODO: Start the Shortest Waiting Map intent
        }

    }
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.shortest_waiting_map_form);
    	
    	start  = (EditText) findViewById(R.id.startEditText);
    	end    = (EditText) findViewById(R.id.endEditText);
    	searchBtn = (Button) findViewById(R.id.searchButton);
    }
	
}
