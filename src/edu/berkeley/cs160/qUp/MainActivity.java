package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONObject;

public class MainActivity extends Activity {

    private Button loginBtn;

    private EditText username;
    private EditText password;

    private ProgressBar mProgressBar;
    private Context context;

    public void cleanUpWritingToTag() {

        //TODO: Write this method
    }

    /*
     * Private Listener Class
     * onClick() will go to the MyQActivity class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;
        EditText username,password;
        //Constructor
        public ButtonListener(Context context, EditText username, EditText password) {
            this.username = username;
            this.password = password;
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {
            //TODO remove this
            //This just fills in some fake data for now
            ParseObject newUser = new ParseObject("user");
            JSONObject profile = new JSONObject();
            JSONObject restaurants = new JSONObject();
            try {
                profile.put("username", username);
                profile.put("password", password);
                newUser.put("profile", profile);

                restaurants.put("cheeseboard", "00:10");
                restaurants.put("sliver", "00:14");
                restaurants.put("purple kow", "01:30");

                newUser.put("queue",restaurants);
                newUser.saveInBackground();
            }
            catch (Exception e) {}

            Intent intent = new Intent(context, MyQActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Initialize the Parse information
        Parse.initialize(this, "gQiGNtNHGVLvJd9lyH9s0AOpBuRPwgPJPCb5g7fQ", "jg9f4X7ij8r2COoUy2qOsmEb9EADrG0R6kRqWxQy");
        ParseAnalytics.trackAppOpened(getIntent());


        /*
         * Initialize the UI form elements
         */
        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener((android.view.View.OnClickListener) new ButtonListener(this, username, password));

    }

    /**
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener
                (new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        mProgressBar.setVisibility(View.VISIBLE);

                        String usernameString = username.getText().toString();
                        String passwordString = password.getText().toString();
                        ParseUser user = new ParseUser();
                        user.setUsername(usernameString);
                        user.setPassword(passwordString);


                        ParseUser.logInInBackground(usernameString, passwordString,
                                new LogInCallback() {
                                    @Override
                                    public void done(ParseUser parseUser, com.parse.ParseException e) {
                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        if (parseUser != null) {
                                            // Hooray! The user is logged in.
                                            startActivity(new Intent(
                                                    MainActivity.this,
                                                    MyQActivity.class));
                                        } else {
                                            // Login failed. Look at the
                                            // ParseException to see what happened.
                                            Toast.makeText(
                                                    MainActivity.this,
                                                    "Login failed! Try again.",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                    }

                });
    }
**/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

