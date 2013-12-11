package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.gson.Gson;
import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.Model.User;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.premium.ReservationSearch;
import edu.berkeley.cs160.qUp.animlv.AnimatedListViewAdapter;
import edu.berkeley.cs160.qUp.netio.QueueListUpdateListener;
import edu.berkeley.cs160.qUp.netio.RESTController;
import edu.berkeley.cs160.qUp.netio.UserListResponse;
import edu.berkeley.cs160.qUp.qUpApplication;

import java.util.List;

public class MainActivity extends Activity {

    Button loginBtn;
    EditText username, password;
    User mUser;

    Button registerBtn;


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
            for (int i = 0; i < userList.size(); i++) {
                User user = userList.get(i);
                if (user.getUsername().equals(username.getText().toString())) {

                    mUser = user;
                    Gson userGson = new Gson();

                    if (mUser.isBusiness) {
                        Intent bizIntent = new Intent(MainActivity.this, BusinessActivityMain.class);
                        bizIntent.putExtra("User", userGson.toJson(mUser));
                        bizIntent.putExtra("isBusiness", true);
                        startActivity(bizIntent);
                    }

                    Intent intent;


                    if (mUser.isPremium) {
                        intent = new Intent(MainActivity.this, MyQActivity.class);
                        intent.putExtra("User", userGson.toJson(mUser));
                        intent.putExtra("isPremium", true);
                        startActivity(intent);
                    }

                    else{
                        intent = new Intent(MainActivity.this, MyQActivity.class);
                        intent.putExtra("User", userGson.toJson(mUser));
                        intent.putExtra("isPremium", false);
                        startActivity(intent);
                    }
                }
            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        /*
         * Initialize the UI form elements
         */
        loginBtn = (Button) findViewById(R.id.loginButton);
        loginBtn.setOnClickListener(new ButtonListener(this));

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);


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
            RESTController.retrieveUserList(userListResponse, un, ps, sr);

        }

    }
}
