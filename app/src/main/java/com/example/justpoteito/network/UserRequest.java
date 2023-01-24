package com.example.justpoteito.network;

import com.example.justpoteito.models.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserRequest extends NetConfiguration implements Runnable{

    private final String theUrl = theBaseUrl + "/usersNoToken";

    private User response;

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

                // Processing the JSON...
                String theUnprocessedJSON = response.toString();

                JSONArray jsonArray = new JSONArray (theUnprocessedJSON);

                this.response = new User();

                User user;
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject( i );

                    user = new User();
                    user.setId(object.getInt("id"));
                    user.setName(object.getString("name"));
                    user.setSurname( object.getString("surname"));
                    user.setUserName(object.getString("username"));
                    user.setEmail(object.getString("email"));
                    //user.setPassword(object.getString("password"));
                }
            } else {
                System.out.println("Desde el else de user request");
                this.response = new User();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public User getResponse() {
        return response;
    }
}
