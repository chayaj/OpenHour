package com.example.openHour.android.activities;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: chayaj
 * Date: 8/13/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */

public class OpenHourApplication extends Application {

    /**
     * Current user location
     */
    private Location currentLocationUser;

    /**
     * Location manager
     */
    private LocationManager locationManager;

    /**
     * Location listener
     */
    private OnLocationListener onLocationListener;

    /**
     * Static context
     */
    static private Context context;

    /**
     * Return static context
     * @return
     */
    public static Context getAppContext() {
        return context;
    }

    /**
     * Set Listener to change location
     *
     * @param onLocationListener listener
     */
    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }

    /**
     * interface to implement
     */
    public interface OnLocationListener {
        public void onLocationChanged(Location location);
    }

    /**
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //APIService.get().init(this, this);
        if (locationManager == null) {
            // TODO When phone not have GPS and card, launch force close
            try {
                locationManager = (LocationManager) this.getSystemService(getApplicationContext().LOCATION_SERVICE);
                Criteria locationCriteria = new Criteria();
                locationManager.requestLocationUpdates(locationManager.getBestProvider(locationCriteria, true), 0, 20, locationListener);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    /**
     * Return user location
     *
     * @return Location
     */
    public Location getUserLocation() {

        Location lastBestLocation = getLastBestLocation(MAX_DISTANCE, System.currentTimeMillis()-MAX_TIME);

        if (currentLocationUser == null || isBetterLocation(lastBestLocation, currentLocationUser)) {
            currentLocationUser = lastBestLocation;
        }

        return currentLocationUser;
    }

    protected static String SINGLE_LOCATION_UPDATE_ACTION = "com.qthru.android.SINGLE_LOCATION_UPDATE_ACTION";
    protected PendingIntent singleUpatePI;


    // The default search radius when searching for places nearby.
    public static int DEFAULT_RADIUS = 150;
    // The maximum distance the user should travel between location updates.
    public static int MAX_DISTANCE = DEFAULT_RADIUS/2;
    // The maximum time that should pass before the user gets a location update.
    public static long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
    /**
     * Returns the most accurate and timely previously detected location.
     * Where the last result is beyond the specified maximum distance or
     * latency a one-off location update is returned via the {@link android.location.LocationListener}
     * specified in {@link }.
     * @param minDistance Minimum distance before we require a location update.
     * @param minTime Minimum time required between location updates.
     * @return The most accurate and / or timely previously detected location.
     */
    public Location getLastBestLocation(int minDistance, long minTime) {
        Location bestResult = null;
        float bestAccuracy = Float.MAX_VALUE;
        long bestTime = Long.MIN_VALUE;

        // Iterate through all the providers on the system, keeping
        // note of the most accurate result within the acceptable time limit.
        // If no result is found within maxTime, return the newest Location.
        List<String> matchingProviders = locationManager.getAllProviders();
        for (String provider: matchingProviders) {
            Location location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                float accuracy = location.getAccuracy();
                long time = location.getTime();

                if ((time > minTime && accuracy < bestAccuracy)) {
                    bestResult = location;
                    bestAccuracy = accuracy;
                    bestTime = time;
                }
                else if (time < minTime && bestAccuracy == Float.MAX_VALUE && time > bestTime) {
                    bestResult = location;
                    bestTime = time;
                }
            }
        }

        // If the best result is beyond the allowed time limit, or the accuracy of the
        // best result is wider than the acceptable maximum distance, request a single update.
        // This check simply implements the same conditions we set when requesting regular
        // location updates every [minTime] and [minDistance].
        /*if (locationListener != null && (bestTime < minTime || bestAccuracy > minDistance)) {
            IntentFilter locIntentFilter = new IntentFilter(SINGLE_LOCATION_UPDATE_ACTION);
            context.registerReceiver(singleUpdateReceiver, locIntentFilter);

            locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            // Coarse accuracy is specified here to get the fastest possible result.
            // The calling Activity will likely (or have already) request ongoing
            // updates using the Fine location provider.
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_LOW);

            // Construct the Pending Intent that will be broadcast by the oneshot
            // location update.
            Intent updateIntent = new Intent(SINGLE_LOCATION_UPDATE_ACTION);
            singleUpatePI = PendingIntent.getBroadcast(context, 0, updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            locationManager.requestSingleUpdate(criteria, singleUpatePI);
        } */

        return bestResult;
    }

    /**
     * This {@link android.content.BroadcastReceiver} listens for a single location
     * update before unregistering itself.
     * The oneshot location update is returned via the {@link android.location.LocationListener}
     * specified in {@link }.
     */
    protected BroadcastReceiver singleUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.unregisterReceiver(singleUpdateReceiver);

            String key = LocationManager.KEY_LOCATION_CHANGED;
            Location location = (Location)intent.getExtras().get(key);

            if (locationListener != null && location != null)
                locationListener.onLocationChanged(location);

            locationManager.removeUpdates(singleUpatePI);
        }
    };

    /**
     * @see android.location.LocationListener
     */
    LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            if(isBetterLocation(location, currentLocationUser)){
                currentLocationUser = location;
                disableLocationUpdates();
            }
            if (onLocationListener != null) {
                onLocationListener.onLocationChanged(location);
            }
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    public void disableLocationUpdates() {
        if (this.locationManager != null) {
            this.locationManager.removeUpdates(locationListener);
        }
    }

    public void enableLocationUpdates() {
        if (this.locationManager != null) {
            Criteria locationCriteria = new Criteria();
            locationManager.requestLocationUpdates(locationManager.getBestProvider(locationCriteria, true), 0, 20, locationListener);
        }
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /** Determines whether one Location reading is better than the current Location fix
     * @param location  The new Location that you want to evaluate
     * @param currentBestLocation  The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
