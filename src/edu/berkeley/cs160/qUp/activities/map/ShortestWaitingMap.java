package edu.berkeley.cs160.qUp.activities.map;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import edu.berkeley.cs160.qUp.R;
import edu.berkeley.cs160.qUp.activities.QueueListActivity;
import edu.berkeley.cs160.qUp.activities.business.BusinessActivityMain;
import edu.berkeley.cs160.qUp.activities.map.mapnavigator.Navigator;

public class ShortestWaitingMap extends AbstractMapActivity implements
        ActionBar.OnNavigationListener, GoogleMap.OnInfoWindowClickListener {

    private static final String STATE_NAV = "nav";
    private static final int[] MAP_TYPE_NAMES = {R.string.normal,
            R.string.hybrid, R.string.satellite, R.string.terrain};
    private static final int[] MAP_TYPES = {GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID, GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_TERRAIN};
    EditText start, end;
    Button searchBtn;
    private GoogleMap map=null;

    private Double mLat, mLon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (readyToGo()) {
            setContentView(R.layout.nav_activity);
            MapFragment mapFragment = (com.google.android.gms.maps.MapFragment) getFragmentManager().findFragmentById(R.id.map);
            Intent i = getIntent();

            //Loc defaults to center of Berkeley campus:
            mLat = i.getDoubleExtra(QueueListActivity.LAT, -122.28);
            mLon = i.getDoubleExtra(QueueListActivity.LON, 37.871);

//
//            start = (EditText) findViewById(R.id.startEditText);
//            end = (EditText) findViewById(R.id.endEditText);
//            searchBtn = (Button) findViewById(R.id.searchButton);

             initListNav();
            map = mapFragment.getMap();


            if (savedInstanceState == null) {
                CameraUpdate center=
                        CameraUpdateFactory.newLatLng(new LatLng(mLat,
                                mLon));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                map.moveCamera(center);
                map.animateCamera(zoom);

                addMarker(map, mLat, mLon,
                        R.string.business_subtype_title, R.string.business_name);



                map.setInfoWindowAdapter(new PopUpAdapter(getLayoutInflater()));
                map.setOnInfoWindowClickListener(this);
            }

        }
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
        ArrayList<String> items=new ArrayList<String>();
        ArrayAdapter<String> nav=null;
        ActionBar bar=getActionBar();

        for (int type : MAP_TYPE_NAMES) {
            items.add(getString(type));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            nav=
                    new ArrayAdapter<String>(
                            bar.getThemedContext(),
                            android.R.layout.simple_spinner_item,
                            items);
        }
        else {
            nav=
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
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
        LatLng iLoc= new LatLng(mLat,mLon);
        LatLng fLoc = new LatLng(37.781, -122.281);
        Navigator nav = new Navigator(map, iLoc, fLoc);
        nav.findDirections(true, false);

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


    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        map.setMapType(MAP_TYPES[itemPosition]);

        return(true);

    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        getActionBar().setSelectedNavigationItem(savedInstanceState.getInt(STATE_NAV));
    }
    /*
     * Private Listener Class
     * onClick() will go to the URLHandler class
     */
    private class ButtonListener implements Button.OnClickListener {

        Context context;

        //Constructor
        public ButtonListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View arg0) {

        }

    }
}
