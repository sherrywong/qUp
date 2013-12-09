package edu.berkeley.cs160.qUp.netio.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Part of the qUp codebase.
 * Created by sidneyfeygin on 12/7/13.
 */
public class DatabaseWrapper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseWrapper";

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "saf.db";

    public DatabaseWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Called if the database named DATABASE_NAME doesn't exist in order to create it.
     *
     * @param sqLiteDatabase Database in question
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "Creating database [" + DATABASE_NAME + " v." + DATABASE_VERSION + "]...");

        sqLiteDatabase.execSQL(QueueORM.SQL_CREATE_TABLE);
        sqLiteDatabase.execSQL(QueueORM.SQL_CREATE_TABLE);
        sqLiteDatabase.close();
    }

    /**
     * Called when the DATABASE_VERSION is increased.
     *
     * @param sqLiteDatabase Database in question
     * @param oldVersion Number of old version
     * @param newVersion number of new version
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrading database [" + DATABASE_NAME + " v." + oldVersion + "] to [" + DATABASE_NAME + " v." + newVersion + "]...");

        sqLiteDatabase.execSQL(QueueORM.SQL_DROP_TABLE);
        sqLiteDatabase.execSQL(QueueORM.SQL_DROP_TABLE);
        onCreate(sqLiteDatabase);
    }
}

