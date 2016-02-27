package ads.app.dveag.com.dshop;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<String> optionList;
    private ListView drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpMapIfNeeded();

        drawerLayout = (ListView) findViewById(R.id.drawerLayout);
        optionList = new ArrayList<>();
        optionList.add("Vista normal");
        optionList.add("Vista satélite");
        optionList.add("Vista tráfico");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_expandable_list_item_1,optionList);
        drawerLayout.setAdapter(adapter);

        drawerLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    mMap.setTrafficEnabled(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                } else if(position == 1){
                    mMap.setTrafficEnabled(false);
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                }
                else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    mMap.setTrafficEnabled(true);
                }

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng tienda = new LatLng(37.902459, -4.719530);
        mMap.addMarker(new MarkerOptions().position(tienda).title("La tienda"));
        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        return;
                    }
                    mMap.setMyLocationEnabled(true); // Habilitamos nuestra localización en el mapa
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                }
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                setUpMap();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void setUpMap() {
        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
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
        Location myLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude of the current location
        double latitude = myLocation.getLatitude();

        // Get longitude of the current location
        double longitude = myLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng miposicion = new LatLng(latitude, longitude);

        mMap.addMarker(new MarkerOptions().position(miposicion).title("Posicion Actual").alpha(0.8F));
        //Show the current location in Google Map

        CameraUpdate mipos = CameraUpdateFactory.newLatLngZoom(miposicion,19);

        //Zoom in the Google Map
        mMap.animateCamera(mipos);
    }
}
