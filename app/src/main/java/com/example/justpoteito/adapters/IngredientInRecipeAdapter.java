package com.example.justpoteito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.justpoteito.ExplorerActivity;
import com.example.justpoteito.R;
import com.example.justpoteito.RecipeActivity;
import com.example.justpoteito.models.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientInRecipeAdapter extends ArrayAdapter<Ingredient> {
    private final Context context;
    private ArrayList<Ingredient> ingredientList;
    private RecipeActivity recipeActivity = new RecipeActivity();
    Map<Integer, View> views = new HashMap<Integer, View>();

    public IngredientInRecipeAdapter(@NonNull Context context, int resource, ArrayList<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Pasarle el otro view de fuera como parámetro del constructor y con el onClick settearle el texto

        if (views.containsKey(position)) {
            return views.get(position);
        }

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ingredient_row_layout, parent, false);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.row_ingredient_name)).setText(ingredientList.get(position).getName() + "");
        ((TextView) view.findViewById(R.id.ingredientIdTextView)).setText(ingredientList.get(position).getId() + "");
        ((TextView) view.findViewById(R.id.ingredient_amount)).setText(ingredientList.get(position).getAmount() + "");

        views.put(position, view);

        return view;
    }
}
