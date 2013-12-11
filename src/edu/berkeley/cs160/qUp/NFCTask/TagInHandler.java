package edu.berkeley.cs160.qUp.NFCTask;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.berkeley.cs160.qUp.activities.MyQActivity;
import org.ndeftools.Message;
import org.ndeftools.Record;
import org.ndeftools.externaltype.AndroidApplicationRecord;
import org.ndeftools.wellknown.TextRecord;

import java.util.List;

import edu.berkeley.cs160.qUp.R;

public class TagInHandler extends Activity implements NfcAdapter.CreateNdefMessageCallback, OnNdefPushCompleteCallback {
    String business_name_0, business_time_0;
    String business_name_1, business_time_1;
    String business_name_2, business_time_2;

    private static final String TAG = TagInHandler.class.getName();
    private static final int MESSAGE_SENT = 1;
    protected NfcAdapter nfcAdapter;
    protected PendingIntent nfcPendingIntent;


    @Override
    public void onNdefPushComplete(NfcEvent arg0) {
        Log.d(TAG, "onNdefPushComplete");

        // A handler is needed to send messages to the activity when this
        // callback occurs, because it happens from a binder thread
        mHandler.obtainMessage(MESSAGE_SENT).sendToTarget();
    }

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.handler);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        nfcAdapter.setOnNdefPushCompleteCallback(this, this);



    }

    public void enableForegroundMode() {
        Log.d(TAG, "enableForegroundMode");

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED); // filter for all
        IntentFilter[] writeTagFilters = new IntentFilter[]{tagDetected};
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, writeTagFilters, null);
    }

    public void disableForegroundMode() {
        Log.d(TAG, "disableForegroundMode");

        nfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            TextView textView = (TextView) findViewById(R.id.title);

            textView.setText("Hello NFC!");

            Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (messages != null) {

                Log.d(TAG, "Found " + messages.length + " NDEF messages"); // is almost always just one

                vibrate(); // signal found messages :-)

                // parse to records
                for (int i = 0; i < messages.length; i++)
                    try {
                        List<Record> records = new Message((NdefMessage) messages[i]);

                        Log.d(TAG, "Found " + records.size() + " records in message " + i);

                        for (int k = 0; k < records.size(); k++) {
                            Log.d(TAG, " Record #" + k + " is of class " + records.get(k).getClass().getSimpleName());
                            AndroidApplicationRecord record = (AndroidApplicationRecord) records.get(k);

                            if (record != null) {
                                Log.d(TAG, "Package is " + record.getPackageName());
                            }

                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Problem parsing message", e);
                    }
            }
        } else {
            // ignore
        }
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        super.onPause();

        disableForegroundMode();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");

        super.onResume();

        enableForegroundMode();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        Log.d(TAG, "createNdefMessage");

        // create record to be pushed
        TextRecord record = new TextRecord("This is my text record");

        // encode one or more record to NdefMessage
        return new NdefMessage(record.getNdefRecord());
    }


    /**
     * This handler receives a message from onNdefPushComplete
     */
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MESSAGE_SENT:
                    Toast.makeText(getApplicationContext(), "Message beamed!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    private void vibrate() {
        Log.d(TAG, "vibrate");


        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(500);
    }
}



