package com.se25.healthcare.Menu.Components.Calories;

import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class CaloriesItem extends Item {
    private final static InputForm processInput = new InputForm(0, R.string.button_calories, R.string.hint_kcal
            , R.drawable.calories_calculator);
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, R.string.header_calories_in, R.string.hint_kcal, R.drawable.fast_food),
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, R.string.header_calories_out, R.string.hint_kcal, R.drawable.exercise),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(5000, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(3500, R.string.evaluate_calories_bad_h2, R.drawable.item_very_bad),
                    new Entry(3000, R.string.evaluate_calories_bad_h1, R.drawable.item_bad),
                    new Entry(2200, R.string.evaluate_calories_warning_h, R.drawable.item_warning),
                    new Entry(1500, R.string.evaluate_calories_normal, R.drawable.item_good),
                    new Entry(-500, R.string.evaluate_calories_good, R.drawable.item_good),
                    new Entry(-1250, R.string.evaluate_calories_very_good, R.drawable.item_very_good),
                    new Entry(-2500, R.string.evaluate_calories_bad_l1, R.drawable.item_bad),
                    new Entry(-5000, R.string.evaluate_impossible, R.drawable.item_gray),
            }
    };

    public CaloriesItem() {
        super();
    }

    public CaloriesItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new CaloriesItem();
    }

    @Override
    public Double getProcessData() {
        return Double.parseDouble(getData(0)) - 2000 - Double.parseDouble(getData(1));
    }

    @Override
    public Double getProcessData(int index) {
        return getProcessData();
    }

    @Override
    public int getResTitleId() {
        return R.string.button_calories;
    }

    @Override
    public InputForm getListForm(int index) {
        return processInput;
    }

    @Override
    public InputForm[] getInputForms() {
        return inputForms;
    }



    @Override
    public int getDataQuantity(boolean isInput) {
        return isInput?inputForms.length:entries.length;
    }

    @Override
    public Entry[] getEntries(int index) {
        return entries[index];
    }
}
