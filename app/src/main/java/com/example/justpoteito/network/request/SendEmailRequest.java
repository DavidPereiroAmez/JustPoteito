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

public class SendEmailRequest extends NetConfiguration implements Runnable {

    private final String theUrl = theBaseUrl + "/forgotpassword";
    private String response;
    private String email;
    public static Resources res;

    public SendEmailRequest(String email, Context context) {
        res = context.getResources();
        this.email = email;
    }

    @Override
    public void run() {
        try {
            response = new String();

            URL url = new URL(theUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "text/plain");
            httpURLConnection.setDoOutput(true);

            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = email.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                response = "Correo enviado.";
            } else {
                response = "Fallo en el envío.";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getResponse() {
        return response;
    }
}
