package com.example.openHour.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.openHour.android.R;

/**
 * Created with IntelliJ IDEA.
 * User: JessicaC
 * Date: 8/14/13
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterActivity extends Activity {
    /**
     * Click on cancel button
     */
    private final View.OnClickListener clickCancel = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    /**
     * Click on save button
     */
    private final View.OnClickListener clickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveCard();
        }
    };

    /**
     * Save the card to QThru
     */
    private void saveCard() {
        Toast.makeText(this, "save!", Toast.LENGTH_LONG).show();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        Button bCancel = (Button) findViewById(R.id.filter_cancel);
        bCancel.setOnClickListener(clickCancel);

        Button bSave = (Button) findViewById(R.id.filter_save);
        bSave.setOnClickListener(clickSave);
    }
}