package com.example.openHour.android.activities;

import android.content.Context;
import com.example.openHour.android.R;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.builder.api.Foursquare2Api;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Foursquare {

    OAuthService service;

    private static final String API_URL = "https://api.foursquare.com/v2/venues/search";

    public static Foursquare getFsq(Context context) {
        return new Foursquare(context.getString(R.string.client_id), context.getString(R.string.client_secret));
    }

    /**
     * Setup the Yelp API OAuth credentials.
     *
     * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
     *
     * @param clientId Client Id
     * @param clientSecret Client secret
     */
    public Foursquare(String clientId, String clientSecret) {
        this.service = new ServiceBuilder().provider(Foursquare2Api.class).apiKey(clientId).apiSecret(clientSecret).build();
    }

    /**
     * Search with term and location.
     *
     * @param term Search term
     * @param latitude Latitude
     * @param longitude Longitude
     * @return JSON string response
     */
    public String search(String term, double latitude, double longitude) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.foursquare.com/v2/venues/search");
        request.addQuerystringParameter("ll", latitude + "," + longitude);
        request.addQuerystringParameter("query", term);
        request.addQuerystringParameter("limit", "5");
        request.addQuerystringParameter("client_id", "MRNNX23LVCRUMBFXJ504TVEVBIQWHJX1HCE0NWXK5ZJAURC5");
        request.addQuerystringParameter("client_secret", "T1ZLX5LTIDN2J0VDSS30Q0ZH5LWAQTZ124ID2YELKO0GLUJZ");
        request.addQuerystringParameter("v", timeMilisToString(System.currentTimeMillis()));
        Response response = request.send();
        return response.getBody();
    }

    /**
     * Search with term string location.
     *
     * @param term Search term
     * @param location Latitude and longitude
     * @return JSON string response
     */
    public String search(String term, String location) {
        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.foursquare.com/v2/venues/search");
        request.addQuerystringParameter("near", location);
        request.addQuerystringParameter("query", term);
        request.addQuerystringParameter("limit", "5");
        request.addQuerystringParameter("client_id", "MRNNX23LVCRUMBFXJ504TVEVBIQWHJX1HCE0NWXK5ZJAURC5");
        request.addQuerystringParameter("client_secret", "T1ZLX5LTIDN2J0VDSS30Q0ZH5LWAQTZ124ID2YELKO0GLUJZ");
        request.addQuerystringParameter("v", timeMilisToString(System.currentTimeMillis()));
        Response response = request.send();
        return response.getBody();
    }

    private String timeMilisToString(long milis) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar   = Calendar.getInstance();
        calendar.setTimeInMillis(milis);
        return sd.format(calendar.getTime());
    }
}
