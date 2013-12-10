package edu.berkeley.cs160.qUp.netio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import edu.berkeley.cs160.qUp.Model.Queue;
import edu.berkeley.cs160.qUp.Model.User;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/7/13.
 */
public class UserORM {
    private static final String TAG = "UserORM";

    private static final String TABLE_NAME = "user";
    private static final String COMMA_SEP = ", ";

    private static final String COLUMN_NAME_TYPE = "TEXT";
    private static final String COLUMN_NAME = "name";

    private static final String COLUMN_QUEUE_ID_TYPE = "INTEGER";
    private static final String COLUMN_QUEUE_ID = "queue_id";


    public static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME + " " + COLUMN_NAME_TYPE + COMMA_SEP +
                    COLUMN_QUEUE_ID + " " + COLUMN_QUEUE_ID_TYPE +
                    ")";

    public static final String SQL_DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    /**
     * Inserts a User object into the database with an association with the specified Queue
     * @param context
     * @param user
     * @param queueId
     * @return
     */
    public static boolean insertUser(Context context, User user, long queueId) {
        ContentValues values = UserORM.userToContentValues(user, queueId);
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getWritableDatabase();

        boolean success = false;
        try {
            if(database != null) {
                database.insert(UserORM.TABLE_NAME, "null", values);
                Log.i(TAG, "Inserted new User [" + user.getUsername() + "] for Queue [" + queueId + "]");
                success = true;
            }
        } catch (NullPointerException ex) {
            Log.e(TAG, "Failed to insert User[" + user.getUsername() + "] due to: " + ex);
        } finally {
            if (database != null) {
                database.close();
            }
        }

        return success;
    }

    /**
     * Gets a list of all cached Tags associated with the specified Queue
     * @param context
     * @param queue
     * @return
     */
    public static User getUserForQueue(Context context, Queue queue) {
        DatabaseWrapper databaseWrapper = new DatabaseWrapper(context);
        SQLiteDatabase database = databaseWrapper.getReadableDatabase();


        assert database != null;
        Cursor cursor = database.rawQuery(
                "SELECT * FROM " + UserORM.TABLE_NAME + " WHERE " + UserORM.COLUMN_QUEUE_ID + " = " + queue.getId() + " GROUP BY " + UserORM.COLUMN_NAME, null
        );

        if(cursor.getCount() > 0) {

            cursor.moveToFirst();
            User user = UserORM.cursorToUser(cursor);
            cursor.moveToNext();
            return user;
            }
        Log.i(TAG, "User loaded successfully for Queue[" + queue.getId() + "]");


        database.close();
        return null;
    }






    /**
     * Packs a User object into a ContentValues map for use with SQL inserts.
     * @param user
     * @param queueId
     * @return
     */
    private static ContentValues userToContentValues(User user, long queueId) {
        ContentValues values = new ContentValues();
        values.put(UserORM.COLUMN_NAME, user.getUsername());
        values.put(UserORM.COLUMN_QUEUE_ID, queueId);

        return values;
    }

    /**
     * Populates a User object with data from a Cursor
     * @param cursor
     * @return
     */
    private static User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setUsername(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));

        return user;
    }


}
