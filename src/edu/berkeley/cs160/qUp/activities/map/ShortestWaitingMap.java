package edu.berkeley.cs160.qUp.activities.map;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.QueueListActivity;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.map.mapnavigator.LocationUtils;
import edu.berkeley.cs160.qUp.activities.map.mapnavigator.Navigator;

import java.util.ArrayList;

import static com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;


public class ShortestWaitingMap extends AbstractMapActivity implements
        ActionBar.OnNavigationListener, OnInfoWindowClickListener, LocationSource,
        LocationListener,  GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener {

    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    private static final String STATE_NAV = "nav";
    private static final int[] MAP_TYPE_NAMES = {R.string.normal,
            R.string.hybrid, R.string.satellite, R.string.terrain};
    private static final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_TERRAIN};
    private final static int
            CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    public static final String TAG = ShortestWaitingMap.class.getSimpleName();
    EditText start, end;
    /*
     * Note if updates have been turned on. Starts out as "false"; is set to "true" in the
     * method handleRequestSuccess of LocationUpdateReceiver.
     *
     */
    boolean mUpdatesRequested = false;
    //Initial and final locations.
    Location mCurrentLocation;
    LatLng mDest;


    // Handle to SharedPreferences for this app
    SharedPreferences mPrefs;

    // Handle to a SharedPreferences editor
    SharedPreferences.Editor mEditor;
    Button searchBtn;
    private GoogleMap mMap;
    private Double mUserLat, mUserLon;
    private LocationSource.OnLocationChangedListener mapLocationListener = null;
    private LocationManager locMgr = null;
    private Criteria crit = new Criteria();
    private LocationClient mLocationClient;
    // A request to connect to Location Services
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Create a new global location parameters object
        mLocationRequest = LocationRequest.create();

        /*
         * Set the update interval
         */
        mLocationRequest.setInterval(LocationUtils.UPDATE_INTERVAL_IN_MILLISECONDS);

        // Use high accuracy
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Set the interval ceiling to one minute
        mLocationRequest.setFastestInterval(LocationUtils.FAST_INTERVAL_CEILING_IN_MILLISECONDS);

        // Note that location updates are off until the user turns them on
        mUpdatesRequested = false;

        // Open Shared Preferences
        mPrefs = getSharedPreferences(LocationUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);

        // Get an editor
        mEditor = mPrefs.edit();

        /*
         * Create a new location client, using the enclosing class to
         * handle callbacks.
         */
        mLocationClient = new LocationClient(this, this, this);

        if (readyToGo()) {
            setContentView(R.layout.nav_activity);
            Intent i = getIntent();

            MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
            //Loc defaults to center of Berkeley dt:

            initListNav();
            mMap = mapFragment.getMap();

            if (savedInstanceState == null) {




                searchBtn = (Button) findViewById(R.id.search_button);
                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getLocation(v, mCurrentLocation);
                    }
                });

            }

        }
    }

    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }


    /**
     * Button to get current Location. This demonstrates how to get the current Location as required
     * without needing to register a LocationListener.
     */
    public void showMyLocation(View view) {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            String msg = "Location = " + mLocationClient.getLastLocation();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
    public void addDests(){
        addMarker(mMap, 37.8704130000, -122.2660890000,
                R.string.starbucks, R.string.coffee);
        addMarker(mMap, 37.8734560000, -122.2663680000,
                R.string.yalis, R.string.coffee);
        addMarker(mMap, 37.8751850000, -122.2603310000,
                R.string.brewed_awakening, R.string.coffee);
        addMarker(mMap, 37.8757400000, -122.2601050000,
                R.string.northside, R.string.coffee);
        mMap.setInfoWindowAdapter(new PopUpAdapter(getLayoutInflater()));
        mMap.setOnInfoWindowClickListener(this);
    }

    /*
     * Menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    private void initListNav() {
        ArrayList<String> items = new ArrayList<String>();
        ArrayAdapter<String> nav = null;
        ActionBar bar = getActionBar();

        for (int type : MAP_TYPE_NAMES) {
            items.add(getString(type));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            nav =
                    new ArrayAdapter<String>(
                            bar.getThemedContext(),
                            android.R.layout.simple_spinner_item,
                            items);
        } else {
            nav =
                    new ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_spinner_item,
                            items);
        }

        nav.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setListNavigationCallbacks(nav, this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.personal:
                //We are here
                return true;
            case R.id.business:
                Intent intent = new Intent(this, BusinessActivityMain.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Showing directions to " + marker.getTitle(), Toast.LENGTH_LONG).show();
        LatLng mDest = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        mCurrentLocation = mLocationClient.getLastLocation();
        LatLng mInit = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        Navigator nav = new Navigator(mMap, mInit, mDest);
        nav.findDirections(true, false);
        mMap.setInfoWindowAdapter(new PopUpAdapter(getLayoutInflater()));


    }


    /**
     * In response to a request to start updates, send a request
     * to Location Services
     */
    private void startPeriodicUpdates() {

        mLocationClient.requestLocationUpdates(mLocationRequest, this);
    }



    private void addMarker(GoogleMap map, double lat, double lon,
                           int title, int snippet) {
        map.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
                .title(getString(title))
                .snippet(getString(snippet)));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt(STATE_NAV,
                getActionBar().getSelectedNavigationIndex());


    }


    /**
     * In response to a request to stop updates, send a request to
     * Location Services
     */
    private void stopPeriodicUpdates() {
        mLocationClient.removeLocationUpdates(this);
    }

    /*
    * Handle results returned to this Activity by other Activities started with
    * startActivityForResult(). In particular, the method onConnectionFailed() in
    * LocationUpdateRemover and LocationUpdateRequester may call startResolutionForResult() to
    * start an Activity that handles Google Play services problems. The result of this
    * call returns here, to onActivityResult.
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // Log the result
                        Log.d(LocationUtils.APPTAG, getString(R.string.resolved));



                    // If any other result was returned by Google Play services
                    default:
                        // Log the result
                        Log.d(LocationUtils.APPTAG, getString(R.string.no_resolution));

                        // Display the result


                        break;
                }

                // If any other request code was received
            default:
                // Report that this Activity received an unknown requestCode
                Log.d(TAG,
                        getString(R.string.unknown_activity_request_code, requestCode));

                break;
        }
    }
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        mMap.setMapType(MAP_TYPES[itemPosition]);

        return (true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // If the app already has a setting for getting location updates, get it
        if (mPrefs.contains(LocationUtils.KEY_UPDATES_REQUESTED)) {
            mUpdatesRequested = mPrefs.getBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);

            // Otherwise, turn off location updates until requested
        } else {
            mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, false);
            mEditor.commit();
        }


    }

    @Override
    protected void onPause() {
        // Save the current setting for updates
        mEditor.putBoolean(LocationUtils.KEY_UPDATES_REQUESTED, mUpdatesRequested);
        mEditor.commit();

        super.onPause();

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_NAV));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mapLocationListener != null) {
            mapLocationListener.onLocationChanged(location);

            LatLng latlng =
                    new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cu = CameraUpdateFactory.newLatLng(latlng);

            mMap.animateCamera(cu);
        }
    }
    /*
     * Called when the Activity is no longer visible at all.
     * Stop updates and disconnect.
     */
    @Override
    public void onStop() {

        // If the client is connected
        if (mLocationClient.isConnected()) {
            stopPeriodicUpdates();
        }


        super.onStop();
    }

    /*
        * Called when the Activity is restarted, even before it becomes visible.
        */
    @Override
    public void onStart() {

        super.onStart();

        /*
         * Connect the client. Don't re-start any requests here;
         * instead, wait for onResume()
         */
        mLocationClient.connect();

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        this.mapLocationListener = listener;

    }

    @Override
    public void deactivate() {

    }

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
        mCurrentLocation = mLocationClient.getLastLocation();
        mUserLat = mCurrentLocation.getLatitude();
        mUserLon =mCurrentLocation.getLongitude();



//            start = (EditText) findViewById(R.id.startEditText);
//            end = (EditText) findViewById(R.id.endEditText);
        addMarker(mMap, mUserLat, mUserLon,
                R.string.here, R.string.berkeley);
        mMap.setInfoWindowAdapter(new PopUpAdapter(getLayoutInflater()));
        mMap.setOnInfoWindowClickListener(this);
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(mUserLat,
                        mUserLon));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

        mMap.moveCamera(center);
        mMap.animateCamera(zoom);
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * Invoked by the "Start Updates" button
     * Sends a request to start location updates
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void startUpdates(View v) {
        mUpdatesRequested = true;

        if (servicesConnected()) {
            startPeriodicUpdates();
        }
    }
    /**
     * Invoked by the "Stop Updates" button
     * Sends a request to remove location updates
     * request them.
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void stopUpdates(View v) {
        mUpdatesRequested = false;

        if (servicesConnected()) {
            stopPeriodicUpdates();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            showErrorDialog(connectionResult.getErrorCode());
        }
    }

    /**
     * Show a dialog returned by Google Play services for the
     * connection error code
     *
     * @param errorCode An error code returned from onConnectionFailed
     */
    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
                errorCode,
                this,
                LocationUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getFragmentManager(), LocationUtils.APPTAG);
        }
    }

    /**
     * Verify that Google Play services is available before making a request.
     *
     * @return true if Google Play services is available, otherwise false
     */
    private boolean servicesConnected() {

        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d(TAG, getString(R.string.play_services_available));

            // Continue
            return true;
            // Google Play services was not available for some reason
        } else {
            // Display an error dialog
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(dialog);
                errorFragment.show(getFragmentManager(), TAG);
            }
            return false;
        }
    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {
        // Global field to contain the error dialog
        private Dialog mDialog;
        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }
        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }
        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }



    /**
     * Invoked by the "Get Location" button.
     *
     * Calls getLastLocation() to get the current location
     *
     * @param v The view object associated with this method, in this case a Button.
     */
    public void getLocation(View v, Location dest) {

        // If Google Play Services is available
        if (servicesConnected()) {

            addDests();
            Location iLoc;
            mCurrentLocation = mLocationClient.getLastLocation();
            // Display the current location in the UI

            mMap.setInfoWindowAdapter(new PopUpAdapter(getLayoutInflater()));
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(mUserLat,
                            mUserLon));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(13);

            mMap.moveCamera(center);
            mMap.animateCamera(zoom);
            Toast.makeText(this, "Please click on a marker to get directions.",
                    Toast.LENGTH_SHORT).show();

        }
    }
}
