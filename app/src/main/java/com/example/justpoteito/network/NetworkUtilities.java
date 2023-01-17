package com.example.justpoteito.network;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.justpoteito.R;
import com.example.justpoteito.models.CuisineType;

import java.util.ArrayList;

public class NetworkUtilities {
    Context context;
    Resources res;

    public NetworkUtilities() {
    }

    public NetworkUtilities(Context context){
        this.context = context;
        this.res = context.getResources();
    }
    public NetworkUtilities(Context context, Resources res) {
        this.context = context;
        this.res = res;
    }

    public ArrayList<CuisineType> makeRequest(CuisineTypesRequest cuisineTypesRequest) {

        if (isConnected()) {

            Thread thread = new Thread(cuisineTypesRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<CuisineType> listCuisineType = cuisineTypesRequest.getResponse();
            System.out.println("desde el network utilities"+listCuisineType+"---------------------------------------------------------------------------------------------------------");
            return listCuisineType;
        } else
            return new ArrayList<>();
    }

    public boolean isConnected() {
        boolean ret = false;
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService( Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if ((networkInfo != null) && (networkInfo.isAvailable()) && (networkInfo.isConnected()))
                ret = true;
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.error_communication), Toast.LENGTH_SHORT).show();
        }
        return ret;
    }
}
