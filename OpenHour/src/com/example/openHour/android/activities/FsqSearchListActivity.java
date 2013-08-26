package com.example.openHour.android.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.openHour.android.R;
import com.example.openHour.android.adapters.FsqCustomListViewAdapter;
import com.example.openHour.android.components.FsqVenue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class FsqSearchListActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setTitle("Finding Restaurants...");

        Intent intent = getIntent();
        final String searchTerm = intent.getData().getQueryParameter("term");
        final String searchLocation = intent.getData().getQueryParameter("location");

        setProgressBarIndeterminateVisibility(true);
        new AsyncTask<Void, Void, List<FsqVenue>>() {
            @Override
            protected List<FsqVenue> doInBackground(Void... params) {
                String venues = Foursquare.getFsq(FsqSearchListActivity.this).search(searchTerm, searchLocation);
                //String venues = search(searchTerm, searchLocation);
                try {
                    return processJson(venues);
                } catch (JSONException e) {
                    return Collections.<FsqVenue>emptyList();
                }
            }

            @Override
            protected void onPostExecute(List<FsqVenue> venues) {
                setTitle(venues.size() + " restaurants found");
                setProgressBarIndeterminateVisibility(false);
                FsqCustomListViewAdapter adapter = new FsqCustomListViewAdapter(FsqSearchListActivity.this, R.layout.list_item, venues);
                getListView().setAdapter(adapter);
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        //FsqVenue venue = (FsqVenue)listView.getItemAtPosition(position);
        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(venue.getUrl())));
    };

    private List<FsqVenue> processJson(String response) throws JSONException {
        JSONObject jsonObj 	= (JSONObject) new JSONTokener(response).nextValue();
        JSONArray venues = jsonObj.getJSONObject("response").getJSONArray("venues");
        ArrayList<FsqVenue> venueList = new ArrayList<FsqVenue>(venues.length());
        for (int i = 0; i < venues.length(); i++) {
            JSONObject item = venues.getJSONObject(i);

            //get location
            JSONObject location = (JSONObject) item.getJSONObject("location");
            Location loc = new Location(LocationManager.GPS_PROVIDER);
            loc.setLatitude(Double.valueOf(location.getString("lat")));
            loc.setLongitude(Double.valueOf(location.getString("lng")));
            //venue.location	= loc;
            //venue.distance	= location.getInt("distance");

            venueList.add(new FsqVenue(item.getString("id"), item.getString("name"),
                    location.getString("address") + ", " + location.getString("city"),
                    item.getString("canonicalUrl"),
                    item.getJSONObject("contact").getString("phone"),
                    0));
        }
        return venueList;
    }
}
