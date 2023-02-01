package com.example.justpoteito.network.request;

import com.example.justpoteito.models.Dish;
import com.example.justpoteito.network.NetConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DishesByIngredientRequest extends NetConfiguration implements Runnable{
    private String IngredientIds;
    private final String theUrl = theBaseUrl + "/getAllDishesByIngredientNoToken";
    private ArrayList<Dish> response;

    public DishesByIngredientRequest(String IngredientIds) {
        this.IngredientIds = IngredientIds;
    }

    @Override
    public void run() {
        try {
            URL url = new URL( theUrl + "?idList=" + IngredientIds);

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

                this.response = new ArrayList<Dish>();

                Dish dish;
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject( i );

                    dish = new Dish();
                    dish.setId(object.getInt("id"));
                    dish.setName(object.getString("name"));
                    dish.setSubtype( object.getString("subtype"));

                    this.response.add( dish );
                }
            } else {
                this.response = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Dish> getResponse() {
        return response;
    }
}
