package com.se25.healthcare.Menu.Components.Calories.Food;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Menu.Components.Calories.NutriFragment;
import com.se25.healthcare.Models.APIs.NutritionixAPI;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.R;

public class FoodFragment extends NutriFragment {
    String key = "food";
    @Override
    protected void request() {
        NutritionixAPI.requestFood(requireActivity(),txtVwResult,etxtRequest);
    }

    @Override
    protected void setDataTemp(double value) {
        FileModel.setValueToTemp(key,value,getContext());
    }

    @Override
    protected double getDataTemp() {
        String temp = FileModel.getValueFromTemp(key,getContext());
        return Double.parseDouble(temp.length()>0?temp:"0");
    }

    @Override
    protected int getHint() {
        return R.string.food_example;
    }

    @Override
    protected int getIcon() {
        return R.drawable.fast_food;
    }
}
