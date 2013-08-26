package com.example.openHour.android.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import com.example.openHour.android.R;
import com.example.openHour.android.adapters.YelpCustomListViewAdapter;
import com.example.openHour.android.components.Business;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GooSearchListActivity extends ListActivity {

    /*
    class Business {
        final String name;
        final String mobile_url;
        final String img_url;

        public Business(String name, String mobile_url, String img_url) {
            this.name = name;
            this.mobile_url = mobile_url;
            this.img_url = img_url;
        }

        @Override
        public String toString() {
            return name;
        }
    }
      */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        setTitle("Finding Restaurants...");

        Intent intent = getIntent();
        final String searchTerm = intent.getData().getQueryParameter("term");
        final String searchLocation = intent.getData().getQueryParameter("location");

        setProgressBarIndeterminateVisibility(true);
        new AsyncTask<Void, Void, List<Business>>() {
            @Override
            protected List<Business> doInBackground(Void... params) {
                String businesses = Yelp.getYelp(GooSearchListActivity.this).search(searchTerm, searchLocation);
                try {
                    return processJson(businesses);
                } catch (JSONException e) {
                    return Collections.<Business>emptyList();
                }
            }

            @Override
            protected void onPostExecute(List<Business> businesses) {
                setTitle(businesses.size() + " restaurants found");
                setProgressBarIndeterminateVisibility(false);
                YelpCustomListViewAdapter adapter = new YelpCustomListViewAdapter(GooSearchListActivity.this, R.layout.list_item, businesses);
                getListView().setAdapter(adapter);
            }
        }.execute();
    }

    @Override
    protected void onListItemClick(ListView listView, View view, int position, long id) {
        Business biz = (Business)listView.getItemAtPosition(position);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(biz.getMobileUrl())));
    };

    List<Business> processJson(String response) throws JSONException {
        JSONObject json = new JSONObject(response);
        JSONArray businesses = json.getJSONArray("businesses");
        ArrayList<Business> businessObjs = new ArrayList<Business>(businesses.length());
        for (int i = 0; i < businesses.length(); i++) {
            JSONObject business = businesses.getJSONObject(i);
            businessObjs.add(new Business(business.optString("name"), business.optString("mobile_url"),
                    business.optString("image_url"), business.optString("display_phone")));
        }
        return businessObjs;
    }
}
