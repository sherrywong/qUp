package edu.berkeley.cs160.qUp.netio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import edu.berkeley.cs160.qUp.Model.Business;
import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.Model.User;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/7/13.
 */
public class QueueORM {



    private static final String TAG = "QueueORM";
    private static final String TABLE_NAME = "queue";
    private static final String COMMA_SEP = ", ";
    private static final String COLUMN_ID_TYPE = "INTEGER PRIMARY KEY";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BUSINESS_TYPE = "TEXT";
    private static final String COLUMN_BUSINESS = "business";
    private static final String COLUMN_PREVIEW_TYPE = "TEXT";
    private static final String COLUMN_PREVIEW = "preview";
    private static final String COLUMN_USER_TYPE = "TEXT";
    private static final String COLUMN_USER = "user";
    private static final String COLUMN_TIME_ENTERED_IN_QUEUE_TYPE = "TEXT";
    private static final String COLUMN_TIME_ENTERED_QUEUE = "time_entered_in_queue";
    private static final SimpleDateFormat _dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.ENGLISH);

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " " + COLUMN_ID_TYPE + COMMA_SEP +
                    COLUMN_BUSINESS + " " + COLUMN_BUSINESS_TYPE + COMMA_SEP +
                    COLUMN_PREVIEW + " " + COLUMN_PREVIEW_TYPE + COMMA_SEP +
                    COLUMN_USER + " " + COLUMN_USER_TYPE + COMMA_SEP +
                    COLUMN_TIME_ENTERED_QUEUE + " " + COLUMN_TIME_ENTERED_IN_QUEUE_TYPE +
                    ")";
    /**
     * Fetches the full list of Queues stored in the local Database
     *
     * @param context
     * @return
     */
    public static ArrayList<Queue> getQueues(Context context) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        ArrayList<Queue> queueList = null;

        if (database != null) {
            Cursor cursor = database.rawQuery("SELECT * FROM " + QueueORM.TABLE_NAME, null);

            Log.i(TAG, "Loaded " + cursor.getCount() + " Queues...");
            if (cursor.getCount() > 0) {
                queueList = new ArrayList<Queue>();
                cursor.moveToFirst();

                Queue queue = cursorToQueue(cursor);
                queue.setUser(UserORM.getUserForQueue(context, queue));

                queueList.add(queue);
                cursor.moveToNext();

                Log.i(TAG, "Queues loaded successfully.");
            }

            database.close();
        }

        return queueList;
    }

    /**
     * Fetches a single Queue identified by the specified ID
     *
     * @param context
     * @param queueId
     * @return
     */
    public static Queue findQueueById(Context context, long queueId) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();

        Queue queue = null;
        if (database != null) {
            Log.i(TAG, "Loading Queue[" + queueId + "]...");
            Cursor cursor = database.rawQuery("SELECT * FROM " + QueueORM.TABLE_NAME + " WHERE " + QueueORM.COLUMN_ID + " = " + queueId, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                queue = cursorToQueue(cursor);
                queue.setUser(UserORM.getUserForQueue(context, queue));
                Log.i(TAG, "Queue" +
                        " loaded successfully!");
            }

            database.close();
        }

        return queue;
    }

    /**
     * Inserts a Queue object into the local database
     *
     * @param context
     * @param queue
     * @return
     */
    public static boolean insertQueue(Context context, Queue queue) {
        if (findQueueById(context, queue.getId()) != null) {
            Log.i(TAG, "Queue already exists in database, not inserting!");
            return updateQueue(context, queue);
        }

        ContentValues values = queueContentValues(queue);

        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        boolean success = false;
        try {
            if (database != null) {
                long postQueue = database.insert(QueueORM.TABLE_NAME, "null", values);
                Log.i(TAG, "Inserted new Queue with ID: " + postQueue);
                User user = queue.getUser();
                UserORM.insertUser(context, user, queue.getId());
                success = true;
            }
        } catch (NullPointerException ex) {
            Log.e(TAG, "Failed to insert Queue[" + queue.getId() + "] due to: " + ex);
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return success;
    }

    public static boolean updateQueue(Context context, Queue queue) {
        ContentValues values = queueContentValues(queue);
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        boolean success = false;
        try {
            if (database != null) {
                Log.i(TAG, "Updating Queue[" + queue.getId() + "]...");
                database.update(QueueORM.TABLE_NAME, values, QueueORM.COLUMN_ID + " = " + queue.getId(), null);
                success = true;
            }
        } catch (NullPointerException ex) {
            Log.e(TAG, "Failed to update QUeue[" + queue.getId() + "] due to: " + ex);
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return success;
    }

    /**
     * Packs a Queue object into a ContentValues map for use with SQL inserts.
     *
     * @param queue
     * @return
     */
    private static ContentValues queueContentValues(Queue queue) {
        ContentValues values = new ContentValues();
        values.put(QueueORM.COLUMN_ID, queue.getId());
        values.put(QueueORM.COLUMN_BUSINESS, String.valueOf(queue.getBusiness()));
        values.put(QueueORM.COLUMN_USER, queue.getUserString());
        values.put(QueueORM.COLUMN_TIME_ENTERED_QUEUE, _dateFormat.format(queue.getTimeEnteredInQueue()));

        return values;
    }

    /**
     * Populates a Queue object with data from a Cursor
     *
     * @param cursor
     * @return
     */
    private static Queue cursorToQueue(Cursor cursor) {
        Queue queue = new Queue();
        queue.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
        String businessString = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS));
        String userString = cursor.getString(cursor.getColumnIndex(COLUMN_USER));

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson =  gsonBuilder.create();

        Business business = gson.fromJson(businessString, Business.class);
        User user = gson.fromJson(userString, User.class);

        queue.setBusiness(business);
        queue.setUser(user);

        String date = cursor.getString(cursor.getColumnIndex(COLUMN_TIME_ENTERED_QUEUE));
        try {
            queue.setTimeEnteredInQueue(_dateFormat.parse(date));
        } catch (ParseException ex) {
            Log.e(TAG, "Failed to parse date " + date + " for Queue " + queue.getId());
            queue.setTimeEnteredInQueue(null);
        }
        return queue;
    }
}

