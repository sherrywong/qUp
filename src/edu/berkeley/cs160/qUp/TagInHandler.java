/***
 * Note that this Implementation is based upon the aforementioned Commons*/
package edu.berkeley.cs160.qUp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

public class TagInHandler extends Activity {

    private static final int MESSAGE_SENT = 1;
    public static final String BIZ = "Business";
    public static final String TIMES = "Time Remaining";
    public static String DEST_URL = "Url:";
    private String intentTagID;

    private static final String TAG = TagInHandler.class.getName();
    protected NfcAdapter nfcAdapter;
    protected PendingIntent nfcPendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.handler);

        // initialize NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

//        TODO: Remove the following before release. Only for testing.
        if (getIntent().hasExtra(NfcAdapter.EXTRA_TAG)) {
            TextView textView = (TextView) findViewById(R.id.title);
            textView.setText("Hello NFC tag from home screen!");
        }

        printTagId(getIntent());
    }
    public ParseObject targetBiz = new ParseObject("TargetBiz");
    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        setIntent(intent);

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Log.d(TAG, "A tag was scanned!");
            TextView textView = (TextView) findViewById(R.id.title);


            ParseQuery query = new ParseQuery("OwnerID");
            query.getInBackground(intentTagID, new GetCallback(){


                public void done(ParseObject biz, ParseException e){
                    if (e == null)
                    {
                        Log.d(TAG, "It Works!");
                        targetBiz = biz;
                        String owner = targetBiz.getString("owner");
                        String waitTime = targetBiz.getString("WaitTIme");
                        Date updatedAt = targetBiz.getUpdatedAt();
                        Date createdAt = targetBiz.getCreatedAt();
                        //TODO: Update Waiting Time and Button State.
                    }
                    else{
                        Log.e(TAG, "There's been an error!");
                        //TODO: Error handling.
                    }
                }
            });

            textView.setText("Hello ");

            vibrate(); // signal found messages :-)

            printTagId(getIntent());
        }

    }

    /**
     * Converts the byte array to HEX string.
     *
     * @param buffer the buffer.
     * @return the HEX string.
     */
    public String toHexString(byte[] buffer) {
        StringBuilder sb = new StringBuilder();
        for (byte b : buffer)
            sb.append(String.format("%02x ", b & 0xff));
        return sb.toString().toUpperCase();
    }

    protected void printTagId(Intent intent) {
        if (intent.hasExtra(NfcAdapter.EXTRA_ID)) {
            byte[] byteArrayExtra = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
            intentTagID = toHexString(byteArrayExtra);
            Log.d(TAG, "Tag id is " + intentTagID);

        }
    }

    private void vibrate() {
        Log.d(TAG, "vibrate");

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(500);
    }


}

