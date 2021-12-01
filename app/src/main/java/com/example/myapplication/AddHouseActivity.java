package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddHouseActivity extends FragmentActivity {
    private GoogleMap map;
    private EditText edit_address, edit_description, edit_lat, edit_lng, edit_floors;
    private LatLng latLng;
    private String address;
    private Button save;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_house_activity);
        map = MapsActivity.mMap;

        edit_address = findViewById(R.id.edit_address);
        edit_description = findViewById(R.id.edit_description);
        edit_lat = findViewById(R.id.edit_coordinate_lat);
        edit_lng = findViewById(R.id.edit_coordinate_lng);
        edit_floors = findViewById(R.id.edit_floors);
        save = findViewById(R.id.save);
        save.setEnabled(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            latLng = new LatLng(extras.getDouble("lat"), extras.getDouble("lng"));
            String lat_string = "" + latLng.latitude;
            String lng_string = "" + latLng.longitude;
            address = extras.getString("address");
            edit_address.setText(address);
            edit_lat.setText(lat_string);
            edit_lng.setText(lng_string);
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
        }
    }

    @Override
    public void onBackPressed(){
        cancel();
    }

    public void saveHouse(View view) {
        AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this);
        alertDialog_Builder.setCancelable(false);
        alertDialog_Builder.setMessage("Lagre hus?");
        alertDialog_Builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Double lat = Double.parseDouble(String.valueOf(edit_lat.getText()));
                Double lng = Double.parseDouble(String.valueOf(edit_lng.getText()));
                LatLng latLong = new LatLng(lat, lng);

                String address = String.valueOf(edit_address.getText());
                String description = String.valueOf(edit_description.getText());
                int floors = Integer.parseInt(String.valueOf(edit_floors.getText()));

                // TODO: httprequest, post, lagrehus.php
                map.addMarker(new MarkerOptions().position(latLong).title(address));
                map.moveCamera(CameraUpdateFactory.newLatLng(latLong));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLong, 18));
                //TODO Save this to web server
                finish();
            }
        });
        alertDialog_Builder.setNegativeButton("Nei", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alertDialog = alertDialog_Builder.create();
        alertDialog.show();
    }

    public void cancel(View view) {
        cancel();
    }

    public void cancel(){
        AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(this);
        alertDialog_Builder.setCancelable(true);
        alertDialog_Builder.setTitle("Avbryt?");
        alertDialog_Builder.setMessage("Avryt uten å legge til hus?");
        alertDialog_Builder.setPositiveButton("Ja, avbryt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alertDialog_Builder.setNegativeButton("Nei, bli her", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = alertDialog_Builder.create();
        alertDialog.show();
    }
}
