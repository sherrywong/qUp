package edu.berkeley.cs160.qUp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.berkeley.cs160.qUp.R;

/**
 * Purpose of Class:
 * <p/>
 * qUp ==> edu.berkeley.cs160.qUp.activities
 * Date: 12/13/13
 * Time: 1:50 PM
 * Version: 1.0
 */
public class RegisterActivity extends Activity {
    Button regCommitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        Button regCommitButton = (Button) findViewById(R.id.register_commit_button);
        regCommitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
