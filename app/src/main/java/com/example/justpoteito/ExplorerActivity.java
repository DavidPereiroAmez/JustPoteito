package com.example.justpoteito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
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
import com.example.justpoteito.fragments.ExploreBySelectedDishFragment;
import com.example.justpoteito.fragments.ExploreFragment;
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Dish;
import com.example.justpoteito.models.Ingredient;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ExplorerActivity extends AppCompatActivity {
    FragmentTransaction transaction;
    Fragment exploreFragment, exploreByCuisineTypeFragment, exploreByDishFragment, exploreByIngredientFragment, exploreByCookFragment, exploreBySelectedDishFragment;
    ArrayList<CuisineType> cuisineTypeList;
    ArrayList<Dish> dishList;
    ArrayList<Ingredient> ingredientList;
    ArrayList<Cook> cookList;
    ArrayList<Dish> selectedDishList;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        getSupportActionBar().hide();

        exploreFragment = new ExploreFragment();
        exploreByCuisineTypeFragment = new ExploreByCuisineTypeFragment();
        exploreByDishFragment = new ExploreByDishFragment();
        exploreByIngredientFragment = new ExploreByIngredientFragment();
        exploreByCookFragment = new ExploreByCookFragment();
        exploreBySelectedDishFragment = new ExploreBySelectedDishFragment();

        setFragment("exploreFragment");
        /*findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment();*/


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
/*

        setContentView(R.layout.layout_explorer);

*/
        //exploreByCuisineTypeFragment = new ExploreByCuisineTypeFragment();
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
            case "exploreBySelectedDishFragment":
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,exploreBySelectedDishFragment).runOnCommit(new Runnable() {
                    @Override
                    public void run() { exploreBySelectedDishOnCreate(); }
                }).commit();
                break;
            case "explorer":
                setContentView(R.layout.activity_explorer);
            default:
                setContentView(R.layout.activity_explorer);
        }
    }
    public void exploreOnCreate(){
        findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineTypeFragment"));
        findViewById(R.id.explore_by_dish_button).setOnClickListener(view ->
                setFragment("exploreByDishFragment"));
        findViewById(R.id.explore_by_ingredient_button).setOnClickListener(view ->
                setFragment("exploreByIngredientFragment"));
        findViewById(R.id.explore_by_cook_button).setOnClickListener(view ->
                setFragment("exploreByCookFragment"));
    }
    public void exploreByCuisineTypeOnCreate(){
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
        System.out.println("prueba");
        //.setAdapter(new TypeCuisineAdapter(this, R.layout.type_cuisine_row_layout, cuisineTypeList));
        //System.out.println(cuisineTypeList);

        ListView cuisineTypeListView = ((ListView) findViewById(R.id.cuisineTypeList));
        cuisineTypeListView.setAdapter(new TypeCuisineAdapter(this, R.layout.type_cuisine_row_layout, cuisineTypeList));

        cuisineTypeListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                System.out.println("prueba de boton --------------------");
                setFragment("exploreBySelectedDishFragment");
            }
        });

        /*findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineFragment"));*/
    }
    public void exploreByDishOnCreate(){
        System.out.println("pruebaDish");
        dishList = new ArrayList<>();
        dishList.add(new Dish(
                0,
                "Macarrones con tomate",
                30
        ));
        dishList.add(new Dish(
                1,
                "Bacalao al pil pil",
                120
        ));
        dishList.add(new Dish(
                2,
                "Huevos con patatas",
                20
        ));
        dishList.add(new Dish(
                3,
                "Paella",
                200
        ));
        dishList.add(new Dish(
                4,
                "Alubias",
                260
        ));
        dishList.add(new Dish(
                5,
                "Pizza 4 quesos",
                60
        ));
        dishList.add(new Dish(
                6,
                "Risotto",
                90
        ));
        dishList.add(new Dish(
                7,
                "Entrecot a las finas hierbas",
                25
        ));
        System.out.println(dishList.get(0).getName());
        ((ListView) findViewById(R.id.dish_list)).setAdapter(new DishAdapter(this, R.layout.dish_row_layout, dishList));

        System.out.println(dishList);
        /*findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineFragment"));*/
    }
    public void exploreByIngredientOnCreate(){
        System.out.println("Porbando Ingredient");
        ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient(
                0,
                "Tomate"
        ));
        ingredientList.add(new Ingredient(
                1,
                "Bacalao"
        ));
        ingredientList.add(new Ingredient(
                2,
                "Huevo"
        ));
        ingredientList.add(new Ingredient(
                3,
                "Arroz"
        ));
        ingredientList.add(new Ingredient(
                4,
                "Alubias"
        ));
        ingredientList.add(new Ingredient(
                5,
                "Quesos"
        ));
        ingredientList.add(new Ingredient(
                6,
                "Harina"
        ));
        ingredientList.add(new Ingredient(
                7,
                "Lechuga"
        ));

        ListView ingredientListView = ((ListView) findViewById(R.id.ingredient_list));
        ingredientListView.setAdapter(new IngredientAdapter(this, R.layout.ingredient_list_row_layout, ingredientList));
        System.out.println(ingredientList);

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                String ingredient_name = ((TextView) adapterView.getChildAt(position).findViewById(R.id.ingredient_name)).getText().toString();
                String ingredient_table_text = ((TextView) findViewById(R.id.ingredient_table)).getText().toString();
                if(ingredient_table_text.equals("")){
                    ((TextView) findViewById(R.id.ingredient_table)).setText(ingredient_name);
                }else if(ingredient_table_text.contains(ingredient_name)){

                }else{
                    ((TextView) findViewById(R.id.ingredient_table)).setText(ingredient_table_text + ", " + ingredient_name);
                }
            }
        });
        /*findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineFragment"));*/
    }
    public void exploreByCookOnCreate(){
        System.out.println("Explore by cook");
        cookList = new ArrayList<>();
        cookList.add(new Cook(
                0,
                "Carlos Argiñano"
        ));
        cookList.add(new Cook(
                1,
                "Alberto Chicote"
        ));
        cookList.add(new Cook(
                2,
                "Dani García"
        ));
        cookList.add(new Cook(
                3,
                "David De Jorge"
        ));
        cookList.add(new Cook(
                4,
                "Eneko Atxa"
        ));
        cookList.add(new Cook(
                5,
                "Juan Mari Arzak"
        ));
        cookList.add(new Cook(
                6,
                "Pedro Subijana"
        ));
        cookList.add(new Cook(
                7,
                "Martín Berasategi"
        ));

        ((ListView) findViewById(R.id.cook_list)).setAdapter(new CookAdapter(this, R.layout.cook_row_layout, cookList));
        /*findViewById(R.id.explore_by_cuisine_button).setOnClickListener(view ->
                setFragment("exploreByCuisineFragment"));*/
    }
    public void exploreBySelectedDishOnCreate(){

        System.out.println("Probando selected dish");
        selectedDishList = new ArrayList<>();
        selectedDishList.add(new Dish(
                0,
                "Macarrones con tomate",
                30
        ));
        selectedDishList.add(new Dish(
                1,
                "Bacalao al pil pil",
                120
        ));
        selectedDishList.add(new Dish(
                2,
                "Huevos con patatas",
                20
        ));
        selectedDishList.add(new Dish(
                3,
                "Paella",
                200
        ));
        selectedDishList.add(new Dish(
                4,
                "Alubias",
                260
        ));
        selectedDishList.add(new Dish(
                5,
                "Pizza 4 quesos",
                60
        ));
        selectedDishList.add(new Dish(
                6,
                "Risotto",
                90
        ));
        selectedDishList.add(new Dish(
                7,
                "Entrecot a las finas hierbas con salsa de setas",
                25
        ));
        ((ListView) findViewById(R.id.selected_dish_list)).setAdapter(new SelectedDishAdapter(this, R.layout.selected_dish_row_layout, selectedDishList));
    }

}