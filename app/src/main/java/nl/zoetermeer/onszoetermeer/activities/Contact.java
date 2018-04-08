package nl.zoetermeer.onszoetermeer.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.List;

import nl.zoetermeer.onszoetermeer.R;
import nl.zoetermeer.onszoetermeer.data.DummyDatabase;
import nl.zoetermeer.onszoetermeer.data.UserDAO;
import nl.zoetermeer.onszoetermeer.helpers.MapHelper;
import nl.zoetermeer.onszoetermeer.helpers.PermissionUtils;
import nl.zoetermeer.onszoetermeer.models.User;

public class Contact extends Base
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener
{
    private MapHelper mapHelper;
    private GoogleMap mMap;

    // Request code for location permission request.
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    // Flag indicating whether a requested permission has been denied after returning in
    private boolean mPermissionDenied = false;

    private String provider;
    private LocationManager locationManager;
    private Location location;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60;

    private List<User> usersList;

    public Contact()
    {
        mapHelper = new MapHelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        new selectUserAsync().execute();

        mapHelper.getUserPreferences();
    }

    @Override
    public boolean useToolbar()
    {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        enableMyLocation();

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        mapHelper.setUserMarker(location, mMap);

        final Marker socialWorkerMarker = mapHelper.setSocialWorkerMarker(mMap);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {
            @Override
            public boolean onMarkerClick(Marker m)
            {
                if (m.getId().equals(socialWorkerMarker.getId()))
                {
                    Bundle bundle = new Bundle();
                    Log.i("BUTTON:", "Contact > Profile.");
                    Intent messageContact = new Intent(
                            Contact.this, Profile.class);
                    bundle.putInt("user_id", usersList.get(0).getId());
                    messageContact.putExtras(bundle);
                    startActivity(messageContact);
                }
                else
                {
                    mapHelper.setLocationData(location);
                }

                return true;
            }
        });

        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(18.0f);
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        else if (mMap != null)
        {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            provider = locationManager.getBestProvider(criteria, false);

            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(false);
            location = locationManager.getLastKnownLocation(provider);

            if (location == null)
            {
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d("GPS", "Network Enabled");
                if (locationManager != null)
                {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location)
    {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResumeFragments()
    {
        super.onResumeFragments();
        if (mPermissionDenied)
        {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError()
    {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onLocationChanged(Location location)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {

    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    private class selectUserAsync extends AsyncTask<Void,Integer,List<User>>
    {
        private UserDAO userDAO;
        private DummyDatabase dummyDB;

        selectUserAsync() {
            dummyDB = DummyDatabase.getDatabase(getApplication());
            userDAO = dummyDB.userDAO();
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            usersList = new ArrayList<>();
            usersList.add(userDAO.getByEmail("j.koetsier@onszoetermeer.nl"));
            return usersList;
        }

        @Override
        protected void onPostExecute(List<User> users) {
            super.onPostExecute(users);
            usersList = users;
            Log.d("ASYNC-SELECT: ",users.size()+" row(s) found.");
        }
    }

}

