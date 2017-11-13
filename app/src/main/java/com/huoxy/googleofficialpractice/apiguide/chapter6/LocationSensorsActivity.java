package com.huoxy.googleofficialpractice.apiguide.chapter6;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.huoxy.googleofficialpractice.R;

import java.util.Arrays;
import java.util.List;

public class LocationSensorsActivity extends AppCompatActivity implements SensorEventListener {
    private final static String TAG = "Location_Sensor";

    private SensorManager sensorManager;

    private Sensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_sensors);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //identifySensorAndCapability();

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.i(TAG, "onSensorChanged() ------ accuracy = " + event.accuracy + ", time = " + event.timestamp + ", value = " + Arrays.toString(event.values));
        /**
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308795315582000, value = [64.0, 268.0, 4716.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308795715464000, value = [58.0, 245.0, 4734.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308796115946000, value = [57.0, 240.0, 4826.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308796515872000, value = [59.0, 249.0, 4721.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308796916298000, value = [58.0, 244.0, 4660.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308797316313000, value = [132.0, 552.0, 4709.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308797716541000, value = [161.0, 674.0, 6153.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308798115706000, value = [138.0, 577.0, 5221.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308798515261000, value = [116.0, 485.0, 5675.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308798916527000, value = [172.0, 717.0, 3704.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308799316641000, value = [406.0, 1693.0, 8218.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308799716259000, value = [553.0, 2304.0, 5184.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308800116520000, value = [184.0, 770.0, 6416.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308800516656000, value = [179.0, 746.0, 4093.0]
         I/Location_Sensor: onSensorChanged() ------ accuracy = 3, time = 1510308800915959000, value = [94.0, 394.0, 5650.0]
         */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "onSensorChanged() ------ accuracy = " + accuracy);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void getLocationDemo() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener myListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                location.getLongitude();
                location.getLatitude();
                location.getAltitude();
                location.getAccuracy();
                location.getProvider();
                location.getBearing();
                location.getSpeed();
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

    //------------------------------Sensors------------------------------

    //1 - 获取Sensor
    private void identifySensorAndCapability(){
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor:sensorList){
            Log.i("Sensor", " --- name = " + sensor.getName() + ", type = " + sensor.getType() +
                    ", version = " + sensor.getVersion() + ", power = " + sensor.getPower());

            /**
             Log I/Sensor:  --- name = Accelerometer Sensor, type = 1, version = 1, power = 0.17
             Log I/Sensor:  --- name = Magnetic field Sensor, type = 2, version = 1, power = 5.0
             Log I/Sensor:  --- name = Gyroscope Sensor, type = 4, version = 1, power = 6.1
             Log I/Sensor:  --- name = Light Sensor, type = 5, version = 1, power = 0.15
             Log I/Sensor:  --- name = Proximity Sensor, type = 8, version = 1, power = 0.15
             Log I/Sensor:  --- name = Orientation Sensor, type = 3, version = 1, power = 11.27
             Log I/Sensor:  --- name = Rotation Vector, type = 11, version = 1, power = 11.27
             Log I/Sensor:  --- name = Linear Acceleration, type = 10, version = 1, power = 11.27
             Log I/Sensor:  --- name = Gravity, type = 9, version = 1, power = 11.27
             Log I/Sensor:  --- name = Magnetic Uncalibrated, type = 14, version = 1, power = 5.0
             Log I/Sensor:  --- name = Gyroscope Uncalibrated, type = 16, version = 1, power = 6.1
             Log I/Sensor:  --- name = Game Rotation Vector, type = 15, version = 1, power = 11.27
             Log I/Sensor:  --- name = Geomagnetic Rotation Vector, type = 20, version = 1, power = 11.27
             Log I/Sensor:  --- name = Cywee Step Detector, type = 18, version = 1, power = 0.17
             Log I/Sensor:  --- name = Cywee Step Counter, type = 19, version = 1, power = 0.17
             Log I/Sensor:  --- name = Significant Motion, type = 17, version = 1, power = 0.17
             Log I/Sensor:  --- name = Cywee Shake, type = 33171002, version = 1, power = 0.17
             Log I/Sensor:  --- name = Cywee Tap, type = 33171003, version = 1, power = 0.17
             Log I/Sensor:  --- name = Cywee Reserve Sensor A, type = 33171001, version = 1, power = 6.1
             Log I/Sensor:  --- name = Auto Pick Up, type = 25, version = 1, power = 0.17
             Log I/Sensor:  --- name = Tilt, type = 22, version = 1, power = 6.1
             Log I/Sensor:  --- name = Proximity Sensor (WAKE_UP), type = 8, version = 1, power = 0.15
             Log I/Sensor:  --- name = Accelerometer Sensor (WAKE_UP), type = 1, version = 1, power = 0.17
             Log I/Sensor:  --- name = Magnetic field Sensor (WAKE_UP), type = 2, version = 1, power = 5.0
             Log I/Sensor:  --- name = Gyroscope Sensor (WAKE_UP), type = 4, version = 1, power = 6.1
             Log I/Sensor:  --- name = Orientation Sensor (WAKE_UP), type = 3, version = 1, power = 11.27
             Log I/Sensor:  --- name = Gravity (WAKE_UP), type = 9, version = 1, power = 11.27
             Log I/Sensor:  --- name = Linear Acceleration (WAKE_UP), type = 10, version = 1, power = 11.27
             Log I/Sensor:  --- name = Rotation Vector (WAKE_UP), type = 11, version = 1, power = 11.27
             Log I/Sensor:  --- name = Magnetic Uncalibrated (WAKE_UP), type = 14, version = 1, power = 5.0
             Log I/Sensor:  --- name = Gyroscope Uncalibrated (WAKE_UP), type = 16, version = 1, power = 6.1
             Log I/Sensor:  --- name = Game Rotation Vector (WAKE_UP), type = 15, version = 1, power = 11.27
             Log I/Sensor:  --- name = Geomagnetic Rotation Vector (WAKE_UP), type = 20, version = 1, power = 11.27
             Log I/Sensor:  --- name = Step Detector (WAKE_UP), type = 18, version = 1, power = 0.17
             Log I/Sensor:  --- name = Step Counter (WAKE_UP), type = 19, version = 1, power = 0.17
             Log I/Sensor:  --- name = Cywee Reserve Sensor A, type = 33171001, version = 1, power = 2.0
             */
        }
    }
}
