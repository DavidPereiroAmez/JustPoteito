package com.example.justpoteito.network.request;

import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.UserImage;
import com.example.justpoteito.network.NetConfiguration;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class UserImageRequest extends NetConfiguration implements Runnable{
    private final String theUrl = theBaseUrl + "/cooksNoToken";
    private UserImage response;

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

                this.response = new UserImage();

                UserImage userImage;
                for(int i=0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject( i );

                    userImage = new UserImage();
                    userImage.setId(object.getInt("id"));
                    userImage.setImage(object.getString("image"));

                    this.response=  userImage;
                }
            } else {
                this.response = new UserImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public UserImage getResponse() {
        return response;
    }
}
