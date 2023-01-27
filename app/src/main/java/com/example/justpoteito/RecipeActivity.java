package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.example.justpoteito.models.Dish;
import com.example.justpoteito.network.DishByIdRequest;
import com.example.justpoteito.network.NetworkUtilities;

public class RecipeActivity extends AppCompatActivity {
    NetworkUtilities networkUtilities;
    String dishId;
    Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userName = sharedPreferences.getAll().get("username").toString();
        ((TextView) findViewById(R.id.username)).setText(userName);

        Bundle extras = getIntent().getExtras();
        dishId= extras.getString("dishId");
        System.out.println("dedeedededede "+dishId);
        networkUtilities = new NetworkUtilities(RecipeActivity.this);
        chargeDishData();

        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void chargeDishData(){
        dish = networkUtilities.makeRequest(new DishByIdRequest(dishId));
        System.out.println(dish.getName());
        ((TextView) findViewById(R.id.title_dish)).setText(dish.getName());
        System.out.println(dish);
        ((TextView) findViewById(R.id.dish_preparation_time)).setText(dish.getPrepTime()+"");

    }
}