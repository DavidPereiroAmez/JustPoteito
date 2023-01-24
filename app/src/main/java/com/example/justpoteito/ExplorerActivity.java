package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.justpoteito.adapters.CookAdapter;
import com.example.justpoteito.adapters.DishAdapter;
import com.example.justpoteito.adapters.IngredientAdapter;
import com.example.justpoteito.adapters.SelectedDishAdapter;
import com.example.justpoteito.adapters.TypeCuisineAdapter;
import com.example.justpoteito.fragments.ExploreByCookFragment;
import com.example.justpoteito.fragments.ExploreByCuisineTypeFragment;
import com.example.justpoteito.fragments.ExploreByDishFragment;
import com.example.justpoteito.fragments.ExploreByIngredientFragment;
import com.example.justpoteito.fragments.SelectedDishFromCookFragment;
import com.example.justpoteito.fragments.SelectedDishFromCuisineTypeFragment;
import com.example.justpoteito.fragments.ExploreFragment;
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.network.CooksRequest;
import com.example.justpoteito.network.CuisineTypesRequest;
import com.example.justpoteito.network.DishesByCookRequest;
import com.example.justpoteito.network.DishesByCuisineTypeRequest;
import com.example.justpoteito.network.DishesRequest;
import com.example.justpoteito.network.IngredientsRequest;
import com.example.justpoteito.network.NetworkUtilities;

import java.util.ArrayList;

public class ExplorerActivity extends AppCompatActivity {

    FragmentTransaction transaction;

    Fragment exploreFragment,
             exploreByCuisineTypeFragment,
             exploreByDishFragment,
             exploreByIngredientFragment,
             exploreByCookFragment,
             selectedDishFromCuisineTypeFragment,
             selectedDishFromCookFragment;

    ArrayList<CuisineType> cuisineTypeList;
    ArrayList<Dish> dishList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<Cook> cookList;
    ArrayList<Dish> selectedDishList;

    NetworkUtilities networkUtilities;

    int idCuisineType;
    int idCook;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);

        exploreFragment = new ExploreFragment();
        exploreByCuisineTypeFragment = new ExploreByCuisineTypeFragment();
        exploreByDishFragment = new ExploreByDishFragment();
        exploreByIngredientFragment = new ExploreByIngredientFragment();
        exploreByCookFragment = new ExploreByCookFragment();
        selectedDishFromCuisineTypeFragment = new SelectedDishFromCuisineTypeFragment();
        selectedDishFromCookFragment = new SelectedDishFromCookFragment();

        networkUtilities = new NetworkUtilities(ExplorerActivity.this);

        setFragment("exploreFragment");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public void setFragment(String fragment) {
        switch (fragment) {
            case "exploreFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, exploreFragment).runOnCommit(new Runnable(){
                    @Override
                    public void run() {
                        exploreOnCreate();
                    }
                }).commit();
                break;

            case "exploreByCuisineTypeFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByCuisineTypeFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByCuisineTypeOnCreate(); }
                }).commit();
                break;

            case "exploreByDishFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByDishFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByDishOnCreate(); }
                }).commit();
                break;

            case "exploreByIngredientFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByIngredientFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByIngredientOnCreate(); }
                }).commit();
                break;

            case "exploreByCookFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByCookFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByCookOnCreate(); }
                }).commit();
                break;

            case "SelectedDishFromCuisineTypeFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedDishFromCuisineTypeFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { selectedDishFromCuisineTypeOnCreate(); }
                }).commit();
                break;

            case "SelectedDishFromCookFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedDishFromCookFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("Probando bando");
                        selectedDishFromCookOnCreate(); }
                }).commit();
                break;

            case "explorer":
                setContentView(R.layout.activity_explorer);
            default:
                setContentView(R.layout.activity_explorer);
        }
    }

    public void exploreOnCreate() {
        findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineTypeFragment"));
        findViewById(R.id.explore_by_dish_button).setOnClickListener(view ->
                setFragment("exploreByDishFragment"));
        findViewById(R.id.explore_by_ingredient_button).setOnClickListener(view ->
                setFragment("exploreByIngredientFragment"));
        findViewById(R.id.explore_by_cook_button).setOnClickListener(view ->
                setFragment("exploreByCookFragment"));
        findViewById(R.id.profile_photo).setOnClickListener(view -> {
            Intent intent = new Intent(ExplorerActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });
        findViewById(R.id.back_button).setOnClickListener(view ->
                finish());
    }

    @SuppressLint("MissingInflatedId")
    public void exploreByCuisineTypeOnCreate(){
        cuisineTypeList = new ArrayList<>();
        cuisineTypeList = networkUtilities.makeRequest(new CuisineTypesRequest());

        ListView cuisineTypeListView = ((ListView) findViewById(R.id.cuisineTypeList));
        cuisineTypeListView.setAdapter(new TypeCuisineAdapter(this, R.layout.type_cuisine_row_layout, cuisineTypeList));
        cuisineTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                idCuisineType = ((CuisineType) adapterView.getItemAtPosition(position)).getId();
                setFragment("SelectedDishFromCuisineTypeFragment");

            }
        });

        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByDishOnCreate(){
        dishList = new ArrayList<>();
        dishList = networkUtilities.makeRequest(new DishesRequest());

        System.out.println(dishList.get(0).getName());
        ((ListView) findViewById(R.id.dish_list)).setAdapter(new DishAdapter(this, R.layout.dish_row_layout, dishList));

        System.out.println(dishList);
        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByIngredientOnCreate(){
        ingredientList = new ArrayList<>();
        ingredientList = networkUtilities.makeRequest(new IngredientsRequest());

        ListView ingredientListView = ((ListView) findViewById(R.id.ingredient_list));
        ingredientListView.setAdapter(new IngredientAdapter(this, R.layout.ingredient_list_row_layout, ingredientList));
        System.out.println(ingredientList);

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String ingredient_name = ((TextView) adapterView.getChildAt(position).findViewById(R.id.ingredient_name)).getText().toString();
                String ingredient_table_text = ((TextView) findViewById(R.id.ingredient_table)).getText().toString();

                if (ingredient_table_text.equals("")) {
                    ((TextView) findViewById(R.id.ingredient_table)).setText(ingredient_name);
                } else if (ingredient_table_text.contains(ingredient_name)){

                } else {
                    ((TextView) findViewById(R.id.ingredient_table)).setText(ingredient_table_text + ", " + ingredient_name);
                }
            }
        });

        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByCookOnCreate(){
        cookList = new ArrayList<>();
        cookList = networkUtilities.makeRequest(new CooksRequest());

        ListView cookListView = ((ListView) findViewById(R.id.cook_list));
        cookListView.setAdapter(new CookAdapter(this, R.layout.cook_row_layout, cookList));

        cookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                idCook = ((Cook) adapterView.getItemAtPosition(position)).getId();
                System.out.println("Pruebaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                setFragment("SelectedDishFromCookFragment");
            }
        });

        ((ListView) findViewById(R.id.cook_list)).setAdapter(new CookAdapter(this, R.layout.cook_row_layout, cookList));
        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void selectedDishFromCuisineTypeOnCreate(){
        selectedDishList = new ArrayList<>();
        selectedDishList = networkUtilities.makeRequest(new DishesByCuisineTypeRequest(idCuisineType));

        ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreByCuisineTypeFragment"));
    }

    public void selectedDishFromCookOnCreate(){
        selectedDishList = new ArrayList<>();
        System.out.println("IdCook------------------------------------"+idCook);
        selectedDishList = networkUtilities.makeRequest(new DishesByCookRequest(idCook));
        ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
    }
}