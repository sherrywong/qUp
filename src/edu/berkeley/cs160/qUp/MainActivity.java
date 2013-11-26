package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends Activity {

    Button loginBtn;    
    EditText username,password;

    public void cleanUpWritingToTag() {

        //TODO: Write this method
    }

    /*
     * Private Listener Class
     * onClick() will go to the MyQActivity class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        
        //Constructor
        public ButtonListener(Context context) {
            this.context = context;
        }
        
        @Override
        public void onClick(View arg0) {
        	//Log the user ins
        	ParseUser currentUser = ParseUser.getCurrentUser();
        	if (currentUser != null) {
                Intent intent = new Intent(context, MyQActivity.class);
                startActivity(intent);    
        	} else {
        		//Unsuccessful login
        		//Either sign-up or try again
                Intent intent = new Intent(context, SignUp.class);
                startActivity(intent);    
        	}        
        }
        
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*
         * Initialize the UI form elements
         */
        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener((android.view.View.OnClickListener) new ButtonListener(this));
        
        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        
        //Initialize the Parse library
        Parse.initialize(this, "gQiGNtNHGVLvJd9lyH9s0AOpBuRPwgPJPCb5g7fQ", "jg9f4X7ij8r2COoUy2qOsmEb9EADrG0R6kRqWxQy"); 
        ParseAnalytics.trackAppOpened(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
