package com.se25.healthcare.Menu.Components.BMI;

import android.view.inputmethod.EditorInfo;

import com.se25.healthcare.Menu.Components.Base.Item;
import com.se25.healthcare.R;

import java.time.LocalDateTime;

public class BMIItem extends Item {
    private final static InputForm processInput = new InputForm(0, R.string.header_bmi, R.string.hint_empty, R.drawable.bmi);
    private final static InputForm[] inputForms = {
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER, R.string.header_height, R.string.hint_cm, R.drawable.height),
            new InputForm(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL, R.string.header_weight, R.string.hint_kg, R.drawable.scale),
    };
    private final static Entry[][] entries = {
            {
                    new Entry(100, R.string.evaluate_impossible, R.drawable.item_gray),
                    new Entry(40, R.string.evaluate_bmi_very_bad_h, R.drawable.item_very_bad),
                    new Entry(35, R.string.evaluate_bmi_bad_h2, R.drawable.item_bad),
                    new Entry(30, R.string.evaluate_bmi_bad_h1, R.drawable.item_bad),
                    new Entry(25, R.string.evaluate_bmi_warning_h, R.drawable.item_warning),
                    new Entry(18.5, R.string.evaluate_bmi_normal, R.drawable.item_very_good),
                    new Entry(17, R.string.evaluate_bmi_warning_l, R.drawable.item_good),
                    new Entry(16, R.string.evaluate_bmi_bad_l1, R.drawable.item_warning),
                    new Entry(10, R.string.evaluate_bmi_bad_l2, R.drawable.item_bad),
                    new Entry(0, R.string.evaluate_impossible, R.drawable.item_gray),
            }
    };

    public BMIItem() {
        super();
    }

    public BMIItem(LocalDateTime dateCreate, Object... data) {
        super(dateCreate, data);
    }

    @Override
    public Item Instance() {
        return new BMIItem();
    }

    @Override
    public Double getProcessData() {
        return Double.parseDouble(getData(1))/
                Math.pow(Double.parseDouble(getData(0))/100,2);
    }

    @Override
    public Double getProcessData(int index) {
        return getProcessData();
    }

    @Override
    public int getResTitleId() {
        return R.string.button_BMI;
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
