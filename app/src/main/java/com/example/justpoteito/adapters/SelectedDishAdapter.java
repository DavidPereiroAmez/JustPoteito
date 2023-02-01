package com.example.justpoteito.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.justpoteito.R;
import com.example.justpoteito.RecipeActivity;
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

        if(selectedDishList.get(position).getImage().length() == 0){
            ((ImageView) view.findViewById(R.id.selected_dish_row_image)).setImageResource(R.drawable.verduras);
        }else{
            byte[] decodedString = Base64.decode(selectedDishList.get(position).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            //Drawable image = new BitmapDrawable(context.getResources(), decodedByte);
            ((ImageView) view.findViewById(R.id.selected_dish_row_image)).setImageBitmap(decodedByte);
        }

        ((TextView) view.findViewById(R.id.selected_dish_row_name)).setText(selectedDishList.get(position).getName() + "");
        ((TextView) view.findViewById(R.id.dishIdTextView)).setText(selectedDishList.get(position).getId() + "");
        view.setOnClickListener((View v) -> {
            String dishId = ((TextView) view.findViewById(R.id.dishIdTextView)).getText().toString();
            Intent intent = new Intent(context, RecipeActivity.class);
            intent.putExtra("dishId", dishId);
            context.startActivity(intent);
        });


        return view;
    }
}
