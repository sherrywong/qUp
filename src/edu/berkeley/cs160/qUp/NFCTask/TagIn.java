package edu.berkeley.cs160.qUp.NFCTask;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.List;

import edu.berkeley.cs160.qUp.R;

/**
 * Created by sidneyfeygin on 11/25/13.
 */


public class TagIn extends Activity {
    //fields//
    private NfcAdapter nfcAdapter;
    private Handler handler = new Handler();
    private String lastNfcId = null;

    private boolean nfcEnabled = false;
    private ProgressBar mProgressBar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handler);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        nfc = NfcAdapter.getDefaultAdapter(this);

        String business = getIntent().getStringExtra("businessName");
        if (business == null) business = "Unknown Business";
        this.setTitle(business);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        findNFC(intent);
    }

    /**
     * Trys to find NFC from the given intent. Calls nfcDidConnect if a tag is found.
     *
     * @param intent
     */
    private void findNFC(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            nfcDidConnect(tag);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (lastNfcId == null)
            setNfcEnabled(true);
    }


    /**
     * Callback method to be called when the NFC successfully connected.
     *
     * @param tag
     */
    private void nfcDidConnect(Tag tag) {
        String id = getNfcId(tag);
        TextView tv = (TextView) this.findViewById(R.id.text);
        tv.setText(id);

        if (lastNfcId == null) {
            // proceed to fetching the print jobs if no current processing job
            ("Fetching print jobs...", R.drawable.big_progress);

            lastNfcId = id;
            setNfcEnabled(false);
        }
    }

    /**
     * Called when the fetch request is completed (it is asynchronous). Gets the result, parses it
     * into JSON and then use it to populate the print queue (listview on screen). Then prints the
     * most recent job.
     *
     * @param result
     */
    private void handleFetchResult(String result) {
        try {

            JSONObject fetchResponse = new JSONObject(result);
            if (!fetchResponse.getBoolean("success")) {
                // prompt to register if there is a key supplied
                if (fetchResponse.has("key")) {
                    registerWithKey(fetchResponse.getString("key"));
                    return;
                }
                throw new RuntimeException(fetchResponse.getString("message"));
            }

            List<Job> jobs = Utils.toList(fetchResponse.getJSONArray("jobs"), Job.class);
            adapter.clear();
            adapter.addAll(jobs);

            if (jobs.isEmpty()) {
                throw new RuntimeException("No file to print");
            }

            jobs.get(0).status = Job.Status.IN_PROGRESS;
            new PrintTask().execute(jobs.get(0));

        } catch (Exception e) {
            e.printStackTrace();
            done();
            showListMessage(e.getMessage(), R.drawable.warning);
        }
    }

    private String getNfcId(Tag tag) {

        return "identifier";
    }

    /**
     * Done with this NFC card. Timeout and then wait for another card.
     */
    private void done() {
        handler.postDelayed(reset, 5000);
    }

    /**
     * Enable / Disable foreground dispatch for NFC.
     *
     * @param enable
     */
    private void setNfcEnabled(boolean enable) {
        if (nfcAdapter == null || enable == nfcEnabled) {
            return;
        }
        if (enable) {
            // Respond to all ACTION_TAG_DISCOVERED
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingNfcIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            nfcAdapter.enableForegroundDispatch(this, pendingNfcIntent, null, null);
            nfcEnabled = true;
            Log.d("NFC", "ENABLE");
        } else {
            nfcAdapter.disableForegroundDispatch(this);
            nfcEnabled = false;
            Log.d("NFC", "DISABLE");
        }
    }

    /**
     * Reset all states and wait for another card.
     */
    private Runnable reset = new Runnable() {
        @Override
        public void run() {
            TextView nfcid = (TextView) findViewById(R.id.text);
            nfcid.setText("");
            showListMessage("Tap your NFC tag on the back", R.drawable.nfc);
            lastNfcId = null;
            try {
                setNfcEnabled(true);
            } catch (IllegalStateException e) {
                // Illegal state exception will be thrown when the we try to set foreground dispatch
                // while we are not foreground.
                e.printStackTrace();
            }
        }
    };

}