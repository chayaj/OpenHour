package com.example.openHour.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.example.openHour.android.R;

public class MainActivity extends BaseActivity {

    private EditText mSearchLocation;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mSearchLocation = (EditText)findViewById(R.id.searchLocation);
    }

    public void search(View v) {
        String category = "restaurants";
        String location = mSearchLocation.getText().toString();
        Intent intent = new Intent(this, YelpSearchListActivity.class);
        intent.setData(new Uri.Builder().appendQueryParameter("term", category).appendQueryParameter("location", location).build());
        startActivity(intent);
    }
}
