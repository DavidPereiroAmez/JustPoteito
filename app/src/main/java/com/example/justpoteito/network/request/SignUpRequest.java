package com.example.justpoteito.network.request;

import android.content.Context;
import android.content.res.Resources;

import com.example.justpoteito.R;
import com.example.justpoteito.models.UserResponse;
import com.example.justpoteito.network.NetConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignUpRequest extends NetConfiguration implements Runnable {

    private final String theUrl = theBaseUrl + "/auth/signupCrypted";
    private UserResponse response;
    private String userDataJson;
    public static Resources res;

    public SignUpRequest(String userDataJson, Context context) {
        res = context.getResources();
        this.userDataJson = userDataJson;
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

            this.response = new UserResponse();

            if (responseCode == 432) {

                this.response.setAccess(false);
                this.response.setMessage(res.getString(R.string.user_already_exists));

            }  else  if(responseCode == HttpURLConnection.HTTP_CREATED){

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader( httpURLConnection.getInputStream() ) );
                StringBuffer response = new StringBuffer();
                String inputLine;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    response.append( inputLine );
                }
                bufferedReader.close();

                this.response.setAccess(true);
                this.response.setMessage(res.getString(R.string.user_created));

            } else {
                this.response.setAccess(false);
                this.response.setMessage(res.getString(R.string.request_error));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public UserResponse getResponse() {
        return response;
    }
}
