package com.example.justpoteito.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.justpoteito.R;
import com.example.justpoteito.models.CuisineType;

import java.util.ArrayList;

public class TypeCuisineAdapter extends ArrayAdapter<CuisineType> {
    private final Context context;
    private ArrayList<CuisineType> cuisineTypeList;
    SharedPreferences sharedPreferences;

    public TypeCuisineAdapter(@NonNull Context context, int resource, ArrayList<CuisineType> cuisineTypeList) {
        super(context, resource, cuisineTypeList);
        this.context = context;
        this.cuisineTypeList = cuisineTypeList;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.type_cuisine_row_layout, null);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.cuisine_type_row_name)).setText(cuisineTypeList.get(position).getName() + "");
        ((TextView) view.findViewById(R.id.cuisineTypeIdTextView)).setText(cuisineTypeList.get(position).getId() + "");
        System.out.println("Prueba desde el adapter -> " + ((TextView) view.findViewById(R.id.cuisineTypeIdTextView)).getText());
        return view;
    }
}
