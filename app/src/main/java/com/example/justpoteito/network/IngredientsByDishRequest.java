package com.example.justpoteito.network;

import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class IngredientsByDishRequest extends NetConfiguration implements Runnable{
    private final String theUrl = theBaseUrl + "/ingredientsByDishIdNoToken";
    private ArrayList<Ingredient> response;
    private String dishId;

    public IngredientsByDishRequest(String dishId) {
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
                JSONArray jsonArray = new JSONArray (theUnprocessedJSON);

                this.response = new ArrayList<Ingredient>();

                Ingredient ingredient;
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject( i );

                    ingredient = new Ingredient();
                    ingredient.setId(object.getInt("id"));
                    ingredient.setName(object.getString("name"));
                    ingredient.setType( object.getString("type"));

                    this.response.add( ingredient );
                }
            } else {
                this.response = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Ingredient> getResponse() {
        return response;
    }
}
