package com.example.justpoteito.network;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.justpoteito.R;
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.models.UserResponse;

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

    public UserResponse makeRequest(CreateUserRequest createUserRequest) {

        if (isConnected()) {

            Thread thread = new Thread(createUserRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            UserResponse response = createUserRequest.getResponse();

            return response;


        } else
            return new UserResponse(false, res.getString(R.string.error_communication));
    }

    public UserResponse makeRequest(LoginRequest loginRequest) {
        if (isConnected()) {

            Thread thread = new Thread(loginRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            UserResponse response = loginRequest.getResponse();

            return response;
        } else
            return new UserResponse(false, res.getString(R.string.error_communication));
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
            return listCuisineType;
        } else
            return new ArrayList<>();
    }

    public ArrayList<Dish> makeRequest(DishesRequest dishesRequest) {

        if (isConnected()) {

            Thread thread = new Thread(dishesRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Dish> listDish = dishesRequest.getResponse();
            return listDish;
        } else
            return new ArrayList<>();
    }

    public ArrayList<Cook> makeRequest(CooksRequest cookRequest) {

        if (isConnected()) {

            Thread thread = new Thread(cookRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Cook> listCook = cookRequest.getResponse();
            return listCook;
        } else
            return new ArrayList<>();
    }

    public ArrayList<Ingredient> makeRequest(IngredientsRequest ingredientsRequest) {

        if (isConnected()) {

            Thread thread = new Thread(ingredientsRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Ingredient> listIngredients = ingredientsRequest.getResponse();
            return listIngredients;
        } else
            return new ArrayList<>();
    }

    public ArrayList<Dish> makeRequest(DishesByCuisineTypeRequest dishesByCuisineTypeRequest) {

        if (isConnected()) {

            Thread thread = new Thread(dishesByCuisineTypeRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Dish> listDish = dishesByCuisineTypeRequest.getResponse();
            return listDish;
        } else
            return new ArrayList<>();
    }

    public ArrayList<Dish> makeRequest(DishesByCookRequest dishesByCookRequest) {

        if (isConnected()) {
            Thread thread = new Thread(dishesByCookRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Dish> listDish = dishesByCookRequest.getResponse();
            return listDish;
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
