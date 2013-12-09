package edu.berkeley.cs160.qUp;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import org.ndeftools.Message;
import org.ndeftools.Record;

public class TagInHandler extends Activity {
    String business_name_0, business_time_0;
    String business_name_1, business_time_1;
    String business_name_2, business_time_2;

    private static final String TAG = TagInHandler.class.getName();


    class TagDescription {

        public String title;

        public NdefMessage[] msgs;

        public TagDescription(String title, Record record) {
            this.title = title;
            Message message = new Message();
            message.add(record);
            try {
                msgs = new NdefMessage[]{message.getNdefMessage()};
            } catch (final Exception e) {
                throw new RuntimeException("Failed to create tag description", e);
            }
        }

        @Override
        public String toString() {
            return title;
        }

    }


    TagDescription mTagDescription0;
    TagDescription mTagDescription1;
    TagDescription mTagDescription2;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        setContentView(R.layout.handler);


        mTagDescription0 = new TagDescription("Tagged in For PurpleKow", MockNdefMessages.PURPLE_KOW);
//        mTagDescription1 = new TagDescription("Tagged in for SLiver", MockNdefMessages.SLIVER);
//        mTagDescription2 = new TagDescription("Tagged in For Cheeseboard", MockNdefMessages.CHEESEBOARD);


        final ImageButton button = (ImageButton) findViewById(R.id.toggle);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MyQActivity.class);


                intent.putExtra(MainActivity.BUSINESS_TIME_0, "14 Minutes");
                intent.putExtra(MainActivity.BUSINESS_TIME_1, "10");
                intent.putExtra(MainActivity.BUSINESS_TIME_2, "00:08");


                intent.putExtra(NfcAdapter.EXTRA_NDEF_MESSAGES, mTagDescription0.msgs);

                startActivity(intent);
            }
        });

    }


}


//    public ParseObject targetBiz = new ParseObject("TargetBiz");
//
//
//
//        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
//            Log.d(TAG, "A tag was scanned!");
//            TextView textView = (TextView) findViewById(R.id.title);
//
//
//            ParseQuery query = new ParseQuery("OwnerID");
//            query.getInBackground(intentTagID, new GetCallback(){
//
//
//                public void done(ParseObject biz, ParseException e){
//                    if (e == null)
//                    {
//                        Log.d(TAG, "It Works!");
//                        targetBiz = biz;
//                        String owner = targetBiz.getString("owner");
//                        String waitTime = targetBiz.getString("WaitTime");
//                        Date updatedAt = targetBiz.getUpdatedAt();
//                        Date createdAt = targetBiz.getCreatedAt();
//                        //TODO: Update Waiting Time and Button State.
//                    }
//                    else{
//                        Log.e(TAG, "There's been an error!");
//                        //TODO: Error handling.
//                    }
//                }
//            });
//    }




