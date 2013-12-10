package edu.berkeley.cs160.qUp;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.netio.QueueListResponse;
import edu.berkeley.cs160.qUp.netio.QueueListUpdateListener;
import edu.berkeley.cs160.qUp.netio.RESTController;
import edu.berkeley.cs160.qUp.netio.database.QueueORM;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 11/25/13.
 */
public class qUpApplication extends Application {

    public static Context s_applicationContext = null;

    private static String TAG = "QUpApplication";

    private QueueListUpdateListener mQueueListUpdateListener;

    private Date mLastLoadedQueueList;
    private static final int LOAD_QUEUE_LIST_INTERVAL = 1000 * 60 * 10; //10 minutes



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG, "configuration changed!");

    }

    /**
     * User to make Context globally aware.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        s_applicationContext = getApplicationContext();
        Log.d(TAG, "In onCreate");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.d(TAG, "In on Terminate");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG, "In onLowMemory");

    }

    public void registerQueueUpdateListener(QueueListUpdateListener queueListUpdateListener) {
        this.mQueueListUpdateListener = queueListUpdateListener;
    }

    public void checkForUpdates() {
        if(mLastLoadedQueueList == null || new Date().getTime() - mLastLoadedQueueList.getTime() > LOAD_QUEUE_LIST_INTERVAL) {
            this.loadQueueList();
        } else {
            ArrayList<Queue> queues = QueueORM.getQueues(this);

            if(queues != null) {
                Collections.sort(queues);
                this.mQueueListUpdateListener.onQueueListLoaded(queues);
            }
        }

    }

    /**
     * Fetches the list of Posts from the remove server
     */
    private void loadQueueList() {
        Log.i(TAG, "Loading Post List....");

        RESTController.retrieveQueueList(queueListResponse, new long[0]);
    }

    /**
     * Returns a Post object identified by the specified id, if available
     * @param postId
     * @return
     */
    public Queue getQueueById(long postId) {
        return QueueORM.findQueueById(this, postId);
    }

    /**
     * Handler for the Post list being fetched remotely
     */
    private QueueListResponse queueListResponse = new QueueListResponse() {

        @Override
        public void success(String json) {
            super.success(json);
            Collections.sort(queueList);

            if(mQueueListUpdateListener != null) {
                mQueueListUpdateListener.onQueueListLoaded(queueList);
            }

            for (Queue queue : queueList) {
                QueueORM.insertQueue(qUpApplication.this, queue);
            }

            mLastLoadedQueueList = new Date();
        }
    };
}
