package com.example.justpoteito.network.request;

import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.network.NetConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CuisineTypesRequest extends NetConfiguration implements Runnable {

    private final String theUrl = theBaseUrl + "/cuisineTypesNoToken";
    private ArrayList<CuisineType> response;
    @Override
    public void run() {
        try {
            URL url = new URL( theUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod( "GET" );
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader( httpURLConnection.getInputStream() ) );
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append( inputLine );
                }
                bufferedReader.close();

                String theUnprocessedJSON = response.toString();

                JSONArray jsonArray = new JSONArray (theUnprocessedJSON);

                this.response = new ArrayList<CuisineType>();

                CuisineType cuisineType;
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject( i );

                    cuisineType = new CuisineType();
                    cuisineType.setId(object.getInt("id"));
                    cuisineType.setName(object.getString("name"));
                    cuisineType.setSubtype( object.getString("subtype"));

                    this.response.add( cuisineType );
                }
            } else {
                this.response = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<CuisineType> getResponse() {
        return response;
    }


}
