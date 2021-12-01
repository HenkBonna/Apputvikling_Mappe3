package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.setHasOptionsMenu(true);
        /*
        toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.list_menu);
        setActionBar(toolbar);
        getActionBar().setTitle(null);
        toolbar.setNavigationIcon(R.drawable.icons8_plus);
        */
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setMapStyle();


        LatLng p35 = new LatLng(59.91941,10.73478);
        mMap.addMarker(new MarkerOptions().position(p35).title("Markør på P35"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(p35));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(p35, 18));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                System.out.println("Marker clicked " + marker.getTitle());
                return false;
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                getAddress(latLng);
            }
        });


        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void addByAddress(View view){
        startActivity(new Intent(getApplicationContext(), SearchField.class));
    }

    public void getAddress(LatLng latLng){
        GetAddress getAddress = new GetAddress(latLng);
        getAddress.execute();
    }

    private class GetAddress extends AsyncTask<LatLng, Void, String>{

        JSONObject jsonObject;
        String address;
        double lat, lng;

        public GetAddress (LatLng latLng){
            this.lat = latLng.latitude;
            this.lng = latLng.longitude;
        }

        @Override
        public String doInBackground(LatLng... latLongs){
            String s = "";
            String output = "";
            String query = "https://maps.googleapis.com/maps/api/geocode/json?latlng="
                    + lat + "," + lng +
                    "&key=AIzaSyAaPT9tMV9E8RBUSLBCNNRr0-T9dd1nG1s";
            try {
                URL url = new URL(query);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if (connection.getResponseCode() != 200){
                    throw new RuntimeException("Failed: HTTP error code: "
                            + connection.getResponseCode());
                }

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                while ((s = br.readLine()) != null){
                    output += s;
                }

                jsonObject = new JSONObject(output);
                connection.disconnect();


                address = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0)
                        .getString("formatted_address");

                boolean valid = false;
                if (address.matches("[A-ZÆØÅa-zæøå\\s\\.\\']{4,30} [0-9]{1,5}[A-ZÆØÅa-zæøå]?, [0-9]{4} [A-ZÆØÅa-zæøå]{2,20}, [A-ZÆØÅa-zæøå]{2,20}")){
                    valid = true;
                }
                if (valid){
                    Intent intent = new Intent(getApplicationContext(), AddHouseActivity.class);
                    intent.putExtra("address", address);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    startActivity(intent);
                }
                return address;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setMapStyle(){
        //mMap.setBuildingsEnabled(false);
        MapStyleOptions mapStyleOptions = new MapStyleOptions("[" +
                "  {" +
                "    \"featureType\":\"poi\"," + // can spesify with poi.business, etc.
                "    \"elementType\":\"all\"," +
                "    \"stylers\":[" +
                "      {" +
                "        \"visibility\":\"off\"" +
                "      }" +
                "    ]" +
                "  }," +
                "  {" +
                "    \"featureType\":\"transit\"," +
                "    \"elementType\":\"all\"," +
                "    \"stylers\":[" +
                "      {" +
                "        \"visibility\":\"off\"" +
                "      }" +
                "    ]" +
                "  }" +
                "]");
        mMap.setMapStyle(mapStyleOptions);
    }
}