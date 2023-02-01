package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

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
import com.example.justpoteito.fragments.SelectedDishFromIngredientFragment;
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;
import com.example.justpoteito.network.request.CooksRequest;
import com.example.justpoteito.network.request.CuisineTypesRequest;
import com.example.justpoteito.network.request.DishesByCookRequest;
import com.example.justpoteito.network.request.DishesByCuisineTypeRequest;
import com.example.justpoteito.network.request.DishesByIngredientRequest;
import com.example.justpoteito.network.request.DishesRequest;
import com.example.justpoteito.network.request.IngredientsRequest;
import com.example.justpoteito.network.NetworkUtilities;

import java.util.ArrayList;

public class ExplorerActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;

    Fragment exploreFragment,
             exploreByCuisineTypeFragment,
             exploreByDishFragment,
             exploreByIngredientFragment,
             exploreByCookFragment,
             selectedDishFromCuisineTypeFragment,
             selectedDishFromCookFragment,
             selectedDishFromIngredientFragment;

    ArrayList<CuisineType> cuisineTypeList;
    ArrayList<Dish> dishList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<Cook> cookList;
    ArrayList<Dish> selectedDishList;
    String ingredientIds;
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
        selectedDishFromIngredientFragment = new SelectedDishFromIngredientFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
                fragmentTransaction.addToBackStack(null);
                break;

            case "exploreByDishFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByDishFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByDishOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "exploreByIngredientFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByIngredientFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByIngredientOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "exploreByCookFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreByCookFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreByCookOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "SelectedDishFromCuisineTypeFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedDishFromCuisineTypeFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { selectedDishFromCuisineTypeOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "SelectedDishFromCookFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedDishFromCookFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() {
                        selectedDishFromCookOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "SelectedDishFromIngredientFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedDishFromIngredientFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() {
                        selectedDishFromIngredientOnCreate(); }
                }).commit();
                fragmentTransaction.addToBackStack(null);
                break;

            case "explorer":
                setContentView(R.layout.activity_explorer);
                fragmentTransaction.addToBackStack(null);
                break;
            default:
                setContentView(R.layout.activity_explorer);
                fragmentTransaction.addToBackStack(null);
                break;
        }
    }

    public void exploreOnCreate() {
        TextView hiTextView = findViewById(R.id.textView12);
        hiTextView.setText(sharedPreferences.getString("username", ""));

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
        findViewById(R.id.back_button).setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @SuppressLint("MissingInflatedId")
    public void exploreByCuisineTypeOnCreate(){
        cuisineTypeList = new ArrayList<>();
        cuisineTypeList = networkUtilities.makeRequest(new CuisineTypesRequest());
        if(cuisineTypeList!=null){
            ListView cuisineTypeListView = ((ListView) findViewById(R.id.cuisineTypeList));
            cuisineTypeListView.setAdapter(new TypeCuisineAdapter(this, R.layout.type_cuisine_row_layout, cuisineTypeList));
            cuisineTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    idCuisineType = ((CuisineType) adapterView.getItemAtPosition(position)).getId();
                    setFragment("SelectedDishFromCuisineTypeFragment");

                }
            });
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }
        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByDishOnCreate(){
        dishList = new ArrayList<>();
        dishList = networkUtilities.makeRequest(new DishesRequest());
        if(dishList!=null){
            ((ListView) findViewById(R.id.dish_list)).setAdapter(new DishAdapter(this, R.layout.dish_row_layout, dishList));
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }
        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByIngredientOnCreate(){
        ingredientList = new ArrayList<>();
        TextView targetView;
        TextView ingredientIdsTextView;
        ingredientList = networkUtilities.makeRequest(new IngredientsRequest());

        if(ingredientList!=null){
            ListView ingredientListView = ((ListView) findViewById(R.id.ingredient_list));
            targetView = findViewById(R.id.ingredient_table);
            ingredientIdsTextView = findViewById(R.id.ingredientIdListTextView);
            ingredientListView.setAdapter(new IngredientAdapter(ExplorerActivity.this, R.layout.ingredient_list_row_layout, ingredientList, targetView, ingredientIdsTextView));

            findViewById(R.id.expore_by_ingredient_search_button).setOnClickListener(view -> {
                ingredientIds = ingredientIdsTextView.getText().toString();
                ingredientIds.substring(0, ingredientIds.length() - 1);
                System.out.println(ingredientIds);
                setFragment("SelectedDishFromIngredientFragment");
            });
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }

        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void exploreByCookOnCreate(){
        cookList = new ArrayList<>();
        cookList = networkUtilities.makeRequest(new CooksRequest());

        System.out.println(cookList);
        if(cookList!=null){
            System.out.println("Entrando al if que no tiene que entrar");
            ListView cookListView = ((ListView) findViewById(R.id.cook_list));
            cookListView.setAdapter(new CookAdapter(this, R.layout.cook_row_layout, cookList));

            cookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    idCook = ((Cook) adapterView.getItemAtPosition(position)).getId();
                    setFragment("SelectedDishFromCookFragment");
                }
            });
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }

        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void selectedDishFromCuisineTypeOnCreate(){
        selectedDishList = new ArrayList<>();
        selectedDishList = networkUtilities.makeRequest(new DishesByCuisineTypeRequest(idCuisineType));

        if(selectedDishList!=null){
            ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }

        findViewById(R.id.back_button).setOnClickListener(view ->
                setFragment("exploreFragment"));
    }

    public void selectedDishFromCookOnCreate(){
        selectedDishList = new ArrayList<>();
        selectedDishList = networkUtilities.makeRequest(new DishesByCookRequest(idCook));

        if(selectedDishList!=null){
            ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }
    }

    public void selectedDishFromIngredientOnCreate(){
        selectedDishList = new ArrayList<>();
        selectedDishList = networkUtilities.makeRequest(new DishesByIngredientRequest(ingredientIds));

        if(selectedDishList!=null){
            ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
        }
        else{
            new Toast(this).makeText(this, (R.string.request_error), Toast.LENGTH_LONG).show();
            setFragment("exploreFragment");
        }
    }

}