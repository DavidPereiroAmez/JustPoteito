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
import com.example.justpoteito.models.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IngredientAdapter extends ArrayAdapter<Ingredient> {
    private final Context context;
    private ArrayList<Ingredient> ingredientList;
    private ExplorerActivity explorerActivity = new ExplorerActivity();
    Map<Integer, View> views = new HashMap<Integer, View>();
    private TextView targetView;
    private TextView ingredientListTextView;

    public IngredientAdapter(@NonNull Context context, int resource, ArrayList<Ingredient> ingredientList, TextView targetView, TextView ingredientListTextView) {
        super(context, resource, ingredientList);
        this.context = context;
        this.ingredientList = ingredientList;
        this.targetView = targetView;
        this.ingredientListTextView = ingredientListTextView;
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
        View view = layoutInflater.inflate(R.layout.ingredient_list_row_layout, parent, false);

        //((ImageView) view.findViewById(R.id.cuisine_type_row_imageView)).setImageURI(cuisineTypeList.get(position).getImage() + "");
        ((TextView) view.findViewById(R.id.ingredient_name)).setText(ingredientList.get(position).getName() + "");
        ((TextView) view.findViewById(R.id.ingredientIdTextView)).setText(ingredientList.get(position).getId() + "");

        view.setOnClickListener((View v) -> {

            String ingredient_name = ((TextView) v.findViewById(R.id.ingredient_name)).getText().toString();
                String ingredient_id = ((TextView) v.findViewById(R.id.ingredientIdTextView)).getText().toString();
                String ingredient_table_text = targetView.getText().toString();
                if(ingredient_table_text.equals("")){
                    targetView.setText(ingredient_name);
                    ingredientListTextView.setText(ingredientListTextView.getText() + ingredient_id + ",");
                }else if(ingredient_table_text.contains(ingredient_name)){

                }else{
                    targetView.setText(ingredient_table_text + ", " + ingredient_name);
                    ingredientListTextView.setText(ingredientListTextView.getText() + ingredient_id + ",");
                }
        });

        views.put(position, view);

        return view;
    }


}
