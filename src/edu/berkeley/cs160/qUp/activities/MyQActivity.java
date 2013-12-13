package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.Model.User;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.map.ShortestWaitingMap;
import edu.berkeley.cs160.qUp.activities.premium.ReservationSearch;
import edu.berkeley.cs160.qUp.netio.QueueListUpdateListener;
import edu.berkeley.cs160.qUp.qUpApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MyQActivity extends Activity implements QueueListUpdateListener {
    //A mapping of  waiting times to business names sorted according to time remaining.
    public TreeMap<Integer, String> mUserQueueTreeMap;
    public ArrayList<Queue> queueList;
    public Runnable reloadQueueList;
    public ArrayList<Queue> mUserQueueList;
    public User mUser;

    {
        reloadQueueList = new Runnable() {


            @Override
            public void run() {

                int userId = mUser.getUserID();
                Date now = getAsTime(new Date());

                //Get all of the queues and filter down to those that the user is in (later move to server side filter).
                // Serializer support is not yet there. We also need to get the waiting time... see next loop.
                for (Queue queue : queueList) {
                    if (userId == queue.getUser().userID) {
                        mUserQueueList.add(queue);
                    }
                }

                //Now get the people who are waiting in queue ahead of user:
                for (Queue q : mUserQueueList) {
                    //At first, we assume there are no people waiting in a queue for a queue that a user is in:
                    q.waiting = 0;
                    //for each queue in our list,
                    for (Queue qq : queueList) {
                        //If our user is waiting in line w/ someone else (i.e., the biz is in the complement of the queues
                        //that our user is in.
                        if (qq.getBusiness().name.equals(q.getBusiness().name) && (qq.getUser().userID != mUser.userID)) {
                            q.waiting++;
                        }
                    }
                }

                for (Queue queue : mUserQueueList) {

                    double avgWaitTime = queue.getBusiness().getAvgWaitTime();
                    //The number of people waiting should have been updated since the last time.
                    Integer minutes_remaining = 0;
                    if (queue.waiting > 0) {

                        minutes_remaining = (int) (avgWaitTime * queue.waiting * 60);


                        mUserQueueTreeMap.put(minutes_remaining, queue.business.name);
                    }

                }

                updateWaitTimes();
                mProgressBar.setVisibility(View.GONE);

            }
        };
    }

    String getBusiness_name_0, getBusiness_time_0;
    String getBusiness_name_1, getBusiness_time_1;
    String getBusiness_name_2, getBusiness_time_2;
    TextView business_name_0, business_time_0;
    TextView business_name_1, business_time_1;
    TextView business_name_2, business_time_2;
    Button tagBtn;
    Button reserveBtn;
    Button searchBtn;
    ProgressBar mProgressBar;

    /**
     * Updates the three next wait times (sorts the full collection of businesses)
     */
    public void updateWaitTimes() {

        business_time_0.setText(mUserQueueTreeMap.firstKey().toString() + " Minutes Left.");
        business_name_0.setText(mUserQueueTreeMap.pollFirstEntry().getValue());

        business_time_1.setText(mUserQueueTreeMap.firstKey().toString() + " Minutes Left");
        business_name_1.setText(mUserQueueTreeMap.pollFirstEntry().getValue());

        business_time_2.setText(mUserQueueTreeMap.firstKey().toString() + " Minutes Left");
        business_name_2.setText(mUserQueueTreeMap.pollFirstEntry().getValue());

    }

    /**
     * Inherited from QueueListUpdateListener to be notified when the Business list changes
     *
     * @param queues A list of queue objects
     */
    @Override
    public void onQueueListLoaded(List<Queue> queues) {
        this.queueList = (ArrayList) queues;
        runOnUiThread(reloadQueueList);
    }

    public Date getAsTime(Date date) {
        String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
        SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
        // set up rules for daylight savings time
        pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        //get current date time with Date()
        Calendar calendar = new GregorianCalendar(pdt);

        calendar.setTime(date);
        return calendar.getTime();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_q);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        qUpApplication application = (qUpApplication) getApplication();
        application.registerQueueUpdateListener(this);


        business_name_0 = (TextView) findViewById(R.id.business_name_0);
        business_name_1 = (TextView) findViewById(R.id.business_name_1);
        business_name_2 = (TextView) findViewById(R.id.business_name_2);

        business_time_0 = (TextView) findViewById(R.id.business_time_0);
        business_time_1 = (TextView) findViewById(R.id.business_time_1);
        business_time_2 = (TextView) findViewById(R.id.business_time_2);

        mUserQueueList = new ArrayList<Queue>();
        mUserQueueTreeMap = new TreeMap<Integer, String>();

        Gson gson = new Gson();
        SharedPreferences jsonUser = getSharedPreferences(MainActivity.PREFS_NAME, 0);

        mUser = gson.fromJson(jsonUser.getString("user", ""), User.class);



        tagBtn = (Button) findViewById(R.id.button_tagin);
        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQActivity.this, TagInHandler.class);
                startActivity(intent);
            }
        });

        searchBtn = (Button) findViewById(R.id.search_map_activity_button);
        tagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQActivity.this, ShortestWaitingMap.class);
                startActivity(intent);
            }
        });

        reserveBtn = (Button) findViewById(R.id.app_reservation);
        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyQActivity.this, ReservationSearch.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for any updated Queue Times
        qUpApplication application = (qUpApplication) getApplication();
        application.checkForUpdates();
    }

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

