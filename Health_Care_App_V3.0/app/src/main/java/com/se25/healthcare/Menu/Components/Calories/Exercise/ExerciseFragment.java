package com.se25.healthcare.Menu.Components.Calories.Exercise;

import android.annotation.SuppressLint;

import com.se25.healthcare.Main.MainActivity;
import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.Menu.Components.Calories.NutriFragment;
import com.se25.healthcare.Models.APIs.NutritionixAPI;
import com.se25.healthcare.Models.Databases.LocalDatabase;
import com.se25.healthcare.Models.File.FileModel;
import com.se25.healthcare.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ExerciseFragment extends NutriFragment {
    String key = "exercise";

    @SuppressLint("DefaultLocale")
    @Override
    protected void request() {
        LocalDatabase db = new LocalDatabase(getContext(), 1);
        ArrayList<Item> items = db.getListData(R.string.button_BMI);
        float weight = items.size() > 0 ? (Float.parseFloat(items.get(0).getData(1))) : 50f;

        NutritionixAPI.requestExercise(requireActivity(), txtVwResult, etxtRequest, weight);
        ((MainActivity) requireActivity()).showMessage(R.string.header_weight, String.format("%.02f kg", weight));
    }

    @Override
    protected void setDataTemp(double value) {
        FileModel.setValueToTemp(key, value, getContext());
    }

    @Override
    protected double getDataTemp() {
        String temp = FileModel.getValueFromTemp(key, getContext());
        return Double.parseDouble(temp.length() > 0 ? temp : "0");
    }

    @Override
    protected int getHint() {
        return R.string.exercise_example;
    }

    @Override
    protected int getIcon() {
        return R.drawable.exercise;
    }
}
