package edu.berkeley.cs160.qUp;

//~--- non-JDK imports --------------------------------------------------------

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import edu.berkeley.cs160.qUp.Model.Queue;

//~--- JDK imports ------------------------------------------------------------

public class BusinessListActivity extends Activity implements QueueListUpdateListener {
    private static final String TAG = "BusinessListActivity";

    /**
     * Triggered when the queue list is updated in order to update the UI
     */
    private Runnable reloadQueueList = new Runnable() {
        @Override
        public void run() {
            ArrayAdapter postListAdapter = new ArrayAdapter<Queue>(BusinessListActivity.this, R.layout.queue_list_item,
                    queueList);

            queueListView.setAdapter(postListAdapter);
            mProgressBar.setVisibility(View.GONE);
        }
    };

    /**
     * Triggered when a Queue is selected from the ListView
     */
    private ListView.OnItemClickListener queueItemSelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Queue selectedQueue = queueList.get(position);
            Intent toQueueActivity = new Intent(BusinessListActivity.this, QueueActivity.class);

            toQueueActivity.putExtra("queueId", selectedQueue.getId());
            startActivity(toQueueActivity);

//          overridePendingTransition(R.anim.start_right_to_left, R.anim.start_left_to_right);
        }
    };
    public Intent intent;
    private ListView queueListView;
    private ArrayList<Queue> queueList;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "On created");
        setContentView(R.layout.main_activity);

        // Customize the action bar
        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.biz_list, null);

            assert v != null;

            TextView title = (TextView) v.findViewById(R.id.business_name_0);

            title.setTypeface(Typeface.SANS_SERIF);
            actionBar.setCustomView(v);
        }

        // Initializer Views
        queueListView = (ListView) findViewById(R.layout.biz_list);
        queueListView.setOnClickListener((View.OnClickListener) queueItemSelectedListener);
        mProgressBar = (ProgressBar) findViewById(R.id.loader);

        QUpApplication application = (QUpApplication) getApplication();

        application.registerQueueUpdateListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for any updated Posts
        QUpApplication application = (QUpApplication) getApplication();

        application.checkForUpdates();
    }

    /**
     * Inherited from QueueListUpdateListener to be notified when the Post list changes
     *
     * @param queues A list of queue objects
     */
    @Override
    public void onQueueListLoaded(ArrayList<Queue> queues) {
        this.queueList = queues;
        runOnUiThread(reloadQueueList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    public void cleanUpWritingToTag() {

    }
}


//~ Formatted by Jindent --- http://www.jindent.com
