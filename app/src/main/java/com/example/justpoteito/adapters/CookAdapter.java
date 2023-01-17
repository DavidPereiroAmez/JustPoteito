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
import com.example.justpoteito.models.Cook;
import com.example.justpoteito.models.Dish;

import java.util.ArrayList;

public class CookAdapter extends ArrayAdapter<Cook> {
    private final Context context;
    private ArrayList<Cook> cookList;
    SharedPreferences sharedPreferences;

    public CookAdapter(@NonNull Context context, int resource, ArrayList<Cook> cookList) {
        super(context, resource, cookList);
        this.context = context;
        this.cookList = cookList;
    }
    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cook_row_layout, null);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.cook_name)).setText(cookList.get(position).getName() + "");

        return view;
    }
}
