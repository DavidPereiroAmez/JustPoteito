package com.example.justpoteito.network.request;

import android.content.Context;
import android.content.res.Resources;

import com.example.justpoteito.R;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.models.UserResponse;
import com.example.justpoteito.network.NetConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LoginRequest extends NetConfiguration implements Runnable {

    private final String theUrl = theBaseUrl + "/notoken/login";
    public UserResponse response;
    public static Resources res;
    String userDataJson;

    public LoginRequest(String userDataJson, Context context) {
        this.userDataJson = userDataJson;
        response = new UserResponse();
        res = context.getResources();
    }

    @Override
    public void run() {

        try {
            URL url = new URL(theUrl);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);

            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = userDataJson.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == 433) {
                response.setAccess(false);
                response.setMessage(res.getString(R.string.user_doesnt_exist));

            }else if (responseCode == 434) {
                response.setAccess(false);
                response.setMessage(res.getString(R.string.incorrect_password));

            } else if (responseCode == 435) {
                response.setAccess(false);
                response.setMessage(res.getString(R.string.incorrect_password));

            } else if (responseCode == 202) {

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader( httpURLConnection.getInputStream() ) );
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append( inputLine );
                }
                bufferedReader.close();

                String theUnprocessedJSON = response.toString();
                JSONObject object = new JSONObject (theUnprocessedJSON);

                System.out.println("User id: " + theUnprocessedJSON);

                this.response.setId(object.getInt("id"));
                this.response.setName(object.getString("name"));
                this.response.setSurnames(object.getString("surnames"));
                this.response.setUserName(object.getString("userName"));
                this.response.setEmail(object.getString("email"));
                this.response.setEnabled(object.getBoolean("enabled"));


                this.response.setAccess(true);
                this.response.setMessage(res.getString(R.string.welcome)+ " " + this.response.getUserName());
            } else {
                response.setAccess(false);
                response.setMessage(res.getString(R.string.request_error));
            }
        } catch (MalformedURLException | ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponse getResponse(){
        return response;
    }
}
