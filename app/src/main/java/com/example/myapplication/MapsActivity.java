package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.icu.text.SymbolTable;
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
import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Toolbar toolbar;
    public ArrayList<House> houses = new ArrayList<>();
    public JSONArray buildings_JSON = new JSONArray();

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
        populate();


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
    }


    private void populate() {
        System.out.println("##### 099 : Du er i populate \n");
        getJSON task = new getJSON();
        task.execute(new String[] {"http://studdata.cs.oslomet.no/~dbuser28/hentallehus.php"});
    }

    private class getJSON extends AsyncTask<String, Void, String> {
        //JSONObject jsonObject;
        @Override
        protected String doInBackground(String... urls) {
            String retur = "";
            String s = "";
            String output = "";
            for (String url : urls) {
                try{
                    URL the_url = new URL(urls[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) the_url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("ACccept", "application/json");
                    if (httpURLConnection.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : " + httpURLConnection.getResponseCode());
                    }
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    System.out.println("Output from server ..... \n");
                    while ((s = bufferedReader.readLine()) != null) {
                        output = output + s;
                    }
                    httpURLConnection.disconnect();
                    try {
                        //JSONArray array = new JSONArray(output);
                        buildings_JSON = new JSONArray(output);
                        /*for (int i = 0; i < array.length(); i++){
                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String beskrivelse = object.getString("beskrivelse");
                            String gateadresse = object.getString("gateadresse");
                            String etasjer = object.getString("etasjer");
                            String latitude = object.getString("latitude");
                            String longitude = object.getString("longitude");
                            String object_string = "{'id':'" + id + "', 'beskrivelse':'" + beskrivelse
                                    + "', 'gateadresse':'" + gateadresse  + "', 'etasjer':'" + etasjer  + "', 'latitude':'" + latitude
                                    + "', 'longitude':'" + longitude + "'}";
                            retur = retur + object_string + "\n";
                        }

                         */
                        return retur;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e){
                    return "Noe gikk feil.";
                }
            }
            System.out.println("## 150 ###\n" + retur);
            return retur;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("## 155 ###\n" + s);
            //buildings.add(s);
            //buildings_JSON.

            generateMarkers();
        }

    }

    private void generateMarkers() {
        System.out.println("## 168 generateMarkers() ###\n");
        for (int i = 0; i < buildings_JSON.length(); i++){
            JSONObject object = null;
            try {
                object = buildings_JSON.getJSONObject(i);
                int id = Integer.parseInt(object.getString("id"));
                String beskrivelse = object.getString("beskrivelse");
                String gateadresse = object.getString("gateadresse");
                int etasjer = Integer.parseInt(object.getString("etasjer"));
                Double latitude = Double.parseDouble(object.getString("latitude"));
                Double longitude = Double.parseDouble(object.getString("longitude"));

                LatLng latLng = new LatLng(latitude, longitude);

                // TODO: If we implement a class, do it here!
                House house = new House(id, beskrivelse, gateadresse, etasjer, latLng);
                houses.add(house);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (House h : houses){
            mMap.addMarker(new MarkerOptions().position(h.getLatLng()).title(h.getDescription()));
        }
    }

    private void addSingleMarker(String id, String beskrivelse, String gateadresse, String latitude, String longitude) {
        // TODO: Implement
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