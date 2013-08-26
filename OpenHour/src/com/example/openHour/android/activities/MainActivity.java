package com.example.openHour.android.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.example.openHour.android.R;
import com.example.openHour.android.components.FsqVenue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends BaseActivity {

    private EditText mYelpSearchLocation;
    private EditText mFsqSearchLocation;
    private EditText mGooSearchLocation;
    private static final String category = "restaurant";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        mYelpSearchLocation = (EditText)findViewById(R.id.et_yelpSearchLocation);
        mFsqSearchLocation = (EditText)findViewById(R.id.et_fsqSearchLocation);
        mGooSearchLocation = (EditText)findViewById(R.id.et_gooSearchLocation);
    }

    public void yelpSearch(View v) {
        String location = mYelpSearchLocation.getText().toString();
        Intent intent = new Intent(this, YelpSearchListActivity.class);
        intent.setData(new Uri.Builder().appendQueryParameter("term", category).appendQueryParameter("location", location).build());
        startActivity(intent);
    }

    public void fsqSearch(View v) {
        String location = mFsqSearchLocation.getText().toString();
        Intent intent = new Intent(this, FsqSearchListActivity.class);
        intent.setData(new Uri.Builder().appendQueryParameter("term", category).appendQueryParameter("location", location).build());
        startActivity(intent);
    }

    public void gooSearch(View v) {
        String location = mGooSearchLocation.getText().toString();
        Intent intent = new Intent(this, GooSearchListActivity.class);
        intent.setData(new Uri.Builder().appendQueryParameter("term", category).appendQueryParameter("location", location).build());
        startActivity(intent);
    }
}
