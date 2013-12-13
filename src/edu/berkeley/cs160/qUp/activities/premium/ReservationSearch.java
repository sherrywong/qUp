package edu.berkeley.cs160.qUp.activities.premium;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import edu.berkeley.cs160.qUp.model.Queue;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.MyQActivity;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.animlv.AnimatedListView;
import edu.berkeley.cs160.qUp.animlv.AnimatedListViewAdapter;
import edu.berkeley.cs160.qUp.animlv.AnimatedListViewObjectMapper;
import edu.berkeley.cs160.qUp.netio.QueueListUpdateListener;
import edu.berkeley.cs160.qUp.qUpApplication;

import java.util.*;

public class ReservationSearch extends Activity  implements QueueListUpdateListener {

    private static final String TAG = "QueueListActivity";
    public Intent intent;
    EditText name;
    /**
     * Triggered when the queue list is updated in order to update the UI
     */
    private Runnable reloadQueueList = new Runnable() {
        HashSet<Queue> queueSet = new HashSet<Queue>();
        ArrayList<Queue> queueArrayList = new ArrayList<Queue>();

        @Override
        public void run() {
             int i=0;
            //TODO: Ugly hack... oh well :-/...
            for (Queue queue : queueList) {
                queueSet.add(queue);
            }
            for (Queue queue : queueSet) {

                queueArrayList.add(queue);
                i++;
                if(i>5)
                    break;
            }


            AnimatedListViewAdapter queueListAdapter = new AnimatedListViewAdapter(ReservationSearch.this, R.layout.queue_list_item, queueArrayList, objectMapper);

            queueListView.setAdapter(queueListAdapter);

            mProgressBar.setVisibility(View.GONE);
        }
    };
    /**
     * Called to bind a Post object to a View for the AnimatedListView
     */
    private AnimatedListViewObjectMapper objectMapper = new AnimatedListViewObjectMapper() {
        @Override
        public void bindObjectToView(Object object, View view) {
            Queue queue = (Queue) object;

            TextView queueItemBusiness = (TextView) view.findViewById(R.id.queue_item_business_name);
            queueItemBusiness.setText(queue.getBusiness().getName());

            TextView queueItemSubCat = (TextView) view.findViewById(R.id.queue_item_category);
            queueItemSubCat.setText(queue.getBusiness().getOrganizationType());

            TextView queueItemTimeToWait = (TextView) view.findViewById(R.id.queue_avg_wait_time);
            queueItemTimeToWait.setText(String.valueOf(queue.getBusiness().getAvgWaitTime()*60)+" Minutes.");

        }
    };
    private AnimatedListView queueListView;
    private List<Queue> queueList;
    private ProgressBar mProgressBar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_search_activity_list);

        // Customize the action bar
        ActionBar actionBar = getActionBar();


        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.header, null);

            assert v != null;

            title = (TextView) v.findViewById(R.id.business_name_0);

            actionBar.setCustomView(v);
        }

        // Initializer Views
        queueListView = (AnimatedListView) findViewById(R.id.queue_list);
        queueListView.setOnItemClickListener(queueItemSelectedListener);


        mProgressBar = (ProgressBar) findViewById(R.id.loader);

        qUpApplication application = (qUpApplication) getApplication();
        application.registerQueueUpdateListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for any updated Queue Times
        qUpApplication application = (qUpApplication) getApplication();
        application.checkForUpdates();
    }
    private ListView.OnItemClickListener queueItemSelectedListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Queue selectedQueue = queueList.get(position);
            System.out.println(selectedQueue.business.getAvgWaitTime());
            Intent toRerservationForm = new Intent(ReservationSearch.this, ReservationForm.class);

            toRerservationForm.putExtra("Business", selectedQueue.business.getName());

            startActivity(toRerservationForm);


            overridePendingTransition(R.anim.start_right_to_left, R.anim.start_left_to_right);
        }
    };
    /**
     * Inherited from QueueListUpdateListener to be notified when the Business list changes
     *
     * @param queues A list of queue objects
     */
    @Override
    public void onQueueListLoaded(List<Queue> queues) {
        this.queueList = queues;
        runOnUiThread(reloadQueueList);
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
