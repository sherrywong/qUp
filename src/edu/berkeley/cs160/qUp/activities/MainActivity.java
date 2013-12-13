package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.google.gson.Gson;
import edu.berkeley.cs160.qUp.model.User;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.netio.RESTController;
import edu.berkeley.cs160.qUp.netio.UserListResponse;

public class MainActivity extends Activity {

    public static final String PREFS_NAME = "userInfo";
    Button loginBtn;
    EditText username, password;
    User mUser;
    Button registerBtn;
    ProgressBar progressBar;
    /**
     * Handler for the User list being fetched remotely
     */
    private UserListResponse userListResponse = new UserListResponse() {

        @Override
        public void fail(Exception ex) {
            super.fail(ex);

        }

        @Override
        public void success(String json) {
            super.success(json);

            // Commit the ed
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                if (user.getUsername().equals(username.getText().toString())) {

                    mUser = user;
                    Gson userGson = new Gson();

                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("user", userGson.toJson(mUser));
                    editor.commit();

                    if (mUser.isBusiness) {
                        Intent bizIntent = new Intent(MainActivity.this, BusinessActivityMain.class);
                        startActivity(bizIntent);
                    }

                    Intent intent;


                    if (mUser.isPremium) {
                        intent = new Intent(MainActivity.this, MyQActivity.class);
                        startActivity(intent);
                    }

                    else{
                        intent = new Intent(MainActivity.this, MyQActivity.class);
                        startActivity(intent);
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        /*
         * Initialize the UI form elements
         */
        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new ButtonListener(this));

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        registerBtn = (Button) findViewById(R.id.register_button);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public Intent fillUserIntentExtras(User user) {


        return null;
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

            String un = username.getText().toString();
            String ps = password.getText().toString();
            String sr = "/users/";
            progressBar.setVisibility(View.VISIBLE);
            RESTController.retrieveUserList(userListResponse, un, ps, sr);

        }

    }
}
