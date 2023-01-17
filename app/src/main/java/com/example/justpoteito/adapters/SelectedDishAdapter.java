package com.example.justpoteito.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.justpoteito.R;
import com.example.justpoteito.models.Dish;

import java.util.ArrayList;

public class SelectedDishAdapter extends ArrayAdapter<Dish> {
    private final Context context;
    private ArrayList<Dish> selectedDishList;
    SharedPreferences sharedPreferences;

    public SelectedDishAdapter(@NonNull Context context, int resource, ArrayList<Dish> selectedDishList) {
        super(context, resource, selectedDishList);
        this.context = context;
        this.selectedDishList = selectedDishList;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.selected_dish_row_layout, null);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.selected_dish_row_name)).setText(selectedDishList.get(position).getName() + "");

        return view;
    }
}
