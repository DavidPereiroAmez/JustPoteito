package com.example.justpoteito.network;

import com.example.justpoteito.models.CuisineType;

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
        System.out.println("Desde el inicio de cuisine types Request+-+-+-+-+-+-+-+-+-+-");
        try {
            System.out.println("Desde el try de cuisine types Request");
            URL url = new URL( theUrl);
            System.out.println("desde hola1");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            System.out.println("desde hola2");
            httpURLConnection.setRequestMethod( "GET" );
            System.out.println("desde hola3");
            System.out.println("Desde justo antes del if de cuisine types Request");
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("desde codigo de respuesta: "+responseCode);
            if(responseCode == HttpURLConnection.HTTP_OK){
                System.out.println("Desde el if de codigo de respuesta ok");
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader( httpURLConnection.getInputStream() ) );
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append( inputLine );
                }
                bufferedReader.close();

                // Processing the JSON...
                String theUnprocessedJSON = response.toString();

                JSONArray jsonArray = new JSONArray (theUnprocessedJSON);

                this.response = new ArrayList<CuisineType>();

                CuisineType cuisineType;
                for(int i=0; i < jsonArray.length(); i++) {
                    System.out.println("Desde Iterando" + i);
                    JSONObject object = jsonArray.getJSONObject( i );

                    cuisineType = new CuisineType();
                    cuisineType.setId(object.getInt("id"));
                    cuisineType.setName(object.getString("name"));
                    cuisineType.setSubtype( object.getString("subtype"));

                    this.response.add( cuisineType );
                }
            } else {
                System.out.println("Desde el else de cuisine type request");
                this.response = new ArrayList<>();
            }
            System.out.println("Desde cuisine type request"+response+"--------------------------------------------------------------------------");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<CuisineType> getResponse() {
        System.out.println("Desde cuisine type request getresponse"+response+"--------------------------------------------------------------------------");
        return response;
    }


}
