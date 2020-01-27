package pl.edu.agh.eis.wsswaml.localization;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import pl.edu.agh.eis.wsswaml.ErrorActivity;
import pl.edu.agh.eis.wsswaml.views.cuisines.CuisineDescriptionActivity;
import pl.edu.agh.eis.wsswaml.views.restaurants.RestaurantsFindSettingsActivity;

public class LocalizerService extends Service implements LocationListener {
    private static final String TAG = "LocalizerService";
    private static final int ONE_MINUTE = 1000 * 60;
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    private static final int RECORD_FREQ = ONE_MINUTE / 60;

    private LocationManager mLocationManager;
    private Location currentLocation;

    private final IBinder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        public LocalizerService getService() {
            return LocalizerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, TAG + " Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Started");

        Location gpsLocation = requestUpdatesFromProvider(LocationManager.GPS_PROVIDER);
        Location networkLocation = requestUpdatesFromProvider(LocationManager.NETWORK_PROVIDER);

        if(gpsLocation == null && networkLocation == null) {
            onProvidersNotExists();
        }

        if (isNewLocationBetter(gpsLocation, networkLocation))
            currentLocation = gpsLocation;
        else
            currentLocation = networkLocation;

        return binder;
    }

    @Override
    synchronized public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, TAG + " Created", Toast.LENGTH_LONG).show();
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        registerReceiver(this.batteryInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        currentLocation = null;
    }

    public void onLocationChanged(Location location) {
        Log.d(TAG, "new location: " + location.toString());
        if (isNewLocationBetter(location, currentLocation))
            currentLocation = location;
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    private boolean isNewLocationBetter(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }
        if (location == null) {
            // An old location is always better than nothing
            return false;
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
        } else return isNewer && !isSignificantlyLessAccurate && isFromSameProvider;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    private Location requestUpdatesFromProvider(final String provider) {
        Location location;
        if (mLocationManager.isProviderEnabled(provider)) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.e(TAG, "No permissions");
                return null;
            }
            Log.d(TAG, "requestLocationUpdates from " + provider);
            mLocationManager.requestLocationUpdates(provider, LocalizerService.RECORD_FREQ, 0, this);
            location = mLocationManager.getLastKnownLocation(provider);
        } else {
            Log.e(TAG, "No provider: " + provider);
            return null;
        }
        return location;
    }

    private void onProvidersNotExists() {
        Toast.makeText(LocalizerService.this, "No localization providers", Toast.LENGTH_SHORT).show();
        Intent errorIntent = new Intent(this, ErrorActivity.class);
        this.startActivity(errorIntent);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, TAG + "Stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "Stopped");
        unregisterReceiver(batteryInfoReceiver);
        mLocationManager.removeUpdates(this);
    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        synchronized public void onReceive(Context context, Intent intent) {
            int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            if(batteryLevel < 3) {
                Toast.makeText(context, "Battery level extra low", Toast.LENGTH_LONG).show();
                stopSelf();
            } else if(batteryLevel < 10)
                Toast.makeText(context,"Battery level low",Toast.LENGTH_LONG).show();
        }
    };

    public Location getLocation() {
        return currentLocation;
    }
}
