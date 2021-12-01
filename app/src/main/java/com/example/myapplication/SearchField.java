package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SearchField extends FragmentActivity {
    Button search, cancel;
    EditText search_field;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_field);
        search = findViewById(R.id.search_button);
        cancel = findViewById(R.id.cancel_search);
        search_field = findViewById(R.id.address_search);
    }

    public void cancel(View view) {
        finish();
    }

    public void search(View view) {
        if (search_field.getText() != null){
            String address = String.valueOf(search_field.getText());
            getCoordinates(address);
        }
    }
    public void getCoordinates(String address){
        GetLocationTask get_coordinates = new GetLocationTask(address);
        get_coordinates.execute();
    }

    private class GetLocationTask extends AsyncTask<Void, Void, String> {
        JSONObject jsonObject;
        String address;
        String location;
        public GetLocationTask(String address){
            this.address = address;
        }

        @Override
        protected String doInBackground(Void... voids){
            String s = "";
            String output = "";
            String query = "https://maps.googleapis.com/maps/api/geocode/json?address=" +
                    address.replaceAll(" ", "%20") +
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

                Double longitude = (double) 0;
                Double latitude = (double) 0;
                longitude = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location")
                        .getDouble("lng");
                latitude = ((JSONArray) jsonObject.get("results"))
                        .getJSONObject(0)
                        .getJSONObject("geometry")
                        .getJSONObject("location")
                        .getDouble("lat");

                location = String.valueOf(longitude) + ":" + String.valueOf(latitude);
                Intent intent = new Intent(getApplicationContext(), AddHouseActivity.class);
                intent.putExtra("address", address);
                intent.putExtra("lat", latitude);
                intent.putExtra("lng", longitude);
                startActivity(intent);
                finish();
                return location;


            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String result){
            System.out.println(result);
        }
    }
}
