package com.example.justpoteito.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.justpoteito.R;
import com.example.justpoteito.models.CuisineType;
import com.example.justpoteito.models.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private final Context context;
    private ArrayList<Ingredient> ingredientList;

    public IngredientAdapter(@NonNull Context context, int resource, ArrayList<Ingredient> ingredientList) {
        super(context, resource, ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.ingredient_row_layout, null);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.row_ingredient_name)).setText(ingredientList.get(position).getName() + "");

        return view;
    }
}
