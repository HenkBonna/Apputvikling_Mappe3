package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EditHouse extends FragmentActivity {
    private GoogleMap map;
    private EditText house_id, edit_address, edit_description, edit_lat, edit_lng, edit_floors;
    private LatLng latLng;
    private String address;
    private Button save;
    public House houseToEdit;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_house);
        map = MapsActivity.mMap;

        house_id = findViewById(R.id.house_id);
        edit_address = findViewById(R.id.edit_house_address);
        edit_description = findViewById(R.id.edit_house_description);
        edit_lat = findViewById(R.id.edit_house_coordinate_lat);
        edit_lng = findViewById(R.id.edit_house_coordinate_lng);
        edit_floors = findViewById(R.id.edit_house_floors);
        save = findViewById(R.id.save_edit);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            latLng = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
            String lat_string = "" + latLng.latitude;
            String lng_string = "" + latLng.longitude;
            address = extras.getString("address");
            house_id.setText(extras.getInt("id"));
            edit_address.setText(address);
            edit_lat.setText(lat_string);
            edit_lng.setText(lng_string);
            edit_floors.setText(extras.getInt("floors"));
            edit_description.setText(extras.getString("description"));
        }
        edit_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_lat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_lng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
        edit_floors.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkFieldsNotEmpty();
            }
        });
    }

    private void checkFieldsNotEmpty() {
        if (edit_address.getText() != null
                && edit_description.getText() != null
                && edit_lat.getText() != null
                && edit_lng.getText() != null
                && edit_floors.getText() != null){
            save.setEnabled(true);
        } else {
            save.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed(){
        cancel();
    }

    public void cancel() {
        finish();
    }

    public void saveEdit(View view) {
        int id = Integer.parseInt(String.valueOf(house_id.getText()));
        Double lat = Double.parseDouble(String.valueOf(edit_lat.getText()));
        Double lng = Double.parseDouble(String.valueOf(edit_lng.getText()));
        LatLng latLong = new LatLng(lat, lng);

        @SuppressLint("DefaultLocale") String lat_formatted = String.format("%,.5f", lat);
        @SuppressLint("DefaultLocale") String lng_formatted = String.format("%,.5f", lng);

        String address = String.valueOf(edit_address.getText());
        String description = String.valueOf(edit_description.getText());
        int floors = Integer.parseInt(String.valueOf(edit_floors.getText()));

        //####################################################

        houseToEdit =  new House(id, description, address, floors, latLong);

        String url = "http://studdata.cs.oslomet.no/~dbuser28/endrehus.php/" +
                "?Id=" + id +
                "&Beskrivelse=" + description.replaceAll(" ", "%20") +
                "&Gateadresse=" + address.replaceAll(" ", "%20") +
                "&Etasjer=" + floors +
                "&Latitude=" + lat_formatted +
                "&Longitude=" + lng_formatted;

        PostEditedHouse task = new PostEditedHouse();
        task.execute(new String[] {url});

        //####################################################

        //TODO: httprequest, put/update, endrehus.php
        //TODO Save this to web server
        finish();
    }

    //####################################################

    private class PostEditedHouse extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String retur = "";
            String s = "";
            String output = "";
            for (String url : urls) {
                try {
                    URL the_url = new URL(urls[0]);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) the_url.openConnection();
                    httpURLConnection.setRequestMethod("PUT");
                    httpURLConnection.setRequestProperty("Accept", "application/json");
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
                        System.out.println("## AddHouseActivity @ 244 ###\n");
                        return retur;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    return "Noe gikk feil.";
                }
            }
            System.out.println("## AddHouseActivity 266 ###\n" + retur);
            return retur;
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("## AddHouseActivity @ 257 ###\n" + s);
            try {
                //houseToEdit.setId(-1); // we should have post return Id as s.
                //addHouse(houseToSave);
                // TODO: Refresh houses? Render markers again?
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    //####################################################



}
