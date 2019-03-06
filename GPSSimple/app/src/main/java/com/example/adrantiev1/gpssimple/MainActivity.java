package com.example.adrantiev1.gpssimple;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    TextView tvHomeLat;
    TextView tvHomeLong;
    TextView tvCurrentLat;
    TextView tvCurrentLong;
    TextView tvMeters;

    public static double homeLat = 53.56792656239122;
    public static double homeLong = 113.50055138580501;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvHomeLat = (TextView) findViewById(R.id.tv_home_latitude);
        tvHomeLong = (TextView) findViewById(R.id.tv_home_longtitude);
        tvCurrentLat = (TextView) findViewById(R.id.tv_current_latitude);
        tvCurrentLong = (TextView) findViewById(R.id.tv_current_longtitude);
        tvMeters = (TextView) findViewById(R.id.tv_meters);


        tvHomeLat.setText("Home Latitude = " + homeLat);
        tvHomeLong.setText("Home Lomgtitude = " + homeLong);
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
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
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListner());
    }
    private class MyLocationListner implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location)
        {
            double meters = 33;
            double dLong = location.getLongitude() * -1;
            double dLat = location.getLatitude();

            meters = calcDifferenceInMeters(dLat,dLong);
            NumberFormat formater = new DecimalFormat("0.00");
            String strMeters = formater.format(meters);

            tvHomeLat.setText("Home Latitude = " + homeLat);
            tvHomeLong.setText("Home Lomgtitude = " + homeLong);
            tvCurrentLat.setText("Latitude = " + dLat);
            tvCurrentLong.setText("Latitude = " + dLong);
            tvMeters.setText("Meters = " + strMeters);
        }

        private double calcDifferenceInMeters(double dLat, double dLong)
        {
            double distance = 0;
            double earthRadius = 3958.75;
            double dLatitude = (Math.toRadians(dLat- homeLat));
            double dLongtitude = (Math.toRadians(dLong- homeLong));
            double sinLatitude = Math.sin(dLatitude / 2);
            double sinLongtitude = Math.sin(dLongtitude / 2);
            double a = Math.pow(sinLatitude,2) + Math.pow(sinLongtitude, 2)
                    * Math.cos(dLat) * Math.cos(homeLat);

            double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
            double distanceInMiles = earthRadius * c;
            distance = distanceInMiles * 1.609344 * 1000;



            return  distance;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle)
        {
            //ignore
        }

        @Override
        public void onProviderEnabled(String s)
        {
            Toast.makeText(getApplicationContext(),"GPS_ENABLED",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onProviderDisabled(String s)
        {
            Toast.makeText(getApplicationContext(),"GPS_DISABLED",Toast.LENGTH_LONG).show();
        }
    }
}
