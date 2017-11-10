package com.huoxy.googleofficialpractice.apiguide.chapter6;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huoxy.googleofficialpractice.R;

public class LocationSensorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sensors);
    }

    private void getLocationDemo() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener myListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location.getLatitude();
                location.getLongitude();
                location.getAltitude();
                location.getAccuracy();
                location.getProvider();
                location.getSpeed();
                location.getBearing();
                location.getTime();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //同时使用2种Provider - 更精确
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myListener);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myListener);

        final Location lastKnownLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    //比较2个Location的准确定方法
    private boolean isBetterLocation(Location newerLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // 1 - Check whether the new location fix is newer or older
        long timeDelta = newerLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantOlder = timeDelta < TWO_MINUTES;
        boolean isNewer = timeDelta < 0;
        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantOlder) {
            return false;
        }

        // 2 - Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newerLocation.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantLessAccurate = accuracyDelta > 200;

        // 3 - Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(newerLocation.getProvider(), currentBestLocation.getProvider());

        // 4 - Determine location quality using a combination of timeliness and accuracy[！！！]
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantLessAccurate && isFromSameProvider) {
            return true;
        }

        return false;
    }

    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }

        return provider1.equals(provider2);
    }
}
