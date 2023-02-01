package com.example.justpoteito.network;

import com.example.justpoteito.models.Dish;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DishByIdRequest extends NetConfiguration implements Runnable{
    private final String theUrl = theBaseUrl + "/dishesNoToken";
    private Dish response;
    private String dishId;

    public DishByIdRequest(String dishId) {
        this.dishId = dishId;
    }

    @Override
    public void run() {
        try {
            URL url = new URL( theUrl + "/" + dishId);
            System.out.println(url);
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
                // Processing the JSON...
                String theUnprocessedJSON = response.toString();
                JSONObject jsonObject = new JSONObject(theUnprocessedJSON);

                this.response = new Dish();

                Dish dish;

                    this.response.setId(jsonObject.getInt("id"));
                    this.response.setName(jsonObject.getString("name"));
                    this.response.setIdCuisineType(jsonObject.getInt("cuisineTypeId"));
                    this.response.setSubtype(jsonObject.getString("subtype"));
                    this.response.setPrepTime(jsonObject.getInt("prepTime"));
                    this.response.setrecipe(jsonObject.getString("recipe"));
                    this.response.setImage(jsonObject.getString("image"));

            } else {
                this.response = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Dish getResponse() {
        return response;
    }
}
