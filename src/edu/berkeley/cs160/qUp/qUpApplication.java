package edu.berkeley.cs160.qUp;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;

/**
 * Created by sidneyfeygin on 11/25/13.
 */
public class qUpApplication extends Application {

    private static String tag = "qUpApplication";

    private static String PARSE_APPLICATION_ID = "gQiGNtNHGVLvJd9lyH9s0AOpBuRPwgPJPCb5g7fQ";
    private static String PARSE_CLIENT_KEY = "jg9f4X7ij8r2COoUy2qOsmEb9EADrG0R6kRqWxQy";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(tag, "initializing qUp with keys");
        //initialize with splash screen
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        //This creates an anonymous user
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);


        ParseACL.setDefaultACL(defaultACL, true);

        Log.d(tag, "initializing app complete");

    }
}
