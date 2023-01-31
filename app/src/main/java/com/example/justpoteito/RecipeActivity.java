package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justpoteito.adapters.IngredientInRecipeAdapter;
import com.example.justpoteito.adapters.TypeCuisineAdapter;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.network.DishByIdRequest;
import com.example.justpoteito.network.IngredientsByDishRequest;
import com.example.justpoteito.network.NetworkUtilities;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    private NetworkUtilities networkUtilities;
    private String dishId;
    private Dish dish;
    private List<Ingredient> ingredientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userName = sharedPreferences.getAll().get("username").toString();
        ((TextView) findViewById(R.id.username)).setText(userName);

        Bundle extras = getIntent().getExtras();
        dishId= extras.getString("dishId");
        networkUtilities = new NetworkUtilities(RecipeActivity.this);
        if(dishId!=null){
            chargeDishData();
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        }
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void chargeDishData(){
        dish = networkUtilities.makeRequest(new DishByIdRequest(dishId));
        if(dish!=null){
            ((TextView) findViewById(R.id.title_dish)).setText(dish.getName());
            ((TextView) findViewById(R.id.dish_preparation_time)).setText(dish.getPrepTime()+"");
            ((TextView) findViewById(R.id.load_recipe)).setText(dish.getrecipe()+"");

            ingredientList = networkUtilities.makeRequest(new IngredientsByDishRequest(dishId));

            ListView ingredientListView = ((ListView) findViewById(R.id.load_ingredients));
            ingredientListView.setAdapter(new IngredientInRecipeAdapter(this, R.layout.ingredient_row_layout, (ArrayList<Ingredient>) ingredientList));
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ExplorerActivity.class);
            startActivity(intent);
            finish();
        }
    }
}