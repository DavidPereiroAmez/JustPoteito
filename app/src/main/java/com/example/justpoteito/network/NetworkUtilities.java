package com.example.justpoteito.network;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.justpoteito.R;
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.models.RequestResponse;
import com.example.justpoteito.models.UserImage;
import com.example.justpoteito.models.UserResponse;
import com.example.justpoteito.network.request.ChangePasswordRequest;
import com.example.justpoteito.network.request.CooksRequest;
import com.example.justpoteito.network.request.CuisineTypesRequest;
import com.example.justpoteito.network.request.DeleteUserRequest;
import com.example.justpoteito.network.request.DishByIdRequest;
import com.example.justpoteito.network.request.DishesByCookRequest;
import com.example.justpoteito.network.request.DishesByCuisineTypeRequest;
import com.example.justpoteito.network.request.DishesByIngredientRequest;
import com.example.justpoteito.network.request.DishesRequest;
import com.example.justpoteito.network.request.IngredientsByDishRequest;
import com.example.justpoteito.network.request.IngredientsRequest;
import com.example.justpoteito.network.request.LoginRequest;
import com.example.justpoteito.network.request.SendEmailRequest;
import com.example.justpoteito.network.request.SignUpRequest;
import com.example.justpoteito.network.request.SendUserImageRequest;
import com.example.justpoteito.network.request.UserImageRequest;

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

    public UserResponse makeRequest(SignUpRequest signUpRequest) {

        if (isConnected()) {

            Thread thread = new Thread(signUpRequest);
            try {
                thread.start();
                thread.join(10000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            UserResponse response = signUpRequest.getResponse();

            return response;


        } else
            return new UserResponse(false, res.getString(R.string.error_communication));
    }
    public UserImage makeRequest(SendUserImageRequest sendUserImageRequest) {

        if (isConnected()) {

            Thread thread = new Thread(sendUserImageRequest);
            try {
                thread.start();
                thread.join(10000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            UserImage response = sendUserImageRequest.getResponse();

            return response;


        } else
            return new UserImage();
    }

    public UserImage makeRequest(UserImageRequest userImageRequest) {

        if (isConnected()) {

            Thread thread = new Thread(userImageRequest);
            try {
                thread.start();
                thread.join(10000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            UserImage response = userImageRequest.getResponse();

            return response;


        } else
            return new UserImage();
    }

    public UserResponse makeRequest(LoginRequest loginRequest) {
        if (isConnected()) {

            Thread thread = new Thread(loginRequest);
            try {
                thread.start();
                thread.join(5000); // Awaiting response from the server...
            } catch (InterruptedException e) {

            }
            // Processing the answer
            UserResponse response = loginRequest.getResponse();

            return response;
        } else
            return new UserResponse(false, res.getString(R.string.error_communication));
    }

    public String makeRequest(SendEmailRequest sendEmailRequest) {
        if (isConnected()) {

            Thread thread = new Thread(sendEmailRequest);
            try {
                thread.start();
                thread.join(); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            String response = sendEmailRequest.getResponse();

            return response;
        } else
            return new String("Something went wrong");
    }

    public String makeRequest(DeleteUserRequest deleteUserRequest) {
        if (isConnected()) {

            Thread thread = new Thread(deleteUserRequest);
            try {
                thread.start();
                thread.join(10000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            String response = deleteUserRequest.getResponse();

            return response;
        } else
            return new String("Something went wrong");
    }

    public RequestResponse makeRequest(ChangePasswordRequest changePasswordRequest) {

        if (isConnected()) {

            Thread thread = new Thread(changePasswordRequest);
            try {
                thread.start();
                thread.join(10000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            RequestResponse response = changePasswordRequest.getResponse();

            return response;


        } else
            return new RequestResponse(false, res.getString(R.string.error_communication));
    }

    public ArrayList<CuisineType> makeRequest(CuisineTypesRequest cuisineTypesRequest) {

        if (isConnected()) {

            Thread thread = new Thread(cuisineTypesRequest);
            try {
                thread.start();
                thread.join(5000); // Awaiting response from the server...
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
                thread.join(5000); // Awaiting response from the server...
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
                thread.join(5000); // Awaiting response from the server...
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
                thread.join(5000); // Awaiting response from the server...
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
                thread.join(5000); // Awaiting response from the server...
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
                thread.join(5000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Dish> listDish = dishesByCookRequest.getResponse();
            return listDish;
        } else
            return new ArrayList<>();
    }
    public ArrayList<Dish> makeRequest(DishesByIngredientRequest dishesByIngredientRequest) {

        if (isConnected()) {

            Thread thread = new Thread(dishesByIngredientRequest);
            try {
                thread.start();
                thread.join(5000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Dish> listDish = dishesByIngredientRequest.getResponse();
            return listDish;
        } else
            return new ArrayList<>();
    }

    public Dish makeRequest(DishByIdRequest dishByIdRequest) {
        if (isConnected()) {
            Thread thread = new Thread(dishByIdRequest);
            try {
                thread.start();
                thread.join(5000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            Dish dish = dishByIdRequest.getResponse();

            return dish;
        } else
            return null;
    }

    public ArrayList<Ingredient> makeRequest(IngredientsByDishRequest ingredientsByDishRequest) {

        if (isConnected()) {

            Thread thread = new Thread(ingredientsByDishRequest);
            try {
                thread.start();
                thread.join(5000); // Awaiting response from the server...
            } catch (InterruptedException e) {
                // Nothing to do here...
            }
            // Processing the answer
            ArrayList<Ingredient> listIngredient = ingredientsByDishRequest.getResponse();
            return listIngredient;
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
            e.printStackTrace();
        }
        return ret;
    }

}
