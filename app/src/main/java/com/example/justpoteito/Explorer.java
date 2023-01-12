package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;

import com.example.justpoteito.adapters.TypeCuisineAdapter;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Ingredient;

import java.util.ArrayList;

public class Explorer extends AppCompatActivity {
    ArrayList<CuisineType> cuisineTypeList;
    ArrayList<Ingredient> ingredients;

    FragmentTransaction transaction;
    Fragment exploreRecipesFragment, exploreByCookFragment, exploreByCuisineTypeFragment, exploreByDishFragment;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_explorer);
        getSupportActionBar().hide();

        cuisineTypeList = new ArrayList<>();
        cuisineTypeList.add(new CuisineType(
                0,
                "Chinesse",
                "Asian"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Italian",
                "Mediterranean"
        ));
        cuisineTypeList.add(new CuisineType(
                0,
                "Chinesse",
                "Asian"
        ));
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ingredients = new ArrayList<>();
        ingredients.add(new Ingredient(

                )

        );

        setContentView(R.layout.layout_explorer);
        ((ListView) findViewById(R.id.cuisineTypeList)).setAdapter(new TypeCuisineAdapter(this, R.layout.type_cuisine_row_layout, cuisineTypeList));

        //exploreByCuisineTypeFragment = new ExploreByCuisineTypeFragment();
    }
}