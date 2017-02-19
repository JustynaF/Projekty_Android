package justynafirkowska.pocketknife;

import android.*;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.R.attr.country;

public class MapActivity extends AppCompatActivity implements LocationListener{

    LocationManager locationManager;
    Bundle savedInstanceState;
    Location mCurrentLocation;
    PolylineOptions options;

    private ArrayList<LatLng> points;
    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        points = new ArrayList<LatLng>();

        Button backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        locationManager = (LocationManager)
                getSystemService(MapActivity.this.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(MapActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.


            }

        }
        else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10, this);

            final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            final MapView fullMapView = (MapView) findViewById(R.id.fullMapView);
            fullMapView.onCreate(savedInstanceState);
            fullMapView.getMapAsync(new OnMapReadyCallback() {

                @Override
                public void onMapReady(GoogleMap googleMap) {
                    LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                    points.add(coordinates);
                    googleMap.addMarker(new MarkerOptions().position(coordinates).title("Current Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                    googleMap.getUiSettings().setCompassEnabled(true);

                    fullMapView.onResume();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission. ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                //Request location updates:
                locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER, 5000, 10, this);

                final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                final MapView fullMapView = (MapView) findViewById(R.id.fullMapView);
                fullMapView.onCreate(savedInstanceState);
                fullMapView.getMapAsync(new OnMapReadyCallback() {

                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(coordinates).title("Current Location"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                        fullMapView.onResume();
                    }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (checkLocationPermission()) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission. ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER, 5000, 10, this);

                        mCurrentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        final MapView fullMapView = (MapView) findViewById(R.id.fullMapView);
                        fullMapView.onCreate(savedInstanceState);
                        fullMapView.getMapAsync(new OnMapReadyCallback() {

                            @Override
                            public void onMapReady(GoogleMap googleMap) {
                                LatLng coordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                                googleMap.addMarker(new MarkerOptions().position(coordinates).title("Current Location"));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                                fullMapView.onResume();
                            }
                        });
                    }
                } else {

                }
                return;
            }

        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(MapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Permission required")
                        .setMessage("ACCESS_FINE_LOCATION Permission required.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                            }
                        })
                        .create()
                        .show();


            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MapActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

            }
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void redrawLine(final Location l){

        final MapView fullMapView = (MapView) findViewById(R.id.fullMapView);
        //fullMapView.onCreate(savedInstanceState);
        fullMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.clear();

                options = new PolylineOptions().width(10).color(Color.BLUE).geodesic(true);

                for (int i = 0; i < points.size(); i++) {
                    LatLng point = points.get(i);
                    options.add(point);
                }

                for(int i = 0; i < points.size(); i++)
                    Log.i("Marker " + Integer.toString(i), points.get(i).toString());

                line = googleMap.addPolyline(options);
                //addMarker(l);
                updateUI();
            }
        });
    }

    private void addMarker(final Location location) {
        final MapView fullMapView = (MapView) findViewById(R.id.fullMapView);
        fullMapView.onCreate(savedInstanceState);
        fullMapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                MarkerOptions options = new MarkerOptions();

                LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                options.position(current);
                Marker mapMarker = googleMap.addMarker(options);

                Log.d("Marker", "added");
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current,
                        13));
                Log.d("Zoom", "ok");
            }
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;

        String msg = "New Latitude: " + location.getLatitude()
                + "New Longitude: " + location.getLongitude();

        Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        points.add(latLng);

        redrawLine(location);

        //updateUI();
    }

    @Override
    public void onProviderEnabled (String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void updateUI() {
        final MapView mapView = (MapView) findViewById(R.id.fullMapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng coordinates = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                googleMap.addMarker(new MarkerOptions().position(coordinates).title("Current Location"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
                googleMap.addPolyline(options);
                mapView.onResume();
            }
        });
    }
}
